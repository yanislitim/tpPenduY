import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * Permet de gérer un Text associé à une Timeline pour afficher un temps écoulé
 */
public class Chronometre extends Text {
    /**
     * timeline qui va gérer le temps
     */
    private Timeline timeline;
    /**
     * la fenêtre de temps
     */
    private KeyFrame keyFrame;
    /**
     * le contrôleur associé au chronomètre
     */
    private ControleurChronometre actionTemps;

    /**
     * Constructeur permettant de créer le chronomètre
     * avec un label initialisé à "0:0:0"
     * Ce constructeur créer la Timeline, la KeyFrame et le contrôleur
     */
    public Chronometre() {
        super("0:00");
        
        // Configuration du texte
        this.setFont(Font.font("Arial", 16));
        this.setTextAlignment(TextAlignment.CENTER);
        
        // Création du contrôleur
        this.actionTemps = new ControleurChronometre(this);
        
        // Création de la KeyFrame (mise à jour toutes les 10ms)
        this.keyFrame = new KeyFrame(Duration.millis(10), this.actionTemps);
        
        // Création de la Timeline
        this.timeline = new Timeline(this.keyFrame);
        this.timeline.setCycleCount(Timeline.INDEFINITE); // Répétition infinie
    }

    /**
     * Permet au controleur de mettre à jour le text
     * la durée est affichée sous la forme m:s
     * @param tempsMillisec la durée depuis à afficher
     */
    public void setTime(long tempsMillisec) {
        long minutes = tempsMillisec / 60000;
        long secondes = (tempsMillisec % 60000) / 1000;
        
        this.setText(String.format("%d:%02d", minutes, secondes));
    }

    /**
     * Permet de démarrer le chronomètre
     */
    public void start() {
        this.actionTemps.reset();
        this.timeline.play();
    }

    /**
     * Permet d'arrêter le chronomètre
     */
    public void stop() {
        this.timeline.stop();
    }

    /**
     * Permet de remettre le chronomètre à 0
     */
    public void resetTime() {
        this.timeline.stop();
        this.actionTemps.reset();
        this.setText("0:00");
    }
}
