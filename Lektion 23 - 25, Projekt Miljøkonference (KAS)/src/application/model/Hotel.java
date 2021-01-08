package application.model;

import java.util.ArrayList;

/**
 * Denne klasse modellerer et hotel.
 *
 * @author Louise van de Weerd
 *
 */
public class Hotel {

    private String navn;
    private String adresse;
    private String by;
    private double enkeltPris;
    private double dobbeltPris;
    private Konference konference;
    // linker til tilmeldingsklassen (--> 0..*)
    private ArrayList<Tilmelding> tilmeldinger;
    // linker til tilkøbsklassen (--> 0..*)
    private ArrayList<Tilkøb> tilkøB;

    /**
     * Initialiserer et hotel.
     *
     * @param navn
     *            navnet på hotellet
     * @param adresse
     *            hotellets adresse
     * @param by
     *            byen hvori hotellet ligger
     * @param enkeltPris
     *            hotellets pris for et enkelt-værelse
     * @param dobbeltPris
     *            hotellets pris for et dobbeltværelse
     * @param konference
     *            konferencen som tilknyttes hotellet.
     */
    public Hotel(String navn, String adresse, String by, double enkeltPris, double dobbeltPris, Konference konference) {
        this.navn = navn;
        this.adresse = adresse;
        this.by = by;
        this.enkeltPris = enkeltPris;
        this.dobbeltPris = dobbeltPris;
        this.konference = konference;
        this.tilmeldinger = new ArrayList<>();
        this.tilkøB = new ArrayList<>();
    }

    /**
     * Beregner prisen for hotelværelset.
     *
     * @param dobbeltVærelse
     *            boolean som bestemmer om det er et enkeltværelse eller et
     *            dobbeltværelse
     */
    public double værelsesPris(boolean dobbeltVærelse) {
        double pris = enkeltPris;

        if (dobbeltVærelse) {
            pris = dobbeltPris;
        }

        return pris;
    }

    /**
     * Returnerer navnet på hotellet.
     *
     * @return navn
     */
    public String getNavn() {
        return navn;
    }

    /**
     * Returnerer hotellets adresse.
     *
     * @return adresse
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Returnerer byen som hotellet ligger i.
     *
     * @return by
     */
    public String getBy() {
        return by;
    }

    /**
     * Returnerer prisen for et enkelt-værelse.
     *
     * @return enkeltPris
     */
    public double getEnkeltPris() {
        return enkeltPris;
    }

    /**
     * Registrerer prisen for et enkelt-værelse.
     *
     * @param enkeltPris
     *            den nye pris for et enkeltværelse
     */
    public void setEnkeltPris(double enkeltPris) {
        this.enkeltPris = enkeltPris;
    }

    /**
     * Returnerer prisen for et dobbeltværelse.
     *
     * @return dobbeltPris
     */
    public double getDobbeltPris() {
        return dobbeltPris;
    }

    /**
     * Registrerer prisen for et dobbeltværelse.
     *
     * @param dobbeltPris
     *            den nye pris for et dobbeltværelse
     */
    public void setDobbeltPris(double dobbeltPris) {
        this.dobbeltPris = dobbeltPris;
    }

    /**
     * Returnerer den konference, som hotellet udbydes til.
     *
     * @return konference
     */
    public Konference getKonference() {
        return konference;
    }

    /**
     * Registrere en konference som hotellet knyttes til.
     *
     * @param Konference
     */
    public void setKonference(Konference konference) {
        this.konference = konference;
    }

    /**
     * ToString metode.
     *
     * @return navn
     * @return by
     */
    @Override
    public String toString() {
        return navn + ", " + by;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returnerer konferencetilmeldte gæster på hotellet.
     *
     * @return tilmeldinger listen over konferencedeltagende gæster
     */
    public ArrayList<Tilmelding> getTilmeldinger() {
        return new ArrayList<>(tilmeldinger);
    }

    /**
     * Tilføjer konferencetilmeldt gæst på hotellet.
     *
     * @param tilmelding
     *            den tilmelding som skal tilføjes til listen over overnattende
     *            konferencedeltagere
     */
    public void addTilmelding(Tilmelding tilmelding) {
        tilmeldinger.add(tilmelding);
        tilmelding.setHotel(this);
    }

    /**
     * Fjerner gæst tilmeldt konferencen fra hotellet.
     *
     * @param tilmelding
     *            den tilmelding som skal fjernes fra listen over overnattende
     *            konferencedeltagere
     */
    public void removeTilmelding(Tilmelding tilmelding) {
        tilmeldinger.remove(tilmelding);
        tilmelding.setHotel(null);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returnerer en liste over tilkøb af servicess udbudt af hotellet.
     *
     * @return tilkøb listen over tilkøb som bliver udbudt på dette hotel
     */
    public ArrayList<Tilkøb> getTilkøb() {
        return new ArrayList<>(tilkøB);
    }

    /**
     * Tilføjer en ny service som kan tilkøbes på hotellet.
     *
     * @param tilkøb
     *            det tilkøb som skal tilføjes til listen over udbudte tilkøb
     */
    public void addTilkøb(Tilkøb tilkøb) {
        tilkøB.add(tilkøb);

    }

    /**
     * Fjerner et tilkøb fra listen af udbudte tilkøb.
     *
     * @param tilkøb
     *            det tilkøb som skal fjernes fra listen over udbudte tilkøb
     */
    public void removeTilkøb(Tilkøb tilkøb) {
        tilkøB.remove(tilkøb);
    }

    // --------------------------------------------------------------------------------------------
}