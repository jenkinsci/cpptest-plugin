package hudson.plugins.cpptest;

import hudson.Plugin;
import hudson.plugins.cpptest.rules.CpptestRules;

/**
 * Initializes the Cpptest messages and descriptions.
 *
 * @author Ulli Hafner
 * 
 * NQH: adapt for Cpptest
 */
public class CpptestPlugin extends Plugin {
    /** {@inheritDoc} */
    @Override
    public void start() throws Exception {
        CpptestRules.getInstance().initialize();
    }
}
