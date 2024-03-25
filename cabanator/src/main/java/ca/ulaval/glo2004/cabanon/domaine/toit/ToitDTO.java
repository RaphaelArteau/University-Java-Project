/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.cabanon.domaine.toit;

import java.util.ArrayList;
import ca.ulaval.glo2004.cabanon.domaine.Cabanon;

/**
 *
 * @author Utilisateur
 */
public class ToitDTO {
    public int OrientationToit;
    public double AngleToit;
    public double LongueurDebordement;
    public double PorteAFaux;
    public double PorteAFauxHauteur;
    public double PorteAFauxHypothenus;
    public double EntraitFerme;
    public double chevrons;
    public double poinconsPrincipal;
    public double poinconsSupp;
    public double EspacementFermes;
    public double EspacementEntremisesDebor;
    public int nombreEntremises;
    public double nombreFermes;
    public ArrayList<Fermes> Fermes;
    public ArrayList<Double> CoordPoinconsX;
     public ArrayList<Double> CoordPoinconsY;
    public ArrayList<Poincons> PoinconsFermes;
    public ArrayList<Double> EntremisesDebor;
    public ArrayList<Double> CoordFermes;
    public double HauteurToit;
    public Cabanon parent;
    public ArrayList<Double> CoordFermesOrdo;
    public ArrayList<Double> CoordEntremisesOrdo;
    
    public ToitDTO (Toit toit){
        OrientationToit = toit.getOrientationToit();
        AngleToit = toit.getAngleToit();
        LongueurDebordement = toit.getLongueurDebord();
        PorteAFaux = toit.getPorteAfaux();
        PorteAFauxHauteur = toit.getPorteAFauxHauteur();
        PorteAFauxHypothenus = toit.getPorteAFauxHypo();
        EspacementFermes = toit.getEspacementFermes();
        EspacementEntremisesDebor = toit.getEspacementEntremises();
        Fermes = toit.getFermes();
        EntremisesDebor = toit.getCoordEntremisesDebor();
        CoordFermes = new ArrayList<>(toit.getCoordFermes());
        HauteurToit = toit.getHauteurToit();
        EntraitFerme = toit.getEntraitFerme();
        CoordPoinconsX = toit.getCoordPoinconsX();
        CoordPoinconsY = toit.getCoordPoinconsY();
        PoinconsFermes = toit.getPoincons();
        parent = toit.getParent();
        chevrons = toit.getChevrons();
       // System.out.println(chevrons);
        poinconsPrincipal = toit.getPoinconsPrincipal();
        poinconsSupp = toit.getPoinconsSupp();
        nombreEntremises = toit.getNombreEntremises();
        nombreFermes = toit.getNombreFermes();
        CoordFermesOrdo = toit.getCoordFermesOrdo();
        CoordEntremisesOrdo = toit.getCoordEntremisesOrdo();
    }
}
