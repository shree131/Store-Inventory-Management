/**
 * This class holds the details of items in inventory including number of items in 
 * stock, the cost to buy the items, and the retail price while allowing to modify and 
 * retrive their values.
 */
public class ItemDetails {

   // Data Fields
   private int quantity;
   private double cost;
   private double retail;
   
   
   /**
    * A constructor to initialize an item's details
    * @param q The number of items in stock
    * @param c The cost to buy the item
    * @param r The retail price of the item
    */
   public ItemDetails (int q, double c, double r) {
   
      quantity = q;
      cost = c;
      retail = r;
   }
   
   
   /**
    * Modifies the number of items in stock
    * @param q The number of units in inventory
    */
   public void setQuantity(int q) {
   
      quantity = q;
      
   }
   
   
   
   /**
    * Returns the number of items in stock
    * @return quantity The number of units in inventory
    */
   public int getQuantity() {
   
      return quantity;
      
   }
   
   
   /**
    * Returns the cost to buy an item
    * @return cost The cost to buy the item
    */
   public double getCost() {
   
      return cost;
      
   }
   
   
   /**
    * Returns the retail value of an item
    * @return retail The retail price of the item
    */
   public double getRetail() {
   
      return retail;
      
   }
}