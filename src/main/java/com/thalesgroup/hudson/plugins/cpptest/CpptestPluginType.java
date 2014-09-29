package com.thalesgroup.hudson.plugins.cpptest;

import hudson.Extension;
import org.jenkinsci.lib.dtkit.descriptor.TestTypeDescriptor;
import org.jenkinsci.lib.dtkit.type.TestType;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * CppUnit Type
 *
 * @author Gregory Boissinot
 */

public class CpptestPluginType extends TestType {

    @DataBoundConstructor
    public CpptestPluginType(String pattern, boolean failIfNotNew, boolean deleteOutputFiles) {
        super(pattern, failIfNotNew, deleteOutputFiles);
    }

    public TestTypeDescriptor<?> getDescriptor() {
        return new CpptestPluginType.DescriptorImpl();
    }

    @Extension
    public static class DescriptorImpl extends TestTypeDescriptor<CpptestPluginType> {

        public DescriptorImpl() {
            super(CpptestPluginType.class, CpptestInputMetric.class);
        }

        public String getId() {
            return CpptestPluginType.class.getCanonicalName();
        }

    }

}
