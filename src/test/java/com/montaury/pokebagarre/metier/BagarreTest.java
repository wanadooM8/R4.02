package com.montaury.pokebagarre.metier;

import com.montaury.pokebagarre.erreurs.ErreurMemePokemon;
import com.montaury.pokebagarre.erreurs.ErreurPokemonNonRenseigne;
import com.montaury.pokebagarre.webapi.PokeBuildApi;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class BagarreTest {
    @Test
    void verif_nom_premier_pokemon_vide() {
        // GIVEN
        Bagarre bagarre = new Bagarre();
        String nomPremier = "";
        String nomSecond = "pikachu";

        // WHEN
        Throwable thrown = catchThrowable(() -> bagarre.demarrer(nomPremier, nomSecond));

        // THEN
        assertThat(thrown)
                .isInstanceOf(ErreurPokemonNonRenseigne.class)
                .hasMessage("Le premier pokemon n'est pas renseigne");
    }

    @Test
    void verif_nom_second_pokemon_vide() {
        // GIVEN
        Bagarre bagarre = new Bagarre();
        String nomPremier = "pikachu";
        String nomSecond = "";

        // WHEN
        Throwable thrown = catchThrowable(() -> bagarre.demarrer(nomPremier, nomSecond));

        // THEN
        assertThat(thrown)
                .isInstanceOf(ErreurPokemonNonRenseigne.class)
                .hasMessage("Le second pokemon n'est pas renseigne");
    }

    @Test
    void verif_premier_pokemon_null() {
        // GIVEN
        Bagarre bagarre = new Bagarre();
        String nomPremier = null;
        String nomSecond = "pikachu";

        // WHEN
        Throwable thrown = catchThrowable(() -> bagarre.demarrer(nomPremier, nomSecond));

        // THEN
        assertThat(thrown).isInstanceOf(ErreurPokemonNonRenseigne.class);
    }

    @Test
    void verif_second_pokemon_null() {
        // GIVEN
        Bagarre bagarre = new Bagarre();
        String nomPremier = "pikachu";
        String nomSecond = null;

        // WHEN
        Throwable thrown = catchThrowable(() -> bagarre.demarrer(nomPremier, nomSecond));

        // THEN
        assertThat(thrown).isInstanceOf(ErreurPokemonNonRenseigne.class);
    }

    @Test
    void verif_pokemon_identique() {
        // GIVEN
        Bagarre bagarre = new Bagarre();
        String nomPremier = "pikachu";
        String nomSecond = "pikachu";

        // WHEN
        Throwable thrown = catchThrowable(() -> bagarre.demarrer(nomPremier, nomSecond));

        // THEN
        // On suppose ici qu'une erreur spécifique existe pour deux noms identiques
        assertThat(thrown)
                .isInstanceOf(ErreurMemePokemon.class)
                .hasMessage("Impossible de faire se bagarrer un pokemon avec lui-meme");
    }

    @Test
    public void test_renvoie_1er_pokemon() {
        // GIVEN
        var fausseApi = Mockito.mock(PokeBuildApi.class);
        Bagarre bagarre = new Bagarre(fausseApi);

        Pokemon pika = new Pokemon("pikachu", "url1", new Stats(10, 10));

        Mockito.when(fausseApi.recupererParNom("pikachu"))
                .thenReturn(CompletableFuture.completedFuture(pika));

        // WHEN
        var futurPokemon = fausseApi.recupererParNom("pikachu");

        // THEN
        assertThat(futurPokemon)
                .succeedsWithin(Duration.ofSeconds(2))
                .satisfies(pokemon -> {
                    assertThat(pokemon.getNom()).isEqualTo("pikachu");
                });
    }

    @Test
    public void test_renvoie_2eme_pokemon() {
        // GIVEN
        var fausseApi = Mockito.mock(PokeBuildApi.class);
        Bagarre bagarre = new Bagarre(fausseApi);

        Pokemon carapuce = new Pokemon("carapuce", "url2", new Stats(5, 5));

        Mockito.when(fausseApi.recupererParNom("carapuce"))
                .thenReturn(CompletableFuture.completedFuture(carapuce));

        // WHEN
        var futurPokemon = fausseApi.recupererParNom("carapuce");

        // THEN
        assertThat(futurPokemon)
                .succeedsWithin(Duration.ofSeconds(2))
                .satisfies(pokemon -> {
                    assertThat(pokemon.getNom()).isEqualTo("carapuce");
                });
    }

    @Test
    public void test1erPokemon_gagne() {
        // GIVEN
        var fausseApi = Mockito.mock(PokeBuildApi.class);
        Bagarre bagarre = new Bagarre(fausseApi);

        Pokemon pika = new Pokemon("pikachu", "url1", new Stats(100, 10));
        Pokemon ratta = new Pokemon("rattata", "url2", new Stats(10, 5));

        Mockito.when(fausseApi.recupererParNom("pikachu"))
                .thenReturn(CompletableFuture.completedFuture(pika));
        Mockito.when(fausseApi.recupererParNom("rattata"))
                .thenReturn(CompletableFuture.completedFuture(ratta));

        // WHEN
        var futurVainqueur = bagarre.demarrer("pikachu", "rattata");

        // THEN
        assertThat(futurVainqueur)
                .succeedsWithin(Duration.ofSeconds(2))
                .satisfies(pokemon -> {
                    assertThat(pokemon.getNom()).isEqualTo("pikachu");
                });
    }

    @Test
    public void test2emePokemon_gagne() {
        // GIVEN
        var fausseApi = Mockito.mock(PokeBuildApi.class);
        Bagarre bagarre = new Bagarre(fausseApi);

        Pokemon magi = new Pokemon("magicarpe", "url1", new Stats(10, 1));
        Pokemon mew = new Pokemon("mewtwo", "url2", new Stats(500, 100));

        Mockito.when(fausseApi.recupererParNom("magicarpe"))
                .thenReturn(CompletableFuture.completedFuture(magi));
        Mockito.when(fausseApi.recupererParNom("mewtwo"))
                .thenReturn(CompletableFuture.completedFuture(mew));

        // WHEN
        var futurVainqueur = bagarre.demarrer("magicarpe", "mewtwo");

        // THEN
        assertThat(futurVainqueur)
                .succeedsWithin(Duration.ofSeconds(2))
                .satisfies(pokemon -> {
                    assertThat(pokemon.getNom()).isEqualTo("mewtwo");
                });
    }
}