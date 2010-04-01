package hudson.plugins.cpptest.parser;

/**
 * Java Bean class for a location of file of the Cpptest format.
 *
 * @author NQH
 */
public class Location {
    /** Full path location of the file */
    private String fsPath;
    /** short location of the file */
    private String loc;
    
	public void setPath(String fsPath) {
		this.fsPath = fsPath;
	}

	public String getPath() {
		return fsPath;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public String getLoc() {
		return loc;
	}    
}

