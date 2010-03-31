package hudson.plugins.cpptest.parser;

/**
 * Java Bean class for a category of the Cpptest format.
 *
 * @author NQH
 */
public class Category {
    /** Name of the violation. */
    private String name;
    /** Description of the violation. */
    private String desc;
    
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}    
}

