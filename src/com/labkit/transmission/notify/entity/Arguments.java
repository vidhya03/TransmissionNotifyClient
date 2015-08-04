/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labkit.transmission.notify.entity;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Vidhyadharan Deivamani (vidhya) - it.vidhyadharan@gmail.com
 */
@XmlRootElement(name="arguments")
final public class Arguments implements Serializable {
      private static final long serialVersionUID = 12L;
    
    @XmlElementWrapper( name="torrents" )
    @XmlElement(type=Torrents.class,name="torrents")  
    private List<Torrents> torrents;

    public List<Torrents> getTorrents() {
        return torrents;
    }

    public void setTorrents(List<Torrents> torrents) {
        this.torrents = torrents;
    }
    
}
