package hudson.plugins.cpptest.parser;

/**
 * Java Bean class for a violation of the Cpptest format.
 *
 * @author NQH
 */
public class Error {
// CHECKSTYLE:OFF
    /** Source of warning. */
    private String rule;
    /** Priority of warning. */
    private String sev;
    /** Message of warning. */
    private String msg;
    /** The first line of the warning range. */
    private int ln;
// CHECKSTYLE:ON

    /**
     * Returns the source.
     *
     * @return the source
     */
    public String getRule() {
        return rule;
    }

    /**
     * Sets the source to the specified value.
     *
     * @param source the value to set
     */
    public void setRule(final String rule) {
        this.rule = rule;
    }

    /**
     * Returns the severity.
     *
     * @return the severity
     */
    public String getSev() {
        return sev;
    }

    /**
     * Sets the severity to the specified value.
     *
     * @param severity the value to set
     */
    public void setSev(final String sev) {
        this.sev = sev;
    }

    /**
     * Returns the message.
     *
     * @return the message
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Sets the message to the specified value.
     *
     * @param message the value to set
     */
    public void setMsg(final String msg) {
        this.msg = msg;
    }

    /**
     * Returns the line.
     *
     * @return the line
     */
    public int getLn() {
        return ln;
    }

    /**
     * Sets the line to the specified value.
     *
     * @param line the value to set
     */
    public void setLn(final int ln) {
        this.ln = ln;
    }
}

