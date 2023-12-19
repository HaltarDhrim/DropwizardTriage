package it.haltardhrim.model;

import java.util.*;

/**
 * Questa classe fa parte del pattern DAO.
 * Risponde alla domanda: come lancio i comandi del DAO come utente?
 * L'utente manda dei comandi, che qui vengono gestiti richiamando i metodi DAO.
 */
public class PazienteService {

    private PazienteDAO pazDAO;

    public PazienteService() {
        pazDAO = new PazienteDAO();
    }

    public Paziente accogliPaziente(Paziente paz) {
        paz = pazDAO.createPaziente(paz);
        return paz;
    }

    public Paziente getPaziente(int id) {
        return pazDAO.readPaziente(id);
    }

    public ArrayList<Paziente> getCodaPazienti() {
        return pazDAO.readPazientiInCoda();
    }

    public Paziente aggiornaPaziente(Paziente p, int id) {
        return pazDAO.updatePaziente(p, id);
    }

    public void eliminaPaziente(int id) {
        pazDAO.deletePaziente(id);
    }

    public void resetPazienti() {
        pazDAO.deleteAllPazienti();
    }
}
