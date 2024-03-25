/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.cabanon.domaine.murs;

import java.util.ArrayList;
import java.util.UUID;
/**
 *
 * @author Maxence
 */
public class Accessoire {
    
    
public static int typeFenetre = 1;
public static int typePorte = 2;

 private UUID id;
 private int typeAccessoire;
 private double hauteurContour;
 private double largeurContour;
 private double coordAccessoireX;
 private double coordAccessoireY;
 private double coordXPourcentage;
 private double coordYPourcentage;
 private double espacementMontants;
 private ArrayList<Double> coordMontantsX;
 private tailleLinteaux tailleLinteau;
 private boolean conflit;
 private boolean selected;
 private EntremiseMur entremiseGauche;
 private EntremiseMur entremiseDroite;
 private Mur parent;
 
 public static enum tailleLinteaux{
     deuxXsix, deuxXhuit, deuxXdix
 }
 
 // constructeur de la classe Accessoire
 public Accessoire(Mur parent, int typeAccessoire){
     this.parent = parent;
     this.id = UUID.randomUUID();
     this.typeAccessoire = typeAccessoire;
     if(typeAccessoire == 1){
         this.hauteurContour = 40;
         this.largeurContour = 50;
         this.coordAccessoireX = this.parent.getLargeur()/2 - 25;
         this.coordAccessoireY = this.parent.getHauteur()/2 - 20;
     }
     else{
         this.hauteurContour = 80;
         this.largeurContour = 30;
         this.coordAccessoireY = 0;
         this.coordAccessoireX = this.parent.getLargeur()/2 - 15;
     }
     this.coordXPourcentage = ((this.coordAccessoireX + this.largeurContour/2)* 100 )/ this.parent.getLargeur();
     this.coordYPourcentage = ((this.coordAccessoireY + this.hauteurContour/2)* 100) / this.parent.getHauteur();
     this.espacementMontants = 24;
     this.calculerPositionMontants();
     this.tailleLinteau = tailleLinteaux.deuxXsix;
     this.conflit = false;
     this.entremiseGauche = new EntremiseMur(this.parent);
     this.parent.getEntremises().add(entremiseGauche);
     entremiseGauche.setAccessoireParent(this);
     this.entremiseDroite = new EntremiseMur(this.parent);
     this.parent.getEntremises().add(entremiseDroite);
     entremiseDroite.setAccessoireParent(this);
     this.repositionnerEntremises();
     //this.conflitEntremise = false;
     //this.largeurEntDroite = 0;
     //this.largeurEntGauche = 0;
             }
 
 public void setHauteurContour(double hauteurContour){
     this.hauteurContour = hauteurContour;
     this.repositionnerEntremises();
 }
 
 public void setLargeurContour(double largeurContour){
     this.largeurContour = largeurContour;
     this.parent.calculerPositionMontants();
     this.repositionnerEntremises();
 }
 
 public void setcoordAccessoireX(double coordX){
     if(coordX >= 4 && coordX + this.largeurContour < this.parent.getLargeur()-4){
     this.coordAccessoireX = coordX;
     this.coordXPourcentage = ((this.coordAccessoireX + this.largeurContour/2)* 100 )/ this.parent.getLargeur();
     this.calculerPositionMontants();
     this.parent.calculerPositionMontants();
     this.repositionnerEntremises();
     }
 }
 
 public void setcoordAccessoireY(double coordY){
     double epaisseurLinteau = 6;
     if(this.tailleLinteau == tailleLinteaux.deuxXdix){
         epaisseurLinteau = 10;
     }
     if(this.tailleLinteau == tailleLinteaux.deuxXhuit){
         epaisseurLinteau = 8;
     }     
     if(this.typeAccessoire != 2){
         if(coordY >= 2 && coordY + this.hauteurContour + epaisseurLinteau < this.parent.getHauteur()-2){
     this.coordAccessoireY = coordY;
     }
     }
     this.coordYPourcentage = ((this.coordAccessoireY + this.hauteurContour/2)* 100) / this.parent.getHauteur();
     this.calculerPositionMontants();
     this.parent.calculerPositionMontants();
     this.repositionnerEntremises();
     
 }
 
 public void setCoordXPourcent(double pourcentage){
     this.coordXPourcentage = pourcentage;
     this.coordAccessoireX = ((pourcentage * this.parent.getLargeur()) / 100) - (this.largeurContour/2);
     this.calculerPositionMontants();
     this.parent.calculerPositionMontants();
     this.repositionnerEntremises();
 }
 
  public void setCoordYPourcent(double pourcentage){
     if(this.typeAccessoire != 2){
         this.coordYPourcentage = pourcentage;
     }
     this.coordAccessoireY = ((pourcentage * this.parent.getLargeur()) / 100) - (this.hauteurContour/2);
     this.calculerPositionMontants();
     this.parent.calculerPositionMontants();
     this.repositionnerEntremises();
 }
 
 public void setEspacementMontants(double espacement){
     if(espacement >=2){
     this.espacementMontants = espacement;
     }
 }
 
 public void setTailleLinteau(tailleLinteaux linteau){
     if(linteau == tailleLinteaux.deuxXsix || linteau == tailleLinteaux.deuxXhuit|| linteau == tailleLinteaux.deuxXdix){
         this.tailleLinteau = linteau;
     }
 }
 public void setConflit(boolean conflit){
     this.conflit = conflit;
 }
 public void setIdAccessoire(UUID id){
     this.id = id;
 }

 public UUID getIdAccessoire(){
     return this.id;
 }
 
 public int getTypeAccessoire(){
     return this.typeAccessoire;
 }
 
 public double getLargeurContour(){
     return this.largeurContour;
 }
 
 public double getHauteurContour(){
     return this.hauteurContour;
 }
 
 public double getCoordAccessoireX(){
     return this.coordAccessoireX;
 }
 
 public double getCoordAccessoireY(){
     return this.coordAccessoireY;
 }
 
 public double getCoordXPourcent(){
     return this.coordXPourcentage;
 }
 
 public double getCoordYPourcent(){
     return this.coordYPourcentage;
 }
 
 public double getEspacementMontants(){
     return this.espacementMontants;
 }
 
 public ArrayList<Double> getCoordMontantsX(){
     return this.coordMontantsX;
 }
 
 public tailleLinteaux getTailleLinteau(){
     return this.tailleLinteau;
 }
 
 public boolean getConflit(){
     return this.conflit;
 }
 public EntremiseMur getEntremiseGauche(){
     return this.entremiseGauche;
 }
 public EntremiseMur getEntremiseDroite(){
     return this.entremiseDroite;
 }

 public Mur getParent(){
     return this.parent;
 }
 
 private void calculerPositionMontants(){
     this.coordMontantsX = new ArrayList<Double>();
     int nombreMontants = 1;
     double positionMontant = this.coordAccessoireX;
     for(int i = 0; i<nombreMontants; i++){
       this.coordMontantsX.add(i, positionMontant + 1);
       if(positionMontant + this.espacementMontants < this.coordAccessoireX + this.largeurContour - 2){
           positionMontant += this.espacementMontants;
           //System.out.println(positionMontant);
           nombreMontants++;
       }
       else{
           this.coordMontantsX.add(i+1, this.coordAccessoireX + this.largeurContour - 1);
       }
     }
     /*for(double position : this.coordMontantsX){
         System.out.println(position);
     }*/
 }
 
 private void repositionnerEntremises(){
     this.entremiseGauche.setCoordEntremiseX(this.coordAccessoireX -3);
     this.entremiseGauche.setCoordEntremiseY(this.coordAccessoireY +(this.hauteurContour/2));
     this.entremiseDroite.setCoordEntremiseX(this.coordAccessoireX + this.largeurContour +3);
     this.entremiseDroite.setCoordEntremiseY(this.coordAccessoireY +(this.hauteurContour/2));
 }

 public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}


