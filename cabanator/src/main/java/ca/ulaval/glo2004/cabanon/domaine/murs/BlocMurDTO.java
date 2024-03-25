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
public class BlocMurDTO {
public UUID Id;
public Mur Parent;
public double CoordX;
public double CoordY;
public double Largeur;
public double Hauteur;

public BlocMurDTO(BlocMur blocMur){
    Id = blocMur.getUuidBloc();
    Parent = blocMur.getParent();
    CoordX = blocMur.getCoordXBloc();
    CoordY = blocMur.getCoordYBloc();
    Largeur = blocMur.getlargeurBloc();
    Hauteur = blocMur.getHauteurBloc();
}
}
