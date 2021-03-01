import os
from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from flask_jwt_extended import JWTManager
from flask_selfdoc import Autodoc

db = SQLAlchemy()

# Set up image directory
basedir = os.path.abspath(os.path.dirname(__file__))
imagesdir = os.path.join(os.path.dirname(basedir),'uploads')

# Init flask app
app = Flask(__name__, static_folder=imagesdir)
auto = Autodoc(app)

from server.routes.auth import auth as AuthBlueprint
from server.routes.records import records as RecordsBlueprint

# Setup jwt
app.config["JWT_SECRET_KEY"] = "thisisasecret"
jwt = JWTManager(app)

# Setup and init db
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///db.sqlite3'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = True
db.init_app(app)

app.register_blueprint(AuthBlueprint)
app.register_blueprint(RecordsBlueprint)

@app.route('/')
def documentation():
  return auto.html(groups=['auth', 'records'], title='FloraCheck Documentation', template='doc_template.html')