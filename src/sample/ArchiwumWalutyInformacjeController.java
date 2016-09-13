package sample;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by patryk on 22.08.16.
 */
public class ArchiwumWalutyInformacjeController implements Initializable {


    @FXML
    private TableColumn KolumnaSprzedaz;

    @FXML
    private TableColumn KolumnaDataDo;

    @FXML
    private TableColumn KolumnaKupno;

    @FXML
    private TableColumn KolumnaPrzelicznik;

    @FXML
    private TableColumn KolumnaNazwa;

    @FXML
    private TableView<WalutyNBP.WalutyDane> TabelaWalutyPelna;

    @FXML
    private TableColumn KolumnaKodWaluty;

    @FXML
    private TableColumn KolumnaDataOd;

    private WalutyNBP walutyNBP = new WalutyNBP();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        walutyNBP.WczytajPodsawoweDaneWaluty();

        KolumnaKodWaluty.setCellValueFactory(new PropertyValueFactory<WalutyNBP.WalutyDane,String>("KodWaluty"));
        KolumnaNazwa.setCellValueFactory(new PropertyValueFactory<WalutyNBP.WalutyDane,String>("NazwaWaluty"));
        KolumnaDataDo.setCellValueFactory(new PropertyValueFactory<WalutyNBP.WalutyDane,String>("DataDo"));
        KolumnaDataOd.setCellValueFactory(new PropertyValueFactory<WalutyNBP.WalutyDane,String>("DataOd"));
        KolumnaKupno.setCellValueFactory(new PropertyValueFactory<WalutyNBP.WalutyDane,String>("KursKupna"));
        KolumnaSprzedaz.setCellValueFactory(new PropertyValueFactory<WalutyNBP.WalutyDane,String>("KursSprzedazy"));
        KolumnaPrzelicznik.setCellValueFactory(new PropertyValueFactory<WalutyNBP.WalutyDane,String>("Przelicznik"));

        TabelaWalutyPelna.setEditable(false);
        TabelaWalutyPelna.setItems(walutyNBP.walutyPodstawoweDane);

    }
}
