package com.ghzmdr.panicbutton;

import java.io.Serializable;

/**
 * Created by ghzmdr on 18/01/15.
 */
public class Person implements Serializable{
    private String number;
    private String name;

    public Person(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public boolean equals(Person p) {
        return p.getName().equals(name) && p.getNumber().equals(number);
    }
}
