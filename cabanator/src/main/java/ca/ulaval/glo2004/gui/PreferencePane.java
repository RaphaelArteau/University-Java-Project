/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ca.ulaval.glo2004.gui;

import ca.ulaval.glo2004.cabanon.domaine.Controlleur;
import ca.ulaval.glo2004.preferences.Preferences;
import static resources.UnitConverter.defractionator;
import static resources.UnitConverter.fractionator;

/**
 *
 * @author raphael
 */
public class PreferencePane extends javax.swing.JFrame {

    MainWindow mainWindow;
    Controlleur controlleur;
    
    /**
     * Creates new form PreferencePane
     * @param mainWindow
     */
    public PreferencePane(Controlleur controlleur, MainWindow mainWindow) {
        initComponents();
        this.mainWindow = mainWindow;
        this.controlleur = controlleur;
        textFieldTailleMax24.setText(fractionator(Preferences.getLongueurMax24()));
        textFieldTailleMax26.setText(fractionator(Preferences.getLongueurMax26()));
        textFieldTailleMax28.setText(fractionator(Preferences.getLongueurMax28()));
        textFieldTailleMax210.setText(fractionator(Preferences.getLongueurMax210()));
        textFieldDimensionCasesGrille.setText(fractionator(Preferences.getGridSize()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelPreferences = new javax.swing.JLabel();
        panelTaillesMax = new javax.swing.JPanel();
        labelTaillesMax = new javax.swing.JLabel();
        labelTailleMax24 = new javax.swing.JLabel();
        labelTailleMax26 = new javax.swing.JLabel();
        labelTailleMax28 = new javax.swing.JLabel();
        labelTailleMax210 = new javax.swing.JLabel();
        textFieldTailleMax24 = new javax.swing.JFormattedTextField();
        textFieldTailleMax26 = new javax.swing.JFormattedTextField();
        textFieldTailleMax28 = new javax.swing.JFormattedTextField();
        textFieldTailleMax210 = new javax.swing.JFormattedTextField();
        boutonOkTaillesMax = new javax.swing.JButton();
        panelDimensionCasesGrille = new javax.swing.JPanel();
        labelDimensionCasesGrille = new javax.swing.JLabel();
        textFieldDimensionCasesGrille = new javax.swing.JFormattedTextField();
        boutonOkDimensionCasesGrille = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        labelPreferences.setText("Préférences");

        labelTaillesMax.setText("Taille maximale des planches :");

        labelTailleMax24.setText("2 x 4 :");

        labelTailleMax26.setText("2 x 6 :");

        labelTailleMax28.setText("2 x 8  :");

        labelTailleMax210.setText("2 x 10 :");

        textFieldTailleMax24.setText("jFormattedTextField1");
        textFieldTailleMax24.setMinimumSize(new java.awt.Dimension(150, 23));
        textFieldTailleMax24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldTailleMax24ActionPerformed(evt);
            }
        });

        textFieldTailleMax26.setText("jFormattedTextField2");
        textFieldTailleMax26.setMinimumSize(new java.awt.Dimension(150, 23));

        textFieldTailleMax28.setText("jFormattedTextField3");
        textFieldTailleMax28.setMinimumSize(new java.awt.Dimension(150, 23));

        textFieldTailleMax210.setText("jFormattedTextField4");
        textFieldTailleMax210.setMinimumSize(new java.awt.Dimension(150, 23));
        textFieldTailleMax210.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldTailleMax210ActionPerformed(evt);
            }
        });

        boutonOkTaillesMax.setText("OK");
        boutonOkTaillesMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonOkTaillesMaxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelTaillesMaxLayout = new javax.swing.GroupLayout(panelTaillesMax);
        panelTaillesMax.setLayout(panelTaillesMaxLayout);
        panelTaillesMaxLayout.setHorizontalGroup(
            panelTaillesMaxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTaillesMaxLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTaillesMaxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelTaillesMaxLayout.createSequentialGroup()
                        .addComponent(labelTailleMax24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldTailleMax24, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                        .addGap(84, 84, 84))
                    .addGroup(panelTaillesMaxLayout.createSequentialGroup()
                        .addComponent(labelTailleMax26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldTailleMax26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelTaillesMaxLayout.createSequentialGroup()
                        .addComponent(labelTailleMax28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldTailleMax28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelTaillesMaxLayout.createSequentialGroup()
                        .addComponent(labelTailleMax210)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldTailleMax210, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(boutonOkTaillesMax, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(11, Short.MAX_VALUE))
            .addGroup(panelTaillesMaxLayout.createSequentialGroup()
                .addComponent(labelTaillesMax)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelTaillesMaxLayout.setVerticalGroup(
            panelTaillesMaxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTaillesMaxLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelTaillesMax)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTaillesMaxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTailleMax24)
                    .addComponent(textFieldTailleMax24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTaillesMaxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTailleMax26)
                    .addComponent(textFieldTailleMax26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTaillesMaxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTailleMax28)
                    .addComponent(textFieldTailleMax28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTaillesMaxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(boutonOkTaillesMax, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelTaillesMaxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelTailleMax210)
                        .addComponent(textFieldTailleMax210, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        labelDimensionCasesGrille.setText("Dimensions des cases de la grille :");

        textFieldDimensionCasesGrille.setText("jFormattedTextField5");
        textFieldDimensionCasesGrille.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldDimensionCasesGrilleActionPerformed(evt);
            }
        });

        boutonOkDimensionCasesGrille.setText("OK");
        boutonOkDimensionCasesGrille.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonOkDimensionCasesGrilleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelDimensionCasesGrilleLayout = new javax.swing.GroupLayout(panelDimensionCasesGrille);
        panelDimensionCasesGrille.setLayout(panelDimensionCasesGrilleLayout);
        panelDimensionCasesGrilleLayout.setHorizontalGroup(
            panelDimensionCasesGrilleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDimensionCasesGrilleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDimensionCasesGrilleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDimensionCasesGrilleLayout.createSequentialGroup()
                        .addComponent(labelDimensionCasesGrille)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelDimensionCasesGrilleLayout.createSequentialGroup()
                        .addComponent(textFieldDimensionCasesGrille, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(boutonOkDimensionCasesGrille, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15))))
        );
        panelDimensionCasesGrilleLayout.setVerticalGroup(
            panelDimensionCasesGrilleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDimensionCasesGrilleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelDimensionCasesGrille)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDimensionCasesGrilleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldDimensionCasesGrille, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boutonOkDimensionCasesGrille))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelTaillesMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelPreferences))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelDimensionCasesGrille, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelPreferences)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTaillesMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelDimensionCasesGrille, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void textFieldTailleMax24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldTailleMax24ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldTailleMax24ActionPerformed

    private void textFieldDimensionCasesGrilleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldDimensionCasesGrilleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldDimensionCasesGrilleActionPerformed

    private void boutonOkTaillesMaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonOkTaillesMaxActionPerformed
        Preferences.setLongueurMax24(defractionator(textFieldTailleMax24.getText()));
        Preferences.setLongueurMax26(defractionator(textFieldTailleMax26.getText()));
        Preferences.setLongueurMax28(defractionator(textFieldTailleMax28.getText()));
        Preferences.setLongueurMax210(defractionator(textFieldTailleMax210.getText()));
        controlleur.recalculCabanon();
        this.mainWindow.miseAJourCout();
        this.mainWindow.refreshVisualizer();
    }//GEN-LAST:event_boutonOkTaillesMaxActionPerformed

    private void textFieldTailleMax210ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldTailleMax210ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldTailleMax210ActionPerformed

    private void boutonOkDimensionCasesGrilleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonOkDimensionCasesGrilleActionPerformed
        Preferences.setGridSize(defractionator(textFieldDimensionCasesGrille.getText()));
        this.mainWindow.refreshVisualizer();
    }//GEN-LAST:event_boutonOkDimensionCasesGrilleActionPerformed

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
            java.util.logging.Logger.getLogger(PreferencePane.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PreferencePane.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PreferencePane.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PreferencePane.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //new PreferencePane().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boutonOkDimensionCasesGrille;
    private javax.swing.JButton boutonOkTaillesMax;
    private javax.swing.JLabel labelDimensionCasesGrille;
    private javax.swing.JLabel labelPreferences;
    private javax.swing.JLabel labelTailleMax210;
    private javax.swing.JLabel labelTailleMax24;
    private javax.swing.JLabel labelTailleMax26;
    private javax.swing.JLabel labelTailleMax28;
    private javax.swing.JLabel labelTaillesMax;
    private javax.swing.JPanel panelDimensionCasesGrille;
    private javax.swing.JPanel panelTaillesMax;
    private javax.swing.JFormattedTextField textFieldDimensionCasesGrille;
    private javax.swing.JFormattedTextField textFieldTailleMax210;
    private javax.swing.JFormattedTextField textFieldTailleMax24;
    private javax.swing.JFormattedTextField textFieldTailleMax26;
    private javax.swing.JFormattedTextField textFieldTailleMax28;
    // End of variables declaration//GEN-END:variables
}
