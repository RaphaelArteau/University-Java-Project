/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.cabanon.domaine;

import ca.ulaval.glo2004.cabanon.domaine.toit.ToitDTO;
import ca.ulaval.glo2004.cabanon.domaine.plancher.PlancherDTO;
import ca.ulaval.glo2004.cabanon.domaine.murs.Accessoire;
import ca.ulaval.glo2004.cabanon.domaine.murs.BlocMur;
import ca.ulaval.glo2004.cabanon.domaine.Cabanon;
import ca.ulaval.glo2004.cabanon.domaine.murs.EntremiseMur;
import ca.ulaval.glo2004.cabanon.domaine.murs.Mur;
import ca.ulaval.glo2004.cabanon.domaine.toit.Fermes;
import ca.ulaval.glo2004.cabanon.domaine.CabanonDTO;
import ca.ulaval.glo2004.cabanon.domaine.murs.AccessoireDTO;
import ca.ulaval.glo2004.cabanon.domaine.murs.BlocMurDTO;
import ca.ulaval.glo2004.cabanon.domaine.murs.EntremiseMurDTO;
import ca.ulaval.glo2004.cabanon.domaine.murs.MurDTO;
import ca.ulaval.glo2004.cabanon.domaine.plancher.LigneEntremiseDTO;
import ca.ulaval.glo2004.cabanon.domaine.plancher.LigneEntremises;
import ca.ulaval.glo2004.cabanon.domaine.plancher.Plancher;
import ca.ulaval.glo2004.gui.MainWindow;
import ca.ulaval.glo2004.preferences.Preferences;
import static resources.UnitConverter.fractionator;
import static resources.UnitConverter.defractionator;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author Maxence
 */
public class Controlleur {

    private Cabanon cabanon;
    private MainWindow window;
    File valeursParDefaut;
    private ArrayList<TimeStamp> historiqueModifications;
    private int indexModifications;

    public Controlleur(File creationValues, MainWindow window) {
        this.cabanon = new Cabanon(creationValues);
        this.window = window;
        this.valeursParDefaut = creationValues;
        this.historiqueModifications = new ArrayList();
        this.sauvegardeEtat();
        this.sauvegardeEtat();
        this.indexModifications = 1;

    }

    public ArrayList<double[]> miseAJourCout() {
        return this.cabanon.calculListePieces();
    }

    /**
     *** INTERACTIONS CABANON
    *
     */
    // GETTERS CABANON
    public CabanonDTO getCabanon() {
        CabanonDTO cabanonDTO = new CabanonDTO(this.cabanon);
        return cabanonDTO;
    }

    public PlancherDTO getPlancher() {
        PlancherDTO plancherDTO = new PlancherDTO(this.cabanon.getPlancher());
        return plancherDTO;
    }

    public ToitDTO getToit() {
        ToitDTO toitDTO = new ToitDTO(this.cabanon.getToit());
        return toitDTO;
    }

    public String getLargeurCabanonString() {
        CabanonDTO cabanonDTO = new CabanonDTO(this.cabanon);
        return fractionator(cabanonDTO.Largeur);
    }

    public double getLargeurCabanon() {
        CabanonDTO cabanonDTO = new CabanonDTO(this.cabanon);
        return cabanonDTO.Largeur;
    }

    public String getProfondeurCabanonString() {
        CabanonDTO cabanonDTO = new CabanonDTO(this.cabanon);
        return fractionator(cabanonDTO.Profondeur);
    }

    public double getProfondeurCabanon() {
        CabanonDTO cabanonDTO = new CabanonDTO(this.cabanon);
        return cabanonDTO.Profondeur;
    }

    public MurDTO getMurByOrientation(Mur.orientations orientation) {
        Mur mur = this.cabanon.getMurs().get(orientation);
        return new MurDTO(mur);
    }

    public double getHauteurMursCabanon() {
        CabanonDTO cabanonDTO = new CabanonDTO(this.cabanon);
        return cabanonDTO.HauteurMurs;
    }

    public String getHauteurMursString() {
        CabanonDTO cabanonDTO = new CabanonDTO(this.cabanon);
        return fractionator(cabanonDTO.HauteurMurs);
    }

    public double getCout() {
        CabanonDTO cabanonDTO = new CabanonDTO(this.cabanon);
        return cabanonDTO.Cout;
    }

    //SETTERS CABANON
    public void setLargeurCabanon(String largeurString) {
        double largeur = defractionator(largeurString);
        if (largeur >= 4) {
            this.cabanon.setLargeur(largeur);

            recalculerMurs();
            this.cabanon.getPlancher().setLargeur(largeur);
            //this.sauvegardeEtat();
            this.recalculCabanon();
        }
    }

    public void setProfondeurCabanon(String profondeurString) {
        double profondeur = defractionator(profondeurString);
        if (profondeur >= 4) {
            this.cabanon.setProfondeur(profondeur);
            this.cabanon.getPlancher().setProfondeur(profondeur);
            this.sauvegardeEtat();
            this.recalculCabanon();
        }
    }

    public void setHauteurMurs(String hauteurString) {
        double hauteur = defractionator(hauteurString);
        if (hauteur >= 4) {
            this.cabanon.setHauteurMurs(hauteur);
            recalculerMurs();
            this.sauvegardeEtat();
        }
    }

    public void setDimensionsParDefaut() {
        Cabanon cabanonDefaut = new Cabanon(this.valeursParDefaut);
        CabanonDTO cabanonDTO = new CabanonDTO(cabanonDefaut);
        this.cabanon.setLargeur(cabanonDTO.Largeur);
        this.cabanon.setProfondeur(cabanonDTO.Profondeur);
        this.cabanon.setHauteurMurs(cabanonDTO.HauteurMurs);
        this.recalculCabanon();
        this.historiqueModifications = null;
        this.historiqueModifications = new ArrayList();
        this.indexModifications = 0;

    }

    //INTERACTIONS CABANON
    public void recalculCabanon() {
        this.cabanon.getPlancher().miseAJourPlancher();
        this.recalculerMurs();
        this.cabanon.getToit().MiseAJourToit();
        miseAJourCout();
    }

    /**
     *** INTERACTIONS MURS
    *
     */
    // GETTERS MURS
    public Mur.orientations getOrientationMur(Mur mur) {
        MurDTO murDTO = new MurDTO(mur);
        return murDTO.OrientationMur;
    }

    public double getEspacementMontantsMur(Mur.orientations orientation) {
        MurDTO murDTO = null;
        murDTO = new MurDTO(this.cabanon.getMur(orientation));
        return murDTO.EspacementMontants;
    }

    public String getEspacementMontantsMurString(Mur.orientations orientation) {
        MurDTO murDTO = null;
        murDTO = new MurDTO(this.cabanon.getMur(orientation));
        return fractionator(murDTO.EspacementMontants);
    }

    public ArrayList<ArrayList<Double>> getCoordMontantsMur(Mur.orientations orientation) {
        MurDTO murDTO = null;
        murDTO = new MurDTO(this.cabanon.getMur(orientation));
        return murDTO.CoordMontants;
    }

    public ArrayList<EntremiseMurDTO> getEntremisesMur(Mur.orientations orientation) {
        MurDTO murDTO;
        murDTO = new MurDTO(this.cabanon.getMur(orientation));
        ArrayList<EntremiseMurDTO> listeEntremise = new ArrayList();
        for (EntremiseMur entremises : murDTO.Entremises) {
            EntremiseMurDTO entremiseTmp = new EntremiseMurDTO(entremises);
            listeEntremise.add(entremiseTmp);
        }
        return listeEntremise;
    }

    public EntremiseMurDTO getEntremiseMur(Mur.orientations orientation, UUID id) {
        Mur mur = this.cabanon.getMur(orientation);
        ArrayList<EntremiseMur> entremises = mur.getEntremises();
        EntremiseMurDTO entremise = null;
        for (EntremiseMur e : entremises) {
            if (e.getId() == id) {
                entremise = new EntremiseMurDTO(e);
            }
        }
        return entremise;
    }

    public EntremiseMurDTO getEntremiseMur(UUID id) {
        Map<Mur.orientations, Mur> murs = this.cabanon.getMurs();
        for (Mur mur : murs.values()) {
            ArrayList<EntremiseMur> entremises = mur.getEntremises();
            for (EntremiseMur e : entremises) {
                if (e.getId() == id) {
                    return new EntremiseMurDTO(e);
                }
            }
        }
        return null;
    }

    public AccessoireDTO getAccParentEntremise(Mur.orientations orientation, UUID idEntremise) {
        Mur mur = this.cabanon.getMur(orientation);
        AccessoireDTO accessoireDTO = null;
        for (EntremiseMur entremise : mur.getEntremises()) {
            if (idEntremise == entremise.getId()) {
                accessoireDTO = new AccessoireDTO(entremise.getAccessoireParent());

            }
        }
        return accessoireDTO;
    }

    public void setHauteurAccessoire(Mur.orientations orientation, UUID idAccessoire, double hauteur) {
        for (Accessoire accessoire : this.cabanon.getMur(orientation).getAccessoire()) {
            if (idAccessoire == accessoire.getIdAccessoire()) {
                accessoire.setHauteurContour(hauteur);
            }
        }
        this.recalculerMurs();
        this.sauvegardeEtat();
    }

    public void setTailleLinteauAcc(Mur.orientations orientation, UUID idAccessoire, Accessoire.tailleLinteaux tailleLinteau) {
        for (Accessoire accessoire : this.cabanon.getMur(orientation).getAccessoire()) {
            if (idAccessoire == accessoire.getIdAccessoire()) {
                accessoire.setTailleLinteau(tailleLinteau);
            }
        }
        this.recalculerMurs();
        this.sauvegardeEtat();
    }

    public void setLargeurAccessoire(Mur.orientations orientation, UUID idAccessoire, double largeur) {
        for (Accessoire accessoire : this.cabanon.getMur(orientation).getAccessoire()) {
            if (idAccessoire == accessoire.getIdAccessoire()) {
                accessoire.setLargeurContour(largeur);
            }
        }
        this.recalculerMurs();
    }

    public AccessoireDTO getAccessoireFromUUID(Mur.orientations orientation, UUID idAccessoire) {
        AccessoireDTO accessoireDTO = null;
        MurDTO murDTO;
        murDTO = new MurDTO(this.cabanon.getMur(orientation));
        for (Accessoire accessoire : this.cabanon.getMur(orientation).getAccessoire()) {
            if (accessoire.getIdAccessoire() == idAccessoire) {
                accessoireDTO = new AccessoireDTO(accessoire);
            }
        }
        return accessoireDTO;
    }

    public AccessoireDTO getAccessoireFromUUID(UUID idAccessoire) {
        AccessoireDTO accessoireDTO = null;
        for (Mur.orientations orientation : Mur.orientations.values()) {
            for (Accessoire accessoire : this.cabanon.getMur(orientation).getAccessoire()) {
                if (accessoire.getIdAccessoire() == idAccessoire) {
                    accessoireDTO = new AccessoireDTO(accessoire);
                    break;
                }
            }
            if (accessoireDTO != null) {
                break;
            }
        }
        return accessoireDTO;
    }

    public ArrayList<AccessoireDTO> getAccessoiresMur(Mur.orientations orientation) {
        MurDTO murDTO;
        murDTO = new MurDTO(this.cabanon.getMur(orientation));
        ArrayList<AccessoireDTO> listeAccessoire = new ArrayList();
        for (Accessoire accessoires : murDTO.Accessoires) {
            AccessoireDTO accessoireTmp = new AccessoireDTO(accessoires);
            listeAccessoire.add(accessoireTmp);
        }
        return listeAccessoire;
    }

    public int getAccessoireCountMur(Mur.orientations orientation) {
        return this.cabanon.getMur(orientation).getAccessoire().size();
    }

    public int getEntremiseCountMur(Mur.orientations orientation) {
        return this.cabanon.getMur(orientation).getEntremises().size();
    }

    public ArrayList<ArrayList<BlocMurDTO>> getLignesBlocMurs(Mur.orientations orientation) {

        MurDTO murDTO;
        murDTO = new MurDTO(this.cabanon.getMur(orientation));
        ArrayList<ArrayList<BlocMurDTO>> listeBlocs = new ArrayList();
        for (ArrayList<BlocMur> ligneBlocs : murDTO.ListeDeBlocs) {
            ArrayList<BlocMurDTO> ligneBlocsDTO = new ArrayList();
            listeBlocs.add(ligneBlocsDTO);
            for (BlocMur blocs : ligneBlocs) {
                BlocMurDTO blocMurTmp = new BlocMurDTO(blocs);
                ligneBlocsDTO.add(blocMurTmp);
            }
        }
        return listeBlocs;
    }

    // SETTERS MURS
    public void setEspacementMontantsMur(Mur.orientations orientation, String espacementString) {
        double espacement = defractionator(espacementString);
        if (espacement > 2) {
            Mur mur = this.cabanon.getMur(orientation);
            mur.setEspacementMontants(espacement);
            recalculerMurs();
            this.sauvegardeEtat();
        }
    }

    public void setEspacementMontantsAcc(Mur.orientations orientation, UUID idAccessoire, double espacement) {
        for (Accessoire accessoire : this.cabanon.getMur(orientation).getAccessoire()) {
            if (idAccessoire == accessoire.getIdAccessoire()) {
                accessoire.setEspacementMontants(espacement);
            }
        }
        recalculerMurs();
        this.sauvegardeEtat();
    }

    // DÉPLACEMENTS MURS
    public void deplacerAccessoire(Mur.orientations orientation, UUID idAccessoire, String coordXString, String coordYString, boolean saveTimeStamp) {
        double coordX = defractionator(coordXString);
        double coordY = defractionator(coordYString);
        for (Accessoire accessoire : this.cabanon.getMur(orientation).getAccessoire()) {
            if (idAccessoire == accessoire.getIdAccessoire()) {
                accessoire.setcoordAccessoireX(coordX);
                accessoire.setcoordAccessoireY(coordY);
                recalculerMurs();
                if (saveTimeStamp) this.sauvegardeEtat();
            }
        }
    }

    public void deplacerAccessoire(Mur.orientations orientation, UUID idAccessoire, String coordXString, String coordYString) {
        this.deplacerAccessoire(orientation, idAccessoire, coordXString, coordYString, true);
    }

    public void deplacerEntremiseMur(Mur.orientations orientation, UUID idEntremise, String coordXString, String coordYString, boolean saveTimeStamp) {
        // System.out.println(coordXString);
        double coordX = defractionator(coordXString);
        double coordY = defractionator(coordYString);
        //System.out.println(coordX);
        //System.out.println(coordY);
        for (EntremiseMur entremise : this.cabanon.getMur(orientation).getEntremises()) {
            if (idEntremise == entremise.getId()) {
                entremise.setCoordEntremiseX(coordX);
                entremise.setCoordEntremiseY(coordY);
                if(saveTimeStamp && entremise.getAccessoireParent()==null) this.sauvegardeEtat();
            }
        }
    }
    public void deplacerEntremiseMur(Mur.orientations orientation, UUID idEntremise, String coordXString, String coordYString) {
        this.deplacerEntremiseMur(orientation, idEntremise, coordXString, coordYString, true);
    }

    public void deplacerLigneEntremise(UUID id, String coordXString, boolean saveTimeStamp) {
        double coordX = defractionator(coordXString);
        for (LigneEntremises ligne : this.cabanon.getPlancher().getLignesEntremises()) {
            if (ligne.getUUID() == id) {
                ligne.setCoordXLigneEntremise(coordX);
                if(saveTimeStamp){this.sauvegardeEtat();}
            }
        }
    }
    public void deplacerLigneEntremise(UUID id, String coordXString){
        this.deplacerLigneEntremise(id, coordXString, true);
    }

// Ajout/suppression d'éléments
    public UUID ajouterAccessoire(Mur.orientations orientation, int type) {
        //System.out.print(type);
        if (type == 1 || type == 2) {
            Accessoire nouvelAccessoire = this.cabanon.getMur(orientation).ajouterAccessoire(type);
            this.sauvegardeEtat();
            return nouvelAccessoire.getIdAccessoire();
        }
        return null;
    }

    public ArrayList<AccessoireDTO> genererListeAccessoireParLigne(Mur.orientations orientation, int ligne) {
        ArrayList<AccessoireDTO> listeAccessoiresDTO = new ArrayList();
        for (Accessoire accessoire : this.cabanon.getMur(orientation).genererListeAccessoireParLigne(ligne)) {
            AccessoireDTO accessoireDTO = new AccessoireDTO(accessoire);
            listeAccessoiresDTO.add(accessoireDTO);
        }
        return listeAccessoiresDTO;
    }

    public void supprimerAccessoire(UUID id) {

        for (Mur.orientations orientation : Mur.orientations.values()) {
            for (int i = 0; i < this.cabanon.getMur(orientation).getAccessoire().size(); i++) {
                if (this.cabanon.getMur(orientation).getAccessoire().get(i).getIdAccessoire() == id) {
                    this.cabanon.getMur(orientation).supprimerAccessoire(id);
                }
            }
        }
        this.sauvegardeEtat();
    }

    public UUID ajouterEntremiseMur(Mur.orientations orientation) {
        EntremiseMur nouvelEntremise = this.cabanon.getMur(orientation).ajouterEntremiseMur();
        this.sauvegardeEtat();
        return nouvelEntremise.getId();
    }

    public void supprimerEntremiseMur(Mur.orientations orientation, UUID id) {
        this.cabanon.getMur(orientation).supprimerEntremiseMur(id);
        this.sauvegardeEtat();
    }

    public void recalculerMurs() {
        for (Mur.orientations orientation : Mur.orientations.values()) {
            this.cabanon.getMur(orientation).recalculerPositionToutElement();
        }

    }

    //Interactions toit
    public int getOrientationToit() {
        ToitDTO toitDTO;
        toitDTO = new ToitDTO(this.cabanon.getToit());
        return toitDTO.OrientationToit;
    }

    //  public void setOrientationToit(int orientation){
    //     Toit toit = this.cabanon.getToit();
    //    toit.setOrientationToit(orientation);}
    public double getAngleToit() {
        ToitDTO toitDTO;
        toitDTO = new ToitDTO(this.cabanon.getToit());
        return toitDTO.AngleToit;
    }

    public double getLongueurDebordement() {
        ToitDTO toitDTO;
        toitDTO = new ToitDTO(this.cabanon.getToit());
        return toitDTO.LongueurDebordement;
    }

    public double getNombreEntremiseDebor() {
        ToitDTO toitDTO;
        toitDTO = new ToitDTO(this.cabanon.getToit());
        return toitDTO.nombreEntremises;
    }

    public double getEspacementFermes() {
        ToitDTO toitDTO;
        toitDTO = new ToitDTO(this.cabanon.getToit());
        return toitDTO.EspacementFermes;
    }

    public double getEspacementEntremiseDebord() {
        ToitDTO toitDTO;
        toitDTO = new ToitDTO(this.cabanon.getToit());
        //System.out.println(toitDTO.EspacementEntremisesDebor);
        return toitDTO.EspacementEntremisesDebor;
    }

    public ArrayList<Fermes> getFermes() {
        ToitDTO toitDTO;
        toitDTO = new ToitDTO(this.cabanon.getToit());
        return toitDTO.Fermes;
    }

    public double getPorteAFaux() {
        ToitDTO toitDTO;
        toitDTO = new ToitDTO(this.cabanon.getToit());
        return toitDTO.PorteAFaux;
    }

    public ArrayList<Double> getEntremiseDebord() {
        ToitDTO toitDTO;
        toitDTO = new ToitDTO(this.cabanon.getToit());
        return toitDTO.EntremisesDebor;
    }

    public ArrayList<Double> getCoordFermes() {
        ToitDTO toitDTO;
        toitDTO = new ToitDTO(this.cabanon.getToit());
        return toitDTO.CoordFermes;
    }
    
    public ArrayList<Double> getCoordFermesOrdo(){
        ToitDTO toitDTO;
        toitDTO = new ToitDTO(this.cabanon.getToit());
        return toitDTO.CoordFermesOrdo;
    }
    
    public ArrayList<Double> getCoordEntremisesOrdo(){
        ToitDTO toitDTO = new ToitDTO(this.cabanon.getToit());
        return toitDTO.CoordEntremisesOrdo;
    }

    public double getHauteurToit() {
        ToitDTO toitDTO;
        toitDTO = new ToitDTO(this.cabanon.getToit());
        return toitDTO.HauteurToit;
    }

    public String getHauteurTotaleCabanonString() {
        double hauteurTotale = this.getHauteurToit() + this.getHauteurMursCabanon();
        //System.out.println(hauteurTotale);
        return fractionator(hauteurTotale);
    }

    public double getTailleChevronUnitaire() {
        ToitDTO toitDTO;
        toitDTO = new ToitDTO(this.cabanon.getToit());
        return toitDTO.chevrons;
    }

    public double getPoinconsPrincipal() {
        ToitDTO toitDTO;
        toitDTO = new ToitDTO(this.cabanon.getToit());
        return toitDTO.poinconsPrincipal;
    }

    public double getPoinconsSupp() {
        ToitDTO toitDTO;
        toitDTO = new ToitDTO(this.cabanon.getToit());
        return toitDTO.poinconsSupp;
    }

    public ArrayList<Double> getCoordPoinconsX() {
        ToitDTO toitDTO;
        toitDTO = new ToitDTO(this.cabanon.getToit());
        return toitDTO.CoordPoinconsX;
    }

    public ArrayList<Double> getCoordPoinconsY() {
        ToitDTO toitDTO;
        toitDTO = new ToitDTO(this.cabanon.getToit());
        return toitDTO.CoordPoinconsY;
    }

    public void setAngleToit(String angle) {
        DecimalFormat df = new DecimalFormat("#.#");
        double nvelAngle = Double.parseDouble(df.format(Double.parseDouble(angle.replaceAll(",","."))));
        if (nvelAngle >= 0) {
            this.cabanon.getToit().setAngle(nvelAngle % 179.999);
            this.sauvegardeEtat();
        }
    }

    public void setEspacementFermes(String espacement) {
        double nvelEspacement = defractionator(espacement);
        if (nvelEspacement >= 2) {
            this.cabanon.getToit().setEspacementFermes(nvelEspacement);
            this.sauvegardeEtat();
        }
    }

    public void setEspacementEntDebord(String espacement) {
        double nvelEspacement = defractionator(espacement);
        if (nvelEspacement >= 2) {
            this.cabanon.getToit().setEspacementEntremisesDebor(nvelEspacement);
            this.sauvegardeEtat();
        }

    }

    public void setLongueurDebordement(String longueur) {
        double nvelleLongueur = defractionator(longueur);
        if (nvelleLongueur >= 2) {
            this.cabanon.getToit().setLongueurDebor(nvelleLongueur);
            this.sauvegardeEtat();
        }

    }

    public void setPorteAFaux(String porteAFaux) {
        double nveauPorteAFaux = defractionator(porteAFaux);
        if (nveauPorteAFaux >= 0) {
            this.cabanon.getToit().setPorteAfaux(nveauPorteAFaux);
            this.sauvegardeEtat();
        }
    }

    public void setOrientationToit(int nvelleOrientation) {
        if (nvelleOrientation == 1 || nvelleOrientation == 2) {
            this.cabanon.getToit().setOrientationToit((int) nvelleOrientation);
            this.sauvegardeEtat();
        }
    }

    /**
     *** INTERACTIONS PLANCHER
    *
     */
    // GETTERS PLANCHER
    public double getDernierBlocLargeur() {
        PlancherDTO plancherDTO;
        plancherDTO = new PlancherDTO(this.cabanon.getPlancher());
        return plancherDTO.DernierBlocLargeur;
    }

    public double getDernierBlocProfondeur() {
        PlancherDTO plancherDTO;
        plancherDTO = new PlancherDTO(this.cabanon.getPlancher());
        return plancherDTO.DernierBlocProfondeur;
    }

    public double getEspacementSolives() {
        PlancherDTO plancherDTO;
        plancherDTO = new PlancherDTO(this.cabanon.getPlancher());
        return plancherDTO.EspacementSolives;
    }

    public String getEspacementSolivesString() {
        PlancherDTO plancherDTO;
        plancherDTO = new PlancherDTO(this.cabanon.getPlancher());
        return fractionator(plancherDTO.EspacementSolives);
    }

    public int[] getDimensionGridBlocPlancher() {
        PlancherDTO plancherDTO;
        plancherDTO = new PlancherDTO(this.cabanon.getPlancher());
        return plancherDTO.DimensionGridBlocPlancher;
    }

    public ArrayList<Double> getCoordYSolives() {
        PlancherDTO plancherDTO;
        plancherDTO = new PlancherDTO(this.cabanon.getPlancher());
        return plancherDTO.CoordYSolives;
    }

    public ArrayList<LigneEntremiseDTO> getLignesEntremises() {
        PlancherDTO plancherDTO;
        LigneEntremiseDTO ligneEntremiseDTO;
        ArrayList<LigneEntremiseDTO> listeLignesDTO = new ArrayList();
        plancherDTO = new PlancherDTO(this.cabanon.getPlancher());
        for (LigneEntremises ligne : plancherDTO.LignesEntremises) {
            LigneEntremiseDTO ligneDTO = new LigneEntremiseDTO(ligne);
            listeLignesDTO.add(ligneDTO);
        }
        return listeLignesDTO;
    }

    public LigneEntremiseDTO getLigneEntremises(UUID id) {
        PlancherDTO plancherDTO;
        LigneEntremiseDTO ligneEntremiseDTO;
        plancherDTO = new PlancherDTO(this.cabanon.getPlancher());
        LigneEntremises ligneEntremise = null;
        for (LigneEntremises ligne : plancherDTO.LignesEntremises) {
            if (ligne.getUUID() == id) {
                ligneEntremise = ligne;
            }
        }
        ligneEntremiseDTO = new LigneEntremiseDTO(ligneEntremise);
        return ligneEntremiseDTO;
    }

    public void setcoordXLigneEntremise(UUID id, double coordX) {
        for (LigneEntremises ligneEntremise : this.cabanon.getPlancher().getLignesEntremises()) {
            if (ligneEntremise.getUUID() == id) {
                ligneEntremise.setCoordXLigneEntremise(coordX);
                this.sauvegardeEtat();
            }
        }
    }


    /* public Cabanon getParent(){
       PlancherDTO plancherDTO;
       plancherDTO = new PlancherDTO(this.cabanon.getPlancher());
       return plancherDTO.Parent;
    }*/
    public UUID getUuid() {
        PlancherDTO plancherDTO;
        plancherDTO = new PlancherDTO(this.cabanon.getPlancher());
        return plancherDTO.Uuid;
    }

    // SETTERS PLANCHER
    public void setEspacementSolivesPlancher(String nouvelEspacement) {
        this.cabanon.getPlancher().setEspacementSolives(defractionator(nouvelEspacement));
        this.sauvegardeEtat();
    }

    // ACCESSOIRES PLANCHER
    public UUID ajouterLigneEntremises(double coordY) {
        UUID idLigne = this.cabanon.getPlancher().ajouterLigneEntremises(coordY);
        this.sauvegardeEtat();
        return idLigne;

    }

    public UUID ajouterLigneEntremises() {
        UUID idLigne = this.cabanon.getPlancher().ajouterLigneEntremises((double) this.getLargeurCabanon() / (double) 2);
        this.sauvegardeEtat();
        return idLigne;
    }

    public void supprimerLigneEntremises(UUID id) {
        this.cabanon.getPlancher().supprimerLigneEntremises(id);
        this.sauvegardeEtat();
    }

    /**
     *** INTERACTIONS PREFERENCES
    *
     */
    //GETTERS
    public String getLongueurMax24() {
        return fractionator(Preferences.getLongueurMax24());
    }

    public String getLongueurMax26() {
        return fractionator(Preferences.getLongueurMax26());
    }

    public String getLongueurMax28() {
        return fractionator(Preferences.getLongueurMax28());
    }

    public String getLongueurMax210() {
        return fractionator(Preferences.getLongueurMax210());
    }

    //SETTERS
    public void setLongueurMax24(String longueurMax) {
        Preferences.setLongueurMax24(defractionator(longueurMax));
        this.sauvegardeEtat();
    }

    public void setLongueurMax26(String longueurMax) {
        Preferences.setLongueurMax26(defractionator(longueurMax));
        this.sauvegardeEtat();
    }

    public void setLongueurMax28(String longueurMax) {
        Preferences.setLongueurMax28(defractionator(longueurMax));
        this.sauvegardeEtat();
    }

    public void setLongueurMax210(String longueurMax) {
        Preferences.setLongueurMax210(defractionator(longueurMax));
        this.sauvegardeEtat();
    }

    //UNDO/REDO
    public void sauvegardeEtat() {
        //System.out.println("prout");
        this.indexModifications++;
        TimeStamp timeStamp = new TimeStamp();
        timeStamp.largeur = this.getLargeurCabanon();
        timeStamp.profondeur = this.getProfondeurCabanon();
        timeStamp.hauteurMurs = this.getHauteurMursCabanon();
        timeStamp.orientationToit = this.getOrientationToit();
        timeStamp.angleToit = this.getAngleToit();
        timeStamp.EspacementSolivesPlancher = this.getEspacementSolives();
        timeStamp.espacementFermes = this.getEspacementFermes();
        timeStamp.Debordement = this.getLongueurDebordement();
        timeStamp.porteAFaux = this.getPorteAFaux();
        timeStamp.EspacementEntDebord = this.getEspacementEntremiseDebord();
        timeStamp.EspacementMurNord = this.getEspacementMontantsMur(Mur.orientations.Nord);
        timeStamp.EspacementMurSud = this.getEspacementMontantsMur(Mur.orientations.Sud);
        timeStamp.EspacementMurEst = this.getEspacementMontantsMur(Mur.orientations.Est);
        timeStamp.EspacementMurOuest = this.getEspacementMontantsMur(Mur.orientations.Ouest);
        timeStamp.listeLignesEntremises = this.getLignesEntremises();
        timeStamp.listeAccNord = this.getAccessoiresMur(Mur.orientations.Nord);
        timeStamp.listeAccSud = this.getAccessoiresMur(Mur.orientations.Sud);
        timeStamp.listeAccEst = this.getAccessoiresMur(Mur.orientations.Est);
        timeStamp.listeAccOuest = this.getAccessoiresMur(Mur.orientations.Ouest);
        timeStamp.listeEntNord = this.getEntremisesMur(Mur.orientations.Nord);
        timeStamp.listeEntSud = this.getEntremisesMur(Mur.orientations.Sud);
        timeStamp.listeEntEst = this.getEntremisesMur(Mur.orientations.Est);
        timeStamp.listeEntOuest = this.getEntremisesMur(Mur.orientations.Ouest);

        for (int i = this.historiqueModifications.size() - 1; i > indexModifications; i--) {
            this.historiqueModifications.remove(i);
        }

        this.historiqueModifications.add(timeStamp);
        
    }

    private void appliquerTimeStamp(TimeStamp timeStamp) {
        //System.out.println(timeStamp.id);
        //System.out.println(this.indexModifications);
        this.cabanon.setLargeur(timeStamp.largeur);
        this.cabanon.setProfondeur(timeStamp.profondeur);
        this.cabanon.getPlancher().setLargeur(timeStamp.largeur);
        this.cabanon.getPlancher().setProfondeur(timeStamp.profondeur);
        this.cabanon.setHauteurMurs(timeStamp.hauteurMurs);
        this.cabanon.getToit().setOrientationToit(timeStamp.orientationToit);
        this.cabanon.getToit().setAngle(timeStamp.angleToit);
        this.cabanon.getPlancher().setEspacementSolives(timeStamp.EspacementSolivesPlancher);
        this.cabanon.getToit().setEspacementFermes(timeStamp.espacementFermes);
        this.cabanon.getToit().setLongueurDebor(timeStamp.Debordement);
        this.cabanon.getToit().setPorteAfaux(timeStamp.porteAFaux);
        this.cabanon.getToit().setEspacementEntremisesDebor(timeStamp.EspacementEntDebord);
        this.cabanon.getMur(Mur.orientations.Nord).setEspacementMontants(timeStamp.EspacementMurNord);
        this.cabanon.getMur(Mur.orientations.Sud).setEspacementMontants(timeStamp.EspacementMurSud);
        this.cabanon.getMur(Mur.orientations.Est).setEspacementMontants(timeStamp.EspacementMurEst);
        this.cabanon.getMur(Mur.orientations.Ouest).setEspacementMontants(timeStamp.EspacementMurOuest);
        this.window.setAllPaneVisibility(false);
        Mur.orientations orientationMur = Mur.orientations.Nord;
        ArrayList<AccessoireDTO> listeAccTimeStamp = new ArrayList();
        ArrayList<EntremiseMurDTO> listeEntTimeStamp = new ArrayList();
        for (Mur.orientations orientation : Mur.orientations.values()) {
            if (orientation == orientation.Nord) {
                orientationMur = Mur.orientations.Nord;
                listeAccTimeStamp = timeStamp.listeAccNord;
                listeEntTimeStamp = timeStamp.listeEntNord;
            }
            if (orientation == orientation.Sud) {
                orientationMur = Mur.orientations.Sud;
                listeAccTimeStamp = timeStamp.listeAccSud;
                listeEntTimeStamp = timeStamp.listeEntSud;
            }
            if (orientation == orientation.Est) {
                orientationMur = Mur.orientations.Est;
                listeAccTimeStamp = timeStamp.listeAccEst;
                listeEntTimeStamp = timeStamp.listeEntEst;
            }
            if (orientation == orientation.Ouest) {
                orientationMur = Mur.orientations.Ouest;
                listeAccTimeStamp = timeStamp.listeAccOuest;
                listeEntTimeStamp = timeStamp.listeEntOuest;
            }
            // On supprime toutes les lignes d'entremises
            int nombreLignes = this.cabanon.getPlancher().getLignesEntremises().size();
            for (int x = 0; x < nombreLignes; x++) {
                this.cabanon.getPlancher().supprimerLigneEntremises(this.cabanon.getPlancher().getLignesEntremises().get(x).getUUID());
            }
            // On crée un objet ligneEntremises pour chaque objet ligneEntremiseDTO du timeStamp
            for (LigneEntremiseDTO ligneTimeStamp : timeStamp.listeLignesEntremises) {
                LigneEntremises ligne = new LigneEntremises(ligneTimeStamp.CoordXLigneEntremise);
                ligne.setConflit(ligneTimeStamp.Conflit);
                ligne.setIdLigneEntremise(ligneTimeStamp.Id);
                this.cabanon.getPlancher().getLignesEntremises().add(ligne);
                this.window.setPaneVisibility(ligneTimeStamp.Id, true);
            }

            // On supprime tous les accessoires
            while (this.cabanon.getMur(orientationMur).getAccessoire().size() > 0) {
                this.cabanon.getMur(orientationMur).supprimerAccessoire(this.cabanon.getMur(orientation).getAccessoire().get(0).getIdAccessoire());
            }

            // On supprime chaque entremiseMur du mur puis on l'ajoute au domaine
            while (this.cabanon.getMur(orientationMur).getEntremises().size() > 0) {
                this.cabanon.getMur(orientationMur).supprimerEntremiseMur(this.cabanon.getMur(orientationMur).getEntremises().get(0).getId());
            }

            // On crée un objet Accessoire à partir de chaque objet AccessoireDTO du timeStamp
            Map<UUID, Accessoire> dictionnaireAccessoire = new HashMap();
            for (AccessoireDTO accessoireTimeStamp : listeAccTimeStamp) {
                Accessoire accessoire = new Accessoire(this.cabanon.getMur(orientationMur), accessoireTimeStamp.TypeAccessoire);
                accessoire.setIdAccessoire(accessoireTimeStamp.Id);
                accessoire.setcoordAccessoireX(accessoireTimeStamp.CoordAccessoireX);
                accessoire.setcoordAccessoireY(accessoireTimeStamp.CoordAccessoireY);
                accessoire.setLargeurContour(accessoireTimeStamp.LargeurContour);
                //System.out.println(accessoire.getLargeurContour());
                accessoire.setHauteurContour(accessoireTimeStamp.HauteurContour);
                accessoire.setEspacementMontants(accessoireTimeStamp.EspacementMontants);
                accessoire.setTailleLinteau(accessoireTimeStamp.TailleLinteau);
                accessoire.setConflit(accessoireTimeStamp.Conflit);
                //On ajoute l'accessoire au domaine
                dictionnaireAccessoire.put(accessoire.getIdAccessoire(), accessoire);
                this.cabanon.getMur(orientationMur).getAccessoire().add(accessoire);
                this.window.setPaneVisibility(accessoireTimeStamp.Id, true);
            }

            for (EntremiseMurDTO entremiseTimeStamp : listeEntTimeStamp) {
                if(entremiseTimeStamp.AccessoireParent == null){
                    EntremiseMur entremiseMur = new EntremiseMur(this.cabanon.getMur(orientationMur));
                    entremiseMur.setIdEntremiseMur(entremiseTimeStamp.Id);
                    entremiseMur.setCoordEntremiseX(entremiseTimeStamp.CoordEntremiseX);
                    entremiseMur.setCoordEntremiseY(entremiseTimeStamp.CoordEntremiseY);
                    entremiseMur.setConflit(entremiseTimeStamp.Conflit);
                    this.window.setPaneVisibility(entremiseTimeStamp.Id, true);
                    this.cabanon.getMur(orientation).getEntremises().add(entremiseMur);
                }
                //this.window.setPaneVisibility(entremiseTimeStamp.Id, true);
            }
        }
        this.recalculerMurs();
        this.window.refreshVisualizer();
    }

    public void undo() {
        if (this.indexModifications - 1 > -1) {
            this.indexModifications--;
            TimeStamp timeStamp = this.historiqueModifications.get(indexModifications);

            this.appliquerTimeStamp(timeStamp);

        }
    }

    public void redo() {
        if (this.historiqueModifications.size() > indexModifications + 1) {
            this.indexModifications++;
            TimeStamp timeStamp = this.historiqueModifications.get(indexModifications);

            this.appliquerTimeStamp(timeStamp);
        } else {
            TimeStamp timeStamp = this.historiqueModifications.get(this.historiqueModifications.size() - 1);
            this.appliquerTimeStamp(timeStamp);
        }
    }

    public void unselectAccessories() {
        Plancher plancher = this.cabanon.getPlancher();
        for (LigneEntremises ligne : plancher.getLignesEntremises()) {
            ligne.setSelected(false);
        }
        Map<Mur.orientations, Mur> murs = this.cabanon.getMurs();
        for (Mur mur : murs.values()) {
            for (Accessoire acc : mur.getAccessoire()) {
                acc.setSelected(false);
            }
            for (EntremiseMur entre : mur.getEntremises()) {
                entre.setSelected(false);
            }
        }
    }

    public void selectAccessory(AccessoireDTO accessoire) {
        Mur.orientations orientation = accessoire.orientation;
        Mur mur = this.cabanon.getMur(orientation);
        for (Accessoire acc : mur.getAccessoire()) {
            if (acc.getIdAccessoire() == accessoire.Id) {
                acc.setSelected(true);
                break;
            }
        }
    }

    public void selectLigneEntremise(LigneEntremiseDTO ligne) {
        Plancher plancher = this.cabanon.getPlancher();
        for (LigneEntremises l : plancher.getLignesEntremises()) {
            if (l.getUUID() == ligne.Id) {
                l.setSelected(true);
            }
        }
    }

    public void selectEntremiseMur(EntremiseMurDTO entremise) {
        Mur.orientations orientation = entremise.orientation;
        Mur mur = this.cabanon.getMur(orientation);
        for (EntremiseMur entre : mur.getEntremises()) {
            if (entre.getId() == entremise.Id) {
                entre.setSelected(true);
                break;
            }
        }
    }

}
