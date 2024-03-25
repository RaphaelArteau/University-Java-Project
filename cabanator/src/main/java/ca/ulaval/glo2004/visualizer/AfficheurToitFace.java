/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.visualizer;

import ca.ulaval.glo2004.cabanon.domaine.toit.ToitDTO;
import ca.ulaval.glo2004.preferences.Preferences;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Utilisateur
 */
public class AfficheurToitFace extends Afficheur{
    private ToitDTO toit;
    
    public AfficheurToitFace(Visualizer parent) {
        super(parent);
        this.toit = this.controlleur.getToit();
    }
    
   @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.toit = this.controlleur.getToit();
        int _4inches_px = InchesToPixels(4);
        int _4angledinches_px = (int) (_4inches_px / Math.sin(Math.toRadians(toit.AngleToit / 2)));
        double largeur;
        if(toit.OrientationToit ==1){
        largeur = InchesToPixels(this.controlleur.getLargeurCabanon());}
        else{ largeur = InchesToPixels(this.controlleur.getProfondeurCabanon());}
        double hauteur = InchesToPixels(this.controlleur.getHauteurToit());
        int porteAfaux = InchesToPixels(this.controlleur.getPorteAFaux());
        int hauteurPorteAfaux = InchesToPixels(toit.PorteAFauxHauteur);
        int hypotenusePorteAfaux = InchesToPixels(toit.PorteAFauxHypothenus);
     //   System.out.print(this.controlleur.getToit().HauteurToit);
     
    //ferme 
    //entrait
    /*int RectX1 = porteAfaux;
    int RectX2 = RectX1 + (int)largeur;
    int RectX3 = RectX2;
    int RectX4 = RectX1;
    int [] BaseX = {RectX1, RectX2, RectX3, RectX4};
    
    int RectY1 = (int)hauteur;
    int RectY2 = RectY1;
    int RectY3 = RectY1 + _4inches_px;
    int RectY4 = RectY3;
    int [] BaseY = {RectY1, RectY2,RectY3,RectY4};

    g.setColor(Color.black);
    g.drawPolygon(BaseX, BaseY, 4);
    g.setColor(Color.white);
    g.fillPolygon(BaseX, BaseY, 4);
    g.setColor(Color.black);*/

    
   // g.drawRect(50, (int)hauteur + _4inches_px, (int)largeur, _4inches_px);
    //chevron 1
    int x = 0;
  //  System.out.print(x);
    int x2 = (int)largeur / 2 + porteAfaux;
    int x3 = (int)largeur / 2 + porteAfaux;
    int x4 = x;
    int [] Chevron1X = {x,x2,x3,x4};
     
    int y1 = hauteurPorteAfaux - _4angledinches_px;
    int y2 = 0;
    int y3 = y2 + _4angledinches_px;
    int y4 = hauteurPorteAfaux;
    int [] Chevron1Y = {y1,y2,y3,y4};
        //poincons principal

    g.setColor(Color.white);
    g.fillRect(x2 - InchesToPixels(2), y3, _4inches_px, (int)hauteur -InchesToPixels(4.2));
    g.setColor(Color.black);
    g.drawRect(x2 - InchesToPixels(2), y3, _4inches_px, (int)hauteur-InchesToPixels(4.2));
    g.setColor(Color.black);
    
    g.setColor(Color.WHITE);
    g.fillRect(InchesToPixels(this.toit.PorteAFaux), (int)hauteur, (int)largeur, InchesToPixels(4));
    g.setColor(Color.BLACK);
    g.drawRect(InchesToPixels(this.toit.PorteAFaux), (int)hauteur, (int)largeur, InchesToPixels(4));
    if(PixelsToInches((int)largeur) > Double.parseDouble(this.controlleur.getLongueurMax24())){
        g.drawLine(porteAfaux+(int)largeur/2, (int)hauteur, porteAfaux+(int)largeur/2, (int)hauteur+InchesToPixels(4));
    }
    //poincons supp
      int demiHauteur = (int)hauteur / 2;
      int quartLargeur = (int)largeur/4;
      if (this.controlleur.getTailleChevronUnitaire() >= Preferences.getLongueurMax24()){
           g.setColor(Color.white);
           g.fillRect(porteAfaux+quartLargeur-InchesToPixels(1), y3+(int)hauteur/2 -InchesToPixels(5), _4inches_px, (int)(hauteur / 2));
           g.fillRect(porteAfaux+3*quartLargeur-InchesToPixels(3), y3+(int)hauteur/2 -InchesToPixels(5), _4inches_px, (int)(hauteur) / 2);
           g.setColor(Color.black);
           g.drawRect(porteAfaux+quartLargeur-InchesToPixels(1), y3+(int)hauteur/2-InchesToPixels(5), _4inches_px, (int)(hauteur / 2));
           g.drawRect(porteAfaux+3*quartLargeur-InchesToPixels(3), y3+(int)hauteur/2-InchesToPixels(5), _4inches_px, (int)(hauteur) / 2);
           

           g.setColor(Color.black);
       }
       
    
    g.setColor(Color.white);
    g.fillPolygon(Chevron1X, Chevron1Y, 4);
    g.setColor(Color.black);
    g.drawPolygon(Chevron1X, Chevron1Y, 4);

    g.setColor(Color.black);
    
    //chevron 2
    int x5 = (int)largeur + 2 * porteAfaux;
    int x6 = x5;
    int [] Chevron2X = {x6,x2,x3,x5};
    int [] Chevron2Y = {y1,y2,y3,y4};
    
    g.setColor(Color.white);
    g.fillPolygon(Chevron2X, Chevron2Y, 4);
    g.setColor(Color.black);
    g.drawPolygon(Chevron2X, Chevron2Y, 4);
    g.setColor(Color.black);

    if (this.controlleur.getTailleChevronUnitaire() >= Preferences.getLongueurMax24()){
        g.setColor(Color.black);
        g.drawLine(porteAfaux+quartLargeur-InchesToPixels(1), y3+(int)hauteur/2-InchesToPixels(5) + InchesToPixels(4*Math.sin(Math.toRadians(90-toit.AngleToit/2))/Math.tan(Math.toRadians(90-toit.AngleToit/2))),
                   porteAfaux+quartLargeur-InchesToPixels(1) - InchesToPixels(4*Math.sin(Math.toRadians(90-toit.AngleToit/2))),y3+(int)hauteur/2-InchesToPixels(5))
                   ;
        g.drawLine(porteAfaux+3*quartLargeur, y3+(int)hauteur/2-InchesToPixels(7),
                   porteAfaux+3*quartLargeur - InchesToPixels(4*Math.sin(Math.toRadians(90-toit.AngleToit/2))),y3+(int)hauteur/2-InchesToPixels(7) + InchesToPixels(4*Math.sin(Math.toRadians(90-toit.AngleToit/2))/Math.tan(Math.toRadians(90-toit.AngleToit/2))))
                   ;
    }
     /* 
     int p1 =RectX1 - _4inches_px;
     int p2 = RectX1;
     int p3 = p2 - porteAfaux;
     int p4 = p3 - _4inches_px;
     int[] porteAfauxX = {p1,p2,p3,p4};
     
     int p5 = (int)hauteur;
     int p6 = p5;
     int p7 = p5 + porteAfaux;
     int p8 = p7;
     int[] porteAfauxY = {p5,p6,p7,p8};
     g.drawPolygon(porteAfauxX, porteAfauxY, 4);
     */

    /*
     // triangle externe pour ferme, avec bonne largeur
        int x1 = InchesToPixels(50);
        int x2 = x1 + ((int)largeur / 2);
        int x3 = x1 + ((int)largeur);
        int [] FermeExterneX = {x1,x2,x3};
        
        int y1 = InchesToPixels(60) + (int)hauteur;
        int y2 = InchesToPixels(55);
        int y3 = y1;
        int [] FermeExterneY = {y1,y2,y3};
        
        g.drawPolygon(FermeExterneX, FermeExterneY, 3);
        

        
        //triangle interne pour ferme, avec bonne hauteur
       int x4 = x1 + InchesToPixels(7);
       int x5 = x2;
       int x6 = x1 + ((int)largeur) - InchesToPixels(7);
       int [] FermeInterneX = {x4,x5,x6};
       
       int y4 = y1 - _4inches_px;
       int y5 = y2 + _4inches_px;
       int y6 = y4;
       int [] FermeInterneY = {y4,y5,y6};
       g.drawPolygon(FermeInterneX, FermeInterneY, 3);
       
       //poincons principal
       g.drawRect(x2 - InchesToPixels(1), y5, _4inches_px, (int)hauteur+ InchesToPixels(3));
       
       //poincons supp
      int demiHauteur = (int)hauteur / 2;
      if (InchesToPixels(this.controlleur.getTailleChevronUnitaire()) >= InchesToPixels(Preferences.getLongueurMax26())){
           g.drawRect((int)largeur / 2 + _4inches_px, y5 + demiHauteur + _4inches_px, _4inches_px, (((int)hauteur) / 2));
           g.drawRect(x3 - x1, y5 + demiHauteur + _4inches_px, _4inches_px, (((int)hauteur) / 2));
       }
      
      //porte a faux
    int porteAfaux = InchesToPixels(this.controlleur.getToit().PorteAFaux);
    int firmeToit = InchesToPixels(Math.sqrt((porteAfaux* (Math.floor(porteAfaux / 5))) + (porteAfaux*(Math.floor(porteAfaux / 5)))));
   // g.drawRect(x4, y4, x1 - x4, firmeToit);

    int [] porteAfauxX = {x1,x4 - InchesToPixels(3), x1 - porteAfaux + (x4 - x1) - InchesToPixels(3), x1 - porteAfaux};
    int [] porteAfauxY = {y1, y1, y1 + firmeToit, y1 + firmeToit};
    g.drawPolygon(porteAfauxX, porteAfauxY, 4);
    
    
    int [] porteAfaux2X = {x6 + InchesToPixels(3),x3, x3 + porteAfaux,x3 + porteAfaux -(x3 - x6) + InchesToPixels(3)};
    int [] porteAfaux2Y = {y3,y3,y3 + firmeToit, y3 + firmeToit};
    g.drawPolygon(porteAfaux2X, porteAfaux2Y, 4);

       */
              


    
    
    
    }


            
            
    
    @Override
    public double getElementWidth() {
        ToitDTO toit = this.controlleur.getToit();
        if(this.toit.OrientationToit ==1){
        return this.controlleur.getLargeurCabanon() + 2 * toit.PorteAFaux+1;}
        else{return this.controlleur.getProfondeurCabanon() + 2*toit.PorteAFaux+1;}

    }
    
    @Override
    public double getElementHeight() {
        ToitDTO toit = this.controlleur.getToit();
        return  toit.PorteAFauxHauteur + InchesToPixels(8);
    }


}