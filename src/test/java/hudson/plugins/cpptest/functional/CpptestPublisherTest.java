package hudson.plugins.cpptest.functional;

import java.io.IOException;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.model.FreeStyleProject;
import hudson.plugins.cpptest.CpptestPublisher;
import hudson.plugins.cpptest.CpptestResultAction;

import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.TestBuilder;

import static org.junit.Assert.*;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;

public class CpptestPublisherTest {

    @Rule
    public JenkinsRule j = new JenkinsRule();

    @Test
    public void testRunPublisher() throws Exception {
        FreeStyleProject fs = j.createFreeStyleProject("fs");
        fs.getBuildersList().add(new CpptestBuilder());
        j.assertBuildStatusSuccess(fs.scheduleBuild2(0));
        CpptestResultAction resultAction = fs.getLastBuild().getAction(CpptestResultAction.class);
        assertTrue(resultAction != null);
        // Original input file has exactly 6 annotations to be processed
        assertTrue(resultAction.getResult().toString().equals("Cpptest Warnings: 6 annotations"));
    }

    @Test
    public void testTrendsLink200() throws ElementNotFoundException, Exception {
        FreeStyleProject fs = j.createFreeStyleProject("fs");
        fs.getPublishersList().add(CpptestPublisherTest.createDefaultCpptestPublisher());
        fs.save();
        // Facing https://issues.jenkins-ci.org/browse/JENKINS-8854 in 1.410
        // com.gargoylesoftware.htmlunit.ScriptException: TypeError: Cannot read property "nextSibling" from null (http://localhost:52114/static/c4a73f6e/scripts/hudson-behavior.js#863)
        // This seems to be fixed in 1.480
        j.submit(j.createWebClient().getPage(fs, "configure").getFormByName("config"));
        j.createWebClient().goTo(fs.getUrl() + "Cpptest/configureDefaults");
    }

    private class CpptestBuilder extends TestBuilder {

        private CpptestPublisher publisher;

        public CpptestBuilder() {
            publisher = CpptestPublisherTest.createDefaultCpptestPublisher();
        }

        @Override
        public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener)
                throws InterruptedException, IOException {
            FilePath dir = build.getWorkspace().child("Cpptest-result.xml");
            dir.copyFrom(CpptestPublisherTest.class.getResourceAsStream("/hudson/plugins/cpptest/parser/report-9.5.0.49-test.xml"));
            return publisher.perform(build, launcher, listener);
        }
    }

    public static CpptestPublisher createDefaultCpptestPublisher() {
        return new CpptestPublisher("", "", "", 
                "normal", "", false, "", 
                "", "", "", "", 
                "", "", "", "", "", 
                "", "", "", "", "", 
                "", false, false, "Cpptest");
    }

}
