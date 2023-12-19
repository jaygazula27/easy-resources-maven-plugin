package com.jgazula.easyresources.maven.enhancedresourcebundle;

import com.jgazula.easyresources.core.util.ValidationException;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

public class ResourceBundleConfigurationTests {

    private static final File TEST_BUNDLE_PATH = new File("my-bundle-path");
    private static final String TEST_BUNDLE_NAME = "TestBundle";
    private static final String TEST_CLASS_NAME = "testClass";
    private static final String TEST_PACKAGE_NAME = "com.jgazula.testClass";

    @Test
    void bundlePathCannotBeNull() {
        // given
        ResourceBundleConfiguration configuration = new ResourceBundleConfiguration();
        configuration.setBundleName(TEST_BUNDLE_NAME);
        configuration.setGeneratedPackageName(TEST_PACKAGE_NAME);
        configuration.setGeneratedClassName(TEST_CLASS_NAME);

        // then
        assertThatExceptionOfType(ValidationException.class).isThrownBy(configuration::getBundlePath);
    }

    @Test
    void bundleNameCannotBeNull() {
        // given
        ResourceBundleConfiguration configuration = new ResourceBundleConfiguration();
        configuration.setBundlePath(TEST_BUNDLE_PATH);
        configuration.setGeneratedPackageName(TEST_PACKAGE_NAME);
        configuration.setGeneratedClassName(TEST_CLASS_NAME);

        // then
        assertThatExceptionOfType(ValidationException.class).isThrownBy(configuration::getBundleName);
    }

    @Test
    void bundleNameCannotBeEmpty() {
        // given
        ResourceBundleConfiguration configuration = new ResourceBundleConfiguration();
        configuration.setBundlePath(TEST_BUNDLE_PATH);
        configuration.setBundleName("");
        configuration.setGeneratedPackageName(TEST_PACKAGE_NAME);
        configuration.setGeneratedClassName(TEST_CLASS_NAME);

        // then
        assertThatExceptionOfType(ValidationException.class).isThrownBy(configuration::getBundleName);
    }

    @Test
    void generatedPackageNameCannotBeNull() {
        // given
        ResourceBundleConfiguration configuration = new ResourceBundleConfiguration();
        configuration.setBundlePath(TEST_BUNDLE_PATH);
        configuration.setBundleName(TEST_BUNDLE_NAME);
        configuration.setGeneratedClassName(TEST_CLASS_NAME);

        // then
        assertThatExceptionOfType(ValidationException.class).isThrownBy(configuration::getGeneratedPackageName);
    }

    @Test
    void generatedPackageNameCannotBeEmpty() {
        // given
        ResourceBundleConfiguration configuration = new ResourceBundleConfiguration();
        configuration.setBundlePath(TEST_BUNDLE_PATH);
        configuration.setBundleName(TEST_BUNDLE_NAME);
        configuration.setGeneratedPackageName("");
        configuration.setGeneratedClassName(TEST_CLASS_NAME);

        // then
        assertThatExceptionOfType(ValidationException.class).isThrownBy(configuration::getGeneratedPackageName);
    }

    @Test
    void generatedClassNameCannotBeNull() {
        // given
        ResourceBundleConfiguration configuration = new ResourceBundleConfiguration();
        configuration.setBundlePath(TEST_BUNDLE_PATH);
        configuration.setBundleName(TEST_BUNDLE_NAME);
        configuration.setGeneratedPackageName(TEST_PACKAGE_NAME);

        // then
        assertThatExceptionOfType(ValidationException.class).isThrownBy(configuration::getGeneratedClassName);
    }

    @Test
    void generatedClassNameCannotBeEmpty() {
        // given
        ResourceBundleConfiguration configuration = new ResourceBundleConfiguration();
        configuration.setBundlePath(TEST_BUNDLE_PATH);
        configuration.setBundleName(TEST_BUNDLE_NAME);
        configuration.setGeneratedPackageName(TEST_PACKAGE_NAME);
        configuration.setGeneratedClassName("");

        // then
        assertThatExceptionOfType(ValidationException.class).isThrownBy(configuration::getGeneratedClassName);
    }

    @Test
    void successfulValidation() {
        // given
        ResourceBundleConfiguration configuration = new ResourceBundleConfiguration();
        configuration.setBundlePath(TEST_BUNDLE_PATH);
        configuration.setBundleName(TEST_BUNDLE_NAME);
        configuration.setGeneratedPackageName(TEST_PACKAGE_NAME);
        configuration.setGeneratedClassName(TEST_CLASS_NAME);

        // then
        assertThatNoException().isThrownBy(() -> {
            configuration.getBundlePath();
            configuration.getBundleName();
            configuration.getGeneratedPackageName();
            configuration.getGeneratedClassName();
        });
    }
}
