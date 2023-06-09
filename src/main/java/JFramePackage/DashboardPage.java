/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package JFramePackage;

import MainPackage.UniSync;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.List;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.Collections;
import java.util.Comparator;


/**
 *
 * @author sebte
 */

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
public class DashboardPage extends javax.swing.JFrame {
    
    
    DecimalFormat decimalFormat = new DecimalFormat("#.##");
    /**
     * Creates new form DashboardPage
     */
    public DashboardPage() {
        initComponents();
        showLineChart();
        showBarChart();
        showPieChart();
        
        
        studentName.setText(UniSync.getStudent().getFirstName() + " " + UniSync.getStudent().getLastName());
        programLabel.setText(UniSync.getStudent().getProgram());
        
        UniSync.student.setGPA(UniSync.calcGPA());
        double gpa = Double.parseDouble(decimalFormat.format(UniSync.student.getGPA()));
        gpaLabel.setText(Double.toString(gpa));
        numCoursesLabel.setText(Integer.toString(UniSync.numCourses()));
        yearLabel.setText(Integer.toString(UniSync.student.getYear()));
    }
    
    public void showBarChart(){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        try {
            PreparedStatement stmt = UniSync.conn.prepareStatement("SELECT * FROM studentcourses where Status = 1 AND StudentID = ?");
            stmt.setInt(1, UniSync.getStudent().getStudentID());
            UniSync.result = stmt.executeQuery();

            while (UniSync.result.next()) {
                dataset.setValue(UniSync.result.getDouble("Grade"), "Grade", UniSync.result.getString("Code"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JFreeChart chart = ChartFactory.createBarChart("Courses","Course Code","Grade", 
                dataset, PlotOrientation.VERTICAL, false,true,false);
        
        CategoryPlot categoryPlot = chart.getCategoryPlot();
        categoryPlot.setRangeGridlinePaint(Color.BLUE);
        categoryPlot.setBackgroundPaint(Color.WHITE);
        BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
        Color clr = new Color(204,0,51);
        renderer.setSeriesPaint(0, clr);
        
        ChartPanel barpChartPanel = new ChartPanel(chart);
        barChartPanel.removeAll();
        barChartPanel.add(barpChartPanel, BorderLayout.CENTER);
        barChartPanel.validate();
        
        
    }
    
    public void showPieChart(){
        double completedWeightSum=0;
        double weightSum = UniSync.numCourses()*100;

        try{
            PreparedStatement pstmt = UniSync.conn.prepareStatement("SELECT * FROM deliverables INNER JOIN studentdeliverables ON deliverables.Code = studentdeliverables.Code AND deliverables.DeliverableNum = studentdeliverables.DeliverableNum WHERE DeliverableStatus = 2 AND StudentID = ?");
            pstmt.setInt(1, UniSync.getStudent().getStudentID());
            UniSync.result = pstmt.executeQuery();

            while (UniSync.result.next()) {
                completedWeightSum += UniSync.result.getDouble("DeliverableWeight");
            }
            
        }catch (Exception e) {
            e.printStackTrace();
        }
      
      //create dataset
        DefaultPieDataset barDataset = new DefaultPieDataset( );
        barDataset.setValue( "Completed" , completedWeightSum );  
        barDataset.setValue( "Not completed" , weightSum - completedWeightSum);    
      
      //create chart
        JFreeChart piechart = ChartFactory.createPieChart("Task Completion Status",barDataset, false,true,false);//explain

        PiePlot piePlot =(PiePlot) piechart.getPlot();

        //changing pie chart blocks colors
        piePlot.setSectionPaint("Completed", new Color(102,255,102));
        piePlot.setSectionPaint("Not completed", new Color(204,0,51));


         piePlot.setBackgroundPaint(Color.white);

         //create chartPanel to display chart(graph)
         ChartPanel barChartPanel = new ChartPanel(piechart);
         pieChartPanel.removeAll();
         pieChartPanel.add(barChartPanel, BorderLayout.CENTER);
         pieChartPanel.validate();
    }
    
    public void showLineChart(){
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        ArrayList<Pair> datesArr = new ArrayList<>();
        
        //create dataset for the graph
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        
        try{
            PreparedStatement pstmt = UniSync.conn.prepareStatement("SELECT * from studentdeliverables WHERE StudentID = ? AND DeliverableStatus=2");
            pstmt.setInt(1, UniSync.student.getStudentID());
            UniSync.result = pstmt.executeQuery();

            while(UniSync.result.next()){
                LocalDate dueDate = LocalDate.parse(UniSync.result.getString("DueDate"), formatter);
                LocalDate completedDate = LocalDate.parse(UniSync.result.getString("CompletedDate"), formatter);
                
                long daysBetween = ChronoUnit.DAYS.between(dueDate,completedDate );
                
                datesArr.add(new Pair(dueDate, daysBetween));
                
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        
        Collections.sort(datesArr, new Comparator<Pair>(){
            public int compare(Pair pair1, Pair pair2){
                return pair1.getDate().compareTo(pair2.getDate());
            }
        });
        
        for(Pair pair : datesArr){
            System.out.println(pair.getDate() + " " + pair.getValue());
            dataset.setValue(pair.getValue(), "Completion", "");
        }
      
        
        //create chart
        JFreeChart linechart = ChartFactory.createLineChart("Performance","Time","Completion", 
                dataset, PlotOrientation.VERTICAL, false,true,false);
        
        //create plot object
        CategoryPlot lineCategoryPlot = linechart.getCategoryPlot();
        // lineCategoryPlot.setRangeGridlinePaint(Color.BLUE);
        lineCategoryPlot.setBackgroundPaint(Color.white);
        
        //create render object to change the moficy the line properties like color
        LineAndShapeRenderer lineRenderer = (LineAndShapeRenderer) lineCategoryPlot.getRenderer();
        Color lineChartColor = new Color(204,0,51);
        lineRenderer.setSeriesPaint(0, lineChartColor);
        
        //create chartPanel to display chart(graph)
        ChartPanel lineChartPanel = new ChartPanel(linechart);
        panelLineChart.removeAll();
        panelLineChart.add(lineChartPanel, BorderLayout.CENTER);
        panelLineChart.validate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        paintList1 = new org.jfree.util.PaintList();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        yearLabel = new javax.swing.JLabel();
        barChartPanel = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        gpaLabel = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        numCoursesLabel = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        pieChartPanel = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        panelLineChart = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        studentName = new javax.swing.JLabel();
        programLabel = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1200, 800));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(1000, 800));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(600, 800));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel15.setBackground(java.awt.Color.white);
        jPanel15.setLayout(new java.awt.BorderLayout());

        jPanel18.setBackground(java.awt.Color.white);
        jPanel18.setPreferredSize(new java.awt.Dimension(200, 0));

        jLabel10.setText("Year");
        jLabel10.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 0, 51)));

        yearLabel.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        yearLabel.setText("Year");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(yearLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(yearLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );

        jPanel15.add(jPanel18, java.awt.BorderLayout.EAST);

        barChartPanel.setBackground(java.awt.Color.white);
        barChartPanel.setPreferredSize(new java.awt.Dimension(600, 300));
        barChartPanel.setLayout(new java.awt.BorderLayout());
        jPanel15.add(barChartPanel, java.awt.BorderLayout.PAGE_END);

        jPanel20.setBackground(java.awt.Color.white);
        jPanel20.setPreferredSize(new java.awt.Dimension(200, 432));

        jLabel4.setText("Current GPA");
        jLabel4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 0, 51)));

        gpaLabel.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        gpaLabel.setText("GPA");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gpaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gpaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );

        jPanel15.add(jPanel20, java.awt.BorderLayout.WEST);

        jPanel21.setBackground(java.awt.Color.white);

        jLabel9.setText("Number of Courses");
        jLabel9.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 0, 51)));

        numCoursesLabel.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        numCoursesLabel.setText("NumCourses");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(numCoursesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numCoursesLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel15.add(jPanel21, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel15, java.awt.BorderLayout.CENTER);

        jPanel17.setBackground(java.awt.Color.white);
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 1, 36)); // NOI18N
        jLabel3.setText("Dashboard");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel17.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jPanel2.add(jPanel17, java.awt.BorderLayout.PAGE_START);

        jPanel3.add(jPanel2, java.awt.BorderLayout.WEST);

        pieChartPanel.setBackground(java.awt.Color.white);
        pieChartPanel.setPreferredSize(new java.awt.Dimension(400, 800));
        pieChartPanel.setLayout(new java.awt.BorderLayout());
        jPanel3.add(pieChartPanel, java.awt.BorderLayout.EAST);

        jPanel16.setPreferredSize(new java.awt.Dimension(1000, 300));
        jPanel16.setLayout(new java.awt.BorderLayout());

        panelLineChart.setBackground(java.awt.Color.white);
        panelLineChart.setPreferredSize(new java.awt.Dimension(600, 300));

        javax.swing.GroupLayout panelLineChartLayout = new javax.swing.GroupLayout(panelLineChart);
        panelLineChart.setLayout(panelLineChartLayout);
        panelLineChartLayout.setHorizontalGroup(
            panelLineChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelLineChartLayout.setVerticalGroup(
            panelLineChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jPanel16.add(panelLineChart, java.awt.BorderLayout.CENTER);

        jPanel8.setPreferredSize(new java.awt.Dimension(400, 300));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jPanel16.add(jPanel8, java.awt.BorderLayout.EAST);

        jPanel3.add(jPanel16, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel3, java.awt.BorderLayout.EAST);

        jPanel1.setLayout(new java.awt.GridLayout(10, 0));

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 0, 51));
        jLabel1.setText("Uni");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, -1, 20));

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        jLabel2.setText("Sync");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 24, -1, -1));

        jPanel1.add(jPanel4);

        jPanel5.setLayout(new java.awt.BorderLayout());

        studentName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        studentName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        studentName.setText("Student Full Name");
        studentName.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        studentName.setPreferredSize(new java.awt.Dimension(97, 50));
        jPanel5.add(studentName, java.awt.BorderLayout.NORTH);

        programLabel.setForeground(new java.awt.Color(153, 153, 153));
        programLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        programLabel.setText("Student Program");
        programLabel.setPreferredSize(new java.awt.Dimension(91, 30));
        jPanel5.add(programLabel, java.awt.BorderLayout.SOUTH);

        jPanel1.add(jPanel5);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 238, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel6);

        jPanel7.setPreferredSize(new java.awt.Dimension(212, 160));
        jPanel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel7MouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 0, 51));
        jLabel5.setText("Dashboard");
        jPanel7.add(jLabel5);

        jPanel1.add(jPanel7);

        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel9MouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Courses");
        jPanel9.add(jLabel6);

        jPanel1.add(jPanel9);

        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel10MouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Deliverables");
        jPanel10.add(jLabel7);

        jPanel1.add(jPanel10);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 238, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel11);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 238, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel12);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 238, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel13);

        jPanel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel14MouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Log out");
        jPanel14.add(jLabel8);

        jPanel1.add(jPanel14);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
        
    }//GEN-LAST:event_jPanel7MouseClicked

    private void jPanel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel14MouseClicked
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to sign out?", "Sign Out Confirmation", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            LoginPage frame = new LoginPage();
            frame.setVisible(true);
            dispose();
            
        } 
    }//GEN-LAST:event_jPanel14MouseClicked

    private void jPanel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseClicked
        CoursesPage frame = new CoursesPage();
        frame.setVisible(true);
        dispose();
    }//GEN-LAST:event_jPanel9MouseClicked

    private void jPanel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseClicked
        DeliverablesPage frame = new DeliverablesPage();
        frame.setVisible(true);
        dispose();
    }//GEN-LAST:event_jPanel10MouseClicked

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
            java.util.logging.Logger.getLogger(DashboardPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DashboardPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DashboardPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DashboardPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DashboardPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel barChartPanel;
    private javax.swing.JLabel gpaLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel numCoursesLabel;
    private org.jfree.util.PaintList paintList1;
    private javax.swing.JPanel panelLineChart;
    private javax.swing.JPanel pieChartPanel;
    private javax.swing.JLabel programLabel;
    private javax.swing.JLabel studentName;
    private javax.swing.JLabel yearLabel;
    // End of variables declaration//GEN-END:variables
}
