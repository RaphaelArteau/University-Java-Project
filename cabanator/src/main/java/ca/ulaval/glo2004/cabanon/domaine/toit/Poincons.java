/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.cabanon.domaine.toit;

/**
 *
 * @author Charles
 */
public class Poincons {
   private double Longueur; 
   private double CoordPoinconsX;
   private double CoordPoinconsY;
   
   public Poincons(double longueur, double CoordX, double CoordY){
       this.Longueur = longueur;
       this.CoordPoinconsX = CoordX;
       this.CoordPoinconsY = CoordY;
       
   }
   
   public double getLongueurPoincons(){
       return this.Longueur;
   }
   
   public void setLongueurPoincons(double longueur){
       this.Longueur = longueur;
   }
   
   public double getCoordPoinconsX(){
       return this.CoordPoinconsX;
   }
   
   public void setCoordPoinconsX(double Coord){
       this.CoordPoinconsX = Coord;
   }
   
   public double getCoordPoinconsY(){
       return this.CoordPoinconsY;
   }
   
   public void setCoordPoinconsY(double Coord){
       this.CoordPoinconsY = Coord;
   }
}
