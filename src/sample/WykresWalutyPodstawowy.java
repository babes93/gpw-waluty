package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;

/**
 * Created by patryk on 22.08.16.
 */
public class WykresWalutyPodstawowy {

    private CheckBoksyWaluty checkBoksyWaluty;
    private CategoryAxis osx = new CategoryAxis();
    private NumberAxis osy = new NumberAxis();
    private AnchorPane PanelLiniowy;
    public LineChart<String,Number> Liniowy = new LineChart<String, Number>(osx,osy);

    private ObservableList<String> KodyWalutWyswietlone = FXCollections.observableArrayList();
    public ObservableList<Waluta> ListaWalut = FXCollections.observableArrayList();

    public WykresWalutyPodstawowy(){}

    public WykresWalutyPodstawowy(AnchorPane panelLiniowy){
        this.PanelLiniowy=panelLiniowy;
        this.Liniowy.setCreateSymbols(false);
        this.Liniowy.setAnimated(false);
        this.osy.setForceZeroInRange(false);
        this.Liniowy.setMaxSize(732,485);
        this.Liniowy.setMinSize(731,484);
        this.Liniowy.setTitle("Kursy Walut");

        this.PanelLiniowy.getChildren().add(this.Liniowy);

    }

    public void setCheckBoksyWaluty(CheckBox kupno, CheckBox sprzedaz, CheckBox przelicznik){
        this.checkBoksyWaluty=new CheckBoksyWaluty(kupno,sprzedaz,przelicznik);
    }

    public void addWaluty(ObservableList<String> kodywalut){
        for(int i=0;i<kodywalut.size();i++){
            this.ListaWalut.add(new Waluta(kodywalut.get(i),this.checkBoksyWaluty,this.Liniowy));
        }
    }

    public void setData(ObservableList<String> dataod, ObservableList<String> datado){
        if(datado.size()==ListaWalut.size()){
            for(int i=0;i<datado.size();i++){
                ListaWalut.get(i).setData(datado.get(i),dataod.get(i));
            }
        }
    }

    public void WyswietlWalute(String kodwaluty){
        for(int i=0;i<ListaWalut.size();i++){
            if(ListaWalut.get(i).equalKodWaluty(kodwaluty))ListaWalut.get(i).WyswietlWykres();
        }
    }

    public void usunWykres(String kodwaluty){
        for(int i=0;i<ListaWalut.size();i++){
            if(ListaWalut.get(i).equalKodWaluty(kodwaluty))ListaWalut.get(i).usunDaneWalutyZWykresu();
        }
    }

    public void usunDaneZWykresu(){
        for(int i=0;i<KodyWalutWyswietlone.size();i++){
            this.usunWykres(KodyWalutWyswietlone.get(i));
        }
    }

    public boolean isWyswietlony(String kodwaluty){
        boolean status = false;
        for (int i=0;i<ListaWalut.size();i++){
            if(ListaWalut.get(i).equalKodWaluty(kodwaluty)){
                status =ListaWalut.get(i).getWykresWyswietlono();
                break;
            }
        }

        return status;
    }

    public void setKodyWalutWyswietlone(){
        this.KodyWalutWyswietlone.clear();
        while (this.KodyWalutWyswietlone.size()!=0)this.KodyWalutWyswietlone.clear();

        for(int i=0;i<ListaWalut.size();i++){
            if(this.isWyswietlony(ListaWalut.get(i).getKodWaluty())){
                this.KodyWalutWyswietlone.add(new String(ListaWalut.get(i).getKodWaluty()));
            }
        }
    }

    public int getMinDataIndexWyswietlone(){
        int index=-1;

        for(int i=0;i<KodyWalutWyswietlone.size();i++){
            int id = this.getIndexByKodWaluty(KodyWalutWyswietlone.get(i));
            index = ListaWalut.get(id).getIndexDataOd();
        }

        return index;
    }

    public int getMaxDataIndexWyswietlone(){
        int index =-1;

        for (int i=0;i<KodyWalutWyswietlone.size();i++){
            int id = this.getIndexByKodWaluty(KodyWalutWyswietlone.get(i));
            index=ListaWalut.get(id).getIndexDataDo();
        }

        return index;
    }


    public int getIndexByKodWaluty(String kodwaluty){
        int tmp=-1;
        for(int i=0;i<ListaWalut.size();i++){
            if(this.ListaWalut.get(i).equalKodWaluty(kodwaluty)){
                tmp=i;
            }
        }
        return tmp;
    }

    public void WyczyscWykres(){
        this.Liniowy.getData().clear();
        while (this.Liniowy.getData().size()!=0)this.Liniowy.getData().clear();
    }

    public void PrzepiszWyswietloneWykresy(int from,int to,int odstemp){
        this.WyczyscWykres();
        for(int i=0;i<KodyWalutWyswietlone.size();i++){
            int id = this.getIndexByKodWaluty(KodyWalutWyswietlone.get(i));
            ListaWalut.get(id).PrzepiszWykres(from,to,odstemp);
        }
        for(int i=0;i<KodyWalutWyswietlone.size();i++){
            int id = this.getIndexByKodWaluty(KodyWalutWyswietlone.get(i));
            ListaWalut.get(id).WyswietlWykres();
        }

    }

    public void PrzesunWykresyPrawo(int x){
        this.WyczyscWykres();
        for(int i=0;i<KodyWalutWyswietlone.size();i++){
            int id=this.getIndexByKodWaluty(KodyWalutWyswietlone.get(i));
            ListaWalut.get(id).przesunWykresPrawo(x);
        }

        for (int i=0;i<KodyWalutWyswietlone.size();i++){
            int id = this.getIndexByKodWaluty(KodyWalutWyswietlone.get(i));
            ListaWalut.get(id).WyswietlWykres();
        }
    }

    public void PrzesunWykresyLewo(int x){
        this.WyczyscWykres();
        for(int i=0;i<KodyWalutWyswietlone.size();i++){
            int id = this.getIndexByKodWaluty(KodyWalutWyswietlone.get(i));
            ListaWalut.get(id).przesunWykresLewo(x);
        }

        for(int i=0;i<KodyWalutWyswietlone.size();i++){
            int id = this.getIndexByKodWaluty(KodyWalutWyswietlone.get(i));
            ListaWalut.get(id).WyswietlWykres();
        }
    }

    public void PrzepiszWyswietloneWykresy(String dataOd, String dataDo, int odstemp){
        this.WyczyscWykres();
        for(int i=0;i<KodyWalutWyswietlone.size();i++){
            int id = this.getIndexByKodWaluty(KodyWalutWyswietlone.get(i));
            ListaWalut.get(id).PrzebiszDane(dataOd,dataDo,odstemp);
        }

        for(int i=0;i<KodyWalutWyswietlone.size();i++){
            int id = this.getIndexByKodWaluty(KodyWalutWyswietlone.get(i));
            ListaWalut.get(id).WyswietlWykres();
        }
    }

    public String getMinDataWyswietlone(){
        int index = this.getIndexByKodWaluty(KodyWalutWyswietlone.get(0));
        return ListaWalut.get(index).getMinData();
    }

    public String getMaxDataWyswietlone(){
        int index = this.getIndexByKodWaluty(KodyWalutWyswietlone.get(0));
        return ListaWalut.get(index).getMaxData();
    }

    public void WyswietlPunkty(){
        this.Liniowy.setCreateSymbols(true);
    }

    public void UsunPunkty(){
        this.Liniowy.setCreateSymbols(false);
    }

    //***********************************************************************   Wykres waluty
    public class WykresWaluty{
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

    //************************************************************************      Klasa waluty
    public class Waluta{
        private boolean wykresWyswietlono=false;
        private int indexDataOd,indexDataDo;
        private String KodWaluty = new String();
        private String NazwaWaluty = new String();
        private String DataOd = new String();
        private String DataDo = new String();
        private CheckBoksyWaluty checkBoksyWaluty;
        private LineChart liniowy;
        private int BufforSize =0;
        public WykresWaluty KursKupna = new WykresWaluty();
        public WykresWaluty KursSprzedazy = new WykresWaluty();
        public WykresWaluty Przelicznik = new WykresWaluty();
        private String MaxData = new String();
        private String MinData = new String();
        private UstawieniaPlik ustawieniaPlik = new UstawieniaPlik();

        //********************************************************************************

        public Waluta(String kodWaluty,CheckBoksyWaluty checkBoksyWaluty,LineChart lin){
            this.KodWaluty=kodWaluty;
            this.checkBoksyWaluty=checkBoksyWaluty;
            this.liniowy=lin;
            this.KursKupna.setName(kodWaluty+":Kurs Kupna");
            this.KursSprzedazy.setName(kodWaluty+":Kurs Sprzedazy");
            this.Przelicznik.setName(kodWaluty+":Przelicznik");
        }

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

        public void setData(String dataDo,String dataOd){
            this.DataDo=dataDo;
            this.DataOd=dataOd;
        }

        public void setNazwaWaluty(String nazwaWaluty){this.NazwaWaluty=nazwaWaluty;}

        public String getKodWaluty(){return this.KodWaluty;}

        public String getNazwaWaluty(){return this.NazwaWaluty;}

        public boolean equalKodWaluty(String kodWaluty){
            return (kodWaluty.equals(this.KodWaluty)?true:false);
        }

        public void setAllDataBufors(String data, Number kupno, Number sprzedaz, Number przelicznik){
            this.KursKupna.addDataBuf(data,kupno);
            this.KursSprzedazy.addDataBuf(data,sprzedaz);
            this.Przelicznik.addDataBuf(data,przelicznik);
            this.BufforSize=this.KursKupna.getBufforSize();
        }

        public void usunDaneWalutyZWykresu(){
            if(this.liniowy.getData().contains(this.KursKupna.Wyk))this.liniowy.getData().remove(this.KursKupna.Wyk);
            if(this.liniowy.getData().contains(this.KursSprzedazy.Wyk))this.liniowy.getData().remove(this.KursSprzedazy.Wyk);
            if(this.liniowy.getData().contains(this.Przelicznik.Wyk))this.liniowy.getData().remove(this.Przelicznik.Wyk);
            this.wykresWyswietlono=false;
        }

        public void wyczyscBuffory(){
            this.usunDaneWalutyZWykresu();
            this.KursSprzedazy.wyczyscBufor();
            this.KursKupna.wyczyscBufor();
            this.Przelicznik.wyczyscBufor();
        }

        public void WyswietlWykres(){
            this.usunDaneWalutyZWykresu();

            if(checkBoksyWaluty.Kupno())this.liniowy.getData().add(this.KursKupna.Wyk);
            else this.liniowy.getData().remove(this.KursKupna.Wyk);

            if(checkBoksyWaluty.Sprzedaz())this.liniowy.getData().add(this.KursSprzedazy.Wyk);
            else this.liniowy.getData().remove(this.KursSprzedazy.Wyk);

            if(checkBoksyWaluty.Przelicznik())this.liniowy.getData().add(this.Przelicznik.Wyk);
            else this.liniowy.getData().remove(this.Przelicznik.Wyk);
            this.wykresWyswietlono=true;
        }

        public boolean getWykresWyswietlono(){return this.wykresWyswietlono;}

        public void PrzepiszWykres(int from, int to, int odstemp){
            this.indexDataOd = from;
            this.indexDataDo =to;

            this.usunDaneWalutyZWykresu();

            this.KursKupna.przepiszDane(from,to,odstemp);
            this.KursSprzedazy.przepiszDane(from,to,odstemp);
            this.Przelicznik.przepiszDane(from,to,odstemp);

            this.MaxData=this.KursKupna.getMaxData();
            this.MinData=this.KursKupna.getMinData();

        }

        public void PrzebiszDane(String DataOd, String DataDo, int odstemp){
            this.usunDaneWalutyZWykresu();
            this.indexDataOd = this.KursKupna.getIndexMinData(DataOd);
            this.indexDataDo = this.KursKupna.getIndexMaxData(DataDo);

            int dlugosc = indexDataDo-indexDataOd;
            int odst = (int)dlugosc/ustawieniaPlik.ustawienia.Waluty0MaxIloscDanychWykres;
            if(odst<2)odst=1;

            this.KursKupna.przepiszDane(indexDataOd,indexDataDo,odst);
            this.KursSprzedazy.przepiszDane(indexDataOd,indexDataDo,odst);
            this.Przelicznik.przepiszDane(indexDataOd,indexDataDo,odst);

            this.MaxData=this.KursKupna.getMaxData();
            this.MinData=this.KursKupna.getMinData();
        }

        public int getBufforSize(){return this.BufforSize;}

        public void przesunWykresPrawo(int x){
            if((this.indexDataDo+x)>=(this.KursKupna.getBufforSize()-1)){
                this.indexDataDo=this.KursKupna.getBufforSize()-1-x;
                this.indexDataOd=this.indexDataDo-x;
            }
            this.PrzepiszWykres(indexDataOd+x,indexDataDo+x,1);
            this.indexDataDo+=x;
            this.indexDataOd+=x;
        }

        public void przesunWykresLewo(int x){
            if((this.indexDataOd-x)<=0){
                this.indexDataOd=x;
                this.indexDataDo=0;
                this.indexDataDo=2*x;
            }
            this.PrzepiszWykres(indexDataOd-x,indexDataDo-x,1);
            this.indexDataOd-=x;
            this.indexDataDo-=x;

        }

    }

    //************************************************************************          CheckBoksy
    private class CheckBoksyWaluty{
        public CheckBox Kupno;
        public CheckBox Sprzedaz;
        public CheckBox Przelicznik;

        public CheckBoksyWaluty(CheckBox kupno,CheckBox sprzedaz,CheckBox przelicznik){
            this.Kupno=kupno;
            this.Sprzedaz=sprzedaz;
            this.Przelicznik=przelicznik;
        }

        public CheckBoksyWaluty(){}

        public boolean Kupno(){return this.Kupno.isSelected();}

        public boolean Sprzedaz(){return this.Sprzedaz.isSelected();}

        public boolean Przelicznik(){return this.Przelicznik.isSelected();}

    }

}
