/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.cabanon.domaine.toit;

import java.util.UUID;
/**
 *
 * @author Utilisateur
 */
public class FermesDTO {
     public double coordFermeX;
     public UUID id;
     
     public FermesDTO(Fermes ferme){
         coordFermeX = ferme.getCoordFermesX();
         id = ferme.getUUID();
     }
}
