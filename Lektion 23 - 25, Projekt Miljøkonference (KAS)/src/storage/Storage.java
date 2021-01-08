package storage;

import java.util.ArrayList;
import application.model.Deltager;
import application.model.Firma;
import application.model.Hotel;
import application.model.Konference;
import application.model.Ledsager;
import application.model.Tilkøb;
import application.model.Tilmelding;
import application.model.Udflugt;

public class Storage {

    private static ArrayList<Konference> konferencer = new ArrayList<>();
    private static ArrayList<Hotel> hoteller = new ArrayList<>();
    private static ArrayList<Udflugt> udflugter = new ArrayList<>();
    private static ArrayList<Deltager> deltagere = new ArrayList<>();
    private static ArrayList<Tilmelding> tilmeldinger = new ArrayList<>();
    private static ArrayList<Ledsager> ledsagere = new ArrayList<>();
    private static ArrayList<Tilkøb> tilkøB = new ArrayList<>();
    private static ArrayList<Firma> firmaer = new ArrayList<>();

    // --------------------------------------------------------------------------------------------

    public static ArrayList<Konference> getKonferencer() {
        return new ArrayList<>(konferencer);
    }

    public static void addKonference(Konference konference) {
        konferencer.add(konference);
    }

    public static void removeKonference(Konference konference) {
        konferencer.remove(konference);
    }

    // --------------------------------------------------------------------------------------------

    public static ArrayList<Hotel> getHoteller() {
        return new ArrayList<>(hoteller);
    }

    public static void addHotel(Hotel hotel) {
        hoteller.add(hotel);
    }

    public static void removeHotel(Hotel hotel) {
        hoteller.remove(hotel);
    }

    // --------------------------------------------------------------------------------------------

    public static ArrayList<Udflugt> getUdflugter() {
        return new ArrayList<>(udflugter);
    }

    public static void addUdflugt(Udflugt udflugt) {
        udflugter.add(udflugt);
    }

    public static void removeUdflugt(Udflugt udflugt) {
        udflugter.remove(udflugt);
    }

    // --------------------------------------------------------------------------------------------

    public static ArrayList<Deltager> getDeltagere() {
        return new ArrayList<>(deltagere);
    }

    public static void addDeltager(Deltager deltager) {
        deltagere.add(deltager);
    }

    public static void removeDeltager(Deltager deltager) {
        deltagere.remove(deltager);
    }

    // --------------------------------------------------------------------------------------------

    public static ArrayList<Tilmelding> getTilmeldinger() {
        return new ArrayList<>(tilmeldinger);
    }

    public static void addTilmelding(Tilmelding tilmelding) {
        tilmeldinger.add(tilmelding);
    }

    public static void removeTilmelding(Tilmelding tilmelding) {
        tilmeldinger.remove(tilmelding);
    }

    // --------------------------------------------------------------------------------------------

    public static ArrayList<Ledsager> getLedsagere() {
        return new ArrayList<>(ledsagere);
    }

    public static void addLedsager(Ledsager ledsager) {
        ledsagere.add(ledsager);
    }

    public static void removeLedsager(Ledsager ledsager) {
        ledsagere.remove(ledsager);
    }

    // --------------------------------------------------------------------------------------------

    public static ArrayList<Tilkøb> getTilkøb() {
        return new ArrayList<>(tilkøB);
    }

    public static void addTilkøb(Tilkøb tilkøb) {
        tilkøB.add(tilkøb);
    }

    public static void removeTilkøb(Tilkøb tilkøb) {
        tilkøB.remove(tilkøb);
    }

    // --------------------------------------------------------------------------------------------

    public static ArrayList<Firma> getFirmaer() {
        return new ArrayList<>(firmaer);
    }

    public static void addFirma(Firma firma) {
        firmaer.add(firma);
    }

    public static void removeFirma(Firma firma) {
        firmaer.remove(firma);
    }

    // --------------------------------------------------------------------------------------------
}
