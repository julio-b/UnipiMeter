package unipi.sem7.unipimeter;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.location.Location;

import java.util.Date;

@Entity
@TypeConverters({DateConverter.class, LocationConverter.class})
public class EventOverSpeed {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public float speed;

    public float speedLimit;

    public Location location;

    public Date date;

    public EventOverSpeed(float speed, float speedLimit, Location location, Date date) {
        this.speed = speed;
        this.speedLimit = speedLimit;
        this.location = location;
        this.date = date;
    }

    public EventOverSpeed(float speed, float speedLimit, double lat, double lon) {
        this(speed, speedLimit, new Location("db"), new Date());
        this.location.setLatitude(lat);
        this.location.setLongitude(lon);
    }

    @Ignore
    public EventOverSpeed(float speed, float speedLimit, Location location) {
        this(speed, speedLimit, location, new Date());
    }
}
