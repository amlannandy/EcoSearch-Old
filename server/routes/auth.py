from flask import Blueprint

auth = Blueprint('auth', __name__, url_prefix='/auth')

@auth.route('/login', methods=['POST'])
def login():
   return 'Login Route', 200

@auth.route('/register', methods=['POST'])
def register():
   return 'Register Route', 200

@auth.route('/logout', methods=['GET'])
def logout():
   return 'Logout Route', 200

@auth.route('/current-user', methods=['GET'])
def get_current_user():
   return 'Get Current User Route', 200