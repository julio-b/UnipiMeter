package unipi.sem7.unipimeter;
import android.arch.persistence.room.TypeConverter;
import android.location.Location;

import java.util.Locale;

// typeconverter from https://commonsware.com/AndroidArch/previews/room-and-custom-types
public class LocationConverter {
    @TypeConverter
    public static String fromLocation(Location location) {
        if (location == null) {
            return(null);
        }

        return(String.format(Locale.US, "%f,%f", location.getLatitude(),
                location.getLongitude()));
    }

    @TypeConverter
    public static Location toLocation(String latlon) {
        if (latlon == null) {
            return(null);
        }

        String[] pieces = latlon.split(",");
        Location result = new Location("");

        result.setLatitude(Double.parseDouble(pieces[0]));
        result.setLongitude(Double.parseDouble(pieces[1]));

        return(result);
    }
}