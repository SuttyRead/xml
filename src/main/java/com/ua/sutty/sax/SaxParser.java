package com.ua.sutty.sax;

import com.ua.sutty.handler.XmlHandler;
import lombok.extern.slf4j.Slf4j;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
public class SaxParser extends DefaultHandler {

    private XMLStreamWriter xmlStreamWriter;
    private XMLReader xmlReader;
    private boolean isEven;

    public void parse(String source, String target) {
        try {
            XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            this.xmlReader = saxParser.getXMLReader();
            this.xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(new FileOutputStream(target));
            saxParser.parse(source, this);
        } catch (IOException | ParserConfigurationException | XMLStreamException | SAXException e) {
            log.trace("Error during the execution of the parse method", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void startDocument() {
        try {
            xmlStreamWriter.writeStartDocument("UTF-8", "1.0");
            xmlStreamWriter.writeCharacters("\n");
        } catch (XMLStreamException e) {
            log.error("Error during the execution of the startDocument method", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (isEven) {
            xmlReader.setContentHandler(new XmlHandler(xmlReader, this));
            isEven = false;
        } else {
            try {
                xmlStreamWriter.writeStartElement(qName);
                for (int i = 0; i < attributes.getLength(); i++) {
                    xmlStreamWriter.writeAttribute(attributes.getQName(i), attributes.getValue(i));
                }
            } catch (XMLStreamException e) {
                log.error("Error during the execution of the startElement method", e);
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        try {
            xmlStreamWriter.writeCharacters(ch, start, length);
        } catch (XMLStreamException e) {
            log.error("Error during the execution of the characters method", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        try {
            xmlStreamWriter.writeEndElement();
        } catch (XMLStreamException e) {
            log.error("Error during the execution of the endElement method", e);
            throw new RuntimeException(e);
        }
        if (!isEven) {
            isEven = true;
        }
    }

}
