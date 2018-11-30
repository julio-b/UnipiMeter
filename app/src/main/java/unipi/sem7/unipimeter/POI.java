package unipi.sem7.unipimeter;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.location.Location;

@Entity(tableName = "points_of_interes")
@TypeConverters(LocationConverter.class)
public class POI {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;

    public String description;

    public String category;

    public Location location;

    public POI(String title, String description, String  category, Location location) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.location = location;
    }

    public POI(String title, double lat, double lon, String category, String description) {
        this(title, description, category, new Location("db"));
        this.location.setLatitude(lat);
        this.location.setLongitude(lon);
    }
}
