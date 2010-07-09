package com.thalesgroup.hudson.plugins.cpptest;

import com.thalesgroup.dtkit.junit.model.JUnitModel;
import com.thalesgroup.dtkit.metrics.api.InputMetricXSL;
import com.thalesgroup.dtkit.metrics.api.InputType;
import com.thalesgroup.dtkit.metrics.api.OutputMetric;


public class CpptestInputMetric extends InputMetricXSL {

    @Override
    public InputType getToolType() {
        return InputType.TEST;
    }

    @Override
    public String getToolVersion() {
        return "7.x";
    }

    @Override
    public String getToolName() {
        return "CppTest";
    }

    @Override
    public String getXslName() {
        return "cpptest-1.0-to-junit-1.0.xsl";
    }

    @Override
    public String getInputXsd() {
        return null;
    }

    public OutputMetric getOutputFormatType() {
        return JUnitModel.OUTPUT_JUNIT_1_0;
    }

}
