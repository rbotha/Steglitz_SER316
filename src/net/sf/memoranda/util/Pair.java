package net.sf.memoranda.util;
 
 import nu.xom.Element;
 
 // TODO: Auto-generated Javadoc
/**
  * The Class Pair.
  */
 public class Pair {
   
   /** The element. */
   private Element element;
   
   /** The priority. */
   private int priority;
   
   /**
    * Instantiates a new pair.
    *
    * @param value the value
    * @param priority the priority
    */
   public Pair(Element value, int priority){
     setElement(value);
     setPriority(priority);
   }
   
   /**
    * Gets the element.
    *
    * @return the element
    */
   public Element getElement() {
     return element;
   }
   
   /**
    * Sets the element.
    *
    * @param value the new element
    */
   public void setElement(Element value) {
     this.element = value;
   }
   
   /**
    * Gets the priority.
    *
    * @return the priority
    */
   public int getPriority() {
     return priority;
   }
   
   /**
    * Sets the priority.
    *
    * @param priority the new priority
    */
   public void setPriority(int priority) {
     this.priority = priority;
   }
 
 }