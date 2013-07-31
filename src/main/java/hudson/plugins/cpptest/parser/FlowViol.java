package hudson.plugins.cpptest.parser;

/**
 * Java Bean class for a file of the Cpptest format. 
 * For BugDetective use and extends violation of static analysis (StdViol).
 *
 * @author Aurelien Hebert
 * 
 * NQH: adapt for Cpptest
 */
 
public class FlowViol extends StdViol {
    /** Category of BugDetective violation. */
	private String ruleSAFMsg;
	
	public void setRuleSAFMsg(String ruleSAFMsg) {
		this.ruleSAFMsg = ruleSAFMsg;
		this.setCat(ruleSAFMsg);
	}

	public String getRuleSAFMsg() {
		return ruleSAFMsg;
	}
}