package com.ua.sutty.sax;

import lombok.extern.slf4j.Slf4j;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
public class SaxParser extends DefaultHandler {

    private FileWriter fileWriter;
    //    private StringBuilder stringBuilder;
    private String indent = "";
    private XMLReader xmlReader;

    public SaxParser() {
    }

    public SaxParser(FileWriter fileWriter) {
        this.fileWriter = fileWriter;
    }

    public void parseXml(String fromFile, String toFile) {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp;

        try (FileWriter fileWriter = new FileWriter(new File(toFile))) {

            sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            xr.setContentHandler(this);

            this.xmlReader = xr;
            this.fileWriter = fileWriter;

            xr.parse(fromFile);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void startDocument() throws SAXException {
        try {
            fileWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8" + "\"?>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(indent);
        indent += "    ";
        stringBuilder.append('<').append(qName);
        System.out.println("QName = " + qName);

        if (attributes != null) {
            int numberAttributes = attributes.getLength();
            for (int i = 0; i < numberAttributes; i++) {
                stringBuilder.append(' ').append(attributes.getQName(i)).append("=\"")
                        .append(attributes.getValue(i)).append('"');
            }
        }
        stringBuilder.append('>');
        writeData(stringBuilder.toString());
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String characterData = (new String(ch, start, length)).trim();
        if (!characterData.contains("\n") && characterData.length() > 0) {
            writeData(characterData);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {

//        shouldSkip = true;
        indent = indent.substring(0, indent.length() - 4);
        String endElement = indent + "</" + qName + ">";

        writeData(endElement);
    }

    private void writeData(String data) {
        try {
            fileWriter.write(data);
            fileWriter.write('\n');

        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
