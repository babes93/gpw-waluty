package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.omg.CORBA.INV_FLAG;
import org.omg.CORBA.ParameterMode;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 * Created by patryk on 21.08.16.
 */
public class WalutyNBP {
    public static boolean aktualizacja=true;
    public static boolean aktualizacjaKomenda = false;
    public static boolean zaktualizowanoWaluty = false;
    public InfoPobieranie infoPobieranie;
    public ObservableList<WalutyDane> walutyPodstawoweDane = FXCollections.observableArrayList();
    private WalutySQLKomendy walutySQLKomendy = new WalutySQLKomendy();


    public WalutyNBP(){
        this.infoPobieranie=new InfoPobieranie();
    }

    public void PobierzWalutyDoBazyDanych(){

            PobieranieWalutNBPdoBazyDanych pobieranieWalutNBPdoBazyDanych = new PobieranieWalutNBPdoBazyDanych(2002,2016,this.infoPobieranie);
            Thread watekWaluty = new Thread(pobieranieWalutNBPdoBazyDanych);
            watekWaluty.start();
    }

    public void WczytajPodsawoweDaneWaluty() {

        //****************************************************************      Poleczenie z Baza Danych
        Connection polaczenie = null;
        try {
            Class.forName("org.sqlite.JDBC");
            polaczenie = DriverManager.getConnection("jdbc:sqlite:GPW.db");
            infoPobieranie.setKomenda("Polaczono z Baza Danych");
        } catch (Exception e) {
            e.printStackTrace();
        }

        java.sql.Statement stat = null;

        try {
            stat = polaczenie.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        //**********************************************************    Wlasciwie wczytywanie Danych


        //********************************************************* ILOSC WALUT
        int IloscWalutwArchiwum = 0;
        try {
            ResultSet rs = stat.executeQuery(walutySQLKomendy.getKomendaIloscWalut());
            while (rs.next()) {
                IloscWalutwArchiwum = Integer.valueOf(rs.getString("COUNT(DISTINCT KOD_WALUTY)"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //******************************************************** POSZCZEGULNE WALUTY
        try {
            ResultSet rs = stat.executeQuery(walutySQLKomendy.getKodyWalut());
            while (rs.next()) {
                String kodwaluty = rs.getString("KOD_WALUTY");
                this.walutyPodstawoweDane.add(new WalutyDane(kodwaluty));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //******************************************************* Uzupelnianie danych
        for (int i = 0; i < walutyPodstawoweDane.size(); i++) {
            walutySQLKomendy.setKodWaluty(walutyPodstawoweDane.get(i).getKodWaluty());

            //*********************************************************************         MaxData
            try {
                ResultSet rs = stat.executeQuery(walutySQLKomendy.getMaxData());
                while (rs.next()) {
                    String maxdata = rs.getString("MAX(DATA)");
                    this.walutyPodstawoweDane.get(i).setDataDo(maxdata);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //**********************************************************************        MinData
            try {
                ResultSet rs = stat.executeQuery(walutySQLKomendy.getMinData());
                while (rs.next()) {
                    String mindata = rs.getString("MIN(DATA)");
                    if (mindata.compareTo("2002-03-01")<=0){
                        mindata="2002-03-01";
                    }
                    this.walutyPodstawoweDane.get(i).setDataOd(mindata);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //********************************************************************          Dane
            try {
                ResultSet rs = stat.executeQuery(walutySQLKomendy.getDaneArchiwumByData(walutyPodstawoweDane.get(i).getDataDo()));
                while (rs.next()) {
                    walutyPodstawoweDane.get(i).setKursSprzedazy(rs.getString("KURS_PRZEDAZY"));
                    walutyPodstawoweDane.get(i).setKursKupna(rs.getString("KURS_KUPNA"));
                    walutyPodstawoweDane.get(i).setPrzelicznik(rs.getString("PRZELICZNIK"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        //*********************************************************     Wczytywanie naz walut

        for (int i = 0; i < walutyPodstawoweDane.size(); i++) {
            walutySQLKomendy.setKodWaluty(walutyPodstawoweDane.get(i).getKodWaluty());
            try {
                ResultSet rs = stat.executeQuery(walutySQLKomendy.getNazwaWalutyByKodWaluty());
                while (rs.next()) {
                    walutyPodstawoweDane.get(i).setNazwaWaluty(rs.getString("NAZWA_WALUTY"));
                    break;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

        //*********************************************************         Usuwanie starych walut
        int rozmiar = walutyPodstawoweDane.size();
        for(int i=0;i<rozmiar;i++){
            if(walutyPodstawoweDane.get(i).getDataDo().compareTo("2011-01-01")<=0){
                walutyPodstawoweDane.remove(i);
                i--;
                rozmiar=walutyPodstawoweDane.size();
            }
        }


        //***********************************************************       Zakonczenie polaczenia z Baza Danych
        try{
            stat.close();
            polaczenie.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void PobierzNazwyWalutDoBazyDanych(){
        String AdresURL = new String("https://www.ups.com/worldshiphelp/WS14/PLK/AppHelp/Codes/Country_Territory_and_Currency_Codes.htm");

        Document doc=null;
        try{
            doc = Jsoup.connect(AdresURL).get();
        }catch (IOException e){
            e.printStackTrace();
        }
        //********************************************************************      laczenie  zBaza danych
        Connection polaczenie =null;
        try{
            Class.forName("org.sqlite.JDBC");
            polaczenie= DriverManager.getConnection("jdbc:sqlite:GPW.db");
        }catch (Exception e){
            e.printStackTrace();
        }
        java.sql.Statement stat = null;
        try{
            stat = polaczenie.createStatement();
        }catch (SQLException e){
            e.printStackTrace();
        }


        //**********************************************************************        przetwarzanie i zapis do bazy danych
        Elements tabela = doc.getElementsByTag("table");
        Elements elementyTabeli = tabela.get(0).getElementsByTag("td");

        int IloscDanych = elementyTabeli.size()/5;
        int x=0;
       for(int i=1;i<IloscDanych;i++){
           walutySQLKomendy.setKraj(elementyTabeli.get(i*5).text());
           walutySQLKomendy.setKodUPS(elementyTabeli.get(i*5+1).text());
           walutySQLKomendy.setKodIATA(elementyTabeli.get(i*5+2).text());
           walutySQLKomendy.setKodWaluty(elementyTabeli.get(i*5+3).text());
           walutySQLKomendy.setNazwaWaluty(elementyTabeli.get(i*5+4).text());

           try{
               stat.executeUpdate(walutySQLKomendy.getKomendaInsertKodyWalut(++x));
           }catch (SQLException e){
               e.printStackTrace();
           }
       }
       //*************************************************************************  Rozlaczenie z baza danych
        try{
            stat.close();
            polaczenie.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        infoPobieranie.setKomenda("");
        try{
            Thread.sleep(100);
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }

    }

    public ObservableList<String> getListaKodyWalut(){
        ObservableList<String> kodywalut = FXCollections.observableArrayList();
        for (int i=0;i<walutyPodstawoweDane.size();i++){
            kodywalut.add(new String(walutyPodstawoweDane.get(i).getKodWaluty()));
        }
        return kodywalut;
    }

    public ObservableList<String> getDataDo() {
        ObservableList<String> datado = FXCollections.observableArrayList();
        for (int i = 0; i < walutyPodstawoweDane.size(); i++) {
            datado.add(new String(walutyPodstawoweDane.get(i).getDataDo()));
        }
        return datado;
    }

    public ObservableList<String> getDataOd(){
        ObservableList<String> dataod = FXCollections.observableArrayList();
        for (int i=0; i<walutyPodstawoweDane.size();i++){
            dataod.add(new String(walutyPodstawoweDane.get(i).getDataOd()));
        }
        return dataod;
    }

    public void aktualizujWalutyBazaDanych(){
        AktualizacjaWalutNBPBazaDanych aktualizacjaWalutNBPBazaDanych = new AktualizacjaWalutNBPBazaDanych(walutyPodstawoweDane,infoPobieranie);
        Thread watekAktualizacji = new Thread(aktualizacjaWalutNBPBazaDanych);
        watekAktualizacji.start();

    }

    //*********************************************************************     Watek Aktualizacji Walut
    private class AktualizacjaWalutNBPBazaDanych implements Runnable{
        ObservableList<WalutyDane> walutyDane = FXCollections.observableArrayList();
        WalutySQLKomendy walutySQLKomendy = new WalutySQLKomendy();
        InfoPobieranie infoPobieranie = new InfoPobieranie();
        private UstawieniaPlik ustawieniaPlik = new UstawieniaPlik();


        private double prog=0;
        private int odRoku,doRoku;
        private String sciezka_dir = new String("http://www.nbp.pl/kursy/xml/dir");
        private String aktualnyDIR = new String(sciezka_dir+".txt");
        private ObservableList<String> ListaTabel = FXCollections.observableArrayList();
        private String sciezka = new String();
        private String sciezkaXML = new String("http://www.nbp.pl/kursy/xml/");
        private Connection polaczenie = null;


        public AktualizacjaWalutNBPBazaDanych(ObservableList<WalutyDane> walutyDanes){
            this.walutyDane=walutyDanes;
            this.setData();
        }

        public AktualizacjaWalutNBPBazaDanych(ObservableList<WalutyDane> walutyDanes, InfoPobieranie infoPobieranie){
            this.walutyDane=walutyDanes;
            this.infoPobieranie=infoPobieranie;
            this.setData();
        }

        public void setData(){
            String dataod = walutyDane.get(0).getDataOd();
            String datado = walutyDane.get(0).getDataDo();
            String[] tmp = dataod.split("-");
            this.odRoku = Integer.valueOf(tmp[0]);
            String[] tmp2 = datado.split("-");
            this.doRoku = Integer.valueOf(tmp2[0]);
        }

        @Override
        public void run() {
            //************************************************************* Wydorebnianie plikow z znancznikiem c
            //************************************************************* Z wszystkich lat i zapis do ListaTabel
            Document doc;
            infoPobieranie.setKomenda("Ropoczecie pobierania walut, przetwarzanie...");
            try{
                Thread.sleep(100);
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }
            infoPobieranie.setKomenda("Ropoczecie pobierania walut, przetwarzanie...");
            try{
                Thread.sleep(100);
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }
            for(int rok=odRoku;rok<=doRoku;rok++) {
                try {
                    if(rok!=doRoku) {
                        sciezka = (sciezka_dir+rok+".txt");
                    }else sciezka=aktualnyDIR;
                    doc = Jsoup.connect(sciezka).get();
                    this.WydorebnijPliki(doc.text(), ListaTabel, 'c');
                    infoPobieranie.setKomenda("");
                    infoPobieranie.Przetwarzanie=true;
                    infoPobieranie.setKomenda("L. plików XML: "+ListaTabel.size());

                    //  System.out.println("Liczba plików XML: "+ListaTabel.size());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //**************************************************************************** Laczenie z Baza Danych
            try{
                Class.forName("org.sqlite.JDBC");
                polaczenie= DriverManager.getConnection("jdbc:sqlite:GPW.db");
            }catch (Exception e){
                e.printStackTrace();
            }
            java.sql.Statement stat = null;
            try{
                stat = polaczenie.createStatement();
            }catch (SQLException e){
                e.printStackTrace();
            }

            //*****************************************************************************
            //*********************************************************************** Pobieranie Kursów Walut
            infoPobieranie.setKomenda("");
            try{
                Thread.sleep(100);
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }
            int ID=0;
            infoPobieranie.WszystkieStrony=ListaTabel.size();

            for(int x=(ListaTabel.size()-1);x>=0;x--) {
                infoPobieranie.czasStart1Strona=System.currentTimeMillis();
                infoPobieranie.PozostaleStrony=ListaTabel.size()-x;
                this.prog=(double)x/(double)(ListaTabel.size()-1);
                infoPobieranie.Postep=this.prog;
                try {
                    sciezka = (sciezkaXML + ListaTabel.get(x) + ".xml");
                    doc = Jsoup.connect(sciezka).get();
                    Elements ele = doc.select("pozycja");
                    //  System.out.println(sciezka);
                    String rok = "20"+ListaTabel.get(x).substring(5,7);
                    String miesiac = ListaTabel.get(x).substring(7,9);
                    String dzien = ListaTabel.get(x).substring(9,11);

                    String data = rok+"-"+miesiac+"-"+dzien;

                    if(this.porownajDaty(data)){
                        for(int i=0;i<ele.size();i++){
                         Elements poz = ele.get(i).getAllElements();
                            String kodwaluty = poz.select("kod_waluty").text();
                            if(this.isKodWaluty(kodwaluty)){

                                walutySQLKomendy.setData(data);
                                walutySQLKomendy.setKodWaluty(kodwaluty);
                                walutySQLKomendy.setKursKupna(poz.select("kurs_kupna").text().replaceAll(",","."));
                                walutySQLKomendy.setKursSprzedazy(poz.select("kurs_sprzedazy").text().replaceAll(",","."));
                                walutySQLKomendy.setPrzelicznik(poz.select("przelicznik").text());

                                try{
                                    stat.executeUpdate(walutySQLKomendy.getKomendaInsert(++ID));
                                }catch (SQLException e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    }else break;

                } catch (IOException e) {
                    e.printStackTrace();
                }
                infoPobieranie.czasStop1Strona=System.currentTimeMillis();
                infoPobieranie.ObliczCzasy();

               // infoPobieranie.Przetwarzanie=true;
              //  infoPobieranie.setKomenda("Przetworzono plikow: "+x+" z "+ListaTabel.size()+" Pozostały czas:"+infoPobieranie.getCzasPozostaly());
            }


            //**************************************************************************** Rozlaczenie z Baza danych
            try{
                stat.close();
                polaczenie.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
            infoPobieranie.setKomenda("");
            try{
                Thread.sleep(100);
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }
            infoPobieranie.setKomenda("Zakonczono Pobieranie Kursow Walut");
            aktualizacjaKomenda=true;
            zaktualizowanoWaluty=true;
            aktualizacja=false;

            //**************************************************************    zapis do XML
            ustawieniaPlik.ustawienia.DataOstatniejAktualizacjiWaluty=ustawieniaPlik.getAktualnaData();
            ustawieniaPlik.ustawienia.AktualnaBazaWalutNaDzien=true;
            ustawieniaPlik.ZapiszUstawienia();
        }


        //*************************************************************************************************

        private void WydorebnijPliki(String tekst,ObservableList<String> listaTabel,char ZnacznikTabeli){
            int liczbaplikow = (tekst.length()+1)/12;
            for(int i=0;i<liczbaplikow;i++){
                String tmp = new String();
                tmp = tekst.substring(0 + i * 12, 11 + i * 12);
                if(tmp.charAt(0)==ZnacznikTabeli){
                    listaTabel.add(tmp);
                }
            }
        }

        private boolean porownajDaty(String data){
            boolean tmp = false;
            for(int i=0;i<walutyDane.size();i++){
             tmp = (walutyDane.get(i).getDataDo().compareTo(data)<=0?true:false);
                if(tmp)break;
            }
            return tmp;
        }

        private boolean isKodWaluty(String kodwaluty){
            boolean tmp=false;
            for(int i=0;i<walutyDane.size();i++){
                tmp = walutyDane.get(i).getKodWaluty().equals(kodwaluty);
                if(tmp)break;
            }

            return tmp;
        }

    }

    //***********************************************************************  Watek pobieranie calej bazy danych

    private class PobieranieWalutNBPdoBazyDanych implements Runnable{
        private double prog=0;
        private int odRoku,doRoku;
        private String sciezka_dir = new String("http://www.nbp.pl/kursy/xml/dir");
        private String aktualnyDIR = new String(sciezka_dir+".txt");
        private ObservableList<String> ListaTabel = FXCollections.observableArrayList();
        private String sciezka = new String();
        private String sciezkaXML = new String("http://www.nbp.pl/kursy/xml/");
        private Connection polaczenie = null;
        private InfoPobieranie infoPobieranie;
        private WalutySQLKomendy walutySQLKomendy = new WalutySQLKomendy();


        public PobieranieWalutNBPdoBazyDanych(int rokOd, int rokDo,double progres, InfoPobieranie infoPobieranie){
            this.prog=progres;
            this.odRoku=rokOd;
            this.doRoku=rokDo;
            this.infoPobieranie=infoPobieranie;
        }

        public PobieranieWalutNBPdoBazyDanych(int rokOd,int rokDo,InfoPobieranie infoPobieranie){
            this.odRoku=rokOd;
            this.doRoku=rokDo;
            this.infoPobieranie=infoPobieranie;
        }

        @Override
        public void run() {
            if(this.odRoku<2002)System.out.println("Za mała data");

            //************************************************************* Wydorebnianie plikow z znancznikiem c
            //************************************************************* Z wszystkich lat i zapis do ListaTabel
            Document doc;
            infoPobieranie.setKomenda("Ropoczecie pobierania walut, przetwarzanie...");
            try{
                Thread.sleep(100);
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }
            infoPobieranie.setKomenda("Ropoczecie pobierania walut, przetwarzanie...");
            try{
                Thread.sleep(100);
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }
            for(int rok=odRoku;rok<=doRoku;rok++) {
                try {
                    if(rok!=doRoku) {
                        sciezka = (sciezka_dir+rok+".txt");
                    }else sciezka=aktualnyDIR;
                    doc = Jsoup.connect(sciezka).get();
                    this.WydorebnijPliki(doc.text(), ListaTabel, 'c');
                    infoPobieranie.setKomenda("");
                    infoPobieranie.Przetwarzanie=true;
                    infoPobieranie.setKomenda("Liczba plików XML do przetworzenia: "+ListaTabel.size());

                  //  System.out.println("Liczba plików XML: "+ListaTabel.size());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            //**************************************************************************** Laczenie z Baza Danych
            try{
                Class.forName("org.sqlite.JDBC");
                polaczenie= DriverManager.getConnection("jdbc:sqlite:GPW.db");
            }catch (Exception e){
                e.printStackTrace();
            }
            java.sql.Statement stat = null;
            try{
                stat = polaczenie.createStatement();
            }catch (SQLException e){
                e.printStackTrace();
            }


            //*********************************************************************** Pobieranie Kursów Walut
            infoPobieranie.setKomenda("");
            try{
                Thread.sleep(100);
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }
            int ID=0;
            infoPobieranie.WszystkieStrony=ListaTabel.size();

            for(int x=0;x<ListaTabel.size();x++) {
                infoPobieranie.czasStart1Strona=System.currentTimeMillis();
                infoPobieranie.PozostaleStrony=ListaTabel.size()-x;
                this.prog=(double)x/(double)(ListaTabel.size()-1);
                infoPobieranie.Postep=this.prog;
                try {
                    sciezka = (sciezkaXML + ListaTabel.get(x) + ".xml");
                    doc = Jsoup.connect(sciezka).get();
                    Elements ele = doc.select("pozycja");
                    //  System.out.println(sciezka);
                    String rok = "20"+ListaTabel.get(x).substring(5,7);
                    String miesiac = ListaTabel.get(x).substring(7,9);
                    String dzien = ListaTabel.get(x).substring(9,11);

                    String data = rok+"-"+miesiac+"-"+dzien;
                    walutySQLKomendy.setData(data);

                    for (int i = 0; i < ele.size(); i++) {
                        Elements poz = ele.get(i).getAllElements();

                        walutySQLKomendy.setKodWaluty(poz.select("kod_waluty").text());
                        walutySQLKomendy.setKursKupna(poz.select("kurs_kupna").text().replaceAll(",","."));
                        walutySQLKomendy.setKursSprzedazy(poz.select("kurs_sprzedazy").text().replaceAll(",","."));
                        walutySQLKomendy.setPrzelicznik(poz.select("przelicznik").text());


                        try{
                            stat.executeUpdate(walutySQLKomendy.getKomendaInsert(++ID));
                        }catch (SQLException e){
                            e.printStackTrace();
                        }

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                infoPobieranie.czasStop1Strona=System.currentTimeMillis();
                infoPobieranie.ObliczCzasy();
                infoPobieranie.setKomenda("");
                infoPobieranie.Przetwarzanie=true;
                infoPobieranie.setKomenda("Przetworzono plikow: "+x+" z "+ListaTabel.size()+" Pozostały czas:"+infoPobieranie.getCzasPozostaly());
            }


            //**************************************************************************** Rozlaczenie z Baza danych
            try{
                stat.close();
                polaczenie.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
            infoPobieranie.setKomenda("");
            try{
                Thread.sleep(100);
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }
            infoPobieranie.setKomenda("Zakonczono Pobieranie Kursow Walut");
        }


        //*********************************  Wyodrebnanie pliku z NBP po znaczniuku oraz dodawanei do kolejki
        private void WydorebnijPliki(String tekst,ObservableList<String> listaTabel,char ZnacznikTabeli){
            int liczbaplikow = (tekst.length()+1)/12;
            for(int i=0;i<liczbaplikow;i++){
                String tmp = new String();
                tmp = tekst.substring(0 + i * 12, 11 + i * 12);
                if(tmp.charAt(0)==ZnacznikTabeli){
                    listaTabel.add(tmp);
                }
            }
        }

    }

    //********************************************************************            Info o pobieraniu
    public class InfoPobieranie{
        public double Postep =0;
        public double Przetworzone = 0;
        public double WszystkieStrony=0;
        public String Komenda = new String();
        public boolean ustawionoKomende = false;
        public boolean Przetwarzanie = false;
        public double PozostaleStrony =1;
        public long czasStart1Strona =1;
        public long czasStop1Strona =1;
        public double przetwarzanie1Strony =1;
        public double Pozostalyczas=1;
        public int Godziny=0;
        public int Minuty=0;
        public double Sekundy=0;

        public void setKomenda(String kom){
            this.Komenda=kom;
            this.ustawionoKomende=true;
        }

        public String getKomenda(){
            this.ustawionoKomende=false;
            return this.Komenda;
        }

        public void ObliczCzasy(){
            this.przetwarzanie1Strony=((double)czasStop1Strona-(double)czasStart1Strona)/1000;
            this.Pozostalyczas=this.przetwarzanie1Strony*this.PozostaleStrony;
            this.Godziny=(int)this.Pozostalyczas/3600;
            this.Minuty=((int)this.Pozostalyczas/60)-(60*this.Godziny);
            this.Sekundy=this.Pozostalyczas-3600*this.Godziny-60*this.Minuty;
        }

        public String getCzasPozostaly(){
            if(this.Godziny>=1){
                return new String("h:"+this.Godziny+" m:"+this.Minuty+" s:"+this.Sekundy);
            }else if(this.Minuty>=1){
                return new String("m:"+this.Minuty+" s:"+this.Sekundy);
            }else return new String("s:"+(int)this.Sekundy);
        }
    }

    //*************************************************************************          Komendy SQL

    private class WalutySQLKomendy{
        private String data = new String();
        private String KodWaluty = new String();
        private String KursKupna = new String();
        private String KursSprzedazy = new String();
        private String Kraj = new String();
        private String Przelicznik = new String();
        private String ID = new String();
        private String NazwaWaluty = new String();
        private String KodUPS = new String();
        private String KodIATA = new String();
        //**************************************************************************************


        public String getNazwaWaluty() {
            return NazwaWaluty;
        }

        public String getKodIATA() {
            return KodIATA;
        }

        public String getKodUPS() {
            return KodUPS;
        }

        public void setKodIATA(String kodIATA) {
            KodIATA = kodIATA;
        }

        public void setKodUPS(String kodUPS) {
            KodUPS = kodUPS;
        }

        public void setNazwaWaluty(String nazwaWaluty) {
            NazwaWaluty = nazwaWaluty;
        }

        public String getData() {
            return data;
        }

        public String getKodWaluty() {
            return KodWaluty;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getID() {
            return ID;
        }

        public String getKraj() {
            return Kraj;
        }

        public void setKodWaluty(String kodWaluty) {
            KodWaluty = kodWaluty;
        }

        public String getKursKupna() {
            return KursKupna;
        }

        public String getKursSprzedazy() {
            return KursSprzedazy;
        }

        public String getPrzelicznik() {
            return Przelicznik;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public void setKraj(String kraj) {
            Kraj = kraj;
        }

        public void setKursKupna(String kursKupna) {
            KursKupna = kursKupna;
        }

        public void setKursSprzedazy(String kursSprzedazy) {
            KursSprzedazy = kursSprzedazy;
        }

        public void setPrzelicznik(String przelicznik) {
            Przelicznik = przelicznik;
        }

        //*********************************************************************************** KOMENDT SQL

        public String getKomendaInsert(int id){
            return new String("INSERT INTO WALUTY VALUES( "+id+", '"+this.KodWaluty+"', "+this.KursKupna+", "+
                            this.KursSprzedazy+", "+this.Przelicznik+", '"+this.data+"');");
        }

        public String getKomendaIloscWalut(){
            return new String("SELECT COUNT(DISTINCT KOD_WALUTY) FROM WALUTY;");
        }

        public String getMaxData(){
            return new String("SELECT MAX(DATA) FROM WALUTY WHERE KOD_WALUTY='"+this.KodWaluty+"';");
        }

        public String getMinData(){
            return new String("SELECT MIN(DATA) FROM WALUTY WHERE KOD_WALUTY='"+this.KodWaluty+"';");
        }

        public String getDaneArchiwumByData(String data){
            return new String("SELECT * FROM WALUTY WHERE KOD_WALUTY='"+this.KodWaluty+"' AND DATA='"+data+"';");
        }

        public String getKodyWalut(){
            return new String("SELECT DISTINCT KOD_WALUTY FROM WALUTY;");
        }

        public String getKomendaInsertKodyWalut(int id){
            return new String("INSERT INTO KODYWALUT VALUES ("+id+", '"+this.Kraj+"', '"+this.KodUPS+"', '"+
                               this.KodIATA+"', '"+ this.KodWaluty+"', '"+this.NazwaWaluty+"');");
        }

        public String getNazwaWalutyByKodWaluty(){
            return new String("SELECT DISTINCT NAZWA_WALUTY FROM KODYWALUT WHERE KOD_WALUTY ='"+this.KodWaluty+"';");
        }
    }

    //**********************************************************************  Info podstawowoe oraz Tabele
    public class WalutyDane{
        private String DataOd = new String();
        private String DataDo = new String();
        private String KodWaluty = new String();
        private String NazwaWaluty = new String("brak danych");
        private String KursKupna = new String();
        private String KursSprzedazy = new String();
        private String Przelicznik = new String();


        public WalutyDane(){}

        public WalutyDane(String kodWaluty){
            this.KodWaluty=kodWaluty;
        }

        //**********************************************************************************

        public void setPrzelicznik(String przelicznik) {
            Przelicznik = przelicznik;
        }

        public void setDataDo(String dataDo) {
            DataDo = dataDo;
        }

        public void setKursKupna(String kursKupna) {
            KursKupna = kursKupna;
        }

        public void setKursSprzedazy(String kursSprzedazy) {
            KursSprzedazy = kursSprzedazy;
        }

        public void setDataOd(String dataOd) {
            DataOd = dataOd;
        }

        public void setKodWaluty(String kodWaluty) {
            KodWaluty = kodWaluty;
        }

        public void setNazwaWaluty(String nazwaWaluty) {
            NazwaWaluty = nazwaWaluty;
        }

        public String getDataDo() {
            return DataDo;
        }

        public String getDataOd() {
            return DataOd;
        }

        public String getKodWaluty() {
            return KodWaluty;
        }

        public String getKursKupna() {
            return KursKupna;
        }

        public String getKursSprzedazy() {
            return KursSprzedazy;
        }

        public String getNazwaWaluty() {
            return NazwaWaluty;
        }

        public String getPrzelicznik() {
            return Przelicznik;
        }

    }

}
