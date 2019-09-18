/*
 * A Table Model for the Items table.
 */
package GUI;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author wessamalzahir
 */
public class ListItemTableModel extends DefaultTableModel {

    /**
     * This method indicates how many columns are in the table.
     * @return int: Always returns 1 to set the table as one column table.
     */
    @Override
    public int getColumnCount() {
        return 1;
    }
    
     /**
     * This method removes all rows from the table
     * Used before reloading the table with full list of items.
     */
    public void removeAllRows() {
        int rowCount = super.getRowCount();
        //Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            super.removeRow(i);
        }
    }
    
}
