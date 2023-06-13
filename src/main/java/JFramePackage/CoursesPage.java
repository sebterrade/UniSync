/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package JFramePackage;

import MainPackage.UniSync;
import java.awt.event.ItemEvent;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author sebte
 */
public class CoursesPage extends javax.swing.JFrame {

    DefaultTableModel model;
    DecimalFormat decimalFormat = new DecimalFormat("#.##");
    /**
     * Creates new form CoursesPage
     */
    public CoursesPage() {
        initComponents();
        
        //Load Courses table
        model = (DefaultTableModel) rSTableMetro2.getModel();
        try {
            PreparedStatement stmt = UniSync.conn.prepareStatement("SELECT * FROM courses INNER JOIN studentcourses USING (Code) WHERE Status = 1 AND StudentID = ?");
            stmt.setInt(1, UniSync.getStudent().getStudentID());
            UniSync.result = stmt.executeQuery();

            while (UniSync.result.next()) {
                model.insertRow(model.getRowCount(), new Object[] {
                        UniSync.result.getString("Code"),
                        UniSync.result.getDouble("Credits"),
                        Double.parseDouble(decimalFormat.format(UniSync.result.getDouble("Grade"))),
                        UniSync.result.getDouble("CourseAverage")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        
        //Load Combo Box options
        DefaultComboBoxModel modelCombo = (DefaultComboBoxModel) rSComboMetro1.getModel();
        try {
            PreparedStatement stmt = UniSync.conn.prepareStatement("SELECT * FROM courses INNER JOIN studentcourses USING (Code) WHERE Status = 1 AND StudentID = ?");
            stmt.setInt(1, UniSync.getStudent().getStudentID());
            UniSync.result = stmt.executeQuery();

            while (UniSync.result.next()) {
                modelCombo.addElement(UniSync.result.getString("Code"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        Double courseAvg = UniSync.calculateCourseGrade((String)modelCombo.getSelectedItem());
        jLabel4.setText("Current Grade in "+ (String)modelCombo.getSelectedItem());
        
        courseAvg = Double.parseDouble(decimalFormat.format(courseAvg));
        courseGradeLabel.setText(Double.toString(courseAvg)+ "%");
        
        double gpa = UniSync.calcGPA();
        gpa = Double.parseDouble(decimalFormat.format(gpa));
        gpaLabel.setText(Double.toString(gpa));
        
        courseGradeLabel1.setText(" ");
        jLabel8.setText(" ");
        jLabel9.setText(" ");
        
        new ComponentResizer(this);
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
        rSMaterialButtonRectangle1 = new rojerusan.RSMaterialButtonRectangle();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        txt_enterCourse = new app.bolivia.swing.JCTextField();
        addCourseButton = new rojerusan.RSMaterialButtonRectangle();
        deleteCourseButton = new rojerusan.RSMaterialButtonRectangle();
        jPanel10 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        rSTableMetro2 = new rojeru_san.complementos.RSTableMetro();
        errorMsg = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        gpaLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        courseGradeLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        deliverablesTable = new rojeru_san.complementos.RSTableMetro();
        errorMsg2 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        rSComboMetro1 = new rojerusan.RSComboMetro();
        jPanel17 = new javax.swing.JPanel();
        txt_enterDelivNum = new app.bolivia.swing.JCTextField();
        txt_enterGrade = new app.bolivia.swing.JCTextField();
        modifyGradeButton = new rojerusan.RSMaterialButtonRectangle();
        jPanel20 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        desiredGradeLabel = new app.bolivia.swing.JCTextField();
        calculateButton = new rojerusan.RSMaterialButtonRectangle();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        courseGradeLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.BorderLayout(0, 15));

        jPanel1.setPreferredSize(new java.awt.Dimension(1200, 75));

        jLabel1.setFont(new java.awt.Font("Segoe UI Symbol", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Grades");
        jPanel1.add(jLabel1);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel2.setPreferredSize(new java.awt.Dimension(1200, 100));

        rSMaterialButtonRectangle1.setBackground(new java.awt.Color(204, 0, 51));
        rSMaterialButtonRectangle1.setText("Done");
        rSMaterialButtonRectangle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle1ActionPerformed(evt);
            }
        });
        jPanel2.add(rSMaterialButtonRectangle1);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        jPanel3.setPreferredSize(new java.awt.Dimension(500, 580));
        jPanel3.setLayout(new java.awt.BorderLayout(0, 25));

        jPanel5.setLayout(new java.awt.BorderLayout(0, 25));

        jPanel9.setPreferredSize(new java.awt.Dimension(600, 75));

        txt_enterCourse.setPlaceholder("Enter Course... (e.g APSC100)");
        txt_enterCourse.setPreferredSize(new java.awt.Dimension(170, 32));
        txt_enterCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_enterCourseActionPerformed(evt);
            }
        });
        jPanel9.add(txt_enterCourse);

        addCourseButton.setBackground(new java.awt.Color(204, 0, 51));
        addCourseButton.setText("Add Course");
        addCourseButton.setPreferredSize(new java.awt.Dimension(150, 40));
        addCourseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCourseButtonActionPerformed(evt);
            }
        });
        jPanel9.add(addCourseButton);

        deleteCourseButton.setBackground(new java.awt.Color(204, 0, 51));
        deleteCourseButton.setText("Delete Course");
        deleteCourseButton.setPreferredSize(new java.awt.Dimension(150, 40));
        deleteCourseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteCourseButtonActionPerformed(evt);
            }
        });
        jPanel9.add(deleteCourseButton);

        jPanel5.add(jPanel9, java.awt.BorderLayout.SOUTH);

        jLabel2.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        jLabel2.setText("Enter Or Modify Your Courses Here");
        jPanel10.add(jLabel2);

        rSTableMetro2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Courses", "Credits", "Grade", "Average"
            }
        ));
        rSTableMetro2.setColorBackgoundHead(new java.awt.Color(204, 0, 51));
        rSTableMetro2.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        rSTableMetro2.setColorFilasForeground1(new java.awt.Color(0, 0, 0));
        rSTableMetro2.setColorFilasForeground2(new java.awt.Color(0, 0, 0));
        rSTableMetro2.setColorSelBackgound(new java.awt.Color(204, 0, 51));
        jScrollPane2.setViewportView(rSTableMetro2);

        jPanel10.add(jScrollPane2);
        jPanel10.add(errorMsg);

        jPanel5.add(jPanel10, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel6.setPreferredSize(new java.awt.Dimension(600, 150));
        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 50, 5));

        jPanel7.setBackground(new java.awt.Color(204, 0, 51));

        gpaLabel.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        gpaLabel.setForeground(new java.awt.Color(255, 255, 255));

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Current GPA");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gpaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(gpaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addContainerGap())
        );

        jPanel6.add(jPanel7);

        jPanel8.setBackground(new java.awt.Color(204, 0, 51));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Current Grade");

        courseGradeLabel.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        courseGradeLabel.setForeground(new java.awt.Color(255, 255, 255));
        courseGradeLabel.setText("jLabel5");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(courseGradeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 1, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(courseGradeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addContainerGap())
        );

        jPanel6.add(jPanel8);

        jPanel3.add(jPanel6, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel3, java.awt.BorderLayout.WEST);

        jPanel4.setPreferredSize(new java.awt.Dimension(700, 580));
        jPanel4.setLayout(new java.awt.BorderLayout(0, 25));

        jPanel11.setLayout(new java.awt.BorderLayout(15, 25));

        jPanel15.setPreferredSize(new java.awt.Dimension(400, 300));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        jLabel3.setText("Modify Your Deliverables Here");
        jPanel15.add(jLabel3);

        deliverablesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Number", "Name", "Grade", "Weight"
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
        deliverablesTable.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        deliverablesTable.setColorFilasForeground1(new java.awt.Color(0, 0, 0));
        deliverablesTable.setColorFilasForeground2(new java.awt.Color(0, 0, 0));
        deliverablesTable.setColorSelBackgound(new java.awt.Color(204, 0, 51));
        deliverablesTable.setPreferredScrollableViewportSize(new java.awt.Dimension(450, 300));
        deliverablesTable.setPreferredSize(new java.awt.Dimension(400, 300));
        jScrollPane1.setViewportView(deliverablesTable);

        jPanel15.add(jScrollPane1);
        jPanel15.add(errorMsg2);

        jPanel11.add(jPanel15, java.awt.BorderLayout.CENTER);

        jPanel16.setPreferredSize(new java.awt.Dimension(200, 375));

        rSComboMetro1.setToolTipText("");
        rSComboMetro1.setColorArrow(new java.awt.Color(204, 0, 51));
        rSComboMetro1.setColorBorde(new java.awt.Color(204, 0, 51));
        rSComboMetro1.setColorFondo(new java.awt.Color(204, 0, 51));
        rSComboMetro1.setPreferredSize(new java.awt.Dimension(175, 32));
        rSComboMetro1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rSComboMetro1ItemStateChanged(evt);
            }
        });
        rSComboMetro1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSComboMetro1ActionPerformed(evt);
            }
        });
        jPanel16.add(rSComboMetro1);

        jPanel11.add(jPanel16, java.awt.BorderLayout.EAST);

        jPanel17.setPreferredSize(new java.awt.Dimension(700, 75));

        txt_enterDelivNum.setPlaceholder("Enter Deliverable Number");
        txt_enterDelivNum.setPreferredSize(new java.awt.Dimension(170, 32));
        txt_enterDelivNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_enterDelivNumActionPerformed(evt);
            }
        });
        jPanel17.add(txt_enterDelivNum);

        txt_enterGrade.setPlaceholder("Enter Grade Obtained...");
        txt_enterGrade.setPreferredSize(new java.awt.Dimension(170, 32));
        txt_enterGrade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_enterGradeActionPerformed(evt);
            }
        });
        jPanel17.add(txt_enterGrade);

        modifyGradeButton.setBackground(new java.awt.Color(204, 0, 51));
        modifyGradeButton.setText("Modify Grade");
        modifyGradeButton.setActionCommand("");
        modifyGradeButton.setPreferredSize(new java.awt.Dimension(150, 40));
        modifyGradeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyGradeButtonActionPerformed(evt);
            }
        });
        jPanel17.add(modifyGradeButton);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        jPanel17.add(jPanel20);

        jPanel11.add(jPanel17, java.awt.BorderLayout.PAGE_END);

        jPanel4.add(jPanel11, java.awt.BorderLayout.CENTER);

        jPanel12.setPreferredSize(new java.awt.Dimension(600, 150));
        jPanel12.setLayout(new java.awt.BorderLayout());

        jPanel14.setPreferredSize(new java.awt.Dimension(250, 100));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        jLabel5.setText("Grade Calculator");
        jPanel14.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel7.setText("What is your desired grade");
        jPanel14.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, -1, -1));

        desiredGradeLabel.setPlaceholder("Enter desired grade ...");
        jPanel14.add(desiredGradeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, -1, -1));

        calculateButton.setBackground(new java.awt.Color(204, 0, 51));
        calculateButton.setText("Calculate");
        calculateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateButtonActionPerformed(evt);
            }
        });
        jPanel14.add(calculateButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 210, 50));

        jPanel12.add(jPanel14, java.awt.BorderLayout.WEST);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        jPanel12.add(jPanel18, java.awt.BorderLayout.EAST);

        jPanel19.setBackground(new java.awt.Color(204, 0, 51));
        jPanel19.setMinimumSize(new java.awt.Dimension(215, 97));

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("to get a ... in the course");

        courseGradeLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        courseGradeLabel1.setForeground(new java.awt.Color(255, 255, 255));
        courseGradeLabel1.setText("jLabel5");

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("You need a ");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(courseGradeLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 64, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addGap(3, 3, 3)
                .addComponent(courseGradeLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addContainerGap())
        );

        jPanel12.add(jPanel19, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel12, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel4, java.awt.BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void rSMaterialButtonRectangle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle1ActionPerformed
        DashboardPage frame = new DashboardPage();
        frame.setVisible(true);
        dispose();
    }//GEN-LAST:event_rSMaterialButtonRectangle1ActionPerformed

    private void txt_enterCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_enterCourseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_enterCourseActionPerformed

    private void addCourseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCourseButtonActionPerformed
        String codeEntered = txt_enterCourse.getText();
        try{
            DefaultTableModel model = (DefaultTableModel) rSTableMetro2.getModel();
            DefaultComboBoxModel modelCombo = (DefaultComboBoxModel) rSComboMetro1.getModel();

            int res = UniSync.addCourses(UniSync.getStudent().getStudentID(), codeEntered);

            switch (res) {
                case 1:
                errorMsg.setText("Course has already been added");
                break;
                case 2:
                PreparedStatement stmt = UniSync.conn.prepareStatement("SELECT * FROM courses INNER JOIN studentcourses USING (Code) WHERE Status = 1 AND StudentID = ? AND Code = ?");
                stmt.setInt(1, UniSync.getStudent().getStudentID());
                stmt.setString(2, codeEntered);
                UniSync.result = stmt.executeQuery();

                while (UniSync.result.next()) {
                    model.addRow(new Object[] {UniSync.result.getString("Code"), UniSync.result.getDouble("Credits"), UniSync.result.getDouble("Grade"), UniSync.result.getDouble("CourseAverage") });
                    modelCombo.addElement(UniSync.result.getString("Code"));
                }
                errorMsg.setText("Course succesfully added");
                double gpa = UniSync.calcGPA();
                gpa = Double.parseDouble(decimalFormat.format(gpa));
                gpaLabel.setText(Double.toString(gpa));
                break;
                default:
                errorMsg.setText("Course does not exist");
                break;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }//GEN-LAST:event_addCourseButtonActionPerformed

    private void deleteCourseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteCourseButtonActionPerformed
        String codeEntered = txt_enterCourse.getText();
        try{
            int res = UniSync.deleteCourses(UniSync.getStudent().getStudentID(), codeEntered);

            if (res == 1){
                DefaultComboBoxModel modelCombo = (DefaultComboBoxModel) rSComboMetro1.getModel();
                DefaultTableModel model = (DefaultTableModel) rSTableMetro2.getModel();
                int rowCount = model.getRowCount();

                for (int row = 0; row < rowCount; row++) {
                    Object name = rSTableMetro2.getValueAt(row, 0);
                    if (name.equals(codeEntered)) {
                        model.removeRow(row);
                        modelCombo.removeElement(codeEntered);
                        break;
                    }
                }

                errorMsg.setText("Course succesfully deleted");
            }else {
                errorMsg.setText("You're not enrolled in this course");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_deleteCourseButtonActionPerformed

    private void calculateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculateButtonActionPerformed
        DefaultComboBoxModel modelCombo = (DefaultComboBoxModel) rSComboMetro1.getModel();

        String courseSelected = (String)modelCombo.getSelectedItem();
        double desiredGrade = Double.parseDouble(desiredGradeLabel.getText());

        double reqGrade = UniSync.requiredGrade(desiredGrade, courseSelected);
        reqGrade = Double.parseDouble(decimalFormat.format(reqGrade));

        courseGradeLabel1.setText(reqGrade + "%");
        jLabel8.setText("to get a "+ desiredGrade + " in "+ courseSelected);
        jLabel9.setText("On your remaining tasks, you need a");

    }//GEN-LAST:event_calculateButtonActionPerformed
    private boolean isUpdating = false;
    private void rSComboMetro1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rSComboMetro1ItemStateChanged

        DefaultTableModel model = (DefaultTableModel) deliverablesTable.getModel();

        if (evt.getStateChange() == ItemEvent.SELECTED && !isUpdating) {
            isUpdating = true;

            JComboBox<String> source = (JComboBox<String>) evt.getSource();
            String selectedItem = (String) source.getSelectedItem();
            model.setRowCount(0);

            try {
                PreparedStatement stmt = UniSync.conn.prepareStatement("SELECT * FROM deliverables INNER JOIN studentdeliverables ON deliverables.Code = studentdeliverables.Code AND deliverables.DeliverableNum = studentdeliverables.DeliverableNum WHERE studentdeliverables.StudentID = ? AND deliverables.Code = ?");
                stmt.setInt(1, UniSync.getStudent().getStudentID());
                stmt.setString(2, selectedItem);
                UniSync.result = stmt.executeQuery();

                while (UniSync.result.next()) {
                    model.addRow(new Object[] { UniSync.result.getInt("DeliverableNum"), UniSync.result.getString("DeliverableName"), UniSync.result.getDouble("DeliverableGrade"), UniSync.result.getDouble("DeliverableWeight") });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            DefaultComboBoxModel modelCombo = (DefaultComboBoxModel) rSComboMetro1.getModel();
            try {
                modelCombo.removeAllElements();

                PreparedStatement stmt = UniSync.conn.prepareStatement("SELECT * FROM courses INNER JOIN studentcourses USING (Code) WHERE Status = 1 AND StudentID = ?");
                stmt.setInt(1, UniSync.getStudent().getStudentID());
                UniSync.result = stmt.executeQuery();

                while (UniSync.result.next()) {
                    modelCombo.addElement(UniSync.result.getString("Code"));
                }
                modelCombo.setSelectedItem(selectedItem);
            } catch (Exception e) {
                e.printStackTrace();
            }

            isUpdating = false;

            Double courseAvg = UniSync.calculateCourseGrade((String)modelCombo.getSelectedItem());
            jLabel4.setText("Current Grade in "+ (String)modelCombo.getSelectedItem());
            courseAvg = Double.parseDouble(decimalFormat.format(courseAvg));
            courseGradeLabel.setText(Double.toString(courseAvg)+ "%");

            jLabel7.setText("What is your desired grade in "+ selectedItem);
            courseGradeLabel1.setText("");
            jLabel8.setText("");
            jLabel9.setText("");
        }

    }//GEN-LAST:event_rSComboMetro1ItemStateChanged

    private void rSComboMetro1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSComboMetro1ActionPerformed

    }//GEN-LAST:event_rSComboMetro1ActionPerformed

    private void txt_enterDelivNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_enterDelivNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_enterDelivNumActionPerformed

    private void txt_enterGradeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_enterGradeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_enterGradeActionPerformed

    private void modifyGradeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyGradeButtonActionPerformed
        DefaultTableModel modelDelivTable = (DefaultTableModel) deliverablesTable.getModel();
        DefaultTableModel modelCourseTable = (DefaultTableModel) rSTableMetro2.getModel();
        DefaultComboBoxModel modelCombo = (DefaultComboBoxModel) rSComboMetro1.getModel();

        String courseSelected = (String)modelCombo.getSelectedItem();
        int numEntered = Integer.parseInt(txt_enterDelivNum.getText());
        Double gradeEntered = Double.valueOf(txt_enterGrade.getText());

        try{

            int res = UniSync.updateGrade(numEntered, gradeEntered, courseSelected);

            switch (res) {
                case 1:
                modelDelivTable.setValueAt(gradeEntered, numEntered-1, 2);

                Double courseAvg = UniSync.calculateCourseGrade(courseSelected);

                int rowCount = modelCourseTable.getRowCount();

                for (int row = 0; row < rowCount; row++) {
                    Object course = rSTableMetro2.getValueAt(row, 0);
                    if (course.equals(courseSelected)) {
                        modelCourseTable.setValueAt(courseAvg, row, 2);
                        break;
                    }
                }
                jLabel4.setText("Current Grade in "+ courseSelected);
                courseAvg = Double.parseDouble(decimalFormat.format(courseAvg));

                courseGradeLabel.setText(Double.toString(courseAvg)+ "%");

                double gpa = UniSync.calcGPA();
                gpa = Double.parseDouble(decimalFormat.format(gpa));
                gpaLabel.setText(Double.toString(gpa));
                break;

                default:
                errorMsg2.setText("Deliverable does not exist");
                break;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_modifyGradeButtonActionPerformed

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
            java.util.logging.Logger.getLogger(CoursesPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CoursesPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CoursesPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CoursesPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CoursesPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSMaterialButtonRectangle addCourseButton;
    private rojerusan.RSMaterialButtonRectangle calculateButton;
    private javax.swing.JLabel courseGradeLabel;
    private javax.swing.JLabel courseGradeLabel1;
    private rojerusan.RSMaterialButtonRectangle deleteCourseButton;
    private rojeru_san.complementos.RSTableMetro deliverablesTable;
    private app.bolivia.swing.JCTextField desiredGradeLabel;
    private javax.swing.JLabel errorMsg;
    private javax.swing.JLabel errorMsg2;
    private javax.swing.JLabel gpaLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private rojerusan.RSMaterialButtonRectangle modifyGradeButton;
    private rojerusan.RSComboMetro rSComboMetro1;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle1;
    private rojeru_san.complementos.RSTableMetro rSTableMetro2;
    private app.bolivia.swing.JCTextField txt_enterCourse;
    private app.bolivia.swing.JCTextField txt_enterDelivNum;
    private app.bolivia.swing.JCTextField txt_enterGrade;
    // End of variables declaration//GEN-END:variables
}
