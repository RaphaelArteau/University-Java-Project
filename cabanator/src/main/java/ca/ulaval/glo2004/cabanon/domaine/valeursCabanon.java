/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.cabanon.domaine;

import ca.ulaval.glo2004.cabanon.domaine.murs.AccessoireDTO;
import ca.ulaval.glo2004.cabanon.domaine.murs.EntremiseMurDTO;
import ca.ulaval.glo2004.cabanon.domaine.murs.Mur;
import ca.ulaval.glo2004.cabanon.domaine.plancher.LigneEntremiseDTO;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import resources.Sauvegarde;

/**
 *
 * @author raphael
 */
public class valeursCabanon {
    
    public double hauteurMurs = 96;
    public double largeur = 144;
    public double profondeur = 180;
    public ArrayList<LigneEntremiseDTO> LignesEntremises = null;
    public Double distanceSolivePlancher = (double) 18;
    public Map<Mur.orientations, Map<String, Double>> valeursMurs = new HashMap();
    public Map<Mur.orientations, ArrayList<AccessoireDTO>> AccessoiresMurs = new HashMap();
    public Map<Mur.orientations, ArrayList<EntremiseMurDTO>> EntremisesMurs = new HashMap();

    public Double EspacementEntremisesDebor;
    public Double EspacementFermes;
    public Double AngleToit;
    public Double PorteAFaux ;
    public Double OrientationToit;
    public Double HauteurToit;
    public boolean valeursToit = false;


    valeursCabanon(File valeurs) {
        if(valeurs != null) chargerValeursDeFichier(valeurs);
    }
    
    
    private void chargerValeursDeFichier(File valeurs){
        boolean loadedSuccesfully = false;
        Exception error = null;
        try {
         FileInputStream fileIn = new FileInputStream(valeurs.getAbsoluteFile());
         ObjectInputStream in = new ObjectInputStream(fileIn);
         Sauvegarde save = (Sauvegarde) in.readObject();
         in.close();
         fileIn.close();
         loadedSuccesfully = true;
         this.hauteurMurs = save.valeursCabanon.get("hauteurMur");
         this.largeur = save.valeursPlancher.get("largeur");
         this.profondeur = save.valeursPlancher.get("profondeur");
         this.LignesEntremises = save.ligneEntremisesPlancher;
         this.distanceSolivePlancher = save.valeursPlancher.get("distanceSolives");

         this.valeursMurs = save.valeursMurs;
         this.AccessoiresMurs = save.AccessoiresMurs;
         this.EntremisesMurs = save.EntremisesMurs;

         this.EspacementEntremisesDebor = save.valeursToit.get("EspacementEntremisesDebor");
         this.EspacementFermes = save.valeursToit.get("EspacementFermes");
         this.AngleToit = save.valeursToit.get("AngleToit");
         this.PorteAFaux = save.valeursToit.get("PorteAFaux");
         this.OrientationToit = save.valeursToit.get("OrientationToit");
         this.HauteurToit = save.valeursToit.get("HauteurToit");
         this.valeursToit = true;


      } catch (Exception e) {
          error = e;
      }
      if(!loadedSuccesfully){
          if(error != null) error.printStackTrace();
      }

    }
    
}
