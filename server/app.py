from flask import Flask
from flask_sqlalchemy import SQLAlchemy

from routes.auth import auth as AuthBlueprint
from routes.records import records as RecordsBlueprint

# Init flask app
app = Flask(__name__)

# Setup and init db
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///db.sqlite3'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = True
db = SQLAlchemy(app)

app.register_blueprint(AuthBlueprint)
app.register_blueprint(RecordsBlueprint)
