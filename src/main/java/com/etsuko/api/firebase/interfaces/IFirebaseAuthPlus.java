/*
 * Conçu avec ♡ par Levasseur Wesley pour Etsuko.
 * © Copyright 2021. Tous droits réservés.
 *
 * Création datant du 1/5/2021 à 18:25:8.
 */

package com.etsuko.api.firebase.interfaces;

import com.google.gson.Gson;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public interface IFirebaseAuthPlus {

    default void signInWithPassword(String email, String password, SignInHandler signInHandler) {
        if (email == null || email.trim().isEmpty()) {
            signInHandler.handle(ResponseCodes.ERROR, null, new NullPointerException("The email address is null."));
            return;
        }
        if (password == null || password.trim().isEmpty()) {
            signInHandler.handle(ResponseCodes.ERROR, null, new NullPointerException("The password is null."));
            return;
        }
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + System.getenv("GOOGLE_API_WEB")).openConnection();
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            byte[] outData = ("{\"email\":\"" + email + "\",\"password\":\"" + password + "\",\"returnSecureToken\":true}").getBytes(StandardCharsets.UTF_8);
            httpURLConnection.setFixedLengthStreamingMode(outData.length);
            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            try (OutputStream os = httpURLConnection.getOutputStream()) {
                os.write(outData);
            }
            SignInData signInData = null;
            if (httpURLConnection.getResponseCode() == 200)
                signInData = new Gson().fromJson(new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())), SignInData.class);
            httpURLConnection.disconnect();
            signInHandler.handle(ResponseCodes.getResponseCodesFromResponseCode(httpURLConnection.getResponseCode()), signInData);
        } catch (IOException e) {
            signInHandler.handle(ResponseCodes.ERROR, null, new InternalError("It's currently not possible to perform this action for the following errors.", e));
        }
    }

    default void signInWithPasswordAsync(String email, String password, SignInHandler signInHandler) {
        new Thread(() -> this.signInWithPassword(email, password, signInHandler)).start();
    }

    enum ResponseCodes {
        OK(200),
        BAD(400),
        ERROR(-1);

        private final int responseCode;

        ResponseCodes(int responseCode) {
            this.responseCode = responseCode;
        }

        public static Map<Integer, ResponseCodes> getResponseCodes() {
            return Arrays.stream(values()).collect(Collectors.toMap(value -> value.responseCode, value -> value, (a, b) -> b));
        }

        public static ResponseCodes getResponseCodesFromResponseCode(int responseCode) {
            return ResponseCodes.getResponseCodes().get(responseCode);
        }

        public int getResponseCode() {
            return this.responseCode;
        }
    }

    interface SignInHandler {
        void handle(ResponseCodes responseCode, @Nullable SignInData signInData, Throwable... throwables);
    }

    class SignInData {
        private String localId;
        private String email;
        private String displayName;
        private String idToken;
        private boolean registered;
        private String refreshToken;
        private long expiresIn;

        public String getLocalId() {
            return this.localId;
        }

        public String getEmail() {
            return this.email;
        }

        public String getDisplayName() {
            return this.displayName;
        }

        public String getIdToken() {
            return this.idToken;
        }

        public boolean isRegistered() {
            return this.registered;
        }

        public String getRefreshToken() {
            return this.refreshToken;
        }

        public long getExpiresIn() {
            return this.expiresIn;
        }

        @Override
        public String toString() {
            return "DataResponse{" +
                    "localId='" + this.localId + '\'' +
                    ", email='" + this.email + '\'' +
                    ", displayName='" + this.displayName + '\'' +
                    ", idToken='" + this.idToken + '\'' +
                    ", registered=" + this.registered +
                    ", refreshToken='" + this.refreshToken + '\'' +
                    ", expiresIn=" + this.expiresIn +
                    "}";
        }
    }

}
