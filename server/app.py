import os
import cloudinary
from flask import Flask
from flask_mail import Mail
from dotenv import load_dotenv
from flask_sqlalchemy import SQLAlchemy
from flask_jwt_extended import JWTManager

db = SQLAlchemy()

# Init flask app
app = Flask(__name__)
app.secret_key = os.urandom(24)

# Load environment variables
load_dotenv()

# Set up email service
app.config.update(dict(
  DEBUG = True,
  MAIL_SERVER = os.getenv('MAIL_SERVER'),
  MAIL_PORT = os.getenv('MAIL_PORT'),
  MAIL_USE_TLS = True,
  MAIL_USE_SSL = False,
  MAIL_USERNAME = os.getenv('MAIL_USERNAME'),
  MAIL_PASSWORD = os.getenv('MAIL_PASSWORD'),
  MAIL_DEFAULT_SENDER = os.environ.get('MAIL_DEFAULT_SENDER'),
))
mail = Mail(app)

from server.routes.auth import auth as AuthBlueprint
from server.routes.records import records as RecordsBlueprint

# Setup jwt
app.config['JWT_SECRET_KEY'] = os.getenv('JWT_SECRET_KEY')
app.config['JWT_ACCESS_TOKEN_EXPIRES'] = int(os.getenv('JWT_ACCESS_TOKEN_EXPIRES'))
jwt = JWTManager(app)

# Setup and init db
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///db.sqlite3'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = True
db.init_app(app)

# Setup cloudinary service
cloudinary.config(
  cloud_name = os.getenv('CLOUDINARY_CLOUD_NAME'),
  api_key = os.getenv('CLOUDINARY_API_KEY'),
  api_secret = os.getenv('CLOUDINARY_API_SECRET'),
)

app.register_blueprint(AuthBlueprint)
app.register_blueprint(RecordsBlueprint)