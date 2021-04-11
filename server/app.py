import os
from flask import Flask
from dotenv import load_dotenv
from flask_sqlalchemy import SQLAlchemy
from flask_jwt_extended import JWTManager

db = SQLAlchemy()

# Init flask app
app = Flask(__name__)

# Load environment variables
load_dotenv()

from server.routes.auth import auth as AuthBlueprint
from server.routes.records import records as RecordsBlueprint

# Setup jwt
app.config['JWT_SECRET_KEY'] = os.getenv('JWT_SECRET_KEY')
jwt = JWTManager(app)

# Setup and init db
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///db.sqlite3'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = True
db.init_app(app)

app.register_blueprint(AuthBlueprint)
app.register_blueprint(RecordsBlueprint)