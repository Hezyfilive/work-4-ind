package org.example.w4ind;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import javafx.beans.property.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XStreamAlias("MetroStation")
public class MetroStation extends BaseEntity {
    private List<Hour> hours;

    public MetroStation(String name, int year){
        super(name, year);
        this.hours = new ArrayList<>();
    }

    public MetroStation(String name, int year, List<Hour> hours) {
        super(name, year);
        this.hours = hours;
    }


    public StringProperty nameProperty() {
        return new SimpleStringProperty(name);
    }

    public IntegerProperty yearProperty() {
        return new SimpleIntegerProperty(year);
    }

    public List<Hour> getHours() {
        return hours;
    }

    public void setHours(List<Hour> hours) {
        this.hours = hours;
    }

    public void setYear(int year) {
        this.year = year;
    }


    @Override
    public List<Hour> getSequence() {
        return hours;
    }

    @Override
    public void addHour(Hour hour) {
        if(!hours.contains(hour)){
            hours.add(hour);
        }
    }

    @Override
    public void removeHour(Hour hour) {
        hours.remove(hour);
    }

    @Override
    public String toString() {
        return "MetroStation{" +
                "name='" + getName() + '\'' +
                ", year=" + getYear() +
                ", hours=" + hours +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetroStation that = (MetroStation) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(year, that.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, year);
    }
}
