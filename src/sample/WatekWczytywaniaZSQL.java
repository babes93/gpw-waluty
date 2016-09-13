package sample;

import javafx.scene.control.RadioButton;

import javax.swing.plaf.TableHeaderUI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by patryk on 17.08.16.
 */
public class WatekWczytywaniaZSQL implements Runnable {
    private String Ticker = new String();
    private String data = new String();
    private String komendaCount = new String();
    private String komendaDane = new String();
    private WykresWIG20Podstawowy wig20Podstawowy=new WykresWIG20Podstawowy();
    private boolean OstatniMiesiac=false;
    private boolean OstatniRok=false;
    private boolean CaleArchiwum=false;
    public static double Progres=0;
    public static double iloscDanych=0;
    public static String Komenda = new String();
    private UstawieniaPlik ustawieniaPlik = new UstawieniaPlik();


    public WatekWczytywaniaZSQL(String ticker,WykresWIG20Podstawowy wig20Podstawowy){
        this.Ticker=ticker;
        this.wig20Podstawowy=wig20Podstawowy;

    }

    public WatekWczytywaniaZSQL(String ticker){
        this.Ticker=ticker;
    }

    public void setTrybDane(boolean ostatniMiesiac,boolean ostatniRok,boolean caleArchiwum) {
        this.OstatniMiesiac = ostatniMiesiac;
        this.OstatniRok = ostatniRok;
        this.CaleArchiwum = caleArchiwum;

        if (this.OstatniMiesiac) {
            this.data = this.OstatniMiesiacData();
            this.komendaCount = this.getCountSQLOstatni(data);
            this.komendaDane = this.getSQLOstatni(data);
        }
        if (this.OstatniRok) {
            this.data = this.OstatniRokData();
            this.komendaCount = this.getCountSQLOstatni(data);
            this.komendaDane = this.getSQLOstatni(data);
        }
        if (this.CaleArchiwum) {
                this.komendaCount = new String("SELECT COUNT(DATA) FROM NOTOWANIA WHERE TICKER ='"+this.Ticker+"';");
                this.komendaDane = new String("SELECT * FROM NOTOWANIA WHERE TICKER ='"+ this.Ticker+"';");
        }
    }



    @Override
    public void run() {
        Progres=0;
        //********************************************************************      Laczenie z baza danych
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

        //********************************************************************* Okreslanie ilosci danych do pobrania
        try {
            ResultSet rs = stat.executeQuery(this.komendaCount);
            while (rs.next()) {
                iloscDanych = Double.valueOf(rs.getString("COUNT(DATA)"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //********************************************************************  Wczytywanie danych z bazy
        double postep=1;
        wig20Podstawowy.SpolkiLista.get(wig20Podstawowy.getIndexSpolki()).WyczyscDane();
        try {
            ResultSet rs = stat.executeQuery(this.komendaDane);
            while (rs.next()) {
                Progres = postep/iloscDanych;
                postep++;
                if(Progres>=0.9)Progres=0.9;
                String data = rs.getString("DATA");
                double otwarcie = rs.getDouble("OTWARCIE");
                double zamkniecie = rs.getDouble("ZAMKNIECIE");
                double maxKurs = rs.getDouble("MAXKURS");
                double minKurs = rs.getDouble("MINKURS");
                int wolumen = rs.getInt("WOLUMEN");
                long obrot = rs.getLong("OBROT");

                  wig20Podstawowy.SpolkiLista.get(wig20Podstawowy.getIndexSpolki()).setAllDataBufors(data,otwarcie,zamkniecie,maxKurs,minKurs,wolumen,obrot);


             //   System.out.println("Progres: "+Progres+" %");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        int odstemp = 1;
        if(CaleArchiwum){
            odstemp = (int)(iloscDanych/ustawieniaPlik.ustawienia.WIG20MaxIloscDanychWykres);
        }else odstemp=1;
        wig20Podstawowy.SpolkiLista.get(wig20Podstawowy.getIndexSpolki()).wyczyscWyswietloneWykresy();
        wig20Podstawowy.SpolkiLista.get(wig20Podstawowy.getIndexSpolki()).WyswietlWykres(0,(int)(iloscDanych-1),odstemp);

        Progres=1.0;


        //*******************************************************************************       Rozlaczenie z Baza

        try{
            stat.close();
            polaczenie.close();

        }catch (SQLException e){
            e.printStackTrace();
        }



    }

    //***************************************************************

    public String OstatniMiesiacData(){
        String data = wig20Podstawowy.SpolkiLista.get(wig20Podstawowy.getIndexSpolki()).getDataDo();
        String[] tmp = data.split("-");
        String Rok = tmp[0];
        String Miesiac = tmp[1];
        int rok = Integer.valueOf(Rok);
        int miesiac = Integer.valueOf(Miesiac);
        if(miesiac==1){
            rok--;
            miesiac=12;
        }else miesiac--;
        Rok = Integer.toString(rok);
        if(miesiac <10){
            Miesiac = "0"+Integer.toString(miesiac);
        }else Miesiac = Integer.toString(miesiac);

        return new String(Rok+"-"+Miesiac+"-"+tmp[2]);
    }
    public String OstatniRokData(){
        String data = wig20Podstawowy.SpolkiLista.get(wig20Podstawowy.getIndexSpolki()).getDataDo();
        String[] tmp = data.split("-");
        return new String(Integer.toString(Integer.valueOf(tmp[0])-1)+"-"+tmp[1]+"-"+tmp[2]);
    }

    public String getSQLOstatni(String data){
        return new String("SELECT * FROM NOTOWANIA WHERE TICKER = '"+wig20Podstawowy.SpolkiLista.get(wig20Podstawowy.getIndexSpolki()).getTicker()+
                        "' AND DATA >= '"+data+"' ORDER BY DATA ASC;");
    }

    public String getCountSQLOstatni(String data){
        return new String("SELECT COUNT(DATA) FROM NOTOWANIA WHERE TICKER = '"+wig20Podstawowy.SpolkiLista.get(wig20Podstawowy.getIndexSpolki()).getTicker()+
                "' AND DATA >= '"+data+"';");
    }


    //******************************************************************************



}
