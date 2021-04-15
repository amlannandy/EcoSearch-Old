from os import name
from flask.views import MethodView
from flask import request, jsonify, make_response

from server.helpers.user import login_only
from server.helpers.record import save_record, upload_image, save_image_to_record

class UserRecordsView(MethodView):
  # Get all records of a user
  @login_only
  def get(self):
    response = {
      'success': True,
      'msg': 'User record',
    }
    return make_response(jsonify(response)), 200

  # Create a record
  @login_only
  def post(self):
    data = request.get_json()
    if not data:
      response = {
        'success': False,
        'msg': 'Please enter record data',
      }
      return make_response(jsonify(response)), 400

    try:
      title = str(data['title'])
      description = str(data['description'])
    except KeyError as err:
      response = {
        'success': False,
        'msg': f'Please provide a {str(err)}'
      }
      return make_response(jsonify(response)), 400

    email = request.args['user']['email']

    record = save_record(title, description, email)
    response = {
      'success': True,
      'data': record.to_json(),
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

class ImageUploadView(MethodView):
  # Upload a image to cloud storage
  @login_only
  def post(self, id):
    image = request.files.get('image')
    if not image:
      response = {
        'success': False,
        'msg': 'Please add an image',
      }
      return make_response(jsonify(response)), 400

    file_name, extension = image.filename.split('.')
    if extension not in ['jpeg', 'jpg', 'png']:
      response = {
        'success': False,
        'msg': 'Please add a valid file (jpeg, jpg or png)',
      }
      return make_response(jsonify(response)), 400

    try:
      url = upload_image(image, name=file_name)
    except Exception as e:
      response = {
        'success': False,
        'msg': str(e),
      }
      return make_response(jsonify(response)), 500

    record = save_image_to_record(id, url)

    response = {
      'success': True,
      'data': record.to_json(),
    }
    return make_response(jsonify(response)), 200

records_controller = {
  'user_records' : UserRecordsView.as_view('user_records'),
  'record': RecordView.as_view('record'),
  'upload_image': ImageUploadView.as_view('upload_image'),
}