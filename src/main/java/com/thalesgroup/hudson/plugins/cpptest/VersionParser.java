package com.thalesgroup.hudson.plugins.cpptest;

import org.apache.commons.digester3.Digester;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * An easy-to-use function to extract the version number from a C++test report.
 */
class VersionParser {

    static String parse(File file) throws IOException {
        final Digester digester;
        try {
            digester = createDigester(!Boolean.getBoolean(VersionParser.class.getName() + ".UNSAFE"));
        } catch (SAXException e) {
            throw new IOException(e); // same handling than later in this method
        }
        digester.setValidating(false);
        digester.setClassLoader(VersionParser.class.getClassLoader());

        digester.addObjectCreate(ResultsSession.XPATH, ResultsSession.class);
        digester.addSetProperties(ResultsSession.XPATH);

        ResultsSession rs;
        try {
            rs = (ResultsSession) digester.parse(file);
        }
        catch (SAXException e) {
            throw new IOException(e);
        }

        if (rs == null || StringUtils.isBlank(rs.toolVer)) {
            throw new IOException("Invalid C++test report");
        }

        return rs.toolVer;
    }

    /**
     * This class needs to be public only for the {@link Digester} class.
     */
    public static class ResultsSession {
        static final String XPATH = "ResultsSession";

        private String toolVer;

        public void setToolVer(String toolVer) {
            this.toolVer = toolVer;
        }
    }
    
    private static Digester createDigester(boolean secure) throws SAXException {
        Digester digester = new Digester();
        if (secure) {
            digester.setXIncludeAware(false);
            try {
                digester.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
                digester.setFeature("http://xml.org/sax/features/external-general-entities", false);
                digester.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
                digester.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            } catch (ParserConfigurationException ex) {
                throw new SAXException("Failed to securely configure xml digester parser", ex);
            }
        }
        return digester;
    }
}
