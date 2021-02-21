from flask_bcrypt import Bcrypt
from datetime import datetime

from server.app import db

class Record(db.Model):
  id = db.Column(db.Integer, primary_key=True)
  label = db.Column(db.String(100), nullable=False)
  confidence = db.Column(db.Integer, nullable=False)
  image = db.Column(db.String(200), unique=True, nullable=False)
  created_at = db.Column(db.DateTime, nullable=False, default=datetime.utcnow)