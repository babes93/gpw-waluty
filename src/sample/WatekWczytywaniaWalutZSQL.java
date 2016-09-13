package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by patryk on 22.08.16.
 */
public class WatekWczytywaniaWalutZSQL implements Runnable {
    private String kodwaluty = new String();
    private String data = new String();
    private String komendaCount = new String();
    private String komendaData = new String();
    private WykresWalutyPodstawowy wykresWalutyPodstawowy = new WykresWalutyPodstawowy();
    private boolean OstatniMiesiac=false;
    private boolean OstatniRok=false;
    private boolean CaleArchiwum=false;
    public static double Progres=0;
    public static double iloscDanych=0;
    public static String Komenda = new String();
    private int indexWaluty = -1;
    private UstawieniaPlik ustawieniaPlik = new UstawieniaPlik();


    public WatekWczytywaniaWalutZSQL(String kodwaluty,WykresWalutyPodstawowy wykresWalutyPodstawowy){
        this.kodwaluty=kodwaluty;
        this.wykresWalutyPodstawowy=wykresWalutyPodstawowy;
        for (int i=0;i<wykresWalutyPodstawowy.ListaWalut.size();i++){
            if(wykresWalutyPodstawowy.ListaWalut.get(i).equalKodWaluty(this.kodwaluty)){
                this.indexWaluty=i;
                break;
            }
        }
    }

    public void setTrybDane(boolean ostatniRok,boolean ostatniMiesiac, boolean caleArchiwum){
        this.OstatniMiesiac=ostatniMiesiac;
        this.OstatniRok=ostatniRok;
        this.CaleArchiwum=caleArchiwum;

        if(OstatniMiesiac){
            this.komendaCount=this.getCountOstatni(this.OstatniMiesiacData(wykresWalutyPodstawowy.ListaWalut.get(this.indexWaluty).getDataDo()));
            this.komendaData=this.getOstatni(this.OstatniMiesiacData(wykresWalutyPodstawowy.ListaWalut.get(this.indexWaluty).getDataDo()));
        }else if(OstatniRok){
            this.komendaCount=this.getCountOstatni(this.OstatniRokData(wykresWalutyPodstawowy.ListaWalut.get(this.indexWaluty).getDataDo()));
            this.komendaData=this.getOstatni(this.OstatniRokData(wykresWalutyPodstawowy.ListaWalut.get(this.indexWaluty).getDataDo()));
        }else if(CaleArchiwum){
            this.komendaCount=this.getCountOstatni(wykresWalutyPodstawowy.ListaWalut.get(this.indexWaluty).getDataOd());
            this.komendaData=this.getOstatni(wykresWalutyPodstawowy.ListaWalut.get(this.indexWaluty).getDataOd());
        }
    }
    //**************************************************************

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
        wykresWalutyPodstawowy.ListaWalut.get(this.indexWaluty).wyczyscBuffory();
        try {
            ResultSet rs = stat.executeQuery(this.komendaData);
            while (rs.next()){
                Progres = postep/iloscDanych;
                postep++;
                if(Progres>=0.9)Progres=0.9;
                String data = rs.getString("DATA");
                double kupno = rs.getDouble("KURS_KUPNA");
                double sprzedaz = rs.getDouble("KURS_PRZEDAZY");
                int przelicznik = rs.getInt("PRZELICZNIK");

                wykresWalutyPodstawowy.ListaWalut.get(this.indexWaluty).setAllDataBufors(data,kupno,sprzedaz,przelicznik);

            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        int odstemp = 1;
        if(CaleArchiwum){
            odstemp = (int)(iloscDanych/ustawieniaPlik.ustawienia.Waluty0MaxIloscDanychWykres);
        }else odstemp=1;

        wykresWalutyPodstawowy.ListaWalut.get(this.indexWaluty).usunDaneWalutyZWykresu();
        wykresWalutyPodstawowy.ListaWalut.get(this.indexWaluty).PrzepiszWykres(0,(int)iloscDanych-1,odstemp);

        Progres=1.0;


        //*******************************************************************************       Rozlaczenie z Baza

        try{
            stat.close();
            polaczenie.close();

        }catch (SQLException e){
            e.printStackTrace();
        }



    }
    //*************************************************************************************
    public String getOstatni(String data1){
        return new String("SELECT * FROM WALUTY WHERE KOD_WALUTY='"+this.kodwaluty+"' AND DATA >='"+data1+"' ORDER BY DATA ASC;");
    }

    public String getCountOstatni(String data1){
        return new String("SELECT COUNT(DATA) FROM WALUTY WHERE KOD_WALUTY='"+this.kodwaluty+"' AND DATA >='"+data1+"';");
    }

    public String OstatniMiesiacData(String data){

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
    public String OstatniRokData(String data){

        String[] tmp = data.split("-");
        return new String(Integer.toString(Integer.valueOf(tmp[0])-1)+"-"+tmp[1]+"-"+tmp[2]);
    }

}
