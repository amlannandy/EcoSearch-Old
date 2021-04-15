from flask_bcrypt import Bcrypt
from datetime import datetime

from server.app import db

class Record(db.Model):
  id = db.Column(db.Integer, primary_key=True)
  user_email = db.Column(db.Integer, nullable=False)
  title = db.Column(db.String(100), nullable=False)
  description = db.Column(db.String(100), default=None)
  label = db.Column(db.String(100), default=None)
  confidence = db.Column(db.Integer, default=None)
  image = db.Column(db.String(200), default=None)
  created_at = db.Column(db.DateTime, nullable=False, default=datetime.utcnow)

  def to_json(self):
    return {
      'id': self.id,
      'user_email': self.user_email,
      'title': self.title,
      'description': self.description,
      'label': self.label,
      'confidence': self.label,
      'image': self.image,
      'created_at': self.created_at,
    }