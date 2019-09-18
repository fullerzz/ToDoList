/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import todolistunlimited2019.ListItem;

/**
 *
 * @author wessamalzahir
 */
class ListItemPanel extends JPanel {


    private final JLabel indexLbl = new JLabel("#");
    private final JLabel priorityLbl = new JLabel(" Priority: # ");
    private final JLabel nameLbl = new JLabel(" NAME");
    private final JLabel descriptionLbl = new JLabel(" Description....");
    private final JLabel dueDateLbl = new JLabel(" MM/DD/YEAR");
    private final JLabel statusLbl = new JLabel(" Not started");
    private String finishedOn = "";
    
    private final int LEFT_PADDING = 10;
    private final int INDEX_PADDING = 12;
    private final int TOP_PADDING = 10;
    
    private final int FONT_SIZE = 12;
    
    private final Color PRIORITY_COLOR = Color.red;
    private final Color DUE_DATE_COLOR = Color.red;
    
     /**
     * Constructor
     * Initalizes Item table cell as JPanel
     */
    public ListItemPanel() {
        setLayout(new BorderLayout());
        
        // DECLARATIONS
        JPanel containerCenter = new JPanel();
        JPanel containerRight = new JPanel();
        JPanel containerRightTop = new JPanel();
        containerCenter.setLayout(new BorderLayout());
        containerRight.setLayout(new BorderLayout());
        containerRightTop.setLayout(new BorderLayout());
        
        // PADDING //
        nameLbl.setBorder( new EmptyBorder( TOP_PADDING, LEFT_PADDING, 3, 3 ) );
        descriptionLbl.setBorder( new EmptyBorder( -5, LEFT_PADDING, 3, 3 ) );
        priorityLbl.setBorder( new EmptyBorder( 3, 10, LEFT_PADDING, 3 ) );
        dueDateLbl.setBorder( new EmptyBorder( TOP_PADDING,0,0,10 ) );
        statusLbl.setBorder( new EmptyBorder( -40,0,0,10 ) );
        indexLbl.setBorder( new EmptyBorder( 0, INDEX_PADDING, 0, INDEX_PADDING ) );
        
        
        // FONT & COLOR
        Font nameFont = new Font(nameLbl.getFont().getName(), Font.BOLD, FONT_SIZE);
        Font descpFont = new Font(descriptionLbl.getFont().getName(), Font.PLAIN, FONT_SIZE);
        
        nameLbl.setFont(nameFont);
        descriptionLbl.setFont(descpFont);
        
        dueDateLbl.setForeground(DUE_DATE_COLOR);
        priorityLbl.setForeground(PRIORITY_COLOR);
        
        // ADD TO PANELS
        containerCenter.add(nameLbl, BorderLayout.PAGE_START);
        containerCenter.add(descriptionLbl, BorderLayout.CENTER);
        containerCenter.add(priorityLbl, BorderLayout.PAGE_END);
        
        containerRightTop.add(dueDateLbl, BorderLayout.EAST);
        containerRight.add(containerRightTop, BorderLayout.NORTH);
        containerRight.add(statusLbl, BorderLayout.CENTER);
        
        add(indexLbl, BorderLayout.WEST);
        add(containerCenter, BorderLayout.CENTER);
        add(containerRight, BorderLayout.EAST);



    
    }

    /**
    * This method sets an Item info to the cell
    * @param ListItem: Item object.
    * @param int: Index of cell (Row)
    */
    public void setItem(ListItem item, int index) {
        indexLbl.setText(Integer.toString(index+1));
        nameLbl.setText(" " + item.getName());
        descriptionLbl.setText(" " + item.getDescription());
        priorityLbl.setText(" Priority: " + Integer.toString(item.getPriority()));
        dueDateLbl.setText(item.getDueDate());
        finishedOn = item.getFinishedOn();
        
        // Set finished date if item is finished.
        if(!finishedOn.isEmpty()) {
            statusLbl.setText("Finished: " + item.getFinishedOn());
        } else {
             statusLbl.setText(item.getStatus());
        }
        
    }
    
     /**
     * This method returns an item to the renderer.
     * Based on format: mm/dd/yyyy
     * @return ListItem: Item object
     */
     public ListItem getItem() {
        return new ListItem(nameLbl.getText(), descriptionLbl.getText(), Integer.parseInt(priorityLbl.getText()), dueDateLbl.getText(), finishedOn, statusLbl.getText() );
    }

   
}