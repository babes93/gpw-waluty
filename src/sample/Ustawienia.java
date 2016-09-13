package sample;

/**
 * Created by patryk on 24.08.16.
 */
public class Ustawienia {
    public static boolean ZmianaUstawien = false;
    public static String DataOstatniejAktualizacjiWIG20 = new String();
    public static String DataOstatniejAktualizacjiWaluty = new String();
    public static boolean AktualnaBazaWIG20NaDzien = false;
    public static boolean AktualnaBazaWalutNaDzien = false;
    //********************************************************************************      WIG20
    public static boolean WIG20AutoAktualizacja;
    public static boolean WIG20AutoAktualizacjaPoGodzienie;
    public static boolean WIG20AutoAktualizacjaPoUruchomieniu;
    public static boolean WIG20WyswietlaniePunktow;
    public static boolean WIG20OstatniRok,WIG20OstatniMiesiac,WIG20CaleArchiwum;
    public static String WIG20GodzinaAktualizacji = new String("18:00");
    public static String WIG20WyswietlanePunkty = new String("30");
    public static int WIG20WyswietlonePunkty;
    public static String WIG20MaksIloscDanychWykres = new String("200");
    public static int WIG20MaxIloscDanychWykres = 200;

    //*********************************************************************************         WalutyNBP
    public static boolean WalutyAutoAktualizacja;
    public static boolean WalutyAutoAktualizacjaPoGodzienie;
    public static boolean WalutyAutoAktualizacjaPoUruchomieniu;
    public static boolean WalutyWyswietlaniePunktow;
    public static boolean WalutyOstatniRok,WalutyOstatniMiesiac,WalutyCaleArchiwum;
    public static String WalutyGodzinaAktualizacji = new String("18:00");
    public static String WalutyWyswietlanePunkty = new String("30");
    public static int WalutyWyswietlonePunkty;
    public static String WalutyMaksIloscDanychWykres = new String("200");
    public static int Waluty0MaxIloscDanychWykres = 200;

}
