package commands;

/**
 * Trieda reprezentuje konkretny prikaz, ktory moze byt zavolany z roznych miest (tlacidlo, polozka menu...)
 */
public interface Command {

    /** Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou*/
    public void execute();

}
