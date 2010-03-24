package hudson.plugins.cpptest;

import hudson.maven.MavenBuild;
import hudson.maven.MavenBuildProxy;
import hudson.maven.MavenModule;
import hudson.maven.MojoInfo;
import hudson.model.Action;
import hudson.plugins.analysis.core.FilesParser;
import hudson.plugins.analysis.core.HealthAwareMavenReporter;
import hudson.plugins.analysis.core.ParserResult;
import hudson.plugins.analysis.util.PluginLogger;
import hudson.plugins.cpptest.parser.CpptestParser;

import java.io.IOException;

import org.apache.maven.project.MavenProject;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Publishes the results of the Cpptest analysis (maven 2 project type).
 *
 * @author Ulli Hafner
 * 
 * NQH: adapt for Cpptest
 */
public class CpptestReporter extends HealthAwareMavenReporter {
    /** Unique identifier of this class. */
    private static final long serialVersionUID = 2272875032054063496L;

    /** Default Cpptest pattern. */
    private static final String Cpptest_XML_FILE = "Cpptest-result.xml";

    /**
     * Creates a new instance of <code>CpptestReporter</code>.
     *
     * @param threshold
     *            Annotation threshold to be reached if a build should be considered as
     *            unstable.
     * @param newThreshold
     *            New annotations threshold to be reached if a build should be
     *            considered as unstable.
     * @param failureThreshold
     *            Annotation threshold to be reached if a build should be considered as
     *            failure.
     * @param newFailureThreshold
     *            New annotations threshold to be reached if a build should be
     *            considered as failure.
     * @param healthy
     *            Report health as 100% when the number of warnings is less than
     *            this value
     * @param unHealthy
     *            Report health as 0% when the number of warnings is greater
     *            than this value
     * @param thresholdLimit
     *            determines which warning priorities should be considered when
     *            evaluating the build stability and health
     */
    // Cpptest:OFF
    @SuppressWarnings("PMD.ExcessiveParameterList")
    @DataBoundConstructor
    public CpptestReporter(final String threshold, final String newThreshold,
            final String failureThreshold, final String newFailureThreshold,
            final String healthy, final String unHealthy, final String thresholdLimit) {
        super(threshold, newThreshold, failureThreshold, newFailureThreshold,
                healthy, unHealthy, thresholdLimit, "Cpptest");
    }
    // Cpptest:ON

    /** {@inheritDoc} */
    @Override
    protected boolean acceptGoal(final String goal) {
        return "Cpptest".equals(goal) || "site".equals(goal);
    }

    /** {@inheritDoc} */
    @Override
    public ParserResult perform(final MavenBuildProxy build, final MavenProject pom,
            final MojoInfo mojo, final PluginLogger logger) throws InterruptedException, IOException {
        FilesParser CpptestCollector = new FilesParser(logger, Cpptest_XML_FILE, new CpptestParser(getDefaultEncoding()), true, false);

        return getTargetPath(pom).act(CpptestCollector);
    }

    /** {@inheritDoc} */
    @Override
    protected CpptestResult persistResult(final ParserResult project, final MavenBuild build) {
        CpptestResult result = new CpptestResult(build, getDefaultEncoding(), project);
        build.getActions().add(new MavenCpptestResultAction(build, this, getDefaultEncoding(), result));
        build.registerAsProjectAction(CpptestReporter.this);

        return result;
    }

    /** {@inheritDoc} */
    @Override
    public Action getProjectAction(final MavenModule module) {
        return new CpptestProjectAction(module);
    }

    /** {@inheritDoc} */
    @Override
    protected Class<? extends Action> getResultActionClass() {
        return MavenCpptestResultAction.class;
    }
}

