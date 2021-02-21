from server.app import db, app
from server.models.User import User
from server.models.Record import Record

with app.app_context():
  db.create_all()