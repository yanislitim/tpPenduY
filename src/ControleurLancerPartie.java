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

    /**
     * @param modelePendu modèle du jeu
     * @param vuePendu vue du jeu
     */
    public ControleurLancerPartie(MotMystere modelePendu, Pendu vuePendu) {
        this.modelePendu = modelePendu;
        this.vuePendu = vuePendu;
    }

    /**
     * L'action consiste à recommencer une partie. Il faut vérifier qu'il n'y a pas une partie en cours
     * @param actionEvent l'événement action
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        boolean partieEnCours = this.modelePendu.getNbEssais() > 0 && 
                               !this.modelePendu.gagne() && 
                               !this.modelePendu.perdu();
        
        if (partieEnCours) {
            // Il y a une partie en cours, demander confirmation
            Optional<ButtonType> reponse = this.vuePendu.popUpPartieEnCours().showAndWait();
            
            if (reponse.isPresent() && reponse.get().equals(ButtonType.YES)) {
                // L'utilisateur confirme, on lance une nouvelle partie
                this.vuePendu.lancePartie();
                System.out.println("Nouvelle partie lancée (partie précédente interrompue)");
            } else {
                // L'utilisateur annule, on ne fait rien
                System.out.println("Lancement de partie annulé");
            }
        } else {
            // Pas de partie en cours, lancer directement une nouvelle partie
            this.vuePendu.lancePartie();
            System.out.println("Nouvelle partie lancée");
        }
    }
}
