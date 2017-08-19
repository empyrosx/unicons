/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.xml;

import java.io.InputStream;
import java.io.StringReader;
import org.apache.xerces.parsers.XMLDocumentParser;
import org.apache.xerces.util.SAXInputSource;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.xml.sax.InputSource;

/**
 *
 * @author Admin
 */
public class XMLParser {

    /**
     * Корневой элемент
     */
    private XMLNode root;

    /**
     * Текущий элемент
     */
    private XMLNode currentNode;

    /**
     * Разбор строки xml
     * @param xml
     * @return
     */
    public XMLNode parse(String xml) {
        return parse(new InputSource(new StringReader(xml)));
    }

    public XMLNode parse(InputSource source){
        try {
            MyParser parser = new MyParser();
            parser.parse(new SAXInputSource(source));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return root;
    }

    public XMLNode parse(InputStream stream){
        try {
            MyParser parser = new MyParser();
            parser.parse(new SAXInputSource(new InputSource(stream)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return root;
    }

    public class MyParser extends XMLDocumentParser{

        @Override
        public void startDocument(XMLLocator locator, String encoding, NamespaceContext namespaceContext, Augmentations augs) throws XNIException {
            root = null;
            currentNode = null;
        }

        @Override
        public void startElement(QName element, XMLAttributes attributes, Augmentations augs) throws XNIException {

            // если корневой элемент не задан, то значит это он
            if (root == null){
                root = new XMLNode(element.localpart);
                currentNode = root;
            }
            else {
                // иначе добавляем как дочерний
                XMLNode parentNode = currentNode;
                currentNode = new XMLNode(element.localpart);
                parentNode.add(currentNode);
            }

            // устанавливаем аттрибуты
            for (int i = 0; i < attributes.getLength(); i++){
                String name = attributes.getQName(i);
                String value = attributes.getNonNormalizedValue(i);
                if (value == null){
                    value = attributes.getValue(i);
                }
                else {
                    value = normalizeValue(value);
                }
                currentNode.addAttribute(name, value);
            }
        }

        @Override
        public void endElement(QName element, Augmentations augs) throws XNIException {
            super.endElement(element, augs);
            currentNode = currentNode.getParent();
        }

        @Override
        public void characters(XMLString text, Augmentations augs) throws XNIException {
            super.characters(text, augs);
            currentNode.addValue(text.toString());
        }

        private String normalizeValue(String value){

            String result = value;
            result = result.replaceAll("&gt;", ">");
            result = result.replaceAll("&lt;", "<");
            return result;
        }
    }


}
