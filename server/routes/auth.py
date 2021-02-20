import sys
sys.path.append('..')
from flask import Blueprint, jsonify, request

from server.models.User import User
from server.app import db

auth = Blueprint('auth', __name__, url_prefix='/auth')

@auth.route('/login', methods=['POST'])
def login():
  return 'Login route', 200

@auth.route('/register', methods=['POST'])
def register():
  data = request.get_json()
  if not data:
    response = {
      'success': False,
      'message': 'Please provide login data',
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
      'message': f'Please provide {str(err)}'
    }
    return jsonify(response), 400

  user = User(name=name, email=email, username=username, password=password)
  db.session.add(user)
  db.session.commit()

  response = {
    'success': True,
    'message': 'User successfully registered',
    'data': user,
  }
  return jsonify(response), 200

@auth.route('/logout', methods=['GET'])
def logout():
   return 'Logout Route', 200

@auth.route('/current-user', methods=['GET'])
def get_current_user():
   return 'Get Current User Route', 200