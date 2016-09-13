package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;


/**
 * Created by patryk on 25.08.16.
 */
public class SkryptySciLabController implements Initializable {


    @FXML
    private CheckBox PlikiWczytywaniaCheck;

    @FXML
    private CheckBox plikiWalutyNBP;

    @FXML
    private ProgressIndicator ProgInd;

    @FXML
    private CheckBox PlikInfoCheck;

    @FXML
    private CheckBox PlikiDanychCheck;

    @FXML
    private CheckBox PlikiWIG20Check;

    @FXML
    private TextField KatalogTextField;

    @FXML
    private Button WybierzKatalogButton;

    @FXML
    private Button GenerujPlikiButton;

    @FXML
    private ProgressBar ProgBar;
    private String AktualnaSciezka = new String();
    private DirectoryChooser WyborKatalogu = new DirectoryChooser();
    private File WybranyKatalog;
    private String SciezkaDoKatalogu = new String();
    private SkryptSciLab skryptSciLab = new SkryptSciLab();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //***********************************Inicjalizacja

        PlikiWIG20Check.setSelected(true);
        plikiWalutyNBP.setSelected(true);
        PlikiDanychCheck.setSelected(true);
        PlikInfoCheck.setSelected(true);
        PlikiWczytywaniaCheck.setSelected(true);

        Timeline AktualizacjaTimer = new Timeline(new KeyFrame(javafx.util.Duration.millis(100), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ProgBar.setProgress(SkryptSciLab.Progres);
                ProgInd.setProgress(SkryptSciLab.Progres);
            }
        }));
        AktualizacjaTimer.setCycleCount(Timeline.INDEFINITE);
        AktualizacjaTimer.play();


        WybierzKatalogButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File Aktualny = new File(".");
                try {
                    AktualnaSciezka = new java.io.File(".").getCanonicalPath();
                    Aktualny = new java.io.File(".").getCanonicalFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                WyborKatalogu.setInitialDirectory(Aktualny);

                WybranyKatalog = WyborKatalogu.showDialog(new Stage());
                if (WybranyKatalog == null) {
                    KatalogTextField.setText("nie wybrano katalogu");
                }else {
                    SciezkaDoKatalogu = WybranyKatalog.getAbsolutePath();
                    KatalogTextField.setText(SciezkaDoKatalogu);
                }

            }
        });

        GenerujPlikiButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                skryptSciLab.setTryb(PlikiWIG20Check.isSelected(),plikiWalutyNBP.isSelected(),PlikiWczytywaniaCheck.isSelected());
                skryptSciLab.setSciezka(SciezkaDoKatalogu);
                skryptSciLab.uruchomWatekZapisu();

            }
        });

    }

}
