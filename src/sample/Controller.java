package sample;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;
import java.util.stream.Stream;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Controller implements Initializable {



    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker DataDoWykresSNS;



    @FXML
    private RadioButton OstatniMiesiacCheckWaluty;

    @FXML
    private CheckBox MaxSNSCheck;

    @FXML
    private ProgressIndicator ProgIndWczytywanie;

    @FXML
    private MenuItem WIG20ArchiwumMenuItem;

    @FXML
    private MenuItem MenuOpcje;

    @FXML
    private TextField TextDoWalutyL;

    @FXML
    private TextField TextPrzesunWaluty;

    @FXML
    private TableColumn KolumnaOdWaluty;

    @FXML
    private TextField DataOdSNSR;

    @FXML
    private Slider SliderDoSNS;

    @FXML
    private TextField DataOdSNSL;

    @FXML
    private Button PrzesPButSNS;

    @FXML
    private AnchorPane PanelWykresow;

    @FXML
    private CheckBox CheckBoxPrzelicznikWaluty;

    @FXML
    private CheckBox OtwarcieSNSCheck;

    @FXML
    private RadioButton OstatniMiesiacCheck;

    @FXML
    private RadioButton OstatniRokCheck;

    @FXML
    private TextField TextDoWalutyR;

    @FXML
    private Slider SliderOdWaluty;

    @FXML
    private AnchorPane BarPanelSNS;

    @FXML
    private TableColumn KolumnaDo;

    @FXML
    private TableView<WalutyNBP.WalutyDane> TabelaWaluty;

    @FXML
    private AnchorPane PanelWaluty;

    @FXML
    private TextField PrzesTextFieldSNS;



    @FXML
    private CheckBox ZamkniecieSNSCheck;

    @FXML
    private CheckBox MinSNSCheck;

    @FXML
    private DatePicker DataOdWykresSNS;

    @FXML
    private TableColumn KolumnaWaluty;

    @FXML
    private AnchorPane SwiecowyPanelSNS;

    @FXML
    private TableColumn KolumnaDoWaluty;

    @FXML
    private Button PrzesLButSNS;

    @FXML
    private Button ButtonPrzesunPrawoWaluty;

    @FXML
    private TableColumn KolumnaOd;

    @FXML
    private TableColumn KolumnaSredniNotowania;

    @FXML
    private AnchorPane PanelSNSLin;

    @FXML
    private DatePicker DataDoWaluty;

    @FXML
    private TextField TextOdWalutyL;

    @FXML
    private TextField TextOdWalutyR;

    @FXML
    private CheckBox CheckBoxSprzedazWaluty;

    @FXML
    private Button WyswietlWykButtonSNS;

    @FXML
    private TableView<WIG20.WIG20Tabela> TabelaSpolek;

    @FXML
    private DatePicker DataOdWaluty;

    @FXML
    private CheckBox CiaglySNSCheck;

    @FXML
    private ProgressBar ProgBarWczytywanie;

    @FXML
    private Slider SliderDoWaluty;

    @FXML
    private MenuItem PobieranieWalutMenu;

    @FXML
    private RadioButton CaleArchiwumCheckWaluty;

    @FXML
    private ProgressBar ProgBarWczytywanieWaluty;

    @FXML
    private RadioButton CaleArchiwumCheck;

    @FXML
    private ComboBox<String> ComboBoxWaluty;

    @FXML
    private Button ButtonPrzesunLewoWaluty;

    @FXML
    private Button ButtonWyswietlWaluty;

    @FXML
    private ProgressIndicator ProgIndWczytywanieWaluty;

    @FXML
    private TextField DataDoSNSL;

    @FXML
    private CheckBox CheckBoxKupnoWaluty;

    @FXML
    private Slider SliderOdSNS;

    @FXML
    private TextField DataDoSNSR;

    @FXML
    private Button DodajUsunWalutyButton;

    @FXML
    private RadioButton OstatniRokCheckWaluty;

    @FXML
    private TableColumn KolumnaNazwa;

    @FXML
    private TableColumn KolumnaTicker;

    @FXML
    private ListView<String> KonsolaWidok;

    @FXML
    private MenuItem MenuItemPomocInformacje;

    @FXML
    private ComboBox<String> ComboBoxSpolki;

    @FXML
    private MenuItem MenuItemPelneInfoArchiwum;

    @FXML
    private MenuItem MenuItemSkryptSciLab;

    @FXML
    private MenuItem MenuItemArchiwumWaluty;

    @FXML
    private CheckBox CheckBoxObrot;

    @FXML
    private CheckBox CheckBoxWolumen;

    @FXML
    private Button WyczyscWykresWalutyButton;

    final ToggleGroup togRadioWczytywanie = new ToggleGroup();
    final ToggleGroup tohRadioWaluty = new ToggleGroup();
    private WIG20 wig20 = new WIG20();
    private WalutyNBP walutyNBP = new WalutyNBP();
    private ObservableList<String> Komendy = FXCollections.observableArrayList();
    SimpleDateFormat CzasSystemowy = new SimpleDateFormat("HH:mm");
    private WykresWIG20Podstawowy wykresWIG20Podstawowy;
    private WykresWalutyPodstawowy wykresWalutyPodstawowy;
    private UstawieniaPlik ustawieniaPlik = new UstawieniaPlik();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       //Konfiguracja
        ustawieniaPlik.cratePlikUstawienia();
        ustawieniaPlik.WczytajUstawienia();

        //***********************************************************************************   Notowania
        this.WIG20();

        //************************************************************************************      Waluty
        this.WALUTYNBP();

        //**************************************************************************** Odswiezanie
        Timeline odswiezanie = new Timeline(new KeyFrame(javafx.util.Duration.millis(100), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                WALUTYNBPOdswiezanie();

                WIG20Odswiezanie();

            }
        }));
        odswiezanie.setCycleCount(Timeline.INDEFINITE);
        odswiezanie.play();


        //**************************************************************************        Aktualizacja
        Timeline AktualizacjaTimer = new Timeline(new KeyFrame(javafx.util.Duration.millis(5000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ustawieniaPlik.SprawdzAktualnoscBazDanychNaDzien();
                WIG20Aktualizacja();
                WALUTYNBPAktualizacja();
            }
        }));
        AktualizacjaTimer.setCycleCount(Timeline.INDEFINITE);
        AktualizacjaTimer.play();


        //***********************************************************************                  Menu

        this.Menu();

    }

    private void setCheckBox(){
        OtwarcieSNSCheck.setSelected(true);
        ZamkniecieSNSCheck.setSelected(true);
        MaxSNSCheck.setSelected(true);
        MinSNSCheck.setSelected(true);
        CheckBoxWolumen.setSelected(true);
        CheckBoxObrot.setSelected(true);
    }

    private void Menu(){

        MenuItemPomocInformacje.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Komendy.add(CzasSystemowy.format(new Date())+">> Otwarcie okna Informacje");
                try{
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InformacjeOkno.fxml"));
                    Parent root = (Parent)fxmlLoader.load();
                    Stage stage  = new Stage();
                    stage.setTitle("Informacje");
                    stage.setResizable(false);
                    stage.setScene(new Scene(root,520,381));
                    stage.getScene().getStylesheets().add("vip.css");
                    stage.show();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        WIG20ArchiwumMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Komendy.add(CzasSystemowy.format(new Date())+">> Otwarcie okna Pobieranie archiwum WIG20");
                try{
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WIG20ArchiwumOkno.fxml"));
                    Parent root = (Parent)fxmlLoader.load();
                    Stage stage  = new Stage();
                    stage.setTitle("Pobieranie WIG20");
                    stage.setResizable(false);
                    stage.setScene(new Scene(root,687,400));
                    stage.getScene().getStylesheets().add("vip.css");
                    stage.show();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        MenuItemPelneInfoArchiwum.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Komendy.add(CzasSystemowy.format(new Date())+">> Otwarcie okna z informacjami WIG20");
                try{
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WIG20ArchiwumInformacjeOkno.fxml"));
                    Parent root = (Parent)fxmlLoader.load();
                    Stage stage  = new Stage();
                    stage.setTitle("WIG20 Informacje baza danych");
                    stage.setResizable(false);
                    stage.setScene(new Scene(root,807,554));
                    stage.getScene().getStylesheets().add("vip.css");
                    stage.show();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        MenuOpcje.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Komendy.add(CzasSystemowy.format(new Date())+">> Otwarcie okna z Opcjami");
                try{
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OpcjeOkno.fxml"));
                    Parent root = (Parent)fxmlLoader.load();
                    Stage stage  = new Stage();
                    stage.setTitle("Opcje");
                    stage.setResizable(false);
                    stage.setScene(new Scene(root,628,290));
                    stage.getScene().getStylesheets().add("vip.css");
                    stage.show();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        PobieranieWalutMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Komendy.add(CzasSystemowy.format(new Date())+">> Otwarcie okna Pobieranie archiwum Walut");
                try{
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WalutyArchiwumOkno.fxml"));
                    Parent root = (Parent)fxmlLoader.load();
                    Stage stage  = new Stage();
                    stage.setTitle("Pobieranie Walut z NBP");
                    stage.setResizable(false);
                    stage.setScene(new Scene(root,687,400));
                    stage.getScene().getStylesheets().add("vip.css");
                    stage.show();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        MenuItemArchiwumWaluty.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Komendy.add(CzasSystemowy.format(new Date())+">> Otwarcie okna z informacjami Waluty NBP");
                try{
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ArchiwumWalutyInformacjeOkno.fxml"));
                    Parent root = (Parent)fxmlLoader.load();
                    Stage stage  = new Stage();
                    stage.setTitle("Waluty Informacje Baza Danych");
                    stage.setResizable(false);
                    stage.setScene(new Scene(root,630,400));
                    stage.getScene().getStylesheets().add("vip.css");
                    stage.show();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        MenuItemSkryptSciLab.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Komendy.add(CzasSystemowy.format(new Date())+">> Otwarcie okna Skrypt SciLab");
                try{
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SkryptSciLabOkno.fxml"));
                    Parent root = (Parent)fxmlLoader.load();
                    Stage stage  = new Stage();
                    stage.setTitle("Skrypt SciLab");
                    stage.setResizable(false);
                    stage.setScene(new Scene(root,550,148));
                    stage.getScene().getStylesheets().add("vip.css");
                    stage.show();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void WIG20(){
        this.setCheckBox();
        PrzesTextFieldSNS.setText("5");

        DataOdSNSL.setEditable(false);
        DataOdSNSR.setEditable(false);
        DataDoSNSL.setEditable(false);
        DataDoSNSR.setEditable(false);

        wykresWIG20Podstawowy =  new WykresWIG20Podstawowy(PanelSNSLin,BarPanelSNS);
        wykresWIG20Podstawowy.setCheckBox(OtwarcieSNSCheck,ZamkniecieSNSCheck,MaxSNSCheck,MinSNSCheck,CheckBoxWolumen,CheckBoxObrot,CiaglySNSCheck);


        KonsolaWidok.setItems(Komendy);
        KonsolaWidok.setEditable(false);
        OstatniMiesiacCheck.setToggleGroup(togRadioWczytywanie);
        OstatniRokCheck.setToggleGroup(togRadioWczytywanie);
        CaleArchiwumCheck.setToggleGroup(togRadioWczytywanie);
        OstatniMiesiacCheck.setSelected(true);

        KolumnaDo.setCellValueFactory(new PropertyValueFactory<WIG20.WIG20Tabela,String>("DataDo"));
        KolumnaOd.setCellValueFactory(new PropertyValueFactory<WIG20.WIG20Tabela,String>("DataOd"));
        KolumnaTicker.setCellValueFactory(new PropertyValueFactory<WIG20.WIG20Tabela,String>("Ticker"));
        KolumnaSredniNotowania.setCellValueFactory(new PropertyValueFactory<WIG20.WIG20Tabela,String>("SredniKurs"));
        //************************************************************************ Inicjalizacja

        wig20.WczytajWIG20();
        ComboBoxSpolki.setItems(wig20.getWIG20Ticker());
        ComboBoxSpolki.setValue(wig20.getWIG20Ticker().get(0));

        wig20.WczytajPodstawoweInfoWIG20();
        TabelaSpolek.setItems(wig20.wig20Tabela);
        TabelaSpolek.setEditable(false);

        wykresWIG20Podstawowy.addSpolki(wig20.getWIG20Ticker());
        wykresWIG20Podstawowy.setPelnaNazwa(wig20.getWIG20PelnaNazwa());
        wykresWIG20Podstawowy.setData(wig20.getDataOdArchiwum(),wig20.getDataDoArchiwum());

        Komendy.add(CzasSystemowy.format(new Date())+">> Uruchomiono Program");

        if(!ustawieniaPlik.ustawienia.WIG20WyswietlaniePunktow) {
            if (ustawieniaPlik.ustawienia.WIG20OstatniMiesiac) wykresWIG20Podstawowy.WyswietlPunkty();
            else wykresWIG20Podstawowy.UsunPunkty();
        }

        ComboBoxSpolki.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String ticker = ComboBoxSpolki.getValue();
                wykresWIG20Podstawowy.WybierzSpolke(ComboBoxSpolki.getValue());
                WatekWczytywaniaZSQL watekWczytywaniaZSQL = new WatekWczytywaniaZSQL(ticker,wykresWIG20Podstawowy);
                watekWczytywaniaZSQL.setTrybDane(OstatniMiesiacCheck.isSelected(),OstatniRokCheck.isSelected(),CaleArchiwumCheck.isSelected());
                Thread watek = new Thread(watekWczytywaniaZSQL);
                watek.start();
            }
        });

        OstatniMiesiacCheck.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

               if(!ustawieniaPlik.ustawienia.WIG20WyswietlaniePunktow) {
                   if (ustawieniaPlik.ustawienia.WIG20OstatniMiesiac) wykresWIG20Podstawowy.WyswietlPunkty();
                   else wykresWIG20Podstawowy.UsunPunkty();
               }

                String ticker = ComboBoxSpolki.getValue();
                wykresWIG20Podstawowy.WybierzSpolke(ComboBoxSpolki.getValue());
                WatekWczytywaniaZSQL watekWczytywaniaZSQL = new WatekWczytywaniaZSQL(ticker, wykresWIG20Podstawowy);
                watekWczytywaniaZSQL.setTrybDane(OstatniMiesiacCheck.isSelected(), OstatniRokCheck.isSelected(), CaleArchiwumCheck.isSelected());
                Thread watek = new Thread(watekWczytywaniaZSQL);
                watek.start();

            }
        });

        OstatniRokCheck.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(!ustawieniaPlik.ustawienia.WIG20WyswietlaniePunktow) {
                    if (ustawieniaPlik.ustawienia.WIG20OstatniRok) wykresWIG20Podstawowy.WyswietlPunkty();
                    else wykresWIG20Podstawowy.UsunPunkty();
                }

                String ticker = ComboBoxSpolki.getValue();
                wykresWIG20Podstawowy.WybierzSpolke(ComboBoxSpolki.getValue());
                WatekWczytywaniaZSQL watekWczytywaniaZSQL = new WatekWczytywaniaZSQL(ticker, wykresWIG20Podstawowy);
                watekWczytywaniaZSQL.setTrybDane(OstatniMiesiacCheck.isSelected(), OstatniRokCheck.isSelected(), CaleArchiwumCheck.isSelected());
                Thread watek = new Thread(watekWczytywaniaZSQL);
                watek.start();

            }
        });

        CaleArchiwumCheck.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(!ustawieniaPlik.ustawienia.WIG20WyswietlaniePunktow){
                    if(ustawieniaPlik.ustawienia.WIG20CaleArchiwum)wykresWIG20Podstawowy.WyswietlPunkty();
                    else wykresWIG20Podstawowy.UsunPunkty();
                }

                String ticker = ComboBoxSpolki.getValue();
                wykresWIG20Podstawowy.WybierzSpolke(ComboBoxSpolki.getValue());
                WatekWczytywaniaZSQL watekWczytywaniaZSQL = new WatekWczytywaniaZSQL(ticker, wykresWIG20Podstawowy);
                watekWczytywaniaZSQL.setTrybDane(OstatniMiesiacCheck.isSelected(), OstatniRokCheck.isSelected(), CaleArchiwumCheck.isSelected());
                Thread watek = new Thread(watekWczytywaniaZSQL);
                watek.start();

            }
        });

        WyswietlWykButtonSNS.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // wykresWIG20Podstawowy.przepiszDane(DataOdWykresSNS.getValue().toString(),DataDoWykresSNS.getValue().toString(),1);
                wykresWIG20Podstawowy.SpolkiLista.get(wykresWIG20Podstawowy.getIndexSpolki()).PrzepiszDane(DataOdWykresSNS.getValue().toString(),DataDoWykresSNS.getValue().toString(),1);
                wykresWIG20Podstawowy.WyswietlWykres();


                DataOdSNSL.setText(wykresWIG20Podstawowy.SpolkiLista.get(wykresWIG20Podstawowy.getIndexSpolki()).getMinData());
                DataOdSNSR.setText(wykresWIG20Podstawowy.SpolkiLista.get(wykresWIG20Podstawowy.getIndexSpolki()).getMaxData());
                DataDoSNSL.setText(wykresWIG20Podstawowy.SpolkiLista.get(wykresWIG20Podstawowy.getIndexSpolki()).getMinData());
                DataDoSNSR.setText(wykresWIG20Podstawowy.SpolkiLista.get(wykresWIG20Podstawowy.getIndexSpolki()).getMaxData());

                int indexod = wykresWIG20Podstawowy.SpolkiLista.get(wykresWIG20Podstawowy.getIndexSpolki()).getIndexDataOd();
                int indexdo = wykresWIG20Podstawowy.SpolkiLista.get(wykresWIG20Podstawowy.getIndexSpolki()).getIndexDataDo();

                SliderOdSNS.setMin(indexod);
                SliderOdSNS.setMax(indexdo);
                SliderDoSNS.setMin(indexod);
                SliderDoSNS.setMax(indexdo);

                SliderOdSNS.setValue(indexod);
                SliderDoSNS.setValue(indexdo);
            }
        });

        PrzesLButSNS.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                wykresWIG20Podstawowy.przesunWykresLewo(Integer.valueOf(PrzesTextFieldSNS.getText()));
                wykresWIG20Podstawowy.WyswietlWykres();

                int indexod = wykresWIG20Podstawowy.SpolkiLista.get(wykresWIG20Podstawowy.getIndexSpolki()).getIndexDataOd();
                int indexdo = wykresWIG20Podstawowy.SpolkiLista.get(wykresWIG20Podstawowy.getIndexSpolki()).getIndexDataDo();

                SliderOdSNS.setMin(indexod);
                SliderOdSNS.setMax(indexdo);
                SliderDoSNS.setMin(indexod);
                SliderDoSNS.setMax(indexdo);

                SliderOdSNS.setValue(indexod);
                SliderDoSNS.setValue(indexdo);
            }
        });

        PrzesPButSNS.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                wykresWIG20Podstawowy.przesunWykresPrawo(Integer.valueOf(PrzesTextFieldSNS.getText()));
                wykresWIG20Podstawowy.WyswietlWykres();

                int indexod = wykresWIG20Podstawowy.SpolkiLista.get(wykresWIG20Podstawowy.getIndexSpolki()).getIndexDataOd();
                int indexdo = wykresWIG20Podstawowy.SpolkiLista.get(wykresWIG20Podstawowy.getIndexSpolki()).getIndexDataDo();

                SliderOdSNS.setMin(indexod);
                SliderOdSNS.setMax(indexdo);
                SliderDoSNS.setMin(indexod);
                SliderDoSNS.setMax(indexdo);

                SliderOdSNS.setValue(indexod);
                SliderDoSNS.setValue(indexdo);
            }
        });

        OtwarcieSNSCheck.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                wykresWIG20Podstawowy.WyswietlWykres();
            }
        });

        ZamkniecieSNSCheck.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                wykresWIG20Podstawowy.WyswietlWykres();
            }
        });

        MaxSNSCheck.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                wykresWIG20Podstawowy.WyswietlWykres();
            }
        });

        MinSNSCheck.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                wykresWIG20Podstawowy.WyswietlWykres();
            }
        });

        CheckBoxWolumen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                wykresWIG20Podstawowy.WyswietlWykres();
            }
        });

        CheckBoxObrot.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                wykresWIG20Podstawowy.WyswietlWykres();
            }
        });

        SliderOdSNS.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //  if(!CaleArchiwumCheck.isSelected()) {
                SliderDoSNS.setMin(SliderOdSNS.getValue());
                int x1=wykresWIG20Podstawowy.SpolkiLista.get(wykresWIG20Podstawowy.getIndexSpolki()).getIndexDataDo();
                int x2=wykresWIG20Podstawowy.SpolkiLista.get(wykresWIG20Podstawowy.getIndexSpolki()).getIndexDataOd();


                int dlugos =x1-x2;
                // double dlugos = SliderDoSNS.getValue()-SliderOdSNS.getValue();
                int odstemp = (int)dlugos/ustawieniaPlik.ustawienia.WIG20MaxIloscDanychWykres;
                if(odstemp<=1)odstemp=1;

                wykresWIG20Podstawowy.SpolkiLista.get(wykresWIG20Podstawowy.getIndexSpolki()).WyswietlWykres((int)SliderOdSNS.getValue(),(int)SliderDoSNS.getValue(),odstemp);
                wykresWIG20Podstawowy.SpolkiLista.get(wykresWIG20Podstawowy.getIndexSpolki()).WyswietlWykres();
                DataOdSNSR.setText(wykresWIG20Podstawowy.SpolkiLista.get(wykresWIG20Podstawowy.getIndexSpolki()).getMinData());
                DataDoSNSL.setText(wykresWIG20Podstawowy.SpolkiLista.get(wykresWIG20Podstawowy.getIndexSpolki()).getMinData());

                if(ustawieniaPlik.ustawienia.WIG20WyswietlaniePunktow){
                    if(dlugos<=ustawieniaPlik.ustawienia.WIG20WyswietlonePunkty)wykresWIG20Podstawowy.WyswietlPunkty();
                    else wykresWIG20Podstawowy.UsunPunkty();
                }

                //     }

            }
        });

        SliderDoSNS.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                //  if(!CaleArchiwumCheck.isSelected()) {
                SliderOdSNS.setMax(SliderDoSNS.getValue());

                int x1=wykresWIG20Podstawowy.SpolkiLista.get(wykresWIG20Podstawowy.getIndexSpolki()).getIndexDataDo();
                int x2=wykresWIG20Podstawowy.SpolkiLista.get(wykresWIG20Podstawowy.getIndexSpolki()).getIndexDataOd();
                int dlugos =x1-x2;

                int odstemp = dlugos/ustawieniaPlik.ustawienia.WIG20MaxIloscDanychWykres;
                if(odstemp<=1)odstemp=1;


                wykresWIG20Podstawowy.SpolkiLista.get(wykresWIG20Podstawowy.getIndexSpolki()).WyswietlWykres((int)SliderOdSNS.getValue(),(int)SliderDoSNS.getValue(),odstemp);
                wykresWIG20Podstawowy.SpolkiLista.get(wykresWIG20Podstawowy.getIndexSpolki()).WyswietlWykres();
                DataDoSNSR.setText(wykresWIG20Podstawowy.SpolkiLista.get(wykresWIG20Podstawowy.getIndexSpolki()).getMaxData());
                //   }
                if(ustawieniaPlik.ustawienia.WIG20WyswietlaniePunktow){
                    if(dlugos<=ustawieniaPlik.ustawienia.WIG20WyswietlonePunkty)wykresWIG20Podstawowy.WyswietlPunkty();
                    else wykresWIG20Podstawowy.UsunPunkty();
                }

            }
        });

    }

    private void WIG20Odswiezanie(){
        ProgBarWczytywanie.setProgress(WatekWczytywaniaZSQL.Progres);
        ProgIndWczytywanie.setProgress(WatekWczytywaniaZSQL.Progres);
        if(WatekWczytywaniaZSQL.Progres == 1.0){
            Komendy.add(CzasSystemowy.format(new Date())+">> Wczytano spolke: "+ComboBoxSpolki.getValue()+" o rozmiarze: "+(int)WatekWczytywaniaZSQL.iloscDanych);
            wykresWIG20Podstawowy.SpolkiLista.get(wykresWIG20Podstawowy.getIndexSpolki()).WyswietlWykres();
            SliderOdSNS.setMin(0);
            SliderDoSNS.setMin(0);
            SliderDoSNS.setMax(WatekWczytywaniaZSQL.iloscDanych-1);
            SliderOdSNS.setMax(WatekWczytywaniaZSQL.iloscDanych-1);
            SliderOdSNS.setValue(0);
            SliderDoSNS.setValue(WatekWczytywaniaZSQL.iloscDanych-1);

            DataOdSNSL.setText(wykresWIG20Podstawowy.SpolkiLista.get(wykresWIG20Podstawowy.getIndexSpolki()).getMinData());
            DataOdSNSR.setText(wykresWIG20Podstawowy.SpolkiLista.get(wykresWIG20Podstawowy.getIndexSpolki()).getMaxData());
            DataDoSNSL.setText(wykresWIG20Podstawowy.SpolkiLista.get(wykresWIG20Podstawowy.getIndexSpolki()).getMinData());
            DataDoSNSR.setText(wykresWIG20Podstawowy.SpolkiLista.get(wykresWIG20Podstawowy.getIndexSpolki()).getMaxData());
            WatekWczytywaniaZSQL.Progres=1.1;

        }


        if(wig20.infoPobieranie.ustawionoKomende){

            if(wig20.infoPobieranie.Przetwarzanie){
                Komendy.remove(Komendy.size()-1);
                Komendy.add(CzasSystemowy.format(new Date())+">>"+wig20.infoPobieranie.getKomenda());
                wig20.infoPobieranie.Przetwarzanie=false;
            }
            else Komendy.add(CzasSystemowy.format(new Date())+">>"+wig20.infoPobieranie.getKomenda());
        }

        if(WIG20.aktualizacjaKomenda){
            wig20.WczytajWIG20();
            wig20.WczytajPodstawoweInfoWIG20();

            Komendy.add(CzasSystemowy.format(new Date())+">>Zaktualizowano archiwum WIG20");
            WIG20.aktualizacjaKomenda=false;
        }
    }

    private void WIG20Aktualizacja(){
        if(!ustawieniaPlik.ustawienia.AktualnaBazaWIG20NaDzien){
            if(ustawieniaPlik.ustawienia.WIG20AutoAktualizacja){
                if(ustawieniaPlik.ustawienia.WIG20AutoAktualizacjaPoGodzienie){
                    int czas = ustawieniaPlik.ustawienia.WIG20GodzinaAktualizacji.compareTo(ustawieniaPlik.getAktualnyCzas());
                    if(czas<=0) {
                            if(WIG20.aktualizacja){
                                WIG20.aktualizacja=false;
                                wig20.AktualizujBazeDanych();
                            }
                    }
                }
            }
        }

        if(ustawieniaPlik.ustawienia.WIG20AutoAktualizacja){
            if(ustawieniaPlik.ustawienia.WIG20AutoAktualizacjaPoUruchomieniu){
                if(WIG20.aktualizacja){
                    WIG20.aktualizacja=false;
                    wig20.AktualizujBazeDanych();
                }
            }
        }

    }

    private void WALUTYNBP(){
        walutyNBP.WczytajPodsawoweDaneWaluty();

        KolumnaWaluty.setCellValueFactory(new PropertyValueFactory<WalutyNBP.WalutyDane,String>("KodWaluty"));
        KolumnaOdWaluty.setCellValueFactory(new PropertyValueFactory<WalutyNBP.WalutyDane,String>("DataOd"));
        KolumnaDoWaluty.setCellValueFactory(new PropertyValueFactory<WalutyNBP.WalutyDane,String>("DataDo"));
        KolumnaNazwa.setCellValueFactory(new PropertyValueFactory<WalutyNBP.WalutyDane,String>("NazwaWaluty"));

        TabelaWaluty.setItems(walutyNBP.walutyPodstawoweDane);
        TabelaWaluty.setEditable(false);

        ComboBoxWaluty.setItems(walutyNBP.getListaKodyWalut());
        ComboBoxWaluty.setValue(walutyNBP.walutyPodstawoweDane.get(0).getKodWaluty());

        TextDoWalutyL.setEditable(false);
        TextOdWalutyL.setEditable(false);
        TextOdWalutyR.setEditable(false);
        TextDoWalutyR.setEditable(false);

        TextPrzesunWaluty.setText("5");

        //***********************************************************************************
        OstatniRokCheckWaluty.setToggleGroup(tohRadioWaluty);
        CaleArchiwumCheckWaluty.setToggleGroup(tohRadioWaluty);
        OstatniMiesiacCheckWaluty.setToggleGroup(tohRadioWaluty);
        OstatniMiesiacCheckWaluty.setSelected(true);


        //*************************************************************************     WYKRES
        CheckBoxKupnoWaluty.setSelected(true);
        CheckBoxSprzedazWaluty.setSelected(true);

        wykresWalutyPodstawowy = new WykresWalutyPodstawowy(PanelWaluty);
        wykresWalutyPodstawowy.setCheckBoksyWaluty(CheckBoxKupnoWaluty,CheckBoxSprzedazWaluty,CheckBoxPrzelicznikWaluty);

        wykresWalutyPodstawowy.addWaluty(walutyNBP.getListaKodyWalut());
        wykresWalutyPodstawowy.setData(walutyNBP.getDataOd(),walutyNBP.getDataDo());

        if(!ustawieniaPlik.ustawienia.WalutyWyswietlaniePunktow){
            if(ustawieniaPlik.ustawienia.WalutyOstatniMiesiac)wykresWalutyPodstawowy.WyswietlPunkty();
            else wykresWalutyPodstawowy.UsunPunkty();
        }

        OstatniMiesiacCheckWaluty.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!ustawieniaPlik.ustawienia.WalutyWyswietlaniePunktow){
                    if(ustawieniaPlik.ustawienia.WalutyOstatniMiesiac)wykresWalutyPodstawowy.WyswietlPunkty();
                    else wykresWalutyPodstawowy.UsunPunkty();
                }
            }
        });

        OstatniRokCheckWaluty.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!ustawieniaPlik.ustawienia.WalutyWyswietlaniePunktow){
                    if(ustawieniaPlik.ustawienia.WalutyOstatniRok)wykresWalutyPodstawowy.WyswietlPunkty();
                    else wykresWalutyPodstawowy.UsunPunkty();
                }
            }
        });

        CaleArchiwumCheckWaluty.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!ustawieniaPlik.ustawienia.WalutyWyswietlaniePunktow){
                    if(ustawieniaPlik.ustawienia.WalutyCaleArchiwum)wykresWalutyPodstawowy.WyswietlPunkty();
                    else wykresWalutyPodstawowy.UsunPunkty();
                }
            }
        });

        ComboBoxWaluty.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean wyswietlony = wykresWalutyPodstawowy.isWyswietlony(ComboBoxWaluty.getValue());

                if(wyswietlony)DodajUsunWalutyButton.setText("Usun");
                else DodajUsunWalutyButton.setText("Dodaj");

            }
        });

        DodajUsunWalutyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean wyswietlony = wykresWalutyPodstawowy.isWyswietlony(ComboBoxWaluty.getValue());


                if(wyswietlony){
                    DodajUsunWalutyButton.setText("Dodaj");
                    wykresWalutyPodstawowy.usunWykres(ComboBoxWaluty.getValue());
                }
                else{
                    DodajUsunWalutyButton.setText("Usun");
                   // wykresWalutyPodstawowy.WyswietlWalute(ComboBoxWaluty.getValue());
                    WatekWczytywaniaWalutZSQL watekWczytywaniaWalutZSQL = new WatekWczytywaniaWalutZSQL(ComboBoxWaluty.getValue(),wykresWalutyPodstawowy);
                    watekWczytywaniaWalutZSQL.setTrybDane(OstatniRokCheckWaluty.isSelected(),OstatniMiesiacCheckWaluty.isSelected(),CaleArchiwumCheckWaluty.isSelected());

                    Thread watek = new Thread(watekWczytywaniaWalutZSQL);
                    watek.start();
                }

            }
        });

        //********************************************************************      Boksy
        CheckBoxKupnoWaluty.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(wykresWalutyPodstawowy.isWyswietlony(ComboBoxWaluty.getValue())){
                    wykresWalutyPodstawowy.WyswietlWalute(ComboBoxWaluty.getValue());
                }
            }
        });

        CheckBoxSprzedazWaluty.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(wykresWalutyPodstawowy.isWyswietlony(ComboBoxWaluty.getValue())){
                    wykresWalutyPodstawowy.WyswietlWalute(ComboBoxWaluty.getValue());
                }
            }
        });

        CheckBoxPrzelicznikWaluty.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(wykresWalutyPodstawowy.isWyswietlony(ComboBoxWaluty.getValue())){
                    wykresWalutyPodstawowy.WyswietlWalute(ComboBoxWaluty.getValue());
                }
            }
        });
        //****************************************************************************      Wykres Obsluga

        SliderOdWaluty.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
              //  if(!CaleArchiwumCheckWaluty.isSelected()) {
                    SliderDoWaluty.setMin(SliderOdWaluty.getValue());

                    wykresWalutyPodstawowy.setKodyWalutWyswietlone();

                    //int x1 = wykresWalutyPodstawowy.getMaxDataIndexWyswietlone();
                    //int x2 = wykresWalutyPodstawowy.getMaxDataIndexWyswietlone();
                    int x1 = (int)SliderDoWaluty.getValue();
                    int x2= (int) SliderOdWaluty.getValue();

                    int dlugosc = x1 - x2;
                    int odstemp = (int) dlugosc / ustawieniaPlik.ustawienia.Waluty0MaxIloscDanychWykres;
                    if (odstemp <= 1) {
                        odstemp = 1;
                    }

                    wykresWalutyPodstawowy.PrzepiszWyswietloneWykresy((int) SliderOdWaluty.getValue(), (int) SliderDoWaluty.getValue(), odstemp);
                    TextOdWalutyR.setText(wykresWalutyPodstawowy.getMinDataWyswietlone());
                    TextDoWalutyL.setText(wykresWalutyPodstawowy.getMinDataWyswietlone());

                    if(ustawieniaPlik.ustawienia.WalutyWyswietlaniePunktow){
                        if(dlugosc<=ustawieniaPlik.ustawienia.WalutyWyswietlonePunkty)wykresWalutyPodstawowy.WyswietlPunkty();
                        else wykresWalutyPodstawowy.UsunPunkty();
                    }

              //  }
            }
        });

        SliderDoWaluty.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
           //     if(!CaleArchiwumCheckWaluty.isSelected()) {
                    SliderOdWaluty.setMax(SliderDoWaluty.getValue());

                    wykresWalutyPodstawowy.setKodyWalutWyswietlone();

                    int x1 = (int)SliderDoWaluty.getValue();
                    int x2 = (int)SliderOdWaluty.getValue();
                    int dlugosc = x1 - x2;
                    int odstemp = (int) dlugosc / ustawieniaPlik.ustawienia.Waluty0MaxIloscDanychWykres;
                    if (odstemp <= 1) {
                        odstemp = 1;
                    }

                    wykresWalutyPodstawowy.PrzepiszWyswietloneWykresy((int) SliderOdWaluty.getValue(), (int) SliderDoWaluty.getValue(), odstemp);

                    TextDoWalutyR.setText(wykresWalutyPodstawowy.getMaxDataWyswietlone());
                    if(ustawieniaPlik.ustawienia.WalutyWyswietlaniePunktow){
                    if(dlugosc<=ustawieniaPlik.ustawienia.WalutyWyswietlonePunkty)wykresWalutyPodstawowy.WyswietlPunkty();
                    else wykresWalutyPodstawowy.UsunPunkty();
                }
           //     }
            }
        });

        //*****************************************************************************     Wyk cale archwium
        ButtonWyswietlWaluty.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                wykresWalutyPodstawowy.setKodyWalutWyswietlone();


                wykresWalutyPodstawowy.PrzepiszWyswietloneWykresy(DataOdWaluty.getValue().toString(),DataDoWaluty.getValue().toString(),1);

                TextOdWalutyL.setText(wykresWalutyPodstawowy.getMinDataWyswietlone());
                TextOdWalutyR.setText(wykresWalutyPodstawowy.getMaxDataWyswietlone());

                TextDoWalutyL.setText(wykresWalutyPodstawowy.getMinDataWyswietlone());
                TextDoWalutyR.setText(wykresWalutyPodstawowy.getMaxDataWyswietlone());

                int indexod = wykresWalutyPodstawowy.getMinDataIndexWyswietlone();
                int indexdo = wykresWalutyPodstawowy.getMaxDataIndexWyswietlone();

                SliderOdWaluty.setMin(indexod);
                SliderOdWaluty.setMax(indexdo);

                SliderDoWaluty.setMin(indexod);
                SliderDoWaluty.setMax(indexdo);

                SliderOdWaluty.setValue(indexod);
                SliderDoWaluty.setValue(indexdo);
            }
        });

        ButtonPrzesunLewoWaluty.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                wykresWalutyPodstawowy.setKodyWalutWyswietlone();
                wykresWalutyPodstawowy.PrzesunWykresyLewo(Integer.valueOf(TextPrzesunWaluty.getText()));


                TextOdWalutyL.setText(wykresWalutyPodstawowy.getMinDataWyswietlone());
                TextOdWalutyR.setText(wykresWalutyPodstawowy.getMaxDataWyswietlone());

                TextDoWalutyL.setText(wykresWalutyPodstawowy.getMinDataWyswietlone());
                TextDoWalutyR.setText(wykresWalutyPodstawowy.getMaxDataWyswietlone());

                int indexod = wykresWalutyPodstawowy.getMinDataIndexWyswietlone();
                int indexdo = wykresWalutyPodstawowy.getMaxDataIndexWyswietlone();

                SliderOdWaluty.setMin(indexod);
                SliderOdWaluty.setMax(indexdo);

                SliderDoWaluty.setMin(indexod);
                SliderDoWaluty.setMax(indexdo);

                SliderOdWaluty.setValue(indexod);
                SliderDoWaluty.setValue(indexdo);
            }
        });

        ButtonPrzesunPrawoWaluty.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                wykresWalutyPodstawowy.setKodyWalutWyswietlone();
                wykresWalutyPodstawowy.PrzesunWykresyPrawo(Integer.valueOf(TextPrzesunWaluty.getText()));

                TextOdWalutyL.setText(wykresWalutyPodstawowy.getMinDataWyswietlone());
                TextOdWalutyR.setText(wykresWalutyPodstawowy.getMaxDataWyswietlone());

                TextDoWalutyL.setText(wykresWalutyPodstawowy.getMinDataWyswietlone());
                TextDoWalutyR.setText(wykresWalutyPodstawowy.getMaxDataWyswietlone());

                int indexod = wykresWalutyPodstawowy.getMinDataIndexWyswietlone();
                int indexdo = wykresWalutyPodstawowy.getMaxDataIndexWyswietlone();

                SliderOdWaluty.setMin(indexod);
                SliderOdWaluty.setMax(indexdo);

                SliderDoWaluty.setMin(indexod);
                SliderDoWaluty.setMax(indexdo);

                SliderOdWaluty.setValue(indexod);
                SliderDoWaluty.setValue(indexdo);
            }
        });

        WyczyscWykresWalutyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               wykresWalutyPodstawowy.setKodyWalutWyswietlone();
                wykresWalutyPodstawowy.usunDaneZWykresu();

                DodajUsunWalutyButton.setText("Dodaj");
            }
        });

    }

    private void WALUTYNBPOdswiezanie(){
        ProgBarWczytywanieWaluty.setProgress(WatekWczytywaniaWalutZSQL.Progres);
        ProgIndWczytywanieWaluty.setProgress(WatekWczytywaniaWalutZSQL.Progres);
        if(WatekWczytywaniaWalutZSQL.Progres==1.0){
            Komendy.add(CzasSystemowy.format(new Date())+">> Wczytano walute:"+ComboBoxWaluty.getValue()+" o rozmiarze: "+WatekWczytywaniaWalutZSQL.iloscDanych);
            wykresWalutyPodstawowy.WyswietlWalute(ComboBoxWaluty.getValue());
            SliderOdWaluty.setMin(0);
            SliderDoWaluty.setMin(0);
            SliderDoWaluty.setMax(WatekWczytywaniaWalutZSQL.iloscDanych-1);
            SliderOdWaluty.setMax(WatekWczytywaniaWalutZSQL.iloscDanych-1);
            SliderOdWaluty.setValue(0);
            SliderDoWaluty.setValue(WatekWczytywaniaWalutZSQL.iloscDanych-1);

            int index = wykresWalutyPodstawowy.getIndexByKodWaluty(ComboBoxWaluty.getValue());

            TextOdWalutyL.setText(wykresWalutyPodstawowy.ListaWalut.get(index).getMinData());
            TextOdWalutyR.setText(wykresWalutyPodstawowy.ListaWalut.get(index).getMinData());

            TextDoWalutyL.setText(wykresWalutyPodstawowy.ListaWalut.get(index).getMinData());
            TextDoWalutyR.setText(wykresWalutyPodstawowy.ListaWalut.get(index).getMaxData());

            WatekWczytywaniaWalutZSQL.Progres=1.1;
        }

        if(walutyNBP.infoPobieranie.ustawionoKomende){

            if(walutyNBP.infoPobieranie.Przetwarzanie){
                Komendy.remove(Komendy.size()-1);
                Komendy.add(CzasSystemowy.format(new Date())+">>"+walutyNBP.infoPobieranie.getKomenda());
                walutyNBP.infoPobieranie.Przetwarzanie=false;
            }
            else Komendy.add(CzasSystemowy.format(new Date())+">>"+walutyNBP.infoPobieranie.getKomenda());
        }


        if(WalutyNBP.zaktualizowanoWaluty){
            walutyNBP.WczytajPodsawoweDaneWaluty();
            WalutyNBP.zaktualizowanoWaluty=false;
            Komendy.add(CzasSystemowy.format(new Date())+">> Zaktualizowano archiwum walut");
        }


    }

    private void WALUTYNBPAktualizacja(){
        if(!ustawieniaPlik.ustawienia.AktualnaBazaWalutNaDzien){
            if(ustawieniaPlik.ustawienia.WalutyAutoAktualizacja){
                if(ustawieniaPlik.ustawienia.AktualnaBazaWIG20NaDzien){
                    if(ustawieniaPlik.ustawienia.WalutyAutoAktualizacjaPoUruchomieniu){
                        if(WalutyNBP.aktualizacja){
                            WalutyNBP.aktualizacja=false;
                            walutyNBP.aktualizujWalutyBazaDanych();
                        }
                    }
                    if(ustawieniaPlik.ustawienia.WalutyAutoAktualizacjaPoGodzienie){
                     int czas = ustawieniaPlik.ustawienia.WalutyGodzinaAktualizacji.compareTo(ustawieniaPlik.getAktualnyCzas());
                        if(czas<=0){
                            if(WalutyNBP.aktualizacja){
                                WalutyNBP.aktualizacja=false;
                                walutyNBP.aktualizujWalutyBazaDanych();
                            }
                        }
                    }
                }else if(ustawieniaPlik.ustawienia.WIG20AutoAktualizacjaPoGodzienie) {
                    int czaswig = ustawieniaPlik.ustawienia.WIG20GodzinaAktualizacji.compareTo(ustawieniaPlik.getAktualnyCzas());
                        if(czaswig>0) {
                            if (ustawieniaPlik.ustawienia.WalutyAutoAktualizacjaPoUruchomieniu) {

                                String[] tmpczas = ustawieniaPlik.getAktualnaData().split(":");
                                String[] tmpWIG = ustawieniaPlik.ustawienia.WIG20GodzinaAktualizacji.split(":");
                                if (tmpczas[0].equals(tmpWIG[0])) {
                                    int minutyczas = Integer.valueOf(tmpczas[1]);
                                    int minutywig = Integer.valueOf(tmpWIG[1]);
                                    if ((minutywig-minutyczas) >= 5) {
                                        if(WalutyNBP.aktualizacja){
                                            WalutyNBP.aktualizacja=false;
                                            walutyNBP.aktualizujWalutyBazaDanych();
                                        }
                                    }
                                }else if(WalutyNBP.aktualizacja){
                                    WalutyNBP.aktualizacja=false;
                                    walutyNBP.aktualizujWalutyBazaDanych();
                                }
                            }

                            if(ustawieniaPlik.ustawienia.WalutyAutoAktualizacjaPoGodzienie){
                                int czaswaluty = ustawieniaPlik.ustawienia.WalutyGodzinaAktualizacji.compareTo(ustawieniaPlik.getAktualnyCzas());
                                if(czaswaluty<=0) {
                                    String[] tmpczas = ustawieniaPlik.getAktualnaData().split(":");
                                    String[] tmpWIG = ustawieniaPlik.ustawienia.WIG20GodzinaAktualizacji.split(":");
                                    if (tmpczas[0].equals(tmpWIG[0])) {
                                        int minutyczas = Integer.valueOf(tmpczas[1]);
                                        int minutywig = Integer.valueOf(tmpWIG[1]);
                                        if ((minutywig-minutyczas) >= 5) {
                                            if(WalutyNBP.aktualizacja){
                                                WalutyNBP.aktualizacja=false;
                                                walutyNBP.aktualizujWalutyBazaDanych();
                                            }

                                        }

                                    }else if(WalutyNBP.aktualizacja){
                                        WalutyNBP.aktualizacja=false;
                                        walutyNBP.aktualizujWalutyBazaDanych();
                                    }

                                }
                            }
                        }
                }
            }
        }

        //************************************************************************      Po kazdym uruchomieniu
        if(ustawieniaPlik.ustawienia.WalutyAutoAktualizacja){
            if(ustawieniaPlik.ustawienia.WalutyAutoAktualizacjaPoUruchomieniu){
                if(ustawieniaPlik.ustawienia.WIG20AutoAktualizacja && ustawieniaPlik.ustawienia.WIG20AutoAktualizacjaPoUruchomieniu){
                    if(WIG20.zaktualizowanoWIG20){
                        if(WalutyNBP.aktualizacja){
                            WalutyNBP.aktualizacja=false;
                            walutyNBP.aktualizujWalutyBazaDanych();
                        }
                    }
                }else if(WalutyNBP.aktualizacja){
                    WalutyNBP.aktualizacja=false;
                    walutyNBP.aktualizujWalutyBazaDanych();
                }
            }
        }

    }

}
