/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasaxparser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Brian L
 */
public class TargetXMLLoader {
    
    private static Element root;
    private static Stack<Element> stack;
    private static Element current;
    
    private static String text;
    
    public static Element load(File xmlFile) throws Exception{
                
        try {
            
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            
            DefaultHandler handler = new DefaultHandler() { //Anynomonoys inner class
                @Override
                public void startDocument() throws SAXException{
                    root = null;
                    stack = new Stack<>();
                }
                
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    
                    Element node = new Element();
                    node.name = qName;
                    text += node.name + ":\t";
                    node.attributes = new HashMap();
                    
                    for(int i = 0; i < attributes.getLength(); i++){
                        node.attributes.put(attributes.getQName(i), attributes.getValue(i));
                    } 
                    stack.push(node);
                    
                    if (current != null){
                        if(current.properties != null){
                            current.properties.add(node); //if children exist add to the array
                        }
                        else{
                            current.properties = new ArrayList(); //if no children create array
                        }
                    }                  
                    current = node;               
                }
                
                @Override
                public void endElement (String uri, String localName, String qName) throws SAXException {  
                    Element popNode = stack.pop();                    
                    if(popNode != null){
                        popNode.content = popNode.content.trim();
                    
                        if (stack.empty()){
                            root = popNode;
                            current = null;
                        }
                        else{
                            current = stack.peek();//top of stack
                        }
                    }           
                }
                
                @Override
                public void characters(char ch[], int start, int length) throws SAXException {
                    current.content = new String (ch, start, length);
                    text += current.content;
                }
                
                
            };    
            
            saxParser.parse(xmlFile.getAbsoluteFile(), handler);    
       
        } catch (Exception e) {
            throw e;
        }
        
      return root; 
    }
    
    public static String getText(){
        text.trim();
        return text;
    }
}

