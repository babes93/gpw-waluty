package sample;

import javafx.event.EventHandler;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 * Created by patryk on 13.08.16.
 */
public class WIG20ArchiwumInformacjeController implements Initializable {

    @FXML
    private TableColumn KolumnaOd;

    @FXML
    private TableColumn KolumnaDo;

    @FXML
    private TableView<WIG20.WIG20Tabela> TabelaWIG20;

    @FXML
    private TableColumn KolumnaLink;

    @FXML
    private TableColumn KolumnaTicker;

    @FXML
    private TableColumn KolumnaNazwa;

    @FXML
    private TableColumn KolumnaPelnaNazwa;

    @FXML
    private TableColumn KolumnaID;

    @FXML
    private TableColumn KolumnaSredniKurs;

    private WIG20 wig20=new WIG20();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        KolumnaDo.setCellValueFactory(new PropertyValueFactory<WIG20.WIG20Tabela,String>("DataDo"));
        KolumnaOd.setCellValueFactory(new PropertyValueFactory<WIG20.WIG20Tabela,String>("DataOd"));
        KolumnaTicker.setCellValueFactory(new PropertyValueFactory<WIG20.WIG20Tabela,String>("Ticker"));
        KolumnaNazwa.setCellValueFactory(new PropertyValueFactory<WIG20.WIG20Tabela,String>("Nazwa"));
        KolumnaPelnaNazwa.setCellValueFactory(new PropertyValueFactory<WIG20.WIG20Tabela,String>("PelnaNazwa"));
        KolumnaLink.setCellValueFactory(new PropertyValueFactory<WIG20.WIG20Tabela,String>("Link"));
        KolumnaID.setCellValueFactory(new PropertyValueFactory<WIG20.WIG20Tabela,String>("ID"));
        KolumnaSredniKurs.setCellValueFactory(new PropertyValueFactory<WIG20.WIG20Tabela,String>("SredniKurs"));

        KolumnaLink.setEditable(true);
        TabelaWIG20.setEditable(true);
        wig20.WczytajWIG20();
        wig20.WczytajPodstawoweInfoWIG20();
        TabelaWIG20.setItems(wig20.wig20Tabela);



        KolumnaLink.setCellFactory(TextFieldTableCell.forTableColumn());
        KolumnaLink.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {

            }
        });


    }
}
