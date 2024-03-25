/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.cabanon.domaine.murs;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author Maxence
 */
public class EntremiseMurDTO implements Serializable{
    public UUID Id;
    public transient Mur Parent;
    public double CoordEntremiseX;
    public double AbsoluteCoordEntremiseX;
    public double CoordEntremiseY;
    public boolean Conflit;
    public double Longueur;
    public transient Accessoire AccessoireParent;
    public boolean selected;
    public Mur.orientations orientation;

    public EntremiseMurDTO(EntremiseMur Entremise){
        Id = Entremise.getId();
        Parent = Entremise.getParent();
        CoordEntremiseX = Entremise.getcoordEntremiseX();
        CoordEntremiseY = Entremise.getcoordEntremiseY();
        Conflit = Entremise.getConflit();
        Longueur = Entremise.getLongueurEntremise();
        AccessoireParent = Entremise.getAccessoireParent();
        selected = Entremise.isSelected();
        orientation = Entremise.getParent().getOrientation();
        AbsoluteCoordEntremiseX = Entremise.getAbsoluteCoordEntremiseX();
    }
}
