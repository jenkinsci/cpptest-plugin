package hudson.plugins.cpptest.rules;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.digester.Digester;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

/**
 * Reads the meta data of the Cpptest rules from the DocBook files of the Cpptest distribution.
 *
 * @author NQH
 */
public final class CpptestRules {
    /** Mapping of rule names to rules. */
    private final Map<String, Rule> rulesByName = new HashMap<String, Rule>();
    /** Singleton instance. */
    private static final CpptestRules INSTANCE = new CpptestRules();

    /**
     * Returns the singleton instance.
     *
     * @return the singleton instance
     */
    public static CpptestRules getInstance() {
        return INSTANCE;
    }

    /**
     * Creates the singleton instance.
     */
    private CpptestRules() {
        // prevents instantiation
    }

    /**
     * Initializes the rules.
     */
    public void initialize() {
        try {
            String[] ruleFiles = new String[] {"coding"};
            for (int i = 0; i < ruleFiles.length; i++) {
                InputStream inputStream = CpptestRules.class.getResourceAsStream("config_" + ruleFiles[i] + ".xml");
                Digester digester = createDigester();
                List<Rule> rules = new ArrayList<Rule>();
                digester.push(rules);
                digester.parse(inputStream);
                for (Rule rule : rules) {
                    rulesByName.put(rule.getName(), rule);
                }
            }
        }
        catch (ParserConfigurationException exception) {
            Logger.getLogger(CpptestRules.class.getName()).log(Level.SEVERE, "Can't initialize Cpptest rules.", exception);
        }
        catch (IOException exception) {
            Logger.getLogger(CpptestRules.class.getName()).log(Level.SEVERE, "Can't initialize Cpptest rules.", exception);
        }
        catch (SAXException exception) {
            Logger.getLogger(CpptestRules.class.getName()).log(Level.SEVERE, "Can't initialize Cpptest rules.", exception);
        }
    }

    /**
     * Creates a new digester.
     *
     * @return the new digester.
     * @throws ParserConfigurationException
     *             if digester is not configured properly
     */
    private Digester createDigester() throws ParserConfigurationException {
        Digester digester = new Digester();
        digester.setValidating(false);
        digester.setClassLoader(CpptestRules.class.getClassLoader());

        String section = "*/section";
        digester.addObjectCreate(section, Rule.class);
        digester.addSetProperties(section);
        digester.addSetNext(section, "add");

        String subSection = "*/section/subsection";
        digester.addObjectCreate(subSection, Topic.class);
        digester.addSetProperties(subSection);
        digester.addSetNext(subSection, "setDescription");
        digester.addRule(subSection, new TopicRule());
        return digester;
    }

    /**
     * Returns all Cpptest rules.
     *
     * @return all Cpptest rules
     */
    public Collection<Rule> getRules() {
        return Collections.unmodifiableCollection(rulesByName.values());
    }

    /**
     * Returns the Cpptest rule with the specified name.
     *
     * @param name the name of the rule
     * @return the Cpptest rule with the specified name.
     */
    public Rule getRule(final String name) {
        Rule rule = rulesByName.get(name);
        if (rule == null) {
            rule = rulesByName.get(StringUtils.removeEnd(name, "Check"));
        }
        if (rule == null) {
            return new Rule(name);
        }
        return rule;
    }

    /**
     * Returns the description of the Cpptest rule with the specified name.
     *
     * @param name the name of the rule
     * @return the description for the specified rule .
     */
    public String getDescription(final String name) {
        return getRule(name).getDescription();
    }
}
