package hudson.plugins.cpptest.parser;

import org.apache.commons.lang.StringUtils;

import hudson.plugins.cpptest.parser.CpptestParser.FileAnnotationBuilder;

import java.util.*;


/**
 * Java Bean class for a errors and other collection of the Cpptest format.
 *
 * @author Ulli Hafner
 *         <p/>
 *         NQH: adapt for Cpptest
 *         NQH: add rule description & category implementation & location
 */
public class ResultsSession {

    static final String XPATH = "ResultsSession";

    /**
     * All files of this violations collection.
     */
    private final List<StdViol> files = new ArrayList<StdViol>();
    private final Map<String, String> ruleDs = new HashMap<String, String>();
    private final Map<String, String> categs = new HashMap<String, String>();
    private final Map<String, String> locs = new HashMap<String, String>();

    /**
     * Returns all files of this violations collection. The returned collection is
     * read-only.
     *
     * @return all files of this bug collection
     */
    public Collection<? extends FileAnnotationBuilder> getFiles() {

        // This should in theory only happen once, but it's fine to run it several times.
        // It is supposedly idempotent, but the classes are mutable. At least, nothing
        // should change once XML parsing is done.

        for (StdViol viol : files) {
            if (viol.isValid()) {
                viol.setCatDesc(categs);
                viol.setRuleDesc(ruleDs);
                viol.setFsPath(locs);
            }
        }

        return Collections.unmodifiableCollection(files);
    }

    /**
     * Adds a new file to this bug collection.
     *
     * @param file the file to add
     */
    public void addFile(final StdViol file) {
        files.add(file);
    }

    /**
     * @param e the category
     * @return true if OK, false otherwise
     * @see java.util.List#add(java.lang.Object)
     */
    public void addCategory(Category e) {
        categs.put(e.getName(), StringUtils.capitalize(e.getDesc()));
    }

    /**
     * @param e the result description
     * @return true if OK, false otherwise
     * @see java.util.List#add(java.lang.Object)
     */
    public void addRuleDesc(RuleDesc e) {
        ruleDs.put(e.getId(), e.getDesc());
    }

    /**
     * @param e the result description
     * @return true if OK, false otherwise
     * @see java.util.List#add(java.lang.Object)
     */
    public void addLocation(Location e) {
        locs.put(e.getLoc(), e.getFsPath());
    }
}

