from flask.views import MethodView
from flask import request, jsonify, make_response

from server.helpers.user import login_only

class AnimalClassificationView(MethodView):
  # Run the animal classification model on a record
  # api/v1/classification/animal/<id>
  @login_only
  def get(self, id):
    response = {
      'success': True,
      'msg': 'Animal Classification Route',
    }
    return make_response(jsonify(response)), 200

class BirdClassificationView(MethodView):
  # Run the bird classification model on a record
  # api/v1/classification/bird/<id>
  @login_only
  def get(self, id):
    response = {
      'success': True,
      'msg': 'Bird Classification Route',
    }
    return make_response(jsonify(response)), 200

class InsectClassification(MethodView):
  # Run the insect classification model on a record
  # api/v1/classification/insect/<id>
  @login_only
  def get(self, id):
    response = {
      'success': True,
      'msg': 'Insect Classification Route',
    }
    return make_response(jsonify(response)), 200

class PlantClassification(MethodView):
  # Run the animal classification model on a record
  # api/v1/classification/plant/<id>
  @login_only
  def get(self, id):
    response = {
      'success': True,
      'msg': 'Plant Classification Route',
    }
    return make_response(jsonify(response)), 200

classification_controller = {
  'animal_classification': AnimalClassificationView.as_view('animal_classification'),
  'bird_classification': BirdClassificationView.as_view('bird_classification'),
  'insect_classification': InsectClassification.as_view('insect_classification'),
  'plant_classification': PlantClassification.as_view('plant_classification'),
}