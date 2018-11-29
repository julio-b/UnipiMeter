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
}
