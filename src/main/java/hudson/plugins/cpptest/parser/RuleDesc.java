package hudson.plugins.cpptest.parser;

/**
 * Java Bean class for a category of the Cpptest format.
 *
 * @author NQH
 */
public class RuleDesc {
    /** Name of the violation. */
    private String id;
    /** Severity of the violation. */
    private String sev;
    /** Description of the violation. */
    private String desc;
    /** Category of the violation. */
    private String cat;
    
    
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setSev(String sev) {
		this.sev = sev;
	}

	public String getSev() {
		return sev;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public String getCat() {
		return cat;
	}

	public void setCat(String cat) {
		this.cat = cat;
	}
    
}

