from flask import Blueprint

from server.controllers.classification import classification_controller

classification = Blueprint('classification', __name__, url_prefix='/api/v1/classification')

classification.add_url_rule('/animal/<int:id>',
  view_func=classification_controller['animal_classification'],
  methods=['GET'],
)

classification.add_url_rule('/bird/<int:id>',
  view_func=classification_controller['bird_classification'],
  methods=['GET'],
)

classification.add_url_rule('/insect/<int:id>',
  view_func=classification_controller['insect_classification'],
  methods=['GET'],
)

classification.add_url_rule('/plant/<int:id>',
  view_func=classification_controller['plant_classification'],
  methods=['GET'],
)