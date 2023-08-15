# Spring MediatR Library

![](https://github.com/AnirudhPandita2020/spring-mediatr/actions/workflows/mediatr-min.yml/badge.svg)
![LINE](https://img.shields.io/badge/line--coverage-92.11%25-brightgreen.svg)
![BRANCH](https://img.shields.io/badge/branch--coverage-68.75%25-yellow.svg)
![COMPLEXITY](https://img.shields.io/badge/complexity-1.62-brightgreen.svg)

The Spring MediatR Library is a simple implementation of the [MediatR](https://github.com/jbogard/MediatR) pattern for
Spring applications, designed for versions above 2.7.14. It facilitates efficient communication between components in a
Spring application by providing a mediator pattern implementation.

## Features

- Easy communication between components using the mediator pattern.
- Support for sending commands and queries, and handling them using specialized handlers.

## Requirements

* Java 11+
* Spring Framework 5/ Spring Boot 2*

## Note

*Presently, all the methods defined in the `Mediator`, `CommandHandler`, `QueryHandler`, and `NotificationHandler`
interfaces operate synchronously. However, it is important to note that asynchronous methods will be introduced in the
near future.*

## Getting Started

### Gradle

```gradle
dependencies{
    implementation "com.anirudh:spring-mediatr:1.0.0"
}
```

### Maven

```maven
<dependency>
  <groupId>com.anirudh</groupId>
  <artifactId>spring-mediatr</artifactId>
  <version>1.0.0</version>
</dependency>
```

### Jar Setup

Clone the repository and run ```gradle jar``` to create the jar file. Once the jar file is ready, move the jar to a
separate location and add it to the build.gradle file using the following command.

```gradle
dependencies{
    implementation files("your-path./spring-mediatR-0.0.1-SNAPSHOT-plain.jar")
}
```

## Setup

### 1. Enabling MediatR

To get started, simply add the ```@EnableMediatR``` annotation to the main class annotated
with ```@SpringBootApplication```. This annotation will automatically configure the necessary MediatR settings, reducing
the need for manual injection.

```java
package com.example;

import com.anirudh.springmediatr.core.annotation.EnableMediatr;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMediatr
public class SpringMediatRApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringMediatRApplication.class, args);
    }
}
```

### 2. Create your Request/Query

* MediatR operates based on requests and queries, which are then directed to their respective handlers. To distinguish
  between these two types, we have two marker interfaces: ```Query``` and ```Command```.

* The ```Query<Response>``` marker interface designates a class as a query, with an expected result provided through
  generics.
  Conversely, the ```Command``` marker interface signifies a class as a command, indicating that it won't return any
  data after
  being handled.

* For instance, consider a basic Query request. To begin, create a class named SimpleQuery.

```java
package com.example;

import com.anirudh.springmediatr.core.mediatr.Query;
import lombok.Data;

@Data //Using lombok for reducing boilerplate code
public class SimpleQuery implements Query<String> {
    private String name;
}
```

### 3. Define your handlers

* After defining the query, the next step is to create a handler for it. To accomplish this, we will employ the
  ```QueryHandler``` interface.

```java
package com.example;

import com.anirudh.springmediatr.core.mediatr.QueryHandler;
import com.example.SimpleQuery;
import org.springframework.stereotype.Component;

@Component
public class SimpleQueryHandler implements QueryHandler<SimpleQuery, String> {

    @Override
    public String handleQuery(SimpleQuery query) {
        return "Hello" + query.getName();
    }
}
```

### 4. Using MediatR

* Excellent! Now we are fully prepared to begin using the ```Mediator``` interface to dispatch the ```SimpleQuery``` to
  its
  corresponding handler. Let's proceed by crafting a controller class for this purpose. We'll name it
  ```SimpleQueryController```.

```java
package com.example.controller;

import com.anirudh.springmediatr.core.mediatr.Mediator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SimpleQueryController {

    private final Mediator mediator; // Inject mediator instance 

    @GetMapping
    public String nameQuery() {
        var simpleQuery = new SimpleQuery();
        nameQuery.setName("Anirudh");
        return mediator.send(nameQuery); ///Dispatching of the query. MediatR will automatically locate the handler and return's after execution.
    }
}

```

* Upon calling the GET endpoint, you will receive the response: ```"Hello Anirudh"```.
* The same process can be applied in case of a ```Command```. For every command there is a ```CommandHandler```.

## Events and Notification Handler

Just like query and command, We can also emit ```Event``` which can then be handled by a ```NotificationHandler```.
Some of the use-cases can be:

* Send notification's in various forms such as email, push-notification, messaging queue and soon
* Trigger some background work.(Synchronously for now!!)

Let's look into an example. We will be using the same ```SimpleQueryHandler``` to emit some events.

### 1. Event Creation

* Let's create an Event class called ```SimpleEvent```. This will implement the ```Event``` interface.

```java
package com.example.event;

import com.anirudh.springmediatr.core.notification.Event;
import lombok.Data;

@Data
public class SimpleEvent implements Event {
    private String message;
}
```

### 2. Notification Handler

* Once we are done creating the event, we can proceed with the handler. To create a handler, we will be using
  ```NotificationHandler```. Let's create one called ```SimpleNotificationHandler```

*In contrast to the limitations placed on the count of `CommandHandlers` and `QueryHandlers`, there exist no restrictions on
the number of NotificationHandlers that can be employed*

```java
package com.example.notification;

import com.anirudh.springmediatr.core.notification.NotificationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SimpleNotificationHandler implements NotificationHandler<SimpleNotification> {
  private static final Logger log = LoggerFactory.getLogger(Simple2NotificationHandler.class);
  @Override
  public void handle(SimpleNotification event) {
    log.info("Simple notification called: {}",event.getMessage()); //Simple log message. The actual implementation may vary depending on the use-case
  }
}
```

### 3. Invoke the handler
* To publish an event, we will be injecting the mediator instance to our ```SimpleQueryHandler``` which we created before.
```java
package com.example;

import com.anirudh.springmediatr.core.mediatr.QueryHandler;
import com.example.SimpleQuery;
import org.springframework.stereotype.Component;

@Component
public class SimpleQueryHandler implements QueryHandler<SimpleQuery, String> {
    
    private final Mediator mediator;
    
    public SimpleQueryHandler(Mediator mediator){
        this.mediator = mediator; // Injecting the mediator instance
    }

    @Override
    public String handleQuery(SimpleQuery query) {
        String response =  "Hello" + query.getName();
        SimpleEvent event = new SimpleEvent(); //Create the event object
        event.setMessage("Sending event");
        mediator.publish(event); //Invoke the handler by calling publish.
        return response;
    }
}
```

*The invocation sequence of handlers lacks a predefined order, leading to randomness that could potentially be perplexing. A solution to this predicament involves establishing a specific order in which the handlers will be executed.*
