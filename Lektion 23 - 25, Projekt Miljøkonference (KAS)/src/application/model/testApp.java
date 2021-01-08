package application.model;

import java.time.LocalDate;

public class testApp {

    public static void main(String[] args) {
        /**
         * Vores Test app er den som vi bruger til at teste vores klasser i package
         * model. Vi har både en tester i Service og i Model for at være sikker på, at
         * alt ville fungere både her og i Service.
         */
        Konference k1 = new Konference("Miljø", LocalDate.now(), LocalDate.now().plusDays(2), "Århusvej 17", "Århus",
                1500);

        Deltager d1 = new Deltager("Finn", "Jensensdal 17", "Odense", "Danmark", 55664477);
        Deltager d2 = new Deltager("Niels", "Jernbanegade 24", "Århus", "Danmark", 88776655);
        Deltager d3 = new Deltager("Peter Sommer", "Byvej 14", "Århus", "Danmark", 99887766);
        Ledsager l1 = new Ledsager("Mie Sommer", d3);

        Deltager d4 = new Deltager("Lone Jensen", "RolighedsVej 14", "Århus", "Danmark", 99887766);
        Ledsager l2 = new Ledsager("Jan Madsen", d4);

        Hotel h1 = new Hotel("Den Hvide Svane", "Tisvej 14", "Århus", 1250, 1050, k1);

        Tilkøb t1 = new Tilkøb("WIFI", 50);
        h1.addTilkøb(t1);

        Udflugt u1 = new Udflugt("Egeskov", LocalDate.now(), "Petersvej 15", "Århus", 75, false, k1);
        Udflugt u2 = new Udflugt("Trapholt", LocalDate.now(), "Højvej 15", "Århus", 200, true, k1);
        Udflugt u3 = new Udflugt("Odense bytur", LocalDate.now(), "Fuckvej 15", "Odense", 125, true, k1);

        Tilmelding til1 = new Tilmelding(k1, d1, false, LocalDate.now(), LocalDate.now().minusDays(3), false);
        til1.setKonference(k1);
        System.out.println(til1.samletPris());

        Tilmelding til2 = new Tilmelding(k1, d2, false, LocalDate.now(), LocalDate.now().minusDays(3), false);
        til2.setKonference(k1);
        til2.setHotel(h1);
        System.out.println(til2.samletPris());

        Tilmelding til3 = new Tilmelding(k1, d3, false, LocalDate.now(), LocalDate.now().minusDays(3), true);
        til3.setKonference(k1);
        til3.setHotel(h1);
        til3.addTilkøb(t1);

        l1.addUdflugt(u1);
        l1.addUdflugt(u2);
        System.out.println(til3.samletPris());

        Tilmelding til4 = new Tilmelding(k1, d4, true, LocalDate.now(), LocalDate.now().minusDays(3), true);
        til4.setKonference(k1);
        til4.setHotel(h1);
        til4.addTilkøb(t1);

        l2.addUdflugt(u1);
        l2.addUdflugt(u3);
        System.out.println(til4.samletPris());
    }

}
