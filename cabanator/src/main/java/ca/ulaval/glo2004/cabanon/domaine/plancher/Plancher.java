package ca.ulaval.glo2004.cabanon.domaine.plancher;

import ca.ulaval.glo2004.cabanon.domaine.Cabanon;
import ca.ulaval.glo2004.preferences.Preferences;
import java.util.UUID;
import java.util.ArrayList;

public class Plancher {
    
    private double profondeur;
    private double largeur;
    private double dernierBlocLargeur;
    private double dernierBlocProfondeur;
    private final int[] dimensionGridBlocPlancher;
    private ArrayList<Double> coordYSolives;
    private double espacementSolives = 18;
    private ArrayList<LigneEntremises> lignesEntremises;
    private final Cabanon parent;
    private UUID uuid;
    
    public Plancher(Cabanon parent){
        this.parent = parent;
        this.largeur = (double) this.parent.getLargeur();
        this.profondeur = (double) this.parent.getProfondeur();
        this.dimensionGridBlocPlancher = new int[2];
        this.espacementSolives = 18;
        this.lignesEntremises = new ArrayList();
        gestionBlocsPlancher();
        gestionSolives();
    }
    
    public void miseAJourPlancher(){
        gestionBlocsPlancher();
        gestionSolives();
    }
    
    private void gestionBlocsPlancher(){
        double dimensionMax = Preferences.global().getLongueurMax26();
        this.dimensionGridBlocPlancher[0] = (int) Math.ceil((double)this.largeur / dimensionMax);
        this.dimensionGridBlocPlancher[1] = (int) Math.ceil((double)this.profondeur / dimensionMax);
        this.dernierBlocLargeur = (double) this.largeur % dimensionMax;
        if (this.dernierBlocLargeur == 0){
            this.dernierBlocLargeur = dimensionMax;
        }
        this.dernierBlocProfondeur = (double) this.profondeur % dimensionMax;
        if (this.dernierBlocLargeur == 0){
            this.dernierBlocLargeur = dimensionMax;
        }
    }
    
    private void gestionSolives() {
        /*this.coordYSolives.clear();
        double dimensionMax = Preferences.global().getLongueurMax26();
        for (int i = 0; i < this.dimensionGridBlocPlancher[1]; i++){
            double coord = 0;
            while(coord < (dimensionMax - 2)){
                coord += this.espacementSolives;
                this.coordYSolives.add((coord + (i * dimensionMax)));
            }
        }*/
        int nombreSolives = 1;
        double positionSolive = 2;
        double profondeur = this.getProfondeur();
        double longueurMaxMateriaux = Preferences.global().getLongueurMax26();
        this.coordYSolives = new ArrayList();
        for(int i =0; i<nombreSolives;i++){
        if((positionSolive + this.espacementSolives)% longueurMaxMateriaux < this.espacementSolives){
            positionSolive += longueurMaxMateriaux - (positionSolive % longueurMaxMateriaux)+2+this.espacementSolives;
            //System.out.println(positionSolive);
            this.coordYSolives.add(positionSolive-1);
            if(positionSolive < profondeur){
                nombreSolives++;
            }
        }
        else if((positionSolive + this.espacementSolives) < (profondeur)){
            nombreSolives++;
            positionSolive += this.espacementSolives;
            this.coordYSolives.add(positionSolive-1);
        }
        //System.out.println(this.coordYSolives.toString());
       }
    }
    
    private void gestionLignesEntremises(double nouvelleLargeur){
        for (LigneEntremises ligne : lignesEntremises){
            double coordX = ligne.getCoordXLigneEntremise();
            double nouvelleCoordX = ((coordX / this.largeur) * nouvelleLargeur);
            ligne.setCoordXLigneEntremise(nouvelleCoordX);
        }
    }

    public UUID ajouterLigneEntremises(double coordY){
        LigneEntremises ligne = new LigneEntremises(coordY);
        lignesEntremises.add(ligne);
        return ligne.getUUID();
    }
    
    public void supprimerLigneEntremises(UUID id){
        for (LigneEntremises ligne : lignesEntremises){
            if (ligne.getUUID()== id){
            this.lignesEntremises.remove(ligne);
            break;
        }
    }
    }
    
    public void detecterConflitEntremises(){
        //On retire tous les conflits
        for(LigneEntremises ligne : this.lignesEntremises){
            ligne.setConflit(false);
            // Cas de la ligne en dehors des limites du plancher
            if(ligne.getCoordXLigneEntremise() < 4 || ligne.getCoordXLigneEntremise() > this.largeur -4){
                ligne.setConflit(true);
            }
            // Cas de la ligne sur une limite de bloc
            if(ligne.getCoordXLigneEntremise() % Preferences.global().getLongueurMax26() < 4 ||
                    Preferences.global().getLongueurMax26() - (ligne.getCoordXLigneEntremise() % Preferences.global().getLongueurMax26()) < 4){
                ligne.setConflit(true);
            }
                    
        }
    }
    
    //Gestion des pièces : 
    public ArrayList<double[]> calculPiecesPlancher(){

        //Initialisation de l'array
        ArrayList<double[]> piecesPlancher = new ArrayList<>();

        //Incrémentation des pièces avec les entremises
        if (!this.lignesEntremises.isEmpty()){
            int nbEntremisesPleinesBlocPlein = (int) (Preferences.getLongueurMax26() / (this.espacementSolives + 2));
            int nbEntremisesPleinesBlocFinal = (int) (this.dernierBlocProfondeur / (this.espacementSolives + 2));
            double longueurDerniereEntremiseBlocPlein = (Preferences.getLongueurMax26() - 4) % (this.espacementSolives + 2);
            double longueurDerniereEntremiseBlocFinal = (this.dernierBlocProfondeur) % (this.espacementSolives + 2);

            double[] entremisesPlancherPleines = {26, this.espacementSolives, 90, 90, ((nbEntremisesPleinesBlocPlein + nbEntremisesPleinesBlocFinal) * this.lignesEntremises.size())};
            piecesPlancher.add(entremisesPlancherPleines);

            if (((this.dimensionGridBlocPlancher[1] - 1) != 0)&&(longueurDerniereEntremiseBlocPlein!=0)){
                double[] entremisesPlancherPartiellesBlocsPleins = {26, longueurDerniereEntremiseBlocPlein, 90, 90, ((this.dimensionGridBlocPlancher[1] - 1) * this.lignesEntremises.size())};
                piecesPlancher.add(entremisesPlancherPartiellesBlocsPleins);
            }

            if (longueurDerniereEntremiseBlocFinal!=0){
                double[] entremisesPlancherPartiellesBlocFinal = {26, longueurDerniereEntremiseBlocFinal, 90, 90, this.lignesEntremises.size()};
                piecesPlancher.add(entremisesPlancherPartiellesBlocFinal);
            }
        }
        
        int nombreLignesSolives = this.coordYSolives.size();
        
        
        //Incrémentation pour les solives est-ouest
        //Incrémentation pour les solives des derniers blocs en x
        double[] solivesContourEOFinal = {26, this.dernierBlocLargeur, 90, 90, (this.dimensionGridBlocPlancher[1] * 2)};
        piecesPlancher.add(solivesContourEOFinal);
        //System.out.println("solivesContourEOFinal");
        //System.out.println(Arrays.toString(solivesContourEOFinal));
        
        double[] solivesInternesEOFinal = {26, (double) (this.dernierBlocLargeur - 4), 90, 90, nombreLignesSolives};
        piecesPlancher.add(solivesInternesEOFinal);
        //System.out.println("solivesInternesEOFinal");
        //System.out.println(Arrays.toString(solivesInternesEOFinal));
        
        //Incrémentations pour les solives des blocs pleins en x
        if ((this.dimensionGridBlocPlancher[0] - 1) != 0){
            double[] solivesContourEOPleins = {26, (Preferences.getLongueurMax26() + 4), 90, 90, (this.dimensionGridBlocPlancher[1] * 2 * (this.dimensionGridBlocPlancher[0] - 1))};
            piecesPlancher.add(solivesContourEOPleins);
            //System.out.println("solivesContourEOPleins");
            //System.out.println(Arrays.toString(solivesContourEOPleins));
            
            double[] solivesInternesEOPleins = {26, Preferences.getLongueurMax26(), 90, 90, (nombreLignesSolives * (this.dimensionGridBlocPlancher[0] - 1))};
            piecesPlancher.add(solivesInternesEOPleins);
            //System.out.println("solivesInternesEOPleins");
            //System.out.println(Arrays.toString(solivesInternesEOPleins));
        }
        
        //Incrémentation pour les solives nord-sud
        
        if (this.dimensionGridBlocPlancher[1] - 1 != 0){
            double[] solivesContourNSPleins = {26, Preferences.getLongueurMax26(), 90, 90, (2 * (this.dimensionGridBlocPlancher[1] - 1))};
            piecesPlancher.add(solivesContourNSPleins);
            //System.out.println("solivesContourNSPleins");
            //System.out.println(Arrays.toString(solivesContourNSPleins));
        }
        
        double[] solivesContourNSPartiels = {26, (this.dernierBlocProfondeur - 4), 90, 90, (2 * this.dimensionGridBlocPlancher[0])};
        piecesPlancher.add(solivesContourNSPartiels);
        //System.out.println("solivesContourNSPartiels");
        //System.out.println(Arrays.toString(solivesContourNSPartiels));
        
        return piecesPlancher;
    }
    
    
    //Setters
        
    public void setEspacementSolives(double nouvelEspacementSolives){
        if(nouvelEspacementSolives >=2){
        this.espacementSolives = nouvelEspacementSolives;
        gestionSolives();
        }
    };
    
    public void setProfondeur(double nouvelleProfondeur){
        this.profondeur = nouvelleProfondeur;
        gestionBlocsPlancher();
        gestionSolives();
    }
    
    public void setLargeur(double nouvelleLargeur){
        gestionLignesEntremises(nouvelleLargeur);
        this.largeur = nouvelleLargeur;
        gestionBlocsPlancher();
        gestionSolives();
    }
    
    
    //Getters

    public double getLargeur(){
        return this.largeur;
    }
    
    public double getProfondeur(){
        return this.profondeur;
    }
    
    public double getDernierBlocLargeur(){
        return this.dernierBlocLargeur;
    }
    
    public double getDernierBlocProfondeur(){
        return this.dernierBlocProfondeur;
    }
    
    public int[] getDimensionGridBlocPlancher(){
        return this.dimensionGridBlocPlancher;
    }

    public ArrayList<Double> getCoordYSolives(){
        return this.coordYSolives;
    };
        
    public double getEspacementSolives(){
        return this.espacementSolives;
    }
    
    public ArrayList<LigneEntremises> getLignesEntremises(){
        return this.lignesEntremises;
    }

    public LigneEntremises getLigneEntremise(UUID id){
        LigneEntremises ligne = null;
        for(LigneEntremises l : this.lignesEntremises){
            if(l.getUUID() == id){
                return l;
            }   
        }
        return ligne;
    }
    
    public Cabanon getParent(){
        return this.parent;
    }
    
    public UUID getUuid(){
        return this.uuid;
    }
}
