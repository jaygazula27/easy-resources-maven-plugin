# easy-resources-maven-plugin

[![GitHub Workflow Status](https://img.shields.io/github/actions/workflow/status/jaygazula27/easy-resources-maven-plugin/maven-build.yml)](https://github.com/jaygazula27/easy-resources-maven-plugin/actions/workflows/maven-build.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.jgazula/easy-resources-maven-plugin)](https://search.maven.org/artifact/com.jgazula/easy-resources-maven-plugin)
[![GitHub](https://img.shields.io/github/license/jaygazula27/easy-resources-maven-plugin)](LICENSE)


# Table of contents
* [Overview](#overview)
* [Requirements](#requirements)
* [Usage](#usage)
    * [properties-constants](#properties-constants)
    * [enhance-resource-bundle](#enhance-resource-bundle)
* [Development](#development)
* [License](#license)


## Overview

This maven plugin enables easy and typesafe access to resources. This is achieved by generating code with the following features:
* Classes consisting of `static final` fields which point to the keys of properties files.
* Classes consisting of methods with the appropriate parameters for dynamic internationalization which allows for lookup of localized strings.

Release notes are available in the [CHANGELOG](CHANGELOG.md) file.

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

| Name                 | Description                                        |
|----------------------|----------------------------------------------------|
| file                 | The properties file to parse.                      |
| generatedPackageName | The java package the generated class should be in. |
| generatedClassName   | The name to use for the generated class.           |

Example:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>com.jgazula</groupId>
            <artifactId>easy-resources-maven-plugin</artifactId>
            <version>0.2.0</version>
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

### enhance-resource-bundle

The `enhance-resource-bundle` goal generates classes with methods that correspond to the keys of the properties in a resource bundle.

Parameters:

| Name                 | Description                                                                |
|----------------------|----------------------------------------------------------------------------|
| bundlePath           | The directory in which the resource bundle's properties files are located. |
| bundleName           | The name of the resource bundle.                                           |
| generatedPackageName | The java package the generated class should be in.                         |
| generatedClassName   | The name to use for the generated class.                                   |

Example:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>com.jgazula</groupId>
            <artifactId>easy-resources-maven-plugin</artifactId>
            <version>0.2.0</version>
            <executions>
                <execution>
                    <id>generate-enhanced-resource-bundle</id>
                    <goals>
                        <goal>enhance-resource-bundle</goal>
                    </goals>
                    <configuration>
                        <resourceBundles>
                            <resourceBundle>
                                <bundlePath>${project.basedir}/src/main/resources/</bundlePath>
                                <bundleName>AppBundle</bundleName>
                                <generatedPackageName>com.jgazula</generatedPackageName>
                                <generatedClassName>AppResourceBundle</generatedClassName>
                            </resourceBundle>
                        </resourceBundles>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

If we have a `AppBundle.properties` file with the following contents...

```properties
button.continue=Continue
class.final.grade=His final grade for {0} class was {1,number,percent}.
planet.quantity=There are {0,number,integer} planets.
monthly.payment=The monthly {1} bill is typically over {0,number,currency}.
```

as well as a `AppBundle_fr_FR.properties` file with the following contents...

```properties
date.of.birth=The applicant''s DOB is {0,date}.
store.opening.time=The {0} store opens every day at {1,time}.
files.quantity=There {0,choice,0#are no files|1#is one file|1<are {0,number,integer} files}.
```

...this would be the generated class:

```java
package com.jgazula;

import java.lang.Object;
import java.lang.String;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class AppResourceBundle {
    private final ResourceBundle resourceBundle;

    public AppResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public String buttonContinue() {
      String message = this.resourceBundle.getString("button.continue");
      return message;
    }

    public String classFinalGrade(String arg0, BigDecimal arg1) {
        String message = this.resourceBundle.getString("class.final.grade");
        Object[] messageArguments = {arg0, arg1};
        return new MessageFormat(message, this.resourceBundle.getLocale()).format(messageArguments);
    }

    public String planetQuantity(int arg0) {
      String message = this.resourceBundle.getString("planet.quantity");
      Object[] messageArguments = {arg0};
      return new MessageFormat(message, this.resourceBundle.getLocale()).format(messageArguments);
    }

    public String monthlyPayment(BigDecimal arg0, String arg1) {
      String message = this.resourceBundle.getString("monthly.payment");
      Object[] messageArguments = {arg0, arg1};
      return new MessageFormat(message, this.resourceBundle.getLocale()).format(messageArguments);
    }

    public String dateOfBirth(Date arg0) {
        String message = this.resourceBundle.getString("date.of.birth");
        Object[] messageArguments = {arg0};
        return new MessageFormat(message, this.resourceBundle.getLocale()).format(messageArguments);
    }

    public String storeOpeningTime(String arg0, Date arg1) {
      String message = this.resourceBundle.getString("store.opening.time");
      Object[] messageArguments = {arg0, arg1};
      return new MessageFormat(message, this.resourceBundle.getLocale()).format(messageArguments);
    }

    public String filesQuantity(int arg0) {
        String message = this.resourceBundle.getString("files.quantity");
        Object[] messageArguments = {arg0};
        return new MessageFormat(message, this.resourceBundle.getLocale()).format(messageArguments);
    }
}
```

As seen above in the generated class, the methods correspond to property keys of the resource bundle.
Additionally, the parameters of these methods allow for formatting messages in a typesafe manner.

In order to use the generated class, the developer simply needs to construct an instance by passing in the appropriate resource bundle.
Since the instance of this generated class is thread safe, it can then be exposed as a bean and injected if using a framework such Spring, Quarkus, etc.


## Development

* Requires Java 11 and Maven 3.
* The parsing and class generation logic is implemented in a dependency of this project: [easy-resources-core](https://github.com/jaygazula27/easy-resources-core)
* To build the project (and run unit and e2e tests): `mvn -U clean install`
* Use the [Publish to Maven Central](https://github.com/jaygazula27/easy-resources-maven-plugin/actions/workflows/maven-publish.yml) workflow to deploy to maven central.
  * Needs the following secrets: `OSSRH_USERNAME`, `OSSRH_PASSWORD`, `MAVEN_GPG_PASSPHRASE`, and `MAVEN_GPG_PRIVATE_KEY`


## License

MIT License. Please see the [LICENSE](LICENSE) file for more information.