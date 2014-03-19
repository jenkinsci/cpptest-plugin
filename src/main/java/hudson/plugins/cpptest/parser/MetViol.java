package hudson.plugins.cpptest.parser;

/**
 * Java Bean class for a file of the Cpptest format.
 * To detect Metrics violations. Extends violation of static analysis (StdViol).
 *
 * @author Aurelien Hebert
 *         <p/>
 *         NQH: adapt for Cpptest
 */
public class MetViol extends StdViol {
    static final String XPATH = "ResultsSession/CodingStandards/StdViols/MetViol";

    public void setResFile(String resFile) {
        setLocFile(resFile);
    }
}
