package unipi.sem7.unipimeter;

import android.arch.persistence.room.Entity;
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
}
