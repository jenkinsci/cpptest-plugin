package hudson.plugins.cpptest.parser;

import hudson.plugins.analysis.util.model.AbstractAnnotation;
import hudson.plugins.analysis.util.model.Priority;

/**
 * A serializable Java Bean class representing a warning.
 * <p>
 * Note: this class has a natural ordering that is inconsistent with equals.
 * </p>
 *
 * @author Ulli Hafner
 * 
 * NQH: adapt for Cpptest
 */
public class Warning extends AbstractAnnotation {
    /** Unique identifier of this class. */
    private static final long serialVersionUID = 5171663552905752370L;
    /** Origin of the annotation. */
    public static final String ORIGIN = "cpptest";    
    /** Description of the rule reported in this waring */
    private String desc = "No description available.";

    /**
     * Creates a new instance of {@link Warning}.
     *
     * @param priority
     *            the priority
     * @param message
     *            the message of the warning
     * @param category
     *            the warning category
     * @param type
     *            the identifier of the warning type
     * @param start
     *            the first line of the line range
     * @param end
     *            the last line of the line range
     */
    public Warning(final Priority priority, final String message, final String category, final String type,
            final int start, final int end) {
        super(priority, message, start, end, category, type);
        setOrigin(ORIGIN);
    }

    /**
     * Creates a new instance of {@link Warning}.
     *
     * @param priority
     *            the priority
     * @param message
     *            the message of the warning
     * @param category
     *            the warning category
     * @param type
     *            the identifier of the warning type
     * @param lineNumber
     *            the line number of the warning in the corresponding file
     */
    public Warning(final Priority priority, final String message, final String category, final String type, final int lineNumber) {
        this(priority, message, category, type, lineNumber, lineNumber);
    }

    /** {@inheritDoc} */
    public String getToolTip() {
    	return desc;
    }

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

    
}

