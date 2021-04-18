from server.app import db

class Location(db.Model):
  id = db.Column(db.Integer, primary_key=True)
  address = db.Column(db.String(100), nullable=False)
  latitude = db.Column(db.Integer, nullable=False)
  longitude = db.Column(db.Integer, nullable=False)