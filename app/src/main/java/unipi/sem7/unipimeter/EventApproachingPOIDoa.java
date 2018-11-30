package unipi.sem7.unipimeter;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface EventApproachingPOIDoa {
    @Query("SELECT * from EventApproachingPOI ORDER BY id ASC")
    public LiveData<List<EventApproachingPOI>> getAllApproachingPOIs();

    @Query("SELECT * from EventApproachingPOI WHERE ID = (SELECT MAX(ID)  FROM EventApproachingPOI);")
    public EventApproachingPOI getLast();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public long insertApproachingPOI(EventApproachingPOI eap);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertApproachingPOI(EventApproachingPOI... eap);

    @Delete
    public void deleteApproachingPOI(EventApproachingPOI eap);

    @Query("DELETE FROM EventApproachingPOI")
    public void deleteAll();
}
