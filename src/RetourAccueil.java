import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * Contrôleur à activer lorsque l'on clique sur le bouton Accueil
 */
public class RetourAccueil implements EventHandler<ActionEvent> {
    /**
     * modèle du jeu
     */
    private MotMystere modelePendu;
    /**
     * vue du jeu
     **/
    private Pendu vuePendu;

    /**
     * @param modelePendu modèle du jeu
     * @param vuePendu vue du jeu
     */
    public RetourAccueil(MotMystere modelePendu, Pendu vuePendu) {
        this.modelePendu = modelePendu;
        this.vuePendu = vuePendu;
    }

    /**
     * L'action consiste à retourner sur la page d'accueil. Il faut vérifier qu'il n'y avait pas une partie en cours
     * @param actionEvent l'événement action
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        // Vérifier si une partie est en cours
        if (modelePendu.getNbEssais() > 0 && !modelePendu.gagne() && !modelePendu.perdu()) {
            // Afficher popup de confirmation
            Optional<ButtonType> reponse = vuePendu.popUpPartieEnCours().showAndWait();
            
            if (reponse.isPresent() && reponse.get().equals(ButtonType.YES)) {
                // L'utilisateur confirme l'interruption
                vuePendu.modeAccueil();
                vuePendu.getChrono().stop();
                vuePendu.getChrono().resetTime();
            }
            // Sinon, on ne fait rien (reste en mode jeu)
        } else {
            // Pas de partie en cours, retour direct à l'accueil
            vuePendu.modeAccueil();
        }
    }
}
