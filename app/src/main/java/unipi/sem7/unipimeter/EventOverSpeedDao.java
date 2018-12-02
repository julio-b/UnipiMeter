package unipi.sem7.unipimeter;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface EventOverSpeedDao {
    @Query("SELECT * from EventOverSpeed ORDER BY id DESC")
    public LiveData<List<EventOverSpeed>> getAllOverSpeed();

    @Query("SELECT * from EventOverSpeed WHERE ID = (SELECT MAX(ID)  FROM EventOverSpeed);")
    public EventOverSpeed getLast();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public long insertOverSpeed(EventOverSpeed eos);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertOverSpeed(EventOverSpeed... eos);

    @Delete
    public void deleteOverSpeed(EventOverSpeed eos);

    @Query("DELETE FROM EventOverSpeed")
    public void deleteAll();

}
