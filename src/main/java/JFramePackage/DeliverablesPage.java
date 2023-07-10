/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package JFramePackage;

import MainPackage.UniSync;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;



/**
 *
 * @author sebte
 */
public class DeliverablesPage extends javax.swing.JFrame {
    /**
     * Creates new form DeliverablesPage
     */
    static double currentWeight; //Weight of added deliverable (used for Row Renderer)
    public  DeliverablesPage() {
        
        initComponents();
        
        DefaultTableModel deliverablesTableModel = (DefaultTableModel)deliverablesTable.getModel();
        try{
            PreparedStatement stmt = UniSync.conn.prepareStatement("SELECT * FROM deliverables INNER JOIN studentdeliverables ON deliverables.Code = studentdeliverables.Code "
                    + "AND deliverables.DeliverableNum = studentdeliverables.DeliverableNum WHERE DeliverableStatus = 1 AND StudentID = ?");

            stmt.setInt(1, UniSync.getStudent().getStudentID());
            UniSync.result = stmt.executeQuery();
            
            int rowCount = 0;
            while(UniSync.result.next()){
                currentWeight = UniSync.result.getDouble("DeliverableWeight");
                deliverablesTableModel.addRow(new Object[] {UniSync.result.getString("Code"), UniSync.result.getInt("DeliverableNum"), UniSync.result.getString("DeliverableName"), UniSync.result.getString("DueDate") });
                rowCount++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }  
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        rSMaterialButtonRectangle2 = new rojerusan.RSMaterialButtonRectangle();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        courseLabel = new app.bolivia.swing.JCTextField();
        deliverableIDLabel = new app.bolivia.swing.JCTextField();
        dateLabel = new app.bolivia.swing.JCTextField();
        addDeliverableButton = new rojerusan.RSMaterialButtonRectangle();
        removeDeliverableButton = new rojerusan.RSMaterialButtonRectangle();
        completedButton = new rojerusan.RSMaterialButtonRectangle();
        errorMsg = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        deliverablesTable = new rojeru_san.complementos.RSTableMetro();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1200, 800));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 36)); // NOI18N
        jLabel1.setText("Deliverables");
        jPanel1.add(jLabel1);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setPreferredSize(new java.awt.Dimension(400, 200));
        jPanel2.setRequestFocusEnabled(false);
        jPanel2.setLayout(new java.awt.BorderLayout());

        rSMaterialButtonRectangle2.setBackground(new java.awt.Color(204, 0, 51));
        rSMaterialButtonRectangle2.setText("Done");
        rSMaterialButtonRectangle2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle2ActionPerformed(evt);
            }
        });
        jPanel5.add(rSMaterialButtonRectangle2);

        jPanel2.add(jPanel5, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel2, java.awt.BorderLayout.EAST);

        jPanel3.setPreferredSize(new java.awt.Dimension(800, 200));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setPreferredSize(new java.awt.Dimension(800, 130));
        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 40, 5));

        courseLabel.setPlaceholder("Enter Course (e.g \"ELEC252\")");
        courseLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                courseLabelActionPerformed(evt);
            }
        });
        jPanel4.add(courseLabel);

        deliverableIDLabel.setPlaceholder("Enter Deliverable ID");
        deliverableIDLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deliverableIDLabelActionPerformed(evt);
            }
        });
        jPanel4.add(deliverableIDLabel);

        dateLabel.setPlaceholder("Enter Due Date \"dd/mm/yyyy\"");
        dateLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateLabelActionPerformed(evt);
            }
        });
        jPanel4.add(dateLabel);

        addDeliverableButton.setBackground(new java.awt.Color(204, 0, 51));
        addDeliverableButton.setText("Add Deliverable");
        addDeliverableButton.setPreferredSize(new java.awt.Dimension(200, 50));
        addDeliverableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addDeliverableButtonActionPerformed(evt);
            }
        });
        jPanel4.add(addDeliverableButton);

        removeDeliverableButton.setBackground(new java.awt.Color(204, 0, 51));
        removeDeliverableButton.setText("Remove Deliverable");
        removeDeliverableButton.setPreferredSize(new java.awt.Dimension(200, 50));
        removeDeliverableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeDeliverableButtonActionPerformed(evt);
            }
        });
        jPanel4.add(removeDeliverableButton);

        completedButton.setBackground(new java.awt.Color(204, 0, 51));
        completedButton.setText("Completed");
        completedButton.setPreferredSize(new java.awt.Dimension(200, 50));
        completedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                completedButtonActionPerformed(evt);
            }
        });
        jPanel4.add(completedButton);

        errorMsg.setForeground(new java.awt.Color(204, 0, 51));
        errorMsg.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        errorMsg.setPreferredSize(new java.awt.Dimension(500, 30));
        jPanel4.add(errorMsg);

        jPanel3.add(jPanel4, java.awt.BorderLayout.SOUTH);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(600, 600));

        deliverablesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Course", "Deliverable ID", "Deliverable Name", "Due Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        deliverablesTable.setColorBackgoundHead(new java.awt.Color(204, 0, 51));
        deliverablesTable.setPreferredSize(new java.awt.Dimension(300, 400));
        jScrollPane1.setViewportView(deliverablesTable);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel3, java.awt.BorderLayout.WEST);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void addDeliverableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addDeliverableButtonActionPerformed
        DefaultTableModel deliverablesTableModel = (DefaultTableModel)deliverablesTable.getModel();
        int res = UniSync.addDeliverableDate(courseLabel.getText(), Integer.parseInt(deliverableIDLabel.getText()), dateLabel.getText());
        
        switch (res) {
            case 1:

                try{
                    PreparedStatement stmt = UniSync.conn.prepareStatement("SELECT * FROM deliverables INNER JOIN studentdeliverables ON deliverables.Code = studentdeliverables.Code AND deliverables.DeliverableNum = studentdeliverables.DeliverableNum WHERE DeliverableStatus = 1 AND StudentID = ? AND studentdeliverables.Code = ? AND studentdeliverables.DeliverableNum = ?");
                    
                    stmt.setInt(1, UniSync.getStudent().getStudentID());
                    stmt.setString(2, courseLabel.getText());
                    stmt.setInt(3, Integer.parseInt(deliverableIDLabel.getText()));
                    UniSync.result = stmt.executeQuery();
                    
                    while(UniSync.result.next()){
                        currentWeight = UniSync.result.getDouble("DeliverableWeight");
                        deliverablesTableModel.addRow(new Object[] {courseLabel.getText(), Integer.parseInt(deliverableIDLabel.getText()), UniSync.result.getString("DeliverableName"), dateLabel.getText() });
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }   break;
            case 2:
                errorMsg.setText("The date entered is invalid");
                break;
            case 3:
                errorMsg.setText("Deliverable has already been added");
                break;
            default:
                break;
        }
    }//GEN-LAST:event_addDeliverableButtonActionPerformed

    private void removeDeliverableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeDeliverableButtonActionPerformed
        DefaultTableModel deliverablesTableModel = (DefaultTableModel)deliverablesTable.getModel();
        int res = UniSync.removeDeliverableDate(courseLabel.getText(), Integer.parseInt(deliverableIDLabel.getText()));
        
        if (res == 1){
            int rowCount = deliverablesTableModel.getRowCount();

            for (int row = 0; row < rowCount; row++) {
                Object code = deliverablesTable.getValueAt(row, 0);
                Object num =  deliverablesTable.getValueAt(row, 1);
                if (code.equals(courseLabel.getText()) && num.equals(Integer.parseInt(deliverableIDLabel.getText()))) {
                    deliverablesTableModel.removeRow(row);
                    break;
                }
            }

            errorMsg.setText("Deliverable succesfully removed");
        }else if (res==2) {
            errorMsg.setText("Deliverable has not been added yet");
        }
    }//GEN-LAST:event_removeDeliverableButtonActionPerformed

    private void courseLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_courseLabelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_courseLabelActionPerformed

    private void deliverableIDLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deliverableIDLabelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deliverableIDLabelActionPerformed

    private void rSMaterialButtonRectangle2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle2ActionPerformed
        DashboardPage frame = new DashboardPage();
        frame.setVisible(true);
        dispose();
    }//GEN-LAST:event_rSMaterialButtonRectangle2ActionPerformed

    private void completedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_completedButtonActionPerformed
        DefaultTableModel deliverablesTableModel = (DefaultTableModel)deliverablesTable.getModel();
        int res = UniSync.completedDeliverableDate(courseLabel.getText(), Integer.parseInt(deliverableIDLabel.getText()));
        
        if (res == 1){
            int rowCount = deliverablesTableModel.getRowCount();
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
            LocalDateTime now = LocalDateTime.now();
            
            try{
                PreparedStatement pstmt = UniSync.conn.prepareStatement("UPDATE studentdeliverables SET CompletedDate=? "
                                                                        + "WHERE StudentID =? AND Code = ? AND DeliverableNum = ?");
                pstmt.setString(1, formatter.format(now));
                pstmt.setInt(2, UniSync.student.getStudentID());
                pstmt.setString(3, courseLabel.getText());
                pstmt.setInt(4, Integer.parseInt(deliverableIDLabel.getText()));
                pstmt.executeUpdate();
                
                
                
            }catch(Exception e){
                    e.printStackTrace();
                }
            

            for (int row = 0; row < rowCount; row++) {
                Object code = deliverablesTable.getValueAt(row, 0);
                Object num =  deliverablesTable.getValueAt(row, 1);
                if (code.equals(courseLabel.getText()) && num.equals(Integer.parseInt(deliverableIDLabel.getText()))) {
                    deliverablesTableModel.removeRow(row);
                    break;
                }
            }

            errorMsg.setText("Deliverable succesfully removed");
        }else if (res==2) {
            errorMsg.setText("Deliverable has not been added yet");
        }
    }//GEN-LAST:event_completedButtonActionPerformed

    private void dateLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateLabelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dateLabelActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DeliverablesPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DeliverablesPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DeliverablesPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DeliverablesPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DeliverablesPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSMaterialButtonRectangle addDeliverableButton;
    private rojerusan.RSMaterialButtonRectangle completedButton;
    private app.bolivia.swing.JCTextField courseLabel;
    private app.bolivia.swing.JCTextField dateLabel;
    private app.bolivia.swing.JCTextField deliverableIDLabel;
    private rojeru_san.complementos.RSTableMetro deliverablesTable;
    private javax.swing.JLabel errorMsg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle2;
    private rojerusan.RSMaterialButtonRectangle removeDeliverableButton;
    // End of variables declaration//GEN-END:variables
}
