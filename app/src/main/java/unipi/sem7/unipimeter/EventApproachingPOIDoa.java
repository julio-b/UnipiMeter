package unipi.sem7.unipimeter;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface EventApproachingPOIDoa {
    @Query("SELECT * from EventApproachingPOI ORDER BY eap_id ASC")
    public LiveData<List<EventApproachingPOI>> getAllApproachingPOIs();

    @Query("SELECT * from EventApproachingPOI WHERE eap_id = (SELECT MAX(eap_id)  FROM EventApproachingPOI);")
    public EventApproachingPOI getLast();

    @Query("SELECT EAP.*, POI.* from EventApproachingPOI AS EAP INNER JOIN points_of_interes AS POI ON EAP.poiId=POI.id ORDER BY EAP.eap_id ASC")
    public LiveData<List<EventApproachingPOIjoined>> getAllApproachingPOIJoined();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public long insertApproachingPOI(EventApproachingPOI eap);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertApproachingPOI(EventApproachingPOI... eap);

    @Delete
    public void deleteApproachingPOI(EventApproachingPOI eap);

    @Query("DELETE FROM EventApproachingPOI")
    public void deleteAll();


    public class EventApproachingPOIjoined {
        @Embedded
        EventApproachingPOI eap;

        @Embedded
        POI poi;
    }

}
