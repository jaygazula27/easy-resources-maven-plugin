package com.jgazula.typesaferesources.maven.propertiesconstants;

import com.jgazula.typesaferesources.core.util.ValidationException;
import java.io.File;

public class PropertiesFileConfiguration {

  @SuppressWarnings("NullAway.Init")
  private File file;

  @SuppressWarnings("NullAway.Init")
  private String generatedPackageName;

  @SuppressWarnings("NullAway.Init")
  private String generatedClassName;

  public File getFile() {
    if (file == null) {
      throw new ValidationException("<file> configuration cannot be empty.");
    }

    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }

  public String getGeneratedPackageName() {
    if (generatedPackageName == null || generatedPackageName.isEmpty()) {
      throw new ValidationException("<generatedClassName> configuration cannot be empty.");
    }

    return generatedPackageName;
  }

  public void setGeneratedPackageName(String generatedPackageName) {
    this.generatedPackageName = generatedPackageName;
  }

  public String getGeneratedClassName() {
    if (generatedClassName == null || generatedClassName.isEmpty()) {
      throw new ValidationException("<generatedClassName> configuration cannot be empty.");
    }

    return generatedClassName;
  }

  public void setGeneratedClassName(String generatedClassName) {
    this.generatedClassName = generatedClassName;
  }
}
