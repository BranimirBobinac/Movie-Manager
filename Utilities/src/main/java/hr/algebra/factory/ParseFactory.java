/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.factory;

import java.io.InputStream;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author dnlbe
 */
public class ParseFactory {

    private ParseFactory() {
    }

    public static XMLEventReader createStaxParser(InputStream stream) throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        // spoji tekst/CDATA u jedan CHARACTERS event (inace se description reze na komade)
        factory.setProperty(XMLInputFactory.IS_COALESCING, true);
        XMLEventReader eventReader = factory.createXMLEventReader(stream);
        return eventReader;
    }
}
