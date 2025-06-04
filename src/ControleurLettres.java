import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 * Controleur du clavier
 */
public class ControleurLettres implements EventHandler<ActionEvent> {

    /**
     * modèle du jeu
     */
    private MotMystere modelePendu;
    /**
     * vue du jeu
     */
    private Pendu vuePendu;

    /**
     * @param modelePendu modèle du jeu
     * @param vuePendu vue du jeu
     */
    ControleurLettres(MotMystere modelePendu, Pendu vuePendu) {
        this.modelePendu = modelePendu;
        this.vuePendu = vuePendu;
    }

    /**
     * Actions à effectuer lors du clic sur une touche du clavier
     * Il faut donc: Essayer la lettre, mettre à jour l'affichage et vérifier si la partie est finie
     * @param actionEvent l'événement
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        // Récupérer la lettre du bouton cliqué
        Button boutonClique = (Button) actionEvent.getSource();
        String lettre = boutonClique.getText();
        char lettreChar = lettre.charAt(0);
        
        // Essayer la lettre dans le modèle
        int nbOccurrences = this.modelePendu.essaiLettre(lettreChar);
        
        // Mettre à jour l'affichage
        this.vuePendu.majAffichage();
        
        // Vérifier si la partie est terminée
        if (this.modelePendu.gagne()) {
            // Partie gagnée
            this.vuePendu.getChrono().stop();
            Alert alertGagne = this.vuePendu.popUpMessageGagne();
            alertGagne.showAndWait();
        } else if (this.modelePendu.perdu()) {
            // Partie perdue
            this.vuePendu.getChrono().stop();
            Alert alertPerdu = this.vuePendu.popUpMessagePerdu();
            alertPerdu.showAndWait();
        }
    }
}