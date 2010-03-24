package hudson.plugins.cpptest;

import hudson.model.AbstractBuild;
import hudson.plugins.analysis.core.AbstractResultAction;
import hudson.plugins.analysis.core.HealthDescriptor;
import hudson.plugins.analysis.core.PluginDescriptor;

/**
 * Controls the live cycle of the Cpptest results. This action persists the
 * results of the Cpptest analysis of a build and displays the results on the
 * build page. The actual visualization of the results is defined in the
 * matching <code>summary.jelly</code> file.
 * <p>
 * Moreover, this class renders the Cpptest result trend.
 * </p>
 *
 * @author Ulli Hafner
 * 
 * NQH: adapt for Cpptest
 */
public class CpptestResultAction extends AbstractResultAction<CpptestResult> {
    /** Unique identifier of this class. */
    private static final long serialVersionUID = -5329651349674842873L;

    /**
     * Creates a new instance of <code>CpptestResultAction</code>.
     *
     * @param owner
     *            the associated build of this action
     * @param healthDescriptor
     *            health descriptor
     * @param result
     *            the result in this build
     */
    public CpptestResultAction(final AbstractBuild<?, ?> owner, final HealthDescriptor healthDescriptor, final CpptestResult result) {
        super(owner, new CpptestHealthDescriptor(healthDescriptor), result);
    }

    /**
     * Creates a new instance of <code>CpptestResultAction</code>.
     *
     * @param owner
     *            the associated build of this action
     * @param healthDescriptor
     *            health descriptor
     */
    public CpptestResultAction(final AbstractBuild<?, ?> owner, final HealthDescriptor healthDescriptor) {
        super(owner, new CpptestHealthDescriptor(healthDescriptor));
    }

    /** {@inheritDoc} */
    public String getDisplayName() {
        return Messages.Cpptest_ProjectAction_Name();
    }

    /** {@inheritDoc} */
    @Override
    protected PluginDescriptor getDescriptor() {
        return new CpptestDescriptor();
    }

    /** {@inheritDoc} */
    @Override
    public String getMultipleItemsTooltip(final int numberOfItems) {
        return Messages.Cpptest_ResultAction_MultipleWarnings(numberOfItems);
    }

    /** {@inheritDoc} */
    @Override
    public String getSingleItemTooltip() {
        return Messages.Cpptest_ResultAction_OneWarning();
    }
}
