package com.ua.sutty.sax;

import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.FileReader;
import java.io.IOException;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

public class SaxParserTest {

    private static final String ACTUAL_FILE = "src/test/resources/actual.xml";
    private static final String EXPECTED_FILE = "src/test/resources/expected.xml";
    private static final String PROCESSED_FILE = "src/test/resources/processed_sax.xml";

    private SaxParser saxParser;

    @Before
    public void setUp() {
        saxParser = new SaxParser();
    }

    @Test
    public void testParse() throws IOException, SAXException {
        saxParser.parse(ACTUAL_FILE, PROCESSED_FILE);
        XMLUnit.setIgnoreWhitespace(true);
        assertXMLEqual(new FileReader(EXPECTED_FILE), new FileReader(PROCESSED_FILE));
    }

}
