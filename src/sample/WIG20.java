package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jsoup.Jsoup;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.omg.PortableInterceptor.Interceptor;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.print.Doc;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
import javax.sound.midi.MidiDevice;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


/**
 * Created by patryk on 08.08.16.
 */
public class WIG20 {

    public static boolean aktualizacja = true;
    public static boolean zaktualizowanoWIG20 = false;
    public static boolean aktualizacjaKomenda = false;
    private String SkladWig20URL = new String("http://www.bankier.pl/inwestowanie/profile/quote.html?symbol=WIG20");
    private String NazwaPlikuWIG20= new String("SkladWIG20.xml");
    public InfoPobieranie infoPobieranie = new InfoPobieranie();
    private WIG20KomendySQL wig20KomendySQL = new WIG20KomendySQL();

    private ObservableList<SkladWIG20>  WIG20Lista = FXCollections.observableArrayList();
    public ObservableList<WIG20PodstawoweInfo> WIG20ListaInfo = FXCollections.observableArrayList();
    public ObservableList<WIG20Tabela> wig20Tabela = FXCollections.observableArrayList();
    //********************************************************************  POBIERANIE WARTOSCI Z LISTY WIG20

    public ObservableList<String> getWIG20Ticker(){
        ObservableList<String> ListaNazw = FXCollections.observableArrayList();
        for(int i=0;i<WIG20Lista.size();i++)ListaNazw.add(WIG20Lista.get(i).getTicker());
        return ListaNazw;
    }

    public ObservableList<String> getWIG20Nazwy(){
        ObservableList<String> ListaNazw = FXCollections.observableArrayList();
        for (int i=0;i<WIG20Lista.size();i++)ListaNazw.add(WIG20Lista.get(i).getNazwa());
        return ListaNazw;
    }

    public ObservableList<String> getWIG20PelnaNazwa(){
        ObservableList<String> ListaNazw = FXCollections.observableArrayList();
        for(int i=0;i<WIG20Lista.size();i++)ListaNazw.add(WIG20Lista.get(i).getPelnaNazwa());
        return ListaNazw;
    }

    public ObservableList<String> getDataOdArchiwum(){
        ObservableList<String> lista = FXCollections.observableArrayList();
        for (int i=0;i<WIG20ListaInfo.size();i++)lista.add(WIG20ListaInfo.get(i).getDataOdArchiwum());
        return lista;
    }

    public ObservableList<String> getDataDoArchiwum(){
        ObservableList<String> list = FXCollections.observableArrayList();
        for(int i=0;i<WIG20ListaInfo.size();i++)list.add(WIG20ListaInfo.get(i).getDataDoArchiwum());
        return list;
    }

    public boolean getAktualizacjaStatus(){
        return aktualizacja;
    }
    //*********************************************************************     Wczytywanie do Listy z XML

    public void WczytajWIG20(){
        try{
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuiler = docFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = docBuiler.parse(this.NazwaPlikuWIG20);

            NodeList ListaSpolek = doc.getElementsByTagName("WIG20").item(0).getChildNodes();

            for(int i=0;i<ListaSpolek.getLength();i++){
              this.WIG20Lista.add(new SkladWIG20());
                this.WIG20Lista.get(i).setTicker(ListaSpolek.item(i).getChildNodes().item(0).getTextContent());
                this.WIG20Lista.get(i).setNazwa(ListaSpolek.item(i).getChildNodes().item(1).getTextContent());
                this.WIG20Lista.get(i).setPelnaNazwa(ListaSpolek.item(i).getChildNodes().item(2).getTextContent());
                this.WIG20Lista.get(i).setLink(ListaSpolek.item(i).getChildNodes().item(3).getTextContent());
                this.WIG20Lista.get(i).setLinkArchiwum(ListaSpolek.item(i).getChildNodes().item(4).getTextContent());
            }

        }catch (ParserConfigurationException pce){
            pce.printStackTrace();
        }catch (SAXException sae){
            sae.printStackTrace();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }

    }

    //***********************************************************************  Pobieranie spolek z URL i zapis XML

    public void PobierzSpolkiWIG20(){
        Document doc = null;
        try {
            doc = Jsoup.connect(SkladWig20URL).get();
        }catch (IOException e){
            e.printStackTrace();
        }

        try {
            //XML
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            org.w3c.dom.Document docxml = docBuilder.newDocument();
            org.w3c.dom.Element rootElement = docxml.createElement("WIG20");
            docxml.appendChild(rootElement);

            //JSOUP HTML
            Element tabela = doc.getElementsByTag("table").last();
            Elements elements = tabela.getElementsByTag("tr");
            int i = 1;
            for (Element element : elements) {
                Elements dane = element.getElementsByTag("td");
                if (dane.size() != 0) {
                //    System.out.println("ID: " + i++);
                //    System.out.println("link:\t" + "http://www.bankier.pl" + dane.get(0).getElementsByTag("a").attr("href"));
                //    System.out.println("Pelna Nazwa:" + dane.get(0).getElementsByTag("a").attr("title"));
                //    System.out.println("Nazwa: " + dane.get(0).text());
                  //  System.out.println("Ticker: " + dane.get(1).text());

                    org.w3c.dom.Element spolka = docxml.createElement(dane.get(1).text());
                    rootElement.appendChild(spolka);

                    org.w3c.dom.Element Ticker = docxml.createElement("Ticker");
                    Ticker.appendChild(docxml.createTextNode(dane.get(1).text()));
                    spolka.appendChild(Ticker);

                    org.w3c.dom.Element nazwa = docxml.createElement("Nazwa");
                    nazwa.appendChild(docxml.createTextNode(dane.get(0).text()));
                    spolka.appendChild(nazwa);

                    org.w3c.dom.Element pelnanazwa = docxml.createElement("PełnaNazwa");
                    pelnanazwa.appendChild(docxml.createTextNode(dane.get(0).getElementsByTag("a").attr("title")));
                    spolka.appendChild(pelnanazwa);

                    org.w3c.dom.Element link = docxml.createElement("Link");
                    link.appendChild(docxml.createTextNode("http://www.bankier.pl" + dane.get(0).getElementsByTag("a").attr("href")));
                    spolka.appendChild(link);

                    org.w3c.dom.Element linkarchiwum = docxml.createElement("LinkArchiwum");
                    linkarchiwum.appendChild(docxml.createTextNode("http://www.biznesradar.pl/notowania-historyczne/"+dane.get(0).text()));
                    spolka.appendChild(linkarchiwum);

                }
            }


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(docxml);
            StreamResult result = new StreamResult(new File(this.NazwaPlikuWIG20));

            transformer.transform(source, result);


        }catch (ParserConfigurationException pce){
            pce.printStackTrace();
        }catch (TransformerException tfe){
            tfe.printStackTrace();
        }catch (NullPointerException npe){
            npe.printStackTrace();
        }
    }

    public String getSkladWig20URL(){
        return this.SkladWig20URL;
    }

    public void setSkladWig20URL(String adresWWW){
        this.SkladWig20URL=adresWWW;
    }

    public void PobierzArchiwumWIG20doBazyDanych(){


        PobieranieWig20BazaDanych pobieranieWig20BazaDanych = new PobieranieWig20BazaDanych(WIG20Lista,infoPobieranie);
        Thread watekPobierania = new Thread(pobieranieWig20BazaDanych);
        watekPobierania.start();


    }

    public void WczytajPodstawoweInfoWIG20(){

        for(int i=0;i<WIG20Lista.size();i++){
            WIG20ListaInfo.add(new WIG20PodstawoweInfo());
            WIG20ListaInfo.get(i).setLinkArchiwum(WIG20Lista.get(i).getLinkArchiwum());
            WIG20ListaInfo.get(i).setLink(WIG20Lista.get(i).getLink());
            WIG20ListaInfo.get(i).setTicker(WIG20Lista.get(i).getTicker());
            WIG20ListaInfo.get(i).setNazwa(WIG20Lista.get(i).getNazwa());
            WIG20ListaInfo.get(i).setPelnaNazwa(WIG20Lista.get(i).getPelnaNazwa());
        }


        //**************************************************************      Wczytywanie Info z bazy danych
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

        //*********************************************************         Wczytywanie najmlodszej daty
        for(int i =0;i<WIG20ListaInfo.size();i++) {
            try {
                ResultSet rs = stat.executeQuery(wig20KomendySQL.getSQLWIG20MaxRokTicker(WIG20ListaInfo.get(i).getTicker()));
                while (rs.next()) {
                    WIG20ListaInfo.get(i).setDataDoArchiwum(rs.getString("MAX(DATA)"));
                   // System.out.println("Ticker: "+WIG20ListaInfo.get(i).getTicker()+" max data:"+WIG20ListaInfo.get(i).getDataDoArchiwum());
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //*************************************************************         Wczytywania nasjtarszej daty
        for(int i =0;i<WIG20ListaInfo.size();i++) {
            try {
                ResultSet rs = stat.executeQuery(wig20KomendySQL.getSQLWIG20MinRokTicker(WIG20ListaInfo.get(i).getTicker()));
                while (rs.next()) {
                    WIG20ListaInfo.get(i).setDataOdArchiwum((rs.getString("MIN(DATA)")));
                    //System.out.println("Ticker: "+WIG20ListaInfo.get(i).getTicker()+" data min: "+WIG20ListaInfo.get(i).getDataOdArchiwum());
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //****************************************************************  Wczytywanie najswiezszych danych
        for(int i=0;i<WIG20ListaInfo.size();i++){
            wig20KomendySQL.setTicker(WIG20ListaInfo.get(i).getTicker());
            wig20KomendySQL.data=WIG20ListaInfo.get(i).getDataDoArchiwum();
            try {
                ResultSet rs = stat.executeQuery(wig20KomendySQL.getSQLWIG20NotowaniaOstatnie());
                while (rs.next()) {
                   // WIG20ListaInfo.get(i).setDataOdArchiwum((rs.getString("MIN(DATA)")));
                    //System.out.println("Ticker: "+WIG20ListaInfo.get(i).getTicker()+" data min: "+WIG20ListaInfo.get(i).getDataOdArchiwum());
                    WIG20ListaInfo.get(i).setMaxKurs(rs.getString("MAXKURS"));
                    WIG20ListaInfo.get(i).setMinKurs(rs.getString("MINKURS"));
                    WIG20ListaInfo.get(i).setOtwarcie(rs.getString("OTWARCIE"));
                    WIG20ListaInfo.get(i).setZamkniecie(rs.getString("ZAMKNIECIE"));
                    WIG20ListaInfo.get(i).setSredniKurs();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        try{
            stat.close();
            polaczenie.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        //************************************************        Uzupelnienei kolekcji do Tabler View
        for(int i =0; i<WIG20ListaInfo.size();i++){
            wig20Tabela.add(new WIG20Tabela());
            wig20Tabela.get(i).setTicker(WIG20ListaInfo.get(i).getTicker());
            wig20Tabela.get(i).setLink(WIG20ListaInfo.get(i).getLinkArchiwum());
            wig20Tabela.get(i).setSpolka(WIG20ListaInfo.get(i).getNazwa());
            wig20Tabela.get(i).setDataDo(WIG20ListaInfo.get(i).getDataDoArchiwum());
            wig20Tabela.get(i).setDataOd(WIG20ListaInfo.get(i).getDataOdArchiwum());
            wig20Tabela.get(i).setNazwa(WIG20ListaInfo.get(i).getNazwa());
            wig20Tabela.get(i).setPelnaNazwa(WIG20ListaInfo.get(i).getPelnaNazwa());
            wig20Tabela.get(i).setID(Integer.toString(i+1));
            wig20Tabela.get(i).setSredniKurs(WIG20ListaInfo.get(i).getSredniKurs());
        }
    }

    public void AktualizujBazeDanych(){
        aktualizacja=false;
        AktualizacjaWig20 aktualizacjaWig20 = new AktualizacjaWig20(WIG20ListaInfo,infoPobieranie);
        Thread watekAktualizacji = new Thread(aktualizacjaWig20);
        watekAktualizacji.start();
    }

    //****************************************************************      Klasa WIG20 XML

    private class SkladWIG20{
        public String Ticker = new String();
        public String Nazwa = new String();
        public String PelnaNazwa = new String();
        public String Link = new String();
        public String LinkArchiwum = new String();


        public SkladWIG20(){}
        //*******************************************************//
        public void setTicker(String ticker){
            this.Ticker=ticker;
        }

        public void setNazwa(String nazwa){
            this.Nazwa = nazwa;
        }

        public void setPelnaNazwa(String pelnaNazwa){
            this.PelnaNazwa=pelnaNazwa;
        }

        public void setLink(String link){
            this.Link=link;
        }

        public void setLinkArchiwum(String linkArchiwum){
            this.LinkArchiwum=linkArchiwum;
        }

        //*************************************************************//

        public String getTicker(){
            return this.Ticker;
        }

        public String getNazwa(){
            return this.Nazwa;
        }

        public String getPelnaNazwa(){
            return this.PelnaNazwa;
        }

        public String getLink(){
            return this.Link;
        }

        public String getLinkArchiwum(){return this.LinkArchiwum;}

    }

    private class WIG20PodstawoweInfo extends SkladWIG20{

        private String DataOdArchiwum = new String();
        private String DataDoArchiwum = new String();
        private String MaxKurs = new String();
        private String MinKurs = new String();
        private String Otwarcie = new String();
        private String Zamkniecie = new String();
        private String SredniKurs = new String();



        public WIG20PodstawoweInfo(){
            super();
        }


        //*******************************************

        public void setSredniKurs(){
           double max = Double.valueOf(this.MaxKurs);
           double min = Double.valueOf(this.MinKurs);
            double srednia = (max+min)/2;
            this.SredniKurs = new String(Double.toString(srednia));
        }

        public void setDataDoArchiwum(String dataDo){
            this.DataDoArchiwum = dataDo;
        }

        public void setDataOdArchiwum(String dataOd){
            this.DataOdArchiwum=dataOd;
        }

        public void setMaxKurs(String maxKurs) {
            MaxKurs = maxKurs;
        }

        public void setMinKurs(String minKurs) {
            MinKurs = minKurs;
        }

        public void setOtwarcie(String otwarcie) {
            Otwarcie = otwarcie;
        }

        public void setZamkniecie(String zamkniecie) {
            Zamkniecie = zamkniecie;
        }

        public String getMaxKurs() {
            return MaxKurs;
        }

        public String getMinKurs() {
            return MinKurs;
        }

        public String getOtwarcie() {
            return Otwarcie;
        }

        public String getZamkniecie() {
            return Zamkniecie;
        }

        public String getSredniKurs(){return SredniKurs;}

        public String getDataOdArchiwum(){
            return this.DataOdArchiwum;
        }

        public String getDataDoArchiwum(){
            return this.DataDoArchiwum;
        }

    }

    //****************************************************************      Klasa Tablea WIG20

    public class WIG20Tabela{
        private String spolka = new String();
        private String Ticker = new String();
        private String DataOd = new String();
        private String DataDo = new String();
        private String Link = new String();
        private String Nazwa = new String();
        private String PelnaNazwa = new String();
        private String ID = new String();
        private String SredniKurs = new String();

        public WIG20Tabela(){}

        //******************************************************


        public String getSredniKurs() {
            return SredniKurs;
        }

        public void setSredniKurs(String sredniKurs) {
            SredniKurs = sredniKurs;
        }

        public String getID() {
            return ID;
        }

        public String getNazwa() {
            return Nazwa;
        }

        public String getPelnaNazwa() {
            return PelnaNazwa;
        }

        public String getDataDo() {
            return DataDo;
        }

        public String getDataOd() {
            return DataOd;
        }

        public String getLink() {
            return Link;
        }

        public String getSpolka() {
            return spolka;
        }

        public String getTicker() {
            return Ticker;
        }

        public void setDataDo(String dataDo) {
            DataDo = dataDo;
        }

        public void setDataOd(String dataOd) {
            DataOd = dataOd;
        }

        public void setLink(String link) {
            Link = link;
        }

        public void setSpolka(String spolka) {
            this.spolka = spolka;
        }

        public void setTicker(String ticker) {
            Ticker = ticker;
        }

        public void setNazwa(String nazwa) {
            Nazwa = nazwa;
        }

        public void setPelnaNazwa(String pelnaNazwa) {
            PelnaNazwa = pelnaNazwa;
        }

        public void setID(String ID) {
            this.ID = ID;
        }
    }

    //****************************************************************         Klasa komend i danych SQL

    private class WIG20KomendySQL{
        public String data = new String();
        private String otwarcie = new String();
        private String MaxKurs = new String();
        private String MinKurs = new String();
        private String zamkniecie = new String();
        private String Wolumen = new String();
        private String Obrot = new String();
        private String Ticker = new String();
        private String Nazwa = new String();

        //*******************************************************       Wstawianie danych

        public void setData(String data){
            String[] tmp = data.split(Pattern.quote("."));
            this.data = new String(tmp[2]+"-"+tmp[1]+"-"+tmp[0]);
        }

        public void setOtwarcie(String otwarcie){
            String tmp =new String(otwarcie.replaceAll(",",Pattern.quote(".")));
            this.otwarcie=this.usuwanieBialychZnakow(tmp);
        }

        public void setMaxKurs(String maxKurs){
            String tmp = new String(maxKurs.replaceAll(",",Pattern.quote(".")));
            this.MaxKurs = this.usuwanieBialychZnakow(tmp);
        }

        public void setMinKurs(String minKurs){
            String tmp =new String(minKurs.replaceAll(",",Pattern.quote(".")));
            this.MinKurs = this.usuwanieBialychZnakow(tmp);
        }

        public void setZamkniecie(String zamkniecie){
            String tmp=new String(zamkniecie.replaceAll(",",Pattern.quote(".")));
            this.zamkniecie = this.usuwanieBialychZnakow(tmp);
        }

        public void setWolumen(String wolumen){
            this.Wolumen = this.usuwanieBialychZnakow(wolumen);
        }

        public void setObrot(String obrot){
            this.Obrot=this.usuwanieBialychZnakow(obrot);
        }

        public void setTicker(String ticker){
            this.Ticker = ticker;
        }

        public void setNazwa(String nazwa){
            this.Nazwa=nazwa;
        }

        private String usuwanieBialychZnakow(String tekst){
            String wol = new String(tekst);
            int pom=0;
            int [] tab= new int[wol.length()];
            String wol2 = new String("");
            for(int k=0;k<wol.length();k++){

                if(Character.isDigit(wol.charAt(k))) {
                    tab[pom] = Character.getNumericValue(wol.charAt(k));
                    wol2 = (wol2 + tab[pom]);
                    pom++;
                } else if(wol.charAt(k)=='.'){
                    wol2 = (wol2 +wol.charAt(k));
                    pom++;
                }

            }
            return wol2;
        }

        //****************************************************      Odczyt danych

        public String getData(){return this.data;}

        public String getOtwarcie(){return this.otwarcie;}

        public String getMaxKurs(){return this.MaxKurs;}

        public String getMinKurs(){return this.MinKurs;}

        public String getZamkniecie(){return this.zamkniecie;}

        public String getWolumen(){return this.Wolumen;}

        public String getObrot(){return this.Obrot;}

        public String getTicker(){return this.Ticker;}

        public String getNazwa(){return this.Nazwa;}


        //**********************************************************************        SQL

        public String getSQLWIG20SpolkiKomenda(int id){
            return new String("INSERT INTO WIG20 VALUES( "+id+", '"+this.Ticker+"', '"+this.Nazwa+"' );");
        }

        public String getSQLWIG20NotowaniaKomenda(int id){
            return new String("INSERT INTO NOTOWANIA VALUES ( "+id+", '"+ this.Ticker+"', '"+this.data+"', "+
                            this.otwarcie+", "+this.zamkniecie + ", "+this.MaxKurs+", "+this.MinKurs +","+
                            this.Wolumen + ", "+this.Obrot+ ");");
        }

        public String getSQLWIG20MinRokTicker(String Ticker){
            return new String("SELECT MIN(DATA) FROM NOTOWANIA WHERE TICKER = '"+Ticker+"';");
        }

        public String getSQLWIG20MaxRokTicker(String Ticker){
            return new String("SELECT MAX(DATA) FROM NOTOWANIA WHERE TICKER = '"+Ticker+"';");
        }

        public String getSQLWIG20NotowaniaOstatnie(){
            return new String("SELECT * FROM NOTOWANIA WHERE TICKER ='"+this.Ticker+"' AND DATA >='"+this.getData()+"';");
        }
    }

    //**********************************************************            Info o pobieraniu
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
            }else return new String("s:"+this.Sekundy);
        }
    }

    //********************************************************* Watek pobierania danych i zapis do bazy danych

    private class PobieranieWig20BazaDanych implements Runnable{

        private ObservableList<SkladWIG20> lista = FXCollections.observableArrayList();
        private InfoPobieranie infoPobieranie;
        public PobieranieWig20BazaDanych(ObservableList<SkladWIG20> ListaSpolek, InfoPobieranie infoPobieranie){
            this.lista=ListaSpolek;
            this.infoPobieranie=infoPobieranie;
        }


        @Override
        public void run() {


            WIG20KomendySQL wig20KomendySQL = new WIG20KomendySQL();
            //****************************************                      LACZENIE Z BAZA DANYCH
            Connection polaczenie = null;
            try{
                Class.forName("org.sqlite.JDBC");
                polaczenie= DriverManager.getConnection("jdbc:sqlite:GPW.db");
                System.out.println("polaczono z baza");
                infoPobieranie.setKomenda("Polaczono z baza danych");

            }catch (Exception e){
                e.printStackTrace();
                System.out.println("nie polaczono z baza");
                infoPobieranie.setKomenda("nie polaczono z baza danych");
            }
            java.sql.Statement stat = null;
            try{
                stat=polaczenie.createStatement();
            }catch (SQLException e){
                e.printStackTrace();
            }



            //***************************************    Obliczanie ilosci stron do pobrania oraz poprawa linków
            infoPobieranie.setKomenda("Rozpoczecie obliczania stron do pobrania");
            double IloscWszystkichStron =0;
            double Postep=0;
            double Przetworzone =0;
            int ID_SQL =1;
            for(int i=0;i<WIG20Lista.size();i++){
                // System.out.println("Adres strony: "+WIG20Lista.get(i).getLinkArchiwum());
                Document doc = null;
                try {
                    doc = Jsoup.connect(WIG20Lista.get(i).getLinkArchiwum()).get();
                }catch (IOException e){
                    e.printStackTrace();
                }

                Elements elementy = doc.select("a.pages_pos");
                IloscWszystkichStron += Integer.valueOf(elementy.get(elementy.size()-1).text());
                infoPobieranie.setKomenda("");
                infoPobieranie.Przetwarzanie=true;
                infoPobieranie.setKomenda("Obliczone strony: "+IloscWszystkichStron);
                //**************************************************************        ZAPIS DO BAZY DANYCH
                wig20KomendySQL.setTicker(WIG20Lista.get(i).getTicker());
                wig20KomendySQL.setNazwa(WIG20Lista.get(i).getNazwa());
                WIG20Lista.get(i).setLinkArchiwum(doc.location());

                try {
                    stat.executeUpdate(wig20KomendySQL.getSQLWIG20SpolkiKomenda(i+1));

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            infoPobieranie.WszystkieStrony=IloscWszystkichStron;
            infoPobieranie.setKomenda("Rozpoczecie Przetwarzania stron: "+IloscWszystkichStron);
            infoPobieranie.PozostaleStrony=IloscWszystkichStron;

            //****************************************************************                    Pobieranie stron
            for(int i=0;i<WIG20Lista.size();i++){

                infoPobieranie.setKomenda("Przetwarzanie WIG20: "+WIG20Lista.get(i).getNazwa()+" "+(i+1)+" z: "+WIG20Lista.size()+" Pozostały czas: "+infoPobieranie.getCzasPozostaly());
                try {
                    Thread.sleep(100);
                }catch (InterruptedException ie){
                    ie.printStackTrace();
                }
                infoPobieranie.setKomenda(" ");
                Document doc = null;
                try {
                    //wczytanie strony
                    doc = Jsoup.connect(WIG20Lista.get(i).getLinkArchiwum()).get();
                }catch (IOException e){
                    e.printStackTrace();
                }

                //ustawienie nazyw indeksy
                wig20KomendySQL.setTicker(WIG20Lista.get(i).getTicker());
                wig20KomendySQL.setNazwa(WIG20Lista.get(i).getNazwa());

                //przeszukiwanie strony
                Elements elementy = doc.select("a.pages_pos");
                int IloscStronAktualna = Integer.valueOf(elementy.get(elementy.size()-1).text());

                for(int x=IloscStronAktualna;x>=1;x--){
                    String AktualnaSciezka = new String(WIG20Lista.get(i).getLinkArchiwum()+","+x);
                    try {
                        infoPobieranie.czasStart1Strona=System.currentTimeMillis();
                        doc = Jsoup.connect(AktualnaSciezka).get();

                        Element tabela = doc.select("table.qTableFull").first();
                        Elements DaneNaStronie = tabela.getElementsByTag("tr");

                        Przetworzone++;
                        infoPobieranie.PozostaleStrony--;
                        Postep=(double)Przetworzone/IloscWszystkichStron;
                        infoPobieranie.Postep=Postep;
                        infoPobieranie.Przetworzone=Przetworzone;
                        int daneNaStronie =0;
                        for(int z=DaneNaStronie.size()-1;z>=1;z--){

                            Elements Dane = DaneNaStronie.get(z).getElementsByTag("td");


                            wig20KomendySQL.setData(Dane.get(0).text());
                            wig20KomendySQL.setOtwarcie(Dane.get(1).text());
                            wig20KomendySQL.setMaxKurs(Dane.get(2).text());
                            wig20KomendySQL.setMinKurs(Dane.get(3).text());
                            wig20KomendySQL.setZamkniecie(Dane.get(4).text());
                            wig20KomendySQL.setWolumen(Dane.get(5).text());
                            wig20KomendySQL.setObrot(Dane.get(6).text());
                            try {
                                stat.executeUpdate(wig20KomendySQL.getSQLWIG20NotowaniaKomenda(ID_SQL++));

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            infoPobieranie.Przetwarzanie=true;
                            infoPobieranie.setKomenda("Przetwarzanie: "+Przetworzone+" z: "+IloscWszystkichStron+" Dane na stronie: "+(++daneNaStronie)+" z: "+(DaneNaStronie.size()-1)+" Czas:"+infoPobieranie.getCzasPozostaly());
                           // System.out.println("Postep: "+Postep+"%"+"\tPrzetworzone :"+Przetworzone+" z:"+IloscWszystkichStron);
                            //System.out.println("Data :"+wig20KomendySQL.getData());

                        }
                    }catch (IOException e){
                        System.out.println("Błąd przy sciezce: "+AktualnaSciezka);
                        infoPobieranie.setKomenda("Blad otwarcia sciezki: "+AktualnaSciezka);
                        x++;
                        e.printStackTrace();
                    }
                    infoPobieranie.czasStop1Strona=System.currentTimeMillis();
                    infoPobieranie.ObliczCzasy();
                }

            }


            //****************************************************************KONCZENIE POLACZENIA Z BAZA DANYCH

            try{
                stat.close();
                polaczenie.close();

            }catch (SQLException e){
                e.printStackTrace();
            }
            zaktualizowanoWIG20=true;
            infoPobieranie.setKomenda("Zakonczono Pobieranie");

        }
    }

    //************************************************************Watek aktualizacji bazy danych

    private class AktualizacjaWig20 implements Runnable{

        private ObservableList<WIG20PodstawoweInfo> lista = FXCollections.observableArrayList();
        private InfoPobieranie infoPobieranie = new InfoPobieranie();
        private UstawieniaPlik ustawieniaPlik = new UstawieniaPlik();


        public AktualizacjaWig20(ObservableList<WIG20PodstawoweInfo> lista, InfoPobieranie infoPobieranie){
            this.lista=lista;
            this.infoPobieranie=infoPobieranie;
        }

        public AktualizacjaWig20(ObservableList<WIG20PodstawoweInfo> lista){
            this.lista=lista;
        }


        @Override
        public void run() {
            WIG20KomendySQL wig20KomendySQL = new WIG20KomendySQL();
            int iloscDanych =0;
            //****************************************                      LACZENIE Z BAZA DANYCH
            Connection polaczenie = null;
            try{
                Class.forName("org.sqlite.JDBC");
                polaczenie= DriverManager.getConnection("jdbc:sqlite:GPW.db");
           //     System.out.println("polaczono z baza");
           //     infoPobieranie.setKomenda("Polaczono z baza danych");

            }catch (Exception e){
                e.printStackTrace();
        //        System.out.println("nie polaczono z baza");
                infoPobieranie.setKomenda("nie polaczono z baza danych");
            }


            java.sql.Statement stat = null;
            try{
                stat=polaczenie.createStatement();
            }catch (SQLException e){
                e.printStackTrace();
            }

            //*************************************************************** Sprawdzanie dat i aktualizacja dal kazdej spolki
            double postemp=1;
            int x =1;
            boolean znalezionoNaStronie = false;

            infoPobieranie.setKomenda("Rozpoczeto aktualizacje...");
            try{
                Thread.sleep(100);
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }

            for(int i=0; i<lista.size();i++){
                //***************************************************** Sprawdzanie 1 strony
                wig20KomendySQL.setTicker(lista.get(i).getTicker());



                Document doc = null;
                try {
                    //wczytanie strony
                    doc = Jsoup.connect(lista.get(i).getLinkArchiwum()+","+x).get();
                }catch (IOException e){
                    e.printStackTrace();
                }

                Element tabela = doc.select("table.qTableFull").first();
                Elements DaneNaStronie = tabela.getElementsByTag("tr");
                infoPobieranie.setKomenda("");
                infoPobieranie.Przetwarzanie=true;
                infoPobieranie.setKomenda("Sprawdzanie Danych: "+lista.get(i).getTicker()+" "+i+" z "+lista.size());
               // infoPobieranie.Postep=(double)i/19;
                for(int z=1;z<DaneNaStronie.size();z++) {

                    Elements Dane = DaneNaStronie.get(z).getElementsByTag("td");
                    wig20KomendySQL.setData(Dane.get(0).text());
                    if ((wig20KomendySQL.getData().compareTo(lista.get(i).getDataDoArchiwum()) == 0)){
                        iloscDanych=z-1;
                        x=1;
                        znalezionoNaStronie=true;
       //                 System.out.println("Spolka: "+lista.get(i).getLinkArchiwum()+"\tDanych do aktualizacji:"+iloscDanych);
                        break;
                    }else znalezionoNaStronie=false;
                }
                if(znalezionoNaStronie){
    //                System.out.println("ticker: "+lista.get(i).getTicker());
                    int y=0;
                    for(int z=iloscDanych;z>=1;z--){
                        Elements Dane = DaneNaStronie.get(z).getElementsByTag("td");
                        infoPobieranie.Postep=(((double) i/20)+(1.0/20.0)*(double)(y++)/(iloscDanych-1));
                        infoPobieranie.setKomenda("");
                        infoPobieranie.Przetwarzanie=true;
                        infoPobieranie.setKomenda("Spolki: "+lista.get(i).Ticker+ " "+(i+1)+ " z "+lista.size()+" "+infoPobieranie.Postep*100+"%");
                        wig20KomendySQL.setData(Dane.get(0).text());
                        wig20KomendySQL.setOtwarcie(Dane.get(1).text());
                        wig20KomendySQL.setMaxKurs(Dane.get(2).text());
                        wig20KomendySQL.setMinKurs(Dane.get(3).text());
                        wig20KomendySQL.setZamkniecie(Dane.get(4).text());
                        wig20KomendySQL.setWolumen(Dane.get(5).text());
                        wig20KomendySQL.setObrot(Dane.get(6).text());
                        try {
                            stat.executeUpdate(wig20KomendySQL.getSQLWIG20NotowaniaKomenda(z));

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }else {
                    x++;
                    iloscDanych=0;
                    i--;
                }
               // infoPobieranie.Postep=1.0;
               // infoPobieranie.Postep=((double)i/19);
                    /*
                    wig20KomendySQL.setData(Dane.get(0).text());
                    wig20KomendySQL.setOtwarcie(Dane.get(1).text());
                    wig20KomendySQL.setMaxKurs(Dane.get(2).text());
                    wig20KomendySQL.setMinKurs(Dane.get(3).text());
                    wig20KomendySQL.setZamkniecie(Dane.get(4).text());
                    wig20KomendySQL.setWolumen(Dane.get(5).text());
                    wig20KomendySQL.setObrot(Dane.get(6).text());
                    */
                 /*
                    try {
                        stat.executeUpdate(wig20KomendySQL.getSQLWIG20NotowaniaKomenda(ID_SQL++));

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    infoPobieranie.Przetwarzanie=true;
                    infoPobieranie.setKomenda("Przetwarzanie: "+Przetworzone+" z: "+IloscWszystkichStron+" Dane na stronie: "+(++daneNaStronie)+" z: "+(DaneNaStronie.size()-1)+" Czas:"+infoPobieranie.getCzasPozostaly());
                    */

                    // System.out.println("Postep: "+Postep+"%"+"\tPrzetworzone :"+Przetworzone+" z:"+IloscWszystkichStron);
                    //System.out.println("Data :"+wig20KomendySQL.getData());

               // }





            }

            //***************************************************************Rzoloczenie z baza danych
            try{
                stat.close();
                polaczenie.close();

            }catch (SQLException e){
                e.printStackTrace();
            }
            zaktualizowanoWIG20=true;
            aktualizacjaKomenda=true;
            aktualizacja=false;
            infoPobieranie.setKomenda("Zaktualizowano Baze Danych");

            //***********************************************     Zapisywanie daty aktualizacji do pliku XML
            ustawieniaPlik.ustawienia.DataOstatniejAktualizacjiWIG20=ustawieniaPlik.getAktualnaData();
            ustawieniaPlik.ustawienia.AktualnaBazaWIG20NaDzien=true;
            ustawieniaPlik.ZapiszUstawienia();

        }
    }

}
