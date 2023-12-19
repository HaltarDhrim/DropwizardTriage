package it.haltardhrim.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;

/**
 * Questa classe fa parte del pattern DAO.
 * Risponde alla domanda: com'è fatto un Paziente nel DB?
 * Indica i dati che contiene, e descrive i metodi per aggiornarli in Java.
 */
public class Paziente {
    public final String[] prioritaEnum = { "Rosso", "Giallo", "Verde", "Bianco" };
    public static final int ROSSO = 0;
    public static final int GIALLO = 1;
    public static final int VERDE = 2;
    public static final int BIANCO = 3;

    public final String[] statoEnum = { "Da Visitare", "In Visita", "In Intervento", "In Osservazione", "Evaso" };
    public static final int DA_VISITARE = 0;
    public static final int IN_VISITA = 1;
    public static final int IN_INTERVENTO = 2;
    public static final int IN_OSSERVAZIONE = 3;
    public static final int EVASO = 4;

    private Integer id;
    private String codfisc;
    private Integer prioritaIniz; // 1=Rosso, 2=Giallo, 3=Verde, 4=Bianco
    private Integer priorita; // 1=Rosso, 2=Giallo, 3=Verde, 4=Bianco
    private Integer stato; // 1=Da visitare, 2=In visita, 3=In intervento, 4=In osservazione, 5=Evaso
    private String userInsert;
    private Timestamp timeInsert;
    private String userUpdate;
    private Timestamp timeUpdate;

    public Paziente() {
    }

    public Paziente(String codfisc, int priorita) {
        setCodfisc(codfisc);
        setPrioritaIniz(priorita);
        setPriorita(priorita);
    }

    @JsonProperty
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty
    public String getCodfisc() {
        return codfisc;
    }

    public void setCodfisc(String codfisc) {
        this.codfisc = codfisc;
    }

    @JsonProperty
    public Integer getPrioritaIniz() {
        return prioritaIniz;
    }

    public void setPrioritaIniz(Integer prioritaIniz) {
        this.prioritaIniz = prioritaIniz;
    }

    @JsonProperty
    public Integer getPriorita() {
        return priorita;
    }

    public void setPriorita(Integer priorita) {
        this.priorita = priorita;
    }

    @JsonProperty
    public Integer getStato() {
        return stato;
    }

    public void setStato(Integer stato) {
        this.stato = stato;
    }

    @JsonProperty
    public String getUserInsert() {
        return userInsert;
    }

    public void setUserInsert(String userInsert) {
        this.userInsert = userInsert;
    }

    @JsonProperty
    public Timestamp getTimeInsert() {
        return timeInsert;
    }

    public void setTimeInsert(Timestamp timeInsert) {
        this.timeInsert = timeInsert;
    }

    @JsonProperty
    public String getUserUpdate() {
        return userUpdate;
    }

    public void setUserUpdate(String userUpdate) {
        this.userUpdate = userUpdate;
    }

    @JsonProperty
    public Timestamp getTimeUpdate() {
        return timeUpdate;
    }

    public void setTimeUpdate(Timestamp timeUpdate) {
        this.timeUpdate = timeUpdate;
    }
}
