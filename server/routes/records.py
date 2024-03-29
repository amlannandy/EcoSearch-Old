from flask import Blueprint

from server.controllers.records import records_controller

records = Blueprint('records', __name__, url_prefix='/api/v1/records')

records.add_url_rule('/', 
   view_func=records_controller['user_records'], 
   methods=['GET', 'POST'],
)

records.add_url_rule('/<int:id>', 
   view_func=records_controller['record'], 
   methods=['GET', 'PUT', 'DELETE'],
)

records.add_url_rule('/upload-image/<int:id>',
   view_func=records_controller['upload_image'],
   methods=['POST'],
)

records.add_url_rule('/explore',
   view_func=records_controller['explore'],
   methods=['GET'],
)