/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resources;
import static java.lang.Math.round;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author gabriel
 */
public class UnitConverter {

    //Precision should be 1 / (aPowerOfTwo).
    private static final double PRECISION = ((double) 1 / (double) 64);
    
    public static double defractionator(String inputString){
        Pattern pattern = Pattern.compile("^(\\d+)\\s+(\\d+)/(\\d+)$|^(\\d+)/(\\d+)$|^(\\d+)$");
        Matcher matcher = pattern.matcher(inputString);
        int pouces = 0;
        int numerateur = 0;
        int denominateur = 1;
        if (matcher.find()) {
            if (matcher.group(1)!=null){
                pouces = Integer.parseInt(matcher.group(1));
                numerateur = Integer.parseInt(matcher.group(2));
                denominateur = Integer.parseInt(matcher.group(3));
            }
            else if (matcher.group(6)!=null){
                pouces = Integer.parseInt(matcher.group(6));
            }
            else if(matcher.group(4)!=null){
                numerateur = Integer.parseInt(matcher.group(4));
                denominateur = Integer.parseInt(matcher.group(5));
            }
        }
        double retour = (double) ((double) pouces + (double) numerateur / (double) denominateur);
        return retour;
    }
    
    public static String fractionator(double inputDouble){
        int entier = (int) inputDouble;
        double reste = inputDouble - entier;
        int denominateur = (int) ((double) 1 / (double) PRECISION);
        int numerateur = round(round(reste / PRECISION));
        while ((denominateur % 2 == 0) && (numerateur % 2 == 0)){
            denominateur = denominateur / 2;
            numerateur = numerateur / 2;
        }
        if (numerateur == 0){
           return (entier+"");
        }
        else{
           return (entier + " " + numerateur + "/" + denominateur); 
        }
    }
    
    public static String toMoneyString(double inputMoney){
        String moneyString = String.format("%.2f", inputMoney);
        return moneyString;
    }
    
    public static double toMoneyDouble(String inputMoney){
        double unformattedMoney = Double.parseDouble(inputMoney.replace(",", "."));
        String formattedMoneyString = String.format("%.2f", unformattedMoney);
        double formattedMoney = Double.parseDouble(formattedMoneyString.replace(",", "."));
        return formattedMoney;
    }
}
