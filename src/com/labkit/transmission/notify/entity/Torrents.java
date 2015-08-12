/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labkit.transmission.notify.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author VDE
 */
@XmlRootElement
final public class Torrents implements Serializable {
      private static final long serialVersionUID = 123L;
      @XmlElement
       private long   desiredAvailable;
      @XmlElement
       private long   id;
      @XmlElement
       private String name;
      @XmlElement
       private long   status;
      @XmlElement
       private long   totalSize;

    public long getDesiredAvailable() {
        return desiredAvailable;
    }

    public void setDesiredAvailable(long desiredAvailable) {
        this.desiredAvailable = desiredAvailable;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + (int) (this.totalSize ^ (this.totalSize >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Torrents other = (Torrents) obj;
        if (this.totalSize != other.totalSize) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Torrents{" + "desiredAvailable=" + desiredAvailable + ", id=" + id + ", name=" + name + ", status=" + status + ", totalSize=" + totalSize + '}';
    }

 
       
               
      
}
