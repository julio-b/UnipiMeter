package unipi.sem7.unipimeter;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface POIDao {

    @Query("SELECT * from points_of_interes ORDER BY title ASC")
    public LiveData<List<POI>> getAllPOIs();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertPOI(POI poi);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertPOIs(POI... pois);

    @Update
    public void updatePOI(POI poi);

    @Delete
    public void deletePOI(POI poi);

    @Query("DELETE FROM points_of_interes")
    public void deleteAll();

}
