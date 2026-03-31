package com.montaury.pokebagarre.ui;
import java.util.concurrent.TimeUnit;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
@ExtendWith(ApplicationExtension.class)
class PokeBagarreAppTest {
    /* Liste des differents tests de la classe de pokeBagarreAppTest.java
        - le premier pokemon saisi est un pokemon qui n'existe pas
        - le second pokemon saisi est un pokemon qui n'existe pas
        - le premier pokemon saisi est vide
        - le second pokemon saisi est vide
        - les deux pokemon saisi sont les memes
        - les deux pokemon saisi sont vides
        - les deux pokemon saisi


     */
    private static final String IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_1 = "#nomPokemon1";
    private static final String IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_2 = "#nomPokemon2";
    private static final String IDENTIFIANT_BOUTON_BAGARRE = ".button";

    @Start
    private void start(Stage stage) {
        new PokeBagarreApp().start(stage);
    }
    @Test
    void devrait_afficher_une_erreur_si_premier_pokemon_saisi_est_vide(FxRobot robot) {
        //robot.clickOn(IDENTIFIANT);
        //robot.write("Text");
        //await().atMost(5, TimeUnit.SECONDS).untilAsserted(() ->
        //assertThat(...).isEqualTo(...)
        // );

        //GIVEN
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_1);
        robot.write("");

        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_2);
        robot.write("pikachu");

        //WHEN
        robot.clickOn(IDENTIFIANT_BOUTON_BAGARRE);

        //THEN
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            assertThat(getMessageErreur(robot)).isEqualTo("Erreur: Le premier pokemon n'est pas renseigne");
        });

    }

    @Test
    void devrait_afficher_une_erreur_si_second_pokemon_saisi_est_vide(FxRobot robot) {
        //GIVEN
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_1);
        robot.write("pikachu");

        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_2);
        robot.write("");

        //WHEN
        robot.clickOn(IDENTIFIANT_BOUTON_BAGARRE);

        //THEN
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            assertThat(getMessageErreur(robot)).isEqualTo("Erreur: Le second pokemon n'est pas renseigne");
        });

    }

    @Test
    void devrait_afficher_une_erreur_si_deux_pokemon_saisi_est_vide(FxRobot robot) {
        //GIVEN
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_1);
        robot.write("");

        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_2);
        robot.write("");

        //WHEN
        robot.clickOn(IDENTIFIANT_BOUTON_BAGARRE);

        //THEN
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            assertThat(getMessageErreur(robot)).isEqualTo("Erreur: Le premier pokemon n'est pas renseigne");
        });
    }

    @Test
    void devrait_afficher_une_erreur_si_premier_pokemon_existe_pas(FxRobot robot) {
        //GIVEN
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_1);
        robot.write("Gab");

        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_2);
        robot.write("pikachu");

        //WHEN
        robot.clickOn(IDENTIFIANT_BOUTON_BAGARRE);

        //THEN
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            assertThat(getMessageErreur(robot)).isEqualTo("Erreur: Impossible de recuperer les details sur 'Gab'");
        });
    }

    @Test
    void devrait_afficher_une_erreur_si_second_pokemon_existe_pas(FxRobot robot) {
        //GIVEN
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_1);
        robot.write("pikachu");

        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_2);
        robot.write("Gab");

        //WHEN
        robot.clickOn(IDENTIFIANT_BOUTON_BAGARRE);

        //THEN
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            assertThat(getMessageErreur(robot)).isEqualTo("Erreur: Impossible de recuperer les details sur 'Gab'");
        });
    }

    @Test
    void devrait_afficher_une_erreur_si_les_deux_pokemon_saisi_sont_identiques(FxRobot robot) {
        //GIVEN
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_1);
        robot.write("pikachu");

        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_2);
        robot.write("pikachu");

        //WHEN
        robot.clickOn(IDENTIFIANT_BOUTON_BAGARRE);

        //THEN
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            assertThat(getMessageErreur(robot)).isEqualTo("Erreur: Impossible de faire se bagarrer un pokemon avec lui-meme");
        });
    }

    @Test
    void devrait_afficher_un_resultat_si_les_deux_pokemon_saisi_existe(FxRobot robot) {
        //GIVEN
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_1);
        robot.write("pikachu");

        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_2);
        robot.write("Mewtwo");

        //WHEN
        robot.clickOn(IDENTIFIANT_BOUTON_BAGARRE);

        //THEN
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            assertThat(getResultatBagarre(robot)).isEqualTo("Le vainqueur est: mewtwo");
        });
    }


    private static String getResultatBagarre(FxRobot robot) {
        return robot.lookup("#resultatBagarre").queryText().getText();
    }

    private static String getMessageErreur(FxRobot robot) {
        return robot.lookup("#resultatErreur").queryLabeled().getText();
    }
}

