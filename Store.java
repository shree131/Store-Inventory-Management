import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * This class uses the HashMap class to represent a store inventory, keeps records  
 * of the sales, number of items in stock, total gains, losses, and overall profit.
 */
 
public class Store {
   
   private String id;
   private double profit=0;
   private double expense=0;
   
   // Map representing an inventory
   private Map<String, ItemDetails> invent;
   
   
   /**
    * Constructor to initialize a store inventory
    */
   public Store () {
   
     id="";
     invent = new HashMap<> ();
     
   }
   
   
   /**	
    * Reads from the given file and processes the instructions regarding various 
    * transactions such as adding and deleting an item, buying and selling items,
    * and displaying item's overall report
	 * @param dataFile The file containing transactions and item details
    * @return a string value detailing the transactions performed               
    */
   public String process (String dataFile) {
   
      String direction = "";
      String str = "";
      
      
      // Exceptional handling if file is not found
      try {
      
         // Read from the file
         Scanner input = new Scanner(new File(dataFile));
         
         
         // Read the transaction instructions from file
         while (input.hasNextLine()) {
         
            String text = input.nextLine();
            int space = text.indexOf(" ");
            
            // If spaces are found in the line
            if (space != -1) {
            
               direction = text.substring(0, space);
            }
            
            // If no spaces were found
            else {
            
               direction = text;
            }
            
            
            // Process the given instrcution
            if (direction.equals("new")) {
               
               str += New(text);
            }
            
            else if (direction.equals("delete")) {
               
               str += delete(text);
            }
            
            else if (direction.equals("buy")) {
               
               str += buy(text);
            }
            
            else if (direction.equals("sell")) {
               
               str += sell(text);
            }
            
            else if (direction.equals("item")) {
               
               str += item(text);
            }
            
            else if (direction.equals("report")){
               
               str += report();
            }
            
            // Clear the variables
            direction = "";
            space = 0;
            
         }
      
         return str;
      }
      
      // Catch exception if file is not found
      catch (FileNotFoundException e) {
      
         return "File Not Found!!";
      }
   }
   
   
   
   /**	
	 * Sets a new type of item with a unique ID for the store to carry 
	 * @param str the line from the file with instructions to add a new item
	 * @return a string containing conformation message if item was added successfully;
    *         an error message if item is already in inventory.
    */
   private String New (String str) {
   
      // Split the line into words
      String[] word = str.split(" ");
      
      id = word[1];
      double cost = Double.parseDouble(word[2]);
      double sellPrice = Double.parseDouble(word[3]);
      
      
      // Show error message if ID is not unique
      if (invent.get(id) != null) {
      
         return String.format("ERROR: %-1s already in inventory\n", id);
      }
      
      
      // Add the unique ID with its values to inventory
      else {
      
         invent.put(id, new ItemDetails(0, cost, sellPrice));
         return String.format("%-1s added to inventory\n", id);
         
      }
   } 
   
   
   
   /**	
	 * Removes all units of an item from inventory and calculates the loss
	 * @param text the line from the file with instrcutions to delete an item
	 * @return string containing conformation message if item was deleted successfully;
    *         an error message if item is not found in inventory.
    */
   private String delete(String text) {
   
      // Find index of the space
      int space = text.indexOf(" ");
      
      int quantity = 0;
      double loss = 0.0;
      double cost = 0.0;
      // Retrive the ID
      id = text.substring(space+1, text.length());
      
      
      // Item is found in inventory
      if (invent.get(id) != null) {
         
         cost = invent.get(id).getCost();
         quantity = invent.get(id).getQuantity();
         
         // Total loss after deleting the item
         loss = cost * quantity;
         
         invent.remove(id);
         expense += loss;
         
         return String.format("%-1s removed from inventory for a total loss of $%-1.2f \n", id, loss);
      }
      
      
      // Error message when item is not found in inventory
      else {
      
         return String.format("ERROR: %-1s not in inventory\n", id);
      }
   }
   
   
   
   /**	
	 * Updates the records if the store purchased a number of items at the indicated cost
	 * @param str the line from the file indicating the purchase of certain units of an item
	 * @return the transaction conformation if the units of items purchased was added successfully;
    *         an error message if item is not found in inventory
    */
   private String buy(String str) {
   
      // Split the line into words
      String[] word = str.split(" ");
      
      id = word[1];
      double cost =0;
      int unit = Integer.parseInt(word[2]);
      
      
      // Check if item is in inventory 
      if (invent.get(id) != null) {
         
         cost = (invent.get(id).getCost()) * unit;
         
         // Update the quantity of item
         invent.get(id).setQuantity(unit);
          
         return String.format("%-1d units of %-1s added to inventory at a total cost of $%-1.2f\n", 
                              unit, id, cost); 
      }
      
      
      // Error message if item was not found
      else {
      
         return String.format("ERROR: %-1s not in inventory\n", id);
      }
   }
   
   
   
   /**	
	 * Updates the records if the store sold units of a certain item at the indicated retail price
	 * @param str the line from the file indicating the sales of units of a certain item
	 * @return the transaction conformation if units of items sold were added successfully;
    *         an error message if the units of items exceeded the available items in inventory;
    *         an error message if item is not in inventory
    */
   private String sell (String str) {
   
      // Splits the line into words
      String[] word = str.split(" ");
      
      id = word[1];
      int unit = Integer.parseInt(word[2]);
      double sales = 0.00;
      double cost = 0.00;
      
      
      // Check if item is in inventory
      if (invent.get(id) != null) {
      
         int currentUnit = invent.get(id).getQuantity();
         
         // The no. of items to be sold does not exceed the no. of items in stock
         if (unit <= currentUnit) {
            
            sales = invent.get(id).getRetail() * unit;
            cost = invent.get(id).getCost() * unit;
            
            // Update the quantity after items are sold
            invent.get(id).setQuantity(currentUnit - unit);
            
            profit += sales;
            expense += invent.get(id).getCost() * unit;
            
            return String.format("%-1d units of %-1s sold at a total price of $%-1.2f for a profit of $%-1.2f\n", 
                              unit, id, sales, (sales-cost)); 
         }
         
         
         // The no. of items to be sold exceeds the no. of items is stock
         else {
            return String.format("ERROR: %-1d exceeds units of %-1s in inventory\n", unit, id); 
         }
      
      }
      
      // Error message if item is not in stock
      else {
         return String.format("ERROR: %-1s not in inventory\n", id);
      }
   }
   
   
   
   /**	
	 * Returns a formatted string containing item ID, unit cost, number of units in the 
    * inventory, and the cost of the units	 
    * @param str the line from the file with the given instruction
	 * @return a string containing details about the given item;
    *         an error message if item is not in inventory
    */
   private String item(String str) {
   
      // Find the index of space
      int space = str.indexOf(" ");
      
      id = str.substring(space+1, str.length());
      double cost = 0.00;
      int unit = 0;
      
      
      // Checks if item is in inventory
      if (invent.get(id) != null) {
      
         cost = invent.get(id).getCost();
         unit = invent.get(id).getQuantity();
         
         return String.format("ID: %-1s, Unit cost: $%-1.2f, Units: %-1d, Total cost: $%-1.2f\n", 
                                 id, cost, unit, cost * unit);
      }
      
      // Item is not in inventory 
      return String.format("ERROR: %-1s not in inventory\n", id);
   
   }
   
   
   
   /**	
	 * Returns a formatted string containing the total cost of all items in  
    * inventory and the total profit earned by the store
	 * @return a string containing the total cost and the total profit
    */
   private String report() {
   
      // A set containing items ID
      Set<String> keys = new HashSet<String>();
      keys.addAll(invent.keySet());
      
      Iterator<String> it = keys.iterator();
      double totalCost=0.00;
      
      
      // Calculate total cost of items in inventory
      while(it.hasNext()) {
      
         id = it.next();
         totalCost += invent.get(id).getQuantity() * invent.get(id).getCost();
      }
      
      return String.format("Total cost: $%-1.2f, Total profit: $%-1.2f", totalCost, (profit-expense));
      
   }
   
}