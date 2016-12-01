# Event Server

A simple mock websocket event server.

## Running

### Standalone Server

`java -jar event-server.jar`

### Clients

#### Events

The base URL for all events is : `ws://localhost:8025/websockets/events/`

##### Topics

Here are all the airport topics :

. `airports:UK:London:LHR`  
. `airports:UK:London:GTW`  
. `airports:UK:London:LTN`  
. `airports:France:Peris:CDG`  
. `airports:UK:London`  
. `airports`

Here are all the flight topics :

. `flights:BA:123:1:2016-11-15`  
. `flights:BA:123:2:2016-11-15`  
. `flights:BA:123:1:2016-11-16`  
. `flights:BA:1:1:2016-11-15`  
. `flights`

##### Java Client

`java -classpath event-server.jar event.client.EventClientEndpoint ` topicName

The command line parameter is the topic name

#### Messages

##### Simple Event

````
{
       	"id": "0",
       	"timestamp": "2016-11-16T15:45:54.497529-05:00[America/Toronto]",
       	"type": "Departure",
       	"context": "airports:UK:London:LHR",
       	"content": "Departure message for airports:UK:London:LHR"
}
````

##### Location Event

````
{
       	"id": "0",
       	"timestamp": "2016-11-16T15:45:54.497529-05:00[America/Toronto]",
       	"type": "Departure",
       	"context": "airports:UK:London:LHR",
       	"content": "Departure message for airports:UK:London:LHR",
       	"location": {
       	    "latitude": 12.234,
       	    "longitude": 123.9876,
       	    "altitude": 3456
       	}
}
````

### Notes

Your `JAVA_HOME` environment variable should point to a Java 9 Runtime Environment.  
If not, please use the full path to a Java 9 JRE, e.g. `/Library/Java/JavaVirtualMachines/jdk-9.jdk/Contents/Home/bin/java` on Mac.


## Building

If your `JAVA_HOME` environment variable points to a Java 9 Development Kit :

`mvn package`

If not, point it first to a Java 9 SDK :

`JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-9.jdk/Contents/Home`  
`mvn package`

### Standalone Server

The maven profile to build the standalone server is `cli`.  
The command should therefore be : `mvn -P cli package`.

## Docker

### Build the image

From the directory that contains the `Dockerfile`, run the following command :
`docker build -t event-server .`

### Create a container

`docker run --name eventWS -it -p 8025:8025 event-server`

### Access the container
 
Unless you are still running Boot2Docker, use `localhost` to access the server.  
There is therefore no difference between accessing the server when it is run locally or when it is run as a Docker container.

## Requirements

Development

1. [JSR 356 : Java API for WebSocket](https://jcp.org/en/jsr/detail?id=356)
2. [Maven](http://maven.apache.org)

Runtime

1. [Java 9](https://jdk9.java.net/download/)

