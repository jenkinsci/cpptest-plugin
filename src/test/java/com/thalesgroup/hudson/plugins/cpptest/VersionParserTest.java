package com.thalesgroup.hudson.plugins.cpptest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class VersionParserTest {

    private final String version;
    private final File file;

    public VersionParserTest(String version, String filename) {
        this.version = version;
        this.file = new File(filename);
    }

    @Test
    public void shouldExtractTheVersion() throws IOException {
        Assert.assertEquals(version, VersionParser.parse(file));
    }

    @Parameters
    public static Collection<Object[]> parameters() {
        final String root = "src/test/resources/com/thalesgroup/hudson/plugins/cpptest/";
        return Arrays.asList(new Object[][]{
                {"7.1.3.23", root + "testcase1/result.xml"},
                {"9.5.0.49", root + "testcase2/result.xml"},
        });
    }
}
