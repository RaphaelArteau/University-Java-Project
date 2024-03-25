/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.cabanon.domaine.plancher;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author Maxence
 */
public class LigneEntremiseDTO implements Serializable{
  public UUID Id;
  public double CoordXLigneEntremise;
  public boolean Conflit;
  public boolean selected;
  
  public LigneEntremiseDTO(LigneEntremises ligne){
      Id = ligne.getUUID();
      CoordXLigneEntremise = ligne.getCoordXLigneEntremise();
      Conflit = ligne.getConflit();
      selected = ligne.isSelected();

  }
  
}
