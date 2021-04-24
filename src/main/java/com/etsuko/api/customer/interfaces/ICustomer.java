/*
 * Conçu avec ❤  par Levasseur Wesley pour Etsuko.
 * © Copyright 2021. Tous droits réservés.
 *
 * Création datant du 25/4/2021 à 0:7:44.
 */

package com.etsuko.api.customer.interfaces;

import java.util.Date;
import java.util.UUID;

public interface ICustomer {

    UUID getUniversalUniqueIdentifier();

    String getUsername();
    void setUsername(String username);

    String getAddressMail();
    void setAddressMail(String addressMail);

    String getLastIpAddress();
    void setLastIpAddress(String lastIpAddress);

    Date getLastLogged();
    void setLastLogged(Date lastLogged);

    Date getCreationDate();
}
