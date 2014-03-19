package com.thalesgroup.hudson.plugins.cpptest;

import org.apache.commons.digester.Digester;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

class VersionParser {

    public static String parse(File file) throws IOException {
        final Digester digester = new Digester();
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

    public static class ResultsSession {
        static final String XPATH = "ResultsSession";

        private String toolVer;

        public void setToolVer(String toolVer) {
            this.toolVer = toolVer;
        }
    }
}
