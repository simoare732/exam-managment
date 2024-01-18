package model;

/** Interfaccia che rappresenta le informazioni sull'insegnamento, contenuti nella riga di una tabella
 * @author Aresta Simone
 * @version 1.0
 */
public interface insegnamento {

    /**
     * Metodo che restituisce il nome dell'insegnamento
     * @return Una stringa che è il nome della materia
     */
    public String getNomeMateria();

    /**
     * Metodo per modificare il nome della materia
     * @param nome nuovo nome della materia
     */
    public void setNomeMateria(String nome);

    /**
     * Metodo che restituisce il voto per quella materia
     * @return un intero equivalente al voto
     */
    public int getVoto();

    /**
     * Metodo che imposta il voto per un'insegnamento
     * @param voto voto per quell'insegnamento
     */
    public void setVoto(int voto);

    /**
     * Metodo che restituisce vero se c'è la lode, falso altrimenti
     * @return true se la lode c'è, false altrimenti
     */
    public boolean isLode();

    /**
     * Metodo per impostare la lode
     * @param lode valore della lode
     */
    public void setLode(boolean lode);

    /**
     * Metodo che restituisce la quantita' di CFU per un insegnamento
     * @return un intero equivalente al numero di CFU
     */
    public int getCfu();

    /**
     * Metodo che imposta la quantità di CFU per un insegnamento
     * @param cfu intero equivalente al numero di CFU
     */
    public void setCfu(int cfu);

    /**
     * Metodo che inizializza gli array che conterranno i voti e i pesi per quei voti di un'esame composto
     * @param numTests numero di esami intermedi
     */
    public void esameCompostoVoti(int numTests);

    /**
     * Metodo per impostare uno specifico voto nella posizione indice.
     * @param voto Voto da impostare
     * @param indice Indice della posizione del voto
     */
    public void setVoti(int voto, int indice);
    /**
     * Metodo che legge un voto nella posizione indice
     * @param indice Indice della posizione del voto da leggere
     * @return Il voto letto
     */
    public int getVoti(int indice);

    /**
     * Metodo che imposta il peso (il quale avra' la stessa posizione del voto del quale fa peso) nella posizione indice
     * @param peso In percentuale quanto vale un voto nel totale
     * @param indice Indice della posizione della posizione del peso (o del voto al quale si riferisce)
     */
    public void setPesi(int peso, int indice);

    /**
     * Metodo che legge un peso nella posizione indicata
     * @param indice Indice della posizione del peso da leggere
     * @return Il peso letto
     */
    public int getPesi(int indice);

    /**
     * Metodo che restituisce l'array dei voti che compone un'esame composto
     * @return array di voti
     */
    public int[] getArrVoti();

    /**
     * Metodo che restituisce l'array dei pesi riferendosi ai voti
     * @return array di pesi
     */
    public int[] getArrPesi();

}