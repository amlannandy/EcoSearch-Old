from wtforms import Form, TextField, validators

class ResetPasswordForm(Form):
  password1 = TextField('Password1', validators=[validators.required()])
  password2 = TextField('Password2', validators=[validators.required(), validators.Length(min=6, max=35)])