from flask import Blueprint

from server.controllers.auth import auth_controller

auth = Blueprint('auth', __name__, url_prefix='/api/v1/auth')

auth.add_url_rule('/login', view_func=auth_controller['login'], methods=['POST'])

auth.add_url_rule('/register', view_func=auth_controller['register'], methods=['POST'])

auth.add_url_rule('/get-current-user',
  view_func=auth_controller['get_current_user'],
  methods=['GET'],
)

auth.add_url_rule('/update-info', 
  view_func=auth_controller['update_info'], 
  methods=['PUT'],
)

auth.add_url_rule('/update-password', 
  view_func=auth_controller['update_password'], 
  methods=['PUT'],
)

auth.add_url_rule('/delete-account', 
  view_func=auth_controller['delete_account'], 
  methods=['PUT'],
)

auth.add_url_rule('/forgot-password', 
  view_func=auth_controller['forgot_password'], 
  methods=['POST'],
)

auth.add_url_rule('/reset-password/<token>', 
  view_func=auth_controller['reset_password'], 
  methods=['POST', 'GET'],
)