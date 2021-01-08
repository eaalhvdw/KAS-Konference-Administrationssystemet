package application.model;

import java.util.ArrayList;

/**
 * Denne klasse modellerer en ledsager.
 *
 * @author Louise van de Weerd
 *
 */
public class Ledsager {

    private String navn;
    private Deltager deltager;
    // linker til udflugtsklassen (--> 0..*)
    private ArrayList<Udflugt> udflugter;

    /**
     * Initialiserer en ledsager.
     *
     * @param navn
     *            ledsagerens navn
     * @param deltager
     *            den deltager, som ledsageren ledsager
     */
    public Ledsager(String navn, Deltager deltager) {
        this.navn = navn;
        this.deltager = deltager;
        this.udflugter = new ArrayList<>();
    }

    /**
     * Returnerer prisen for ledsagerens udflugter.
     *
     * @return pris
     */
    public double getPrisUdflugter() {
        double pris = 0;
        for (int i = 0; i < udflugter.size(); i++) {
            pris += udflugter.get(i).getPris();
        }
        return pris;
    }

    /**
     * Returnerer ledsagerens navn.
     *
     * @return navn
     */
    public String getNavn() {
        return navn;
    }

    /**
     * Registrerer et nyt navn til ledsageren.
     *
     * @param navn
     *            ledsagerens navn
     */
    public void setNavn(String navn) {
        this.navn = navn;
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

    // ---------------------------------------------------------------------------------------------

    /**
     * Returnerer den deltager, som ledsageren ledsager.
     *
     * @return deltager
     */
    public Deltager getDeltager() {
        return deltager;
    }

    /**
     * Registrerer den deltager, som ledsages.
     *
     * @param deltager
     *            den deltager som skal registreres
     */
    public void setDeltager(Deltager deltager) {
        this.deltager = deltager;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Tilføjer en udflugt til ledsagerens liste af udflugter.
     *
     * @param udflugt
     *            den udflugt som skal tilføjes kollektionen med udflugter
     */
    public void addUdflugt(Udflugt udflugt) {
        udflugter.add(udflugt);
    }

    /**
     * Fjerner en udflugt fra ledsagerens liste over udflugter.
     *
     * @param udflugt
     *            den udflugt som skal fjernes fra kollektionen med udflugter
     */
    public void removeUdflugt(Udflugt udflugt) {
        udflugter.remove(udflugt);
    }

    /**
     * Returnerer en liste over udflugter, som ledsageren har tilmeldt sig.
     *
     * @return udflugter listen med de udflugter som ledsageren har tilmeldt sig.
     */
    public ArrayList<Udflugt> getUdflugter() {
        return new ArrayList<>(udflugter);
    }

    // ---------------------------------------------------------------------------------------------
}
