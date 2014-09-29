package com.thalesgroup.hudson.plugins.cpptest;


import org.jenkinsci.lib.dtkit.model.InputMetricXSL;
import org.jenkinsci.lib.dtkit.model.InputType;
import org.jenkinsci.lib.dtkit.model.OutputMetric;
import org.jenkinsci.lib.dtkit.util.validator.ValidationException;
import org.jenkinsci.plugins.xunit.types.model.JUnitModel;

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
        return JUnitModel.LATEST;
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
