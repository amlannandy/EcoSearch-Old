from flask import Flask
from flask_sqlalchemy import SQLAlchemy

db = SQLAlchemy()
  
# Init flask app
app = Flask(__name__)

from server.routes.auth import auth as AuthBlueprint
from server.routes.records import records as RecordsBlueprint

# Setup and init db
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///db.sqlite3'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = True
db.init_app(app)

app.register_blueprint(AuthBlueprint)
app.register_blueprint(RecordsBlueprint)