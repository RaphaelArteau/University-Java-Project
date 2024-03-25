/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.cabanon.domaine.murs;

import ca.ulaval.glo2004.cabanon.domaine.murs.EntremiseMur;
import ca.ulaval.glo2004.cabanon.domaine.murs.BlocMur;
import ca.ulaval.glo2004.cabanon.domaine.murs.Accessoire;
import ca.ulaval.glo2004.cabanon.domaine.Cabanon;
import ca.ulaval.glo2004.preferences.Preferences;
import java.util.ArrayList;
import java.util.UUID;
/**
 *
 * @author Maxence
 */
public class Mur {
    
    private orientations orientationMur;
    private double espacementMontants;

    //private Map<UUID, Accessoire> accessoiresMap = new HashMap<>();
    
    //private Map<UUID, Map<String, Double>> indexPositionAccessoire = new HashMap<>();
    
    private Cabanon parent;
    
    private ArrayList<ArrayList<Double>> coordMontants;
    
    private ArrayList<EntremiseMur> entremises;
    
    private ArrayList<Accessoire> accessoires;
    
    private ArrayList<ArrayList<BlocMur>> listeDeBlocs;
    
    private UUID uuid;
    
   /* public static int Nord = 1;
    public static int Sud = 2;
    public static int Est = 3;
    public static int Ouest = 4;*/
    
    public static enum orientations{
        Nord, Sud, Est, Ouest
    }
    
    public Mur(Cabanon parent, orientations orientationMur){
        this.orientationMur = orientationMur;
        this.parent = parent;
        this.uuid = UUID.randomUUID();
        this.espacementMontants = 24;
        this.accessoires = new ArrayList<Accessoire>();
        this.entremises = new ArrayList<EntremiseMur>();
        calculerBlocsMur();
        //calculerPositionMontants();
        //updateSize();
       
    }
 
     final public orientations getOrientation(){
       return this.orientationMur;
   } 

   public double getEspacementMontants(){
       return this.espacementMontants;
   }
   
   final public Cabanon getParent(){
       return this.parent;
   }
   
   public ArrayList<ArrayList<Double>> getCoordMontants(){
       return this.coordMontants;
   }
   
   public ArrayList<EntremiseMur> getEntremises(){
       return this.entremises;
   }
   
   public ArrayList<Accessoire> getAccessoire(){
       return this.accessoires;
   }
   
   public UUID getUuid(){
       return this.uuid;
   }
   
   final public double getLargeur(){
        double largeur = 0;
        if(this.orientationMur == orientations.Nord || this.orientationMur == orientations.Sud){
             largeur = this.parent.getLargeur();
        }else if(this.orientationMur == orientations.Est || this.orientationMur == orientations.Ouest){
            largeur = this.parent.getProfondeur() - 8;
        }
        return largeur;
   }
      
   final public double getHauteur(){
       return this.parent.getHauteurMurs();
   }
   
    /*  final public Map<UUID, Accessoire> getAccessoiresMap(){
       return this.accessoiresMap;
   }*/
   
   public ArrayList<ArrayList<BlocMur>> getListeDeBlocs() { 
      //est-ce qu'on enleve la ligne pour la boucle infinie calculerBlocsMur();
        return this.listeDeBlocs;
    }

   public void setEspacementMontants(double espacement){
       if(espacement>2){
           this.espacementMontants = espacement;
           this.recalculerPositionToutElement();
       }  
   } 
   
  /* final public void determinerConflitsFor(UUID id){
       Accessoire accessoire = this.accessoiresMap.get(id);
       double PosX = accessoire.getCoordAccessoireX();
       double PosY = accessoire.getCoordAccessoireY();
       double PosXLaugeur = accessoire.getLargeurContour();
       double PosYHauteur = accessoire.getCoordAccessoireY() + accessoire.getHauteurContour();    
   }*/
   
   public void retirerToutConflit(){
        for(int i=0; i< this.accessoires.size(); i++){
            this.accessoires.get(i).setConflit(false);
        }
   }
   
   
   public void determinerConflitAccessoire(){
       retirerToutConflit();
       for(int i=0; i<this.listeDeBlocs.size();i++){
       ArrayList<Accessoire> listeAccessoireParLigne = genererListeAccessoireParLigne(i);
       for(int y=0; y<listeAccessoireParLigne.size(); y++){
            for(int z=0; z<listeAccessoireParLigne.size();z++){
                if( listeAccessoireParLigne.get(y).getCoordAccessoireX()> listeAccessoireParLigne.get(z).getCoordAccessoireX() &&
                        listeAccessoireParLigne.get(y).getCoordAccessoireX() < listeAccessoireParLigne.get(z).getCoordAccessoireX()+ 
                        listeAccessoireParLigne.get(z).getLargeurContour()){
                            this.accessoires.get(y).setConflit(true);
                        }
                else if(listeAccessoireParLigne.get(y).getCoordAccessoireX()==listeAccessoireParLigne.get(z).getCoordAccessoireX() && 
                        listeAccessoireParLigne.get(y).getIdAccessoire() != listeAccessoireParLigne.get(z).getIdAccessoire()){
                     this.accessoires.get(y).setConflit(true);
                }
                else if(listeAccessoireParLigne.get(y).getCoordAccessoireX()+listeAccessoireParLigne.get(y).getLargeurContour() < 
                        listeAccessoireParLigne.get(z).getCoordAccessoireX()+listeAccessoireParLigne.get(z).getLargeurContour() &&
                        listeAccessoireParLigne.get(y).getCoordAccessoireX()+listeAccessoireParLigne.get(y).getLargeurContour()> 
                        listeAccessoireParLigne.get(z).getCoordAccessoireX()){
                            this.accessoires.get(y).setConflit(true);
                        }           
                    } 
            for(int v=0; v<this.listeDeBlocs.get(i).size();v++){
                if(listeAccessoireParLigne.get(y).getCoordAccessoireX() < this.listeDeBlocs.get(i).get(v).getCoordXBloc() &&
                        listeAccessoireParLigne.get(y).getCoordAccessoireX() + listeAccessoireParLigne.get(y).getLargeurContour() >
                        this.listeDeBlocs.get(i).get(v).getCoordXBloc()){
                            this.accessoires.get(y).setConflit(true);
                        }
                else if(listeAccessoireParLigne.get(y).getCoordAccessoireX() < this.listeDeBlocs.get(i).get(v).getCoordXBloc() + 
                        this.listeDeBlocs.get(i).get(v).getlargeurBloc()&&
                        listeAccessoireParLigne.get(y).getCoordAccessoireX() + listeAccessoireParLigne.get(y).getLargeurContour() >
                        this.listeDeBlocs.get(i).get(v).getCoordXBloc()+ this.listeDeBlocs.get(i).get(v).getlargeurBloc()){
                            this.accessoires.get(y).setConflit(true);
                        }
                else if(listeAccessoireParLigne.get(y).getCoordAccessoireY()< this.listeDeBlocs.get(i).get(0).getCoordYBloc()){
                    this.accessoires.get(y).setConflit(true);
                }
                else if(listeAccessoireParLigne.get(y).getCoordAccessoireY()+ listeAccessoireParLigne.get(y).getHauteurContour()>
                        this.listeDeBlocs.get(i).get(0).getCoordYBloc()+this.listeDeBlocs.get(i).get(0).getHauteurBloc()){
                    this.accessoires.get(y).setConflit(true);
                }
                else if(listeAccessoireParLigne.get(y).getCoordAccessoireY()> this.getHauteur() || 
                        listeAccessoireParLigne.get(y).getCoordAccessoireY()+ listeAccessoireParLigne.get(y).getHauteurContour() > this.getHauteur()){
                    this.accessoires.get(y).setConflit(true);
                }
                else if(listeAccessoireParLigne.get(y).getCoordAccessoireY() < 0){
                    this.accessoires.get(y).setConflit(true);
                }
                else if(listeAccessoireParLigne.get(y).getCoordAccessoireX() < 0 || 
                        listeAccessoireParLigne.get(y).getCoordAccessoireX() + listeAccessoireParLigne.get(y).getLargeurContour() > this.getLargeur()){
                    this.accessoires.get(y).setConflit(true);
                }

                }
            }
       }
   }
   public void determinerConflitEntremiseMur(){
       this.supprimerToutConflitEntremise();
       for(int i=0;i<this.entremises.size(); i++){
           for(Accessoire accessoire : this.accessoires){
               if(this.entremises.get(i).getcoordEntremiseX() + this.entremises.get(i).getLongueurEntremise()/2 > accessoire.getCoordAccessoireX() &&
                       this.entremises.get(i).getcoordEntremiseX() + this.entremises.get(i).getLongueurEntremise()/2 < accessoire.getCoordAccessoireX()+ accessoire.getLargeurContour()){
                   this.entremises.get(i).setConflit(true);
               }
           }
           for(int y=0; y<this.entremises.size(); y++){
               if(i != y && this.entremises.get(i).getcoordEntremiseX() == this.entremises.get(y).getcoordEntremiseX()){
                   if(this.entremises.get(i).getcoordEntremiseY() > this.entremises.get(y).getcoordEntremiseY()-2 &&
                           this.entremises.get(i).getcoordEntremiseY() < this.entremises.get(y).getcoordEntremiseY()+2){
                       this.entremises.get(i).setConflit(true);
                   }
               }
           }
       }
   }
   public void supprimerToutConflitEntremise(){
       for(int i=0; i < this.entremises.size(); i++){
           this.entremises.get(i).setConflit(false);
       }
   }

   
   /*final public void indexerPositionAccessoire(){
       this.indexPositionAccessoire = new HashMap<>();
       for (Map.Entry<UUID, Accessoire> entree : this.accessoiresMap.entrySet()) {
           Map<String, Double> valeurs = new HashMap<>();
           Accessoire accessoire = entree.getValue();
           valeurs.put("PosX", accessoire.getCoordAccessoireX());
           valeurs.put("PosY", accessoire.getCoordAccessoireY());
           valeurs.put("PosX+Laugeur", accessoire.getCoordAccessoireX() + accessoire.getLargeurContour());
           valeurs.put("PosY+Hauteur", accessoire.getCoordAccessoireY() + accessoire.getHauteurContour());
           this.indexPositionAccessoire.put(entree.getKey(), valeurs);
       }
   }*/
   
   public void calculerBlocsMur(){
       this.listeDeBlocs = null;
       this.listeDeBlocs = new ArrayList<ArrayList<BlocMur>>();
       int compteurLignesBlocs = 1;
       int compteurColonnesBlocs = 1;
       double hauteurMur = this.getHauteur();
       double largeurMur = this.getLargeur();
       double coordYBloc = 0;
       double coordXBloc = 0;
       for(int i=0; i<compteurLignesBlocs; i++){
         ArrayList<BlocMur> lignesBlocs = new ArrayList<BlocMur>();
         this.listeDeBlocs.add(lignesBlocs);
         for(int y=0;y<compteurColonnesBlocs;y++){
             this.listeDeBlocs.get(i).add(new BlocMur(this, coordXBloc, Math.max(coordYBloc, 0)));
             if(largeurMur-(Preferences.global().getLongueurMax24()*(y+1))>0){
                compteurColonnesBlocs += 1;
                coordXBloc += Preferences.global().getLongueurMax24();
             }
         }
         hauteurMur -= Preferences.global().getLongueurMax24()+4;
         if(hauteurMur>0){
             compteurLignesBlocs +=1;
             compteurColonnesBlocs = 1;
             coordYBloc += (Preferences.global().getLongueurMax24()+4);
             coordXBloc = 0;
         }
       }
       calculerLargeurHauteurBlocs();
       calculerPositionMontants();
   }

   
     /* public void calculerBlocsMurAfficheur(){
       this.listeDeBlocs = null;
       this.listeDeBlocs = new ArrayList<ArrayList<BlocMur>>();
       int compteurLignesBlocs = 1;
       int compteurColonnesBlocs = 1;
       double hauteurMur = this.getHauteur();
       double largeurMur = this.getLargeur();
       double coordYBloc = this.getHauteur() - (Preferences.global().getLongueurMax24()+4);
       double coordXBloc = 0;
       for(int i=0; i<compteurLignesBlocs; i++){
         ArrayList<BlocMur> lignesBlocs = new ArrayList<BlocMur>();
         this.listeDeBlocs.add(lignesBlocs);
         for(int y=0;y<compteurColonnesBlocs;y++){
             this.listeDeBlocs.get(i).add(new BlocMur(this, coordXBloc, Math.max(coordYBloc, 0)));
             if(largeurMur-(Preferences.global().getLongueurMax24()*(y+1))>0){
                compteurColonnesBlocs += 1;
                coordXBloc += Preferences.global().getLongueurMax24();
             }
         }
         hauteurMur -= Preferences.global().getLongueurMax24()+4;
         if(hauteurMur>0){
             compteurLignesBlocs +=1;
             compteurColonnesBlocs = 1;
             coordYBloc -= (Preferences.global().getLongueurMax24()+4);
             coordXBloc = 0;
         }
       }
       calculerLargeurHauteurBlocs();
       calculerPositionMontants();
   }
   */
   final public Accessoire ajouterAccessoire(int typeAccessoire){
       Accessoire nouvelAccessoire = new Accessoire(this, typeAccessoire);
        this.accessoires.add(nouvelAccessoire);
        this.determinerConflitAccessoire();
        this.calculerPositionMontants();
        this.recalculerPositionToutElement();
        return nouvelAccessoire;
   }
   
   public void supprimerAccessoire(UUID idAccessoire){
    for(int i =0; i < this.accessoires.size(); i++){
        if(this.accessoires.get(i).getIdAccessoire() == idAccessoire){
            for(int y =0; y<this.entremises.size();y++){
                if(this.entremises.get(y).getId() == this.accessoires.get(i).getEntremiseDroite().getId()){
                    this.entremises.remove(y);
                    //this.recalculerPositionToutElement();
                    break;
                }
            }
            for(int y =0; y<this.entremises.size();y++){
                if(this.entremises.get(y).getId() ==  this.accessoires.get(i).getEntremiseGauche().getId()){
                    this.entremises.remove(y);
                    //this.recalculerPositionToutElement();
                    break;
                }
            }
            this.accessoires.remove(i);
            this.recalculerPositionToutElement();
            break;
        }
    }   
    this.calculerPositionMontants();
    //System.out.println(this.entremises.size());
   }
   
   public EntremiseMur ajouterEntremiseMur(){
   EntremiseMur nouvelleEntremise = new EntremiseMur(this);
   this.entremises.add(nouvelleEntremise);
   //this.determinerConflitEntremiseMur();
   return nouvelleEntremise;
   }
   
   public void supprimerEntremiseMur(UUID idEntremise){
       for(int i=0; i< this.entremises.size();i++){
           if(this.entremises.get(i).getId() == idEntremise){
               this.entremises.remove(i);
               break;
           }
       }
       //this.determinerConflitEntremiseMur();
   }
   
   final public void calculerPositionMontants(){
       this.coordMontants = null;
       this.coordMontants = new ArrayList<ArrayList<Double>>();
       int nombreMontants = 1;
       double positionMontant = 0;
       double largeur = this.getLargeur();
       double longueurMaxMateriaux = Preferences.global().getLongueurMax24();
       this.determinerConflitAccessoire();
       //On calcule la position de chaque montant par ligne de blocs
       for(int i = 0; i < this.listeDeBlocs.size(); i++){
           ArrayList<Double> lignesMontants = new ArrayList<>();
           this.coordMontants.add(lignesMontants);
           positionMontant = 0;
           nombreMontants = 1;
           ArrayList<Accessoire> listeAccessoiresParLigne = genererListeAccessoireParLigne(i);
           //On détermine la coordonnée de chaque montant de la ligne
           for(int y = 0; y < nombreMontants; y++){
                this.coordMontants.get(i).add(positionMontant+1);
                //On itère pour chaque accessoire de la ligne
                for(int z = 0; z < listeAccessoiresParLigne.size(); z++){
                //cas où le prochain montant serait dans un accessoire : On place le montant à la coordonnée de l'accessoire
                if(listeAccessoiresParLigne.get(z).getCoordAccessoireX() < positionMontant + this.espacementMontants +2 && 
                        listeAccessoiresParLigne.get(z).getCoordAccessoireX() + listeAccessoiresParLigne.get(z).getLargeurContour() > positionMontant + this.espacementMontants+2){
                    /*listeAccessoiresParLigne.get(z).setLargeurEntDroite(listeAccessoiresParLigne.get(z).getCoordAccessoireX() - positionMontant - 1);
                    if(listeAccessoiresParLigne.get(z).getLargeurEntDroite()<1 && positionMontant > 0){
                        listeAccessoiresParLigne.get(z).setLargeurEntDroite(this.espacementMontants);    
                    }
                    if(listeAccessoiresParLigne.get(z).getLargeurEntDroite()<2 && positionMontant > 0){
                        listeAccessoiresParLigne.get(z).setLargeurEntDroite(this.espacementMontants+1);    
                    }
                    if(listeAccessoiresParLigne.get(z).getLargeurEntDroite()==3){
                        listeAccessoiresParLigne.get(z).setLargeurEntDroite(0);    
                    }*/

                    //System.out.println(listeAccessoiresParLigne.get(z).getLargeurEntDroite());
                    positionMontant = listeAccessoiresParLigne.get(z).getCoordAccessoireX();
                    this.coordMontants.get(i).add(positionMontant - 1);
                    //On place le prochain montant à la fin de l'accessoire
                    positionMontant += listeAccessoiresParLigne.get(z).getLargeurContour();
                    /*if(positionMontant + this.espacementMontants > this.getLargeur() - 3){
                        listeAccessoiresParLigne.get(z).setLargeurEntGauche(this.getLargeur() - positionMontant - 3);
                    }
                    else if((positionMontant + this.espacementMontants)% longueurMaxMateriaux < this.espacementMontants+1){
                        listeAccessoiresParLigne.get(z).setLargeurEntGauche(longueurMaxMateriaux - (positionMontant % longueurMaxMateriaux)-4);
                    }
                    else{
                         listeAccessoiresParLigne.get(z).setLargeurEntGauche(this.getEspacementMontants() - 2);
                    }*/
                    this.coordMontants.get(i).add(positionMontant + 1);
                    nombreMontants+=2;
                    y++;
                    }
                }
                // cas où le prochain montant se trouve dans le prochain bloc : les deux prochain montants forment la jonction de bloc
                if((positionMontant + this.espacementMontants)% longueurMaxMateriaux < this.espacementMontants && positionMontant +this.espacementMontants < largeur){
                    positionMontant += longueurMaxMateriaux - (positionMontant % longueurMaxMateriaux)-1;
                    this.coordMontants.get(i).add(positionMontant);
                    positionMontant +=1;
                    if(positionMontant < largeur){
                        nombreMontants += 2;
                        y++;
                        }
                
                }
                // cas où le prochain montant peut être placé avant la fin du mur
                else if((positionMontant + this.espacementMontants) < (largeur-3)){
                    nombreMontants++;
                    positionMontant += this.espacementMontants;
                }
                //Sinon le prochain montant marque la fin du mur
                else{
                    this.coordMontants.get(i).add(largeur-1);
                }
            }
        }
              for(EntremiseMur entremise : this.entremises){
           double coordX = entremise.getcoordEntremiseX();
           entremise.setCoordEntremiseX(coordX);}
       //System.out.println(this.coordMontants);
       //System.out.println(this.entremises.size());
       /*if(this.orientationMur == orientations.Nord){
       for(double coord : this.coordMontants.get(0)){
           System.out.println(coord);
       }}*/
       /*if(this.orientationMur == orientations.Nord){
           for(BlocMur bloc : this.listeDeBlocs.get(0)){
               System.out.println(bloc.getlargeurBloc());
           }
       }*/
       /*if(this.orientationMur == orientations.Nord){
           for(Accessoire accessoire : this.accessoires){
               System.out.println(accessoire.getConflit());
           }}*/
    }

   public ArrayList<Accessoire> genererListeAccessoireParLigne(int ligne){
       ArrayList<Accessoire> listeAccessoires = new ArrayList<>();
       for(int i=0; i < this.accessoires.size(); i++){

            if(this.accessoires.get(i).getCoordAccessoireY()+ this.accessoires.get(i).getHauteurContour()/2 >  this.listeDeBlocs.get(ligne).get(0).getCoordYBloc() && 
               this.accessoires.get(i).getCoordAccessoireY()+ this.accessoires.get(i).getHauteurContour()/2 < this.listeDeBlocs.get(ligne).get(0).getCoordYBloc() + this.listeDeBlocs.get(ligne).get(0).getHauteurBloc() 
                   /* && this.accessoires.get(i).getConflit() == false*/){
               listeAccessoires.add(this.accessoires.get(i));

            }
        }
       return listeAccessoires;
   }
   
   public void calculerLargeurHauteurBlocs(){
       double largeurRestante = this.getLargeur();
       double hauteurRestante = this.getHauteur();
       for(int i=0; i<this.listeDeBlocs.size(); i++){
           for(int y=0; y<this.listeDeBlocs.get(i).size(); y++){
               if(i+1 == this.listeDeBlocs.size()){
                   this.listeDeBlocs.get(i).get(y).setHauteurBloc(hauteurRestante);
               }else{
                   this.listeDeBlocs.get(i).get(y).setHauteurBloc(Preferences.global().getLongueurMax24());
                   
                   if(hauteurRestante - (Preferences.global().getLongueurMax24()+4) > 0){
                        hauteurRestante -= (Preferences.global().getLongueurMax24()+4);
                   }
                   
               }
               
               if(y+1 == this.listeDeBlocs.get(i).size()){
                   this.listeDeBlocs.get(i).get(y).setLargeurBloc(largeurRestante);
               }else{
                   this.listeDeBlocs.get(i).get(y).setLargeurBloc(Preferences.global().getLongueurMax24());
                   
                   if(largeurRestante - (Preferences.global().getLongueurMax24()) > 0){
                        largeurRestante -= (Preferences.global().getLongueurMax24());
                   }
               } 
           }
       }
       /*for(int p=0 ; p< this.listeDeBlocs.size();p++){
           for(int q=0; q< this.listeDeBlocs.get(p).size(); q++){
               //System.out.println(this.listeDeBlocs.get(p).get(q).getHauteurBloc());
               //System.out.println(this.listeDeBlocs.get(p).get(q).getlargeurBloc());
               System.out.println(this.listeDeBlocs.get(p).get(q).getCoordYBloc());
           }
       }*/
   }
      
    public void recalculerPositionToutElement(){
        this.calculerBlocsMur();
        this.repositionnerAccessoires();
        this.calculerPositionMontants();
    }
    
    public void repositionnerAccessoires(){
        for( Accessoire accessoires : this.accessoires){
            //System.out.println(accessoires.getCoordYPourcent());
            double coordX = ((accessoires.getCoordXPourcent() * this.getLargeur()) /100) - accessoires.getLargeurContour()/2;
            double coordY = ((accessoires.getCoordYPourcent() * this.getHauteur()) /100) - accessoires.getHauteurContour()/2;
            
            accessoires.setcoordAccessoireX(coordX);
            accessoires.setcoordAccessoireY(coordY);
        }
    }
    
    public ArrayList<double[]> calculListePiecesMur(){
        ArrayList<double[]> piecesMur = new ArrayList<>();
        //Calcul des pièces horizontales de bloc
        for(int x = 0; x< this.listeDeBlocs.get(0).size();x++){
            ArrayList<Accessoire> listePorteBloc = new ArrayList();
            double[] listeHorizPremierBloc = {24, this.listeDeBlocs.get(0).get(x).getlargeurBloc(), 90, 90, 1};
            piecesMur.add(listeHorizPremierBloc);
            if(!this.accessoires.isEmpty()){
            for(Accessoire accessoire : this.accessoires){
                //Si c'est une porte et qu'elle se trouve dans le bloc lu, on l'ajoute à la liste
                if(accessoire.getTypeAccessoire() == 2 && accessoire.getCoordAccessoireX() > this.listeDeBlocs.get(0).get(x).getCoordXBloc() &&
                        accessoire.getCoordAccessoireX()+accessoire.getLargeurContour() < this.listeDeBlocs.get(0).get(x).getCoordXBloc() + this.listeDeBlocs.get(0).get(x).getlargeurBloc()){
                    listePorteBloc.add(accessoire);
                }
                if(listePorteBloc.isEmpty()){
                    double[] listeHorizPremierBlocBas = {24, this.listeDeBlocs.get(0).get(x).getlargeurBloc(), 90, 90, 1};
                    piecesMur.add(listeHorizPremierBlocBas);
                }
                else{
                    
                Accessoire porteSuivante = listePorteBloc.get(0);
                for(Accessoire autrePorte : listePorteBloc){
                    if(porteSuivante.getCoordAccessoireX() > autrePorte.getCoordAccessoireX()){
                        porteSuivante = autrePorte;
                    }
                }
                double posXPiece = this.listeDeBlocs.get(0).get(x).getCoordXBloc();    
                double[] listeBasBloc = {24, porteSuivante.getCoordAccessoireX()-posXPiece, 90,90,1};
                piecesMur.add(listeBasBloc);
                posXPiece = porteSuivante.getCoordAccessoireX() + porteSuivante.getLargeurContour();
                listePorteBloc.remove(porteSuivante);
                for(Accessoire porte : listePorteBloc){
                    if(listePorteBloc.size()>1){
                
                    Accessoire porteDApres = listePorteBloc.get(0);
                    for(Accessoire autrePorte : listePorteBloc){
                        if(porteDApres.getCoordAccessoireX() > autrePorte.getCoordAccessoireX()){
                        porteDApres = autrePorte;
                        }
                    }
                    double[] listeBasBlocSuivant = {24, porteDApres.getCoordAccessoireX()- posXPiece, 90, 90, 1};
                    piecesMur.add(listeBasBlocSuivant);
                    posXPiece = porteDApres.getCoordAccessoireX() + porteDApres.getLargeurContour();
                    listePorteBloc.remove(porteDApres);
                }
                }
                double longueurDernPiece = this.listeDeBlocs.get(0).get(x).getCoordXBloc()+ this.listeDeBlocs.get(0).get(x).getlargeurBloc() -
                        porteSuivante.getCoordAccessoireX()-porteSuivante.getLargeurContour();
                double[] listedernPieceBlocBas = {24, longueurDernPiece, 90, 90, 1};
                piecesMur.add(listedernPieceBlocBas);
                }
            }  
        }
        else{piecesMur.add(listeHorizPremierBloc);}
            
        }
        for(int i = 1; i< this.listeDeBlocs.size(); i++){
            for(int y =0; y < this.listeDeBlocs.get(i).size(); y++){
                double[] listeHorizBlocs = {24, this.listeDeBlocs.get(i).get(y).getlargeurBloc(), 90, 90, 2};
                piecesMur.add(listeHorizBlocs);
            }
        }
        //Calcul des montants par ligne de blocs
        for(int i =0; i< this.listeDeBlocs.size();i++){
            double[] listeMontantsLigne = {24, (this.listeDeBlocs.get(i).get(0).getHauteurBloc() - 4), 90, 90, this.coordMontants.get(i).size()};
            piecesMur.add(listeMontantsLigne);  
            for(Accessoire accessoire : this.genererListeAccessoireParLigne(i)){
                double epaisseurLinteau = 6;
                int tailleLinteau = 26;
                if(accessoire.getTailleLinteau() == Accessoire.tailleLinteaux.deuxXdix){
                    epaisseurLinteau = 10;
                    tailleLinteau = 210;
                }
                if(accessoire.getTailleLinteau() == Accessoire.tailleLinteaux.deuxXhuit){
                    epaisseurLinteau = 8;
                    tailleLinteau = 28;
                }

                double hauteurMontant = this.listeDeBlocs.get(i).get(0).getCoordYBloc() + this.listeDeBlocs.get(i).get(0).getHauteurBloc() -
                        accessoire.getCoordAccessoireX() - accessoire.getHauteurContour() - epaisseurLinteau -4;
                double[] listeMontantsAcc = { 24, hauteurMontant, 90, 90, accessoire.getCoordMontantsX().size()};
                double[] listeLinteau = {tailleLinteau, accessoire.getLargeurContour(), 90, 90, 1}; 
                piecesMur.add(listeMontantsAcc);
                piecesMur.add(listeLinteau);
                //cas d'une fenêtre
                if(accessoire.getTypeAccessoire() == 1){
                    hauteurMontant = this.listeDeBlocs.get(i).get(0).getHauteurBloc() - 
                            hauteurMontant -epaisseurLinteau -accessoire.getHauteurContour() -4;
                    double[] listeMontantsBasAcc = { 24, hauteurMontant, 90, 90, accessoire.getCoordMontantsX().size()};
                    double[] listeBatantsFenetre = {24, accessoire.getHauteurContour()-2, 90, 90, 2};
                    double[] listeRebordFenetre = { 24, accessoire.getLargeurContour(), 90, 90, 1};
                    piecesMur.add(listeMontantsBasAcc);
                    piecesMur.add(listeBatantsFenetre);
                    piecesMur.add(listeRebordFenetre);
                }
                if(accessoire.getTypeAccessoire() == 2){
                    double[] listeBatantsPorte = {24, accessoire.getHauteurContour(), 90, 90, 2};
                    piecesMur.add(listeBatantsPorte);
                }
            }
            for(EntremiseMur entremise : this.entremises){
                double[] listeEntremiseMur = {24, entremise.getLongueurEntremise(), 90, 90, 1};
                piecesMur.add(listeEntremiseMur);
            }
            }
        //System.out.println(piecesMur.size());
        return piecesMur;
    }
}
