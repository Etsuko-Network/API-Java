/*
 * Conçu avec ♡ par Levasseur Wesley pour Etsuko.
 * © Copyright 2021. Tous droits réservés.
 *
 * Création datant du 24/4/2021 à 23:44:56.
 */

package com.etsuko.api.core;

import com.etsuko.api.core.interfaces.IEtsukoAPI;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.IOException;

public class EtsukoAPI implements IEtsukoAPI {

    public EtsukoAPI() throws InternalError {
        Callback loadCallback = (success, errors) -> {

        };
        this.load(loadCallback);
    }

    @Override
    public void load(Callback callback) throws InternalError {
        try {
            FirebaseApp.initializeApp(FirebaseOptions.builder().setCredentials(GoogleCredentials.getApplicationDefault()).build());
        } catch (IOException e) {
            throw new InternalError("An error occurred while initializing FirebaseApp.", e);
        }
    }

}
