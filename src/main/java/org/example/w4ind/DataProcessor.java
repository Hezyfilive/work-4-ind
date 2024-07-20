package org.example.w4ind;

import java.util.List;
import java.util.stream.Collectors;

public class DataProcessor {
    public static List<MetroStation> searchStations(List<MetroStation> stations, String regex) {
        return stations.stream()
                .filter(station -> station.getSequence().stream()
                        .anyMatch(hour -> hour.getComment().matches(regex)))
                .collect(Collectors.toList());
    }
}
