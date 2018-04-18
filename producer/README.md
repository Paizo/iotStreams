# Producer

Make use of @Scheduled tasks to simulate IOT streams of data:
every second sends 4 devices sensors reading randomly generated.

Each Type of device are sent as "raw data" to their specific kafka topics following the pattern TOPIC NAME = (deviceId)-raw

Generate fixed device ids for simplification:

 - smart-couch
 - smart-watch
 - car-fuel
 - refrigerator
 
Sensor ids random among:

 - TEMP,
 - HEART_BEAT,
 - PRESSURE,
 - SPEED

Sensor type random among:

 - TEMPERATURE,
 - HEART_BEAT,
 - PRESSURE,
 - SPEED