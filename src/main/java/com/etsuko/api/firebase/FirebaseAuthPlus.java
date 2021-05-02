/*
 * Conçu avec ♡ par Levasseur Wesley pour Etsuko.
 * © Copyright 2021. Tous droits réservés.
 *
 * Création datant du 1/5/2021 à 17:46:44.
 */

package com.etsuko.api.firebase;

import com.etsuko.api.firebase.interfaces.IFirebaseAuthPlus;

public class FirebaseAuthPlus implements IFirebaseAuthPlus {

    private static FirebaseAuthPlus instance;

    protected FirebaseAuthPlus() {
    }

    public static FirebaseAuthPlus getInstance() {
        return FirebaseAuthPlus.instance != null ? FirebaseAuthPlus.instance : (FirebaseAuthPlus.instance = new FirebaseAuthPlus());
    }

}
