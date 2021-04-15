from flask_bcrypt import Bcrypt
from datetime import datetime

from server.app import db

class Record(db.Model):
  id = db.Column(db.Integer, primary_key=True)
  title = db.Column(db.String(100), nullable=False)
  description = db.Column(db.String(100), default=None)
  label = db.Column(db.String(100), default=None)
  confidence = db.Column(db.Integer, default=None)
  image = db.Column(db.String(200), nullable=False)
  created_at = db.Column(db.DateTime, nullable=False, default=datetime.utcnow)