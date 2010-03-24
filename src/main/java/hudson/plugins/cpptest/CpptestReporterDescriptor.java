package hudson.plugins.cpptest;

import hudson.Extension;
import hudson.maven.MavenReporter;
import hudson.plugins.analysis.core.ReporterDescriptor;
import net.sf.json.JSONObject;

import org.kohsuke.stapler.StaplerRequest;

/**
 * Descriptor for the class {@link CpptestReporter}. Used as a singleton. The
 * class is marked as public so that it can be accessed from views.
 *
 * @author Ulli Hafner
 * 
 * NQH: adapt for Cpptest
 */
@Extension(ordinal = 100)
public class CpptestReporterDescriptor extends ReporterDescriptor {
    /**
     * Creates a new instance of <code>CpptestReporterDescriptor</code>.
     */
    public CpptestReporterDescriptor() {
        super(CpptestReporter.class, new CpptestDescriptor());
    }

    /** {@inheritDoc} */
    @Override
    public MavenReporter newInstance(final StaplerRequest request, final JSONObject formData) throws FormException {
        return request.bindJSON(CpptestReporter.class, formData);
    }
}

