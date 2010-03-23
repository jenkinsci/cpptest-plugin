package hudson.plugins.cpptest;

import hudson.Extension;
import hudson.plugins.analysis.core.PluginDescriptor;

/**
 * Descriptor for the class {@link CpptestPublisher}.
 *
 * @author NQH
 */
@Extension(ordinal = 100)
public final class CpptestDescriptor extends PluginDescriptor {
    /** Plug-in name. */
    private static final String PLUGIN_NAME = "Cpptest";
    /** Icon to use for the result and project action. */
    private static final String ACTION_ICON = "/plugin/Cpptest/icons/Cpptest-24x24.png";

    /**
     * Instantiates a new find bugs descriptor.
     */
    public CpptestDescriptor() {
        super(CpptestPublisher.class);
    }

    /** {@inheritDoc} */
    @Override
    public String getDisplayName() {
        return Messages.Cpptest_Publisher_Name();
    }

    /** {@inheritDoc} */
    @Override
    public String getPluginName() {
        return PLUGIN_NAME;
    }

    /** {@inheritDoc} */
    @Override
    public String getIconUrl() {
        return ACTION_ICON;
    }
}