package application.model;

/**
 * Denne klasse modellerer et tilkøb.
 *
 * @author Louise van de Weerd
 *
 */
public class Tilkøb {

    private String navn;
    private double pris;

    /**
     * Initialiserer et tilkøb.
     *
     * @param hotel
     *            det hotel som tilbyder tilkøbet
     * @param type
     *            beskrivelsen af tilkøbet
     * @param pris
     *            prisen for tilkøbet
     */
    public Tilkøb(String type, double pris) {
        this.navn = type;
        this.pris = pris;
    }

    /**
     * Returnerer navnet på tilkøbet.
     *
     * @return navn
     */
    public String getNavn() {
        return navn;
    }

    /**
     * Registrerer et nyt tilkøb.
     *
     * @param navn
     *            navnet på det nye tilkøb.
     */
    public void setNavn(String navn) {
        this.navn = navn;
    }

    /**
     * Returnerer prisen for tilkøbet.
     *
     * @return pris
     */
    public double getPris() {
        return pris;
    }

    /**
     * Registrerer en ny pris for tilkøbet.
     *
     * @param pris
     *            den nye pris for tilkøbet
     */
    public void setPris(double pris) {
        this.pris = pris;
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
}