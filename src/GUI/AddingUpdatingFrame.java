/*
 * The GUI component of Adding or Updating an Item Window
 * Allows a user to Update or Add new items
 */
package GUI;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import todolistunlimited2019.ListItem;
import todolistunlimited2019.ListItemsManager;

/**
 *
 * @author wessamalzahir
 */
public class AddingUpdatingFrame extends javax.swing.JFrame {
    
    
    private final ListItemsManager listManager;
    private int selectedItem = -1;
    private boolean isAdding = false;
    private AddingUpdatingFrame self;
    
    /**
     * Creates new form AddingUpdating
     * @param listManager The List Managing logical class.
     */
    public AddingUpdatingFrame(ListItemsManager listManager) {
        this.isAdding = true;
        this.listManager = listManager;
        
        initComponents();
        this.setLocationRelativeTo(null);
    }
    
    public AddingUpdatingFrame(ListItemsManager listManager, int itemLocation) {
        this.isAdding = false;
        this.selectedItem = itemLocation;
        this.listManager = listManager;
        
        initComponents();
        this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * It initialize the UI and Action Listeners
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed">                          
    private void initComponents() {
        
        self = this;
        
        // Window title based on Add/Update
        if(isAdding) {
            setTitle("Add task");
        } else {
            setTitle("Update task");
        }
        
        nameLbl = new javax.swing.JLabel();
        descriptionLbl = new javax.swing.JLabel();
        priorityLbl = new javax.swing.JLabel();
        dueDateLbl = new javax.swing.JLabel();
        cancelBtn = new javax.swing.JButton();
        addUpdateBtn = new javax.swing.JButton();
        nameTxt = new javax.swing.JTextField();
        priorityTxt = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        descriptionTxt = new javax.swing.JTextArea();
        indicatorLbl = new javax.swing.JLabel();
        dueDateTxt = new javax.swing.JTextField();
        dueDateIndicatorLbl = new javax.swing.JLabel();
        finishedOnLbl = new javax.swing.JLabel();
        statusLbl = new javax.swing.JLabel();
        statusBox = new javax.swing.JComboBox<>();
        
        // Status list
        statusBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Not started", "In progress", "Finished"}));
        
        
        // Set Item status in status combobox
        if(!isAdding) {
            ListItem item = listManager.getItemByLocation(selectedItem);
            
            if(item.getStatus().trim().equals("Not started")) {
                statusBox.setSelectedIndex(0);
            }
            
            if(item.getStatus().trim().equals("In progress")) {
                statusBox.setSelectedIndex(1);
            }
            
            if(item.getStatus().trim().equals("Finished")) {
                statusBox.setSelectedIndex(2);
                finishedOnLbl.setText(item.getFinishedOn());
            }
        }
        
        // Status changed action listener
        statusBox.addActionListener((ActionEvent e) -> {
            // Set finsished date label if finished selected. Otherwise empty it
            if(statusBox.getSelectedItem().toString().equals("Finished")) {
                finishedOnLbl.setText( getCurrentDate() );
            } else {
                finishedOnLbl.setText("");
            }
        });
        

        statusLbl.setText("Status:");

        nameLbl.setText("Task name:");

        descriptionLbl.setText("Task Description:");

        priorityLbl.setText("Priority:");

        dueDateLbl.setText("Due date:");
        
       
        cancelBtn.setText("Cancel");
        cancelBtn.setMargin(new java.awt.Insets(0, 1, 0, 1));
        
        // Button status based on Add/Update
        if(isAdding) {
            addUpdateBtn.setText("Add");
        } else {
            addUpdateBtn.setText("Update");
        }
        
        addUpdateBtn.setMargin(new java.awt.Insets(0, 1, 0, 1));

        nameTxt.setText("");
        nameTxt.setLocation(new java.awt.Point(0, -32200));
       

        priorityTxt.setText("");

        descriptionTxt.setColumns(20);
        descriptionTxt.setRows(5);
        descriptionTxt.setText("");
        jScrollPane1.setViewportView(descriptionTxt);

        indicatorLbl.setForeground(new java.awt.Color(153, 153, 153));
        indicatorLbl.setText("Must be a number");

        dueDateTxt.setText("");

        dueDateIndicatorLbl.setForeground(new java.awt.Color(153, 153, 153));
        dueDateIndicatorLbl.setText("MM/DD/YEAR");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(descriptionLbl)
                            .addComponent(priorityLbl)
                            .addComponent(dueDateLbl)
                            .addComponent(statusLbl))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addUpdateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                     .addGroup(layout.createSequentialGroup()
                                        .addComponent(statusBox, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(finishedOnLbl))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(dueDateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(dueDateIndicatorLbl))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(priorityTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(indicatorLbl))
                                    .addComponent(nameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(19, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nameLbl)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nameLbl)
                        .addGap(14, 14, 14)
                        .addComponent(descriptionLbl)
                        .addGap(29, 29, 29))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(priorityTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(indicatorLbl)
                    .addComponent(priorityLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dueDateLbl)
                    .addComponent(dueDateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dueDateIndicatorLbl))
                   
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusLbl)
                    .addComponent(statusBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(finishedOnLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                    
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addUpdateBtn)
                    .addComponent(cancelBtn))
                .addContainerGap())
        );

        dueDateIndicatorLbl.getAccessibleContext().setAccessibleName("MM/DD/YEAR");
        pack();
        
        // If Updating form, load selected item data //
        if(!isAdding) {
            ListItem item = listManager.getItemByLocation(selectedItem);

            nameTxt.setText(item.getName());
            descriptionTxt.setText(item.getDescription());
            priorityTxt.setText(Integer.toString(item.getPriority()));
            dueDateTxt.setText(item.getDueDate());
        }
        
         // ============ Action listeners init START ============ //
         
        // Adding/Updating Action listener
        addUpdateBtn.addActionListener((ActionEvent e) -> {
            // Checking Task name
            if (nameTxt.getText().trim().length() < 1)
            {
                // error: no text
                JOptionPane.showMessageDialog(self, "The task must have a name.", "Error",  JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Checking Task description
            if (descriptionTxt.getText().trim().length() < 1)
            {
                // error: no text
                JOptionPane.showMessageDialog(self, "The task must have a description.", "Error",  JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Checking Priority
            try {
                int priorityInt = Integer.parseInt(priorityTxt.getText());
                if (priorityInt < 1)
                {
                    // error: Priorty must be greater than 0
                    JOptionPane.showMessageDialog(self, "Priority must be a positive integer.", "Error",  JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException | HeadlessException ex) {
                // error priority not a numbe, must be number
                JOptionPane.showMessageDialog(self, "Priority must be a positive integer.", "Error",  JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Checking Date
            if(!isValidDate(dueDateTxt.getText())) {
                // error duedate not a date // must be date
                JOptionPane.showMessageDialog(self, "Due date must be a valid date of format MM/DD/YEAR.", "Error",  JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            
            // Init item
            ListItem item = new ListItem(nameTxt.getText(),
                    descriptionTxt.getText(),
                    Integer.parseInt(priorityTxt.getText()),
                    dueDateTxt.getText(), finishedOnLbl.getText(), statusBox.getSelectedItem().toString()
            );
            
            
            // Update Item finished date based on status
            if(statusBox.getSelectedItem().toString().equals("Finished")) {
                item.setFinishedOn(getCurrentDate());
            } else {
                item.setFinishedOn("");
            }
            
            
            if(isAdding) {
                // Adding Action
                
                //Checking Description for uniqueness
                for (ListItem listItem : listManager.getItemsList())
                {
                    if (descriptionTxt.getText().equals(listItem.getDescription()))
                    {
                        JOptionPane.showMessageDialog(self, "Description must not match another task's description.", "Error",  JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            
            
                if(listManager.addItem(item)) {
                    // Added successfully
                    closeForm();
                } else {
                    // Error adding // to be implemented based on error code
                }
                
            } else { 
                // Updating Action
   
                //Checking Description for uniqueness
                int counter = 0;
                for (ListItem listItem : listManager.getItemsList())
                {
                    if (descriptionTxt.getText().equals(listItem.getDescription()) && counter != selectedItem)
                    {
                        JOptionPane.showMessageDialog(self, "Description must not match another task's description.", "Error",  JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    counter++;
                }
                
                if(listManager.updateItemByLocation(selectedItem, item)) {
                    // Updated successfully
                    closeForm();
                } else {
                    // Error updating // to be implemented based on error code
                }
                
            }
        });
        
        
        // Cancel button Action listener
        cancelBtn.addActionListener((ActionEvent e) -> {
            closeForm(); 
        });
         
         // ============ Action listeners init END ============ //
        
        
    }
    // </editor-fold>                        

 
    /**
     * This method checks whether a given date is a valid date format or not.
     * Based on format: mm/dd/yyyy
     * @param inDate  Date given.
     * @return        Whether or not the date is valid.
     */
    private boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
        dateFormat.setLenient(false);
        
        try {
            dateFormat.parse(inDate.trim());
         } catch (ParseException pe) {
            return false;
         }
        
         return true;
    }

   
   /**
     * This method gets the current date
     * Based on format: mm/dd/yyyy
     * @return String: current date
    */
    private String getCurrentDate() {
         DateFormat dateFormat = new SimpleDateFormat("mm/dd/yyy");
         Date date = new Date();

         return dateFormat.format(date);
    }
   
   /**
     * This method closes the current window
   */
   private void closeForm() {
       setVisible(false); 
       dispose(); 
   }

    // Variables declaration         
    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton addUpdateBtn;
    private javax.swing.JLabel nameLbl;
    private javax.swing.JLabel descriptionLbl;
    private javax.swing.JLabel priorityLbl;
    private javax.swing.JLabel dueDateLbl;
    private javax.swing.JLabel indicatorLbl;
    private javax.swing.JLabel dueDateIndicatorLbl;
    private javax.swing.JLabel finishedOnLbl;
    private javax.swing.JLabel statusLbl;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea descriptionTxt;
    private javax.swing.JTextField nameTxt;
    private javax.swing.JTextField dueDateTxt;
    private javax.swing.JTextField priorityTxt;
    private javax.swing.JComboBox<String> statusBox;
    // End of variables            
}
