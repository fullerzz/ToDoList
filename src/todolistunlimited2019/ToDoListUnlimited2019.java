/*
 * The Main class of the program and the GUI component of the main window.
 * Allows a user to view the items and access to the various options.
 */
package todolistunlimited2019;

import GUI.AddingUpdatingFrame;
import GUI.ListItemTableModel;
import GUI.ListItemRenderer;
import com.google.gson.reflect.TypeToken;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 *
 * @author wessamalzahir
 */
public class ToDoListUnlimited2019 extends javax.swing.JFrame {
    
    private static ToDoListUnlimited2019 main;
    private final int ROW_HEIGHT = 80;
    private final String DEFUALT_DISPLAY_BY = "Priority";
    private static final String JSON_FILE = "list.json";
    
    private static ListItemsManager listManager;
    
    private static final java.lang.reflect.Type LIST_ITEM_TYPE = new TypeToken<List<ListItem>>() {
    }.getType();
    
    
    /**
     * Constructor
     * Init main form ToDoListUnlimited2019
     */
    public ToDoListUnlimited2019() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * It initialize the UI and Action Listeners
    **/
    @SuppressWarnings("unchecked")          
    private void initComponents() {
        
        setTitle("ToDoList Unlimited 2019");
        
        // Init
        tasksListPane = new javax.swing.JScrollPane();
        taskstable = new javax.swing.JTable();
        removeBtn = new javax.swing.JButton();
        exportBtn = new javax.swing.JButton();
        saveBtn = new javax.swing.JButton();
        clearBtn = new javax.swing.JButton();
        addBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        moveUpBtn = new javax.swing.JButton();
        sortByLbl = new javax.swing.JLabel();
        sortComboBox = new javax.swing.JComboBox<>();
        moveDownBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        
        // ============ List init START ============ //
                
        tModel = new ListItemTableModel();
        taskstable = new JTable(tModel);
        
        
        // Loading from file / or new list(no file)
        String listJson = "";
        try {
            listJson = loadFile();
        } catch (Exception ex) {}
        
        if(!listJson.equals("")) {
            listManager = new ListItemsManager(tModel, taskstable, listJson, DEFUALT_DISPLAY_BY);      
        } else {
            listManager = new ListItemsManager(tModel, taskstable, DEFUALT_DISPLAY_BY);      
        }
        
        ListItemRenderer itemRenderer = new ListItemRenderer(listManager);
        
        taskstable.setDefaultRenderer(Object.class, itemRenderer);
        taskstable.setDefaultEditor(Object.class, itemRenderer);
                
        taskstable.setRowHeight(ROW_HEIGHT);
        taskstable.setTableHeader(null);
        taskstable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
    

        tasksListPane.setViewportView(taskstable);
        // ============ List init END ============ //
        
        // ============ Buttons & Combobox init START ============ //
        removeBtn.setText("Remove");

        exportBtn.setText("Export report");
        
        saveBtn.setText("Save list");
        
        clearBtn.setText("Clear list");

        addBtn.setText("Add");

        updateBtn.setText("Update");

        moveUpBtn.setText("Move UP");

        sortByLbl.setText("Sort by:");

        // Display list
        sortComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Priority", "Due date", "Name", "Description","Unsorted"}));
        
        moveDownBtn.setText("Move DOWN");
       // ============ Buttons & Combobox init END ============ //

       // ============ Layouts init START ============ //
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tasksListPane, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.DEFAULT_SIZE)
                .addGap(10, 10, 10)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(removeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(moveUpBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(moveDownBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        
                    .addComponent(clearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exportBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(sortComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(sortByLbl)
                            .addGap(48, 48, 48))))
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(addBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateBtn)
                        .addGap(36, 36, 36)
                        .addComponent(moveUpBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(moveDownBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                        .addComponent(sortByLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sortComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(clearBtn)
                        .addGap(18, 18, 18)
                        .addComponent(saveBtn)
                        .addGap(18, 18, 18)
                        .addComponent(exportBtn)
                        .addGap(12, 12, 12))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(tasksListPane)))
                .addContainerGap())
        );

        pack();
        
        // ============ Layouts init END ============ //
        

        // ============ Action listeners init START ============ //
        addBtn.addActionListener((ActionEvent e) -> {
            // Show adding form
            java.awt.EventQueue.invokeLater(() -> {
                AddingUpdatingFrame adding = new AddingUpdatingFrame(listManager);
                
                adding.setMaximumSize(new Dimension(500, 280));
                adding.setMinimumSize(new Dimension(500, 280));
                adding.setSize(500, 250);
                
                adding.setVisible(true);
            }); 
        });
        
        
        updateBtn.addActionListener((ActionEvent e) -> {
            // makesure something is selected
            if(listManager.getSelectedItemLocation() == -1) {
                JOptionPane.showMessageDialog(main, "Nothing selected to update.", "Error",  JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            java.awt.EventQueue.invokeLater(() -> {
                AddingUpdatingFrame updating = new AddingUpdatingFrame(listManager, listManager.getSelectedItemLocation());
                
                updating.setMaximumSize(new Dimension(500, 280));
                updating.setMinimumSize(new Dimension(500, 280));
                updating.setSize(500, 250);
                
                updating.setVisible(true);
            }); 
        });
        
        removeBtn.addActionListener((ActionEvent e) -> {
            if (listManager.getSelectedItemLocation() != -1)
            {
                // Confirm before removing
                int choice = JOptionPane.showConfirmDialog(main, "Are you sure you want to remove this task?", "Confirm Removal", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (choice == JOptionPane.YES_OPTION)
                {
                    listManager.removeItemByLocation(listManager.getSelectedItemLocation());
                }
            }
            else
            { 
                JOptionPane.showMessageDialog(main, "Nothing selected to remove.", "Error",  JOptionPane.ERROR_MESSAGE);
            }
        });
        
         saveBtn.addActionListener((ActionEvent e) -> {
             try {
                 
                 saveToFile();
                 JOptionPane.showMessageDialog(main, "List has been saved.", "Info",  JOptionPane.INFORMATION_MESSAGE);
                 
             } catch (IOException ex) {
                 Logger.getLogger(ToDoListUnlimited2019.class.getName()).log(Level.SEVERE, null, ex);
             } 
        });
         
        clearBtn.addActionListener((ActionEvent e) -> {
            if(listManager.getItemsList().size() > 0) { 
                // Confirm before clearing
                int choice = JOptionPane.showConfirmDialog(main, "Are you sure you want to clear the list?", "Confirm Clearing", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (choice == JOptionPane.YES_OPTION)
                {
                    try {
                    listManager.clearList();
                    saveToFile();
                    JOptionPane.showMessageDialog(main, "List has been cleared.", "Info",  JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        Logger.getLogger(ToDoListUnlimited2019.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(main, "List is already empty.", "Error",  JOptionPane.ERROR_MESSAGE);
            }
        });
        
        
        
        moveDownBtn.addActionListener((ActionEvent e) -> {
            if(!"Unsorted".equals(sortComboBox.getSelectedItem().toString())) { // EDIT // Discuss
                JOptionPane.showMessageDialog(main, "To change the order of the tasks, the list must be unsorted.", "Info",  JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            listManager.moveDownByLocation(listManager.getSelectedItemLocation()); 
        });
        
        moveUpBtn.addActionListener((ActionEvent e) -> {
            if(!"Unsorted".equals(sortComboBox.getSelectedItem().toString())) { // EDIT // Discuss
                JOptionPane.showMessageDialog(main, "To change the order of the tasks, the list must be unsorted.", "Info",  JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            listManager.moveUpByLocation(listManager.getSelectedItemLocation()); 
        });
        
        sortComboBox.setSelectedIndex(listManager.getDisplayByIndex());
        sortComboBox.addActionListener((ActionEvent e) -> {
            listManager.setDisplayBy(sortComboBox.getSelectedItem().toString()); 
        });
        
        
        exportBtn.addActionListener((ActionEvent e) -> {
            JFrame parentFrame = new JFrame();
             
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file name to save to");
            
            int userSelection = fileChooser.showSaveDialog(parentFrame);
            
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                
                BufferedWriter bw = null;
                
                try {
                    String fileContent = listManager.exportReportToTxt();
                    File exportedListFile = new File(fileToSave.getAbsolutePath() + ".txt");
                    
                    FileWriter fw = new FileWriter(exportedListFile);
                    bw = new BufferedWriter(fw);
                    bw.write(fileContent); // Write content to file
                } catch (IOException ioe) {
                    ioe.printStackTrace(System.out);
                }
                finally {
                    try {
                        if (bw != null)
                            bw.close();
                    } catch (Exception ex) {
                        System.out.println("Exception: " + ex);
                    }
                }
                
            } 
        });
        
        
        // ============ Action listeners init END ============ //
    }              
       
    /** 
     * This method loads a JSON file and returns it string content.
     * Called on program start.
     * @return String: file content.
     * @throws UnsupportedEncodingException
     * @throws IOException 
     */    
    private static String loadFile() throws UnsupportedEncodingException, IOException {
        String data; 
        data = new String(Files.readAllBytes(Paths.get(JSON_FILE)));
        return data; 
    }
    
    /**
     * This method opens a JSON file and writes the items list from the List Manager to it.
     * Called on program exit.
     * @throws UnsupportedEncodingException
     * @throws IOException 
     */
    private static void saveToFile() throws UnsupportedEncodingException, IOException {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
              new FileOutputStream(JSON_FILE), "utf-8"))) {
              writer.write(listManager.toJson());
        }
    }
    
    /**
     * @param args the command line arguments
    */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ToDoListUnlimited2019.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

            /* Create and display the main form */
            java.awt.EventQueue.invokeLater(() -> {
                // Init components
                main = new ToDoListUnlimited2019();
               
                // Set minimum size, any smaller view won't look good
                main.setMinimumSize(new Dimension(615, 600));
                main.setSize(615, 600);
                
                
                // Save list to JSON file on exit.
                main.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        
                        try {
                            saveToFile();
                        } catch (IOException ex) {
                            Logger.getLogger(ToDoListUnlimited2019.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        System.exit(0);
                    }
                });
                
                
                // View main form
                main.setVisible(true);
        });
           
                           
            
    }
    
    // Variables declaration               
    private javax.swing.JButton removeBtn;
    private javax.swing.JButton exportBtn;
    private javax.swing.JButton saveBtn;
    private javax.swing.JButton clearBtn;
    private javax.swing.JButton addBtn;
    private javax.swing.JButton updateBtn;
    private javax.swing.JButton moveUpBtn;
    private javax.swing.JButton moveDownBtn;
    private javax.swing.JComboBox<String> sortComboBox;
    private javax.swing.JLabel sortByLbl;
    private javax.swing.JTable taskstable;
    private static ListItemTableModel tModel = null;
    private javax.swing.JScrollPane tasksListPane;
    // End of variables declaration                   
}
