package hudson.plugins.cpptest.parser;

import hudson.plugins.analysis.util.ContextHashCode;
import hudson.plugins.analysis.util.JavaPackageDetector;
import hudson.plugins.analysis.util.model.Priority;
import hudson.plugins.cpptest.parser.CpptestParser.FileAnnotationBuilder;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * Java Bean class for a file of the Cpptest format.
 *
 * @author Ulli Hafner
 *         <p/>
 *         NQH: adapt for Cpptest
 */
public class StdViol implements FileAnnotationBuilder {

    static final String XPATH = "ResultsSession/CodingStandards/StdViols/StdViol";

    // properties set by the xml parser
    private String rule;
    private String msg;
    private Integer ln;
    private String locFile;
    private String cat;
    private String supp;

    // computed fields
    private Priority priority;
    private String catDesc;
    private String ruleDesc;
    private String fsPath;

    // FileAnnotationBuilder methods

    public boolean isValid() {
        return !"true".equalsIgnoreCase(supp) && !rule.endsWith("package.html") && priority != null;
    }

    public Warning toFileAnnotation(String moduleName, String encoding) {
        Warning warning = new Warning(priority, msg, catDesc, rule, ln, ln);
        String packageName =  new JavaPackageDetector().detectPackageName(rule);

        //TODO: module and package settings need to be modify to work properly for C++Test purpose

        warning.setDesc(ruleDesc);
        warning.setModuleName(moduleName);
        warning.setPackageName(packageName);
        warning.setFileName(fsPath != null ? fsPath : locFile);

        try {
            int contextHashCode = new ContextHashCode().create(rule, ln, encoding);
            warning.setContextHashCode(contextHashCode);
        }
        catch (IOException e) {
            // ignore and continue
        }

        return warning;
    }

    // other methods

    void setCatDesc(Map<String, String> categories) {
        catDesc = categories.get(cat);
    }

    void setRuleDesc(Map<String, String> ruleDescs) {
        ruleDesc = ruleDescs.get(rule);
    }

    void setFsPath(Map<String, String> locations) {
        fsPath = locations.get(locFile);
    }

    /**
     * Severity of the violation.
     */
    public void setSev(String sev) {
        if (sev.length() != 1) {
            return;
        }
        switch (sev.charAt(0)) {
            case '1':
            case '2':
                priority = Priority.HIGH;
                break;
            case '3':
            case '4':
                priority = Priority.NORMAL;
                break;
            case '5':
                priority = Priority.LOW;
                break;
        }
    }

    // plain setters below

    /**
     * Name of the violation.
     */
    public void setRule(String rule) {
        this.rule = rule;
    }

    /**
     * Message of the violation.
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * Line of the violation.
     */
    public void setLn(Integer ln) {
        this.ln = ln;
    }

    /**
     * File location
     */
    public void setLocFile(String locFile) {
        this.locFile = locFile;
    }

    /**
     * Category of the violation.
     */
    public void setCat(String cat) {
        this.cat = cat;
    }

    /**
     * Suppressed violation.
     */
    public void setSupp(String supp) {
        this.supp = supp;
    }
}
