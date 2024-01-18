package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Classe che rappresenta il thread che salva regolarmente la tabella su un file temporaneo
 * @author Aresta Simone
 * @version 1.0
 */

public class saveTmp extends Thread{
    /** TableModel della tabella attualmente in uso */
    private tabModel tM;
    /** File temporaneo dove verrà salvata la tabella */
    private File tempFile;
    /** Valore booleano che indica quando (e se) interrompere il thread */
    private boolean isRunning;

    /**
     * Costruttore che e' inizializzato con un TableModel passato come parametro
     * @param tM TableModel della tabella in uso al momento del salvataggio
     */
    public saveTmp(tabModel tM){
        this.tM = tM;
        isRunning = true;

        /*
        Viene creata una directory temp_files nella dir corrente, qui viene creato (o sovrascritto)
        il file tabella.dat ogni 10 secondi.
         */
        File directory = new File(System.getProperty("user.dir"),  "temp_files");
        if(!directory.exists()){
            if(directory.mkdir())
                System.out.println("Cartella creata");
            else {
                System.out.println("Cartella non creata");
                return;
            }
        }
        tempFile = new File(directory, "tabella.dat");
        try{
            if(!tempFile.exists()){
                if(tempFile.createNewFile())
                    System.out.println("File creato");
                else System.out.println("File non creato");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Metodo che ogni 10 secondi salva la tabella nello stato in cui si trova
     * Il processo di salvataggio è un processo atomico
     */
    public void run(){
        while(isRunning){
            try {
                saveLoad.saveTableToFile(tM, tempFile);
                System.out.println("File salvato");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Metodo per interrompere in modo sicuro il thread.
     * Nell'applicazione questo metodo non viene utilizzato
     */
    public void interruptSave(){
        isRunning=false;
        interrupt();
    }
}
