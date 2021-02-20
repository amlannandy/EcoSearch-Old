from server.models.User import User

def find_by_email(email):
  return User.query.filter_by(email=email).first()

def find_by_username(username):
  return User.query.filter_by(username=username).first()