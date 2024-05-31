package Scheduler;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;


public class CPU extends javax.swing.JFrame {
    final boolean[] isPlaceholder = {true};
    private int psNum = 0;
    private int TimeSlice = 0;
    ArrayList<Process> ps = new ArrayList<Process>();
    static HashMap<String, Double> result = new HashMap<String, Double>();
    static List<GanttChart> Gantts = new ArrayList<GanttChart>();
    static GanttChartPanel ganttchart;

    public CPU() {
        initComponents();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CPU().setVisible(true);
            }
        });

    }
    @SuppressWarnings("unchecked")
    private void initComponents() {

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );


        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jTable1 = new javax.swing.JTable();
        jTable1.setFont(new Font("굴림", Font.BOLD,16));
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jTable2.setFont(new Font("굴림", Font.BOLD,16));
        jProgressBar1 = new javax.swing.JProgressBar();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {},
                new String [] {
                        "Process Num", "Arrived Time", "CPU Burst", "Turnarround Time", "Waiting Time", "Response Time"
                }
        ) {
            Class[] types = new Class [] {
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.setDropMode(javax.swing.DropMode.INSERT_ROWS);
        jScrollPane1.setViewportView(jTable1);
        jTable1.setRowHeight(50);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {},
                new String [] {
                        "Execution Time", "Average Waiting Time", "Average Turnaround Time", "Throughput", "CPU Utilization"
                }
        ) {
            Class[] types = new Class [] {
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);
        jTable2.setRowHeight(65);

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        DefaultTableModel model2 = (DefaultTableModel) jTable2.getModel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton2.setFont(new java.awt.Font("맑은 고딕", 1, 18)); // NOI18N
        jButton2.setText("RUN");
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(model.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(new JFrame(), "process를 먼저 생성해주세요!", "경고", JOptionPane.WARNING_MESSAGE);
                }
                else{
                    if(model2.getRowCount() == 0){
                        model2.addRow(new Object[]{null,null,null,null});
                    }

                    String selectedAlgorithm = (String) jComboBox1.getSelectedItem();
                    switch(selectedAlgorithm) {
                        case "FCFS":
                            FCFS fcfs = new FCFS(ps);
                            fcfs.run();
                            for(int i = 0 ; i < ps.size() ; i++){
                                model.setValueAt(result.get("fcfsturnaroundtime"+i), i, 3);
                                model.setValueAt(result.get("fcfswaitingtime"+i), i, 4);
                                model.setValueAt(result.get("fcfsresponsetime"+i), i, 5);
                                model2.setValueAt(result.get("fcfsexecutiontime"), 0,0);
                                model2.setValueAt(result.get("fcfsavgwaitingtime"), 0,1);
                                model2.setValueAt(result.get("fcfsavgturnaroundtime"), 0,2);
                                model2.setValueAt(result.get("fcfsthroughput"), 0,3);
                                model2.setValueAt(result.get("fcfsutil")+"%", 0,4);
                            }
                            jTable1.setDefaultRenderer(Object.class, centerRenderer);
                            jTable2.setDefaultRenderer(Object.class, centerRenderer);
                            JFrame frame = new JFrame("Gantt Chart");
                            frame.add(new JScrollPane(ganttchart));
                            frame.pack();
                            frame.setLocationRelativeTo(null);
                            frame.setVisible(true);
                            break;
                        case "SJF":
                            SJF sjf = new SJF(ps);
                            sjf.run();
                            for(int i = 0 ; i < ps.size() ; i++){
                                model.setValueAt(result.get("sjfturnaroundtime"+i), i, 3);
                                model.setValueAt(result.get("sjfwaitingtime"+i), i, 4);
                                model.setValueAt(result.get("sjfresponsetime"+i), i, 5);
                                model2.setValueAt(result.get("sjfexecutiontime"), 0,0);
                                model2.setValueAt(result.get("sjfavgwaitingtime"), 0,1);
                                model2.setValueAt(result.get("sjfavgturnaroundtime"), 0,2);
                                model2.setValueAt(result.get("sjfthroughput"), 0,3);
                                model2.setValueAt(result.get("sjfutil")+"%", 0,4);
                            }
                            jTable1.setDefaultRenderer(Object.class, centerRenderer);
                            jTable2.setDefaultRenderer(Object.class, centerRenderer);
                            JFrame frame2 = new JFrame("Gantt Chart");
                            frame2.add(new JScrollPane(ganttchart));
                            frame2.pack();
                            frame2.setLocationRelativeTo(null);
                            frame2.setVisible(true);
                            break;

                        case "HRRN":
                            HRRN hrrn = new HRRN(ps);
                            hrrn.run();
                            for(int i = 0 ; i < ps.size() ; i++){
                                model.setValueAt(result.get("hrrnturnaroundtime"+i), i, 3);
                                model.setValueAt(result.get("hrrnwaitingtime"+i), i, 4);
                                model.setValueAt(result.get("hrrnresponsetime"+i), i, 5);
                                model2.setValueAt(result.get("hrrnexecutiontime"), 0,0);
                                model2.setValueAt(result.get("hrrnavgwaitingtime"), 0,1);
                                model2.setValueAt(result.get("hrrnavgturnaroundtime"), 0,2);
                                model2.setValueAt(result.get("hrrnthroughput"), 0,3);
                                model2.setValueAt(result.get("hrrnutil")+"%", 0,4);
                            }
                            jTable1.setDefaultRenderer(Object.class, centerRenderer);
                            jTable2.setDefaultRenderer(Object.class, centerRenderer);
                            JFrame frame3 = new JFrame("Gantt Chart");
                            frame3.add(new JScrollPane(ganttchart));
                            frame3.pack();
                            frame3.setLocationRelativeTo(null);
                            frame3.setVisible(true);
                            break;

                        case "Round Robin":
                            if(TimeSlice > 0) {
                                RoundRobin rr = new RoundRobin(ps,TimeSlice);
                                rr.run();
                                for(int i = 0 ; i < ps.size() ; i++){
                                    model.setValueAt(result.get("rrturnaroundtime"+i), i, 3);
                                    model.setValueAt(result.get("rrwaitingtime"+i), i, 4);
                                    model.setValueAt(result.get("rrresponsetime"+i), i, 5);
                                    model2.setValueAt(result.get("rrexecutiontime"), 0,0);
                                    model2.setValueAt(result.get("rravgwaitingtime"), 0,1);
                                    model2.setValueAt(result.get("rravgturnaroundtime"),0,2);
                                    model2.setValueAt(result.get("rrthroughput"), 0,3);
                                    model2.setValueAt(result.get("rrutil")+"%", 0,4);
                                }
                            }
                            jTable1.setDefaultRenderer(Object.class, centerRenderer);
                            jTable2.setDefaultRenderer(Object.class, centerRenderer);
                            JFrame frame4 = new JFrame("Gantt Chart");
                            frame4.add(new JScrollPane(ganttchart));
                            frame4.pack();
                            frame4.setLocationRelativeTo(null);
                            frame4.setVisible(true);
                            break;

                        case "New":
                            NEWHRRN nh = new NEWHRRN(ps);
                            nh.run();
                            for(int i = 0 ; i < ps.size() ; i++){
                                model.setValueAt(result.get("newturnaroundtime"+i), i, 3);
                                model.setValueAt(result.get("newwaitingtime"+i), i, 4);
                                model.setValueAt(result.get("newresponsetime"+i), i, 5);
                                model2.setValueAt(result.get("newexecutiontime"), 0,0);
                                model2.setValueAt(result.get("newavgwaitingtime"), 0,1);
                                model2.setValueAt(result.get("newavgturnaroundtime"), 0,2);
                                model2.setValueAt(result.get("newthroughput"), 0,3);
                                model2.setValueAt(result.get("newutil")+"%", 0,4);
                            }
                            jTable1.setDefaultRenderer(Object.class, centerRenderer);
                            jTable2.setDefaultRenderer(Object.class, centerRenderer);
                            JFrame frame5 = new JFrame("Gantt Chart");
                            frame5.add(new JScrollPane(ganttchart));
                            frame5.pack();
                            frame5.setLocationRelativeTo(null);
                            frame5.setVisible(true);

                    }
                }
                }

            });

        jComboBox1.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FCFS", "SJF", "HRRN", "Round Robin" , "New"}));




        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("Arrived Time");
        jTextField1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (jTextField1.getText().equals("Arrived Time")) {
                    jTextField1.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (jTextField1.getText().isEmpty()) {
                    jTextField1.setText("Arrived Time");
                }
            }
        });



        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2.setText("CPU Burst");
        jTextField2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (jTextField2.getText().equals("CPU Burst")) {
                    jTextField2.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (jTextField2.getText().isEmpty()) {
                    jTextField2.setText("CPU Burst");
                }
            }
        });


        jButton1.setText("ADD");
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String value1 = jTextField1.getText();
                String value2 = jTextField2.getText();
                if(value1.compareTo("Arrived Time") == 0 ||  value2.compareTo("CPU Burst") == 0 || value1.contains(" ") || value2.contains(" ") ) {
                    JOptionPane.showMessageDialog(new JFrame(), "Arrived Time과 CPU Burst를 모두 정확하게 입력해주세요", "경고", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    model.addRow(new Object[] {psNum+1,value1,value2,null,null,null});
                    jTable1.setDefaultRenderer(Object.class, centerRenderer);
                    ps.add(new Process(psNum++, Integer.parseInt(value2), Integer.parseInt(value1)));
                    jTextField1.setText("Arrived Time");
                    jTextField2.setText("CPU Burst");
                }

            }
        });
        jButton3.setText("DELETE ALL");
        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setRowCount(0);
                model2.setRowCount(0);
                psNum=0;
                ps.clear();
            }
        });


        jLabel1.setText("Time Slice is");


        jProgressBar1.setBackground(new java.awt.Color(255, 255, 255));
        jProgressBar1.setForeground(new java.awt.Color(153, 255, 255));
        jProgressBar1.setToolTipText("");
        jProgressBar1.setValue(25);
        jProgressBar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jProgressBar1.setOpaque(true);

        JComboBox comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"}));
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // 선택된 항목이 변경되었을 때 처리
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    TimeSlice = Integer.parseInt((String)comboBox.getSelectedItem());
                }
            }
        });


        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(11)
                                                .addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING)
                                                                .addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING, false)
                                                                        .addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jButton2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                .addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(jTextField2, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(jButton3))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(10)
                                                                .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                .addGap(38)
                                .addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(jProgressBar1, GroupLayout.DEFAULT_SIZE, 977, Short.MAX_VALUE)
                                        .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 977, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 977, Short.MAX_VALUE))
                                .addGap(48))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(16)
                                .addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18)
                                                .addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(jTextField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addComponent(jButton1)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(jButton3)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 450, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
                                .addGap(75)
                                .addComponent(jProgressBar1, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(81, Short.MAX_VALUE))
        );
        jPanel1.setLayout(jPanel1Layout);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }


    // Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
}
