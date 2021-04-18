import os
import requests

def get_address_from_location(lat, long):
  API_KEY = os.getenv('GEOCODER_API_KEY')
  URL = f'https://api.opencagedata.com/geocode/v1/json?q={lat}+{long}&key={API_KEY}'
  res = requests.get(URL)
  data = res.json()
  locations = data['results'][0]['components']
  print(locations)
  address = f'{locations["county"]}, {locations["state_district"]}, {locations["state"]}, {locations["country"]}'
  return address