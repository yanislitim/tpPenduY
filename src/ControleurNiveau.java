import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.RadioButton;

/**
 * Controleur des radio boutons gérant le niveau
 */
public class ControleurNiveau implements EventHandler<ActionEvent> {

    /**
     * modèle du jeu
     */
    private MotMystere modelePendu;

    /**
     * @param modelePendu modèle du jeu
     */
    public ControleurNiveau(MotMystere modelePendu) {
        this.modelePendu = modelePendu;
    }

    /**
     * gère le changement de niveau
     * @param actionEvent
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        RadioButton radiobouton = (RadioButton) actionEvent.getTarget();
        String nomDuRadiobouton = radiobouton.getText();
        
        // Déterminer le niveau selon le texte du radio bouton
        int niveau;
        switch (nomDuRadiobouton) {
            case "Facile":
                niveau = MotMystere.FACILE;
                break;
            case "Moyen":
                niveau = MotMystere.MOYEN;
                break;
            case "Difficile":
                niveau = MotMystere.DIFFICILE;
                break;
            case "Expert":
                niveau = MotMystere.EXPERT;
                break;
            default:
                niveau = MotMystere.FACILE;
                break;
        }
        
        this.modelePendu.setNiveau(niveau);
        
        System.out.println("Niveau changé pour : " + nomDuRadiobouton + " (valeur : " + niveau + ")");
    }
}