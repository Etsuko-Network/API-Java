/*
 * Conçu avec ❤  par Levasseur Wesley pour Etsuko.
 * © Copyright 2021. Tous droits réservés.
 *
 * Création datant du 25/4/2021 à 12:37:3.
 */

package com.etsuko.api.tools;

import com.etsuko.api.tools.interfaces.ILERManagement;
import dev.morphia.annotations.Embedded;

@Embedded
public class LERManagement implements ILERManagement {

    private static final long NECESSARY_LEVELS_TO_REBIRTHS = 100;
    private long rebirths = 0, levels = 0;
    private double experiences = 0d;

    public LERManagement() {
    }

    @Override
    public long getRebirths() {
        return this.rebirths;
    }

    @Override
    public void incrementRebirth() {
        this.rebirths++;
    }

    @Override
    public long getNecessaryLevelsToRebirths() {
        return LERManagement.NECESSARY_LEVELS_TO_REBIRTHS;
    }

    @Override
    public long getLevels() {
        return this.levels;
    }

    @Override
    public void incrementLevels() {
        this.levels++;
    }

    @Override
    public void removeLevels(long levels) {
        this.levels -= levels;
    }

    @Override
    public double getExperiences() {
        return this.experiences;
    }

    @Override
    public void addExperiences(double experiences) {
        this.experiences += experiences;
        ILERManagement.super.addExperiences(experiences);
    }

    @Override
    public void removeExperiences(double experiences) {
        this.experiences -= experiences;
    }
}
