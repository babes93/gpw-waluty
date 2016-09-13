package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

/**
 * Created by patryk on 23.08.16.
 */
public class OpcjeController implements Initializable {


    @FXML
    private RadioButton WIG20AktualizacjaPoUruchomieniuOn;

    @FXML
    private TextField WalutyGodzinaAktualizacjiText;

    @FXML
    private RadioButton WalutyAutomatycznaAktualizacjaOff;

    @FXML
    private CheckBox WalutyWyswietlaniePunktowCaleArchiwum;

    @FXML
    private TextField WIG20GodzinaAktualizacjiText;

    @FXML
    private CheckBox WIG20WyswietlaniePunktowCaleArchiwum;

    @FXML
    private CheckBox WIG20WyswietalniePunktowMiesiac;

    @FXML
    private CheckBox WalutyWyswietalniePunktowMiesiac;

    @FXML
    private RadioButton WalutyAktualizacjaPoGodzienieOff;

    @FXML
    private RadioButton WIG20AktualizacjaPoUruchomieniuOff;

    @FXML
    private Button WIG20Zapisz;

    @FXML
    private RadioButton WalutyAktualizacjaPoUruchomieniuOn;

    @FXML
    private RadioButton WalutyAktualizacjaPoGodzinieOn;

    @FXML
    private RadioButton WalutyAktualizacjaPoUruchomieniuOff;

    @FXML
    private RadioButton WIG20AutomatycznaAktualizacjaOff;

    @FXML
    private CheckBox WalutyWyswietlaniePunktowRok;

    @FXML
    private RadioButton WIG20AktualizacjaPoGodzienieOff;

    @FXML
    private RadioButton WIG20AktualizacjaPoGodzinieOn;

    @FXML
    private RadioButton WalutyAutomatycznaAktualizacjaOn;

    @FXML
    private RadioButton WIG20WyswietlaniePunktowOn;

    @FXML
    private RadioButton WIG20WyswietlaniePunktowOff;

    @FXML
    private TextField WIG20WyswietlaniePunktowText;

    @FXML
    private TextField WalutyWyswietlPunktyText;

    @FXML
    private TextField DataOstatniejAktualizacjiWIG20;

    @FXML
    private TextField DataOstatniejAktualizacjiWaluty;

    @FXML
    private TextField WIG20MaxIloscDanychWykres;

    @FXML
    private TextField WalutyMaxIloscDanychWykres;

    @FXML
    private RadioButton WalutyWyswietlPunktyOn;

    @FXML
    private RadioButton WalutyWyswietlPunktyOff;

    @FXML
    private Button WalutyZapisz;

    @FXML
    private CheckBox WIG20WyswietlaniePunktowRok;

    @FXML
    private RadioButton WIG20AutomatycznaAktualizacjaOn;

    ToggleGroup GrupaWIG20AktualizacjaAutomatyczna = new ToggleGroup();
    ToggleGroup GrupaWIG20AktualizacjaPoUruchomieniu = new ToggleGroup();
    ToggleGroup GrupaWIG20AktualizacjaPoGodzienie = new ToggleGroup();
    ToggleGroup GrupaWIG20WyswietlaniePunktow = new ToggleGroup();



    ToggleGroup GrupaWalutyAktualizacjaAutomatyczna = new ToggleGroup();
    ToggleGroup GrupaWalutyAktualizacjaPoUruchomieniu = new ToggleGroup();
    ToggleGroup GrupaWalutyAktualizacjaPoGodzienie = new ToggleGroup();
    ToggleGroup GrupaWalutyWyswietlaniePunktow = new ToggleGroup();

    private UstawieniaPlik ustawieniaPlik = new UstawieniaPlik();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        INIT_ALL();

        //  **********************  Inicjalizacja
        WIG20_INIT();
        WALUTYNBP_INIT();

        //  ************************    Obsluga Kontrolek
        WIG20_OBSLUGA_KONTROLERK();
        WALUTY_OBSLUGA_KONTROLERK();

    }

    //*************************************************************************     Inicjalizacja

    private void WIG20_INIT() {
        WIG20AutomatycznaAktualizacjaOn.setToggleGroup(GrupaWIG20AktualizacjaAutomatyczna);
        WIG20AutomatycznaAktualizacjaOff.setToggleGroup(GrupaWIG20AktualizacjaAutomatyczna);

        WIG20AktualizacjaPoUruchomieniuOn.setToggleGroup(GrupaWIG20AktualizacjaPoUruchomieniu);
        WIG20AktualizacjaPoUruchomieniuOff.setToggleGroup(GrupaWIG20AktualizacjaPoUruchomieniu);

        WIG20AktualizacjaPoGodzinieOn.setToggleGroup(GrupaWIG20AktualizacjaPoGodzienie);
        WIG20AktualizacjaPoGodzienieOff.setToggleGroup(GrupaWIG20AktualizacjaPoGodzienie);

        WIG20WyswietlaniePunktowOn.setToggleGroup(GrupaWIG20WyswietlaniePunktow);
        WIG20WyswietlaniePunktowOff.setToggleGroup(GrupaWIG20WyswietlaniePunktow);

    }

    private void WALUTYNBP_INIT() {
        WalutyAutomatycznaAktualizacjaOn.setToggleGroup(GrupaWalutyAktualizacjaAutomatyczna);
        WalutyAutomatycznaAktualizacjaOff.setToggleGroup(GrupaWalutyAktualizacjaAutomatyczna);

        WalutyAktualizacjaPoUruchomieniuOn.setToggleGroup(GrupaWalutyAktualizacjaPoUruchomieniu);
        WalutyAktualizacjaPoUruchomieniuOff.setToggleGroup(GrupaWalutyAktualizacjaPoUruchomieniu);

        WalutyAktualizacjaPoGodzinieOn.setToggleGroup(GrupaWalutyAktualizacjaPoGodzienie);
        WalutyAktualizacjaPoGodzienieOff.setToggleGroup(GrupaWalutyAktualizacjaPoGodzienie);

        WalutyWyswietlPunktyOn.setToggleGroup(GrupaWalutyWyswietlaniePunktow);
        WalutyWyswietlPunktyOff.setToggleGroup(GrupaWalutyWyswietlaniePunktow);
    }

    //**************************************************************************    Obsluga Kontrolerk
    private void WIG20_OBSLUGA_KONTROLERK() {

        //***************************************************************************   Automatyczna Aktualizacja
        WIG20AutomatycznaAktualizacjaOn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ZAPISZ_ALL();
            }
        });

        WIG20AutomatycznaAktualizacjaOff.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ZAPISZ_ALL();
            }
        });

        //************************************************************************* Aktualizacja po Uruchomieniu

        WIG20AktualizacjaPoUruchomieniuOn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                WIG20AktualizacjaPoGodzienieOff.setSelected(true);
                ZAPISZ_ALL();
            }
        });

        WIG20AktualizacjaPoUruchomieniuOff.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                WIG20AktualizacjaPoGodzinieOn.setSelected(true);
                ZAPISZ_ALL();
            }
        });

        //*************************************************************************  Aktualizacja Po Godzienie

        WIG20AktualizacjaPoGodzinieOn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                WIG20AktualizacjaPoUruchomieniuOff.setSelected(true);
                WIG20GodzinaAktualizacjiText.setEditable(true);
                ZAPISZ_ALL();
            }
        });

        WIG20AktualizacjaPoGodzienieOff.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                WIG20GodzinaAktualizacjiText.setEditable(false);
                WIG20AktualizacjaPoUruchomieniuOn.setSelected(true);
                ZAPISZ_ALL();
            }
        });

        //**************************************************************************    Wyswietalnei punktow wykres
        WIG20WyswietlaniePunktowRok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ZAPISZ_ALL();
            }
        });

        WIG20WyswietalniePunktowMiesiac.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ZAPISZ_ALL();
            }
        });

        WIG20WyswietlaniePunktowCaleArchiwum.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ZAPISZ_ALL();
            }
        });

        //**************************************************************************       Wyswietlanie Punktow
        WIG20WyswietlaniePunktowOn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                WIG20WyswietlaniePunktowText.setEditable(true);
                ZAPISZ_ALL();
            }
        });

        WIG20WyswietlaniePunktowOff.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                WIG20WyswietlaniePunktowText.setEditable(false);
                ZAPISZ_ALL();
            }
        });

        //***************************************************************************    Zapis ustawien
        WIG20Zapisz.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ZAPISZ_ALL();
            }
        });

    }

    private void WALUTY_OBSLUGA_KONTROLERK() {

        //***************************************************************************   Automatyczna Aktualizacja
        WalutyAutomatycznaAktualizacjaOn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ZAPISZ_ALL();
            }
        });

        WalutyAutomatycznaAktualizacjaOff.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ZAPISZ_ALL();
            }
        });

        //************************************************************************* Aktualizacja po Uruchomieniu

        WalutyAktualizacjaPoUruchomieniuOn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                WalutyAktualizacjaPoGodzienieOff.setSelected(true);
                ZAPISZ_ALL();
            }
        });

        WalutyAktualizacjaPoUruchomieniuOff.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                WalutyAktualizacjaPoGodzinieOn.setSelected(true);
                ZAPISZ_ALL();
            }
        });

        //*************************************************************************  Aktualizacja Po Godzienie

        WalutyAktualizacjaPoGodzinieOn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                WalutyAktualizacjaPoUruchomieniuOff.setSelected(true);
                WalutyGodzinaAktualizacjiText.setEditable(true);
                ZAPISZ_ALL();
            }
        });

        WalutyAktualizacjaPoGodzienieOff.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                WalutyAktualizacjaPoUruchomieniuOn.setSelected(true);
                WalutyGodzinaAktualizacjiText.setEditable(false);
                ZAPISZ_ALL();
            }
        });

        //**************************************************************************    Wyswietalnei punktow wykres
        WalutyWyswietlaniePunktowRok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ZAPISZ_ALL();
            }
        });

        WalutyWyswietalniePunktowMiesiac.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            ZAPISZ_ALL();
            }
        });

        WalutyWyswietlaniePunktowCaleArchiwum.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ZAPISZ_ALL();
            }
        });

        //**************************************************************************       Wyswietlanie Punktow
        WalutyWyswietlPunktyOn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                WalutyWyswietlPunktyText.setEditable(true);
                ZAPISZ_ALL();
            }
        });

        WalutyWyswietlPunktyOff.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                WalutyWyswietlPunktyText.setEditable(false);
                ZAPISZ_ALL();
            }
        });

        //***************************************************************************    Zapis ustawien
        WalutyZapisz.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ZAPISZ_ALL();
            }
        });


    }

    //**************************************************************************
    private void INIT_ALL() {
        ustawieniaPlik.WczytajUstawienia();
        //*****************************************************************************************************
        WIG20AutomatycznaAktualizacjaOn.setSelected(ustawieniaPlik.ustawienia.WIG20AutoAktualizacja);
        WIG20AutomatycznaAktualizacjaOff.setSelected(!ustawieniaPlik.ustawienia.WIG20AutoAktualizacja);

        WIG20AktualizacjaPoUruchomieniuOn.setSelected(ustawieniaPlik.ustawienia.WIG20AutoAktualizacjaPoUruchomieniu);
        WIG20AktualizacjaPoUruchomieniuOff.setSelected(!ustawieniaPlik.ustawienia.WIG20AutoAktualizacjaPoUruchomieniu);

        WIG20AktualizacjaPoGodzinieOn.setSelected(ustawieniaPlik.ustawienia.WIG20AutoAktualizacjaPoGodzienie);
        WIG20AktualizacjaPoGodzienieOff.setSelected(!ustawieniaPlik.ustawienia.WIG20AutoAktualizacjaPoGodzienie);

        WIG20GodzinaAktualizacjiText.setText(ustawieniaPlik.ustawienia.WIG20GodzinaAktualizacji);

        WIG20WyswietlaniePunktowOn.setSelected(ustawieniaPlik.ustawienia.WIG20WyswietlaniePunktow);
        WIG20WyswietlaniePunktowOff.setSelected(!ustawieniaPlik.ustawienia.WIG20WyswietlaniePunktow);

        WIG20WyswietlaniePunktowText.setText(ustawieniaPlik.ustawienia.WIG20WyswietlanePunkty);

        WIG20WyswietalniePunktowMiesiac.setSelected(ustawieniaPlik.ustawienia.WIG20OstatniMiesiac);
        WIG20WyswietlaniePunktowRok.setSelected(ustawieniaPlik.ustawienia.WIG20OstatniRok);
        WIG20WyswietlaniePunktowCaleArchiwum.setSelected(ustawieniaPlik.ustawienia.WIG20CaleArchiwum);

        //*****************************************************************************************************

        WalutyAutomatycznaAktualizacjaOn.setSelected(ustawieniaPlik.ustawienia.WalutyAutoAktualizacja);
        WalutyAutomatycznaAktualizacjaOff.setSelected(!ustawieniaPlik.ustawienia.WalutyAutoAktualizacja);

        WalutyAktualizacjaPoUruchomieniuOn.setSelected(ustawieniaPlik.ustawienia.WalutyAutoAktualizacjaPoUruchomieniu);
        WalutyAktualizacjaPoUruchomieniuOff.setSelected(!ustawieniaPlik.ustawienia.WalutyAutoAktualizacjaPoUruchomieniu);

        WalutyAktualizacjaPoGodzinieOn.setSelected(ustawieniaPlik.ustawienia.WalutyAutoAktualizacjaPoGodzienie);
        WalutyAktualizacjaPoGodzienieOff.setSelected(!ustawieniaPlik.ustawienia.WalutyAutoAktualizacjaPoGodzienie);

        WalutyGodzinaAktualizacjiText.setText(ustawieniaPlik.ustawienia.WalutyGodzinaAktualizacji);

        WalutyWyswietlPunktyOn.setSelected(ustawieniaPlik.ustawienia.WalutyWyswietlaniePunktow);
        WalutyWyswietlPunktyOff.setSelected(!ustawieniaPlik.ustawienia.WalutyWyswietlaniePunktow);

        WalutyWyswietlPunktyText.setText(ustawieniaPlik.ustawienia.WalutyWyswietlanePunkty);

        WalutyWyswietalniePunktowMiesiac.setSelected(ustawieniaPlik.ustawienia.WalutyOstatniMiesiac);
        WalutyWyswietlaniePunktowRok.setSelected(ustawieniaPlik.ustawienia.WalutyOstatniRok);
        WalutyWyswietlaniePunktowCaleArchiwum.setSelected(ustawieniaPlik.ustawienia.WalutyCaleArchiwum);

        DataOstatniejAktualizacjiWaluty.setEditable(false);
        DataOstatniejAktualizacjiWIG20.setEditable(false);

        DataOstatniejAktualizacjiWIG20.setText(ustawieniaPlik.ustawienia.DataOstatniejAktualizacjiWIG20);
        DataOstatniejAktualizacjiWaluty.setText(ustawieniaPlik.ustawienia.DataOstatniejAktualizacjiWaluty);

        WIG20MaxIloscDanychWykres.setText(ustawieniaPlik.ustawienia.WIG20MaksIloscDanychWykres);
        WalutyMaxIloscDanychWykres.setText(ustawieniaPlik.ustawienia.WalutyMaksIloscDanychWykres);

    }

    private void ZAPISZ_ALL(){

        //****************************************************************************************************
        ustawieniaPlik.ustawienia.WIG20AutoAktualizacja = WIG20AutomatycznaAktualizacjaOn.isSelected();
        ustawieniaPlik.ustawienia.WIG20AutoAktualizacjaPoUruchomieniu = WIG20AktualizacjaPoUruchomieniuOn.isSelected();
        ustawieniaPlik.ustawienia.WIG20AutoAktualizacjaPoGodzienie=WIG20AktualizacjaPoGodzinieOn.isSelected();


        ustawieniaPlik.ustawienia.WIG20WyswietlaniePunktow = WIG20WyswietlaniePunktowOn.isSelected();


        ustawieniaPlik.ustawienia.WIG20OstatniMiesiac=WIG20WyswietalniePunktowMiesiac.isSelected();
        ustawieniaPlik.ustawienia.WIG20OstatniRok=WIG20WyswietlaniePunktowRok.isSelected();
        ustawieniaPlik.ustawienia.WIG20CaleArchiwum=WIG20WyswietlaniePunktowCaleArchiwum.isSelected();

        ustawieniaPlik.ustawienia.WIG20GodzinaAktualizacji = WIG20GodzinaAktualizacjiText.getText();

        ustawieniaPlik.ustawienia.WIG20WyswietlanePunkty = WIG20WyswietlaniePunktowText.getText();
        ustawieniaPlik.ustawienia.WIG20WyswietlonePunkty = Integer.valueOf(ustawieniaPlik.ustawienia.WIG20WyswietlanePunkty);
        //****************************************************************************************************
        ustawieniaPlik.ustawienia.WalutyAutoAktualizacja = WalutyAutomatycznaAktualizacjaOn.isSelected();
        ustawieniaPlik.ustawienia.WalutyAutoAktualizacjaPoUruchomieniu = WalutyAktualizacjaPoUruchomieniuOn.isSelected();
        ustawieniaPlik.ustawienia.WalutyAutoAktualizacjaPoGodzienie=WalutyAktualizacjaPoGodzinieOn.isSelected();
        ustawieniaPlik.ustawienia.WalutyGodzinaAktualizacji = WalutyGodzinaAktualizacjiText.getText();

        ustawieniaPlik.ustawienia.WalutyWyswietlaniePunktow = WalutyWyswietlPunktyOn.isSelected();
        ustawieniaPlik.ustawienia.WalutyWyswietlanePunkty = WalutyWyswietlPunktyText.getText();
        ustawieniaPlik.ustawienia.WalutyWyswietlonePunkty = Integer.valueOf(ustawieniaPlik.ustawienia.WalutyWyswietlanePunkty);

        ustawieniaPlik.ustawienia.WalutyOstatniMiesiac=WalutyWyswietalniePunktowMiesiac.isSelected();
        ustawieniaPlik.ustawienia.WalutyOstatniRok=WalutyWyswietlaniePunktowRok.isSelected();
        ustawieniaPlik.ustawienia.WalutyCaleArchiwum=WalutyWyswietlaniePunktowCaleArchiwum.isSelected();

        ustawieniaPlik.ustawienia.WIG20MaksIloscDanychWykres=WIG20MaxIloscDanychWykres.getText();
        ustawieniaPlik.ustawienia.WIG20MaxIloscDanychWykres=Integer.valueOf(ustawieniaPlik.ustawienia.WIG20MaksIloscDanychWykres);

        ustawieniaPlik.ustawienia.WalutyMaksIloscDanychWykres = WalutyMaxIloscDanychWykres.getText();
        ustawieniaPlik.ustawienia.Waluty0MaxIloscDanychWykres = Integer.valueOf(ustawieniaPlik.ustawienia.WalutyMaksIloscDanychWykres);

        ustawieniaPlik.ZapiszUstawienia();
    }
}