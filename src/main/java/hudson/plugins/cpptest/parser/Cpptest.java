package hudson.plugins.cpptest.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Java Bean class for a errors and other collection of the Cpptest format.
 *
 * @author Ulli Hafner
 * 
 * NQH: adapt for Cpptest
 * NQH: add rule description & category implementation & location
 */
public class Cpptest {
    /** All files of this violations collection. */
	//TODO: those collections should be HashMap
    private final List<StdViol> files = new ArrayList<StdViol>();
    private final List<RuleDesc> ruleDs = new ArrayList<RuleDesc>();
    private final List<Category> categs = new ArrayList<Category>();
    private final List<Location> locs = new ArrayList<Location>();
    /**
     * Adds a new file to this bug collection.
     *
     * @param file the file to add
     */
    public void addFile(final StdViol file) {
        files.add(file);
    }

    /**
     * Returns all files of this violations collection. The returned collection is
     * read-only.
     *
     * @return all files of this bug collection
     */
    public Collection<StdViol> getFiles() {
        return Collections.unmodifiableCollection(files);
    }

    /**
     * Returns all rule descriptions of this violations collection. The returned collection is
     * read-only.
     *
     * @return all descriptions of this bug collection
     */
    public Collection<RuleDesc> getRuleDescs() {
        return Collections.unmodifiableCollection(ruleDs);
    }
    
    /**
     * Returns all categories of this violations collection. The returned collection is
     * read-only.
     *
     * @return all categories of this bug collection
     */
    public Collection<Category> getCategories() {
        return Collections.unmodifiableCollection(categs);
    }
    /**
     * Returns all locations of this violations collection. The returned collection is
     * read-only.
     *
     * @return all locations of this bug collection
     */
    public Collection<Location> getLocations() {
        return Collections.unmodifiableCollection(locs);
    }
	/**
	 * @param e
	 * @return
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean addCategory(Category e) {
		return categs.add(e);
	}

	/**
	 * @param e
	 * @return
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean addRuleDesc(RuleDesc e) {
		return ruleDs.add(e);
	}
	/**
	 * @param e
	 * @return
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean addLocation(Location e) {
		return locs.add(e);
	}
}

