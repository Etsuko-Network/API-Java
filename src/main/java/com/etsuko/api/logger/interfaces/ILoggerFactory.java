/*
 * Conçu avec ♡ par Levasseur Wesley pour Etsuko.
 * © Copyright 2021. Tous droits réservés.
 *
 * Création datant du 25/4/2021 à 17:21:22.
 */

package com.etsuko.api.logger.interfaces;

import com.etsuko.api.logger.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public interface ILoggerFactory {

    default List<String> getLogs() {
        return new ArrayList<>();
    }

    default void addLog(String log) {
        this.getLogs().add(log);
    }

    File getLogsDir();

    default ConcurrentMap<String, ILogger> getLoggers() {
        return new ConcurrentHashMap<>();
    }

    default ILogger getLogger(String name) {
        ILogger logger = this.getLoggers().get(name);
        if (logger != null) return logger;
        else {
            ILogger n = new Logger(name, this);
            ILogger o = this.getLoggers().putIfAbsent(name, n);
            return o == null ? n : o;
        }
    }

    default SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat("MMMM dd',' yyyy '-' hh:mm:ss aaa '('zZ')'");
    }

    default void save(List<String>[] allLogs) throws IOException {
        List<String> logs = new ArrayList<>();
        Arrays.asList(allLogs).forEach(logs::addAll);
        this.save(logs);
    }

    default void save(List<String> logs) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.getLogsDir().getPath() + "/" + this.getDateFormat().format(new Date()).replace(":", "-").replace(" ", "_") + ".log"), StandardCharsets.UTF_8));
        for (String line : logs) {
            writer.write(line);
            writer.newLine();
        }
        writer.flush();
        writer.close();
    }

    default void save() throws IOException {
        this.save(this.getLogs());
    }

}
