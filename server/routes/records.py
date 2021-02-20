from flask import Blueprint

records = Blueprint('records', __name__, url_prefix='/records')

@records.route('/<id>')
def get_user_records(id):
   return f'Get user {id}', 200