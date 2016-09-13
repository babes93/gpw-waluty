package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

/**
 * Created by patryk on 12.08.16.
 */
public class WIG20ArchiwumController implements Initializable {

    @FXML
    private ProgressBar ProgBarPostep;

    @FXML
    private Button CaleArchiwumButton;

    @FXML
    private Button AktualizujArchiwumButton;

    @FXML
    private ListView<String> ListViewKomand;

    @FXML
    private ProgressIndicator ProgIndPostep;

    private ObservableList<String> Komendy = FXCollections.observableArrayList();

    private WIG20 wig20 = new WIG20();
    SimpleDateFormat CzasSystemowy = new SimpleDateFormat("kk:mm");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ListViewKomand.setItems(Komendy);
        ListViewKomand.setEditable(false);
        wig20.WczytajWIG20();
        wig20.WczytajPodstawoweInfoWIG20();

        Timeline odswiezanie = new Timeline(new KeyFrame(javafx.util.Duration.millis(20), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(wig20.infoPobieranie.ustawionoKomende){

                    if(wig20.infoPobieranie.Przetwarzanie){
                        Komendy.remove(Komendy.size()-1);
                        Komendy.add(CzasSystemowy.format(new Date())+">>"+wig20.infoPobieranie.getKomenda());
                        wig20.infoPobieranie.Przetwarzanie=false;
                    }
                    else Komendy.add(CzasSystemowy.format(new Date())+">>"+wig20.infoPobieranie.getKomenda());
                }
                ProgBarPostep.setProgress(wig20.infoPobieranie.Postep);
                ProgIndPostep.setProgress(wig20.infoPobieranie.Postep);
            }
        }));
        odswiezanie.setCycleCount(Timeline.INDEFINITE);
        odswiezanie.play();

        AktualizujArchiwumButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Komendy.add(CzasSystemowy.format(new Date())+">> Aktualizacja Archiwum");
                wig20.AktualizujBazeDanych();
            }
        });

        CaleArchiwumButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Komendy.add(CzasSystemowy.format(new Date())+">> Wczytywanie calego archiwum: Rozpoczecie");
                wig20.PobierzArchiwumWIG20doBazyDanych();

            }
        });

    }
}
