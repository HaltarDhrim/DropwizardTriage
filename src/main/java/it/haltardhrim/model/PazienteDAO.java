package it.haltardhrim.model;

import java.sql.*;
import java.util.*;

/**
 * Questa classe fa parte del pattern DAO.
 * Risponde alla domanda: come gestisco un Paziente nel DB?
 * Contiene i metodi CRUD necessari per manipolare i Pazienti nel DB.
 */
public class PazienteDAO {

    private Connection conn = null;
    private PreparedStatement query = null;

    private final String CREATE_PAZIENTE =
            "INSERT INTO pazienti (id,codfisc,prioritaIniz,priorita,stato,userInsert,timeInsert,userUpdate,timeUpdate)" +
                    "VALUES (default,?,?,?,?,?,?,?,?)";
    private final String READ_PAZIENTE_NEW =
            "SELECT * FROM pazienti WHERE timeInsert = ?";
    private final String READ_PAZIENTE =
            "SELECT * FROM pazienti WHERE id = ?";
    private final String READ_PAZIENTI =
            "SELECT * FROM pazienti WHERE stato <> 4 ORDER BY priorita,timeInsert";
    private final String UPDATE_PAZIENTE =
            "UPDATE pazienti SET stato = ?, priorita = ?, userUpdate = ?, timeUpdate = ? WHERE id = ?";
    private final String DELETE_PAZIENTE =
            "DELETE FROM pazienti WHERE id = ?";
    private final String DELETE_ALL_PAZIENTI =
            "DELETE FROM pazienti";

    public Connection getConnection() {
        Connection conn = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/triage", "postgres", "postgres");
        } catch (Exception e) {
            System.err.println("Errore in fase di connessione al DB");
            e.printStackTrace();
        }

        return conn;
    }

    public boolean isResultEmpty(ResultSet result) throws SQLException{
        return (!result.isBeforeFirst() && result.getRow() == 0);
    };

    public Paziente createPaziente(Paziente paz) {
        paz.setPriorita(paz.getPrioritaIniz());
        paz.setStato(Paziente.DA_VISITARE);
        paz.setUserInsert("PazDAO");
        paz.setTimeInsert(new Timestamp(System.currentTimeMillis()));
        paz.setUserUpdate("");
        paz.setTimeUpdate(new Timestamp(0));

        try {
            conn = this.getConnection();
            query = conn.prepareStatement(CREATE_PAZIENTE);
            query.setString    (1, paz.getCodfisc());
            query.setInt       (2, paz.getPrioritaIniz());
            query.setInt       (3, paz.getPriorita());
            query.setInt       (4, paz.getStato());
            query.setString    (5, paz.getUserInsert());
            query.setTimestamp (6, paz.getTimeInsert());
            query.setString    (7, paz.getUserUpdate());
            query.setTimestamp (8, paz.getTimeUpdate());
            query.executeUpdate();
            query.close();
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore in fase di esecuzione INSERT createPaziente");
            e.printStackTrace();
        }

        try {
            conn = this.getConnection();
            query = conn.prepareStatement(READ_PAZIENTE_NEW);
            query.setTimestamp (1, paz.getTimeInsert());
            ResultSet result = query.executeQuery();

            while (result.next()) {
                if (result.getRow() == 1) {
                    paz.setId(result.getInt("id"));
                }
                else {
                    throw new SQLException();
                }
            }

            query.close();
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore in recupero dato inserito da INSERT createPaziente");
            e.printStackTrace();
        }

        return paz;
    }

    public Paziente readPaziente(int id) {
        Paziente paz = new Paziente();

        try {
            conn = this.getConnection();
            query = conn.prepareStatement(READ_PAZIENTE);
            query.setInt (1, id);
            ResultSet result = query.executeQuery();

            if (isResultEmpty(result)) {
                return null;
            }

            while (result.next()) {
                paz.setId           (result.getInt("id"));
                paz.setCodfisc      (result.getString("codfisc"));
                paz.setPrioritaIniz (result.getInt("prioritainiz"));
                paz.setPriorita     (result.getInt("priorita"));
                paz.setStato        (result.getInt("stato"));
                paz.setUserInsert   (result.getString("userinsert"));
                paz.setTimeInsert   (result.getTimestamp("timeinsert"));
                paz.setUserUpdate   (result.getString("userupdate"));
                paz.setTimeUpdate   (result.getTimestamp("timeupdate"));
            }

            query.close();
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore in fase di esecuzione SELECT readPaziente");
            e.printStackTrace();
        }

        return paz;
    }

    public ArrayList<Paziente> readPazientiInCoda() {
        ArrayList<Paziente> list = new ArrayList<Paziente>();

        try {
            conn = this.getConnection();
            query = conn.prepareStatement(READ_PAZIENTI);
            ResultSet result = query.executeQuery();

            while (result.next()) {
                Paziente paz      = new Paziente();
                paz.setId           (result.getInt("id"));
                paz.setCodfisc      (result.getString("codfisc"));
                paz.setPrioritaIniz (result.getInt("prioritainiz"));
                paz.setPriorita     (result.getInt("priorita"));
                paz.setStato        (result.getInt("stato"));
                paz.setUserInsert   (result.getString("userinsert"));
                paz.setTimeInsert   (result.getTimestamp("timeinsert"));
                paz.setUserUpdate   (result.getString("userupdate"));
                paz.setTimeUpdate   (result.getTimestamp("timeupdate"));
                list.add(paz);
            }

            query.close();
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore in fase di esecuzione SELECT readPazientiInCoda");
            e.printStackTrace();
        }

        return list;
    }

    public Paziente updatePaziente(Paziente p, int id) {
        p.setUserUpdate("PazDAO");
        p.setTimeUpdate(new Timestamp(System.currentTimeMillis()));

        try {
            conn = this.getConnection();
            query = conn.prepareStatement(UPDATE_PAZIENTE);
            query.setInt       (1, p.getStato());
            query.setInt       (2, p.getPriorita());
            query.setString    (3, p.getUserUpdate());
            query.setTimestamp (4, p.getTimeUpdate());
            query.setInt       (5, id);
            query.executeUpdate();
            query.close();
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore in fase di esecuzione UPDATE updatePazienteStato");
            e.printStackTrace();
        }

        p = readPaziente(id);
        return p;
    }

    public void deletePaziente(int id) {
        try {
            conn = this.getConnection();
            query = conn.prepareStatement(DELETE_PAZIENTE);
            query.setInt       (1, id);
            query.executeUpdate();
            query.close();
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore in fase di esecuzione DELETE deletePaziente");
            e.printStackTrace();
        }
    }

    public void deleteAllPazienti() {
        try {
            conn = this.getConnection();
            query = conn.prepareStatement(DELETE_ALL_PAZIENTI);
            query.executeUpdate();
            query.close();
            conn.close();
        } catch (SQLException e) {
            System.err.println("Errore in fase di esecuzione DELETE deleteAllPazienti");
            e.printStackTrace();
        }
    }
}
