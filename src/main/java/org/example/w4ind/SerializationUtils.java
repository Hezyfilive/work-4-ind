package org.example.w4ind;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SerializationUtils {
    private static final XStream xStream = new XStream(new DomDriver());

    static {
        XStream.setupDefaultSecurity(xStream);
        xStream.alias("MetroStation", MetroStation.class);
        xStream.alias("Hour", Hour.class);
        xStream.addPermission(AnyTypePermission.ANY);

    }

    public static void serializeToXML(Object obj, File file) {
        try {
            String xml = xStream.toXML(obj);
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(xml);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static <T> T deserializeFromXML(File file) {
        try (FileReader reader = new FileReader(file)) {
            return (T) xStream.fromXML(reader);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
