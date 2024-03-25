/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resources;
import ca.ulaval.glo2004.cabanon.domaine.CabanonDTO;
import ca.ulaval.glo2004.cabanon.domaine.Controlleur;
import ca.ulaval.glo2004.cabanon.domaine.murs.AccessoireDTO;
import ca.ulaval.glo2004.cabanon.domaine.murs.EntremiseMurDTO;
import ca.ulaval.glo2004.cabanon.domaine.murs.Mur;
import ca.ulaval.glo2004.cabanon.domaine.murs.MurDTO;
import ca.ulaval.glo2004.cabanon.domaine.plancher.LigneEntremiseDTO;
import ca.ulaval.glo2004.cabanon.domaine.plancher.PlancherDTO;
import ca.ulaval.glo2004.cabanon.domaine.toit.ToitDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author raphael
 */
public class Sauvegarde implements Serializable{

    public Map<String, Double> valeursCabanon = new HashMap<>();
    public Map<String, Double> valeursPlancher = new HashMap<>();
    public Map<String, Double> valeursToit = new HashMap<>();
    public ArrayList<LigneEntremiseDTO> ligneEntremisesPlancher;
    public Map<Mur.orientations, Map<String, Double>> valeursMurs = new HashMap();
    public Map<Mur.orientations, ArrayList<AccessoireDTO>> AccessoiresMurs = new HashMap();
    public Map<Mur.orientations, ArrayList<EntremiseMurDTO>> EntremisesMurs = new HashMap();



    public Sauvegarde(Controlleur controlleur){
        //this.controlleur = controlleur;
        CabanonDTO cabanon = controlleur.getCabanon();
        valeursCabanon.put("hauteurMur", cabanon.HauteurMurs);
        ligneEntremisesPlancher = controlleur.getLignesEntremises();

        PlancherDTO plancher = controlleur.getPlancher();
        valeursPlancher.put("largeur", cabanon.Largeur);
        valeursPlancher.put("profondeur", cabanon.Profondeur);
        valeursPlancher.put("distanceSolives", plancher.EspacementSolives);

        for(Mur.orientations orientation : Mur.orientations.values()){
            Map<String, Double> map = new HashMap();
            MurDTO mur = controlleur.getMurByOrientation(orientation);
            map.put("espacementMontant", mur.EspacementMontants);
            valeursMurs.put(orientation, map);
            ArrayList<AccessoireDTO> accessoires = controlleur.getAccessoiresMur(orientation);
            AccessoiresMurs.put(orientation, accessoires);
            ArrayList<EntremiseMurDTO> entremises = controlleur.getEntremisesMur(orientation);
            EntremisesMurs.put(orientation, new ArrayList());
            for(EntremiseMurDTO entremise : entremises){
                System.out.println(entremise.AccessoireParent);
                if(entremise.AccessoireParent == null) EntremisesMurs.get(orientation).add(entremise);
            }
        }

        ToitDTO toit = controlleur.getToit();
        valeursToit.put("EspacementEntremisesDebor", toit.EspacementEntremisesDebor);
        valeursToit.put("EspacementFermes", toit.EspacementFermes);
        valeursToit.put("AngleToit", (double) toit.AngleToit);
        valeursToit.put("HauteurToit", toit.HauteurToit);
        valeursToit.put("PorteAFaux", toit.PorteAFaux);
        valeursToit.put("OrientationToit", (double) toit.OrientationToit);

  
    }

}
