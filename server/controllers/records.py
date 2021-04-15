from flask.views import MethodView
from flask import request, jsonify, make_response
from server.helpers.user import login_only

class UserRecordsView(MethodView):
  # Get all records of a user
  @login_only
  def get(self):
    response = {
      'success': True,
      'msg': 'User record',
    }
    return make_response(jsonify(response)), 200

class RecordView(MethodView):
  # Get a record by id
  def get(self, id):
    response = {
      'success': True,
      'msg': f'Get Record {id}',
    }
    return make_response(jsonify(response)), 200

  # Update a record by id
  def put(self, id):
    response = {
      'success': True,
      'msg': f'Update Record {id}',
    }
    return make_response(jsonify(response)), 200

  # Delete a record by id
  def delete(self, id):
    response = {
      'success': True,
      'msg': f'Delete Record {id}',
    }
    return make_response(jsonify(response)), 200

records_controller = {
  'user_records' : UserRecordsView.as_view('user_records'),
  'record': RecordView.as_view('record'),
}