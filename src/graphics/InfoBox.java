package graphics;

import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Trieda reprezentuje textove pole na vypis informacii z generovania
 */
public class InfoBox extends Label {
    private final List<String> info;
    private String givens;

    public InfoBox() {
        this.info = new ArrayList<>();
        this.givens = "#Givens: 0\n";
    }

    /** Funkcia aktualizuje pocet zadanych cisel v mriezke a vypise to do infoBoxu */
    public void changeGivens(String givens) {
        this.givens = "#Givens: "+givens+"\n";
        this.update();
    }

    /** Funkcia aktualizuje infoBox a vypise jeho stav do hlavneho okna */
    public void update() {
        String text = "";
        for (String t : info) {
            text = t + "\n" + text;
        }
        text = givens + text;
        super.setText(text);
    }

    /** Funkcia prida vystup generatora do interneho zoznamu a aktualizuje stav infoBoxu */
    public void addInfo(String text) {
        Date date = new Date();
        text = date.toString().substring(11,19)+'\n'+text;

        this.info.add(text);
        if (this.info.size() > 10) {
            this.info.remove(0);
        }
        this.update();
    }

}
