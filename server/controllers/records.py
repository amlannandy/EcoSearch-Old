from flask.views import MethodView
from flask import request, jsonify, make_response

from server.helpers.user import login_only
from server.helpers.record import (
  find_by_id,
  save_record, 
  upload_image,
  find_all_user_records,
  save_image_to_record,
  find_record_by_email_and_title,
  update_description,
  delete_by_id,
)

class UserRecordsView(MethodView):
  # Get all records of a user
  @login_only
  def get(self):
    user_email = request.args['user']['email']
    records_data = find_all_user_records(user_email)

    type = request.args.get('type')
    if type not in ['animal', 'bird', 'insect', 'plant']:
      response = {
        'success': False,
        'msg': 'Invalid record type',
      }
      return make_response(jsonify(response)), 400    

    if type:
      records_data = list(filter(lambda rec : rec.type == type, records_data))

    records = list(map(lambda rec : rec.to_json(), records_data))
    response = {
      'success': True,
      'data': records,
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
      type = str(data['type'])
    except KeyError as err:
      response = {
        'success': False,
        'msg': f'Please provide a {str(err)}'
      }
      return make_response(jsonify(response)), 400

    if type not in ['animal', 'bird', 'insect', 'plant']:
      response = {
        'success': False,
        'msg': 'Invalid record type',
      }
      return make_response(jsonify(response)), 400

    email = request.args['user']['email']

    record = find_record_by_email_and_title(email, title)
    if record:
      response = {
        'success': False,
        'msg': 'Record with this title already exists',
      }
      return make_response(jsonify(response)), 409

    record = save_record(title, description, email, type)
    response = {
      'success': True,
      'data': record.to_json(),
    }

    return make_response(jsonify(response)), 200

class RecordView(MethodView):
  # Get a record by id
  @login_only
  def get(self, id):
    record = find_by_id(id)
    if not record:
      response = {
        'success': False,
        'msg': 'Record does not exist',
      }
      return make_response(jsonify(response)), 404

    user_email = request.args['user']['email']
    if record.user_email != user_email:
      response = {
        'success': False,
        'msg': 'Not authorized to view this record'
      }
      return make_response(jsonify(response)), 401

    response = {
      'success': True,
      'data': record.to_json(),
    }
    return make_response(jsonify(response)), 200

  # Update a record by id
  @login_only
  def put(self, id):
    record = find_by_id(id)
    if not record:
      response = {
        'success': False,
        'msg': 'Record does not exist',
      }
      return make_response(jsonify(response)), 404

    user_email = request.args['user']['email']
    if record.user_email != user_email:
      response = {
        'success': False,
        'msg': 'Not authorized to update this record'
      }
      return make_response(jsonify(response)), 401

    try:
      data = request.get_json()
      description = data['description']
      pass
    except:
      response = {
        'success': False,
        'msg': 'Please provide a description'
      }
      return make_response(jsonify(response)), 400

    record = update_description(record.id, description)

    response = {
      'success': True,
      'data': record.to_json(),
    }
    return make_response(jsonify(response)), 200

  # Delete a record by id
  @login_only
  def delete(self, id):
    record = find_by_id(id)
    if not record:
      response = {
        'success': False,
        'msg': 'Record does not exist',
      }
      return make_response(jsonify(response)), 404

    user_email = request.args['user']['email']
    if record.user_email != user_email:
      response = {
        'success': False,
        'msg': 'Not authorized to update this record'
      }
      return make_response(jsonify(response)), 401

    delete_by_id(record.id)
    response = {
      'success': True,
      'msg': 'Record deleted successfully',
    }
    return make_response(jsonify(response)), 200

class ImageUploadView(MethodView):
  # Upload a image to cloud storage
  @login_only
  def post(self, id):
    record = find_by_id(id)
    if not record:
      response = {
        'success': False,
        'msg': 'Record does not exist',
      }
      return make_response(jsonify(response)), 404

    if record.image:
      response = {
        'success': False,
        'msg': 'Record already has an image',
      }
      return make_response(jsonify(response)), 409

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