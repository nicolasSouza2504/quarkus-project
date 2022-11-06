package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "login_user")
public class LoginUser extends PanacheEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(name = "change_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date changeDate;

    @PrePersist
    public void prePersist() {
        setProperties();
    }

    @PreUpdate
    public void preUpdate() {
        setProperties();
    }

    private void setProperties() {

        if (id != null) {
            setChangeDate(new Date());
        } else {
            setCreationDate(new Date());
        }

    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }


}
