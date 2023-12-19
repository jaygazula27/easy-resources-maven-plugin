package com.jgazula.easyresources.maven.enhancedresourcebundle;

import com.jgazula.easyresources.core.util.ValidationException;

import java.io.File;

/**
 * The configuration of the {@code enhanced-resource-bundle} goal used in {@link EnhanceResourceBundleMojo}.
 */
public class ResourceBundleConfiguration {

    @SuppressWarnings("NullAway.Init")
    private File bundlePath;

    @SuppressWarnings("NullAway.Init")
    private String bundleName;

    @SuppressWarnings("NullAway.Init")
    private String generatedPackageName;

    @SuppressWarnings("NullAway.Init")
    private String generatedClassName;

    public File getBundlePath() {
        if (bundlePath == null) {
            throw new ValidationException("<bundlePath> configuration cannot be empty.");
        }

        return bundlePath;
    }

    public void setBundlePath(File bundlePath) {
        this.bundlePath = bundlePath;
    }

    public String getBundleName() {
        if (bundleName == null || bundleName.isEmpty()) {
            throw new ValidationException("<bundleName> configuration cannot be empty.");
        }

        return bundleName;
    }

    public void setBundleName(String bundleName) {
        this.bundleName = bundleName;
    }

    public String getGeneratedPackageName() {
        if (generatedPackageName == null || generatedPackageName.isEmpty()) {
            throw new ValidationException("<generatedPackageName> configuration cannot be empty.");
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
