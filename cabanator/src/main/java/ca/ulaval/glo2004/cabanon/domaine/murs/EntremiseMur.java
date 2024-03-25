/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.cabanon.domaine.murs;

import java.util.UUID;

/**
 *
 * @author Maxence
 */
public class EntremiseMur {
    
    private UUID id;
    private transient Mur parent;
    private double coordEntremiseX;
    private double absoluteCoordEntremiseX;
    private double coordEntremiseY;
    private boolean conflit;
    private double longueur;
    private Accessoire accessoireParent;
    private boolean selected;

    public EntremiseMur(Mur murParent){
        this.parent = murParent;
        this.id = UUID.randomUUID();
        this.coordEntremiseY= this.parent.getHauteur()/2;
        //System.out.println(coordEntremiseY);
        this.coordEntremiseX= 2;
        this.longueur = this.parent.getEspacementMontants();
        this.accessoireParent = null;
        //this.setCoordEntremiseY(coordEntremiseY);
        //this.setCoordEntremiseX(coordEntremiseX);
        this.parent.determinerConflitEntremiseMur();
    }
    
    public UUID getId(){
        return this.id;
    }
    public Mur getParent(){
        return this.parent;
    }
    public double getcoordEntremiseX(){
        return this.coordEntremiseX;
    }
    public double getcoordEntremiseY(){
        return this.coordEntremiseY;
    }
    public boolean getConflit(){
        return this.conflit;
    }
    public double getLongueurEntremise(){
        return this.longueur;
    }
    public Accessoire getAccessoireParent(){
        return this.accessoireParent;
    }
    public void setIdEntremiseMur(UUID id){
        this.id = id;
    }
            
    public void setCoordEntremiseX(double coordX){
        this.absoluteCoordEntremiseX = coordX;
        
        // On vérifie sur quelle ligne de blocs l'entremise se trouve
        for(int i=0; i<this.parent.getListeDeBlocs().size();i++){
           if(this.coordEntremiseY + 1 > this.parent.getListeDeBlocs().get(i).get(0).getCoordYBloc() &&
              this.coordEntremiseY + 1 < this.parent.getListeDeBlocs().get(i).get(0).getCoordYBloc()+this.parent.getListeDeBlocs().get(i).get(0).getHauteurBloc()){
               //Cas de l'entremise trop à gauche : On place l'entremise entre les deux premiers montants de la ligne
               if(coordX < 2){
                this.coordEntremiseX = 2;
                this.longueur = this.parent.getCoordMontants().get(i).get(1) - this.parent.getCoordMontants().get(i).get(0);
                }
               //Cas de l'entremise trop à droite : On place l'entremise entre les deux derniers montants de la ligne
               else if(coordX > this.parent.getLargeur()-2){
                this.coordEntremiseX = this.parent.getCoordMontants().get(i).get(this.parent.getCoordMontants().get(i).size() -2)+1;
                this.longueur = this.parent.getCoordMontants().get(i).get(this.parent.getCoordMontants().get(i).size() -1) -
                    this.parent.getCoordMontants().get(i).get(this.parent.getCoordMontants().get(i).size() -2);
                }
               //Sinon l'entremise est placées entre les deux montants contenant sa coordonnée X
                else{
                    for(int y =0 ; y<this.parent.getCoordMontants().get(i).size(); y++){
                        //System.out.println(this.parent.getCoordMontants().get(i).get(y));
                        if(coordX > this.parent.getCoordMontants().get(i).get(y) && 
                                coordX < this.parent.getCoordMontants().get(i).get(y+1)){
                            this.coordEntremiseX = this.parent.getCoordMontants().get(i).get(y)+1;
                            this.longueur = this.parent.getCoordMontants().get(i).get(y+1) - this.parent.getCoordMontants().get(i).get(y);
                            /*ArrayList<Accessoire> listeAccessoire = this.parent.genererListeAccessoireParLigne(i);
                            for(int z=0; z<listeAccessoire.size();z++){
                                if(coordX > listeAccessoire.get(z).getCoordAccessoireX()-2 && coordX < listeAccessoire.get(z).getCoordAccessoireX() +
                                        listeAccessoire.get(z).getLargeurContour()-2){
                                    this.coordEntremiseX = 2;
                                    this.longueur = this.parent.getEspacementMontants();
                                }
                                else{
                                    this.coordEntremiseX = this.parent.getCoordMontants().get(i).get(y)+1;
                                    this.longueur = this.parent.getCoordMontants().get(i).get(y+1)- this.parent.getCoordMontants().get(i).get(y);
                                }*/
                            }
                        /*else{
                            this.longueur = this.parent.getCoordMontants().get(i).get(this.parent.getCoordMontants().get(i).size()-1) -
                                    this.parent.getCoordMontants().get(i).get(this.parent.getCoordMontants().get(i).size()-2) -2;
                        }*/
                        }
                    }
                }
             }
        this.parent.determinerConflitEntremiseMur();
        }
    
    public void setCoordEntremiseY(double coordY){
        if(coordY < 2){
            this.coordEntremiseY =2;
        }
        else if(coordY > this.parent.getHauteur()-2){
            this.coordEntremiseY = this.parent.getHauteur()-2;
        }
        else{
            /*for(int i=0; i <this.parent.getListeDeBlocs().size();i++){
                if(this.parent.getListeDeBlocs().get(i).get(0).getCoordYBloc()+this.parent.getListeDeBlocs().get(i).get(0).getHauteurBloc() > coordY &&
                       this.parent.getListeDeBlocs().get(i).get(0).getCoordYBloc()+this.parent.getListeDeBlocs().get(i).get(0).getHauteurBloc()-3 < coordY ){
                    this.coordEntremiseY = this.parent.getListeDeBlocs().get(i).get(0).getCoordYBloc()+this.parent.getListeDeBlocs().get(i).get(0).getHauteurBloc()-3;
                }
                else if(this.parent.getListeDeBlocs().get(i).get(0).getCoordYBloc()+3 > coordY){
                    this.coordEntremiseY = this.parent.getListeDeBlocs().get(i).get(0).getCoordYBloc()+3;
                }
                else{*/
                    this.coordEntremiseY = coordY;
                }
        this.setCoordEntremiseX(this.getAbsoluteCoordEntremiseX());
        this.parent.determinerConflitEntremiseMur();
    }
    
    public void setConflit(boolean enConflit){
        this.conflit = enConflit;
    }

    public void setAccessoireParent(Accessoire accessoireParent){
        this.accessoireParent = accessoireParent;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public double getAbsoluteCoordEntremiseX() {
        return absoluteCoordEntremiseX;
    }
}
