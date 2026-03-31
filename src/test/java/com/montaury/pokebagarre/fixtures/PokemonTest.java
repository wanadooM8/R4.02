package com.montaury.pokebagarre.fixtures;
import static com.montaury.pokebagarre.fixtures.ConstructeurDePokemon.unPokemon;
import static org.assertj.core.api.Assertions.assertThat;


import com.montaury.pokebagarre.metier.Pokemon;
import org.junit.jupiter.api.Test;

/**
 * Tests unitaires pour la classe Pokemon
 *
 * Liste des scénarios :
 * - Le premier Pokemon gagne s'il a une meilleure attaque
 * - Le second Pokemon gagne s'il a une meilleure attaque
 * - Le premier Pokemon gagne si les attaques sont égales mais qu'il a une meilleure défense
 * - Le second Pokemon gagne si les attaques sont égales mais qu'il a une meilleure défense
 * - Le premier Pokemon gagne si les statistiques sont égales (il est choisi en premier)
 */
public class PokemonTest {
    @Test
    void premierPokemon_gagne_avec_meilleure_attaque(){
        //GIVEN
        Pokemon premier = unPokemon().avecAttaque(10).avecDefense(5).construire();
        Pokemon second = unPokemon().avecAttaque(5).avecDefense(5).construire();

        //WHEN
        boolean resultat = premier.estVainqueurContre(second);

        //THEN
        assertThat(resultat).isTrue(); // le premier pokemon doit gagner
    }

    @Test
    void second_pokemon_gagne_avec_meilleure_attaque() {
        // GIVEN
        Pokemon premier = unPokemon().avecAttaque(5).avecDefense(5).construire();
        Pokemon second = unPokemon().avecAttaque(10).avecDefense(5).construire();

        // WHEN
        boolean resultat = premier.estVainqueurContre(second);

        // THEN
        assertThat(resultat).isFalse(); // Le premier Pokemon doit perdre, le deuxième doit gagner
    }

    @Test
    void premier_pokemon_gagne_avec_meilleure_defense_quand_attaque_egale() {
        // GIVEN
        Pokemon premier = unPokemon().avecAttaque(10).avecDefense(10).construire();
        Pokemon second = unPokemon().avecAttaque(10).avecDefense(5).construire();

        // WHEN
        boolean resultat = premier.estVainqueurContre(second);

        // THEN
        assertThat(resultat).isTrue(); // Le premier Pokemon gagne car il a une meilleure défense
    }

    @Test
    void second_pokemon_gagne_avec_meilleure_defense_quand_attaque_egale() {
        // GIVEN
        Pokemon premier = unPokemon().avecAttaque(10).avecDefense(5).construire();
        Pokemon second = unPokemon().avecAttaque(10).avecDefense(10).construire();

        // WHEN
        boolean resultat = premier.estVainqueurContre(second);

        // THEN
        assertThat(resultat).isFalse(); // Le premier Pokemon doit perdre, le second gagne car il a une meilleure défense
    }

    @Test
    void premier_pokemon_gagne_quand_stats_egale_mais_choisi_en_premier() {
        // GIVEN
        Pokemon premier = unPokemon().avecAttaque(10).avecDefense(10).construire();
        Pokemon second = unPokemon().avecAttaque(10).avecDefense(10).construire();

        // WHEN
        boolean resultat = premier.estVainqueurContre(second);

        // THEN
        assertThat(resultat).isTrue(); // Le premier Pokemon gagne car même si les stats sont égales, il est choisi en premier
    }

}
