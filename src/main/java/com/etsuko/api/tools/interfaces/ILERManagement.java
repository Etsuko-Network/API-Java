/*
 * Conçu avec ♡ par Levasseur Wesley pour Etsuko.
 * © Copyright 2021. Tous droits réservés.
 *
 * Création datant du 25/4/2021 à 12:37:15.
 */

package com.etsuko.api.tools.interfaces;

/**
 * ILERManagement est une interface et permet la gestion des niveaux, expériences et de renaissances.
 * (En anglais: Levels, Experiences, Rebirths)
 *
 * @author Levasseur Wesley
 */
public interface ILERManagement {

    /**
     * Récupération de la valeur de renaissances.
     *
     * @return la valeur de renaissances.
     */
    long getRebirths();

    /**
     * Incrémente la valeur de renaissances.
     */
    void incrementRebirth();

    /**
     * Récupération de la valeur des niveaux nécessaire à la renaissances.
     *
     * @return la valeur des niveaux nécessaire à la renaissances.
     */
    long getNecessaryLevelsToRebirths();

    /**
     * Récupération de la valeur des niveaux.
     *
     * @return la valeur des niveaux.
     */
    long getLevels();

    /**
     * Incrémente la valeur des niveaux.
     */
    void incrementLevels();

    /**
     * Retire des niveaux.
     *
     * @param levels Montant à retirer des niveaux.
     */
    void removeLevels(long levels);

    /**
     * Récupération de la valeur de l'expérience.
     *
     * @return la valeur de l'expérience.
     */
    double getExperiences();

    /**
     * Ajoute de l'expérience.
     *
     * @param experiences Montant à ajouter de l'expérience.
     */
    default void addExperiences(double experiences) {
        this.machining();
    }

    /**
     * Retire de l'expérience.
     *
     * @param experiences Montant à retirer de l'expérience.
     */
    void removeExperiences(double experiences);

    /**
     * Récupération de la valeur de l'expérience nécessaire pour monter de niveau.
     *
     * @return la valeur de l'expérience nécessaire pour monter de niveau.
     */
    default double getExperiencesToLevelUp() {
        return this.getLevels() * this.getLevels() * 28.5 * (this.getRebirths() + 1);
    }

    /**
     * Effectue tout le machinage de la gestion des niveaux, expériences et de renaissances.
     */
    private void machining() {
        if (this.getLevels() >= this.getNecessaryLevelsToRebirths()) {
            this.removeLevels(this.getNecessaryLevelsToRebirths() - 1L);
            this.removeExperiences(this.getExperiences());
            this.incrementRebirth();
        }

        if (this.getExperiences() >= this.getExperiencesToLevelUp()) {
            this.removeExperiences(this.getExperiencesToLevelUp());
            this.incrementLevels();
            this.machining();
        }
    }

    /**
     * Récupération de la valeur de l'expérience nécessaire pour monter de niveau en pourcentage.
     *
     * @return la valeur de l'expérience nécessaire pour monter de niveau en pourcentage.
     */
    default double getExperiencesToLevelupPercent() {
        return this.getExperiences() / this.getExperiencesToLevelUp() * 100d;
    }

}
