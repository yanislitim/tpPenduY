
/**
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;


 * Controleur du clavier

public class ControleurLettres implements EventHandler<ActionEvent> {

    /**
     * modèle du jeu

    private MotMystere modelePendu;
    /**
     * vue du jeu

    private Pendu vuePendu;



    ControleurLettres(MotMystere modelePendu, Pendu vuePendu){
        // A implémenter
    }
}

    /**
    
    @Override
    public void handle(ActionEvent actionEvent) {
        Button bouton = (Button) event.getSource();
        String lettre = bouton.getText().toUpperCase();
        int nb = this.modelePendu.essaiLettre(lettre.charAt(0));
        bouton.setDisable(true);
        this.vuePendu.majAffichage();

        if (modelePendu.gagne()){
            vuePendu.popUpMessageGagne();}
        else if (modelePendu.perdu()){
            vuePendu.popUpMessagePerdu();
        }

    }

}

 */