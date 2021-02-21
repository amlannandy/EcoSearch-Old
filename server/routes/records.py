from flask import Blueprint, jsonify
from flask_jwt_extended import get_jwt_identity, jwt_required

from server.helpers.user import find_by_email

records = Blueprint('records', __name__, url_prefix='/records')

@records.route('/')
@jwt_required()
def get_records():
   
   email = get_jwt_identity()
   user = find_by_email(email)

   if not user:
      response = {
         'succes': False,
         'msg': 'User does not exist'
      }
      return jsonify(response), 404

      

   return f'Get user {id}', 200

@records.route('/<id>')
def get_record(id):
   pass