package commands.basic;

import commands.Command;
import graphics.stages.HelpStage;

/**
 * Trieda reprezentuje prikaz, ktory zabezpeci zobrazenie okna s informaciami o aplikacii
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
