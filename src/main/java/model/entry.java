package model;

import java.io.Serializable;

/** Classe che rappresenta le informazioni contenute nella riga di una tabella
 * @author Aresta Simone
 * @version 1.0
 */

public class entry implements insegnamento, studente, Serializable{
    //L'implementazione di insegnamento e studente è un esempio di utilizzo dell'ereditarietà

    /** Nome dello studente */
    private String nome;
    /** Cognome dello studente */
    private String cognome;
    /** Nome dell'insegnamento */
    private String nomeMateria;
    /** Voto totale preso per quell'insegnamento */
    private int voto;
    /** SE l'esame e' composto, l'insieme dei voti di cui e' composto un'esame */
    private int[] voti;
    /** SE l'esame e' composto, l'insieme dei pesi relativi ai voti */
    private int[] pesi;
    /** Valore booleano che rappresenta la lode (true se Si, false se no) */
    private boolean lode;
    /** CFU corrispondenti per quell'insegnamento */
    private int cfu;

    /**
     * Metodo costruttore per inizializzare tutti gli attributi della classe. Inizialmente voti e pesi sono null perchè non è detto che quella riga riguarderà un'esame composto
     */
    public entry(){
        nome="";
        cognome="";
        nomeMateria="";
        voti=null;
        pesi=null;
        voto=0;
        lode=false;
        cfu=0;
    }


    /**
     * Metodo che ritorna il nome dello studente
     * @return Una stringa rappresentante il nome (non cognome) dello studente
     */
    @Override
    public String getNome() {
        return nome;
    }

    /**
     * Metodo che imposta il nome dello studente
     * @param nome Una stringa rappresentante il nome (non cognome) dello studente
     */
    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Metodo che ritorna il cognome dello studente
     * @return Una stringa rappresentante il cognome dello studente
     */
    @Override
    public String getCognome() {
        return cognome;
    }

    /**
     * Metodo che imposta il cognome dello studente
     * @param cognome  Una stringa rappresentante il cognome dello studente
     */
    @Override
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    @Override
    public String getNomeMateria() {
        return nomeMateria;
    }

    /**
     * Metodo per modificare il nome della materia
     * @param nomeMateria nuovo nome della materia
     */
    @Override
    public void setNomeMateria(String nomeMateria) {
        this.nomeMateria = nomeMateria;
    }

    /**
     * Metodo che restituisce il voto per quella materia
     * @return un intero equivalente al voto
     */
    @Override
    public int getVoto() {
        return voto;
    }

    /**
     * Metodo che imposta il voto per un'insegnamento
     * @param voto voto per quell'insegnamento
     */
    @Override
    public void setVoto(int voto) {
        this.voto = voto;
    }

    /**
     * Metodo che restituisce vero se c'è la lode, falso altrimenti
     * @return true se la lode c'è, false altrimenti
     */
    @Override
    public boolean isLode() {
        return lode;
    }

    /**
     * Metodo per impostare la lode
     * @param lode valore della lode
     */
    @Override
    public void setLode(boolean lode) {
        this.lode = lode;
    }

    /**
     * Metodo che restituisce la quantita' di CFU per un insegnamento
     * @return un intero equivalente al numero di CFU
     */
    @Override
    public int getCfu() {
        return cfu;
    }

    /**
     * Metodo che imposta la quantità di CFU per un insegnamento
     * @param cfu intero equivalente al numero di CFU
     */
    @Override
    public void setCfu(int cfu) {
        this.cfu = cfu;
    }

    /**
     * Metodo che inizializza gli array che conterranno i voti e i pesi per quei voti di un'esame composto
     * @param numTests numero di esami intermedi
     */
    @Override
    public void esameCompostoVoti(int numTests){
        voti = new int[numTests];
        pesi = new int[numTests];
    }

    /**
     * Metodo per impostare uno specifico voto nella posizione indice.
     * @param voto Voto da impostare
     * @param indice Indice della posizione del voto
     */
    @Override
    public void setVoti(int voto, int indice){
        voti[indice] = voto;
    }
    /**
     * Metodo che legge un voto nella posizione indice
     * @param indice Indice della posizione del voto da leggere
     * @return Il voto letto
     */
    @Override
    public int getVoti(int indice){
        return voti[indice];
    }

    /**
     * Metodo che imposta il peso (il quale avra' la stessa posizione del voto del quale fa peso) nella posizione indice
     * @param peso In percentuale quanto vale un voto nel totale
     * @param indice Indice della posizione della posizione del peso (o del voto al quale si riferisce)
     */
    @Override
    public void setPesi(int peso, int indice){
        pesi[indice] = peso;
    }

    /**
     * Metodo che legge un peso nella posizione indicata
     * @param indice Indice della posizione del peso da leggere
     * @return Il peso letto
     */
    @Override
    public int getPesi(int indice){
        return pesi[indice];
    }

    /**
     * Metodo che restituisce l'array dei voti che compone un'esame composto
     * @return array di voti
     */
    @Override
    public int[] getArrVoti(){
        return voti;
    }

    /**
     * Metodo che restituisce l'array dei pesi riferendosi ai voti
     * @return array di pesi
     */
    @Override
    public int[] getArrPesi(){
        return pesi;
    }

    /**
     * Metodo che calcola la media pesata dato un array di voti e della percentuale di ogni voto
     * @param voti voti di cui bisogna fare la media
     * @param pesi la percentuale di ogni voto
     * @return ritorna la media pesata
     */
    public static double mediaPesata(int [] voti, int[] pesi){
        double sum=0;
        for(int i=0;i<voti.length;i++)
            sum = sum+(voti[i]*pesi[i]);
        double denominatore= java.util.stream.IntStream.of(pesi).sum();
        return sum/denominatore;
    }

}
