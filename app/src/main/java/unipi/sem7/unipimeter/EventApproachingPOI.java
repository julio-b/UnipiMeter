package unipi.sem7.unipimeter;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.location.Location;

import java.util.Date;


@Entity(foreignKeys = @ForeignKey(entity = POI.class,
                parentColumns = "id",
                childColumns = "poiId"))
@TypeConverters({DateConverter.class, LocationConverter.class})
public class EventApproachingPOI {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int poiId;

    public Location location;

    public Date date;

    public EventApproachingPOI(int poiId, Location location, Date date) {
        this.poiId = poiId;
        this.location = location;
        this.date = date;
    }

    public EventApproachingPOI(int poiId, double lat, double lon) {
        this(poiId, new Location("db"), new Date());
        this.location.setLatitude(lat);
        this.location.setLongitude(lon);
    }

    @Ignore
    public EventApproachingPOI(int poiId, Location location) {
        this(poiId, location, new Date());
    }
}


