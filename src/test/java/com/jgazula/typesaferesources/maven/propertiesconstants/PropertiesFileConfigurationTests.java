package com.jgazula.typesaferesources.maven.propertiesconstants;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

import com.jgazula.typesaferesources.core.util.ValidationException;
import java.io.File;
import org.junit.jupiter.api.Test;

public class PropertiesFileConfigurationTests {

  private static final File TEST_FILE = new File("test");
  private static final String TEST_CLASS_NAME = "testClass";
  private static final String TEST_PACKAGE_NAME = "com.jgazula.testClass";

  @Test
  void fileCannotBeNull() {
    // given
    PropertiesFileConfiguration configuration = new PropertiesFileConfiguration();
    configuration.setGeneratedClassName(TEST_CLASS_NAME);
    configuration.setGeneratedPackageName(TEST_PACKAGE_NAME);

    // then
    assertThatExceptionOfType(ValidationException.class).isThrownBy(configuration::getFile);
  }

  @Test
  void generatedPackageNameCannotBeNull() {
    // given
    PropertiesFileConfiguration configuration = new PropertiesFileConfiguration();
    configuration.setFile(TEST_FILE);
    configuration.setGeneratedClassName(TEST_CLASS_NAME);

    // then
    assertThatExceptionOfType(ValidationException.class)
        .isThrownBy(configuration::getGeneratedPackageName);
  }

  @Test
  void generatedPackageNameCannotBeEmpty() {
    // given
    PropertiesFileConfiguration configuration = new PropertiesFileConfiguration();
    configuration.setFile(TEST_FILE);
    configuration.setGeneratedClassName(TEST_CLASS_NAME);
    configuration.setGeneratedPackageName("");

    // then
    assertThatExceptionOfType(ValidationException.class)
        .isThrownBy(configuration::getGeneratedPackageName);
  }

  @Test
  void generatedClassNameCannotBeNull() {
    // given
    PropertiesFileConfiguration configuration = new PropertiesFileConfiguration();
    configuration.setFile(TEST_FILE);
    configuration.setGeneratedPackageName(TEST_PACKAGE_NAME);

    // then
    assertThatExceptionOfType(ValidationException.class)
        .isThrownBy(configuration::getGeneratedClassName);
  }

  @Test
  void generatedClassNameCannotBeEmpty() {
    // given
    PropertiesFileConfiguration configuration = new PropertiesFileConfiguration();
    configuration.setFile(TEST_FILE);
    configuration.setGeneratedClassName("");
    configuration.setGeneratedPackageName(TEST_PACKAGE_NAME);

    // then
    assertThatExceptionOfType(ValidationException.class)
        .isThrownBy(configuration::getGeneratedClassName);
  }

  @Test
  void successfulValidation() {
    // given
    PropertiesFileConfiguration configuration = new PropertiesFileConfiguration();
    configuration.setFile(TEST_FILE);
    configuration.setGeneratedClassName(TEST_CLASS_NAME);
    configuration.setGeneratedPackageName(TEST_PACKAGE_NAME);

    // then
    assertThatNoException()
        .isThrownBy(
            () -> {
              configuration.getFile();
              configuration.getGeneratedPackageName();
              configuration.getGeneratedClassName();
            });
  }
}
