package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * Created by patryk on 14.08.16.
 */


public class WykresWIG20Podstawowy {
    private int indexDataMin,indexDataMax;
    private CheckBoksy checkBoksy;
    private CategoryAxis OsxLin = new CategoryAxis();
    private NumberAxis OsyLin = new NumberAxis();
    private CategoryAxis OsxWol = new CategoryAxis();
    private NumberAxis OsyWol = new NumberAxis();
    private AnchorPane PanelLiniowy;
    private AnchorPane PanelWolumen;
    private int IndexSpolki = -1;
    public LineChart<String,Number> Liniowy = new LineChart<String, Number>(OsxLin,OsyLin);
    public LineChart<String,Number> Wolumen = new LineChart<String, Number>(OsxWol,OsyWol);

    public ObservableList<Spolka> SpolkiLista = FXCollections.observableArrayList();

    public void WybierzSpolke(String ticker){
        this.wyczyscWykres();
        for(int i =0;i<SpolkiLista.size();i++){
            if(SpolkiLista.get(i).equalTicker(ticker)){
                this.IndexSpolki=i;
                SpolkiLista.get(this.IndexSpolki).setTytul();
            }
        }

    }

    public int getIndexSpolki(){return this.IndexSpolki;}

    public void WyswietlWykres(){

        if(IndexSpolki>=0 && IndexSpolki<SpolkiLista.size()){
            this.SpolkiLista.get(this.getIndexSpolki()).WyswietlWykres();
        }
    }

    public void wyczyscWykres(){
        this.Liniowy.getData().clear();
        this.Wolumen.getData().clear();
    }

    public WykresWIG20Podstawowy(){}

    public void WyswietlPunkty(){
        this.Liniowy.setCreateSymbols(true);
        this.Wolumen.setCreateSymbols(true);
    }

    public void UsunPunkty(){
        this.Liniowy.setCreateSymbols(false);
        this.Wolumen.setCreateSymbols(false);
    }

    public WykresWIG20Podstawowy(AnchorPane PanelLin, AnchorPane PanelWol){
        this.PanelLiniowy=PanelLin;
        this.PanelWolumen=PanelWol;

        this.Liniowy.setMaxSize(732,269);
        this.Liniowy.setMinSize(731,268);
        this.Liniowy.setAnimated(false);
        this.Liniowy.setCreateSymbols(false);
        this.OsyLin.setForceZeroInRange(false);

        this.Wolumen.setMaxSize(732,172);
        this.Wolumen.setMinSize(731,171);
        this.Wolumen.setAnimated(false);
        this.Wolumen.setCreateSymbols(false);
        this.OsyWol.setForceZeroInRange(false);

        this.PanelWolumen.getChildren().add(Wolumen);
        this.PanelLiniowy.getChildren().add(Liniowy);
    }

    public void setCheckBox(CheckBox otwarcie,CheckBox zamkniecie, CheckBox maxKurs, CheckBox minKurs, CheckBox wolumen, CheckBox obrot,CheckBox ciaglyKurs){
        this.checkBoksy=new CheckBoksy(otwarcie,zamkniecie,maxKurs,minKurs,wolumen,obrot,ciaglyKurs);
    }

    public void addSpolki(ObservableList<String> ListaSpolek){
        for(int i=0;i<ListaSpolek.size();i++)this.addSpolka(ListaSpolek.get(i));
    }

    public void addSpolka(String ticker){
        SpolkiLista.add(new Spolka(ticker,checkBoksy,Liniowy,Wolumen));
    }

    public void setPelnaNazwa(ObservableList<String> pelnaNazwa){
        if(SpolkiLista.size()==pelnaNazwa.size()) {
            for (int i = 0; i < pelnaNazwa.size(); i++) {
                SpolkiLista.get(i).setPelnaNazwa(pelnaNazwa.get(i));
            }
        }
    }

    public void setData(ObservableList<String> dataOd,ObservableList<String> dataDo){
        if(SpolkiLista.size()==dataDo.size()){
            for (int i=0;i<dataDo.size();i++){
                SpolkiLista.get(i).setData(dataDo.get(i),dataOd.get(i));
            }
        }
    }

    public void przepiszDane(String dataOd, String dataDo, int odstemp){
        this.SpolkiLista.get(this.getIndexSpolki()).PrzepiszDane(dataOd,dataDo,odstemp);
    }

    public void ZamienDane(LocalDate data1, LocalDate data2, String data3,String data4){
        String[] tmp = data1.toString().split(Pattern.quote("."));
        data3 = new String(tmp[0]+"-"+tmp[1]+"-"+tmp[2]);
        tmp = data2.toString().split(Pattern.quote("."));
        data4 = new String(tmp[0]+"-"+tmp[1]+"-"+tmp[2]);
    }

    public void przesunWykresPrawo(int x){
        this.SpolkiLista.get(this.getIndexSpolki()).przesunWykresPrawo(x);
    }

    public void przesunWykresLewo(int x){
        this.SpolkiLista.get(this.getIndexSpolki()).przesunWykresLewo(x);
    }

    public void setindexDataOd(int index){
        this.SpolkiLista.get(this.getIndexSpolki()).setIndexDataOd(index);
    }

    public void setindexDataDo(int index){
        this.SpolkiLista.get(this.getIndexSpolki()).setIndexDataDo(index);
    }

    //******************************************************************************************

    private class CheckBoksy{
        public CheckBox Otwarcie;
        public CheckBox Zamkniecie;
        public CheckBox MaxKurs;
        public CheckBox MinKurs;
        public CheckBox Wolumen;
        public CheckBox Obrot;
        public CheckBox CialgyKurs;

        public CheckBoksy(CheckBox otwarcie,CheckBox zamkniecie, CheckBox maxKurs,CheckBox minKurs,CheckBox wolumen,CheckBox obrot,CheckBox cialgyKurs){
            this.Obrot=obrot;
            this.CialgyKurs=cialgyKurs;
            this.MaxKurs=maxKurs;
            this.MinKurs=minKurs;
            this.Wolumen=wolumen;
            this.Otwarcie=otwarcie;
            this.Zamkniecie=zamkniecie;
        }

        public boolean Obrot(){
            return Obrot.isSelected();
        }

        public boolean Zamkniecie(){
            return Zamkniecie.isSelected();
        }

        public boolean Otwarcie(){
            return Otwarcie.isSelected();
        }

        public boolean MaxKurs(){
            return MaxKurs.isSelected();
        }

        public boolean MinKurs(){
            return MinKurs.isSelected();
        }

        public boolean Wolumen(){
            return Wolumen.isSelected();
        }

        public boolean CiaglyKurs(){
            return CialgyKurs.isSelected();
        }

    }

    //****************************************************************      klasa wykresy

    public class Wykres{
        private String Name = new String();
        private int Size=0;
        private int BufforSize = 0;
        private String MaxData = new String();
        private String MinData = new String();
        public XYChart.Series<String,Number> Buffor = new XYChart.Series<String,Number>();
        public XYChart.Series<String,Number> Wyk = new XYChart.Series<String,Number>();

        public void wyczyscBufor(){
            this.Buffor.getData().clear();
            while (this.Buffor.getData().size() !=0)this.Buffor.getData().clear();
            this.BufforSize=this.Buffor.getData().size();
            this.Wyk.getData().clear();
            while (this.Wyk.getData().size()!=0)this.Wyk.getData().clear();
        }

        public void addDataBuf(String data, Number dane){
            this.Buffor.getData().add(new XYChart.Data<String,Number>(data,dane));
            this.BufforSize=this.Buffor.getData().size();
        }

        public void przepiszDane(int from,int to,int odstep){
            if(to>=this.Buffor.getData().size())to=this.Buffor.getData().size()-1;
            if(from<=0)from=0;
            if(from>=0 && to <this.Buffor.getData().size()){
                this.Wyk.getData().clear();
                int x=0;
                for(int i=from;i<=to;i+=odstep){
                    this.Wyk.getData().add(Buffor.getData().get(i));
                }
                this.Size=this.Wyk.getData().size();
            }
        }

        public int getIndexMaxData(String data){
            int tmp=Buffor.getData().size()-1;
            for(int i=(Buffor.getData().size()-1);i>0;i--){
                if(!(this.Buffor.getData().get(i).getXValue().toString().compareTo(data)>=0)){
                    tmp=i;
                    break;
                }
            }
            return tmp;
        }

        public int getIndexMinData(String data){
            int tmp=-1;
            for(int i=0;i<Buffor.getData().size();i++){
                if(!(this.Buffor.getData().get(i).getXValue().toString().compareTo(data)<=0)){
                    if(i>0)tmp=i-1;
                    else tmp=i;
                    break;
                }
            }
            return tmp;
        }

        public void setName(String name){
            this.Name=name;
            Buffor.setName(this.Name);
            Wyk.setName(this.Name);
        }

        public String getName(){
            return this.Name;
        }

        public String getMaxData() {
            this.MaxData = this.Wyk.getData().get(Wyk.getData().size()-1).getXValue().toString();
            return this.MaxData;
        }

        public String getMinData(){
            this.MinData = this.Wyk.getData().get(0).getXValue().toString();
            return this.MinData;
        }

        public int getBufforSize(){
            return this.BufforSize;
        }

        public int getSize(){
            return this.Size;
        }



    }

    //***************************************************************       klasa spolki

    public class Spolka{
       // public double progres=0;

        private int indexDataOd,indexDataDo;
        private String Ticker = new String();
        private String PelnaNazwa = new String();
        private String DataOd = new String();
        private String DataDo = new String();
        private CheckBoksy checkBoksy;
        private LineChart liniowy;
        private LineChart wol;
        private int BufforSize =0;
        public Wykres Otwarcie = new Wykres();
        public Wykres Zamkniecie = new Wykres();
        public Wykres MaxKurs = new Wykres();
        public Wykres MinKurs = new Wykres();
        public Wykres Wolumen = new Wykres();
        public Wykres Obrot = new Wykres();
        public Wykres CialgyKurs = new Wykres();
        private String MaxData = new String();
        private String MinData = new String();
        private UstawieniaPlik ustawieniaPlik = new UstawieniaPlik();

        public int getIndexDataOd(){return this.indexDataOd;}

        public int getIndexDataDo(){return this.indexDataDo;}

        public void setIndexDataDo(int index){
            this.indexDataDo=index;
        }

        public void setIndexDataOd(int index){
            this.indexDataDo=index;
        }

        public String getMaxData(){return this.MaxData;}

        public String getMinData(){return this.MinData;}

        public String getDataOd(){return this.DataOd;}

        public String getDataDo(){return this.DataDo;}

        public void setTytul(){
            this.liniowy.setTitle("Liniowy: "+this.PelnaNazwa);
            this.wol.setTitle("Wolumen/Obrot: "+this.PelnaNazwa);
        }

        public Spolka(String ticker,CheckBoksy checkBoksy,LineChart lin, LineChart wol1){
            this.liniowy=lin;
            this.wol=wol1;
            this.Ticker=ticker;
            this.Otwarcie.setName(ticker+":Otwarcie");
            this.Zamkniecie.setName(ticker+":Zamkniecie");
            this.MaxKurs.setName(ticker+":MaxKurs");
            this.MinKurs.setName(ticker+":MinKurs");
            this.Wolumen.setName(ticker+":Wolumen");
            this.Obrot.setName(ticker+":Obrot");
            this.CialgyKurs.setName(ticker+":CiaglyKurs");
            this.checkBoksy=checkBoksy;
        }

        public void setPelnaNazwa(String pelnaNazwa){
            this.PelnaNazwa=pelnaNazwa;
        }

        public void setData(String dataDo,String dataOd){
            this.DataDo=dataDo;
            this.DataOd=dataOd;
        }

        public String getTicker(){return this.Ticker;}

        public boolean equalTicker(String ticker){
            return (ticker.equals(this.Ticker)?true:false);
        }

        public void setAllDataBufors(String data, Number otwarcie, Number zamkniecie, Number maxKurs, Number minKurs, Number Wolumen, Number Obrot){
            this.Obrot.addDataBuf(data,Obrot);
            this.Otwarcie.addDataBuf(data,otwarcie);
            this.Zamkniecie.addDataBuf(data,zamkniecie);
            this.Wolumen.addDataBuf(data,Wolumen);
            this.MaxKurs.addDataBuf(data,maxKurs);
            this.MinKurs.addDataBuf(data,minKurs);
            this.BufforSize=this.Wolumen.getBufforSize();
        }

        public void wyczyscWyswietloneWykresy(){
            this.liniowy.getData().clear();
            this.wol.getData().clear();
        }

        public void WyczyscDane(){
            this.liniowy.getData().clear();
            this.wol.getData().clear();
            while (this.liniowy.getData().size()!=0)this.liniowy.getData().clear();
            while (this.wol.getData().size()!=0)this.wol.getData().clear();
            this.Obrot.wyczyscBufor();
            this.Wolumen.wyczyscBufor();
            this.MaxKurs.wyczyscBufor();
            this.MinKurs.wyczyscBufor();
            this.Otwarcie.wyczyscBufor();
            this.Zamkniecie.wyczyscBufor();
        }

        public void WyswietlWykres(){

            if(checkBoksy.Wolumen() || checkBoksy.Obrot())this.wol.getData().clear();
            if(checkBoksy.MaxKurs() || checkBoksy.MinKurs() || checkBoksy.Zamkniecie() || checkBoksy.Otwarcie())this.liniowy.getData().clear();

            if(checkBoksy.MaxKurs())liniowy.getData().add(this.MaxKurs.Wyk);
            else liniowy.getData().remove(this.MaxKurs.Wyk);

            if (checkBoksy.MinKurs())liniowy.getData().add(this.MinKurs.Wyk);
            else liniowy.getData().remove(this.MinKurs.Wyk);

            if(checkBoksy.Otwarcie())liniowy.getData().add(this.Otwarcie.Wyk);
            else liniowy.getData().remove(this.Otwarcie.Wyk);

            if(checkBoksy.Zamkniecie())liniowy.getData().add(this.Zamkniecie.Wyk);
            else liniowy.getData().remove(this.Zamkniecie.Wyk);

            if(checkBoksy.Wolumen())wol.getData().add(this.Wolumen.Wyk);
            else wol.getData().remove(this.Wolumen.Wyk);

            if(checkBoksy.Obrot())wol.getData().add(this.Obrot.Wyk);
            else wol.getData().remove(this.Obrot.Wyk);

        }

        public void WyswietlWykres(int from, int to,int odstemp){

            this.indexDataOd=from;
            this.indexDataDo=to;
    //        this.wyczyscWyswietloneWykresy();
                this.liniowy.getData().clear();
                this.wol.getData().clear();

            this.Zamkniecie.przepiszDane(from,to,odstemp);
            this.Otwarcie.przepiszDane(from,to,odstemp);
            this.MinKurs.przepiszDane(from,to,odstemp);
            this.MaxKurs.przepiszDane(from,to,odstemp);
            this.Wolumen.przepiszDane(from,to,odstemp);
            this.Obrot.przepiszDane(from,to,odstemp);

            this.MaxData=this.Wolumen.getMaxData();
            this.MinData=this.Wolumen.getMinData();
//            this.WyswietlWykres();
        }

        public void PrzepiszDane(String DataOd, String DataDo, int odstemp){
            this.liniowy.getData().clear();
            this.wol.getData().clear();
            this.indexDataOd=this.Wolumen.getIndexMinData(DataOd);
            this.indexDataDo=this.Wolumen.getIndexMaxData(DataDo);

            int dlugosc = indexDataDo-indexDataOd;
            int odst = (int)dlugosc/ustawieniaPlik.ustawienia.WIG20MaxIloscDanychWykres;
            if(odst<2)odst=1;


            this.Otwarcie.przepiszDane(indexDataOd,indexDataDo,odst);
            this.Zamkniecie.przepiszDane(indexDataOd,indexDataDo,odst);
            this.MaxKurs.przepiszDane(indexDataOd,indexDataDo,odst);
            this.MinKurs.przepiszDane(indexDataOd,indexDataDo,odst);
            this.Wolumen.przepiszDane(indexDataOd,indexDataDo,odst);
            this.Obrot.przepiszDane(indexDataOd,indexDataDo,odst);

            this.MaxData=this.Wolumen.getMaxData();
            this.MinData=this.Wolumen.getMinData();

        }

        public int getBufforSize(){return this.BufforSize;}

        public void przesunWykresPrawo(int x){
            if((this.indexDataDo+x)>=(this.Wolumen.getBufforSize()-1)){
                this.indexDataDo=this.Wolumen.getBufforSize()-1-x;
                this.indexDataOd=this.indexDataDo-x;
            }
            this.WyswietlWykres(indexDataOd+x,indexDataDo+x,1);
            this.indexDataDo+=x;
            this.indexDataOd+=x;
        }

        public void przesunWykresLewo(int x){
            if((this.indexDataOd-x)<=0){
                this.indexDataOd=x;
                this.indexDataDo=0;
                this.indexDataDo=2*x;
            }
            this.WyswietlWykres(indexDataOd-x,indexDataDo-x,1);
            this.indexDataOd-=x;
            this.indexDataDo-=x;

        }
    }
}
