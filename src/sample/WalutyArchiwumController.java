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
 * Created by patryk on 21.08.16.
 */
public class WalutyArchiwumController implements Initializable {

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

    private ObservableList<String> komendy = FXCollections.observableArrayList();
    private WalutyNBP walutyNBP = new WalutyNBP();
    SimpleDateFormat CzasSystemowy = new SimpleDateFormat("kk:mm");


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ListViewKomand.setItems(komendy);
        ListViewKomand.setEditable(false);
        walutyNBP.WczytajPodsawoweDaneWaluty();


        Timeline odswiezanie = new Timeline(new KeyFrame(javafx.util.Duration.millis(10), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               ProgBarPostep.setProgress(walutyNBP.infoPobieranie.Postep);
                ProgIndPostep.setProgress(walutyNBP.infoPobieranie.Postep);
                if(walutyNBP.infoPobieranie.ustawionoKomende){

                    if(walutyNBP.infoPobieranie.Przetwarzanie){
                        komendy.remove(komendy.size()-1);
                        komendy.add(CzasSystemowy.format(new Date())+">>"+walutyNBP.infoPobieranie.getKomenda());
                        walutyNBP.infoPobieranie.Przetwarzanie=false;
                    }
                    else komendy.add(CzasSystemowy.format(new Date())+">>"+walutyNBP.infoPobieranie.getKomenda());
                }

            }
        }
        ));
        odswiezanie.setCycleCount(Timeline.INDEFINITE);
        odswiezanie.play();


        CaleArchiwumButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                walutyNBP.PobierzWalutyDoBazyDanych();
            }
        });

        AktualizujArchiwumButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
              walutyNBP.aktualizujWalutyBazaDanych();
            }
        });


    }
}
