package commands;

import graphics.stages.HelpStage;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class HelpCommand implements Command {
    /**
     * Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou
     */
    @Override
    public void execute() {
        HelpStage helpStage = new HelpStage();
        helpStage.show();
    }
}
