package application.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * Denne klasse modellerer en tilmelding til en konference.
 *
 * @author Louise van de Weerd
 *
 */
public class Tilmelding {
    private boolean foredragsholder;
    private boolean påHotel;
    private LocalDate ankomstDato;
    private LocalDate afrejseDato;

    // linker til koferenceklassen (--> 1)
    private Konference konference;
    // linker til deltagerklassen (--> 1)
    private Deltager deltager;
    // linker til hotelklassen (--> 0..1)
    private Hotel hotel;
    // linker til tilkøbsklassen (--> 0..*)
    private ArrayList<Tilkøb> tilkøB;

    /**
     * Initialiserer en tilmelding til en konference.
     *
     * @param konference
     *            de konference som deltageren tilmelder sig
     * @param deltager
     *            deltageren som tilmeldes
     * @param foredragsholder
     *            tjekker om deltageren er foredragsholder
     * @param påHotel
     *            fortæller om der i tilmeldingen er valgt at bo på hotel.
     * @param ankomstdato
     *            datoen for deltagerens ankomst til konferencen
     * @param afrejsedato
     *            datoen for deltagerens afrejse fra konferencen
     */
    Tilmelding(Konference konference, Deltager deltager, boolean foredragsholder, LocalDate ankomstDato,
            LocalDate afrejseDato, boolean påHotel) {
        this.konference = konference;
        this.deltager = deltager;
        this.foredragsholder = foredragsholder;
        this.påHotel = påHotel;
        this.ankomstDato = ankomstDato;
        this.afrejseDato = afrejseDato;
        this.tilkøB = new ArrayList<>();
    }

    /**
     * Returnerer antallet af dage en tilmeldt tilbringer på konferencen.
     *
     * @return dage
     */
    public int getDage() {
        return (int) ChronoUnit.DAYS.between(ankomstDato, afrejseDato);
    }

    /**
     * Returnerer om den tilmedte er foredragsholder.
     *
     * @return foredragsholder
     */
    public boolean getForedragsholder() {
        return foredragsholder;
    }

    /**
     * Registrerer om den tilmeldte er foredragsholder.
     *
     * @param foredragsholder
     *            ændrer deltagerens rolle ved konferencen
     */
    public void setForedragsholder(boolean foredragsholder) {
        this.foredragsholder = foredragsholder;
    }

    /**
     * Returnerer hvorvidt den tilmeldte ønsker at indlogere sig på hotel.
     */
    public boolean isPåHotel() {
        return påHotel;
    }

    /**
     * Registrerer om den tilmeldte vil indlogeres påå hotel eller ej.
     *
     * @param påHotel
     *
     */
    public void setPåHotel(boolean påHotel) {
        this.påHotel = påHotel;
    }

    /**
     * Returnerer datoen for deltagerens ankomst.
     *
     * @return ankomstDato
     */
    public LocalDate getAnkomstDato() {
        return ankomstDato;
    }

    /**
     * Registrer datoen for deltagerens ankomst.
     *
     * @param ankomstDato
     *            den opdaterede dato for deltagerens ankomst
     */
    public void setAnkomstDato(LocalDate ankomstDato) {
        this.ankomstDato = ankomstDato;
    }

    /**
     * Returnerer datoen for deltagerens afrejse.
     *
     * @return afrejseDato
     */
    public LocalDate getAfrejseDato() {
        return afrejseDato;
    }

    /**
     * Registrerer datoen for deltagerens afrejse.
     *
     * @param afrejseDato
     *            den opdaterede dato for deltagerens afrejse
     */
    public void setAfrejseDato(LocalDate afrejseDato) {
        this.afrejseDato = afrejseDato;
    }

    /**
     * toString metode.
     *
     * @return deltager
     * @return konference
     */
    @Override
    public String toString() {
        return deltager + ", " + konference;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returnerer den konference som tilmeldingen går til.
     *
     * @return konference
     */
    public Konference getKonference() {
        return konference;
    }

    /**
     * Registrerer hvilken konference som tilmeldingen går til.
     *
     * @param konference
     *            omregistrering i systemet flytter tilmledingen fra en konferencen
     *            til en anden
     */
    public void setKonference(Konference konference) {
        this.konference = konference;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returnerer den deltager, som skal tilmeldes.
     *
     * @return deltager
     */
    public Deltager getDeltager() {
        return deltager;
    }

    /**
     * Registrerer en deltager til en tilmelding.
     *
     * @param deltager
     *            den deltager som skal tilmeldes
     */
    public void setDeltager(Deltager deltager) {
        this.deltager = deltager;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Returnerer det hotel, som tilmeldingen indlogeres på.
     *
     * @return hotel
     */
    public Hotel getHotel() {
        return hotel;
    }

    /**
     * Registrerer et hotel til en tilmelding.
     *
     * @param hotel
     *            det hotel, som deltageren har reserveret
     */
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
    // ---------------------------------------------------------------------------------------------

    /**
     * Tilføjer et tilkøb af en service til et hotel.
     *
     * @param tilkøb
     *            det tilkøb som en deltager har valgt at tilføje til sit værelse på
     *            hotellet
     */
    public void addTilkøb(Tilkøb tilkøb) {
        tilkøB.add(tilkøb);
    }

    /**
     * Fjerner et tilkøb af en service, som er udgået på et hotel.
     *
     * @param tilkøb
     *            det tilkøb som en deltager har ombestemt sig for og derfor
     *            fravælger sit værelse
     */
    public void removeTilkøb(Tilkøb tilkøb) {
        tilkøB.remove(tilkøb);
    }

    /**
     * Returnerer en liste over tilkøb af services på et hotel.
     *
     * @return tilkøb listen med tilkøb til et hotelværelse for denne tilmelding.
     */
    public ArrayList<Tilkøb> getTilkøb() {
        return new ArrayList<>(tilkøB);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Beregner den samlede pris for tilmeldingen til konferencen, hotellet,
     * udflugter mv.
     *
     * @return sum
     *
     */
    public double samletPris() {
        double sum = 0;
        double konferencePris = 0;
        konferencePris = konference.getDagsPris() * (getDage() + 1);

        if (isPåHotel() == false && deltager.getLedsager() == null && foredragsholder == false) {
            sum = konferencePris;
        } else if (isPåHotel() == true && deltager.getLedsager() == null && foredragsholder == false) {
            sum = konferencePris + hotel.værelsesPris(false) * getDage();
        } else if (isPåHotel() == true && deltager.getLedsager() != null && foredragsholder == false) {
            sum = konferencePris + hotel.værelsesPris(true) * getDage() + deltager.getLedsager().getPrisUdflugter();
        } else if (isPåHotel() == true && deltager.getLedsager() == null && foredragsholder == true) {
            sum = hotel.værelsesPris(false) * getDage();
        } else if (isPåHotel() == true && deltager.getLedsager() != null && foredragsholder == true) {
            sum = hotel.værelsesPris(true) * getDage() + deltager.getLedsager().getPrisUdflugter();
        } else if (isPåHotel() == false && deltager.getLedsager() != null && foredragsholder == false) {
            sum = konferencePris + deltager.getLedsager().getPrisUdflugter();
        } else if (isPåHotel() == false && deltager.getLedsager() != null && foredragsholder == true) {
            sum = deltager.getLedsager().getPrisUdflugter();
        } else if (isPåHotel() == false && deltager.getLedsager() == null && foredragsholder == true) {
            sum = 0;
        }

        for (int i = 0; i < tilkøB.size(); i++) {
            sum += tilkøB.get(i).getPris() * getDage();
        }
        return sum;
    }

    // ---------------------------------------------------------------------------------------------
}
