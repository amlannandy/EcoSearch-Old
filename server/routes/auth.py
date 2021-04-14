from flask import Blueprint

from server.controllers.auth import auth_controller

auth = Blueprint('auth', __name__, url_prefix='/api/v1/auth')

auth.add_url_rule('/login', view_func=auth_controller['login'], methods=['POST'])

auth.add_url_rule('/register', view_func=auth_controller['register'], methods=['POST'])

auth.add_url_rule('/get-current-user', view_func=auth_controller['get_current_user'], methods=['GET'])