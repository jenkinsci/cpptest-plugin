package hudson.plugins.cpptest.parser;

/**
 * Java Bean class for a category of the Cpptest format.
 *
 * @author NQH
 */
public class RuleDesc {
    static final String XPATH = "ResultsSession/CodingStandards/Rules/RulesList/Rule";

    /**
     * Name of the violation.
     */
    private String id;
    /**
     * Description of the violation.
     */
    private String desc;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

}

