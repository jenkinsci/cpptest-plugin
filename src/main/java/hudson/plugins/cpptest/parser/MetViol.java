package hudson.plugins.cpptest.parser;
/**
* Java Bean class for a file of the Cpptest format.
* To detect Metrics violations. Extends violation of static analysis (StdViol).
*
* @author Aurelien Hebert
*
* NQH: adapt for Cpptest
*/
public class MetViol  extends StdViol{

  private String resFile;
	private String locFile = "Not Defined";
	
	public void setResFile(String resFile) {
		this.resFile = resFile;
		this.setLocFile(resFile);
	}

	public String getResFile() {
		return resFile;
	}
}
