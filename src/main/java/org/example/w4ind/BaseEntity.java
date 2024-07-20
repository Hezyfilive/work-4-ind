package org.example.w4ind;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class BaseEntity {
    protected String name;
    protected int year;

    private static final Locale UKRAINIAN = new Locale("uk", "UA");
    private static final Locale AMERICAN = new Locale("en", "US");

    public BaseEntity(String name, int year) {
        this.name = name;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public String getName(Locale locale) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("translations", locale);
            return bundle.getString(name);
        } catch (Exception e) {
            return name;
        }
    }

    public abstract List<Hour> getSequence();
    public abstract void addHour(Hour hour);
    public abstract void removeHour(Hour hour);
}
