import sys
sys.path.append('..')
from flask import Blueprint, jsonify, request

from server.app import db
from server.models.User import User
from server.helpers.user import find_by_email, find_by_username

auth = Blueprint('auth', __name__, url_prefix='/auth')

@auth.route('/login', methods=['POST'])
def login():
  data = request.get_json()
  if not data:
    response = {
      'success': False,
      'message': 'Please provide login data',
    }
    return jsonify(response), 400

  try:
    email = data['email']
    password = data['password']
  except KeyError as err:
    response = {
      'success': False,
      'message': f'Please provide {str(err)}'
    }
    return jsonify(response), 400

  user = find_by_email(email)
  if not user:
    response = {
      'success': False,
      'message': 'User with this email does not exist',
    }
    return jsonify(response), 400

  if password != user.password:
    response = {
      'success': False,
      'message': 'Incorrect Password',
    }
    return jsonify(response), 401

  response = {
    'success': True,
    'message': 'User successfully logged in',
  }
  return jsonify(response), 200
  

@auth.route('/register', methods=['POST'])
def register():
  data = request.get_json()
  if not data:
    response = {
      'success': False,
      'message': 'Please provide registration data',
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

  existing_user = find_by_email(email)
  if existing_user:
    response = {
      'success': False,
      'message': 'User with this email already exists'
    }
    return jsonify(response), 400

  existing_user = find_by_username(username)
  if existing_user:
    response = {
      'success': False,
      'message': 'Username already taken'
    }
    return jsonify(response), 400

  user = User(name=name, email=email, username=username, password=password)
  db.session.add(user)
  db.session.commit()

  response = {
    'success': True,
    'message': 'User successfully registered',
  }
  return jsonify(response), 200

@auth.route('/logout', methods=['GET'])
def logout():
   return 'Logout Route', 200

@auth.route('/current-user', methods=['GET'])
def get_current_user():
   return 'Get Current User Route', 200