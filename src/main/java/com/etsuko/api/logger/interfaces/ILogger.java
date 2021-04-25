/*
 * Conçu avec ♡ par Levasseur Wesley pour Etsuko.
 * © Copyright 2021. Tous droits réservés.
 *
 * Création datant du 25/4/2021 à 16:41:28.
 */

package com.etsuko.api.logger.interfaces;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @version 1.0.0
 */
public interface ILogger extends Logger {

    String getName();

    private String getShortName() {
        return this.getName().substring(this.getName().lastIndexOf(".") + 1);
    }

    ILoggerFactory getLoggerFactory();

    default SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat("MMMM dd',' yyyy '-' hh:mm:ss aaa '('zZ')'");
    }

    default String getLineFormat() {
        return "[{DATE}] [{LEVELS}-{NAME}] {MESSAGE}";
    }

    default boolean isDebugging() {
        return true;
    }

    void activateDebugging();

    void deactivateDebugging();

    default boolean isTracing() {
        return true;
    }

    void activateTracing();

    void deactivateTracing();

    default boolean isInforming() {
        return true;
    }

    void activateInforming();

    void deactivateInforming();

    default boolean isWarning() {
        return true;
    }

    void activateWarning();

    void deactivateWarning();

    default boolean isErroring() {
        return true;
    }

    void activateErroring();

    void deactivateErroring();

    private boolean isLevelsActive(int level) {
        return Levels.TRACE.getLevel() == level ? this.isTracing() : (Levels.DEBUG.getLevel() == level ? this.isDebugging() : (Levels.INFO.getLevel() == level ? this.isInforming() : (Levels.WARN.getLevel() == level ? this.isWarning() : Levels.ERROR.getLevel() != level || this.isErroring())));
    }

    private String getFormattedLine(String date, String levels, String message) {
        return this.getLineFormat().replace("{DATE}", date).replace("{LEVELS}", levels).replace("{NAME}", this.getShortName()).replace("{MESSAGE}", message);
    }

    private String getFormattedDate() {
        return this.getDateFormat().format(new Date());
    }

    private void log(Levels levels, String message, Throwable throwable) {
        if (this.isLevelsActive(levels.getLevel())) {
            final String date = this.getFormattedDate();
            final String line = this.getFormattedLine(date, levels.toString(), message);
            String color = levels.getColors().getColor();
            System.out.println(color + line);
            this.getLoggerFactory().addLog(line);
            if (throwable != null) {
                StringBuilder throwMessage = new StringBuilder(this.getFormattedLine(date, levels.toString(), throwable.toString()));
                Arrays.stream(throwable.getStackTrace()).forEach(traceElement -> throwMessage.append("\n").append(this.getFormattedLine(date, levels.toString(), "\t- " + traceElement.toString())));
                System.out.println(color + throwMessage);
                this.getLoggerFactory().addLog(throwMessage.toString());
            }
            System.out.flush();
        }
    }

    private void formatAndLog(Levels levels, String format, Object a, Object a2) {
        if (this.isLevelsActive(levels.getLevel())) {
            FormattingTuple fT = MessageFormatter.format(format, a, a2);
            this.log(levels, fT.getMessage(), fT.getThrowable());
        }
    }

    private void formatAndLog(Levels levels, String format, Object... a) {
        if (this.isLevelsActive(levels.getLevel())) {
            FormattingTuple fT = MessageFormatter.arrayFormat(format, a);
            this.log(levels, fT.getMessage(), fT.getThrowable());
        }
    }

    @Override
    default boolean isTraceEnabled() {
        return this.isTracing();
    }

    @Override
    default void trace(String msg) {
        this.log(Levels.TRACE, msg, null);
    }

    @Override
    default void trace(String format, Object arg) {
        this.formatAndLog(Levels.TRACE, format, arg, null);
    }

    @Override
    default void trace(String format, Object arg1, Object arg2) {
        this.formatAndLog(Levels.TRACE, format, arg1, arg2);
    }

    @Override
    default void trace(String format, Object... arguments) {
        this.formatAndLog(Levels.TRACE, format, arguments);
    }

    @Override
    default void trace(String msg, Throwable t) {
        this.log(Levels.TRACE, msg, t);
    }

    @Override
    default boolean isTraceEnabled(Marker ignore) {
        return this.isTraceEnabled();
    }

    @Override
    default void trace(Marker ignore, String msg) {
        this.trace(msg);
    }

    @Override
    default void trace(Marker ignore, String format, Object arg) {
        this.trace(format, arg);
    }

    @Override
    default void trace(Marker ignore, String format, Object arg1, Object arg2) {
        this.trace(format, arg1, arg2);
    }

    @Override
    default void trace(Marker ignore, String format, Object... argArray) {
        this.trace(format, argArray);
    }

    @Override
    default void trace(Marker ignore, String msg, Throwable t) {
        this.trace(msg, t);
    }

    @Override
    default boolean isDebugEnabled() {
        return this.isDebugging();
    }

    @Override
    default void debug(String msg) {
        this.log(Levels.DEBUG, msg, null);
    }

    @Override
    default void debug(String format, Object arg) {
        this.formatAndLog(Levels.DEBUG, format, arg, null);
    }

    @Override
    default void debug(String format, Object arg1, Object arg2) {
        this.formatAndLog(Levels.DEBUG, format, arg1, arg2);
    }

    @Override
    default void debug(String format, Object... arguments) {
        this.formatAndLog(Levels.DEBUG, format, arguments);
    }

    @Override
    default void debug(String msg, Throwable t) {
        this.log(Levels.DEBUG, msg, t);
    }

    @Override
    default boolean isDebugEnabled(Marker ignore) {
        return this.isDebugEnabled();
    }

    @Override
    default void debug(Marker ignore, String msg) {
        this.debug(msg);
        this.isDebugEnabled(ignore);
    }

    @Override
    default void debug(Marker ignore, String format, Object arg) {
        this.debug(format, arg);
    }

    @Override
    default void debug(Marker ignore, String format, Object arg1, Object arg2) {
        this.debug(format, arg1, arg2);
    }

    @Override
    default void debug(Marker ignore, String format, Object... arguments) {
        this.debug(format, arguments);
    }

    @Override
    default void debug(Marker ignore, String msg, Throwable t) {
        this.debug(msg, t);
    }

    @Override
    default boolean isInfoEnabled() {
        return this.isInforming();
    }

    @Override
    default void info(String msg) {
        this.log(Levels.INFO, msg, null);
    }

    @Override
    default void info(String format, Object arg) {
        this.formatAndLog(Levels.INFO, format, arg, null);
    }

    @Override
    default void info(String format, Object arg1, Object arg2) {
        this.formatAndLog(Levels.INFO, format, arg1, arg2);
    }

    @Override
    default void info(String format, Object... arguments) {
        this.formatAndLog(Levels.INFO, format, arguments);
    }

    @Override
    default void info(String msg, Throwable t) {
        this.log(Levels.INFO, msg, t);
    }

    @Override
    default boolean isInfoEnabled(Marker ignore) {
        return this.isInfoEnabled();
    }

    @Override
    default void info(Marker ignore, String msg) {
        this.info(msg);
    }

    @Override
    default void info(Marker ignore, String format, Object arg) {
        this.info(format, arg);
    }

    @Override
    default void info(Marker ignore, String format, Object arg1, Object arg2) {
        this.info(format, arg1, arg2);
    }

    @Override
    default void info(Marker ignore, String format, Object... arguments) {
        this.info(format, arguments);
    }

    @Override
    default void info(Marker ignore, String msg, Throwable t) {
        this.info(msg, t);
    }

    @Override
    default boolean isWarnEnabled() {
        return this.isWarning();
    }

    @Override
    default void warn(String msg) {
        this.log(Levels.WARN, msg, null);
    }

    @Override
    default void warn(String format, Object arg) {
        this.formatAndLog(Levels.WARN, format, arg, null);
    }

    @Override
    default void warn(String format, Object... arguments) {
        this.formatAndLog(Levels.WARN, format, arguments);
    }

    @Override
    default void warn(String format, Object arg1, Object arg2) {
        this.formatAndLog(Levels.WARN, format, arg1, arg2);
    }

    @Override
    default void warn(String msg, Throwable t) {
        this.log(Levels.WARN, msg, t);
    }

    @Override
    default boolean isWarnEnabled(Marker ignore) {
        return this.isWarnEnabled();
    }

    @Override
    default void warn(Marker ignore, String msg) {
        this.warn(msg);
    }

    @Override
    default void warn(Marker ignore, String format, Object arg) {
        this.warn(format, arg);
    }

    @Override
    default void warn(Marker ignore, String format, Object arg1, Object arg2) {
        this.warn(format, arg1, arg2);
    }

    @Override
    default void warn(Marker ignore, String format, Object... arguments) {
        this.warn(format, arguments);
    }

    @Override
    default void warn(Marker ignore, String msg, Throwable t) {
        this.warn(msg, t);
    }

    @Override
    default boolean isErrorEnabled() {
        return this.isErroring();
    }

    @Override
    default void error(String msg) {
        this.log(Levels.ERROR, msg, null);
    }

    @Override
    default void error(String format, Object arg) {
        this.formatAndLog(Levels.ERROR, format, arg, null);
    }

    @Override
    default void error(String format, Object arg1, Object arg2) {
        this.formatAndLog(Levels.ERROR, format, arg1, arg2);
    }

    @Override
    default void error(String format, Object... arguments) {
        this.formatAndLog(Levels.ERROR, format, arguments);
    }

    @Override
    default void error(String msg, Throwable t) {
        this.log(Levels.ERROR, msg, t);
    }

    @Override
    default boolean isErrorEnabled(Marker ignore) {
        return this.isErrorEnabled();
    }

    @Override
    default void error(Marker ignore, String msg) {
        this.error(msg);
    }

    @Override
    default void error(Marker ignore, String format, Object arg) {
        this.error(format, arg);
    }

    @Override
    default void error(Marker ignore, String format, Object arg1, Object arg2) {
        this.error(format, arg1, arg2);
    }

    @Override
    default void error(Marker ignore, String format, Object... arguments) {
        this.error(format, arguments);
    }

    @Override
    default void error(Marker ignore, String msg, Throwable t) {
        this.error(msg, t);
    }

    enum Levels {
        TRACE(0, Colors.CYAN),
        DEBUG(10, Colors.PURPLE),
        INFO(20, Colors.BLUE),
        WARN(30, Colors.YELLOW),
        ERROR(40, Colors.RED),
        CONSOLE(50, Colors.GREEN);

        private final int level;
        private final Colors colors;

        Levels(int level, Colors colors) {
            this.level = level;
            this.colors = colors;
        }

        public static Map<Integer, Levels> getLevels() {
            return Arrays.stream(values()).collect(Collectors.toMap(value -> value.level, value -> value, (a, b) -> b));
        }

        public static Levels getLevelsFromLevel(int level) {
            return Levels.getLevels().get(level);
        }

        public int getLevel() {
            return this.level;
        }

        public Colors getColors() {
            return this.colors;
        }
    }

    enum Colors {
        RESET("\u001b[0m"),
        BLACK("\u001b[30m"),
        RED("\u001b[31m"),
        GREEN("\u001b[32m"),
        YELLOW("\u001b[33m"),
        BLUE("\u001b[34m"),
        PURPLE("\u001b[35m"),
        CYAN("\u001b[36m"),
        WHITE("\u001b[37m");

        private final String color;

        Colors(String color) {
            this.color = color;
        }

        public String getColor() {
            return this.color;
        }
    }

}
