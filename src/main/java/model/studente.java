package model;

/** Interfaccia che rappresenta le informazioni sullo studente, contenuti nella riga di una tabella
 * @author Aresta Simone
 * @version 1.0
 */
public interface studente{

    /**
     * Metodo che ritorna il nome dello studente
     * @return Una stringa rappresentante il nome (non cognome) dello studente
     */
    public String getNome();

    /**
     * Metodo che imposta il nome dello studente
     * @param nome Una stringa rappresentante il nome (non cognome) dello studente
     */
    public void setNome(String nome);

    /**
     * Metodo che ritorna il cognome dello studente
     * @return Una stringa rappresentante il cognome dello studente
     */
    public String getCognome();

    /**
     * Metodo che imposta il cognome dello studente
     * @param cognome  Una stringa rappresentante il cognome dello studente
     */
    public void setCognome(String cognome);
}