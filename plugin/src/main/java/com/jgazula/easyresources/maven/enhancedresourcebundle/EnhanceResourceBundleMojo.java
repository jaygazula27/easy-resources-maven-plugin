package com.jgazula.easyresources.maven.enhancedresourcebundle;

import com.jgazula.easyresources.core.enhancedresourcebundle.ERBBundleConfig;
import com.jgazula.easyresources.core.enhancedresourcebundle.ERBConfig;
import com.jgazula.easyresources.core.enhancedresourcebundle.EnhancedResourceBundle;
import com.jgazula.easyresources.core.util.ValidationException;
import com.jgazula.easyresources.maven.Constants;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Defines the {@code enhance-resource-bundle} goal that will generate sources which consist of
 * enhanced resource bundles.
 * <br>
 * This will allow developers to read data from resource bundles in a relatively typesafe manner.
 */
@Mojo(name = "enhance-resource-bundle", defaultPhase = LifecyclePhase.GENERATE_SOURCES, threadSafe = true)
public class EnhanceResourceBundleMojo extends AbstractMojo {

    @SuppressWarnings("NullAway.Init")
    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject mavenProject;

    @SuppressWarnings("NullAway.Init")
    @Parameter(defaultValue = "${project.build.directory}/generated-sources/easy-resources", readonly = true,
            required = true)
    private File generatedSourcesDir;

    @SuppressWarnings("NullAway.Init")
    @Parameter(required = true)
    private List<ResourceBundleConfiguration> resourceBundles;

    @Override
    public void execute() throws MojoFailureException {
        try {
            var bundleConfigs = resourceBundles.stream()
                    .map(this::toBundleConfig)
                    .collect(Collectors.toList());

            var config = ERBConfig.builder()
                    .generatedBy(Constants.PLUGIN_NAME)
                    .bundleConfigs(bundleConfigs)
                    .destinationDir(generatedSourcesDir.toPath())
                    .build();

            EnhancedResourceBundle.create(config).generate();
            mavenProject.addCompileSourceRoot(generatedSourcesDir.getAbsolutePath());
        } catch (ValidationException e) {
            throw new MojoFailureException("Validation error when generating enhanced resource bundle(s)", e);
        } catch (Exception e) {
            throw new MojoFailureException("Unexpected error when generating enhanced resource bundle(s)", e);
        }
    }

    private ERBBundleConfig toBundleConfig(ResourceBundleConfiguration rbConfig) {
        return ERBBundleConfig.builder()
                .bundlePath(rbConfig.getBundlePath().toPath())
                .bundleName(rbConfig.getBundleName())
                .generatedPackageName(rbConfig.getGeneratedPackageName())
                .generatedClassName(rbConfig.getGeneratedClassName())
                .build();
    }
}
