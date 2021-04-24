/*
 * Conçu avec ❤  par Levasseur Wesley pour Etsuko.
 * © Copyright 2021. Tous droits réservés.
 *
 * Création datant du 25/4/2021 à 0:0:54.
 */

package com.etsuko.api.customer;

import com.etsuko.api.customer.interfaces.ICustomer;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.IndexOptions;
import dev.morphia.annotations.Indexed;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.UUID;

@Entity(value = "Customers", useDiscriminator = false)
public class Customer implements ICustomer {

    private final Date creationDate = new Date();
    @Id
    private ObjectId id;
    @Indexed(options = @IndexOptions(unique = true))
    private UUID universalUniqueIdentifier;
    private String username, addressMail, lastIpAddress;
    private Date lastLogged = new Date();

    Customer() {
    }

    @Override
    public UUID getUniversalUniqueIdentifier() {
        return this.universalUniqueIdentifier;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getAddressMail() {
        return this.addressMail;
    }

    @Override
    public void setAddressMail(String addressMail) {
        this.addressMail = addressMail;
    }

    @Override
    public String getLastIpAddress() {
        return this.lastIpAddress;
    }

    @Override
    public void setLastIpAddress(String lastIpAddress) {
        this.lastIpAddress = lastIpAddress;
    }

    @Override
    public Date getLastLogged() {
        return this.lastLogged;
    }

    @Override
    public void setLastLogged(Date lastLogged) {
        this.lastLogged = lastLogged;
    }

    @Override
    public Date getCreationDate() {
        return this.creationDate;
    }
}
