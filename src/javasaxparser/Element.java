/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasaxparser;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Brian L
 */
public class Element {
    
    String name = "";
    String content = "";
    HashMap<String,String> attributes;
    ArrayList<Element> properties;
    
    public Element(){
    }
       
}
