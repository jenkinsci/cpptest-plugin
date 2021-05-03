package hudson.plugins.cpptest.parser;

import hudson.plugins.analysis.core.AbstractAnnotationParser;
import hudson.plugins.analysis.util.model.FileAnnotation;
import hudson.util.IOUtils;
import org.apache.commons.digester3.Digester;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A parser for Cpptest XML files.
 *
 * @author Ulli Hafner
 * @author Aurelien Hebert
 *         <p/>
 *         NQH: adapt for Cpptest
 */
public class CpptestParser extends AbstractAnnotationParser {
    /**
     * Unique identifier of this class.
     */
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
     * @param defaultEncoding the default encoding to be used when reading and parsing files
     */
    public CpptestParser(final String defaultEncoding) {
        super(defaultEncoding);
    }

    /**
     * Boundary between the parser and the model.
     *
     * This interface is here to prevent direct use of the model internals in this class.
     */
    static interface FileAnnotationBuilder {

        /**
         * @return true if the instance yields a valid {@link FileAnnotation}
         */
        boolean isValid();

        /**
         * @param moduleName the name of the module associated with the {@link FileAnnotation}
         * @param encoding the encoding of the report file
         * @return a {@link FileAnnotation}
         */
        FileAnnotation toFileAnnotation(String moduleName, String encoding);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<FileAnnotation> parse(final InputStream stream, final String moduleName)
            throws InvocationTargetException {

        final String encoding = getDefaultEncoding();
        final Collection<? extends FileAnnotationBuilder> builders;
        final Collection<FileAnnotation> annotations;

        builders = parseReport(stream);

        if (builders.isEmpty()) {
            return Collections.emptyList();
        }

        annotations = new ArrayList<FileAnnotation>(builders.size());

        for (FileAnnotationBuilder builder: builders) {
            if (builder.isValid()) {
                annotations.add(builder.toFileAnnotation(moduleName, encoding));
            }
        }

        return annotations;
    }

    private Collection<? extends FileAnnotationBuilder> parseReport(final InputStream stream) throws InvocationTargetException {
        Reader reader = null;
        try {
            final String encoding = getDefaultEncoding();

            if ( StringUtils.isBlank(encoding) ) {
                reader = new InputStreamReader(stream);
            }
            else {
                // FIXME an unknown encoding will silently fail, ignoring the report
                reader = new InputStreamReader(stream, encoding);
            }

            final Digester digester = newDigester();
            final ResultsSession rs = (ResultsSession) digester.parse(reader);

            if (rs == null) {
                throw new IOException("Invalid Cpptest report");
            }

            return rs.getFiles();
        }
        catch (IOException e) {
            throw new InvocationTargetException(e);
        }
        catch (SAXException e) {
            throw new InvocationTargetException(e);
        }
        finally {
            IOUtils.closeQuietly(reader);
        }
    }

    private Digester newDigester() throws SAXException {
        final Digester digester = createDigester(!Boolean.getBoolean(getClass().getName() + ".UNSAFE"));
        digester.setValidating(false);
        digester.setClassLoader(getClass().getClassLoader());

        digester.addObjectCreate(ResultsSession.XPATH, ResultsSession.class);
        digester.addSetProperties(ResultsSession.XPATH);

        addElement(digester, StdViol.XPATH, StdViol.class, "addFile");
        addElement(digester, FlowViol.XPATH, FlowViol.class, "addFile");
        addElement(digester, MetViol.XPATH, MetViol.class, "addFile");
        addElement(digester, RuleDesc.XPATH, RuleDesc.class, "addRuleDesc");
        addElement(digester, Category.XPATH, Category.class, "addCategory");
        addElement(digester, Location.XPATH, Location.class, "addLocation");
        return digester;
    }

    private void addElement(Digester digester, String xpath, Class<?> clazz, String method) {
        digester.addObjectCreate(xpath, clazz);
        digester.addSetProperties(xpath);
        digester.addSetNext(xpath, method, clazz.getName());
    }

    private static Digester createDigester(boolean secure) throws SAXException {
        Digester digester = new Digester();
        if (secure) {
            digester.setXIncludeAware(false);
            try {
                digester.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
                digester.setFeature("http://xml.org/sax/features/external-general-entities", false);
                digester.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
                digester.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            } catch (ParserConfigurationException ex) {
                throw new SAXException("Failed to securely configure xml digester parser", ex);
            }
        }
        return digester;
    }
}
