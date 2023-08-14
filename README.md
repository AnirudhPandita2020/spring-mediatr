# Spring MediatR Library

![](https://github.com/AnirudhPandita2020/spring-mediatr/actions/workflows/mediatr-min.yml/badge.svg)
![LINE](https://img.shields.io/badge/line--coverage-86.59%25-brightgreen.svg)
![BRANCH](https://img.shields.io/badge/branch--coverage-62.50%25-yellow.svg)
![COMPLEXITY](https://img.shields.io/badge/complexity-1.78-brightgreen.svg)

The Spring MediatR Library is a simple implementation of the [MediatR](https://github.com/jbogard/MediatR) pattern for
Spring applications, designed for versions above 2.7.14. It facilitates efficient communication between components in a
Spring application by providing a mediator pattern implementation.

## Features

- Easy communication between components using the mediator pattern.
- Support for sending commands and queries, and handling them using specialized handlers.

## Requirements

* Java 11+
* Spring Framework 5/ Spring Boot 2*

## Getting Started

### Gradle

    This is will be availiable once the artificat is published.

### Maven

    This is will be availiable once the artificat is published.

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



