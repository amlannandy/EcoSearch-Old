import os
from flask_mail import Message
from flask import render_template

from server.app import mail

def send_welcome_email(name, email, token):
  msg = Message('EcoSearch - Reset Password', sender=os.environ.get('MAIL_DEFAULT_SENDER'), recipients=[email])
  base_url = os.getenv('API_URL')
  link = f'{base_url}/api/v1/auth/reset-password/{token}'
  msg.html = render_template('reset_password_email.html', name=name, link=link)
  try:
    mail.send(msg)
  except ConnectionRefusedError as err:
    print(err)
    raise ConnectionRefusedError