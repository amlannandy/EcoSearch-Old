from flask.views import MethodView
from flask import request, jsonify, make_response
from flask_jwt_extended import create_access_token

from server.models.User import User
from server.helpers.user import (
  to_json,
  find_by_email,
  find_by_id, 
  find_by_username, 
  login_only, 
  save,
  update_user,
  update_password,
)

class LoginView(MethodView):
  def post(self):
    data = request.get_json()
    if not data:
      response = {
        'success': False,
        'msg': 'Please provide login data',
      }
      return make_response(jsonify(response)), 400

    try:
      email = data['email']
      password = data['password']
    except KeyError as err:
      response = {
        'success': False,
        'msg': f'Please provide {str(err)}'
      }
      return (make_response(jsonify(response))), 400

    user = find_by_email(email)
    if not user:
      response = {
        'success': False,
        'msg': 'User with this email does not exist',
      }
      return make_response(jsonify(response)), 404

    if not user.match_password(password):
      response = {
        'success': False,
        'msg': 'Incorrect Password',
      }
      return make_response(jsonify(response)), 401

    token = create_access_token(identity=email)

    response = {
      'success': True,
      'msg': 'User successfully logged in',
      'data': token,
    }
    return make_response(jsonify(response)), 200

class RegisterView(MethodView):
  def post(self):
    data = request.get_json()
    if not data:
      response = {
        'success': False,
        'msg': 'Please provide registration data',
      }
      return make_response(jsonify(response)), 400

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
      return make_response(jsonify(response)), 400

    existing_user = find_by_email(email)
    if existing_user:
      response = {
        'success': False,
        'msg': 'User with this email already exists'
      }
      return make_response(jsonify(response)), 400

    existing_user = find_by_username(username)
    if existing_user:
      response = {
        'success': False,
        'msg': 'Username already taken'
      }
      return make_response(jsonify(response)), 400

    user = User(name, username, email, password)
    save(user)

    token = create_access_token(identity=email)

    response = {
      'success': True,
      'msg': 'User successfully registered',
      'data': token,
    }
    return make_response(jsonify(response)), 200
    
class CurrentUserView(MethodView):
  
  @login_only
  def get(self):
    user = request.args['user']

    response = {
      'success': True,
      'data': user,
    }
    
    return make_response(jsonify(response)), 200

class UpdateInfoView(MethodView):
  # Update name and username
  @login_only
  def put(self):
    data = request.get_json()
    if not data:
      response = {
        'success': False,
        'msg': 'Please provide name and username',
      }
      return make_response(jsonify(response)), 400

    try:
      name = data['name']
      username = data['username']
    except KeyError as err:
      response = {
        'success': False,
        'msg': f'Please provide {str(err)}'
      }
      return make_response(jsonify(response)), 400

    existing_user = find_by_username(username)
    if existing_user:
      response = {
        'success': False,
        'msg': 'Username already taken'
      }
      return make_response(jsonify(response)), 400

    user_id = request.args['user']['id']
    user = update_user(user_id, name, username)

    response = {
      'success': True,
      'data': to_json(user),
    }
    return make_response(jsonify(response)), 200

class UpdatePasswordView(MethodView):
  # Update password of a user
  @login_only
  def put(self):
    data = request.get_json()
    if not data:
      response = {
        'success': False,
        'msg': 'Please provide your current and new password',
      }
      return make_response(jsonify(response)), 400

    try:
      current_password = data['current_password']
      new_password = data['new_password']
    except KeyError as err:
      response = {
        'success': False,
        'msg': f'Please provide {str(err)}'
      }
      return make_response(jsonify(response)), 400

    user = find_by_id(request.args['user']['id'])
    if not user.match_password(current_password):
      response = {
        'success': False,
        'msg': 'Incorrect Password',
      }
      return make_response(jsonify(response)), 401

    user = update_password(user.id, new_password)

    response = {
      'success': True,
      'data': to_json(user),
    }
    return make_response(jsonify(response)), 200


auth_controller = {
  'login': LoginView.as_view('login'),
  'register': RegisterView.as_view('register'),
  'get_current_user': CurrentUserView.as_view('get_current_user'),
  'update_info': UpdateInfoView.as_view('update_info'),
  'update_password': UpdatePasswordView.as_view('update_password'),
}