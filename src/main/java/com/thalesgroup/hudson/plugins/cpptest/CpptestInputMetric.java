package com.thalesgroup.hudson.plugins.cpptest;

import com.thalesgroup.dtkit.junit.model.JUnitModel;
import com.thalesgroup.dtkit.metrics.model.InputMetricXSL;
import com.thalesgroup.dtkit.metrics.model.InputType;
import com.thalesgroup.dtkit.metrics.model.OutputMetric;
import com.thalesgroup.dtkit.util.validator.ValidationException;

import java.io.File;
import java.io.IOException;


public class CpptestInputMetric extends InputMetricXSL {

    @Override
    public InputType getToolType() {
        return InputType.TEST;
    }

    @Override
    public String getToolName() {
        return "CppTest";
    }

    @Override
    public String getXslName() {
        String version = getToolVersion();
        if (VersionComparator.INSTANCE.compare(version, "9.0") < 0) {
            return "cpptestunit-2.0-to-junit-1.0.xsl";
        }
        return "cpptestunit-9.0-to-junit-1.0.xsl";
    }

    @Override
    public String[] getInputXsdNameList() {
        return null;
    }

    public OutputMetric getOutputFormatType() {
        return JUnitModel.OUTPUT_JUNIT_1_0;
    }

    @Override
    public boolean validateInputFile(File file) throws ValidationException {
        if (!super.validateInputFile(file)) {
            return false;
        }

        String version;

        try {
            version = VersionParser.parse(file);
        }
        catch (IOException e) {
            throw new ValidationException(e);
        }

        setToolVersion(version);

        return version != null;
    }
}
