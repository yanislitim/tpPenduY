import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import java.util.Optional;
/**
 * Contrôleur à activer lorsque l'on clique sur le bouton rejouer ou Lancer une partie
 */
public class ControleurLancerPartie implements EventHandler<ActionEvent> {
    /**
     * modèle du jeu
     */
    private MotMystere modelePendu;
    /**
     * vue du jeu
     **/
    private Pendu vuePendu;

    public ControleurLancerPartie(MotMystere modelePendu, Pendu vuePendu) {
        this.modelePendu = modelePendu;
        this.vuePendu = vuePendu;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        boolean partieEnCours = this.modelePendu.getNbEssais() > 0 && 
                                !this.modelePendu.gagne() && 
                                !this.modelePendu.perdu();
        
        if (partieEnCours) {
            Optional<ButtonType> reponse = this.vuePendu.popUpPartieEnCours().showAndWait();
            
            if (reponse.isPresent() && reponse.get().equals(ButtonType.YES)) {
                this.vuePendu.lancePartie();
                System.out.println("Nouvelle partie lancée (partie précédente interrompue)");
            } else {
                System.out.println("Lancement de partie annulé");
            }
        } else {
            this.vuePendu.lancePartie();
            System.out.println("Nouvelle partie lancée");
        }
    }
}
