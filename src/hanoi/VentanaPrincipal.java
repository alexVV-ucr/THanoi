/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hanoi;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Estudiante
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    private final int FICHAS = 10;
    private int conNumMovi = 0;
    private int objetivo = 0;
    private double numMinMovi = 0;

    private boolean detener = false;

    Pila PilaTA;
    Pila PilaTB;
    Pila PilaTC;

    /*
    * Este default castea cada tabla que serian las torres
     */
    DefaultTableModel modelTablaTorreA, modelTablaTorreB, modelTablaTorreC;

    /**
     * Creates new form VentanaPrincipal
     */
    public VentanaPrincipal() {
        initComponents();

        /*
        * Para poder utilizar lpos Jtable se 
        * Castea cada torre y la crea con el default de table de swing
        * y centralizar los fichas en la tabla 
         */
        modelTablaTorreA = (DefaultTableModel) TorreA.getModel();
        modelTablaTorreA.setRowCount(FICHAS);

        modelTablaTorreB = (DefaultTableModel) TorreB.getModel();
        modelTablaTorreB.setRowCount(FICHAS);

        modelTablaTorreC = (DefaultTableModel) TorreC.getModel();
        modelTablaTorreB.setRowCount(FICHAS);

        DefaultTableCellRenderer rederA = new DefaultTableCellRenderer();
        rederA.setHorizontalAlignment(SwingConstants.CENTER);
        TorreA.getColumnModel().getColumn(0).setCellRenderer(rederA);

        DefaultTableCellRenderer rederB = new DefaultTableCellRenderer();
        rederB.setHorizontalAlignment(SwingConstants.CENTER);
        TorreB.getColumnModel().getColumn(0).setCellRenderer(rederB);

        DefaultTableCellRenderer rederC = new DefaultTableCellRenderer();
        rederC.setHorizontalAlignment(SwingConstants.CENTER);
        TorreC.getColumnModel().getColumn(0).setCellRenderer(rederC);
    }

    /*
    * Restear los moviminetos 
     */
    private void Limpiar() {

        conNumMovi = 0;
        numMinMovi = 0;

        cb_numDiscos.setSelectedItem(String.valueOf(objetivo));

    }

    private void presentarCantMovi() {
        conNumMovi++;
        lbl_Nmovi.setText(String.valueOf(conNumMovi));
    }

    private void Reiniciar() throws MyException {
        try {
            if (!l_minMov.getText().equals("")) {
                Limpiar();
                Iniciar();
            }
        } catch (Exception e) {
            throw new MyException("No se pudo reiniciar");
        }
    }

    private void Iniciar() throws MyException {

        PilaTA = new Pila();
        PilaTB = new Pila();
        PilaTC = new Pila();

        try {
            objetivo = Integer.parseInt(cb_numDiscos.getSelectedItem().toString());

            numMinMovi = Math.pow(2, objetivo - 1);

            lbl_Nmovi.setText(String.valueOf(conNumMovi));
            l_minMov.setText(String.valueOf(String.format("%.0f", numMinMovi)));;

            for (int i = objetivo; i >= 1; i--) {

                Nodo plataform = new Nodo();

                String disco = "";

                /*
            * Dibuja la ficha
                 */
                for (int j = i; j > 0; j--) {

                    disco += "͟͟";
                }

                plataform.setDato(disco);

                PilaTA.Push(plataform);
            }
            presentarTorreA();
            presentarTorreB();
            presentarTorreC();

        } catch (Exception e) {
            throw new MyException("No se pudo iniciar");
        }
    }

    private void presentarTorreA() {
        ((DefaultTableModel) TorreA.getModel()).setRowCount(0);

        modelTablaTorreA.setRowCount(FICHAS);

        Nodo n;

        int rowDisco = (FICHAS - PilaTA.getContNodo());

        if (PilaTA.getContNodo() > 0) {
            for (n = PilaTA.getCabeza(); n.getAtras() != null; n = n.getAtras()) {
                String[] vector = {n.getDato()};
                modelTablaTorreA.insertRow(rowDisco, vector);
                rowDisco++;
            }
            if (n.getAtras() == null) {
                String[] vector = {n.getDato()};
                modelTablaTorreA.insertRow(rowDisco, vector);
            }
        }
        TorreA.setModel(modelTablaTorreA);
        modelTablaTorreA.setRowCount(FICHAS);
    }

    private void presentarTorreB() {
        ((DefaultTableModel) TorreB.getModel()).setRowCount(0);

        modelTablaTorreB.setRowCount(FICHAS);

        Nodo n;
        int rowDisco = (FICHAS - PilaTB.getContNodo());

        if (PilaTB.getContNodo() > 0) {
            for (n = PilaTB.getCabeza(); n.getAtras() != null; n = n.getAtras()) {
                String[] vector = {n.getDato()};
                modelTablaTorreB.insertRow(rowDisco, vector);
                rowDisco++;
            }
            if (n.getAtras() == null) {
                String[] vector = {n.getDato()};
                modelTablaTorreB.insertRow(rowDisco, vector);
            }
        }
        TorreB.setModel(modelTablaTorreB);
        modelTablaTorreB.setRowCount(FICHAS);
    }

    private void presentarTorreC() {
        ((DefaultTableModel) TorreC.getModel()).setRowCount(0);

        modelTablaTorreC.setRowCount(FICHAS);

        Nodo n;
        int rowDisco = (FICHAS - PilaTC.getContNodo());

        if (PilaTC.getContNodo() > 0) {
            for (n = PilaTC.getCabeza(); n.getAtras() != null; n = n.getAtras()) {
                String[] vector = {n.getDato()};
                modelTablaTorreC.insertRow(rowDisco, vector);
                rowDisco++;
            }
            if (n.getAtras() == null) {
                String[] vector = {n.getDato()};
                modelTablaTorreC.insertRow(rowDisco, vector);
            }
        }
        TorreC.setModel(modelTablaTorreC);
        modelTablaTorreC.setRowCount(FICHAS);
    }

    private void MoverAaB() throws MyException {

        try {
            if (PilaTA.getContNodo() > 0) {
                Nodo plataform = new Nodo();
                plataform.setDato(PilaTA.Peek());

                if (PilaTB.getContNodo() > 0) {
                    //Reglas de hanoi encontrar sinficha es mayor 
                    //que la qie va a ingresar
                    if (plataform.getDato().compareTo(PilaTB.Peek()) > 0) {
                        return;
                    }
                }

                PilaTA.Pop();
                PilaTB.Push(plataform);

                presentarTorreA();
                presentarTorreB();
                presentarCantMovi();
            }
        } catch (Exception e) {
            throw new MyException("No se pudo mover la ficha de torre");
        }
    }

    private void MoverAaC() throws MyException {

        try {
            if (PilaTA.getContNodo() > 0) {
                Nodo plataform = new Nodo();
                plataform.setDato(PilaTA.Peek());

                if (PilaTC.getContNodo() > 0) {
                    //Reglas de hanoi encontrar sinficha es mayor 
                    //que la qie va a ingresar
                    if (plataform.getDato().compareTo(PilaTC.Peek()) > 0) {
                        return;
                    }
                }

                PilaTA.Pop();
                PilaTC.Push(plataform);

                presentarTorreA();
                presentarTorreC();
                presentarCantMovi();

                if (PilaTC.getContNodo() == objetivo && conNumMovi == numMinMovi) {
                    JOptionPane.showMessageDialog(null, "Felicidades has logrado el minimo de movimientos\n\n "
                            + "                             Intenta otro nivel");
                } else if (PilaTC.getContNodo() == objetivo && conNumMovi != numMinMovi) {
                    JOptionPane.showMessageDialog(null, "Felicidades lo has logrado\n\n "
                            + "                             Intenta otro nivel");
                }

            }
        } catch (Exception e) {
            throw new MyException("No se pudo mover la ficha de torre");
        }
    }

    private void MoverBaA() throws MyException {

        try {
            if (PilaTB.getContNodo() > 0) {
                Nodo plataform = new Nodo();
                plataform.setDato(PilaTB.Peek());

                if (PilaTA.getContNodo() > 0) {
                    //Reglas de hanoi encontrar sinficha es mayor 
                    //que la qie va a ingresar
                    if (plataform.getDato().compareTo(PilaTA.Peek()) > 0) {
                        return;
                    }
                }

                PilaTB.Pop();
                PilaTA.Push(plataform);

                presentarTorreA();
                presentarTorreB();
                presentarCantMovi();
            }
        } catch (Exception e) {
            throw new MyException("No se pudo mover la ficha de torre");
        }
    }

    private void MoverBaC() throws MyException {

        try {
            if (PilaTB.getContNodo() > 0) {
                Nodo plataform = new Nodo();
                plataform.setDato(PilaTB.Peek());

                if (PilaTC.getContNodo() > 0) {
                    //Reglas de hanoi encontrar sinficha es mayor 
                    //que la qie va a ingresar
                    if (plataform.getDato().compareTo(PilaTC.Peek()) > 0) {
                        return;
                    }
                }

                PilaTB.Pop();
                PilaTC.Push(plataform);

                presentarTorreB();
                presentarTorreC();
                presentarCantMovi();

                if (PilaTC.getContNodo() == objetivo && conNumMovi == numMinMovi) {
                    JOptionPane.showMessageDialog(null, "Felicidades has logrado el minimo de movimientos\n\n "
                            + "                             Intenta otro nivel");
                } else if (PilaTC.getContNodo() == objetivo && conNumMovi != numMinMovi) {
                    JOptionPane.showMessageDialog(null, "Felicidades lo has logrado\n\n "
                            + "                             Intenta otro nivel");
                }

            }
        } catch (Exception e) {
            throw new MyException("No se pudo mover la ficha de torre");
        }
    }

    private void MoverCaA() throws MyException {

        try {
            if (PilaTC.getContNodo() > 0) {
                Nodo plataform = new Nodo();
                plataform.setDato(PilaTC.Peek());

                if (PilaTA.getContNodo() > 0) {
                    //Reglas de hanoi encontrar sinficha es mayor 
                    //que la qie va a ingresar
                    if (plataform.getDato().compareTo(PilaTA.Peek()) > 0) {
                        return;
                    }
                }

                PilaTC.Pop();
                PilaTA.Push(plataform);

                presentarTorreA();
                presentarTorreC();
                presentarCantMovi();
            }
        } catch (Exception e) {
            throw new MyException("No se pudo mover la ficha de torre");
        }
    }

    private void MoverCaB() throws MyException {

        try {
            if (PilaTC.getContNodo() > 0) {
                Nodo plataform = new Nodo();
                plataform.setDato(PilaTC.Peek());

                if (PilaTB.getContNodo() > 0) {
                    //Reglas de hanoi encontrar sinficha es mayor 
                    //que la qie va a ingresar
                    if (plataform.getDato().compareTo(PilaTB.Peek()) > 0) {
                        return;
                    }
                }

                PilaTC.Pop();
                PilaTB.Push(plataform);

                presentarTorreB();
                presentarTorreC();
                presentarCantMovi();
            }
        } catch (Exception e) {
            throw new MyException("No se pudo mover la ficha de torre");
        }
    }

    
    private void MoverPlatform(Pila origen, Pila destino){
    boolean stop = false;
    
        if (stop == false) {
            Nodo plataform = new Nodo();
            plataform.setDato(origen.Peek());
            origen.Pop();
            destino.Push(plataform);
            
            presentarTorreA();
            presentarTorreB();
            presentarTorreC();
            presentarCantMovi();
            
            JOptionPane pane = new JOptionPane("paso # " + lbl_Nmovi.getText()
                    +"\n\nContinuar?",JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
            JDialog dialog = pane.createDialog("Numero de pasos");
            dialog.setLocation(500, 400);
            
            dialog.setVisible(true);
            
            int opt = (int) pane.getValue();
            
            if (opt == JOptionPane.NO_OPTION) {
                stop = true;
            }
        }
    }
    
    private void HanoiRecursivo(int tamano, Pila origen, Pila auxiliar, Pila destino){
    
        if (tamano == 1) {
            MoverPlatform(origen, destino);
        }else{
            HanoiRecursivo(tamano - 1, origen, destino, auxiliar);
            MoverPlatform(origen, destino);
            
            HanoiRecursivo(tamano - 1, auxiliar, origen, destino);
        }
        
    }
    /**
     *
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        TorreB = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        TorreC = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        TorreA = new javax.swing.JTable();
        btn_ab = new javax.swing.JButton();
        btn_ac = new javax.swing.JButton();
        btn_ba = new javax.swing.JButton();
        btn_bc = new javax.swing.JButton();
        btn_ca = new javax.swing.JButton();
        btn_cb = new javax.swing.JButton();
        cb_numDiscos = new javax.swing.JComboBox<>();
        lbl_Nmovi = new javax.swing.JLabel();
        l_minMov = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        bt_iniciar = new javax.swing.JButton();
        bt_reiniciar = new javax.swing.JButton();
        bt_resolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        TorreB.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Torre B"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TorreB.setFocusable(false);
        TorreB.setRowSelectionAllowed(false);
        TorreB.setShowHorizontalLines(false);
        TorreB.setShowVerticalLines(false);
        jScrollPane1.setViewportView(TorreB);
        if (TorreB.getColumnModel().getColumnCount() > 0) {
            TorreB.getColumnModel().getColumn(0).setResizable(false);
        }

        TorreC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Torre C"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TorreC.setFocusable(false);
        TorreC.setRowSelectionAllowed(false);
        TorreC.setShowHorizontalLines(false);
        TorreC.setShowVerticalLines(false);
        jScrollPane2.setViewportView(TorreC);
        if (TorreC.getColumnModel().getColumnCount() > 0) {
            TorreC.getColumnModel().getColumn(0).setResizable(false);
        }

        TorreA.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Torre A"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TorreA.setAutoscrolls(false);
        TorreA.setFocusable(false);
        TorreA.setRowSelectionAllowed(false);
        TorreA.setShowHorizontalLines(false);
        TorreA.setShowVerticalLines(false);
        jScrollPane3.setViewportView(TorreA);
        if (TorreA.getColumnModel().getColumnCount() > 0) {
            TorreA.getColumnModel().getColumn(0).setResizable(false);
        }

        btn_ab.setText("B");
        btn_ab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_abActionPerformed(evt);
            }
        });

        btn_ac.setText("C");
        btn_ac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_acActionPerformed(evt);
            }
        });

        btn_ba.setText("A");
        btn_ba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_baActionPerformed(evt);
            }
        });

        btn_bc.setText("C");
        btn_bc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_bcActionPerformed(evt);
            }
        });

        btn_ca.setText("A");
        btn_ca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_caActionPerformed(evt);
            }
        });

        btn_cb.setText("B");
        btn_cb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cbActionPerformed(evt);
            }
        });

        cb_numDiscos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "3", "4", "5", "6", "7", "8", "9", "10" }));
        cb_numDiscos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_numDiscosActionPerformed(evt);
            }
        });

        lbl_Nmovi.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lbl_Nmovi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Nmovi.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        l_minMov.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        l_minMov.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        l_minMov.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel3.setText("Número de discos:");

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel4.setText("Número min movimientos");

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel5.setText("Número Realizados");

        bt_iniciar.setText("Iniciar");
        bt_iniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_iniciarActionPerformed(evt);
            }
        });

        bt_reiniciar.setText("Reiniciar");
        bt_reiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_reiniciarActionPerformed(evt);
            }
        });

        bt_resolver.setText("Resolver");
        bt_resolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_resolverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bt_iniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bt_reiniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(bt_resolver, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cb_numDiscos, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_Nmovi, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(l_minMov, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_ab, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_ac))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_ba)
                                .addGap(10, 10, 10)
                                .addComponent(btn_bc)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_ca)
                                .addGap(10, 10, 10)
                                .addComponent(btn_cb))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(76, 76, 76))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_ab)
                        .addComponent(btn_ac))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_ca)
                        .addComponent(btn_cb)
                        .addComponent(btn_ba)
                        .addComponent(btn_bc)))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cb_numDiscos)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(l_minMov, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_Nmovi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_reiniciar)
                    .addComponent(bt_resolver)
                    .addComponent(bt_iniciar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bt_iniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_iniciarActionPerformed
        try {
            // TODO add your handling code here:
            conNumMovi = 0;
            Iniciar();
        } catch (MyException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(new MyException("No ingreso"));
        }
    }//GEN-LAST:event_bt_iniciarActionPerformed

    private void bt_reiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_reiniciarActionPerformed
        try {
            // TODO add your handling code here:
            Reiniciar();
        } catch (MyException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(new MyException("No ingreso"));
        }
    }//GEN-LAST:event_bt_reiniciarActionPerformed


    private void bt_resolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_resolverActionPerformed
         // TODO add your handling code here:
        if (!l_minMov.getText().equals("") && PilaTC.getContNodo() != objetivo) {
            
            try {
                Reiniciar();
            } catch (MyException ex) {
                Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }

            detener = false;

            HanoiRecursivo(objetivo, PilaTA, PilaTB, PilaTC);

        }
    }//GEN-LAST:event_bt_resolverActionPerformed

    private void btn_abActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_abActionPerformed
        try {
            // TODO add your handling code here:

            MoverAaB();
        } catch (MyException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_abActionPerformed

    private void btn_acActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_acActionPerformed
        try {
            // TODO add your handling code here:

            MoverAaC();
        } catch (MyException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_acActionPerformed

    private void btn_baActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_baActionPerformed
        try {
            // TODO add your handling code here:
            MoverBaA();
        } catch (MyException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_baActionPerformed

    private void btn_bcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bcActionPerformed
        try {
            // TODO add your handling code here:
            MoverBaC();
        } catch (MyException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_bcActionPerformed

    private void btn_caActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_caActionPerformed
        try {
            // TODO add your handling code here:
            MoverCaA();
        } catch (MyException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_caActionPerformed

    private void btn_cbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cbActionPerformed
        try {
            // TODO add your handling code here:
            MoverCaB();
        } catch (MyException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_cbActionPerformed

    private void cb_numDiscosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_numDiscosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_numDiscosActionPerformed

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
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TorreA;
    private javax.swing.JTable TorreB;
    private javax.swing.JTable TorreC;
    private javax.swing.JButton bt_iniciar;
    private javax.swing.JButton bt_reiniciar;
    private javax.swing.JButton bt_resolver;
    private javax.swing.JButton btn_ab;
    private javax.swing.JButton btn_ac;
    private javax.swing.JButton btn_ba;
    private javax.swing.JButton btn_bc;
    private javax.swing.JButton btn_ca;
    private javax.swing.JButton btn_cb;
    private javax.swing.JComboBox<String> cb_numDiscos;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel l_minMov;
    private javax.swing.JLabel lbl_Nmovi;
    // End of variables declaration//GEN-END:variables

}
