package graphics;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Created by Zuzka on 21.1.2016.
 */
public class Style {

    public static void setHelloButtonStyle(Button btn) {
        String buttonStyle =
                "-fx-background-color: white;"
                        + "-fx-text-fill: #4A789C;"
                        + "-fx-font-family: 'Arial';"
                        + "-fx-font-weight: bold;";

        String buttonFocusStyle =
                "-fx-background-color: #4A789C;"
                        + "-fx-text-fill: white;"
                        + "-fx-font-family: 'Arial';"
                        + "-fx-font-weight: bold;";

        btn.setStyle(buttonStyle);
        btn.setOnMouseEntered(event -> {
            btn.setStyle(null);
            btn.setStyle(buttonFocusStyle);
        });

        btn.setOnMouseExited(event -> {
            btn.setStyle(null);
            btn.setStyle(buttonStyle);
        });
    }

    public static void setButtonStyle(Button btn, int width) {
        btn.setPrefWidth(width);
        String buttonFocusStyle =
                "-fx-background-color: black;"
                        + "-fx-text-fill: #4A789C;"
                        + "-fx-font-family: 'Arial';"
                        + "-fx-font-weight: bold;";

        String buttonStyle =
                "-fx-background-color: #4A789C;"
                        + "-fx-text-fill: white;"
                        + "-fx-font-family: 'Arial';"
                        + "-fx-font-weight: bold;";

        btn.setStyle(buttonStyle);
        btn.setOnMouseEntered(event -> {
            btn.setStyle(null);
            btn.setStyle(buttonFocusStyle);
        });

        btn.setOnMouseExited(event -> {
            btn.setStyle(null);
            btn.setStyle(buttonStyle);
        });
    }

    public static void setRadioButtonStyle(RadioButton radioButton) {
        radioButton.setFont(new Font("Arial", 12));
        radioButton.setPrefWidth(250);
        radioButton.setBackground(new Background(new BackgroundFill(Color.color(0.7695, 0.8281, 0.875), null, null)));
    }

    public static void setHelpButtonStyle(Button btn, Label info) {
        btn.setPrefWidth(250);
        String buttonFocusStyle =
                "-fx-background-color: black;"
                        + "-fx-text-fill: #4A789C;"
                        + "-fx-font-family: 'Arial';"
                        + "-fx-font-weight: bold;";

        String buttonStyle =
                "-fx-background-color: #4A789C;"
                        + "-fx-text-fill: white;"
                        + "-fx-font-family: 'Arial';"
                        + "-fx-font-weight: bold;";

        btn.setStyle(buttonStyle);
        btn.setOnMouseEntered(event -> {
            btn.setStyle(null);
            btn.setStyle(buttonFocusStyle);
        });

        btn.setOnMouseExited(event -> {
            btn.setStyle(null);
            btn.setStyle(buttonStyle);
        });
        btn.setOnMouseClicked(event -> {
            String show = "Show Info";
            String hide = "Hide Info";
            if (btn.getText().equals(show)) {
                info.setOpacity(1);
                btn.setText(hide);
            }
            else {
                info.setOpacity(0);
                btn.setText(show);
            }
        });

    }

}
