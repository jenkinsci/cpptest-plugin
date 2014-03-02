package com.thalesgroup.hudson.plugins.cpptest;

import com.thalesgroup.dtkit.junit.model.JUnitModel;
import com.thalesgroup.dtkit.metrics.model.InputMetricXSL;
import com.thalesgroup.dtkit.metrics.model.InputType;
import com.thalesgroup.dtkit.metrics.model.OutputMetric;
import com.thalesgroup.dtkit.util.validator.ValidationException;
import hudson.util.IOUtils;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class CpptestInputMetric extends InputMetricXSL {

    private final XMLInputFactory factory = XMLInputFactory.newInstance();

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
    public boolean validateInputFile(File inputXMLFile) throws ValidationException {
        if (!super.validateInputFile(inputXMLFile)) {
            return false;
        }

        String version = null;
        FileReader fileReader = null;
        XMLStreamReader streamReader = null;
        try {
            fileReader = new FileReader(inputXMLFile);
            streamReader = factory.createXMLStreamReader(fileReader);

            while (streamReader.hasNext() && version == null) {
                streamReader.next();
                if (streamReader.getEventType() == XMLStreamReader.START_ELEMENT
                        && streamReader.getLocalName().equals("ResultsSession")) {

                    version = streamReader.getAttributeValue(null, "toolVer");
                    break;
                }
            }
        } catch (IOException e) {
            throw new ValidationException(e);
        } catch (XMLStreamException e) {
            throw new ValidationException(e);
        } finally {
            closeStreamReaderQuietly(streamReader);
            IOUtils.closeQuietly(fileReader);
        }

        this.setToolVersion(version);
        return version != null;
    }

    private void closeStreamReaderQuietly(XMLStreamReader streamReader) {
        try {
            if (streamReader != null) {
                streamReader.close();
            }
        } catch (XMLStreamException e) {
            // ignoring on purpose
        }
    }
}
