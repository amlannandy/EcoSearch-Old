from server.app import db, app
from server.models.User import User
from server.models.Record import Record
from server.models.Location import Location

with app.app_context():
  db.create_all()