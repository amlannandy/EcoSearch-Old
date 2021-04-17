from cloudinary.uploader import upload

from server.app import db
from server.models.Record import Record

def find_by_id(id):
  return Record.query.filter_by(id=id).first()

def find_all_user_records(email):
  return Record.query.filter_by(user_email=email)

def save_record(title, description, email, type):
  record = Record(title=title, description=description, user_email=email, type=type)
  db.session.add(record)
  db.session.commit()
  record = Record.query.filter_by(title=title).first()
  return record

def upload_image(url, name='test'): 
    response = upload(url, public_id=name)
    url = response['url']
    return url

def save_image_to_record(id, url):
  record = Record.query.filter_by(id=id).first()
  record.image = url
  db.session.commit()
  return record

def find_record_by_email_and_title(email, title):
  return Record.query.filter_by(user_email=email, title=title).first()

def update_description(id, description):
  record = Record.query.filter_by(id=id).first()
  record.description = description
  db.session.commit()
  return record

def delete_by_id(id):
  Record.query.filter_by(id=id).delete()
  db.session.commit()