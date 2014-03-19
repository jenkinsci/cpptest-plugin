package hudson.plugins.cpptest.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
    private final List<RuleDesc> ruleDs = new ArrayList<RuleDesc>();
    private final List<Category> categs = new ArrayList<Category>();
    private final List<Location> locs = new ArrayList<Location>();

    /**
     * Returns all files of this violations collection. The returned collection is
     * read-only.
     *
     * @return all files of this bug collection
     */
    public Collection<StdViol> getFiles() {

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
    public boolean addCategory(Category e) {
        return categs.add(e);
    }

    /**
     * @param e the result description
     * @return true if OK, false otherwise
     * @see java.util.List#add(java.lang.Object)
     */
    public boolean addRuleDesc(RuleDesc e) {
        return ruleDs.add(e);
    }

    /**
     * @param e the result description
     * @return true if OK, false otherwise
     * @see java.util.List#add(java.lang.Object)
     */
    public boolean addLocation(Location e) {
        return locs.add(e);
    }
}

