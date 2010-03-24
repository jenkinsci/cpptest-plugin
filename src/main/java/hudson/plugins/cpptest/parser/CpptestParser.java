package hudson.plugins.cpptest.parser;

import hudson.plugins.analysis.core.AbstractAnnotationParser;
import hudson.plugins.analysis.util.JavaPackageDetector;
import hudson.plugins.analysis.util.model.FileAnnotation;
import hudson.plugins.analysis.util.model.Priority;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.digester.Digester;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

/**
 * A parser for Cpptest XML files.
 *
 * @author Ulli Hafner
 * 
 * NQH: adapt for Cpptest
 */
public class CpptestParser extends AbstractAnnotationParser {
    /** Unique identifier of this class. */
    private static final long serialVersionUID = -8705621867291182458L;

    /**
     * Creates a new instance of {@link CpptestParser}.
     */
    public CpptestParser() {
        super(StringUtils.EMPTY);
    }

    /**
     * Creates a new instance of {@link CpptestParser}.
     *
     * @param defaultEncoding
     *            the default encoding to be used when reading and parsing files
     */
    public CpptestParser(final String defaultEncoding) {
        super(defaultEncoding);
    }

    /** {@inheritDoc} */
    @Override
    public Collection<FileAnnotation> parse(final InputStream file, final String moduleName) throws InvocationTargetException {
        try {
            Digester digester = new Digester();
            digester.setValidating(false);
            digester.setClassLoader(CpptestParser.class.getClassLoader());

            String rootXPath = "ResultsSession";
            digester.addObjectCreate(rootXPath, Cpptest.class);
            digester.addSetProperties(rootXPath);

            String fileXPath = "ResultsSession/CodingStandards/StdViols/StdViol";
            digester.addObjectCreate(fileXPath, hudson.plugins.cpptest.parser.StdViol.class);
            digester.addSetProperties(fileXPath);
            digester.addSetNext(fileXPath, "addFile", hudson.plugins.cpptest.parser.StdViol.class.getName());

            //String bugXPath = "ResultsSession/CodingStandards/StdViols/StdViol";
            //digester.addObjectCreate(bugXPath, Error.class);
            //digester.addSetProperties(bugXPath);
            //digester.addSetNext(bugXPath, "addError", Error.class.getName());

            Cpptest module;
            module = (Cpptest)digester.parse(new InputStreamReader(file, "UTF-8"));
            if (module == null) {
                throw new SAXException("Input stream is not a Cpptest file.");
            }

            return convert(module, moduleName);
        }
        catch (IOException exception) {
            throw new InvocationTargetException(exception);
        }
        catch (SAXException exception) {
            throw new InvocationTargetException(exception);
        }
    }

    /**
     * Converts the internal structure to the annotations API.
     *
     * @param collection
     *            the internal maven module
     * @param moduleName
     *            name of the maven module
     * @return a maven module of the annotations API
     */
    private Collection<FileAnnotation> convert(final Cpptest collection, final String moduleName) {
        ArrayList<FileAnnotation> annotations = new ArrayList<FileAnnotation>();

        for (hudson.plugins.cpptest.parser.StdViol viol : collection.getFiles()) {
            if (isValidWarning(viol)) {
                String packageName = new JavaPackageDetector().detectPackageName(viol.getRule());
                Priority priority;
                    if ("1".equalsIgnoreCase(viol.getSev())) {
                        priority = Priority.HIGH;
                    }
                    else if ("2".equalsIgnoreCase(viol.getSev())) {
                        priority = Priority.HIGH;
                    }
                    else if ("3".equalsIgnoreCase(viol.getSev())) {
                        priority = Priority.NORMAL;
                    }
                    else if ("4".equalsIgnoreCase(viol.getSev())) {
                        priority = Priority.NORMAL;
                    }
                    else if ("5".equalsIgnoreCase(viol.getSev())) {
                        priority = Priority.LOW;
                    }
                    else {
                        continue; // ignore
                    }
                    String source = viol.getRule();
                    String type = StringUtils.substringAfterLast(source, ".");
                    String category = StringUtils.substringAfterLast(StringUtils.substringBeforeLast(source, "."), ".");

                    Warning warning = new Warning(priority, viol.getMsg(), StringUtils.capitalize(category),
                            type, viol.getLocStartln(), viol.getLocEndLn());
                    warning.setModuleName(moduleName);
                    warning.setFileName(viol.getLocFile());
                    warning.setPackageName(packageName);

                    try {
                        warning.setContextHashCode(createContextHashCode(viol.getRule(), viol.getLn()));
                    }
                    catch (IOException exception) {
                        // ignore and continue
                    }
                    annotations.add(warning);
                
            }
        }
        return annotations;
    }

    /**
     * Returns <code>true</code> if this warning is valid or <code>false</code>
     * if the warning can't be processed by the Cpptest plug-in.
     *
     * @param file the file to check
     * @return <code>true</code> if this warning is valid
     */
    private boolean isValidWarning(final hudson.plugins.cpptest.parser.StdViol viol) {
        return !viol.getRule().endsWith("package.html");
    }
}

