from flask.views import MethodView
from flask import request, jsonify
from flask_jwt_extended import create_access_token

from server.models.User import User
from server.helpers.user import find_by_email, find_by_username, login_only, save

class LoginView(MethodView):
  def post(self):
    data = request.get_json()
    if not data:
      response = {
        'success': False,
        'msg': 'Please provide login data',
      }
      return jsonify(response), 400

    try:
      email = data['email']
      password = data['password']
    except KeyError as err:
      response = {
        'success': False,
        'msg': f'Please provide {str(err)}'
      }
      return jsonify(response), 400

    user = find_by_email(email)
    if not user:
      response = {
        'success': False,
        'msg': 'User with this email does not exist',
      }
      return jsonify(response), 404

    if not user.match_password(password):
      response = {
        'success': False,
        'msg': 'Incorrect Password',
      }
      return jsonify(response), 401

    token = create_access_token(identity=email)

    response = {
      'success': True,
      'msg': 'User successfully logged in',
      'data': token,
    }
    return jsonify(response), 200

class RegisterView(MethodView):
  def post(self):
    data = request.get_json()
    if not data:
      response = {
        'success': False,
        'msg': 'Please provide registration data',
      }
      return jsonify(response), 400

    try:
      name = data['name']
      email = data['email']
      username = data['username']
      password = data['password']
    except KeyError as err:
      response = {
        'success': False,
        'msg': f'Please provide {str(err)}'
      }
      return jsonify(response), 400

    existing_user = find_by_email(email)
    if existing_user:
      response = {
        'success': False,
        'msg': 'User with this email already exists'
      }
      return jsonify(response), 400

    existing_user = find_by_username(username)
    if existing_user:
      response = {
        'success': False,
        'msg': 'Username already taken'
      }
      return jsonify(response), 400

    user = User(name, username, email, password)
    save(user)

    token = create_access_token(identity=email)

    response = {
      'success': True,
      'msg': 'User successfully registered',
      'data': token,
    }
    return jsonify(response), 200
    
class CurrentUserView(MethodView):
  
  @login_only
  def get(self):
    user = request.args['user']

    response = {
      'success': True,
      'data': user,
    }
    
    return jsonify(response), 200

auth_controller = {
  'login': LoginView.as_view('login'),
  'register': RegisterView.as_view('register'),
  'get_current_user': CurrentUserView.as_view('get_current_user'),
}