package hudson.plugins.cpptest.parser;

import hudson.plugins.analysis.core.AnnotationParser;
import hudson.plugins.analysis.util.model.FileAnnotation;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class CpptestParserPriorityTest {

    private final File file;
    private final int highCount;
    private final int normalCount;
    private final int lowCount;

    public CpptestParserPriorityTest(String filename, int highCount, int normalCount, int lowCount)
            throws Exception {

        this.file = new File(this.getClass().getResource(filename).toURI());
        this.highCount = highCount;
        this.normalCount = normalCount;
        this.lowCount = lowCount;
    }

    @Test
    public void shouldExtractAnnotationsAndTheirPriorities()
            throws InvocationTargetException {

        AnnotationParser parser = new CpptestParser();
        Collection<FileAnnotation> annotations = parser.parse(file, "test");

        Assert.assertEquals(highCount + normalCount + lowCount, annotations.size());

        int high = 0, normal = 0, low = 0;

        for (FileAnnotation annotation: annotations) {
            switch (annotation.getPriority()) {
                case HIGH:   high++;   break;
                case NORMAL: normal++; break;
                case LOW:    low++;    break;
            }
        }

        Assert.assertEquals(highCount, high);
        Assert.assertEquals(normalCount, normal);
        Assert.assertEquals(lowCount, low);
    }

    @Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {"report-7.2.11.35-test.xml", 1, 4, 2},
                {"report-9.5.0.49-test.xml",  2, 1, 3},
        });
    }
}
