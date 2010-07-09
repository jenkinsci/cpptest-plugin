package com.thalesgroup.hudson.plugins.cpptest;


import com.thalesgroup.dtkit.metrics.hudson.api.descriptor.TestTypeDescriptor;
import com.thalesgroup.dtkit.metrics.hudson.api.type.TestType;
import hudson.Extension;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * CppUnit Type
 *
 * @author Gregory Boissinot
 */

public class CpptestPluginType extends TestType {

    @DataBoundConstructor
    public CpptestPluginType(String pattern, boolean faildedIfNotNew, boolean deleteOutputFiles) {
        super(pattern, faildedIfNotNew, deleteOutputFiles);
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
