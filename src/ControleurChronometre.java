import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Contrôleur du chronomètre
 */
public class ControleurChronometre implements EventHandler<ActionEvent> {
    /**
     * temps enregistré lors du dernier événement
     */
    private long tempsPrec;
    /**
     * temps écoulé depuis le début de la mesure
     */
    private long tempsEcoule;
    /**
     * Vue du chronomètre
     */
    private Chronometre chrono;

    public ControleurChronometre(Chronometre chrono) {
        this.chrono = chrono;
        this.tempsEcoule = 0;
        this.tempsPrec = System.currentTimeMillis();
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        long tempsMaintenant = System.currentTimeMillis();
        long deltaTemps = tempsMaintenant - this.tempsPrec;
        
        this.tempsEcoule += deltaTemps;
        this.tempsPrec = tempsMaintenant;
        this.chrono.setTime(this.tempsEcoule);
    }

    public void reset() {
        this.tempsEcoule = 0;
        this.tempsPrec = System.currentTimeMillis();
    }
}
