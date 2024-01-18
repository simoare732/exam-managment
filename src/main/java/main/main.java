package main;

import control.closeListener;
import view.mainPanel;

import javax.swing.*;

import java.awt.*;

/**
 * Classe main per una tabella che gestisce gli esami per gli studenti.
 * @author Aresta Simone
 * @version 1.0
 */

public class main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame finestra = new JFrame("Gestione esami");
        mainPanel p = new mainPanel();

        finestra.add(p);



        finestra.setSize(550,500);
        finestra.setMinimumSize(new Dimension(450, 200));
        finestra.setLocationRelativeTo(null); //Fa apparire la finestra al centro
        finestra.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        finestra.addWindowListener(new closeListener(p.getTabModel()));
        finestra.setVisible(true);
    }
}
