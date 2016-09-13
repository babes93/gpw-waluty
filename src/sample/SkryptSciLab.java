package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

/**
 * Created by patryk on 25.08.16.
 */
public class SkryptSciLab {
    private String Sciezka = new String();
    private String SciezkaDaneWig20 = new String();
    private String SciezkaDaneWaluty = new String();
    private boolean wig20=false;
    private boolean waluty = false;
    private boolean plikiwczytywania = false;
    public static double Progres =0;
    private UstawieniaPlik ustawieniaPlik = new UstawieniaPlik();
    private WIG20 wig = new WIG20();
    private WalutyNBP walutyNBP = new WalutyNBP();
    private PlikWczytywaniaWIG20 plikWczytywaniaWIG20;
    private PlikWczytywaniaWaluty plikWczytywaniaWaluty;

    public void setSciezka(String path){
        this.Sciezka=path;
        this.SciezkaDaneWig20 = Sciezka+"/Dane/WIG20/";
        this.SciezkaDaneWaluty = Sciezka+"/Dane/Waluty/";
    }

    public void setTryb(boolean wig20,boolean waluty, boolean plikiwczytywania){
        this.wig20=wig20;
        this.waluty=waluty;
        this.plikiwczytywania=plikiwczytywania;
    }

    public void uruchomWatekZapisu(){
        WczytanieDanychWatek wczytanieDanychWatek = new WczytanieDanychWatek(Sciezka);
        wczytanieDanychWatek.setTryb(this.wig20,this.waluty,this.plikiwczytywania);
        Thread watekZapisu = new Thread(wczytanieDanychWatek);
        watekZapisu.start();
    }

    public void stwurzPlikWczytywaniaWIG20(){
        plikWczytywaniaWIG20=new PlikWczytywaniaWIG20("SkryptWczytywaniaWIG20",Sciezka);
        plikWczytywaniaWIG20.CreateSkrypt();
    }

    public void stwurzPlikWczytywaniaWaluty(){
        plikWczytywaniaWaluty= new PlikWczytywaniaWaluty("SkryptWczytywaniaWaluty",Sciezka);
        plikWczytywaniaWaluty.CreateSkrypt();
    }

    //*******************************************************************
    public class PlikWczytywaniaWaluty{
        private String sciezka;
        private String nazwapliku;
        private Plik plik;

        public PlikWczytywaniaWaluty(String nazwapliku,String sciezka){
            this.nazwapliku=nazwapliku;
            this.sciezka=sciezka;
            this.plik= new Plik("/SkryptWczytywaniaWaluty",sciezka);
        }

        public void CreateSkrypt(){
            walutyNBP.WczytajPodsawoweDaneWaluty();
            ustawieniaPlik.WczytajUstawienia();
            //****************************************************      wstepne info
            plik.addStringLine("//\t\t\tSktypt wczytywania zmiennych Waluty NBP");
            plik.addStringLine("//Data utworzenia pliku: "+ustawieniaPlik.getAktualnaData());
            plik.addStringLine("//Data ostatniej aktualizacji bazy danych: "+ustawieniaPlik.ustawienia.DataOstatniejAktualizacjiWaluty);
            plik.addStringLine("//\n//\n");
            plik.addStringLine("//Znaczenie danych:");
            plik.addStringLine("//KODWALUTY_BUY - kurs kupna waluty: kodwaluty");
            plik.addStringLine("//KODWALUTY_SEL - kurs sprzedazy waluty: kodwaluty");
            plik.addStringLine("//KODWALUTY_SIZE - dlugosc wektora danych dla waluty: kodwaluty");
            plik.addStringLine("\n");

            for(int i=0;i<walutyNBP.walutyPodstawoweDane.size();i++){
                String kodwaluty = walutyNBP.walutyPodstawoweDane.get(i).getKodWaluty();
                String[]dane = new String[2];
                dane[0] = "_BUY";
                dane[1] = "_SEL";
                plik.addStringLine("\n\n//Dane dla waluty: "+walutyNBP.walutyPodstawoweDane.get(i).getKodWaluty()+"\n");
                for(int x=0;x<2;x++){
                    plik.addStringLine(kodwaluty + "_tmp = mgetl(\"Dane/Waluty/" + kodwaluty + "/" + kodwaluty + dane[x]+".sce\");");
                    plik.addStringLine(kodwaluty + "_tmp_length=length(" + kodwaluty + "_tmp);");
                    plik.addStringLine(kodwaluty + dane[x]+" = evstr(part(" + kodwaluty+ "_tmp,9:" + kodwaluty + "_tmp_length));");
                }
                plik.addStringLine(kodwaluty+"_SIZE = max(size("+kodwaluty+"_BUY));");
            }
            plik.zakonczZapis();
        }
    }
    //*******************************************************************
    public class PlikWczytywaniaWIG20{
        private String sciezka;
        private String nazwapliku;
        private Plik plik;

        public PlikWczytywaniaWIG20(String nazwapliku, String sciezka){
            this.sciezka=sciezka;
            this.nazwapliku=nazwapliku;
            this.plik=new Plik("/SkryptWczytywaniaWIG20",sciezka);
        }

        public void CreateSkrypt(){
            ustawieniaPlik.WczytajUstawienia();
            wig.WczytajWIG20();
            wig.WczytajPodstawoweInfoWIG20();


            //**************************************************************************    Wstepne Info
            plik.addStringLine("//\t\t\tSktypt wczytywania zmiennych WIG20");
            plik.addStringLine("//Data utworzenia pliku: "+ustawieniaPlik.getAktualnaData());
            plik.addStringLine("//Data ostatniej aktualizacji bazy danych: "+ustawieniaPlik.ustawienia.DataOstatniejAktualizacjiWIG20);
            plik.addStringLine("//\n//\n");
            plik.addStringLine("//Znaczenie danych:");
            plik.addStringLine("//TICkER_OPN - kurs otwarcia dla spolki : Ticker");
            plik.addStringLine("//TICKER_CLO - kurs zamkniecia dla spolki: Ticker");
            plik.addStringLine("//TICKER_MAX - max kurs dla spolki: Ticker");
            plik.addStringLine("//TICKER_MIN - min kurs dla spolki: Ticker");
            plik.addStringLine("//TICKER_SIZE - dlugosc wektora danych dla spolki: Ticker");
            plik.addStringLine("\n");
            
            for(int i=0;i<wig.wig20Tabela.size();i++){
                String ticker = wig.wig20Tabela.get(i).getTicker();
                String[] dane = new String[4];
                dane[0] = "_OPN";
                dane[1] = "_CLO";
                dane[2] = "_MAX";
                dane[3] = "_MIN";
                plik.addStringLine("\n\n//Wczytywanie spolki: " + ticker+"\n");
                for(int x=0;x<4;x++) {
                    plik.addStringLine(ticker + "_tmp = mgetl(\"Dane/WIG20/" + ticker + "/" + ticker + dane[x]+".sce\");");
                    plik.addStringLine(ticker + "_tmp_length=length(" + ticker + "_tmp);");
                    plik.addStringLine(ticker + dane[x]+" = evstr(part(" + ticker + "_tmp,9:" + ticker + "_tmp_length));");
                }
                plik.addStringLine(ticker+"_SIZE = max(size("+ticker+"_OPN));");
            }
            plik.zakonczZapis();
        }


    }
    //*******************************************************************       WALUTY DANE
    public class PlikiWaluty{
        private Plik kursKupna;
        private Plik kursSprzedazy;
        private String kodwaluty = new String();
        private String sciezka = new String();

        public PlikiWaluty(String kodwaluty, String sciezka){
            this.kodwaluty=kodwaluty;
            this.sciezka=sciezka;

            String path = new String("/Dane/Waluty/"+kodwaluty+"/"+kodwaluty);

            this.kursKupna = new Plik(path+"_BUY",sciezka);
            this.kursSprzedazy = new Plik(path+"_SEL",sciezka);

            this.kursKupna.addString(kodwaluty+"_BUY=[");
            this.kursSprzedazy.addString(kodwaluty+"_SEL=[");
        }

        public void zakonczZapis(){
            this.kursKupna.zakonczZapisDane();
            this.kursSprzedazy.zakonczZapisDane();
        }

        public void zapiszDane(double kupno, double sprzedaz){
            this.kursKupna.zapisDouble(kupno);
            this.kursSprzedazy.zapisDouble(sprzedaz);
        }

        public void zapiszDane(String kupno, String sprzedaz){
            this.kursKupna.zapisDane(kupno);
            this.kursSprzedazy.zapisDane(sprzedaz);
        }

    }
    //********************************************************************     WIG20 DANE
    public class PlikiWIG20Dane{
        private Plik Otwarcie;
        private Plik Zamkniecie;
        private Plik MaxKurs;
        private Plik MinKurs;
        private String ticker = new String();
        private String sciezka = new String();

        public PlikiWIG20Dane(String ticker, String sciezka){
            this.ticker=ticker;
            this.sciezka=sciezka;
            String path = new String("/Dane/WIG20/"+ticker+"/"+ticker);
            this.Otwarcie=new Plik(path+"_OPN",sciezka);
            this.Zamkniecie=new Plik(path+"_CLO",sciezka);
            this.MaxKurs=new Plik(path+"_MAX",sciezka);
            this.MinKurs=new Plik(path+"_MIN",sciezka);

            this.Otwarcie.addString(ticker+"_OPN=[");
            this.Zamkniecie.addString(ticker+"_CLO=[");
            this.MaxKurs.addString(ticker+"_MAX=[");
            this.MinKurs.addString(ticker+"_MIN=[");
        }

        public void zapiszDane(double otwarcie, double zamkniecie, double maxkurs, double minkurs){
            this.Otwarcie.zapisDouble(otwarcie);
            this.Zamkniecie.zapisDouble(zamkniecie);
            this.MaxKurs.zapisDouble(maxkurs);
            this.MinKurs.zapisDouble(minkurs);
        }

        public void zapiszDane(String otwarcie,String zamkniecie, String maxkurs, String minkurs){
            this.Otwarcie.zapisDane(otwarcie);
            this.Zamkniecie.zapisDane(zamkniecie);
            this.MaxKurs.zapisDane(maxkurs);
            this.MinKurs.zapisDane(minkurs);
        }

        public void zakonczZapis(){
            this.Otwarcie.zakonczZapisDane();
            this.Zamkniecie.zakonczZapisDane();
            this.MaxKurs.zakonczZapisDane();
            this.MinKurs.zakonczZapisDane();
        }
    }
    //*********************************************************************    Clasa pliki
    private class Plik{
        private String NazwaPliku = new String();
        private String Sciezka = new String();
        private PrintWriter zapis;
        private File plik;

        public Plik(String name,String path){
            this.Sciezka=path;
            this.NazwaPliku = name+".sce";
            plik = new File(this.Sciezka+this.NazwaPliku);
            plik.getParentFile().mkdirs();
            try {
                plik.createNewFile();
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
            try{
                zapis = new PrintWriter(this.plik);
            }catch (FileNotFoundException fe){
                fe.printStackTrace();
            }

        }

        public void zapisDouble(double dane){
            this.zapis.print(" "+Double.toString(dane));
        }

        public void zapisDane(String dane){
            this.zapis.print(" "+dane);
        }

        public void addString(String string){
            this.zapis.print(string);
        }

        public void addStringLine(String string){
            this.zapis.println(string);
        }

        public void zakonczZapis(){
            this.zapis.close();
        }

        public void zakonczZapisDane(){
            this.zapis.print(" ];");
            this.zapis.close();
        }
    }
    //********************************************************************      watek wczytywania
    private class WczytanieDanychWatek implements Runnable{
        private String sciezka = new String();
        private WIG20 wig20 = new WIG20();
        private WalutyNBP walutyNBP = new WalutyNBP();
        private PlikiWIG20Dane plikiWIG20Dane;
        private PlikiWaluty plikiWaluty;
        private boolean DaneWIG20=false;
        private boolean DaneWaluty=false;
        private boolean wczytywanie = false;
        private int IloscWszystkichDanych =0;
        private int Postemp=0;
        private PlikWczytywaniaWIG20 plikWczytywaniaWIG20;
        private PlikWczytywaniaWaluty plikWczytywaniaWaluty;
        public WczytanieDanychWatek(String sciezka){
            this.sciezka=sciezka;
            wig20.WczytajWIG20();
            wig20.WczytajPodstawoweInfoWIG20();
            walutyNBP.WczytajPodsawoweDaneWaluty();
        }

        public void setTryb(boolean wig20,boolean waluty,boolean wczytywanie){
            this.DaneWIG20=wig20;
            this.DaneWaluty=waluty;
            this.wczytywanie=wczytywanie;
        }


        @Override
        public void run() {
            //*******************************************************   Laczenie z Baza
            Connection polaczenie=null;
            try {
                Class.forName("org.sqlite.JDBC");
                polaczenie = DriverManager.getConnection("jdbc:sqlite:GPW.db");
            } catch (Exception e) {
                e.printStackTrace();
            }

            java.sql.Statement stat = null;

            try {
                stat = polaczenie.createStatement();

            } catch (SQLException e) {
                e.printStackTrace();
            }
         //   System.out.println("Obliczanie danych: ");
            //****************************************************** Obliczanie danych WIG20
            if(this.DaneWIG20){

                try {
                    ResultSet rs = stat.executeQuery(this.getLiczbaDanychNotowania());
                    while (rs.next()) {
                        IloscWszystkichDanych += Integer.valueOf(rs.getString("COUNT(DATA)"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
         //   System.out.println("Dane: "+IloscWszystkichDanych);
            //******************************************************   Obliczanie danych WalutyNBP
            if(this.DaneWaluty){
                for (int i=0;i<walutyNBP.walutyPodstawoweDane.size();i++){
                    try {
                        ResultSet rs = stat.executeQuery(this.getLiczbaDanychWaluty(walutyNBP.walutyPodstawoweDane.get(i).getKodWaluty()));
                        while (rs.next()) {
                            IloscWszystkichDanych += Integer.valueOf(rs.getString("COUNT(DATA)"));

                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        //    System.out.println("Dane: "+IloscWszystkichDanych);
            //*****************************************************     Pobieranie danych WIG20
            if(this.DaneWIG20){
                for(int i=0;i<wig20.wig20Tabela.size();i++){
                    plikiWIG20Dane = new PlikiWIG20Dane(wig20.wig20Tabela.get(i).getTicker(),this.sciezka);
                    try {
                        ResultSet rs = stat.executeQuery(this.getDaneNotowaniaByTicker(wig20.wig20Tabela.get(i).getTicker()));
                        while (rs.next()) {
                            this.Postemp++;
                            Progres = (double)Postemp/IloscWszystkichDanych;
                            double otwarcie = rs.getDouble("OTWARCIE");
                            double zamkniecie = rs.getDouble("ZAMKNIECIE");
                            double maxkurs = rs.getDouble("MAXKURS");
                            double minkurs = rs.getDouble("MINKURS");
                            plikiWIG20Dane.zapiszDane(otwarcie,zamkniecie,maxkurs,minkurs);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    plikiWIG20Dane.zakonczZapis();
            //        System.out.println("Spolka: "+wig20.wig20Tabela.get(i).getTicker());
                }
            }
            //*****************************************************     Pobieranie danych Waluty NBP
            if(this.DaneWaluty){
                for (int i=0;i<walutyNBP.walutyPodstawoweDane.size();i++){
                    plikiWaluty = new PlikiWaluty(walutyNBP.walutyPodstawoweDane.get(i).getKodWaluty(),sciezka);
                    try {
                        ResultSet rs = stat.executeQuery(this.getDaneWalutyByKodWaluty(walutyNBP.walutyPodstawoweDane.get(i).getKodWaluty()));
                        while (rs.next()) {
                            this.Postemp++;
                            Progres=(double)Postemp/IloscWszystkichDanych;
                            double kupno = rs.getDouble("KURS_KUPNA");
                            double sprzedaz = rs.getDouble("KURS_PRZEDAZY");

                            plikiWaluty.zapiszDane(kupno,sprzedaz);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    plikiWaluty.zakonczZapis();
             //       System.out.println("Waluty: "+walutyNBP.walutyPodstawoweDane.get(i).getKodWaluty());
                }
            }
            //****************************************************      Rozlaczenie z Baza Danych
            try{
                stat.close();
                polaczenie.close();

            }catch (SQLException e){
                e.printStackTrace();
            }


            //********************************************************* Pliki wczytywania
            if(this.wczytywanie){
                plikWczytywaniaWIG20=new PlikWczytywaniaWIG20("SkryptWczytywaniaWIG20",sciezka);
                plikWczytywaniaWIG20.CreateSkrypt();

                plikWczytywaniaWaluty = new PlikWczytywaniaWaluty("SkryptWczytywaniaWaluty",sciezka);
                plikWczytywaniaWaluty.CreateSkrypt();
            }

        //    System.out.println("zakonczono");
        }

        //***********************************************************************   Zapytania SQL
        private String getLiczbaDanychNotowania(){
            return new String("SELECT COUNT(DATA) FROM NOTOWANIA;");
        }

        private String getLiczbaDanychWaluty(String kodwaluty){
            return new String("SELECT COUNT(DATA) FROM WALUTY WHERE KOD_WALUTY ='"+kodwaluty+"';");
        }

        private String getDaneNotowaniaByTicker(String ticker){
            return new String("SELECT * FROM NOTOWANIA WHERE TICKER = '"+ticker+"' ORDER BY DATA;");
        }

        private String getDaneWalutyByKodWaluty(String kodwaluty){
            return new String("SELECT * FROM WALUTY WHERE KOD_WALUTY ='"+kodwaluty+"' ORDER BY DATA;");
        }

    }
}
