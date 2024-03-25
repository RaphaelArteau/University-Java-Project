/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.cabanon.domaine.murs;

import ca.ulaval.glo2004.cabanon.domaine.murs.Accessoire;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
/**
 *
 * @author Maxence
 */
public class BlocMur {
    /*
    public static int MaxWidth = 192;
    public static int MaxHeight = 192;
    */
    
    private UUID id;
    private Mur parent;
    private double coordX;
    private double coordY;
    private double largeur;
    private double hauteur;
    
    //constructeur de la classe BlocMur
    public BlocMur(Mur parent, double coordX, double coordY){
        this.parent = parent;
        this.coordX = coordX;
        this.coordY = coordY;
        this.id = UUID.randomUUID();

    
   }
    public Mur getParent(){
        return this.parent;
    }
    
    public double getCoordXBloc(){
        return this.coordX;
    }
    
    public double getCoordYBloc(){
        return this.coordY;
    }
    
    public UUID getUuidBloc(){
        return this.id;
    }
    
    public double getlargeurBloc(){
        return this.largeur;
    }
    
    public double getHauteurBloc(){
        return this.hauteur;
    }
    
    public void setLargeurBloc(double largeur){
        this.largeur = largeur;
    }
    
    public void setHauteurBloc(double hauteur){
        this.hauteur = hauteur;
    }
}

