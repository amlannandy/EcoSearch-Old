from flask import Flask

from routes.auth import auth as AuthBlueprint
from routes.records import records as RecordsBlueprint

app = Flask(__name__)

app.register_blueprint(AuthBlueprint)
app.register_blueprint(RecordsBlueprint)
