package application.service;

import java.time.LocalDate;
import java.util.ArrayList;
import application.model.Deltager;
import application.model.Firma;
import application.model.Hotel;
import application.model.Konference;
import application.model.Ledsager;
import application.model.Tilkøb;
import application.model.Tilmelding;
import application.model.Udflugt;
import storage.Storage;

public class Service {

    /**
     * Opretter en ny konference. Kræver: pris >= 0.
     *
     * @param navn
     *            navnet på konferencen
     * @param startDato
     *            datoen for konferencens start
     * @param slutDato
     *            datoen for konferencens slutning
     * @param by
     *            den by konferencen bliver afholdt i
     * @param adresse
     *            adressen hvor koferencen bliver afholdt
     * @param dagsPris
     *            konferencegebyret pr. dag for at deltage i konferencen
     * @return konference den konference, som lige er oprettet.
     */
    public static Konference createKonference(String navn, LocalDate startDato, LocalDate slutDato, String by,
            String adresse, double dagsPris) {
        Konference konference = new Konference(navn, startDato, slutDato, by, adresse, dagsPris);
        Storage.addKonference(konference);
        return konference;
    }

    /**
     * Opdaterer en konference. Kræver: pris >= 0.
     *
     * @param konference
     *            den konference som skal opdateres
     * @param navn
     *            navnet på konferencen
     * @param startDato
     *            datoen for konferencens start
     * @param slutDato
     *            datoen for konferencens slutning
     * @param by
     *            den by konferencen bliver afholdt i
     * @param adresse
     *            adressen hvor koferencen bliver afholdt
     * @param dagsPris
     *            konferencegebyret pr. dag for at deltage i konferencen
     */
    public static void updateKonference(Konference konference, String navn, LocalDate startDato, LocalDate slutDato,
            String by, String adresse, double dagsPris) {
        konference.setNavn(navn);
        konference.setStartDato(startDato);
        konference.setSlutDato(slutDato);
        konference.setBy(by);
        konference.setAdresse(adresse);
        konference.setDagsPris(dagsPris);
    }

    /**
     * Sletter en konference. Kræver: konferencen har ingen tilmeldinger.
     *
     * @param konference
     *            den konference som skal slettes
     */
    public static void deleteKonference(Konference konference) {
        Storage.removeKonference(konference);
    }

    /**
     * Returnerer alle konferencer.
     *
     * @return konferencer listen over alle konferencer registreret i dette system.
     */
    public static ArrayList<Konference> getKonferencer() {
        return Storage.getKonferencer();
    }

    /**
     * Tilføjer en tilmelding til en konference.
     *
     * @param tilmelding
     */
    public static void addTilmeldingTilKonference(Tilmelding tilmelding, Konference konference) {
        konference = tilmelding.getKonference();
        konference.addTilmelding(tilmelding);
    }

    /**
     * Fjerner tilmeldingen fra konferencen, hvis konferencen ikke er null.
     *
     * @param konference
     *            konferencen som tilmeldingen flyttes fra. Kan være null.
     * @param tilmelding
     *            tilmeldingen som skal flyttes fra konferencen.
     */
    public static void removeTilmeldingFraKonference(Tilmelding tilmelding, Konference konference) {
        if (konference != null) {
            konference.removeTilmelding(tilmelding);
        }
    }

    /**
     * Tilføjer hotellet til konferencen.
     *
     * @param hotel
     * @param konference
     */
    public static void addHotelTilKonference(Hotel hotel, Konference konference) {
        konference.addHotel(hotel);
    }

    /**
     * Fjerner hotellet fra konferencen, hvis konferencen ikke er null.
     *
     * @param konference
     *            konferencen som hotellet flyttes fra. Kan være null.
     * @param hotel
     *            hotellet som skal flyttes fra konferencen.
     */
    public static void removeHotelFraKonference(Hotel hotel, Konference konference) {
        if (konference != null) {
            konference.removeHotel(hotel);
        }
    }

    /**
     * Tilføjer udflugten til konferencen.
     *
     * @param konference
     *            konferencen som udflugten flyttes til.
     * @param udflugt
     *            den udflugt som tilføjes til konferencen
     */
    public static void addUdflugtTilKonference(Udflugt udflugt, Konference konference) {
        konference.addUdflugt(udflugt);
    }

    /**
     * Fjerner udflugten fra konferencen, hvis konferencen ikke er null.
     *
     * @param konference
     *            konferencen som udflugten flyttes fra. Kan være null.
     * @param udflugt
     *            udflugten som skal flyttes fra konferencen.
     */
    public static void removeUdflugtFraKonference(Udflugt udflugt, Konference konference) {
        if (konference != null) {
            konference.removeUdflugt(udflugt);
        }
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Opretter en ny deltager.
     *
     * @param navn
     *            navnet på deltageren
     * @param adresse
     *            deltagerens adresse
     * @param by
     *            den by som deltageren bor i
     * @param land
     *            det land som deltageren bor i
     * @param telefonNr
     *            deltagerens telefonnummer
     * @return deltager den deltager som lige er blevet oprettet.
     */
    public static Deltager createDeltager(String navn, String adresse, String by, String land, int telefonNr) {
        Deltager deltager = new Deltager(navn, adresse, by, land, telefonNr);
        Storage.addDeltager(deltager);
        return deltager;
    }

    /**
     * Opdaterer en deltager.
     *
     * @param deltager
     *            den deltager som skal opdateres
     * @param navn
     *            navnet på deltageren
     * @param adresse
     *            deltagerens adresse
     * @param by
     *            den by som deltageren bor i
     * @param land
     *            det land som deltageren bor i
     * @param telefonNr
     *            deltagerens telefonnummer
     */
    public static void updateDeltager(Deltager deltager, String navn, String adresse, String by, String land,
            int telefonNr) {
        deltager.setNavn(navn);
        deltager.setAdresse(adresse);
        deltager.setBy(by);
        deltager.setLand(land);
        deltager.setTelefonNr(telefonNr);
    }

    /**
     * Sletter en deltager. Kræver: Deltager er ikke tilmeldt nogle konferencer.
     *
     * @param deltager
     *            den deltager som skal slettes.
     */
    public static void deleteDeltager(Deltager deltager) {
        Storage.removeDeltager(deltager);
    }

    /**
     * Returnerer alle deltagere.
     *
     * @return deltagere listen over alle deltagere registreret i systemet.
     */
    public static ArrayList<Deltager> getDeltagere() {
        return Storage.getDeltagere();
    }

    /**
     * Tilføjer en ledsager til en deltager.
     *
     * @param ledsager
     * @param deltager
     */
    public static void setLedsagerTilDeltager(Ledsager ledsager, Deltager deltager) {
        Deltager oldDeltager = ledsager.getDeltager();
        if (oldDeltager != null) {
            oldDeltager.setLedsager(null);
        }
        deltager.setLedsager(ledsager);
    }

    /**
     * Fjerner ledsageren fra en delager.
     *
     * @param ledsager
     * @param deltager
     */
    public static void removeLedsagerFraDeltager(Ledsager ledsager, Deltager deltager) {
        deltager.setLedsager(null);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Opretter en ny tilmelding. Kræver: En deltager som vil tilmeldes.
     *
     * @param konference
     *            den konference som tilmeldingen går til.
     * @param deltager
     *            den deltager som skal tilmeldes
     * @param foredragsholder
     *            deltageren er(ikke) foredragsholder
     * @param påHotel
     *            viser om deltageren har valgt at bo på hotel
     * @param ankomstDato
     *            datoen for deltagerens ankomst
     * @param afrejseDato
     *            datoen for deltagerens afrejse
     * @return tilmelding den tilmelding som lige er blevet oprettet.
     */
    public static Tilmelding createTilmelding(Konference konference, Deltager deltager, boolean foredragsholder,
            boolean påHotel, LocalDate ankomstDato, LocalDate afrejseDato) {
        Tilmelding tilmelding = konference.createTilmelding(konference, deltager, foredragsholder, påHotel, ankomstDato,
                afrejseDato);
        return tilmelding;
    }

    /**
     * Opdaterer en tilmelding.
     *
     * @param tilmelding
     *            den tilmelding som skal opdateres
     * @param konference
     *            den konference som tilmeldingen evt. flyttes til
     * @param deltager
     *            den deltager som skal tilmeldes
     * @param foredragsholder
     *            deltageren er(ikke) foredragsholder
     * @param påHotel
     *            fortæller om den tilmeldte har reserveret hotel
     * @param ankomstDato
     *            datoen for deltagerens ankomst
     * @param afrejseDato
     *            datoen for deltagerens afrejse
     */
    public static void updateTilmelding(Tilmelding tilmelding, Konference konference, Deltager deltager,
            boolean foredragsholder, boolean påHotel, LocalDate ankomstDato, LocalDate afrejseDato) {
        tilmelding.setKonference(konference);
        tilmelding.setDeltager(deltager);
        tilmelding.setForedragsholder(foredragsholder);
        tilmelding.setPåHotel(påHotel);
        tilmelding.setAnkomstDato(ankomstDato);
        tilmelding.setAfrejseDato(afrejseDato);
    }

    /**
     * Sletter en tilmelding.
     *
     * @param tilmelding
     *            den tilmelding som skal slettes
     */
    public static void deleteTilmelding(Tilmelding tilmelding) {
        Storage.removeTilmelding(tilmelding);
    }

    /**
     * Returnerer en liste af tilmeldinger.
     *
     * @return tilmeldinger listen over alle tilmeldinger i systemet.
     */
    public static ArrayList<Tilmelding> getTilmeldinger() {
        return Storage.getTilmeldinger();
    }

    /**
     * Denne metode sætter én bestemt deltager til en tilmelding.
     *
     * @param deltager
     *            deltageren som skal tilmeldes
     * @param tilmelding
     *            en tilmelding
     */
    public static void setDeltagerTilTilmelding(Deltager deltager, Tilmelding tilmelding) {
        tilmelding.setDeltager(deltager);
    }

    /**
     * Denne metode fjerner en deltager fra en tilmelding.
     *
     * @param deltager
     *            deltageren som ikke skal tilmeldes
     * @param tilmelding
     *            en tilmelding
     */
    public static void removeDeltagerFraTilmelding(Deltager deltager, Tilmelding tilmelding) {
        tilmelding.setDeltager(null);
    }

    /**
     * Tilføj et hotel til en tilmelding.
     *
     * @param hotel
     * @param tilmelding
     */
    public static void setHotelTilTilmelding(Tilmelding tilmelding, Hotel hotel) {
        tilmelding.setHotel(hotel);
    }

    /**
     * Fjern et hotel fra en tilmelding.
     *
     * @param hotel
     * @param tilmelding
     */
    public static void removeHotelFraTilmelding(Tilmelding tilmelding, Hotel hotel) {
        tilmelding.setHotel(null);
    }

    /**
     * Tilføjer et tilkøb til en tilmelding.
     *
     * @param tilkøb
     * @param tilmelding
     */
    public static void addTilkøbTilTilmelding(Tilkøb tilkøb, Tilmelding tilmelding) {
        tilmelding.addTilkøb(tilkøb);
    }

    /**
     * Fjern et tilkøb fra en tilmelding.
     *
     * @param tilmelding
     * @param tilkøb
     */
    public static void removeTilkøbFraTilmelding(Tilmelding tilmelding, Tilkøb tilkøb) {
        tilmelding.removeTilkøb(tilkøb);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Opretter et nyt hotel.
     *
     * @param navn
     *            navnet på hotellet
     * @param adresse
     *            hotellets adresse
     * @param by
     *            den by som hotellet ligger i
     * @param enkeltPris
     *            prisen for et enkeltværelse
     * @param dobbeltPris
     *            prisen for et dobbeltværelse
     * @param konferencen
     *            som knyttes til hotellet.
     * @return hotel det hotel som lige er blevet oprettet.
     */
    public static Hotel createHotel(String navn, String adresse, String by, double dobbeltPris, double enkeltPris,
            Konference konference) {
        Hotel hotel = new Hotel(navn, adresse, by, dobbeltPris, enkeltPris, konference);
        Storage.addHotel(hotel);
        return hotel;
    }

    /**
     * Opdatererer et hotel.
     *
     * @param hotel
     *            det hotel som skal opdateres
     * @param navn
     *            navnet på hotellet
     * @param adresse
     *            hotellets adresse
     * @param by
     *            byen hotellet ligger i
     * @param enkeltPris
     *            prisen for et enkeltværelse
     * @param dobbeltPris
     *            prisen for et dobbeltværelse
     * @param konference
     *            konferencen som hotellet er knyttet til
     */
    public static void updateHotel(Hotel hotel, String navn, String adresse, String by, double enkeltPris,
            double dobbeltPris, Konference konference) {
        hotel.setEnkeltPris(enkeltPris);
        hotel.setDobbeltPris(dobbeltPris);
        hotel.setKonference(konference);
    }

    /**
     * Sletter et hotel. Kræver: ingen deltagere er indlogeret på hotellet.
     *
     * @param hotel
     *            det hotel som skal slettes
     */
    public static void deleteHotel(Hotel hotel) {
        Storage.removeHotel(hotel);
    }

    /**
     * Returnerer alle hoteller.
     *
     * @return hoteller listen over alle hoteller registreret i systemet.
     */
    public static ArrayList<Hotel> getHoteller() {
        return Storage.getHoteller();
    }

    /**
     * Tilføjer et tilkøb til et hotel.
     *
     * @param tilkøb
     *            tilkøbet som tilbydes.
     * @param hotel
     *            hotellet som udbyder tilkøbet.
     */
    public static void addTilkøbTilHotel(Tilkøb tilkøb, Hotel hotel) {
        hotel.addTilkøb(tilkøb);
    }

    /**
     * Fjerner et tilkøb fra et hotel.
     *
     * @param tilkøb
     *            tilkøbet som ikke længere tilbydes.
     * @param hotel
     *            hotellet som ikke udbyder tilkøbet længere.
     */
    public static void removeTilkøbFraHotel(Tilkøb tilkøb, Hotel hotel) {
        hotel.removeTilkøb(tilkøb);
    }

    /**
     * Tilføjer en tilmelding til et Hotel.
     *
     * @param tilmelding
     *            den tilmelding som har valgt hotellet.
     * @param hotel
     *            det hotel som er blevet valgt i tilmeldingen.
     */
    public static void addTilmeldingTilHotel(Hotel hotel, Tilmelding tilmelding) {
        hotel.addTilmelding(tilmelding);
    }

    /**
     * Fjerner en tilmelding fra et Hotel.
     *
     * @param tilmelding
     *            den tilmelding som har afblæst hotellet.
     * @param hotel
     *            det hotel som er blevet valgt fra i tilmeldingen.
     */
    public static void removeTilmeldingFraHotel(Hotel hotel, Tilmelding tilmelding) {
        hotel.removeTilmelding(tilmelding);
    }

    // --------------------------------------------------------------------------------------------

    /**
     * Opretter en ny udflugt tilknyttet en konference. Kræver: Der er oprettet en
     * konference. pris >= 0.
     *
     * @param navn
     *            navnet på udflugten
     * @param dato
     *            datoen hvorpå udflugten finder sted
     * @param adresse
     *            adressen hvor udflugten forgår eller starter fra
     * @param by
     *            den by udflugten foregår i
     * @param pris
     *            prisen for at deltage i udflugten
     * @param frokost
     *            udflugten har(ikke) frokost inkluderet
     * @return udflugt den udflugt, som lige er blevet oprettet.
     */
    public static Udflugt createUdflugt(String navn, LocalDate dato, String by, String adresse, double pris,
            boolean frokost, Konference konference) {
        Udflugt udflugt = new Udflugt(navn, dato, by, adresse, pris, frokost, konference);
        Storage.addUdflugt(udflugt);
        return udflugt;
    }

    /**
     * Opdaterer en udflugt. Kræver: Der er oprettet mindst én udflugt. pris >= 0.
     *
     * @param udflugt
     *            den udflugt som skal opdateres
     * @param navn
     *            navnet på udflugten
     * @param dato
     *            datoen hvorpå udflugten finder sted
     * @param adresse
     *            adressen hvor udflugten forgår eller starter fra
     * @param by
     *            den by udflugten foregår i
     * @param pris
     *            prisen for at deltage i udflugten
     * @param frokost
     *            udflugten har(ikke) frokost inkluderet
     * @param newKonference
     */
    public static void updateUdflugt(Udflugt udflugt, String navn, LocalDate dato, String adresse, String by,
            double pris, boolean frokost, Konference konference) {
        udflugt.setNavn(navn);
        udflugt.setDato(dato);
        udflugt.setAdresse(adresse);
        udflugt.setBy(by);
        udflugt.setPris(pris);
        udflugt.setFrokost(frokost);
        udflugt.setKonference(konference);
    }

    /**
     * Sletter en udflugt. Kræver: Der er ingen ledsagere tilmeldt udflugten.
     *
     * @param udflugt
     *            den udflugt som skal slettes
     */
    public static void deleteUdflugt(Udflugt udflugt) {
        Storage.removeUdflugt(udflugt);
    }

    /**
     * Returnerer alle udflugter. Kræver: udflugter er oprettet.
     *
     * @return udflugter listen over alle udflugter registreret i systemet.
     */
    public static ArrayList<Udflugt> getUdflugter() {
        return Storage.getUdflugter();
    }

    /**
     * Tilføjer en ledsager til en udflugt.
     *
     * @param ledsager
     * @param udflugt
     */
    public static void addLedsagerTilUdflugt(Ledsager ledsager, Udflugt udflugt) {
        udflugt.addLedsager(ledsager);
    }

    /**
     * Fjerner en ledsager fra en udflugt.
     *
     * @param ledsager
     *            ledsageren som har afmeldt sig
     * @param udflugt
     *            udflugten som ledsageren har afmeldt.
     */
    public static void removeLedsagerFraUdflugt(Ledsager ledsager, Udflugt udflugt) {
        udflugt.removeLedsager(ledsager);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Opretter en ledsager.
     *
     * @param navn
     *            navnet på ledsageren
     * @param deltager
     *            den deltager som ledsages af ledsageren
     * @return ledsager den ledsager som lige er blevet oprettet.
     */
    public static Ledsager createLedsager(String navn, Deltager deltager) {
        Ledsager ledsager = new Ledsager(navn, deltager);
        Storage.addLedsager(ledsager);
        return ledsager;
    }

    /**
     * Opdaterer en ledsager. Kræver en ledsager er oprettet.
     *
     * @param ledsager
     *            den ledsager som skal opdateres
     * @param navn
     *            navnet på ledsageren
     * @param deltager
     *            den deltager som ledsages af ledsageren
     */
    public static void updateLedsager(Ledsager ledsager, String navn, Deltager deltager) {
        ledsager.setNavn(navn);
        ledsager.setDeltager(deltager);
    }

    /**
     * Sletter en ledsager. Kræver: Ledsageren er ikke tilmeldt nogle udflugter.
     *
     * @param ledsager
     *            den ledsager som skal slettes
     */
    public static void deleteLedsager(Ledsager ledsager) {
        Storage.removeLedsager(ledsager);
    }

    /**
     * Returnerer alle ledsagere.
     *
     * @return ledsagere listen over alle ledsagere registreret i systemet.
     */
    public static ArrayList<Ledsager> getLedsagere() {
        return Storage.getLedsagere();
    }

    /**
     * Tilføj en deltager til ledsageren.
     */
    public static void setDeltagerTilLedsager(Ledsager ledsager, Deltager deltager) {
        ledsager.setDeltager(deltager);
    }

    /**
     * Fjerner en deltager fra ledsageren.
     *
     * @param deltager
     * @param ledsager
     */
    public static void removeDeltagerFraLedsager(Ledsager ledsager, Deltager deltager) {
        ledsager.setDeltager(null);
    }

    /**
     * Tilføjer en udflugt til en ledsagers program.
     *
     * @param udflugt
     * @param ledsager
     */
    public static void addUdflugtTilLedsager(Udflugt udflugt, Ledsager ledsager) {
        ledsager.addUdflugt(udflugt);
    }

    /**
     * Fjerner en udflugt fra ledsagerens program.
     *
     * @param udflugt
     * @param ledsager
     */
    public static void removeUdflugtFraLedsager(Udflugt udflugt, Ledsager ledsager) {
        ledsager.removeUdflugt(udflugt);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Opretter et nyt firma. Kræver: Firmaet har > 1 medarbejder, som er tilmeldt
     * en konference.
     *
     * @param navn
     *            firmaets navn
     * @param telefonNr
     *            firmaets telefonnummer
     * @return firma det firma som lige er blevet oprettet.
     */
    public static Firma createFirma(String navn, int telefonNr) {
        Firma firma = new Firma(navn, telefonNr);
        Storage.addFirma(firma);
        return firma;
    }

    /**
     * Opdaterer et firma.
     *
     * @param firma
     *            det firma som skal opdateres
     * @param telefonNr
     *            firmaets telefonnummer
     */
    public static void updateFirma(Firma firma, int telefonNr) {
        firma.setTelefonNr(telefonNr);
    }

    /**
     * Sletter et firma. Kræver: Der er ingen deltagere tilmeldt gennem dette firma.
     *
     * @param firma
     *            det firma som skal slettes
     */
    public static void deleteFirma(Firma firma) {
        Storage.removeFirma(firma);
    }

    /**
     * Returnerer alle firmaer.
     *
     * @return firmaer listen over alle firmaer registreret i systemet.
     */
    public static ArrayList<Firma> getFirmaer() {
        return Storage.getFirmaer();
    }

    /**
     * Tilføjer en deltager til et Firma.
     *
     * @param deltager
     * @param firma
     */
    public static void addDeltagerTilFirma(Deltager deltager, Firma firma) {
        Firma oldFirma = deltager.getFirma();
        if (oldFirma != null) {
            oldFirma.removeDeltager(deltager);
        }
        firma.addDeltager(deltager);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Opretter et nyt tilkøb. Kræver: Der er oprettet et hotel som udbyder
     * tilkøbet. Pris >= 0.
     *
     * @param hotel
     *            det hotel som udbyder tilkøbet
     * @param type
     *            typen af tilkøbet beskrives som f.eks. bad, WIFI, mad etc.
     * @param pris
     *            prisen for tilkøbet
     * @return tilkøb det tilkøb som lige er blevet oprettet i systemet.
     */
    public static Tilkøb createTilkøb(String navn, double pris) {
        Tilkøb tilkøb = new Tilkøb(navn, pris);
        Storage.addTilkøb(tilkøb);
        return tilkøb;
    }

    /**
     * Opdaterer et tilkøb. Kræver: Pris >= 0.
     *
     * @param tilkøb
     *            det tilkøb som skal opdateres
     * @param type
     *            tilkøbets type beskrives som f.eks. mad, WIFI, mad, etc.
     * @param pris
     *            prisen for tilkøbet
     */
    public static void updateTilkøb(Tilkøb tilkøb, String navn, double pris) {
        tilkøb.setNavn(navn);
        tilkøb.setPris(pris);
    }

    /**
     * Sletter et tilkøb.
     *
     * @param tilkøb
     *            det tilkøb som skal slettes
     */
    public static void deleteTilkøb(Tilkøb tilkøb) {
        Storage.removeTilkøb(tilkøb);
    }

    /**
     * Returnerer alle tilkøb.
     *
     * @return tilkøB listen over alle tilkøb registreret i systemet.
     */
    public static ArrayList<Tilkøb> getTilkøb() {
        return Storage.getTilkøb();
    }

    // --------------------------------------------------------------------------------------------

    /**
     * Initializes the storage with some objects.
     */
    public static void initStorage() {
        /**
         * InitStorage er det som vi bruger til at afprøve om vores metoder fungerer i
         * service klassen, for at se om programmet kan køre. Inde i ServiceTest vil man
         * kunne teste det og se, at det virker 100 %.
         */
        Konference k1 = Service.createKonference("Miljø", LocalDate.now(), LocalDate.now().plusDays(2), "Århus",
                "Højvej 17", 1500);
        Konference k2 = Service.createKonference("Føling", LocalDate.now(), LocalDate.now().plusDays(3), "Århus",
                "Damvej 15", 2000);

        Deltager d1 = Service.createDeltager("Finn", "Toftvej16", "Århus", "Danmark", 22334455);
        Deltager d2 = Service.createDeltager("Niels", "Vejlevej 14", "Århus", "Danmark", 33445566);
        Deltager d3 = Service.createDeltager("Peter Sommer", "Tisvej 15", "Århus", "Danmark", 55667788);
        Ledsager l1 = Service.createLedsager("Mie Sommer", d3);

        Deltager d4 = Service.createDeltager("Lone Jensen", "Damvej 13", "Århus", "Danmark", 22334455);
        Ledsager l2 = Service.createLedsager("Jan Madsen", d4);

        Hotel h1 = Service.createHotel("Den Hvide Svane", "Bålvej 14", "Århus", 1050, 1250, k1);
        Tilkøb t1 = Service.createTilkøb("WIFI", 50);
        Service.addTilkøbTilHotel(t1, h1);

        Firma f1 = Service.createFirma("IBM", 75734455);
        Firma f2 = Service.createFirma("Google", 55667788);
        Firma f3 = Service.createFirma("Facebook", 22334455);
        Service.addDeltagerTilFirma(d1, f1);

        Udflugt u1 = Service.createUdflugt("Egeskov", LocalDate.now(), "Dudevej 15", "Århus", 75, false, k1);
        Udflugt u2 = Service.createUdflugt("Trapholt", LocalDate.now(), "Dude Upvej 14", "Århus", 200, true, k2);
        Udflugt u3 = Service.createUdflugt("Odense bytur", LocalDate.now(), "Mother 14", "Århus", 125, true, k1);
        Tilmelding til1 = Service.createTilmelding(k1, d1, false, false, LocalDate.now().minusDays(3), LocalDate.now());

        Tilmelding til2 = Service.createTilmelding(k1, d2, false, false, LocalDate.now().minusDays(3), LocalDate.now());
        Service.setHotelTilTilmelding(til2, h1);
        Service.addTilmeldingTilHotel(h1, til2);

        Tilmelding til3 = Service.createTilmelding(k1, d3, false, true, LocalDate.now().minusDays(3), LocalDate.now());
        Service.setHotelTilTilmelding(til3, h1);
        Service.addTilkøbTilTilmelding(t1, til3);
        Service.addUdflugtTilLedsager(u1, l1);
        Service.addUdflugtTilLedsager(u2, l1);

        Tilmelding til4 = Service.createTilmelding(k1, d4, true, true, LocalDate.now().minusDays(3), LocalDate.now());
        Service.setHotelTilTilmelding(til4, h1);
        Service.addTilkøbTilTilmelding(t1, til4);

        Service.addUdflugtTilLedsager(u1, l2);
        Service.addUdflugtTilLedsager(u3, l2);

        System.out.println(til1.samletPris());
        System.out.println(til2.samletPris());
        System.out.println(til3.samletPris());
        System.out.println(til4.samletPris());
    }

}
