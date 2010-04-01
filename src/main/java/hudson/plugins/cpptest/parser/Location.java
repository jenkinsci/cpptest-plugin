package hudson.plugins.cpptest.parser;

/**
 * Java Bean class for a location of file of the Cpptest format.
 *
 * @author NQH
 */
public class Location {
    /** Full path location of the file */
    private String fsPath = "no_path_defined";
    /** short location of the file */
    private String loc;
    
	public void setFsPath(String fsPath) {
		this.fsPath = fsPath;
	}

	public String getFsPath() {
		return fsPath;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public String getLoc() {
		return loc;
	}    
}

