package hudson.plugins.cpptest.parser;

/**
 * Java Bean class for a file of the Cpptest format.
 * For BugDetective use and extends violation of static analysis (StdViol).
 *
 * @author Aurelien Hebert
 *         <p/>
 *         NQH: adapt for Cpptest
 */

public class FlowViol extends StdViol {
    static final String XPATH = "ResultsSession/CodingStandards/StdViols/FlowViol";
    /**
     * Category of BugDetective violation.
     */
    public void setRuleSAFMsg(String ruleSAFMsg) {
        this.setCat(ruleSAFMsg);
    }
}
