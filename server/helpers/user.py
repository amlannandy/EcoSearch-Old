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