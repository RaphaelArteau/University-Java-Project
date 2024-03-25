/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.visualizer;

import ca.ulaval.glo2004.cabanon.domaine.murs.Accessoire;
import ca.ulaval.glo2004.cabanon.domaine.murs.AccessoireDTO;
import ca.ulaval.glo2004.cabanon.domaine.murs.BlocMurDTO;
import ca.ulaval.glo2004.cabanon.domaine.murs.EntremiseMurDTO;
import ca.ulaval.glo2004.cabanon.domaine.murs.Mur;
import ca.ulaval.glo2004.cabanon.domaine.murs.MurDTO;
import ca.ulaval.glo2004.preferences.Preferences;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author raphael
 */
public class AfficheurMur extends Afficheur {
    
    private Mur.orientations orientation = null;
    
    public AfficheurMur(Visualizer parent, Mur.orientations orientation){
        super(parent);
        this.orientation = orientation;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        this.hitzones.removeAll(this.hitzones);
        super.paintComponent(g);
        int _2inches_px = InchesToPixels(2);
        int height = this.getHeight();
        int width = this.getWidth();
        /*if(this.orientation == Mur.orientations.Est || this.orientation == Mur.orientations.Ouest){
            width += 4*_2inches_px;
        }*/
        
        
        int counter = 0;
        MurDTO mur = controlleur.getMurByOrientation(this.orientation);
        //g.drawRect(x(1),x(1), InchesToPixels(mur.Largeur), InchesToPixels(mur.Hauteur));
        ArrayList<ArrayList<BlocMurDTO>> blocs = controlleur.getLignesBlocMurs(this.orientation);
        ArrayList<ArrayList<Double>> montantsParLigne = mur.CoordMontants;
       //code ajouté par Max 
        ArrayList<Double> listeHauteurBlocsAfficheur = new ArrayList();
       double hauteur = 0;
       for(int i = 0 - blocs.size(); i < 0;i++){
           //System.out.println(hauteur);
               listeHauteurBlocsAfficheur.add(hauteur);
               if(i+1 < 0){
                   hauteur = mur.Hauteur - blocs.get(0 -(i+1)).get(0).CoordY;
           }
       }
       double hauteurRestante = mur.Hauteur - ((Preferences.global().getLongueurMax24() + 4) * (mur.ListeDeBlocs.size() - 1));
       if(hauteurRestante < 0){
           hauteurRestante = mur.Hauteur;
       }

       //fin du code ajouté par Max
        for(int i = 0; i < blocs.size(); i++){
            ArrayList<BlocMurDTO> ligne = blocs.get(i);
            ArrayList<Double> montants = montantsParLigne.get(blocs.size()-1-i);
            ArrayList<AccessoireDTO> listeAccessoiresLigne = this.controlleur.genererListeAccessoireParLigne(this.orientation, blocs.size()-1 -i);
            int largeurMur = InchesToPixels(this.controlleur.getLargeurCabanon());
            if(this.orientation == Mur.orientations.Est || this.orientation == Mur.orientations.Ouest){
                largeurMur = InchesToPixels(this.controlleur.getProfondeurCabanon() + 8);
            }
            // On ajoute 1 comme padding
            int originY_px = InchesToPixels(listeHauteurBlocsAfficheur.get(i));

            //On ajoute les flancs des murs Nord et Sud si on est en vue Est ou Ouest
            int hauteurBloc_px = InchesToPixels(hauteurRestante);    
            if(this.orientation == Mur.orientations.Est || this.orientation == Mur.orientations.Ouest){
                g.setColor(Color.WHITE);
                g.fillRect(0, originY_px, 2*_2inches_px, _2inches_px); 
                g.fillRect(0, originY_px+ hauteurBloc_px- _2inches_px, 2*_2inches_px, _2inches_px); 
                g.fillRect(InchesToPixels(this.controlleur.getProfondeurCabanon())-2*_2inches_px, originY_px, 2*_2inches_px, _2inches_px); 
                g.fillRect(InchesToPixels(this.controlleur.getProfondeurCabanon())-2*_2inches_px, originY_px+ hauteurBloc_px- _2inches_px, 2*_2inches_px, _2inches_px);
                g.fillRect(0, originY_px, 2*_2inches_px, hauteurBloc_px - _2inches_px);
                g.fillRect(InchesToPixels(this.controlleur.getProfondeurCabanon())- 2*_2inches_px, originY_px, 2*_2inches_px, hauteurBloc_px - _2inches_px);
                g.setColor(Color.BLACK);
                g.drawRect(0, originY_px, 2*_2inches_px, _2inches_px); 
                g.drawRect(0, originY_px+ hauteurBloc_px- _2inches_px, 2*_2inches_px, _2inches_px); 
                g.drawRect(InchesToPixels(this.controlleur.getProfondeurCabanon()) - 2*_2inches_px, originY_px, 2*_2inches_px, _2inches_px); 
                g.drawRect(InchesToPixels(this.controlleur.getProfondeurCabanon()) - 2*_2inches_px, originY_px+ hauteurBloc_px- _2inches_px, 2*_2inches_px, _2inches_px);
                g.drawRect(0, originY_px + _2inches_px, 2*_2inches_px, hauteurBloc_px - (_2inches_px*2));
                g.drawRect(InchesToPixels(this.controlleur.getProfondeurCabanon())-2*_2inches_px, originY_px +_2inches_px, 2*_2inches_px, hauteurBloc_px - (_2inches_px*2));

            }
            // On vérifie si une porte est placée
            if(i == 0 && !listeAccessoiresLigne.isEmpty()){
                    for(AccessoireDTO porte : listeAccessoiresLigne){
                        if(porte.TypeAccessoire == 2){
                          g.setColor(Color.WHITE);
                          g.fillRect(0, originY_px + hauteurBloc_px - _2inches_px, largeurMur - (largeurMur - InchesToPixels(porte.CoordAccessoireX)), _2inches_px);
                          g.fillRect(largeurMur - (largeurMur - InchesToPixels(porte.CoordAccessoireX + porte.LargeurContour)), originY_px + hauteurBloc_px - _2inches_px, 
                                  largeurMur - InchesToPixels(porte.CoordAccessoireX + porte.LargeurContour), _2inches_px);  
                          
                          g.setColor(Color.BLACK);
                          g.drawRect(0, originY_px + hauteurBloc_px - _2inches_px, largeurMur - (largeurMur - InchesToPixels(porte.CoordAccessoireX)), _2inches_px);
                          g.drawRect(largeurMur - (largeurMur - InchesToPixels(porte.CoordAccessoireX + porte.LargeurContour)), originY_px + hauteurBloc_px - _2inches_px, 
                                  largeurMur - InchesToPixels(porte.CoordAccessoireX + porte.LargeurContour), _2inches_px); 
                          for(int y = 0; y < ligne.size(); y++){
                
                BlocMurDTO bloc = ligne.get(y);
                
                // On ajoute 1 comme padding
                int originX_px = InchesToPixels(bloc.CoordX) -_2inches_px;
                int largeurBloc_px = InchesToPixels(bloc.Largeur);
                               if(this.orientation == Mur.orientations.Est || this.orientation == Mur.orientations.Ouest){
                    originX_px += 2*_2inches_px;
                    //largeurBloc_px += 2*_2inches_px;
                }
                g.setColor(Color.WHITE);
                 g.fillRect(originX_px+ _2inches_px, originY_px, largeurBloc_px, _2inches_px);
                 g.setColor(Color.BLACK);
                 g.drawRect(originX_px + _2inches_px, originY_px, largeurBloc_px, _2inches_px);
                          }
                        }
                    }
            }
            else if(i==0 && listeAccessoiresLigne.isEmpty()){
                for(int y = 0; y < ligne.size(); y++){
                
                BlocMurDTO bloc = ligne.get(y);
                
                // On ajoute 1 comme padding
                int originX_px = InchesToPixels(bloc.CoordX) -_2inches_px;
                int largeurBloc_px = InchesToPixels(bloc.Largeur);
                               if(this.orientation == Mur.orientations.Est || this.orientation == Mur.orientations.Ouest){
                    originX_px += 2*_2inches_px;
                    //largeurBloc_px += 2*_2inches_px;
                }
                g.setColor(Color.WHITE);
                 g.fillRect(originX_px+ _2inches_px, originY_px, largeurBloc_px, _2inches_px);
                 g.fillRect(originX_px+ _2inches_px, originY_px + hauteurBloc_px - _2inches_px, largeurBloc_px, _2inches_px);
                 g.setColor(Color.BLACK);
                 g.drawRect(originX_px + _2inches_px, originY_px, largeurBloc_px, _2inches_px);
                 g.drawRect(originX_px + _2inches_px, originY_px + hauteurBloc_px - _2inches_px, largeurBloc_px, _2inches_px);
            }
            }
            //On dessine les contours de blocs
            if(i>0){
            for(int y = 0; y < ligne.size(); y++){
                
                BlocMurDTO bloc = ligne.get(y);
                
                // On ajoute 1 comme padding
                int originX_px = InchesToPixels(bloc.CoordX) -_2inches_px;
                int largeurBloc_px = InchesToPixels(bloc.Largeur);

                if(this.orientation == Mur.orientations.Est || this.orientation == Mur.orientations.Ouest){
                    originX_px += 2*_2inches_px;
                    //largeurBloc_px += 2*_2inches_px;
                }
                boolean absencePorteBloc = true;
                for(AccessoireDTO porte : listeAccessoiresLigne){
                    if( i== 0 && porte.TypeAccessoire == 2 && porte.CoordAccessoireX > bloc.CoordX && porte.CoordAccessoireX +porte.LargeurContour < bloc.CoordX +bloc.Largeur){
                        absencePorteBloc = false;
                    }
                }
                g.setColor(Color.WHITE);
                g.fillRect(originX_px+ _2inches_px, originY_px, largeurBloc_px, _2inches_px);
                if(absencePorteBloc){
                g.fillRect(originX_px+ _2inches_px, originY_px + hauteurBloc_px - _2inches_px, largeurBloc_px, _2inches_px);}
                g.setColor(Color.BLACK);
                g.drawRect(originX_px + _2inches_px, originY_px, largeurBloc_px, _2inches_px);
                if(absencePorteBloc){
                g.drawRect(originX_px + _2inches_px, originY_px + hauteurBloc_px - _2inches_px, largeurBloc_px, _2inches_px);}
            

                

                counter++;
            }
            }
            for(int y = 0; y < montants.size(); y++){
                int originBlocY = InchesToPixels(listeHauteurBlocsAfficheur.get(i));
                int coordMontant = 0;
                if(this.orientation == Mur.orientations.Nord || this.orientation == Mur.orientations.Sud){
                    coordMontant = InchesToPixels(montants.get(y)- 1);
                }
                else if(this.orientation == Mur.orientations.Est || this.orientation == Mur.orientations.Ouest){
                    coordMontant = InchesToPixels(montants.get(y)+ 3);
                }
                g.setColor(Color.WHITE);
                g.fillRect(coordMontant, originBlocY + _2inches_px, _2inches_px, hauteurBloc_px - (_2inches_px*2));
                g.setColor(Color.BLACK);
                g.drawRect(coordMontant, originBlocY + _2inches_px, _2inches_px, hauteurBloc_px - (_2inches_px*2));
            }
            
            //Accessoires
            for(int p=0; p < listeAccessoiresLigne.size();p++){

                Color couleurDeBase;
                if(listeAccessoiresLigne.get(p).isSelected){
                    couleurDeBase = Color.ORANGE;
                }else if(listeAccessoiresLigne.get(p).Conflit){
                    couleurDeBase = Color.RED;
                }
                else{
                    couleurDeBase = Color.WHITE;
                }
                int originAccX_px = InchesToPixels(listeAccessoiresLigne.get(p).CoordAccessoireX);
                if(this.orientation == Mur.orientations.Est || this.orientation == Mur.orientations.Ouest){
                    originAccX_px += 2*_2inches_px;
                }
                UUID idAccessoire = listeAccessoiresLigne.get(p).Id;
                double epaisseurLinteauPouce;
                int epaisseurLinteau;
                if(this.controlleur.getAccessoireFromUUID(orientation, idAccessoire).TailleLinteau == Accessoire.tailleLinteaux.deuxXhuit){
                    epaisseurLinteau = InchesToPixels(8);
                    epaisseurLinteauPouce = 8;

                }
                else if(this.controlleur.getAccessoireFromUUID(orientation, idAccessoire).TailleLinteau == Accessoire.tailleLinteaux.deuxXdix){
                    epaisseurLinteau = InchesToPixels(10);
                    epaisseurLinteauPouce = 10;
                }
                else{
                    epaisseurLinteau = InchesToPixels(6);
                    epaisseurLinteauPouce = 6;
                }

                int originBlocY = InchesToPixels(listeHauteurBlocsAfficheur.get(i));

                int largeurAccessoire_px = InchesToPixels(listeAccessoiresLigne.get(p).LargeurContour)+1;
                int hauteurAcc_px = InchesToPixels(listeAccessoiresLigne.get(p).HauteurContour);
                double coordYAccessoire = this.controlleur.getMurByOrientation(orientation).Hauteur - this.controlleur.getAccessoireFromUUID(orientation,
                        idAccessoire).CoordAccessoireY - listeAccessoiresLigne.get(p).HauteurContour - epaisseurLinteauPouce;
                int originAccY_px = InchesToPixels(coordYAccessoire);
                
                // On dessine le linteau
                g.setColor(couleurDeBase);
                g.fillRect(originAccX_px, originAccY_px, largeurAccessoire_px, epaisseurLinteau);
                g.setColor(Color.BLACK);
                g.drawRect(originAccX_px, originAccY_px, largeurAccessoire_px, epaisseurLinteau);
                ArrayList<Double> coordMontantsAccX = this.controlleur.getAccessoireFromUUID(orientation, idAccessoire).CoordMontantsX;
                
                //On dessin les montants au dessus du linteau
                for(double m : coordMontantsAccX){
                    m-=1;
                    if(this.orientation == Mur.orientations.Est || this.orientation == Mur.orientations.Ouest){
                        m+=4;
                    }
                    g.setColor(couleurDeBase);
                    g.fillRect(InchesToPixels(m), originBlocY  + _2inches_px, _2inches_px, originAccY_px - originBlocY  - _2inches_px );
                    g.setColor(Color.BLACK);
                    g.drawRect(InchesToPixels(m), originBlocY  + _2inches_px, _2inches_px, originAccY_px - originBlocY  - _2inches_px );
                }


                // cas d'une fenêtre:
                if(listeAccessoiresLigne.get(p).TypeAccessoire == 1){
                    //On dessine les batants de la fenêtre
                    int saveY = originAccY_px;

                    g.setColor(couleurDeBase);
                    g.fillRect(originAccX_px, originAccY_px + epaisseurLinteau, _2inches_px, hauteurAcc_px);
                    g.fillRect(originAccX_px + largeurAccessoire_px - _2inches_px, originAccY_px + epaisseurLinteau, _2inches_px, hauteurAcc_px);
                    g.setColor(Color.BLACK);
                    g.drawRect(originAccX_px, originAccY_px + epaisseurLinteau, _2inches_px, hauteurAcc_px);
                    g.drawRect(originAccX_px + largeurAccessoire_px - _2inches_px, originAccY_px + epaisseurLinteau, _2inches_px, hauteurAcc_px);
                    //On dessine le rebord de la fenêtre
                    originAccY_px += hauteurAcc_px + epaisseurLinteau;
                    g.setColor(couleurDeBase);
                    g.fillRect(originAccX_px, originAccY_px, largeurAccessoire_px, _2inches_px);
                    g.setColor(Color.BLACK);
                    g.drawRect(originAccX_px, originAccY_px, largeurAccessoire_px, _2inches_px);
                    for(double m : this.controlleur.getAccessoireFromUUID(orientation, idAccessoire).CoordMontantsX){
                        m-=1;
                        if(this.orientation == Mur.orientations.Est || this.orientation == Mur.orientations.Ouest){
                            m+=4;
                        }
                        g.setColor(Color.white);
                        g.setColor(couleurDeBase);
                        g.fillRect(InchesToPixels(m)-1, originAccY_px + _2inches_px, _2inches_px +1, originBlocY + InchesToPixels(hauteurRestante) - originAccY_px  -_2inches_px -_2inches_px);
                        g.setColor(Color.BLACK);
                        g.drawRect(InchesToPixels(m)-1, originAccY_px + _2inches_px, _2inches_px + 1, originBlocY + InchesToPixels(hauteurRestante) - originAccY_px  -_2inches_px -_2inches_px);
                    }



                    /*
                    On cree une hitzone pour la fenetre et offrir le drag and drop
                    */
                    int x1 = originAccX_px;
                    int xSize = InchesToPixels(listeAccessoiresLigne.get(p).LargeurContour);
                    int x2 = x1 + xSize;
                    int y1 = saveY;
                    int ySize = InchesToPixels(listeAccessoiresLigne.get(p).HauteurContour) + epaisseurLinteau;
                    int y2 = y1 + ySize;

                    this.hitzones.add(new Hitzone(x1, x2, y1, y2, listeAccessoiresLigne.get(p).Id, Hitzone.TypeAccessoire.Accessoire));

                    //Color c = g.getColor();
                    //g.setColor(Color.BLUE);
                    //g.fillRect(x1, y1, xSize, ySize);
                    //g.setColor(c);

                    /*
                    Fin de la creation de la hitzone, on peut de-commenter en haut pour la visualiser
                    */

                }
                //cas d'une porte
                else if(listeAccessoiresLigne.get(p).TypeAccessoire == 2){
                    g.setColor(couleurDeBase);
                    g.fillRect(originAccX_px, originAccY_px + epaisseurLinteau, _2inches_px,hauteurAcc_px+1);
                    g.fillRect(originAccX_px+largeurAccessoire_px -_2inches_px, originAccY_px + epaisseurLinteau, _2inches_px,hauteurAcc_px +1);
                    g.setColor(Color.BLACK);
                    g.drawRect(originAccX_px, originAccY_px + epaisseurLinteau, _2inches_px, hauteurAcc_px+1);
                     g.drawRect(originAccX_px+largeurAccessoire_px -_2inches_px, originAccY_px + epaisseurLinteau, _2inches_px,hauteurAcc_px +1);

                     /*
                    On cree une hitzone pour la fenetre et offrir le drag and drop
                    */
                    int x1 = originAccX_px;
                    int xSize = InchesToPixels(listeAccessoiresLigne.get(p).LargeurContour);
                    int x2 = x1 + xSize;
                    int y1 = originAccY_px;
                    int ySize = InchesToPixels(listeAccessoiresLigne.get(p).HauteurContour) + epaisseurLinteau;
                    int y2 = y1 + ySize;

                    this.hitzones.add(new Hitzone(x1, x2, y1, y2, listeAccessoiresLigne.get(p).Id, Hitzone.TypeAccessoire.Accessoire));

                    //Color c = g.getColor();
                    //g.setColor(Color.BLUE);
                    //g.fillRect(x1, y1, xSize, ySize);
                    //g.setColor(c);

                    /*
                    Fin de la creation de la hitzone, on peut de-commenter en haut pour la visualiser
                    */



                }
            }
            hauteurRestante = Preferences.global().getLongueurMax24()+4; 
        }
        for(EntremiseMurDTO entremise: this.controlleur.getEntremisesMur(orientation)){
            int coordY = InchesToPixels(this.controlleur.getHauteurMursCabanon()- entremise.CoordEntremiseY);
            //System.out.println(this.controlleur.getHauteurMursCabanon()- entremise.CoordEntremiseY);
            //System.out.println(entremise.Longueur);
            if(entremise.AccessoireParent == null && entremise.selected){
                g.setColor(Color.ORANGE);
            }
            else if(entremise.Conflit){
                g.setColor(Color.RED);
            }
            else{
                g.setColor(Color.white);
            }
            int coordXEntremise = InchesToPixels(entremise.CoordEntremiseX);
            if(this.orientation == Mur.orientations.Est || this.orientation == Mur.orientations.Ouest){
                coordXEntremise += InchesToPixels(4);
            }
            g.fillRect(coordXEntremise, coordY, InchesToPixels(entremise.Longueur) - _2inches_px, _2inches_px);
            g.setColor(Color.black);
            g.drawRect(coordXEntremise, coordY, InchesToPixels(entremise.Longueur) - _2inches_px, _2inches_px);

            if(entremise.AccessoireParent == null){
                /*
               On cree une hitzone pour la fenetre et offrir le drag and drop
               */
               int x1 = coordXEntremise;
               int xSize = InchesToPixels(entremise.Longueur) - _2inches_px+3;
               int x2 = x1 + xSize;
               int y1 = coordY;
               int ySize = _2inches_px;
               int y2 = y1 + ySize;

               this.hitzones.add(new Hitzone(x1, x2, y1, y2, entremise.Id, Hitzone.TypeAccessoire.EntremiseMur));

               //Color c = g.getColor();
               //g.setColor(Color.BLUE);
               //g.fillRect(x1, y1, xSize, ySize);
               //g.setColor(c);

               /*
               Fin de la creation de la hitzone, on peut de-commenter en haut pour la visualiser
               */
            }
       }
    }
    
    @Override
    public double getElementWidth(){
        MurDTO mur = controlleur.getMurByOrientation(this.orientation);
        if(this.orientation == Mur.orientations.Est || this.orientation == Mur.orientations.Ouest){
         return mur.Largeur + 9;
        }
        return mur.Largeur+1;
    };
    
    @Override
    public double getElementHeight(){
        MurDTO mur = controlleur.getMurByOrientation(this.orientation);
        return mur.Hauteur+1;
    };
    
}
