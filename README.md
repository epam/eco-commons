# Eco Commons

It's a library of general-purpose utilities, helpers and other classes that are shared across ECO projects.

The library can be obtained from the Maven by adding the following dependency in the pom.xml:

```
<dependency>
    <groupId>com.epam.eco</groupId>
    <artifactId>commons</artifactId>
    <version>${project.version}</version>
</dependency>
```

## Features

* Compute the difference between the original and revised texts using [java-diff-utils](http://code.google.com/p/java-diff-utils/source/browse/)
* Parse java Object to certain java primitive wrapper or Date
* Simple helper to work with time series

## Build

```
git clone git@github.com:epam/eco-commons.git
cd eco-commons
mvn clean package
```

## License

Licensed under the [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0)