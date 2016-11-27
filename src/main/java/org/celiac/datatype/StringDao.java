package org.celiac.datatype;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StringDao {

   private String value;

   public String getValue() {
      return value;
   }

   public void setValue(String value) {
      this.value = value;
   }
   
}
