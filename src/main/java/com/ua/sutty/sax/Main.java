package com.ua.sutty.sax;

import java.io.FileWriter;
import java.io.IOException;

public class Main {

    private static final String ACTUAL_FILE = "src/test/resources/actual.xml";
    private static final String EXPECTED_FILE = "src/test/resources/expected.xml";
    private static final String PROCESSED_FILE = "src/test/resources/processed.xml";

    public static void main(String[] args) throws IOException {
        FileWriter fileWriter = new FileWriter("src/test/resources/processed.xml");
        SaxParser saxParser = new SaxParser(fileWriter);
        saxParser.parseXml(ACTUAL_FILE, PROCESSED_FILE);
    }

}
