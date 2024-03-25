/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.cabanon.domaine.murs;

import ca.ulaval.glo2004.cabanon.domaine.murs.Accessoire;
import ca.ulaval.glo2004.cabanon.domaine.murs.BlocMur;
import ca.ulaval.glo2004.cabanon.domaine.Cabanon;
import ca.ulaval.glo2004.cabanon.domaine.murs.EntremiseMur;
import ca.ulaval.glo2004.cabanon.domaine.murs.Mur;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author raphael
 */
public class MurDTO {
    
 public Cabanon Parent;
 public double Largeur;
 public double Hauteur;
 public Mur.orientations OrientationMur;
 public double EspacementMontants;
 public ArrayList<ArrayList<Double>> CoordMontants;
 public ArrayList<EntremiseMur> Entremises;
 public ArrayList<Accessoire> Accessoires;
 public ArrayList<ArrayList<BlocMur>> ListeDeBlocs;
 public UUID Uuid;
 
 public MurDTO(Mur mur){
     Parent = mur.getParent();
     Largeur = mur.getLargeur();
     Hauteur = mur.getHauteur();
     OrientationMur = mur.getOrientation();
     EspacementMontants = mur.getEspacementMontants();
     CoordMontants = mur.getCoordMontants();
     Entremises = mur.getEntremises();
     Accessoires = mur.getAccessoire();
     /*
     ArrayList<ArrayList<BlocMurDTO>> blocsDTO = new ArrayList<ArrayList<BlocMurDTO>>();
     ArrayList<ArrayList<BlocMur>> blocs =  mur.getListeDeBlocs();
     for(int i = 0; i < blocs.size(); i++){
         ArrayList<BlocMur> ligne = blocs.get(i);
         ArrayList<BlocMurDTO> tempLigne = new ArrayList<BlocMurDTO>();
         for(int y = 0; y < ligne.size(); y++){
             tempLigne.add(new BlocMurDTO(ligne.get(y)));
         }
         blocsDTO.add(tempLigne);
     }
 */
     ListeDeBlocs = mur.getListeDeBlocs();
     Uuid = mur.getUuid();
 }
}
