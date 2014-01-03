package com.thalesgroup.hudson.plugins.cpptest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class VersionComparatorTest {

    enum Result {DIFFERENT, SAME}

    private final String version1;
    private final String version2;
    private final Result result;

    public VersionComparatorTest(String version1, String version2, Result result) {
        this.version1 = version1;
        this.version2 = version2;
        this.result = result;
    }

    @Test
    public void shouldCompareVersions() {
        switch (result) {
            case DIFFERENT:
                Assert.assertTrue(VersionComparator.INSTANCE.compare(version1, version2) < 0);
                Assert.assertTrue(VersionComparator.INSTANCE.compare(version2, version1) > 0);
                break;
            case SAME:
                Assert.assertTrue(VersionComparator.INSTANCE.compare(version1, version2) == 0);
                Assert.assertTrue(VersionComparator.INSTANCE.compare(version2, version1) == 0);
        }
    }

    @Parameters
    public static Collection<Object[]> addedNumbers() {
        return Arrays.asList(new Object[][] {
                {"7.0", "7.0",   Result.SAME},
                {"7.0", "9.5",   Result.DIFFERENT},
                {"7.0", "10",    Result.DIFFERENT},
                {"7.0", "7.0.1", Result.DIFFERENT},
        });
    }
}
