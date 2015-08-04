/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labkit.transmission.notify.entity;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 *
 * @author Vidhyadharan Deivamani (vidhya) - it.vidhyadharan@gmail.com
 */
final public class TransmissionResponse implements Serializable {
  
    private static final long serialVersionUID = 1L;
    
    private String result;
    @XmlElementWrapper( name="arguments" )
    @XmlElement(type=Arguments.class,name="arguments")  
    private Arguments arguments;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Arguments getArguments() {
        return arguments;
    }

    public void setArguments(Arguments arguments) {
        this.arguments = arguments;
    }
    

    
    
}
