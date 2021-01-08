package application.model;

import java.util.ArrayList;

/**
 * Denne klasse modellerer et firma.
 *
 * @author Louise van de Weerd
 *
 */
public class Firma {
    private String navn;
    private int telefonNr;

    // linker til deltagerklassen (--> 0..*)
    private ArrayList<Deltager> deltagere;

    /**
     * Initialiserer et firma.
     *
     * @param navn
     *            navnet på firmaet
     * @param telefonNr
     *            firmaets telefonnummer
     */
    public Firma(String navn, int telefonNr) {
        this.navn = navn;
        this.telefonNr = telefonNr;
        this.deltagere = new ArrayList<>();
    }

    /**
     * Returnerer navnet på firmaet.
     *
     * @return navn
     */
    public String getNavn() {
        return navn;
    }

    /**
     * Returnerer firmaets telefonnummer.
     *
     * @return telefonNr
     */
    public int getTelefonNr() {
        return telefonNr;
    }

    /**
     * Registrerer firmaets telefonnummer.
     *
     * @param telefonNr
     *            det nye telefonnummer
     */
    public void setTelefonNr(int telefonNr) {
        this.telefonNr = telefonNr;
    }

    /**
     * toString metode.
     *
     * @return navn
     * @return telefonNr
     */
    @Override
    public String toString() {
        return navn + ", " + telefonNr;
    }

    // --------------------------------------------------------------------------------------------

    /**
     * Returnerer en liste af deltagere, som er ansat i firmaet.
     *
     * @return deltagere
     */
    public ArrayList<Deltager> getDeltagere() {
        return new ArrayList<>(deltagere);
    }

    /**
     * Tilføjer en deltager til listen af deltagere tilmeldt igennem dette firma.
     *
     * @param deltager
     *            den deltager som skal tilføjes til listen
     */
    public void addDeltager(Deltager deltager) {
        deltagere.add(deltager);
    }

    /**
     * Fjerner en deltager fra listen af deltagere tilmeldt en koference gennem
     * dette firma.
     *
     * @param deltager
     *            den deltager som skal fjernes fra listen
     */
    public void removeDeltager(Deltager deltager) {
        deltagere.remove(deltager);
    }

    // ---------------------------------------------------------------------------------------------
}