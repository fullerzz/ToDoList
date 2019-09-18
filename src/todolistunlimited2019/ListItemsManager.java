/*
 * Main Items(Tasks) List manager.
 * Used to manage the list of items, add, update, remove, re-order. And other conversions.
 */
package todolistunlimited2019;

import GUI.ListItemTableModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;  
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;

/**
 *
 * @author wessamalzahir
 */
public class ListItemsManager {
    
    private final ListItemTableModel tasksTableModel;
    private final JTable tasksTable;
    
    private final List<ListItem> list;
    private String displayBy;
    
     /**
     * Constructor
     * Initalizes the list manager display mode, table instances, and list array.
     * @param ListItemTableModel: The table model object.
     * @param JTable: The table UI object.
     * @param String: Display mode.
     */
    ListItemsManager(ListItemTableModel tasksTableModel, JTable tasksTable, String displayBy) {
        this.tasksTableModel = tasksTableModel;
        this.tasksTable = tasksTable;
        this.list = new ArrayList<ListItem> (); 
        
        this.displayBy = displayBy;
    }
    
     /**
     * Constructor
     * Initalizes the list manager display mode, table instances, and list array.
     * Parses a JSON String to list array.
     * @param ListItemTableModel: The table model object.
     * @param JTable: The table UI object.
     * @param String: JSON Data.
     * @param String: Display mode.
     */
    ListItemsManager(ListItemTableModel tasksTableModel, JTable tasksTable, String savedFile, String displayBy) {
        this.tasksTableModel = tasksTableModel;
        this.tasksTable = tasksTable;
        this.list = new ArrayList<ListItem> (); 
        this.displayBy = displayBy;
        
        // Parse Json
        JsonElement jelem = new Gson().fromJson(savedFile, JsonElement.class);
        JsonArray jarray = jelem.getAsJsonArray();
        
        JsonObject propertiesJson = (JsonObject) jarray.get(jarray.size()-1);
        this.displayBy = propertiesJson.get("displayBy").getAsString().replace("\"", "").trim();
       
        
        // Init list from Json
        if(jarray.size() > 1) {
            for(int i = 0; i < jarray.size()-1; i++) {
                propertiesJson = (JsonObject) jarray.get(i);
                String name = propertiesJson.get("name").getAsString().replace("\"", "").trim();
                String description = propertiesJson.get("description").getAsString().replace("\"", "").trim();
                String priority = propertiesJson.get("priority").getAsString().replace("\"", "").trim();
                String dueDate = propertiesJson.get("dueDate").getAsString().replace("\"", "").trim();
                String finishedOn = propertiesJson.get("finishedOn").getAsString().replace("\"", "").trim();
                String status = propertiesJson.get("status").getAsString().replace("\"", "").trim();
                
                ListItem item = new ListItem(name, description, Integer.parseInt(priority), dueDate, finishedOn, status);
                addItem(item);
            }
        }
        
    }
    
    /**
    * This method returns a list of items.
    * @return    Array of ListItems as a List object.
    */
    public List<ListItem> getItemsList() {
        return list;
    }
    
    /**
    * This method adds a new Item to the list.
    * It checks for valid information and does any priority shifting if required.
    * @param item   ListItem object.
    * @return       Whether an item has been successfully added. Otherwise returns error number.
    */
    public final boolean addItem(ListItem item) {
        
        // to be implemented // uniqe description checking  ..
        // Error codes ..
        
        // Check priority shifting
        shiftPrioritiesIfNeeded(item.getPriority(), -1);
        
        // Add to items array (list)
        list.add(item);
       
        // To reflect changes on gui
        refreshGUI();
        
        // Select newly added
        tasksTable.changeSelection(list.size()-1, 0, false, false);
        
        return true;
    }
    
    /**
    * This method updates an item from the list.
    * It checks for valid information and does any priority shifting if required.
    * @param location Index of item (Row).
    * @param newItem  Item object.
    * @return         integer: Whether an item has been successfully added. Otherwise returns error number.
    */
    public boolean updateItemByLocation(int location, ListItem newItem) {  
        
        // to be implemented // uniqe description checking etc ..
        // Error codes .. 
        
        // Check priority shifting
        shiftPrioritiesIfNeeded(newItem.getPriority(), location);
        
                
        // Update items array (list)
        list.set(location, newItem);
        
         
        // To reflect changes on gui
        refreshGUI();
         

        return true;
    }
    
    /**
    * This method finds an item in the list by its location.
    * @param location Index of item (Row).
    * @return ListItem: Item object.
    */
    public ListItem getItemByLocation(int location) {  
        return list.get(location);
    }
    
    /**
    * This method finds an item in the list by its location and removes it.
    * @param location Index of item (Row).
    * @return         Boolean: If removed successfully or not.
    */
    public boolean removeItemByLocation(int location) {  
        
         // Makesure an item is selected
         if(location == -1) {
             return false;
         }
         
         // Remove
         list.remove(location);
         
        // To reflect changes on gui
        refreshGUI();
         
         return true;
    }
    
    
    /**
    * This method returns the index of current display mode. 
    * to be used by UI classes.
    * @return    Index of display mode.
    */
    public int getDisplayByIndex() {
        if("Priority".equals(displayBy)) { return 0; }
        if("Due date".equals(displayBy)) { return 1; }
        if("Name".equals(displayBy)) { return 2; }
        if("Description".equals(displayBy)) { return 3; }
        if("Unsorted".equals(displayBy)) { return 4; }
        
        return -1;
    }
    
    /**
    * This method sets the display mode.
    * @param by Display mode name.
    * @return   Boolean: successfully set or not.
    */
    public boolean setDisplayBy(String by) {
        this.displayBy = by;
        
        // To reflect changes on gui
        refreshGUI();
        
        return true;
    }
    
    
    /**
    * This method generates a .txt formatted list of current items list.
    * @return String: Report Data.
    */
    public String exportReportToTxt() {
    	String listContent = "";
    	
        // Windows txt files need "\r\n" instead of "\n" for txt file to display properly
        // for loop to add each item and their contents to string listContent
        for (ListItem list1 : list) {
            listContent += list1.getName() + "\nPriority: " + list1.getPriority();
            listContent += "\nDescription: " + list1.getDescription();
            listContent += "\nStatus: " + list1.getStatus();
            listContent += "\nDue Date: " + list1.getDueDate();
            if ("Finished".equals(list1.getStatus())) {
                listContent += "\nDate Completed: " + list1.getFinishedOn();
            }
            listContent += "\n\n"; // Adding space between separate items
        }
    	
        return listContent;
    }
    
    /**
    * This method moved an item down in the list by it's location
    * It checks if moving is possible according to sorting and position.
    * @param location Index of item (Row).
    */
    public void moveDownByLocation(int location) {
        if(location < list.size()-1 && location != -1) {
            Collections.swap(list, location, location+1);
            
            // To reflect changes on gui
            refreshGUI();
            
            // reselect new location
            tasksTable.changeSelection(location+1, 0, false, false);
        }
    }
    
    
    /**
    * This method moved an item up in the list by it's location
    * It checks if moving is possible according to sorting and position.
    * @param location Index of item (Row).
    */
    public void moveUpByLocation(int location) {
        if(location != 0 && location != -1) {
            Collections.swap(list, location, location-1);
            
            // To reflect changes on gui
            refreshGUI();
            
            // reselect new location
            tasksTable.changeSelection(location-1, 0, false, false);
        }
    }
   
    
    /**
    * This method finds the current selected item location.
    * @return    Index of selected item (Row).
    */
    public int getSelectedItemLocation() {  
        return tasksTable.getSelectedRow();
    }
    
    /**
    * This method converts the items list, and display mode to a JSON string.
    * Used for storing the list.
    * @return String: JSON data.
    */
    public String toJson() {
        Gson gson = new Gson();
        
        JsonElement jsonElement = gson.toJsonTree(list);
        
        JsonObject disp = new JsonObject();
        disp.addProperty("displayBy", displayBy);
        
        jsonElement.getAsJsonArray().add(disp);
        
        return gson.toJson(jsonElement);
    }
    
    /**
    * This method clears the list.
    */
    public void clearList() {
        list.clear();
        refreshTable();
    }
    
    
    // Internal use only //
    
    /**
    * This method calls the functions required to reload the table.
    */
    private void refreshGUI() {
       // To reflect changes on gui
       reSortList();
       refreshTable();
    }
    
    /**
    * This method re-orders the list based on the display mode.
    */
    private void reSortList() {
        
        // Sorting by priority
        if("Priority".equals(displayBy)) {
            Comparator<ListItem> compareByPriority = (ListItem o1, ListItem o2) -> o1.getPriority().compareTo( o2.getPriority() );
            Collections.sort(list, compareByPriority);
        }
        
       
        
        if("Due date".equals(displayBy)) {
            
            SimpleDateFormat dateParser = new SimpleDateFormat("mm/dd/yyyy");
             
            Comparator<ListItem> compareByDate = (ListItem o1, ListItem o2) -> 
            {
                try {
                    return dateParser.parse(o1.getDueDate()).compareTo( dateParser.parse(o2.getDueDate()) );
                } catch (ParseException ex) {
                    Logger.getLogger(ListItemsManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                return 0;
            };
            
            Collections.sort(list, compareByDate);
        }
        
        if("Name".equals(displayBy)) {
            
          Comparator<ListItem> compareByName = (ListItem o1, ListItem o2) -> o1.getName().compareTo( o2.getName() );
          
          Collections.sort(list, compareByName);
          
        }
        
                
        if("Description".equals(displayBy)) {
          
            // For comparing description, we need the entire description in case two descriptions are the same at the beginning.
            Comparator<ListItem> compareByDesc = (ListItem o1, ListItem o2) -> o1.getDescription().compareTo( o2.getDescription() );
            
            Collections.sort(list, compareByDesc);
          
        }
        
    }
    
    /**
    * This is a recursive method to shift the priority.
    * Used to shift priorities of items when an item of similar priority is added or updated.
    * @param int: Newly added or updated priority number.
    * @param int: A location to ignore shifting.
    */
    private void shiftPrioritiesIfNeeded(int newPriority, int ignoreLocation) {
        
        for(int i = 0; i < list.size(); i++) {
            
            if(i != ignoreLocation) {
                ListItem currItem = list.get(i);
                int currPriority = currItem.getPriority();

                if(currPriority == newPriority) {


                    int tempPriority = newPriority + 1;
                    shiftPrioritiesIfNeeded(tempPriority, ignoreLocation);

                    list.get(i).setPriority(tempPriority);
                }
            }
            
        }
    }
    
    
    /**
    * This method refreshes the table by removing all items and adding the ones in the list.
    * Then triggering a data changed event.
    */
    private void refreshTable() {
        tasksTableModel.removeAllRows();
        
        list.stream().forEach((list1) -> {
            tasksTableModel.addRow(new Object[]{list1});
        });
        
        tasksTableModel.fireTableDataChanged();
    }
}
