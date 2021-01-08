package application.model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Denne klasse moddellerer en udflugt.
 *
 * @author Louise van de Weerd
 *
 */
public class Udflugt {

    private String navn;
    private LocalDate dato;
    private String adresse;
    private String by;
    private double pris;
    private boolean frokost;
    private Konference konference;
    // linker til ledsagerklassen (--> 0..*)
    private ArrayList<Ledsager> ledsagere;

    /**
     * Initialiserer en udflugt.
     *
     * @param navn
     *            navnet på udflugten
     * @param dato
     *            datoen udflugten sker
     * @param adresse
     *            adressen udflugter sker på
     * @param by
     *            byen udflugten foregår i
     * @param pris
     *            prisen for at deltage i udflugten
     * @param frokost
     *            udflugt inklusiv frokost?
     * @param konference
     *            konferencen som udflugten er tilknyttet
     */
    public Udflugt(String navn, LocalDate dato, String adresse, String by, double pris, boolean frokost,
            Konference konference) {
        this.navn = navn;
        this.dato = dato;
        this.adresse = adresse;
        this.by = by;
        this.pris = pris;
        this.frokost = frokost;
        this.konference = konference;
        this.ledsagere = new ArrayList<>();
    }

    /**
     * Returnerer navnet på udflugten.
     *
     * @return navn
     */
    public String getNavn() {
        return navn;
    }

    /**
     * Registrerer et nyt navn til en udflugt.
     *
     * @param navn
     *            det nye navn til udflugten
     */
    public void setNavn(String navn) {
        this.navn = navn;
    }

    /**
     * Returnerer datoen for udflugten.
     *
     * @return dato
     */
    public LocalDate getDato() {
        return dato;
    }

    /**
     * Registrerer en ny dato til udflugten.
     *
     * @param dato
     *            den nye dato for udflugtens udspring
     */
    public void setDato(LocalDate dato) {
        this.dato = dato;
    }

    /**
     * Returnerer adressen hvor udflygten bliver afholdt.
     *
     * @return adresse
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Registrerer en ny adresse til udflugten.
     *
     * @param adresse
     *            den nye adresse hvor udflugten holder til eller starter fra
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * Returnerer byen for afholdelsen af udflugten.
     *
     * @return by
     */
    public String getBy() {
        return by;
    }

    /**
     * Registrerer en ny by til udflugtens afholdelse.
     *
     * @param by
     *            den by som udflugten holdes i
     */
    public void setBy(String by) {
        this.by = by;
    }

    /**
     * Returnerer prisen for at deltage i udflugten.
     *
     * @return pris
     */
    public double getPris() {
        return pris;
    }

    /**
     * Registrerer en ny pris for at deltage i udflugten.
     *
     * @param pris
     *            prisen for deltagelse i udflugten
     */
    public void setPris(double pris) {
        this.pris = pris;
    }

    /**
     * Returnerer om udflugten er inklusive frokost.
     *
     * @return frokost
     */
    public boolean getFrokost() {
        return frokost;
    }

    /**
     * Registrerer om udflugten er inklusive frokost.
     *
     * @param frokost
     *            den frokost som er/ikke er inkluderet i udflugten
     */
    public void setFrokost(boolean frokost) {
        this.frokost = frokost;
    }

    /**
     * Returnerer den konference som hotellet er tilknyttet.
     *
     * @return konference
     */
    public Konference getKonference() {
        return konference;
    }

    /**
     * Registrerer konferencen som udflugten tilknyttes
     *
     * @param konference
     *            den konference som udflugten tilknyttes
     */
    public void setKonference(Konference konference) {
        this.konference = konference;
    }

    /**
     * toString metode.
     *
     * @return navn
     * @return pris
     */
    @Override
    public String toString() {
        return navn + ", " + pris;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Tilføjer en deltager til udflugten.
     *
     * @param ledsager
     *            den ledsager som skal tilføjes til listen over deltagere i
     *            udflugten
     */
    public void addLedsager(Ledsager ledsager) {
        ledsagere.add(ledsager);
    }

    /**
     * Fjerner en deltager fra udflugten.
     *
     * @param ledsager
     *            den ledsager som alligevel ikke kan deltage i udflugten
     */
    public void removeLedsager(Ledsager ledsager) {
        ledsagere.remove(ledsager);
    }

    /**
     * Returnerer listen over deltagere til udflugten.
     *
     * @return ledsagere listen over ledsagere som er tilmeldt denne udflugt.
     */
    public ArrayList<Ledsager> getLedsagere() {
        return new ArrayList<>(ledsagere);
    }

    // ---------------------------------------------------------------------------------------------
}