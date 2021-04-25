/*
 * Conçu avec ❤  par Levasseur Wesley pour Etsuko.
 * © Copyright 2021. Tous droits réservés.
 *
 * Création datant du 24/4/2021 à 23:45:14.
 */

package com.etsuko.api.core.interfaces;

/**
 * IEtsukoAPI est une interface et permet la gestion du "core" de EtsukoAPI.
 * Aussi cette dernière est la classe maître de cette API.
 *
 * @author Levasseur Wesley
 */
public interface IEtsukoAPI {

    /**
     * La fonction "load" permet le chargement et la bonne ergonomie de cette API.
     *
     * @param callback Permets de retourné après que la fonction soit appelée, si cette dernière est un sucées ou si elle rencontre des erreurs.
     * @throws InternalError Lors du chargement de cette API, elle peut engendrer des erreurs interne.
     */
    default void load(Callback callback) throws InternalError {
        throw new InternalError("Etsuko load can't process, because there isn't @Override or there is cast of super.");
    }

    /**
     * Le fonction de rappel (Callback en anglais) ou fonction de post-traitement est une fonction qui est passée
     * en argument à une autre fonction. Cette dernière peut alors faire usage de cette fonction de rappel comme
     * de n'importe quelle autre fonction, alors qu'elle ne la connaît pas par avance.
     */
    interface Callback {
        /**
         * La fonction "done" est utilisée en tant que fonction de rappel, elle servira de retourné
         * après que la fonction soit appelée, si cette dernière est un sucées ou si elle rencontre des erreurs.
         *
         * @param success Permets de retourner une valeur booléenne.
         * @param errors  Permets de retourner une liste de plusieurs erreurs ou non.
         */
        void done(boolean success, Throwable... errors);
    }

}
