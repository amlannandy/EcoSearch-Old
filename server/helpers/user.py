from functools import wraps
from flask import request, jsonify
from flask_jwt_extended import decode_token

from server.app import db
from server.models.User import User

def find_by_id(id):
  return User.query.filter_by(id=id).first()

def find_by_email(email):
  return User.query.filter_by(email=email).first()

def find_by_username(username):
  return User.query.filter_by(username=username).first()

def save(user):
  db.session.add(user)
  db.session.commit()
  return User.query.filter_by(email=user.email).first()

def to_json(user):
  return {
    'id': user.id,
    'name': user.name,
    'username': user.username,
    'email': user.email,
    'created_at': user.created_at,
  }

# Use this decorator on protected routes
def login_only(fun):
  @wraps(fun)
  def wrap(*args, **kwargs):
     
    authorization_header = request.headers.get('Authorization')
    if not authorization_header:
      response = {
        'success': False,
        'msg': 'Please log in'
      }
      return jsonify(response), 401

    token = authorization_header.split()[1]
    if not token:
      response = {
        'success': False,
        'msg': 'Please log in'
      }
      return jsonify(response), 401

    email = decode_token(token)['sub']
    user = User.query.filter_by(email=email).first()
    if not user:
      response = {
        'success': False,
        'msg': 'Invalid token'
      }
      return jsonify(response), 401

    # Else continue
    return fun(*args, **kwargs)
  return wrap