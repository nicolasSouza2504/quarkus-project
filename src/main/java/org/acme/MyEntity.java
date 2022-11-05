package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;


@Entity
@Table(name = "first_table_db")
public class MyEntity  extends PanacheEntity {

    @Column(name = "field")
    private String field;


    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
