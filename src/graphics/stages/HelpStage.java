package graphics.stages;

import commands.Command;
import commands.QuitCommand;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class HelpStage {
    BorderPane pane = new BorderPane();
    Scene scene = new Scene(pane);
    Stage stage = new Stage();

    public HelpStage() {
        this.stage.setScene(scene);
        Button ok = new Button("GOT IT!");
        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Command command = new QuitCommand(stage);
                command.execute();
            }
        });
        pane.setBottom(ok);
    }

    public void show() {
        this.stage.show();
    }
}
