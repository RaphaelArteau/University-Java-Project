/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.cabanon.domaine.toit;

/**
 *
 * @author Utilisateur
 */
public class PoinconsDTO {
   public double Longueur; 
   public double CoordPoinconsX;
   public double CoordPoinconsY;
    
   public PoinconsDTO (Poincons poincons){
       Longueur = poincons.getLongueurPoincons();
       CoordPoinconsX = poincons.getCoordPoinconsX();
       CoordPoinconsY = poincons.getCoordPoinconsY();
   }
}
