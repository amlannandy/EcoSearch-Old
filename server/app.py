from flask import Flask

from routes.auth import auth as AuthBlueprint

app = Flask(__name__)

app.register_blueprint(AuthBlueprint)