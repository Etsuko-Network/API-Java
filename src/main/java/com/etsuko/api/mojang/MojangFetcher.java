/*
 * Conçu avec ♡ par Levasseur Wesley pour Etsuko.
 * © Copyright 2021. Tous droits réservés.
 *
 * Création datant du 25/4/2021 à 13:27:47.
 */

package com.etsuko.api.mojang;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * MojangFetcher est une classe permettant la gestion de l'API Mojang pour y récupérer des données.
 *
 * @author Levasseur Wesley
 * @version 1.0.0
 */
public class MojangFetcher {

    private static final String UUID_URL = "https://api.mojang.com/users/profiles/minecraft/%s?at=%d";
    private static final String NAMES_URL = "https://api.mojang.com/user/profiles/%s/names";
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(UUID.class, new UUIDTypeAdapter()).create();

    /**
     * Cette fonction a pour but de récupérer l'UUID du joueur Minecraft au temps actuelle
     * désigner par son nom d'utilisateur en paramètre cette dernière renverra "null"
     * dans le cas échouant ou n'existant pas.
     *
     * @param username le nom d'utilisateur minecraft de la personne désignée.
     * @return l'UUID du joueur Minecraft si ce dernier existe sinon ce dernier renverra  "null".
     */
    public static UUID getUUID(String username) {
        return MojangFetcher.getUUIDAt(username, System.currentTimeMillis());
    }

    /**
     * Cette fonction a pour but de récupérer l'UUID du joueur Minecraft au temps donné en paramètre
     * désigner par son nom d'utilisateur en paramètre cette dernière renverra "null"
     * dans le cas échouant ou n'existant pas.
     *
     * @param username  le nom d'utilisateur minecraft de la personne désignée.
     * @param timestamp le temps à laquelle l'UUID souhaite être récupéré.
     * @return l'UUID du joueur Minecraft si ce dernier existe sinon ce dernier renverra  "null".
     */
    public static UUID getUUIDAt(String username, long timestamp) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(String.format(MojangFetcher.UUID_URL, username.toLowerCase(), timestamp / 1000)).openConnection();
            httpURLConnection.setReadTimeout(5000);
            return MojangFetcher.gson.fromJson(new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())), Data.class).id;
        } catch (Throwable ignored) {
        }
        return null;
    }

    /**
     * Cette fonction a pour but de récupérer le nom d'utilisateur du joueur Minecraft au temps actuelle
     * désigner par son UUID en paramètre cette dernière renverra "null" dans le cas échouant ou n'existant pas.
     *
     * @param uuid l'UUID de la personne désignée.
     * @return le nom d'utilisateur du joueur Minecraft si ce dernier existe sinon ce dernier renverra  "null".
     */
    public static String getUsername(UUID uuid) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(String.format(MojangFetcher.NAMES_URL, UUIDTypeAdapter.fromUUID(uuid))).openConnection();
            httpURLConnection.setReadTimeout(5000);
            return Arrays.stream(MojangFetcher.gson.fromJson(new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())), Data[].class)).findFirst().orElseGet(Data::new).name;
        } catch (Throwable ignored) {
        }
        return null;
    }

    /**
     * Cette fonction a pour but de récupérer les noms d'utilisateur du joueur Minecraft au temps actuelle
     * désigner par son UUID en paramètre cette dernière renverra "null" dans le cas échouant ou n'existant pas.
     *
     * @param uuid l'UUID de la personne désignée.
     * @return les noms d'utilisateur du joueur Minecraft si ce dernier existe sinon ce dernier renverra  "null".
     */
    public static ArrayList<String> getUsernames(UUID uuid) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(String.format(MojangFetcher.NAMES_URL, UUIDTypeAdapter.fromUUID(uuid))).openConnection();
            httpURLConnection.setReadTimeout(5000);
            return Arrays.stream(MojangFetcher.gson.fromJson(new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())), Data[].class)).map(data -> data.name).collect(Collectors.toCollection(ArrayList::new));
        } catch (Throwable ignored) {
        }
        return null;
    }

    private static class Data {
        private UUID id;
        private String name;
    }

    public static class UUIDTypeAdapter extends TypeAdapter<UUID> {

        public static String fromUUID(UUID value) {
            return value.toString().replace("-", "");
        }

        public static UUID fromString(String input) {
            return UUID.fromString(input.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
        }

        public void write(JsonWriter out, UUID value) throws IOException {
            out.value(UUIDTypeAdapter.fromUUID(value));
        }

        public UUID read(JsonReader in) throws IOException {
            return UUIDTypeAdapter.fromString(in.nextString());
        }
    }

}
