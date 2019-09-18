/*
 * An Item object. to be used internally
 */
package todolistunlimited2019;

/**
 *
 * @author wessamalzahir
 */
public class ListItem {
    
    private String name;
    private String description;
    private Integer priority;
    private String dueDate;
    private String finishedOn;
    private String status;

     /**
     * Constructor
     * @param name Name of the item.
     * @param description Description of item.
     * @param dueDate Due date of item.
     * @param finishedOn If finished. Date the item was set as finished.
     * @param status Status of item.
     * @param priority Priority of item.
     */
    public ListItem(String name, String description, int priority, String dueDate, String finishedOn, String status) {
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.finishedOn = finishedOn;
        this.status = status;
    }
    
    
    // Getters/Setters for Item proprities
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return this.status;
    }
    
    
    public void setFinishedOn(String finishedOn) {
        this.finishedOn = finishedOn;
    }
    
    public String getFinishedOn() {
        return this.finishedOn;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    public Integer getPriority() {
        return this.priority;
    }
    
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
    
    public String getDueDate() {
        return this.dueDate;
    }

}
