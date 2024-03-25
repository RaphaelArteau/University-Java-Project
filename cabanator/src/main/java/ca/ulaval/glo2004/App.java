package ca.ulaval.glo2004;

import ca.ulaval.glo2004.gui.WelcomeScreen;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.*;


public class App {
    
    public static void main(String[] args) {
        /*
        String osName = System.getProperty("os.name").toLowerCase();
        boolean isMac = osName.startsWith("mac os x");
        if (isMac) {
            try {
                // Set Nimbus look and feel
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        } 
        */

        boolean loadedStyle = false;
        try {
                // Set Nimbus look and feel
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            loadedStyle = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

       
        

        boolean _20thTime = false;
        File file = new File("counter.txt");
        try {
            FileInputStream input = new FileInputStream(file);

            byte[] buffer = new byte[1024];
            int length = input.read(buffer);
            String strCounter = new String(buffer, 0, length).trim();

            int counter = Integer.parseInt(strCounter);
            counter++;
            if(counter == 20){
                _20thTime = true;
                counter = 1;
            }
            FileOutputStream output = new FileOutputStream(file);
            output.write(Integer.toString(counter).getBytes());
            output.close();
            input.close();
        } catch (FileNotFoundException e) {
            try {
                FileOutputStream output = new FileOutputStream(file);
                int counter = 1;
                output.write(Integer.toString(counter).getBytes());
                output.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        WelcomeScreen frame = new WelcomeScreen("Bienvenue dans Cabanator");
        if(!loadedStyle){
            JOptionPane.showMessageDialog(frame, "Avertissement! Le style n'a pas pu être correctement chargé!");
        }
        if(_20thTime){
            JOptionPane.showMessageDialog(frame, "Bravo! Vous avez ouvert ce logiciel plus de 20 fois, vous êtes une licorne! 🦄");
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}

