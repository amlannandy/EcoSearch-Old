from functools import wraps
from flask import request, jsonify
from flask_jwt_extended import decode_token
from werkzeug.datastructures import ImmutableMultiDict

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

def update_user(id, name, username):
  user = User.query.filter_by(id=id).first()
  user.name = name
  user.username = username
  db.session.commit()
  return user

def update_password(id, password):
  user = User.query.filter_by(id=id).first()
  user.password = User.generate_password_hash(password)
  db.session.commit()
  return user

def delete_by_id(id):
  User.query.filter_by(id=id).delete()
  db.session.commit()

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

    http_args = request.args.to_dict()
    http_args['user'] = to_json(user)
    request.args = ImmutableMultiDict(http_args )

    # Else continue
    return fun(*args, **kwargs)
  return wrap