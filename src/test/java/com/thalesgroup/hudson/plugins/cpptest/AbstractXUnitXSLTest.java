/*******************************************************************************
 * Copyright (c) 2009 Thales Corporate Services SAS                             *
 * Author : Gregory Boissinot                                                   *
 *                                                                              *
 * Permission is hereby granted, free of charge, to any person obtaining a copy *
 * of this software and associated documentation files (the "Software"), to deal*
 * in the Software without restriction, including without limitation the rights *
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell    *
 * copies of the Software, and to permit persons to whom the Software is        *
 * furnished to do so, subject to the following conditions:                     *
 *                                                                              *
 * The above copyright notice and this permission notice shall be included in   *
 * all copies or substantial portions of the Software.                          *
 *                                                                              *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR   *
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,     *
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE  *
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER       *
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,*
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN    *
 * THE SOFTWARE.                                                                *
 *******************************************************************************/

package com.thalesgroup.hudson.plugins.cpptest;

import com.thalesgroup.hudson.plugins.xunit.types.XUnitType;
import com.thalesgroup.hudson.library.tusarconversion.ConversionUtil;
import com.thalesgroup.hudson.library.tusarconversion.model.InputType;
import com.thalesgroup.hudson.library.tusarconversion.exception.ConversionException;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Transform;
import org.custommonkey.xmlunit.XMLUnit;
import static org.junit.Assert.assertTrue;
import org.junit.Assert;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;
import java.io.*;
import java.lang.reflect.Constructor;

public class AbstractXUnitXSLTest {

    private Class<? extends XUnitType> type;

    protected AbstractXUnitXSLTest(Class<? extends XUnitType> type) {
        this.type = type;
        setUp();
    }

    public void setUp() {
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setNormalizeWhitespace(true);
        XMLUnit.setIgnoreComments(true);
    }


    protected void conversion(InputType type, String inputPath, String resultPath) throws ConversionException, IOException, SAXException {

        // define the streams (input/output)
        InputStream inputStream = this.getClass().getResourceAsStream(inputPath);

        File target = File.createTempFile("result", "xml");
        OutputStream outputStream = new FileOutputStream(target);

        // convert the input xml file
        ConversionUtil.convert(type, inputStream, outputStream);

        // compare with expected result
        InputStream expectedResult = this.getClass().getResourceAsStream(resultPath);
        InputStream fisTarget = new FileInputStream(target);

        Diff myDiff = new Diff(XUnitXSLUtil.readXmlAsString(expectedResult), XUnitXSLUtil.readXmlAsString(fisTarget));

        Assert.assertTrue("XSL transformation did not work" + myDiff, myDiff.similar());

        fisTarget.close();

    }


}
