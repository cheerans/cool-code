# calling a method on unix with wget, curl

wget -O- --post-data='{"param1": "value1","param2": "value2"}' \
  --header='Content-Type:application/json' \
  'http://localhost:port1/url' > resp.txt
 
  
curl -X POST http://localhost:port1/url -d '{"param1": "value1","param2": "value2"}'
