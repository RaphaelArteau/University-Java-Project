/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.gui;

import ca.ulaval.glo2004.cabanon.domaine.murs.Accessoire;
import ca.ulaval.glo2004.cabanon.domaine.murs.AccessoireDTO;
import ca.ulaval.glo2004.cabanon.domaine.murs.Mur;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.UUID;
import resources.UnitConverter;

/**
 *
 * @author raphael
 */
public class AddedDoorPane extends AddedAccessoryPane{
    
    public AddedDoorPane(UUID id, Mur.orientations orientation, MainWindow parent) {
        super(id, "Porte", parent);
        
        int index = this.controlleur.getAccessoireCountMur(orientation);
        //this.count.setText(Integer.toString(index+1) );
        this.setOrientation(orientation);
        this.YWrapper.setVisible(false);
        this.initComponents();
        this.setPreferredSize(new Dimension(200, 300));
        AccessoireDTO accessoire = this.controlleur.getAccessoireFromUUID(this.getOrientation(), this.getID());
        this.PosX.setValue(Math.round(accessoire.CoordAccessoireX));
        this.PosY.setValue(Math.round(accessoire.CoordAccessoireY));
        this.largeur.setText(this.controlleur.getAccessoireFromUUID(orientation, id).LargeurContour+"");
        this.hauteur.setText(this.controlleur.getAccessoireFromUUID(orientation, id).HauteurContour+"");
        this.distanceMontant.setText(this.controlleur.getAccessoireFromUUID(orientation, id).EspacementMontants+"");
    }
    
        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        this.controlleur.supprimerAccessoire(id);
        this.parent.refreshVisualizer();
        this.setVisible(false);
    }
        private void initComponents(){

        Ok.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                OkMousePressed(evt);
            }
        });
        OkMontant.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                OkMontantMousePressed(evt);
            }
        });
        
        PosY.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                PosYStateChanged(evt);
            }
        });
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
        LinteauComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LinteauComboBoxActionPerformed(evt);
            }
        });
    }
            private void LinteauComboBoxActionPerformed(java.awt.event.ActionEvent evt) {                                                
        String s = (String)LinteauComboBox.getSelectedItem();
          switch(s){
              case "2x6":
                  this.controlleur.setTailleLinteauAcc(orientation, id, Accessoire.tailleLinteaux.deuxXsix);
                  this.parent.refreshVisualizer();
                  break;
              case "2x8":
                  this.controlleur.setTailleLinteauAcc(orientation, id, Accessoire.tailleLinteaux.deuxXhuit);
                  this.parent.refreshVisualizer();
                  break;
              case "2x10":
                  this.controlleur.setTailleLinteauAcc(orientation, id, Accessoire.tailleLinteaux.deuxXdix);
                  this.parent.refreshVisualizer();
                  break;
              default:
                  this.controlleur.setTailleLinteauAcc(orientation, id, Accessoire.tailleLinteaux.deuxXsix);
                  this.parent.refreshVisualizer();
                  break;
            }
        } 
         private void PosYStateChanged(javax.swing.event.ChangeEvent evt) {
        AccessoireDTO accessoire = this.controlleur.getAccessoireFromUUID(this.getOrientation(), this.getID());
        if(this.PosY.getValue() instanceof Integer){
            String Y = this.PosY.getValue().toString();
            this.controlleur.deplacerAccessoire(this.getOrientation(), this.getID(), UnitConverter.fractionator(accessoire.CoordAccessoireX), Y);
            this.parent.refreshVisualizer();
        }
    }

    private void PosXStateChanged(javax.swing.event.ChangeEvent evt) {
        AccessoireDTO accessoire = this.controlleur.getAccessoireFromUUID(this.getOrientation(), this.getID());
        if(this.PosX.getValue() instanceof Integer){
            String X = this.PosX.getValue().toString();
            this.controlleur.deplacerAccessoire(this.getOrientation(), this.getID(), X, UnitConverter.fractionator(accessoire.CoordAccessoireY));
            this.parent.refreshVisualizer();
        }
    }



    private void OkMousePressed(java.awt.event.MouseEvent evt){
        String largeur = this.largeur.getText();
        String hauteur = this.hauteur.getText();
        double nouvelleLargeur = Double.parseDouble(largeur);
        double nouvelleHauteur = Double.parseDouble(hauteur);
        this.controlleur.setHauteurAccessoire(this.getOrientation(), this.getID(), nouvelleHauteur);
        this.controlleur.setLargeurAccessoire(this.getOrientation(), this.getID(), nouvelleLargeur);
        //this.visualizer.repaint();
        this.controlleur.sauvegardeEtat();
        this.parent.refreshVisualizer();
    }
    
    private void OkMontantMousePressed(MouseEvent evt) {
        String espacement = this.distanceMontant.getText();
        double nouvelEspacement = Double.parseDouble(espacement);
        this.controlleur.setEspacementMontantsAcc(orientation, id, nouvelEspacement);
        //this.visualizer.repaint();
        this.parent.refreshVisualizer();
    }

    @Override
    public void updateValues() {
        AccessoireDTO accessoire = this.controlleur.getAccessoireFromUUID(this.getOrientation(), this.getID());
        this.PosX.setValue(Math.round(accessoire.CoordAccessoireX));
        this.largeur.setText(UnitConverter.fractionator(accessoire.LargeurContour));
        this.hauteur.setText(UnitConverter.fractionator(accessoire.HauteurContour));
        this.distanceMontant.setText(UnitConverter.fractionator(accessoire.EspacementMontants));
        this.hauteur.setText(UnitConverter.fractionator(accessoire.HauteurContour));
        this.largeur.setText(UnitConverter.fractionator(accessoire.LargeurContour));
        ActionListener[] list = this.LinteauComboBox.getActionListeners();
        for(ActionListener l:list){
        this.LinteauComboBox.removeActionListener(l);
        }
        int index= 0;
            if(accessoire.TailleLinteau == Accessoire.tailleLinteaux.deuxXhuit){
                index = 1;
            }
            else if(accessoire.TailleLinteau == Accessoire.tailleLinteaux.deuxXdix){
                index=2;
            }
        //System.out.println(index);
        //System.out.println(this.controlleur.getAccessoireFromUUID(id).TailleLinteau);
        this.LinteauComboBox.setSelectedIndex(index);
        for(ActionListener l:list){
        this.LinteauComboBox.addActionListener(l);
        }
        this.repaint();
    }
}
