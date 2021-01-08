package application.model;

import java.util.ArrayList;

/**
 * Denne klasse modellerer en deltager.
 *
 * @author Louise van de Weerd
 *
 */
public class Deltager {

    private String navn;
    private String adresse;
    private String by;
    private String land;
    private int telefonNr;

    // linker til firmaklassen (--> 0..1)
    private Firma firma;
    // linker til ledsagerklassen (--> 0..1)
    private Ledsager ledsager;
    // linker til tilmeldingsklassen (--> 0..*)
    private ArrayList<Tilmelding> tilmeldinger;

    /**
     * Initialiserer en deltager.
     *
     * @param navn
     *            navnet på deltageren
     * @param adresse
     *            deltagerens adresse
     * @param by
     *            byen deltageren bor i
     *
     * @param land
     *            landet deltageren kommer fra
     *
     * @param telefonNr
     *            deltagerens telefonnummer
     * @param tilmeldinger
     *            alle tilmeldinger deltageren har lavet til forskellige
     *            konferencer.
     */
    public Deltager(String navn, String adresse, String by, String land, int telefonNr) {
        this.navn = navn;
        this.adresse = adresse;
        this.by = by;
        this.land = land;
        this.telefonNr = telefonNr;
        this.tilmeldinger = new ArrayList<>();
    }

    /**
     * Returnerer navnet på deltageren.
     *
     * @return navn
     */
    public String getNavn() {
        return navn;
    }

    /**
     * Registrerer et nyt navn til en deltager.
     *
     * @param navn
     *            det nye navn på deltageren
     */
    public void setNavn(String navn) {
        this.navn = navn;
    }

    /**
     * Returnerer deltagerens adresse.
     *
     * @return adresse
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Registrerer deltagerens adresse.
     *
     * @param adresse
     *            den adresse, deltageren er flyttet til
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * Returnrerer byen deltageren er fra.
     *
     * @return by
     */
    public String getBy() {
        return by;
    }

    /**
     * Registrerer byen deltageren bor i.
     *
     * @param by,
     *            den by som deltageren er flyttet til
     */
    public void setBy(String by) {
        this.by = by;
    }

    /**
     * Returnerer landet deltageren bor i.
     *
     * @return land
     */
    public String getLand() {
        return land;
    }

    /**
     * Registrerer det land, deltageren er flyttet til.
     *
     * @param land
     *            landet deltageren er flyttet til
     */
    public void setLand(String land) {
        this.land = land;
    }

    /**
     * Returnerer deltagerens telefonummer.
     *
     * @return telefonNr
     */
    public int getTelefonNr() {
        return telefonNr;
    }

    /**
     * Registrerer deltagerens evt. nye telefonnummer.
     *
     * @param telefonNr
     *            deltagerens nye telefonnummer
     */
    public void setTelefonNr(int telefonNr) {
        this.telefonNr = telefonNr;
    }

    /**
     * toString metode.
     *
     * @return navn
     */
    @Override
    public String toString() {
        return navn;
    }

    // --------------------------------------------------------------------------------------------

    /**
     * Returnerer det firma, deltageren er ansat i.
     *
     * @return firma
     */
    public Firma getFirma() {
        return firma;
    }

    /**
     * Registrerer et firma.
     *
     * @param firma
     *            det firma som deltageren er ansat i
     */
    public void setFirma(Firma firma) {
        this.firma = firma;
    }

    // --------------------------------------------------------------------------------------------

    /**
     * Returnerer deltagerens ledsager.
     *
     * @return ledsager
     */
    public Ledsager getLedsager() {
        return ledsager;
    }

    /**
     * Registrerer en ledsager til en deltager.
     *
     * @param ledsager
     *            den ledsager som deltageren medbringer til denne konference
     */
    public void setLedsager(Ledsager ledsager) {
        this.ledsager = ledsager;
    }

    // --------------------------------------------------------------------------------------------

    /**
     * Returnerer en liste med tilmeldinger for en deltager til forskellige
     * konferencer.
     *
     * @return tilmeldinger
     */
    public ArrayList<Tilmelding> getTilmeldinger() {
        return new ArrayList<>(tilmeldinger);
    }

    /**
     * Tilføjer en ny tilmelding til deltagerens liste over tilmeldinger til
     * forskellige konferencer.
     *
     * @param tilmelding
     *            tilmeldingen som skal tilføjes til listen
     */
    public void addTilmelding(Tilmelding tilmelding) {
        tilmeldinger.add(tilmelding);
        tilmelding.setDeltager(this);
    }

    /**
     * Fjerner en tilmelding til en konference fra en deltagers liste.
     *
     * @param tilmelding
     *            den tilmelding som skal fjernes fra listen
     */
    public void removeTilmelding(Tilmelding tilmelding) {
        tilmeldinger.remove(tilmelding);
        tilmelding.setDeltager(null);
    }

    // ---------------------------------------------------------------------------------------------
}
