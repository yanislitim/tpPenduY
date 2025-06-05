import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ButtonBar.ButtonData;

import java.util.List;
import java.util.Arrays;
import java.io.File;
import java.util.ArrayList;

/**
 * Vue du jeu du pendu
 */
public class Pendu extends Application {
    /**
     * modèle du jeu
     **/
    private MotMystere modelePendu;
    /**
     * Liste qui contient les images du jeu
     */
    private ArrayList<Image> lesImages;
    /**
     * Liste qui contient les noms des niveaux
     */
    public List<String> niveaux;

    // les différents contrôles qui seront mis à jour ou consultés pour l'affichage
    /**
     * le dessin du pendu
     */
    private ImageView dessin;
    /**
     * le mot à trouver avec les lettres déjà trouvé
     */
    private Text motCrypte;
    /**
     * la barre de progression qui indique le nombre de tentatives
     */
    private ProgressBar pg;
    /**
     * le clavier qui sera géré par une classe à implémenter
     */
    private Clavier clavier;
    /**
     * le text qui indique le niveau de difficulté
     */
    private Text leNiveau;
    /**
     * le chronomètre qui sera géré par une clasee à implémenter
     */
    private Chronometre chrono;
    /**
     * le panel Central qui pourra être modifié selon le mode (accueil ou jeu)
     */
    private BorderPane panelCentral;
    /**
     * le bouton Paramètre / Engrenage
     */
    private Button boutonParametres;
    /**
     * le bouton Accueil / Maison
     */
    private Button boutonMaison;
    /**
     * le bouton qui permet de (lancer ou relancer une partie
     */
    private Button bJouer;

    /**
     * initialise les attributs (créer le modèle, charge les images, crée le chrono
     * ...)
     */
    @Override
    public void init() {
        try {
            this.modelePendu = new MotMystere("/usr/share/dict/french", 3, 10, MotMystere.FACILE, 10);
        } catch (Exception e) {
            this.modelePendu = new MotMystere("JAVA", MotMystere.FACILE, 10);
        }

        this.lesImages = new ArrayList<Image>();
        this.chargerImages("./img");
        this.niveaux = Arrays.asList("Facile", "Moyen", "Difficile", "Expert");
        this.chrono = new Chronometre();
        this.dessin = new ImageView();
        this.motCrypte = new Text();
        this.motCrypte.setFont(Font.font("Arial", 24));
        this.pg = new ProgressBar();
        this.leNiveau = new Text("Niveau: Facile");
        this.clavier = new Clavier("ABCDEFGHIJKLMNOPQRSTUVWXYZ", new ControleurLettres(this.modelePendu, this));
        this.panelCentral = new BorderPane();

        this.boutonMaison = new Button();
        try {
            Image imageMaison = new Image("file:./img/home.png");
            ImageView imageViewMaison = new ImageView();
            imageViewMaison.setImage(imageMaison);
            imageViewMaison.setFitWidth(20);
            imageViewMaison.setFitHeight(20);
            imageViewMaison.setPreserveRatio(true);
            boutonMaison.setGraphic(imageViewMaison);
        } catch (Exception e) {
            boutonMaison.setText("Home");
        }
        boutonMaison.setOnAction(new RetourAccueil(this.modelePendu, this));
        boutonMaison.setTooltip(new Tooltip("Retour à l'accueil"));

        this.boutonParametres = new Button();
        try {
            Image imageParam = new Image("file:./img/parametres.png");
            ImageView imageViewParam = new ImageView();
            imageViewParam.setImage(imageParam);
            imageViewParam.setFitWidth(20);
            imageViewParam.setFitHeight(20);
            imageViewParam.setPreserveRatio(true);
            boutonParametres.setGraphic(imageViewParam);
        } catch (Exception e) {
            boutonParametres.setText("⚙");
        }
        boutonParametres.setTooltip(new Tooltip("Paramètres"));

        this.bJouer = new Button("Lancer une partie");
        this.bJouer.setOnAction(new ControleurLancerPartie(this.modelePendu, this));
    }

    private Scene laScene() {
        BorderPane fenetre = new BorderPane();
        fenetre.setTop(this.titre());
        fenetre.setCenter(this.panelCentral);
        return new Scene(fenetre, 800, 1000);
    }

    private Pane titre() {
        BorderPane banniere = new BorderPane();
        banniere.setPadding(new Insets(10));

        Text titre = new Text("Jeu du pendu");
        titre.setFont(Font.font("Arial", 24));
        titre.setFill(Color.BLACK);

        Button information = new Button();
        try {
            Image imageInfo = new Image("file:./img/info.png");
            ImageView imageViewInfo = new ImageView();
            imageViewInfo.setImage(imageInfo);
            imageViewInfo.setFitWidth(20);
            imageViewInfo.setFitHeight(20);
            imageViewInfo.setPreserveRatio(true);
            information.setGraphic(imageViewInfo);
        } catch (Exception e) {
            information.setText("i");
        }
        information.setOnAction(new ControleurInfos(this));
        information.setTooltip(new Tooltip("Règles du jeu"));

        HBox boutons = new HBox(10);
        boutons.getChildren().addAll(this.boutonMaison, this.boutonParametres, information);

        banniere.setLeft(titre);
        banniere.setRight(boutons);
        banniere.setStyle("-fx-background-color:#e6e6ff;");

        return banniere;
    }

    private TitledPane leChrono() {
        TitledPane res = new TitledPane();
        res.setText("Chronomètre");
        res.setContent(this.chrono);
        res.setCollapsible(false);
        return res;
    }

private Pane fenetreJeu() {
    BorderPane res = new BorderPane();

    VBox partie1 = new VBox(20);
    partie1.setPadding(new Insets(20));

    this.motCrypte.setFont(Font.font("Arial", 32));

    // Image du pendu
    this.dessin.setFitWidth(300);
    this.dessin.setFitHeight(300);
    this.dessin.setPreserveRatio(true);

    this.pg.setPrefWidth(300);
    this.pg.setProgress(1.0);

    HBox imageBox = new HBox(this.dessin);
    imageBox.setAlignment(Pos.CENTER);

    HBox progressBox = new HBox(this.pg);
    progressBox.setAlignment(Pos.CENTER);

    partie1.getChildren().addAll(this.motCrypte, imageBox, progressBox, this.clavier);

    VBox partie2 = new VBox(20);
    partie2.setPadding(new Insets(0, 20, 0, 0)); // Ajoute un padding à droite

    // Bouton "Nouveau mot"
    Button boutonNouveauMot = new Button("Nouveau mot");
    boutonNouveauMot.setOnAction(e -> this.lancePartie());

    partie2.getChildren().addAll(this.leNiveau, this.leChrono(), boutonNouveauMot);

    res.setCenter(partie1);
    res.setRight(partie2);

    return res;
}


    private Pane fenetreAccueil() {
        VBox res = new VBox(30);

        ToggleGroup groupeNiveau = new ToggleGroup();
        VBox niveauxBox = new VBox(10);

        RadioButton facile = new RadioButton("Facile");
        facile.setToggleGroup(groupeNiveau);
        facile.setSelected(true);
        facile.setOnAction(new ControleurNiveau(this.modelePendu));

        RadioButton moyen = new RadioButton("Moyen");
        moyen.setToggleGroup(groupeNiveau);
        moyen.setOnAction(new ControleurNiveau(this.modelePendu));

        RadioButton difficile = new RadioButton("Difficile");
        difficile.setToggleGroup(groupeNiveau);
        difficile.setOnAction(new ControleurNiveau(this.modelePendu));

        RadioButton expert = new RadioButton("Expert");
        expert.setToggleGroup(groupeNiveau);
        expert.setOnAction(new ControleurNiveau(this.modelePendu));

        niveauxBox.getChildren().addAll(facile, moyen, difficile, expert);
        niveauxBox.setPadding(new Insets(10));

        TitledPane boiteNiveaux = new TitledPane("Niveau de difficulté", niveauxBox);
        boiteNiveaux.setCollapsible(false);

        this.bJouer.setFont(Font.font("Arial", 18));

        res.getChildren().addAll(this.bJouer, boiteNiveaux);
        res.setPadding(new Insets(20));

        return res;
    }

    private void chargerImages(String repertoire) {
        for (int i = 0; i < this.modelePendu.getNbErreursMax() + 1; i++) {
            try {
                File file = new File(repertoire + "/pendu" + i + ".png");
                if (file.exists()) {
                    this.lesImages.add(new Image(file.toURI().toString()));
                    System.out.println("Image chargée: " + file.toURI().toString());
                } else {
                    System.out.println("Image non trouvée: " + file.getAbsolutePath());
                }
            } catch (Exception e) {
                System.out.println("Erreur lors du chargement de l'image " + i + ": " + e.getMessage());
            }
        }
    }

    public void modeAccueil() {
        this.panelCentral.setCenter(this.fenetreAccueil());
        this.boutonMaison.setDisable(true);
        this.boutonParametres.setDisable(false);
    }

    public void modeJeu() {
        this.panelCentral.setCenter(this.fenetreJeu());
        this.boutonMaison.setDisable(false);
        this.boutonParametres.setDisable(true);
        this.majAffichage();
        this.chrono.start();
    }

    public void modeParametres() {
        this.modeAccueil();
    }

    public void lancePartie() {
        try {
            this.modelePendu.setMotATrouver();
        } catch (Exception e) {
            this.modelePendu.setMotATrouver("JAVA");
        }
        this.modeJeu();
    }

    public void majAffichage() {
        this.motCrypte.setText(this.modelePendu.getMotCrypte());
        int nbErreurs = this.modelePendu.getNbErreursMax() - this.modelePendu.getNbErreursRestants();
        if (nbErreurs < this.lesImages.size()) {
            this.dessin.setImage(this.lesImages.get(nbErreurs));
        }
        double progression = (double) this.modelePendu.getNbErreursRestants() / this.modelePendu.getNbErreursMax();
        this.pg.setProgress(progression);
        String[] niveauxTexte = { "Facile", "Moyen", "Difficile", "Expert" };
        this.leNiveau.setText("Niveau: " + niveauxTexte[this.modelePendu.getNiveau()]);

        this.clavier.desactiveTouches(this.modelePendu.getLettresEssayees());
    }

    public Chronometre getChrono() {
        return this.chrono;
    }

    public Alert popUpPartieEnCours() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "La partie est en cours!\n Etes-vous sûr de l'interrompre ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Attention");
        return alert;
    }

    public Alert popUpReglesDuJeu() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Règles du jeu");
        alert.setHeaderText("Comment jouer au pendu ?");
        alert.setContentText(
            "Proposez des lettres pour deviner le mot mystère.\n" +
            "• Bonne lettre : elle s'affiche.\n" +
            "• Mauvaise lettre : le pendu avance.\n" +
            "• Gagnez en trouvant le mot, perdez si le dessin est complet.\n\n" +
            "Difficultés :\n" +
            "• Facile : 3 lettres visibles.\n" +
            "• Moyen : 2 lettres visibles.\n" +
            "• Difficile : 1 lettre visible.\n" +
            "• Expert : aucune aide.");
        return alert;
    }


    public Alert popUpMessageGagne() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Félicitations !");
        alert.setHeaderText("Vous avez gagné !");
        alert.setContentText("Bravo ! Vous avez trouvé le mot : " + this.modelePendu.getMotATrouve() +
                "\nNombre d'essais : " + this.modelePendu.getNbEssais() +
                "\nTemps écoulé : " + this.chrono.getText());
        return alert;
    }

    public Alert popUpMessagePerdu() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Dommage !");
        alert.setHeaderText("Vous avez perdu !");
        alert.setContentText("Le mot à trouver était : " + this.modelePendu.getMotATrouve() +
                "\nNombre d'essais : " + this.modelePendu.getNbEssais() +
                "\nTemps écoulé : " + this.chrono.getText());
        return alert;
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("IUTEAM'S - La plateforme de jeux de l'IUTO");
        stage.setScene(this.laScene());
        this.modeAccueil();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}