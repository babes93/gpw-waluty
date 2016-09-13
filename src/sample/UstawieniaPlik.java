package sample;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.text.StyledEditorKit;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by patryk on 24.08.16.
 */
public class UstawieniaPlik {
    public Ustawienia ustawienia = new Ustawienia();
    private final static String NazwaPliku = new String("Conf.xml");
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat aktualnaGodzina = new SimpleDateFormat("HH:mm");

    public void cratePlikUstawienia() {
        if (!(new File(NazwaPliku)).exists()) {
            System.out.println("Tworzenie pliku");
            try {

                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

                org.w3c.dom.Document docxml = docBuilder.newDocument();
                org.w3c.dom.Element rootElement = docxml.createElement("Ustawienia");
                docxml.appendChild(rootElement);

                org.w3c.dom.Element wig20 = docxml.createElement("WIG20");
                org.w3c.dom.Element waluty = docxml.createElement("WalutyNBP");

                rootElement.appendChild(wig20);
                rootElement.appendChild(waluty);

                org.w3c.dom.Element opcjePodstawoweWIG20 = docxml.createElement("Opcje_Podstawowe");
                org.w3c.dom.Element opcjePodstawoweWaluty = docxml.createElement("Opcje_Podstawowe");
                wig20.appendChild(opcjePodstawoweWIG20);
                waluty.appendChild(opcjePodstawoweWaluty);

                org.w3c.dom.Element DataOstatniejAktualizacjiWIG20 = docxml.createElement("Data_Ostatniej_Aktualizacji");
                org.w3c.dom.Element DataOstatniejAktualizacjiWaluty = docxml.createElement("Data_Ostatniej_Aktualizacji");
                opcjePodstawoweWIG20.appendChild(DataOstatniejAktualizacjiWIG20);
                opcjePodstawoweWaluty.appendChild(DataOstatniejAktualizacjiWaluty);

                org.w3c.dom.Element AktualnaBazaNaDzienWIG20 = docxml.createElement("Baza_Aktualna");
                org.w3c.dom.Element AktualnaBazaNaDzienWaluty = docxml.createElement("Baza_Aktualna");
                opcjePodstawoweWIG20.appendChild(AktualnaBazaNaDzienWIG20);
                opcjePodstawoweWaluty.appendChild(AktualnaBazaNaDzienWaluty);


                org.w3c.dom.Element AutoAktualizacja = docxml.createElement("Automatyczna_Aktualizacja");
                org.w3c.dom.Element AutoAktualizacjaWaluty = docxml.createElement("Automatyczna_Aktualizacja");
                opcjePodstawoweWaluty.appendChild(AutoAktualizacjaWaluty);
                opcjePodstawoweWIG20.appendChild(AutoAktualizacja);

                org.w3c.dom.Element AutoAktualizacjaPoUruchomieniu = docxml.createElement("Auto_Aktualizacja_Po_Starcie");
                org.w3c.dom.Element AutoAktualizacjaPoUruchomieniuWaluty = docxml.createElement("Auto_Aktualizacja_Po_Starcie");
                opcjePodstawoweWaluty.appendChild(AutoAktualizacjaPoUruchomieniuWaluty);
                opcjePodstawoweWIG20.appendChild(AutoAktualizacjaPoUruchomieniu);

                org.w3c.dom.Element AutoAktualizacjaPoGodzienie = docxml.createElement("Auto_Aktualizacja_PoGodzinie");
                org.w3c.dom.Element AutoAktualizacjaPoGodzienieWaluty = docxml.createElement("Auto_Aktualizacja_PoGodzinie");
                org.w3c.dom.Element GodzinaAktualizacji = docxml.createElement("Godzina_Aktualizacji");
                org.w3c.dom.Element GodzinaAktualizacjiWaluty = docxml.createElement("Godzina_Aktualizacji");

                opcjePodstawoweWaluty.appendChild(AutoAktualizacjaPoGodzienieWaluty);
                opcjePodstawoweWIG20.appendChild(AutoAktualizacjaPoGodzienie);

                opcjePodstawoweWaluty.appendChild(GodzinaAktualizacjiWaluty);
                opcjePodstawoweWIG20.appendChild(GodzinaAktualizacji);

                org.w3c.dom.Element WyswietlanePunktyCalosc = docxml.createElement("Wyswietlane_Punkty");
                org.w3c.dom.Element OstatniMiesiac = docxml.createElement("Ostatni_Miesiac");
                org.w3c.dom.Element OstatniRok = docxml.createElement("Ostatni_Rok");
                org.w3c.dom.Element CaleArchiwum = docxml.createElement("Cale_Archiwum");

                WyswietlanePunktyCalosc.appendChild(OstatniMiesiac);
                WyswietlanePunktyCalosc.appendChild(OstatniRok);
                WyswietlanePunktyCalosc.appendChild(CaleArchiwum);

                org.w3c.dom.Element WyswietlanePunktyCaloscWaluty = docxml.createElement("Wyswietlane_Punkty");
                org.w3c.dom.Element OstatniMiesiacWaluty = docxml.createElement("Ostatni_Miesiac");
                org.w3c.dom.Element OstatniRokWaluty = docxml.createElement("Ostatni_Rok");
                org.w3c.dom.Element CaleArchiwumWaluty = docxml.createElement("Cale_Archiwum");

                WyswietlanePunktyCaloscWaluty.appendChild(OstatniMiesiacWaluty);
                WyswietlanePunktyCaloscWaluty.appendChild(OstatniRokWaluty);
                WyswietlanePunktyCaloscWaluty.appendChild(CaleArchiwumWaluty);

                opcjePodstawoweWaluty.appendChild(WyswietlanePunktyCaloscWaluty);
                opcjePodstawoweWIG20.appendChild(WyswietlanePunktyCalosc);

                org.w3c.dom.Element WyswietlaniePunktow = docxml.createElement("Wyswietlanie_Punktow");
                org.w3c.dom.Element DaneWyswietlanychPunktow = docxml.createElement("Dane_Wyswietlanych_Punktow_Ponizej");


                org.w3c.dom.Element WyswietlaniePunktowWaluty = docxml.createElement("Wyswietlanie_Punktow");
                org.w3c.dom.Element DaneWyswietlanychPunktowWaluty = docxml.createElement("Dane_Wyswietlanych_Punktow_Ponizej");


                opcjePodstawoweWaluty.appendChild(WyswietlaniePunktowWaluty);
                opcjePodstawoweWIG20.appendChild(WyswietlaniePunktow);

                opcjePodstawoweWaluty.appendChild(DaneWyswietlanychPunktowWaluty);
                opcjePodstawoweWIG20.appendChild(DaneWyswietlanychPunktow);

                org.w3c.dom.Element MaksIloscDanychWIG20 = docxml.createElement("Maksymalna_Ilosc_Danych_Wykres");
                org.w3c.dom.Element MaksIloscDanychWaluty = docxml.createElement("Maksymalna_Ilosc_Danych_Wykres");

                opcjePodstawoweWIG20.appendChild(MaksIloscDanychWIG20);
                opcjePodstawoweWaluty.appendChild(MaksIloscDanychWaluty);

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();

                DOMSource source = new DOMSource(docxml);
                StreamResult result = new StreamResult(new File(NazwaPliku));

                transformer.transform(source, result);

            } catch (ParserConfigurationException pce) {
                pce.printStackTrace();
            } catch (TransformerException tfe) {
                tfe.printStackTrace();
            } catch (NullPointerException npe) {
                npe.printStackTrace();
            }

            this.ZapiszUstawienia();

        }
    }

    public void ZapiszUstawienia(){

        try{
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuiler = docFactory.newDocumentBuilder();
            Document doc = docBuiler.parse(NazwaPliku);

            doc.getElementsByTagName("Automatyczna_Aktualizacja").item(0).setTextContent(Boolean.toString(ustawienia.WIG20AutoAktualizacja));
            doc.getElementsByTagName("Automatyczna_Aktualizacja").item(1).setTextContent(Boolean.toString(ustawienia.WalutyAutoAktualizacja));

            doc.getElementsByTagName("Auto_Aktualizacja_Po_Starcie").item(0).setTextContent(Boolean.toString(ustawienia.WIG20AutoAktualizacjaPoUruchomieniu));
            doc.getElementsByTagName("Auto_Aktualizacja_Po_Starcie").item(1).setTextContent(Boolean.toString(ustawienia.WalutyAutoAktualizacjaPoUruchomieniu));

            doc.getElementsByTagName("Auto_Aktualizacja_PoGodzinie").item(0).setTextContent(Boolean.toString(ustawienia.WIG20AutoAktualizacjaPoGodzienie));
            doc.getElementsByTagName("Auto_Aktualizacja_PoGodzinie").item(1).setTextContent(Boolean.toString(ustawienia.WalutyAutoAktualizacjaPoGodzienie));

            doc.getElementsByTagName("Godzina_Aktualizacji").item(0).setTextContent(ustawienia.WIG20GodzinaAktualizacji);
            doc.getElementsByTagName("Godzina_Aktualizacji").item(1).setTextContent(ustawienia.WalutyGodzinaAktualizacji);

            doc.getElementsByTagName("Ostatni_Miesiac").item(0).setTextContent(Boolean.toString(ustawienia.WIG20OstatniMiesiac));
            doc.getElementsByTagName("Ostatni_Miesiac").item(1).setTextContent(Boolean.toString(ustawienia.WalutyOstatniMiesiac));

            doc.getElementsByTagName("Ostatni_Rok").item(0).setTextContent(Boolean.toString(ustawienia.WIG20OstatniRok));
            doc.getElementsByTagName("Ostatni_Rok").item(1).setTextContent(Boolean.toString(ustawienia.WalutyOstatniRok));

            doc.getElementsByTagName("Cale_Archiwum").item(0).setTextContent(Boolean.toString(ustawienia.WIG20CaleArchiwum));
            doc.getElementsByTagName("Cale_Archiwum").item(1).setTextContent(Boolean.toString(ustawienia.WalutyCaleArchiwum));

            doc.getElementsByTagName("Wyswietlanie_Punktow").item(0).setTextContent(Boolean.toString(ustawienia.WIG20WyswietlaniePunktow));
            doc.getElementsByTagName("Wyswietlanie_Punktow").item(1).setTextContent(Boolean.toString(ustawienia.WalutyWyswietlaniePunktow));

            doc.getElementsByTagName("Dane_Wyswietlanych_Punktow_Ponizej").item(0).setTextContent(ustawienia.WIG20WyswietlanePunkty);
            doc.getElementsByTagName("Dane_Wyswietlanych_Punktow_Ponizej").item(1).setTextContent(ustawienia.WalutyWyswietlanePunkty);

            doc.getElementsByTagName("Data_Ostatniej_Aktualizacji").item(0).setTextContent(ustawienia.DataOstatniejAktualizacjiWIG20);
            doc.getElementsByTagName("Data_Ostatniej_Aktualizacji").item(1).setTextContent(ustawienia.DataOstatniejAktualizacjiWaluty);

            doc.getElementsByTagName("Baza_Aktualna").item(0).setTextContent(Boolean.toString(ustawienia.AktualnaBazaWIG20NaDzien));
            doc.getElementsByTagName("Baza_Aktualna").item(1).setTextContent(Boolean.toString(ustawienia.AktualnaBazaWalutNaDzien));

            doc.getElementsByTagName("Maksymalna_Ilosc_Danych_Wykres").item(0).setTextContent(ustawienia.WIG20MaksIloscDanychWykres);
            doc.getElementsByTagName("Maksymalna_Ilosc_Danych_Wykres").item(1).setTextContent(ustawienia.WalutyMaksIloscDanychWykres);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(NazwaPliku));
            transformer.transform(source,result);

            ustawienia.ZmianaUstawien=true;

        }catch (ParserConfigurationException pce){
            pce.printStackTrace();
        }catch (TransformerException tfe){
            tfe.printStackTrace();
        }catch (SAXException saxe){
            saxe.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }


    }

    public void WczytajUstawienia(){

        try{
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuiler = docFactory.newDocumentBuilder();
            Document doc = docBuiler.parse(NazwaPliku);

           ustawienia.WIG20AutoAktualizacja = Boolean.valueOf(doc.getElementsByTagName("Automatyczna_Aktualizacja").item(0).getTextContent());
           ustawienia.WalutyAutoAktualizacja = Boolean.valueOf(doc.getElementsByTagName("Automatyczna_Aktualizacja").item(1).getTextContent());

            ustawienia.WIG20AutoAktualizacjaPoUruchomieniu =Boolean.valueOf(doc.getElementsByTagName("Auto_Aktualizacja_Po_Starcie").item(0).getTextContent());
            ustawienia.WalutyAutoAktualizacjaPoUruchomieniu = Boolean.valueOf(doc.getElementsByTagName("Auto_Aktualizacja_Po_Starcie").item(1).getTextContent());

            ustawienia.WIG20AutoAktualizacjaPoGodzienie = Boolean.valueOf(doc.getElementsByTagName("Auto_Aktualizacja_PoGodzinie").item(0).getTextContent());
            ustawienia.WalutyAutoAktualizacjaPoGodzienie= Boolean.valueOf(doc.getElementsByTagName("Auto_Aktualizacja_PoGodzinie").item(1).getTextContent());

            ustawienia.WIG20GodzinaAktualizacji =  doc.getElementsByTagName("Godzina_Aktualizacji").item(0).getTextContent();
            ustawienia.WalutyGodzinaAktualizacji=  doc.getElementsByTagName("Godzina_Aktualizacji").item(1).getTextContent();

            ustawienia.WIG20OstatniMiesiac = Boolean.valueOf(doc.getElementsByTagName("Ostatni_Miesiac").item(0).getTextContent());
            ustawienia.WalutyOstatniMiesiac = Boolean.valueOf(doc.getElementsByTagName("Ostatni_Miesiac").item(1).getTextContent());

            ustawienia.WIG20OstatniRok = Boolean.valueOf(doc.getElementsByTagName("Ostatni_Rok").item(0).getTextContent());
            ustawienia.WalutyOstatniRok = Boolean.valueOf(doc.getElementsByTagName("Ostatni_Rok").item(1).getTextContent());

            ustawienia.WIG20CaleArchiwum = Boolean.valueOf(doc.getElementsByTagName("Cale_Archiwum").item(0).getTextContent());
            ustawienia.WalutyCaleArchiwum= Boolean.valueOf(doc.getElementsByTagName("Cale_Archiwum").item(1).getTextContent());

            ustawienia.WIG20WyswietlaniePunktow=Boolean.valueOf(doc.getElementsByTagName("Wyswietlanie_Punktow").item(0).getTextContent());
            ustawienia.WalutyWyswietlaniePunktow=Boolean.valueOf(doc.getElementsByTagName("Wyswietlanie_Punktow").item(1).getTextContent());

            ustawienia.WIG20WyswietlanePunkty = doc.getElementsByTagName("Dane_Wyswietlanych_Punktow_Ponizej").item(0).getTextContent();
            ustawienia.WalutyWyswietlanePunkty =  doc.getElementsByTagName("Dane_Wyswietlanych_Punktow_Ponizej").item(1).getTextContent();

            ustawienia.WIG20WyswietlonePunkty=Integer.valueOf(ustawienia.WIG20WyswietlanePunkty);
            ustawienia.WalutyWyswietlonePunkty=Integer.valueOf(ustawienia.WalutyWyswietlanePunkty);

            ustawienia.DataOstatniejAktualizacjiWIG20=doc.getElementsByTagName("Data_Ostatniej_Aktualizacji").item(0).getTextContent();
            ustawienia.DataOstatniejAktualizacjiWaluty=doc.getElementsByTagName("Data_Ostatniej_Aktualizacji").item(1).getTextContent();

            ustawienia.WIG20MaksIloscDanychWykres = doc.getElementsByTagName("Maksymalna_Ilosc_Danych_Wykres").item(0).getTextContent();
            ustawienia.WalutyMaksIloscDanychWykres = doc.getElementsByTagName("Maksymalna_Ilosc_Danych_Wykres").item(1).getTextContent();

            ustawienia.WIG20MaxIloscDanychWykres = Integer.valueOf(ustawienia.WIG20MaksIloscDanychWykres);
            ustawienia.Waluty0MaxIloscDanychWykres = Integer.valueOf(ustawienia.WalutyMaksIloscDanychWykres);

            String data = format.format(new Date());
            if(data.equals(ustawienia.DataOstatniejAktualizacjiWIG20)){
                ustawienia.AktualnaBazaWIG20NaDzien=true;
            }else ustawienia.AktualnaBazaWIG20NaDzien=false;

            if(data.equals(ustawienia.DataOstatniejAktualizacjiWaluty)){
                ustawienia.AktualnaBazaWalutNaDzien=true;
            }else ustawienia.AktualnaBazaWalutNaDzien=false;

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void SprawdzAktualnoscBazDanychNaDzien(){
        String data = format.format(new Date());
        if(data.equals(ustawienia.DataOstatniejAktualizacjiWIG20)){
            ustawienia.AktualnaBazaWIG20NaDzien=true;
        }else ustawienia.AktualnaBazaWIG20NaDzien=false;

        if(data.equals(ustawienia.DataOstatniejAktualizacjiWaluty)){
            ustawienia.AktualnaBazaWalutNaDzien=true;
        }else ustawienia.AktualnaBazaWalutNaDzien=false;
    }

    public String getAktualnaData(){
        return format.format(new Date());
    }

    public String getAktualnyCzas(){
        return aktualnaGodzina.format(new Date());
    }
}
