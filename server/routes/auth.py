import sys
sys.path.append('..')
from flask import Blueprint, jsonify, request
from flask_jwt_extended import get_jwt_identity, jwt_required, create_access_token

from server.app import db, auto
from server.models.User import User
from server.helpers.user import find_by_email, find_by_username, to_json, save

auth = Blueprint('auth', __name__, url_prefix='/auth')

@auth.route('/login', methods=['POST'])
@auto.doc('auth')
def login():
  '''
  Login with credentials
  '''
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
    'data': {
      'user': to_json(user),
      'token': token,
    },
  }
  return jsonify(response), 200
  

@auth.route('/register', methods=['POST'])
@auto.doc('auth')
def register():
  '''
  Register a new user
  '''
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

  user = User(name=name, email=email, username=username, password=password)
  user = save(user)

  token = create_access_token(identity=email)

  response = {
    'success': True,
    'msg': 'User successfully registered',
    'data': {
      'user': to_json(user),
      'token': token,
    }
  }
  return jsonify(response), 200

@auth.route('/current-user', methods=['GET'])
@auto.doc('auth')
@jwt_required()
def get_current_user():
  '''
  Get the current logged in user
  '''
  email = get_jwt_identity()

  user = find_by_email(email)
  if not user:
    response = {
      'success': False,
      'msg': 'User does not exist',
    }
    return jsonify(response), 404

  response = {
    'success': True,
    'data': to_json(user),
  }
  
  return jsonify(response), 200