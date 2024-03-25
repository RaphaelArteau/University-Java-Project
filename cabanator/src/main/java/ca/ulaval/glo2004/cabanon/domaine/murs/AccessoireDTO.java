/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.cabanon.domaine.murs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author Maxence
 */
public class AccessoireDTO implements Serializable{
   public UUID Id;
   public int TypeAccessoire;
   public double LargeurContour;
   public double HauteurContour;
   public double CoordAccessoireX;
   public double CoordAccessoireY;
   public double PourAccessoireX;
   public double PourAccessoireY;
   public double EspacementMontants;
   public ArrayList<Double> CoordMontantsX;
   public Accessoire.tailleLinteaux TailleLinteau;
   public boolean Conflit;
   public transient Mur Parent;
   public transient EntremiseMur EntremiseGauche;
   public transient EntremiseMur EntremiseDroite;
   public boolean isSelected;
   public Mur.orientations orientation;

   public AccessoireDTO(Accessoire accessoire){
       Id = accessoire.getIdAccessoire();
       TypeAccessoire = accessoire.getTypeAccessoire();
       LargeurContour = accessoire.getLargeurContour();
       HauteurContour = accessoire.getHauteurContour();
       CoordAccessoireX = accessoire.getCoordAccessoireX();
       CoordAccessoireY = accessoire.getCoordAccessoireY();
       PourAccessoireX = accessoire.getCoordXPourcent();
       PourAccessoireY = accessoire.getCoordYPourcent();
       EspacementMontants = accessoire.getEspacementMontants();
       CoordMontantsX = accessoire.getCoordMontantsX();
       TailleLinteau = accessoire.getTailleLinteau();
       Conflit = accessoire.getConflit();
       EntremiseGauche = accessoire.getEntremiseGauche();
       EntremiseDroite = accessoire.getEntremiseDroite();
       Parent = accessoire.getParent();
       isSelected = accessoire.isSelected();
       orientation = accessoire.getParent().getOrientation();

   }
}
