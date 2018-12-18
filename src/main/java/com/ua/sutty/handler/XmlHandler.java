package com.ua.sutty.handler;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class XmlHandler extends DefaultHandler {

    private XMLReader xmlReader;
    private ContentHandler contentHandler;
    private int counter = 1;

    public XmlHandler(XMLReader xmlReader, ContentHandler handler) {
        this.contentHandler = handler;
        this.xmlReader = xmlReader;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        counter++;
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        counter--;
        if (counter == 0) {
            xmlReader.setContentHandler(contentHandler);
        }
    }

}