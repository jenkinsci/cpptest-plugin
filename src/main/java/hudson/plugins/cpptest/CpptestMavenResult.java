package hudson.plugins.cpptest;

import hudson.model.AbstractBuild;
import hudson.plugins.analysis.core.BuildResult;
import hudson.plugins.analysis.core.ParserResult;
import hudson.plugins.analysis.core.ResultAction;

/**
 * Represents the aggregated results of the Cpptest analysis in m2 jobs.
 *
 * @author Ulli Hafner
 * 
 * NQH: adapt for Cpptest
 */
public class CpptestMavenResult extends CpptestResult {
    /** Unique ID of this class. */
    private static final long serialVersionUID = -4913938782537266259L;

    /**
     * Creates a new instance of {@link CpptestMavenResult}.
     *
     * @param build
     *            the current build as owner of this action
     * @param defaultEncoding
     *            the default encoding to be used when reading and parsing files
     * @param result
     *            the parsed result with all annotations
     */
    public CpptestMavenResult(final AbstractBuild<?, ?> build, final String defaultEncoding,
            final ParserResult result) {
        super(build, defaultEncoding, result);
    }

    /** {@inheritDoc} */
    @Override
    protected Class<? extends ResultAction<? extends BuildResult>> getResultActionType() {
        return MavenCpptestResultAction.class;
    }
}

