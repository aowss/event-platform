# Event Server

A simple mock websocket event server.

## Running

### Standalone Server

`java -classpath event-server-`version`.jar event.server.WebSocketServer`

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

`java -classpath event-server-`version`.jar event.client.EventClientEndpoint ` topicName

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

## Notes

Your `JAVA_HOME` environment variable should point to a Java 9 Runtime Environment.  
If not, please use the full path to a Java 9 JRE, e.g. `/Library/Java/JavaVirtualMachines/jdk-9.jdk/Contents/Home/bin/java` on Mac.

## Requirements

1. [JSR 356 : Java API for WebSocket](https://jcp.org/en/jsr/detail?id=356)
2. Java 9
