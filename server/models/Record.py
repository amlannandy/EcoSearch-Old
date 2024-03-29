from datetime import datetime

from sqlalchemy.orm import backref

from server.app import db

class Record(db.Model):
  id = db.Column(db.Integer, primary_key=True)
  user_email = db.Column(db.Integer, nullable=False)
  title = db.Column(db.String(100), nullable=False)
  description = db.Column(db.String(100), default=None)
  type = db.Column(db.String(20), nullable=False)
  location_id = db.Column(db.Integer, db.ForeignKey('location.id'), nullable=False)
  location = db.relationship('Location', backref=db.backref('records', lazy=True))
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
      'type': self.type,
      'label': self.label,
      'confidence': self.label,
      'image': self.image,
      'location': {
        'latitude': self.location.latitude,
        'longitude': self.location.longitude,
        'address': self.location.address,
      },
      'created_at': self.created_at,
    }