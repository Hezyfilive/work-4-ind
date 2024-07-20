package org.example.w4ind;

import java.util.Objects;
import javafx.beans.property.*;

public class Hour implements Comparable<Hour> { ;
    private Integer passengerCount;
    private String comment;

    public Hour(int passengerCount, String comment) {
        this.passengerCount = passengerCount;
        this.comment = comment;
    }

    public IntegerProperty passengerCountProperty() { return new SimpleIntegerProperty(passengerCount); }
    public StringProperty commentProperty() { return new SimpleStringProperty(comment); }

    public int getPassengerCount() { return passengerCount; }
    public String getComment() { return comment; }

    public void setPassengerCount(int passengerCount) { this.passengerCount = passengerCount; }
    public void setComment(String comment) { this.comment = comment; }

    @Override
    public String toString() {
        return "Hour{" +
                ", passengerCount=" + passengerCount +
                ", comment=" + comment +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hour hour = (Hour) o;
        return Objects.equals(passengerCount, hour.passengerCount) &&
                Objects.equals(comment, hour.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passengerCount, comment);
    }

    @Override
    public int compareTo(Hour o) {
        return Integer.compare(this.passengerCount, o.passengerCount);
    }
}
