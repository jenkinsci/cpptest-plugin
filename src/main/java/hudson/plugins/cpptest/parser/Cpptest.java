package hudson.plugins.cpptest.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Java Bean class for a errors collection of the Cpptest format.
 *
 * @author Ulli Hafner
 * 
 * NQH: adapt for Cpptest
 * TODO: add rule description & category implementation
 */
public class Cpptest {
    /** All files of this violations collection. */
    private final List<StdViol> files = new ArrayList<StdViol>();

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
}

