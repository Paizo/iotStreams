# iotStreams
Proof of concept java application to handle streams of data coming from IOT devices


## Scenario

Several IOT devices send sensors readings, types of sensors may vary.

## Proposed solution

The large amount of data coming from the sensors can be streamed using Apache Kafka.
Kafka provides different types of connectors and sinks to integrate its streams capabilities to almost everything such as DBs and applications.

### The big picture

![IOT data streams architecture](IOTDataStreams.png?raw=true "IOT data streams architecture")

Data may comes from different sources: directly from the device/sensor and through an IOT gateways;
Considering this data may differ from device to device, sensors and vendors it may be not possible to abstract a specific model for it.
In order to quickly store this data without possible application error we stream it as is to MongoDB.
Once stored in Mongodb we are free to stream the data to other processing frameworks such as Apache Spark or Hadoop to transform it, enrich it and create meaningful results and reports.
This network of Frameworks and applications can communicate to each other still using the fast streams capabilities of Kafka.

Why MongoDB?

Most of the devices sends the data in json format that may or not adhere to a schema such as AVRO or standard such as O-DF;
mongo without the classic restriction of standards relational database store data natively in a binary json format.
The possibility to save json as is and make operations on it makes mongo a good fit in this case (no transactions!).
It is now mature enough, can scale up via sharding and replicas, plus it provides an aggregation framework to make operations on the data. 

Why Kafka?

Kafka is one of the fastest streaming technologies available, it is mature and several connectors are available either officially or community supported.
Can scale easily by adding additional brokers and the meta-data + data are kept consistent thanks to the use of Zookeeper.
It can even be an option to store the raw data directly in kafka, it has been designed to do this (persisted, checksum-ed and replicated).


Spark and Hadoop

Spark ability to store data in memory and work in cluster may be used for some work loads previously handled by Hadoop.
Spark is more suitable for real-time data analytics while Hadoop for non real time analysis.
They both can scale easily for distributed computing, storage and data safety.

### Simplified flow

In this project things as been simplified to make a little proof of concept with Kakfa/Spring/MongoDB

![IOT data streams simplified architecture](IOTDataStreamsSimplified.png?raw=true "IOT data streams simplified architecture")

The simulation of the IOT data is done by the *producer* application, every seconds it sends the data to raw topics in Kafka per device type.

*Kafka2mongoBridge* act as a connector for storing the raw data in the kafka topics to mongo.
As soon as a record is available in a topic it is consumed and stored as a document in mongo.
It is has been used in place of a Kafka connector or Mongo Sink to simplify this example without having to deal with format conversion and tricky issues.

The *MonitorAPI* application provide a REST API on top of the stored data in mongo to read sensors reading:
avg, min, max and median per device and sensor or device and group of sensors.


## Requirements

 - Java8
 - Maven
 - Docker Compose
 - MongoDB 3.6
 - Kafka 1.1.0
 - Zookeeper 3.4.10
 
### dev and deployment

Either:
 
Manually install a the required MongodDB, Kafka and zookeeper locally and manually run in this order:
 
 - MongoDB
 - Zookeeper
 - Kafka
 - Producer application
 - Bridge application
 - Monitor application
 
Or Build the applications and  make use of the provide *docker-compose.yml* via the commands:
 
 /producer mvn clean install
 /kafka2mongobridge mvn clean install
 /MonitorAPI mvn clean install
 
 *docker-compose --build up -d*
 
 The only change required in the *docker-compose.yml* file is the docker host ip (currently windows default 10.0.75.1);
 depending on your os and installation it might differ,
 if you have the default docker machine installed you can retrieve the address via the command: *docker-machine ip default*
 This IP is needed in this example to let zookeeper and kafka properly communicate and advertise their address.
 
 After some seconds everything should be up and the monitor API can be reached via the *docker machine ip:8080*
 or localhost:8080 if run manually.
 
 The API is documented and reachable also using Swagger at:
 
  - manual run *http://localhost:8080/swagger-ui.html#*
  - docker-compose windows *http://10.0.75.1:8080/swagger-ui.html#*
  
Simple authentication is enabled:
 - Username: user
 - Password: password

Sample data are generated with fixed deviceId for simplification:

 - smart-couch
 - smart-watch
 - car-fuel
 - refrigerator
 
Sensor ids are random picked among:

 - TEMP,
 - HEART_BEAT,
 - PRESSURE,
 - SPEED

### Sample IOT reading document

```
{
	"_id": ObjectId("5adb611a41220029a0547115"),
	"deviceId": "refrigerator",
	"vendor": "HOwJTrfj",
	"time": ISODate("2018-04-21T15:57:08.720Z"),
	"sensorId": "TEMP",
	"sensorValue": 6,
	"_class": "com.github.paizo.kafka2mongo.model.Refrigerator"
}
```

### Swagger page

![Swagger page](swagger.PNG?raw=true "Swagger page")

### Gotchas

 - Cannot find QueryDSL documentation for mongodb's aggregations
 - Type mapping of Spring Data and MongoDB is not perfect and may need the introduction of custom converters
 - Kafka-Mongo Sink connectors are not easy to use
 - AVRO support needs work!
 - Converting a MongoDB aggregation query to Spring data sometimes is tricky
 - Lack in docker for proper services status check requires manual bash magic