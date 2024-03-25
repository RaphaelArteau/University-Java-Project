/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.gui;
import ca.ulaval.glo2004.cabanon.domaine.plancher.PlancherDTO;

import java.awt.Dimension;
import java.util.UUID;

/**
 *
 * @author raphael
 */
public class LigneEntremisePane extends AddedAccessoryPane{

    public LigneEntremisePane(UUID id, int index, MainWindow parent) {
        super(id,"Ligne d'entremise", parent);
        this.PosX.setValue(Math.round(this.controlleur.getLigneEntremises(id).CoordXLigneEntremise));
        this.size.setVisible(false);
        this.PosY.setVisible(false);
        this.jLabel7.setVisible(false);
        this.DistanceMontant.setVisible(false);
        this.LinteauWrapper.setVisible(false);
        this.setPreferredSize(new Dimension(this.getPreferredSize().width, 150));
        this.setSize(this.getPreferredSize());
        initComponents();
        
    }
    
    private void initComponents(){

        
        PosX.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                PosXStateChanged(evt);
            }
        });
        
        
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
    }
    
    private void PosXStateChanged(javax.swing.event.ChangeEvent evt) {
        PlancherDTO plancher = this.controlleur.getPlancher();
        if(this.PosX.getValue() instanceof Integer){
            String X = this.PosX.getValue().toString();
            this.controlleur.deplacerLigneEntremise(this.id, X);
            this.parent.refreshVisualizer();
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        this.controlleur.supprimerLigneEntremises(id);
        this.parent.refreshVisualizer();
        this.setVisible(false);
    }

    @Override
    public void updateValues() {
        this.PosX.setValue(Math.round(this.controlleur.getLigneEntremises(id).CoordXLigneEntremise));
        this.revalidate();
        this.repaint();
    }

}
