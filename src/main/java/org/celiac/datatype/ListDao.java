package org.celiac.datatype;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ListDao {

   private List<StringDao> items;

   public List<StringDao> getItems() {
      return items;
   }

   public void setItems(List<StringDao> items) {
      this.items = items;
   }
   
}
