/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.gui;

import ca.ulaval.glo2004.cabanon.domaine.murs.EntremiseMurDTO;
import ca.ulaval.glo2004.cabanon.domaine.murs.Mur;
import java.awt.Dimension;
import java.util.UUID;
import javax.swing.event.ChangeEvent;
import resources.UnitConverter;

/**
 *
 * @author raphael
 */
public class AddedEntremisePane extends AddedAccessoryPane {

    private boolean init = true;

    public AddedEntremisePane(UUID id, Mur.orientations orientation, MainWindow parent) {
        super(id, "Entremise", parent);
        this.setOrientation(orientation);
        this.setPreferredSize(new Dimension(200, 140));
        initComponents();
        EntremiseMurDTO entremise = this.controlleur.getEntremiseMur(orientation, id);
        //int index = this.controlleur.getEntremiseCountMur(orientation);
        //this.count.setText(Integer.toString(index+1));
        this.size.setVisible(false);
        this.DistanceMontant.setVisible(false);
        this.PosY.setValue(Math.round(entremise.CoordEntremiseY));
        this.PosX.setValue(Math.round(entremise.CoordEntremiseX));
        this.init = false;
        this.taillLinteau.setVisible(false);
    }

    private void initComponents() {

        PosY.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                if(!init) PosStateChanged(evt);
            }
        });
        PosX.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                if(!init) PosStateChanged(evt);
            }

        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

    }

    private void PosStateChanged(ChangeEvent evt) {
        //System.out.println(PosX.getValue());
        //System.out.println("PosX = "+PosX.getValue().toString());
        //System.out.println("PosY = "+PosY.getValue().toString());
        double x = Double.parseDouble(PosX.getValue().toString());
        double y = Double.parseDouble(PosY.getValue().toString());
        this.controlleur.deplacerEntremiseMur(this.getOrientation(), this.getID(), UnitConverter.fractionator(x), UnitConverter.fractionator(y));
        this.parent.refreshVisualizer();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        this.controlleur.supprimerEntremiseMur(orientation, id);
        this.parent.refreshVisualizer();
        this.setVisible(false);
    }

    @Override
    public void updateValues() {
        EntremiseMurDTO entremise = this.controlleur.getEntremiseMur(this.orientation, this.id);
        this.PosY.setValue(Math.round(entremise.CoordEntremiseY));
        this.PosX.setValue(Math.round(entremise.CoordEntremiseX));
        this.repaint();
    }

}
