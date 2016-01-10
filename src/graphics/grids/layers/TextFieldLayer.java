package graphics.grids.layers;

import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;


/**
 * Created by Zuzka on 10.1.2016.
 */
public class TextFieldLayer extends GridPane {


    TextField[][] textFields = new TextField[9][9];

    public TextFieldLayer(int size) {
        this.size = size;
        super.setHgap(1);
        super.setVgap(1);

        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ) {
                TextField t = new TextField();
                t.setPrefWidth(size);
                t.setPrefHeight(size);
                t.setAlignment(Pos.CENTER);
                t.setBackground(Background.EMPTY);
                textFields[i][j] = t;
                super.add(t, j, i);
            }
        }
    }

    public void setSettingsHandlers() {
        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ) {
                setSettingsHandler(i, j);
            }
        }
    }

    private void setSettingsHandler(int x, int y) {

        textFields[x][y].setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                TextField up = textFields[(x + 8) % 9][y];
                TextField down = textFields[(x + 1) % 9][y];
                TextField left = textFields[x][(y + 8) % 9];
                TextField right = textFields[x][(y + 1) % 9];

                if (event.getCode().equals(KeyCode.DOWN)) {
                    down.requestFocus();
                    changeGrid();
                } else if (event.getCode().equals(KeyCode.UP)) {
                    up.requestFocus();
                    changeGrid();
                } else if (event.getCode().equals(KeyCode.LEFT)) {
                    left.requestFocus();
                    changeGrid();
                } else if (event.getCode().equals(KeyCode.RIGHT)) {
                    right.requestFocus();
                    changeGrid();
                }
            }
        });
    }

    public void setInputHandlers() {
        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ) {
                setInputHandler(i, j);
            }
        }
    }

    private void setInputHandler(int x, int y) {
        textFields[x][y].setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                TextField up = textFields[(x+8)%9][y];
                TextField down = textFields[(x+1)%9][y];
                TextField left = textFields[x][(y+8)%9];
                TextField right = textFields[x][(y+1)%9];

                if (event.getCode().equals(KeyCode.DOWN)) {
                    down.requestFocus();
                    filterText(x, y);
                } else if (event.getCode().equals(KeyCode.UP)) {
                    up.requestFocus();
                    filterText(x, y);
                } else if (event.getCode().equals(KeyCode.LEFT)) {
                    left.requestFocus();
                    filterText(x, y);
                } else if (event.getCode().equals(KeyCode.RIGHT)) {
                    right.requestFocus();
                    filterText(x, y);
                }
            }
        });
    }

    private void filterText(int x, int y) {
        TextField text = textFields[x][y];
        StringBuffer s = new StringBuffer();
        for (Integer i = 1; i <= 9; i++) {
            if (text.getText().contains(i.toString())) {
                s.append(i.toString());
            }
        }
        if ((getGrid().start.getMode().equals(Mode.GIVENS)) && (s.length()) > 1){
            text.setText("");
        } else {
            text.setText(new String(s));
        }
    }
}
