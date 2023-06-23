/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JFramePackage;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

class RowRenderer extends DefaultTableCellRenderer {
    private int targetRow;

    public void setTargetRow(int targetRow) {
        this.targetRow = targetRow;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (row == targetRow) {
            if (DeliverablesPage.currentWeight >= 20)
                component.setBackground(Color.RED); 
            else if (DeliverablesPage.currentWeight >= 7.5 && DeliverablesPage.currentWeight < 20)
                component.setBackground(Color.YELLOW);
            else 
                component.setBackground(Color.GREEN);
        } else {
            component.setBackground(table.getBackground());
        }

        return component;
    }
}
