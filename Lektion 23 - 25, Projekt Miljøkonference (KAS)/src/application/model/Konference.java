package application.model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Denne klasse modellerer en konference.
 *
 * @author Louise van de Weerd
 *
 */
public class Konference {

    private String navn;
    private LocalDate startDato;
    private LocalDate slutDato;
    private String by;
    private String adresse;
    private double dagsPris;
    private Hotel hotel;
    private Udflugt udflugt;
    // linker til tilmeldingsklassen (--> 0..*)
    private ArrayList<Tilmelding> tilmeldinger;
    // linker til hotelklassen (--> 0..*)
    private ArrayList<Hotel> hoteller;
    // linker til udflugtsklassen (--> 0..*)
    private ArrayList<Udflugt> udflugter;

    /**
     * Initialiserer en konference.
     *
     * @param navn
     *            navnet på konferencen
     * @param startDato
     *            start datoen for afholdelsen af konferencen
     * @param slutDato
     *            datoen for konferencens afslutning.
     * @param by
     *            byen konferencen bliver afholdt i
     * @param adresse
     *            adressen konferencen bliver afholdt på
     * @param pris
     *            prisen for at deltage i konferencen
     */
    public Konference(String navn, LocalDate startDato, LocalDate slutDato, String by, String adresse,
            double dagsPris) {
        this.navn = navn;
        this.startDato = startDato;
        this.slutDato = slutDato;
        this.by = by;
        this.adresse = adresse;
        this.dagsPris = dagsPris;
        this.tilmeldinger = new ArrayList<>();
        this.hoteller = new ArrayList<>();
        this.udflugter = new ArrayList<>();
    }

    /**
     * Returnerer navnet på konferencen.
     *
     * @return navn
     */
    public String getNavn() {
        return navn;
    }

    /**
     * Registrerer et navn på en konference, f.eks. ændres navnet hvis det
     * forhenværende navn var misvisende eller ikke dækkede omfanget af konferencen.
     *
     * @param navn
     *            det nye navn til konferencen
     */
    public void setNavn(String navn) {
        this.navn = navn;
    }

    /**
     * Returnerer start datoen for konferencens afholdelse.
     *
     * @return startDato
     */
    public LocalDate getStartDato() {
        return startDato;
    }

    /**
     * Registrerer start datoen for konferencens afholdelse.
     *
     * @param startDato
     *            den opdaterede dato for konferencens begyndelse
     */
    public void setStartDato(LocalDate startDato) {
        this.startDato = startDato;
    }

    /**
     * Returnerer slutdatoen for koferencen.
     *
     * @return slutDato
     */
    public LocalDate getSlutDato() {
        return slutDato;
    }

    /**
     * Registrerer slutdatoen for konferencen.
     *
     * @param slutDato
     *            den opdaterede dato for konferencens afslutning
     */
    public void setSlutDato(LocalDate slutDato) {
        this.slutDato = slutDato;
    }

    /**
     * Returnerer byen, konferencen afholdes i.
     *
     * @return by
     */
    public String getBy() {
        return by;
    }

    /**
     * Registrerer den by, konferencen skal afholdes i.
     *
     * @param by
     *            den opdaterede by som konferencen afholdes i
     */
    public void setBy(String by) {
        this.by = by;
    }

    /**
     * Returnerer adressen for konferencens afholdelse.
     *
     * @return adresse
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Registrerer adressen, hvor konferencen afholdes.
     *
     * @param adresse
     *            den nye adresse som konferencen afholdes på
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * Returnerer gebyrprisen for deltagelse til konferencen.
     *
     * @return pris
     */
    public double getDagsPris() {
        return dagsPris;
    }

    /**
     * Registrerer prisen for gebyret som skal betales for at deltage i konferencen.
     *
     * @param pris
     *            prisen for at deltage i konferencen pr. dag
     */
    public void setDagsPris(double dagsPris) {
        this.dagsPris = dagsPris;
    }

    /**
     * Returnerer et hotel.
     *
     * @return hotel
     */
    public Hotel getHotel() {
        return hotel;
    }

    /**
     * Registrerer et hotel.
     *
     * @param hotel
     *            hotellet som registreres.
     */
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    /**
     * Returnerer en udflugt.
     *
     * @return udflugt
     */
    public Udflugt getUdflugt() {
        return udflugt;
    }

    /**
     * Registrerer en udflugt.
     *
     * @param udflugt
     *            den udflugt som registreres.
     */
    public void setUdflugt(Udflugt udflugt) {
        this.udflugt = udflugt;
    }

    /**
     * ToString metode.
     *
     * @return navn
     * @retun by
     */
    @Override
    public String toString() {
        return navn + ", " + by;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Opretter en ny tilmelding til konferencen.
     *
     * @param tilmelding
     *            den tilmelding som skal tilføjes
     */
    public Tilmelding createTilmelding(Konference konference, Deltager deltager, boolean foredragsholder,
            boolean påHotel, LocalDate ankomstDato, LocalDate afrejseDato) {
        Tilmelding tilmelding = new Tilmelding(konference, deltager, foredragsholder, ankomstDato, afrejseDato,
                påHotel);
        tilmeldinger.add(tilmelding);
        tilmelding.setKonference(this);
        return tilmelding;
    }

    /**
     * Tilføjer en tilmelding til listen over tilmledinger.
     *
     * @param tilmelding
     *            tilmeldingen så skal tilføjes
     */
    public void addTilmelding(Tilmelding tilmelding) {
        tilmeldinger.add(tilmelding);
    }

    /**
     * Fjerner en tilmelding til konferencen.
     *
     * @param tilmelding
     *            den tilmelding som skal fjernes fra listen over tilmeldte til
     *            denne konference
     */
    public void removeTilmelding(Tilmelding tilmelding) {
        tilmeldinger.remove(tilmelding);
        tilmelding.setKonference(null);
    }

    /**
     * Returnerer en liste af tilmeldinger for denne konference.
     *
     * @return tilmeldinger listen med tilmeldte til denne konference.
     */
    public ArrayList<Tilmelding> getTilmeldinger() {
        return new ArrayList<>(tilmeldinger);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Tilknytter et hotel til konferencen.
     *
     * @param hotel
     *            det hotel som skal tilknyttes denne konference
     */
    public void addHotel(Hotel hotel) {
        hoteller.add(hotel);
        hotel.setKonference(this);
    }

    /**
     * Frakobler et hotel fra en konference.
     *
     * @param hotel
     *            det hotel som skal frakobles denne konference
     */
    public void removeHotel(Hotel hotel) {
        hoteller.remove(hotel);
        hotel.setKonference(null);
    }

    /**
     * Returnerer en liste over hoteller tilknyttet denne konference.
     *
     * @return hoteller listen med hoteller tilknyttet denne konference.
     */
    public ArrayList<Hotel> getHoteller() {
        return new ArrayList<>(hoteller);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Tilknytter en udflugt til konferencen.
     *
     * @param udflugt
     *            den udflugt som skal tilknyttes denne konference
     */
    public void addUdflugt(Udflugt udflugt) {
        udflugter.add(udflugt);
        udflugt.setKonference(this);
    }

    /**
     * Frakobler en udflugt fra konferencen, så den ikke længere er tilgængelig for
     * denne koference.
     *
     * @param udflugt
     *            den udflugt som skal nedlægges på denne konference
     */
    public void removeUdflugt(Udflugt udflugt) {
        udflugter.remove(udflugt);
        udflugt.setKonference(null);
    }

    /**
     * Returnerer en liste over udflugter som er tilknyttet denne konference.
     *
     * @return udflugter listen over udflugter som er tilknyttet denne konference
     */
    public ArrayList<Udflugt> getUdflugter() {
        return new ArrayList<>(udflugter);
    }

    // ---------------------------------------------------------------------------------------------
}
