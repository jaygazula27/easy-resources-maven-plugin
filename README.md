# easy-resources-maven-plugin


# Table of contents
* [Overview](#overview)
* [Requirements](#requirements)
* [Usage](#usage)
    * [properties-constants](#properties-constants)
* [Development](#development)
* [License](#license)


## Overview

This maven plugin enables easy and typesafe access to resources. This is achieved by generating code with the following features:
* Classes consisting of `static final` fields which point to the keys of properties files.
* Classes consisting of methods with the appropriate parameters for dynamic internationalization which allows for lookup of localized strings.
    * Feature implementation in progress.

For the gradle alternative of this plugin, take a look at [easy-resources-gradle-plugin](https://github.com/jaygazula27/easy-resources-gradle-plugin).


## Requirements

* Java 11 or higher
* Maven 3


## Usage

### properties-constants

The `properties-constants` goal generates classes with `static final` fields which point to the keys of properties files. 

* This is bound by default to the `generate-sources` phase.
* The configuration consists of a list of `propertiesFile`. The parameters are described below.

Parameters:

| Name | Description                   |
| ---- |-------------------------------|
| file | The properties file to parse. |
| generatedPackageName | The package of the generated class. |
| generatedClassName | The name of the generated class. |

Example:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>com.jgazula</groupId>
            <artifactId>easy-resources-maven-plugin</artifactId>
            <version>0.1.0</version>
            <executions>
                <execution>
                    <id>generate-properties-constants</id>
                    <goals>
                        <goal>properties-constants</goal>
                    </goals>
                    <configuration>
                        <propertiesFiles>
                            <propertiesFile>
                                <file>${project.basedir}/src/main/resources/database.properties</file>
                                <generatedPackageName>com.jgazula</generatedPackageName>
                                <generatedClassName>DatabaseProperties</generatedClassName>
                            </propertiesFile>
                        </propertiesFiles>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

If we have a `database.properties` file with the following contents...

```properties
spring.datasource.username=dbuser
spring.datasource.password=dbpass
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```

...this would be the generated class:

```java
package com.jgazula;

import java.lang.String;

public class DatabaseProperties {
    public static final String SPRING_DATASOURCE_USERNAME = "spring.datasource.username";
    public static final String SPRING_DATASOURCE_PASSWORD = "spring.datasource.password";
    public static final String SPRING_DATASOURCE_DRIVER_CLASS_NAME = "spring.datasource.driver-class-name";
}
```


## Development

* Requires Java 11 and Maven 3.
* The parsing and class generation logic is implemented in a dependency of this project: [easy-resources-core](https://github.com/jaygazula27/easy-resources-core)
* To build the project (and run unit and e2e tests): `mvn -U clean install`


## License

MIT License. Please see the [LICENSE](LICENSE) file for more information.