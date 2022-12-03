package com.jgazula.easyresources.maven.propertiesconstants;

import com.jgazula.easyresources.core.propertiesconstants.PCConfig;
import com.jgazula.easyresources.core.propertiesconstants.PCFileConfig;
import com.jgazula.easyresources.core.propertiesconstants.PropertiesConstants;
import com.jgazula.easyresources.core.util.ValidationException;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * Defines the {@code properties-constants} goal that will generate sources which consist of constant fields
 * that point to the keys of properties files.
 * <br/>
 * This will allow developers to read data from properties files in a relatively typesafe manner.
 */
@Mojo(name = "properties-constants", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class PropertiesConstantsMojo extends AbstractMojo {

    @SuppressWarnings("NullAway.Init")
    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject mavenProject;

    @SuppressWarnings("NullAway.Init")
    @Parameter(defaultValue = "${project.build.directory}/generated-sources/easy-resources", readonly = true,
            required = true)
    private File generatedSourcesDir;

    @SuppressWarnings("NullAway.Init")
    @Parameter(required = true)
    private List<PropertiesFileConfiguration> propertiesFiles;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            var fileConfigs = propertiesFiles.stream().map(this::toPCFileConfig)
                    .collect(Collectors.toList());

            var config = PCConfig.builder()
                    .fileConfigs(fileConfigs)
                    .destinationDir(generatedSourcesDir.toPath())
                    .build();

            PropertiesConstants.create(config).generate();
            mavenProject.addCompileSourceRoot(generatedSourcesDir.getAbsolutePath());
        } catch (ValidationException e) {
            throw new MojoFailureException("Validation error when generating constants for properties file(s)", e);
        } catch (Exception e) {
            throw new MojoFailureException("Unexpected error when generating constants for properties file(s)", e);
        }
    }

    private PCFileConfig toPCFileConfig(PropertiesFileConfiguration config) {
        return PCFileConfig.builder()
                .propertiesPath(config.getFile().toPath())
                .generatedClassName(config.getGeneratedClassName())
                .generatedPackageName(config.getGeneratedPackageName())
                .build();
    }
}
