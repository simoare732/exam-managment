package control;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

/**
 * Classe Listener per il tasto stampa
 * @author Aresta Simone
 * @version 1.0
 */
public class printListener implements ActionListener {
    /** Tabella da stampare*/
    private JTable tab;

    /**
     * Metodo costruttore per inizializzare la tabella che verrà stampata
     * @param tab Tabella da stampare
     */
    public printListener(JTable tab){
        this.tab=tab;
    }

    /**
     * Invoca questo metodo quando il bottone Stampa... viene premuto
     *
     * @param e L'evento che sarà processato
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //printerJob rappresenta un lavoro di stampa
        PrinterJob printerJob = PrinterJob.getPrinterJob();

        //impostiamo il nome del lavoro
        printerJob.setJobName("Stampa tabella");

        if (!printerJob.printDialog()) {
            return; // L'utente ha annullato la stampa
        }

        /*Questa riga configura il lavoro di stampa per utilizzare una JTable.
        tab.getPrintable è un metodo di JTable che restituisce un oggetto stampabile con determinati parametri:
        il primo parametro indica la modalità di stampa desiderata, in questo caso JTable.PrintMode.FIT_WIDTH indica
        che la tabella deve essere adattata alla larghezza della pagina. Gli altri due parametri indicano il formato
        per l'intestazione e il footer della pagina, che in questo caso non ci servono e sono impostati a null
        */
        printerJob.setPrintable(tab.getPrintable(JTable.PrintMode.FIT_WIDTH, null, null));

        //Effettua effettivamente l'operazione di stampa. Se c'è un errore come la mancanza di carta o altro lo segnala
        try {
            printerJob.print();
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(null, "Errore durante la stampa: " + ex.getMessage(), "Errore di stampa", JOptionPane.ERROR_MESSAGE);
        }
    }

}
