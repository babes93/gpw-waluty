package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by patryk on 08.08.16.
 */
public class InformacjeController implements Initializable {


    @FXML
    private TextArea InfoText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        InfoText.setEditable(false);


        InfoText.appendText("Pobieranie informacji o spółkach wchodzacych w WIG20:\n");
        InfoText.appendText("http://www.bankier.pl/inwestowanie/profile/quote.html?symbol=WIG20");
        InfoText.appendText("\n\n");
        InfoText.appendText("Pobieranie archiwum notowan WIG20:\n");
        InfoText.appendText("http://www.biznesradar.pl/notowania-historyczne/ALIOR-BANK");
        InfoText.appendText("\n\n");
        InfoText.appendText("Pobieranie archiwum walut: \n");
        InfoText.appendText("http://www.nbp.pl/home.aspx?f=/statystyka/kursy.html");
        InfoText.appendText("\n\n");
        InfoText.appendText("Nazwy walut: \n");
        InfoText.appendText("https://www.ups.com/worldshiphelp/WS14/PLK/AppHelp/Codes/Country_Territory_and_Currency_Codes.htm");
        InfoText.appendText("\n\n");
    }
}
