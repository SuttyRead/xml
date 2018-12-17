package com.ua.sutty.dom;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

@Slf4j
public class DomParser {

    public void parse(String source, String target) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(new File(source));
            deleteEvenElement(document.getDocumentElement());
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(target));
            TransformerFactory transformerFactory = TransformerFactory
                    .newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.transform(domSource, streamResult);
            log.trace("Processed xml file(delete even element)");
        } catch (IOException | ParserConfigurationException | SAXException | TransformerException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

    }

    private void deleteEvenElement(Node node) {
        for (int i = 0, count = 1; i < node.getChildNodes().getLength(); i++) {
            Node childNode = node.getChildNodes().item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                if (count++ % 2 == 0) {
                    node.removeChild(childNode);
                }
                if (childNode.hasChildNodes()) {
                    deleteEvenElement(childNode);
                }
            }
        }
    }

}