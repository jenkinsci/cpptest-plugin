package hudson.plugins.cpptest;

import hudson.model.AbstractProject;
import hudson.plugins.analysis.core.AbstractProjectAction;

/**
 * Entry point to visualize the Cpptest trend graph in the project screen.
 *
 * @author Ulli Hafner
 * 
 * NQH: adapt for Cpptest
 */
public class CpptestProjectAction extends AbstractProjectAction<CpptestResultAction> {
    /** Unique identifier of this class. */
    private static final long serialVersionUID = -654316141132780561L;

    /**
     * Instantiates a new {@link CpptestProjectAction}.
     *
     * @param project
     *            the project that owns this action
     */
    public CpptestProjectAction(final AbstractProject<?, ?> project) {
        super(project, CpptestResultAction.class, new CpptestDescriptor());
    }

    /** {@inheritDoc} */
    public String getDisplayName() {
        return Messages.Cpptest_ProjectAction_Name();
    }

    /** {@inheritDoc} */
    @Override
    public String getTrendName() {
        return Messages.Cpptest_Trend_Name();
    }
}

