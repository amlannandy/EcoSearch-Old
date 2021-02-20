from server.app import db, app
from server.models.User import User

with app.app_context():
  db.create_all()