from flask_bcrypt import Bcrypt
from datetime import datetime

from server.app import db

class User(db.Model):
  id = db.Column(db.Integer, primary_key=True)
  name = db.Column(db.String(100), nullable=False)
  username = db.Column(db.String(80), unique=True, nullable=False)
  email = db.Column(db.String(120), unique=True, nullable=False)
  password = db.Column(db.String(100), nullable=False)
  created_at = db.Column(db.DateTime, nullable=False, default=datetime.utcnow)

  def __init__(self, name, username, email, password = None):
    self.name = name
    self.username = username
    self.email = email
    if password:
      self.password = self.generate_password_hash(password)

  @staticmethod
  def generate_password_hash(password):
    # Returns hash of password
    return Bcrypt().generate_password_hash(password,10).decode()

  def match_password(self, password):
    # Match passwords
    return Bcrypt().check_password_hash(self.password, password)