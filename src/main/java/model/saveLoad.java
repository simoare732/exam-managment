package model;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;

/**
 * Classe che raggruppa i metodi che permettono di salvare su file una tabella e caricare da file una tabella.
 * ATTENZIONE: per la precisione ciò che viene salvato/caricato sono le varie righe della tabella non la tabella in se
 * I metodi per salvare/caricare sono synchronized per evitare errori di qualsiasi tipo
 * @author Aresta Simone
 * @version 1.0
 */
public class saveLoad {

    /**
     * Salva la tabella (per la precisione le informazioni delle righe che ci sono in tabella, cioè le entry)
     * @param tM TableModel della tabella
     * @param file file in cui salvare la tabella
     */
    public static synchronized void saveTableToFile(tabModel tM, File file){
        try{
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file));
            //Scrivo su file EntrysAll e non entry, in questo modo indipendentemente dal filtro immesso salvo tutto
            os.writeObject(tM.getEntrysAll());
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            System.err.println("File non trovato");
            return;
        } catch (IOException e) {
            System.err.println("Errore");
            e.printStackTrace();
            return;
        }
    }

    /**
     * Carica una tabella (o per la precisione le informazioni nelle righe della tabella) da file
     * @param file file da cui caricare la tabella
     * @return ArrayList contenente tutte le entry in tabella
     */
    public static synchronized ArrayList<entry> loadFromFile(File file){
        try{
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));
            ArrayList <entry> ent = (ArrayList<entry>) is.readObject();
            is.close();
            return ent;
        } catch (FileNotFoundException e) {
            System.err.println("File non trovato");
            return null;
        } catch (ClassNotFoundException e) {
            System.err.println("Classe non esistente");
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Errore");
            return null;
        }
    }

    /**
     * Metodo che apre una finestra per poter salvare/carica su/da file
     * @param title Titolo della finestra
     * @param typeFile Tipo di file da salvare/carica
     * @return JFileChooser rappresentante il file scelto
     */
    public static JFileChooser chooseFile(String title, String typeFile){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(title);
        String desc = "File (*."+typeFile+")";
        fileChooser.setFileFilter(new FileNameExtensionFilter(desc,typeFile));

        return fileChooser;
    }
}
