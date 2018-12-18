package com.ua.sutty.sax;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class SaxParserHandler extends DefaultHandler {

    private XMLReader xmlReader;
    private ContentHandler contentHandler;
    private int counter = 1;

    public SaxParserHandler(XMLReader xmlReader, ContentHandler handler) {
        this.contentHandler = handler;
        this.xmlReader = xmlReader;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        counter++;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        counter--;
        if (counter == 0) {
            xmlReader.setContentHandler(contentHandler);
        }
    }

}