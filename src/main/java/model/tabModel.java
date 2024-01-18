package model;

import java.util.*;
import javax.swing.table.AbstractTableModel;

/** Classe che rappresenta il TableModel che avra' la tabella dell'applicazione. Estende AbstractTableModel
 * @author Aresta Simone
 * @version 1.0
 */

public class tabModel extends AbstractTableModel{
    /** Numero di colonne della tabella */
    private int columns;
    /** Numero di righe della tabella */
    private int rows;
    /** Array di stringhe contenente il nome delle colonne */
    private String [] fieldNames;
    /** Array contenente le voci della tabella visualizzate */
    private ArrayList<entry> entrys;
    /** Array contenente tutte le voci della tabella (indipendentemente dal fatto che siano o meno visualizzate) */
    private ArrayList <entry> entrysAll;
    /** Valore booleano che indica true quando la tabella e' stata modificata e non salvata */
    private boolean isDirty;

    /**
     * Metodo costruttore, riceve come parametro le l'array di voci che appariranno in tabella
     * @param entrys Array contenente le righe
     */

    public tabModel(ArrayList<entry> entrys){
        this.entrys=entrys;
        entrysAll = new ArrayList<>();
        entrysAll = (ArrayList)this.entrys.clone();
        fieldNames = new String[]{"Nome", "Cognome", "Materia", "Voto", "Lode", "CFU"};
        isDirty = false;
    }

    /**
     * Metodo che restituisce un'array con le righe visualizzate della tabella in quell'istante
     * @return L'array delle righe visualizzate in quell'istante
     */
    public ArrayList<entry> getEntrys(){
        return entrys;
    }

    /**
     * Metodo che imposta l'array delle righe della tabella visualizzate in quell'istante ad un array di voci passato come parametro
     * @param ent L'array delle righe visualizzate in quell'istante
     */
    public void setEntrys(ArrayList ent){
        entrys = (ArrayList<entry>) ent.clone();
    }

    /**
     * Metodo che restituisce l'array delle righe presente nella tabella
     * @return L'array delle righe della tabella
     */
    public ArrayList<entry> getEntrysAll(){
        return entrysAll;
    }

    /**
     * Metodo che imposta l'array delle righe presente nella tabella
     * @param ent Il nuovo array delle righe della tabella
     */
    public void setEntrysAll(ArrayList ent){
        entrysAll = (ArrayList<entry>) ent.clone();
    }

    /**
     * Ritorna il numero di righe nel modello. Un JTable
     * utilizza questo metodo per sapere quante righe visualizzare.
     *
     * @return il numero di righe nel modello
     */
    @Override
    public int getRowCount() {
        return entrys.size();
    }

    /**
     *Ritorna il numero di colonne nel modello. Un JTable
     *utilizza questo metodo per sapere quante colonne visualizzare.
     *
     * @return il numero di colonne nel modello
     */
    @Override
    public int getColumnCount() {
        return fieldNames.length;
    }

    /**
     * Ritorna il valore alla cella in posizione rowIndex e columnsIndex.
     *
     * @param rowIndex    la riga della cella richiesta
     * @param columnIndex la colonna della cella richiesta
     * @return il valore dell'oggeto specificato
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
            default:
                return entrys.get(rowIndex).getNome();
            case 1:
                return entrys.get(rowIndex).getCognome();
            case 2:
                return entrys.get(rowIndex).getNomeMateria();
            case 3:
                return entrys.get(rowIndex).getVoto();
            case 4:
                if(entrys.get(rowIndex).isLode())
                    return "SI";
                else return "NO";
            case 5:
                return entrys.get(rowIndex).getCfu();

        }
    }

    /**
     * Per dare il nome alle colonne
     * @param colonna Colonna selezionata
     * @return Ritorna il nome di quella colonna
     */
    @Override
    public String getColumnName(int colonna) {
        return fieldNames[colonna];
    }

    /**
     * Metodo che ritorna vero se la cella è editabile, falso altrimenti
     * @param rowIndex la riga della cella richiesta
     * @param columnIndex la colonna della cella richiesta
     * @return vero o falso
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    /**
     * Metodo per aggiungere le nuove entry in tabella
     * @param e Riga da aggiungere in tabella
     */
    public void aggiungiRiga(entry e){
        entrys.add(e);
        entrysAll.add(e);
        fireTableRowsInserted(entrys.size() - 1, entrys.size() - 1);
        fireTableDataChanged();
    }

    /**
     * Metodo che sostituisce la riga row con la riga newEntry
     * @param row Numero di Riga da sostituire
     * @param newEntry Nuova voce da inserire
     *
     */
    public void sostituisciRiga(int row, entry newEntry){
        entrys.set(row, newEntry);
        entrysAll.set(row, newEntry);
        fireTableCellUpdated(row, row);
        fireTableDataChanged();
    }

    /**
     * Metodo che elimina la riga row
     * @param row Numero di riga da eliminare
     */
    public void eliminaRiga(int row){
        entrys.remove(row);
        entrysAll.remove(row);
        fireTableRowsDeleted(row, row);
        fireTableDataChanged();
    }

    /**
     * Metodo che filtra la tabella in base al nome e cognome (e' case insensitive)
     * @param nome Stringa contenente nome e cognome con i quali filtrare la tabella
     */
    public void filterTableByName(String nome){
        entrys.clear();
        nome = nome.toLowerCase();
        String [] names = nome.split(" ",2); //Per gestire i casi di cognomi formati da più parole

        //Controllo se in names ci sono almeno due elementi, se no vuol dire che l'utente ha digitato una sola parola
        if(names.length>1) {
            for (int i = 0; i < entrysAll.size(); i++) {
                if (entrysAll.get(i).getNome().toLowerCase().contains(names[0]) && entrysAll.get(i).getCognome().toLowerCase().contains(names[1]))
                    entrys.add(entrysAll.get(i));
            }
        }
        else{
            for(int i=0;i<entrysAll.size();i++){
                if(entrysAll.get(i).getNome().toLowerCase().contains(nome) || entrysAll.get(i).getCognome().toLowerCase().contains(nome))
                    entrys.add(entrysAll.get(i));
            }
        }
        fireTableDataChanged();

    }
    /**
     * Metodo che filtra la tabella in base alla materia (e' case insensitive)
     * @param materia Stringa contenente la materia con la quale filtrare la tabella
     */
    public void filterTableBySubject(String materia){
        entrys.clear();
        materia = materia.toLowerCase();

        for(int i=0;i<entrysAll.size();i++){
            if(entrysAll.get(i).getNomeMateria().toLowerCase().contains(materia))
                entrys.add(entrysAll.get(i));
        }
        fireTableDataChanged();

    }

    /**
     * Metodo che filtra la tabella sia per nome che per materia
     * @param name Nome e cognome dello studente
     * @param subject Materia
     */
    public void filterTableByName_Subject(String name, String subject){
        entrys.clear();
        name = name.toLowerCase();
        String [] names = name.split(" ", 2);
        subject = subject.toLowerCase();

        //Controllo se in names ci sono almeno due elementi, se no vuol dire che l'utente ha digitato una sola parola
        if(names.length>1){
            for(int i=0;i<entrysAll.size();i++){
                if((entrysAll.get(i).getNome().toLowerCase().contains(names[0]) && entrysAll.get(i).getCognome().toLowerCase().contains(names[1]))
                        && entrysAll.get(i).getNomeMateria().toLowerCase().contains(subject))
                    entrys.add(entrysAll.get(i));
            }
        }
        else{
            for(int i=0;i<entrysAll.size();i++){
                if(((entrysAll.get(i).getNome().toLowerCase().contains(name) || entrysAll.get(i).getCognome().toLowerCase().contains(name)))
                        && entrysAll.get(i).getNomeMateria().toLowerCase().contains(subject))
                    entrys.add(entrysAll.get(i));
            }
        }
        fireTableDataChanged();
    }

    /**
     * Metodo che rimuove i filtri dalla tabella
     */
    public void removeFilter(){
        entrys = (ArrayList) entrysAll.clone();
        fireTableDataChanged();
    }

    /**
     * Restituisce un ArrayList contenente solo una volta tutti i nomi degli studenti/insegnamenti presenti in tabella
     * @param si Per distinguere la presa dei nomi degli studenti o degli insegnamenti
     * @return Un ArrayList per facilitare la presa di ogni elemento all'interno
     */
    public ArrayList<String> getUniqueName(int si){
        //Utilizzo un TreeSet che faccia dei confronti case insensitive. In questo modo non ho duplicati e mi vede uguale gli elementi indipendenti dal case maiuscolo o minuscolo
        Set<String> uniqueNames = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        if(si==0)
            for(int i=0;i< entrys.size();i++)
                uniqueNames.add(entrysAll.get(i).getNome()+" "+entrys.get(i).getCognome());
        else
            for(int i=0;i< entrys.size();i++)
                uniqueNames.add(entrysAll.get(i).getNomeMateria());
        return new ArrayList<>(uniqueNames);
    }

    /**
     * Metodo che imposta il valore isDirty
     * @param val Nuovo valore di isDirty
     */
    public void setIsDirty(boolean val){
        isDirty=val;
    }

    /**
     * Metodo che restituisce il valore isDirty
     * @return true se la tabella è "sporca", cioè da salvare, false altrimenti
     */
    public boolean getIsDirty(){
        return isDirty;
    }

}
