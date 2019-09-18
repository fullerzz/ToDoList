/*
 * A Table Cell renderer for item cells
 */
package GUI;

import java.awt.Color;
import java.awt.Component;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import todolistunlimited2019.ListItem;
import todolistunlimited2019.ListItemsManager;

/**
 *
 * @author wessamalzahir
 */

public class ListItemRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    
    private final ListItemPanel renderer = new ListItemPanel();
    private final ListItemPanel editor = new ListItemPanel();
    private ListItemsManager listManager;

    
    public ListItemRenderer(ListItemsManager listManager) {
        this.listManager = listManager;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        renderer.setItem((ListItem) value, row);
        
        // Updating color on select
        if (isSelected) {
            renderer.setBackground(Color.white);
        }
        else {
            renderer.setBackground(Color.LIGHT_GRAY);
        }
        
        return renderer;
    }

    
    @Override
    public boolean isCellEditable(EventObject anEvent) {
        return false;
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return true;
    }
    
    

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        editor.setItem((ListItem) value, row);
        return editor;
    }

    @Override
    public Object getCellEditorValue() {
        return editor.getItem();
    }

}
