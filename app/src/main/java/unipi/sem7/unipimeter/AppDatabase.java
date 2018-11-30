package unipi.sem7.unipimeter;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.concurrent.Executors;

@Database(entities = {POI.class, EventApproachingPOI.class, EventOverSpeed.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract POIDao poiDao();
    public abstract EventOverSpeedDao overspeedDao();

    private static volatile AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .allowMainThreadQueries()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final POIDao poiDao;
        private final EventOverSpeedDao overSpeedDao;

        PopulateDbAsync(AppDatabase db) {
            poiDao = db.poiDao();
            overSpeedDao = db.overspeedDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            POI p = new POI("Unipi", 37.941649, 23.652894, "University", "Papei"); poiDao.insertPOI(p);
            p = new POI("ASOEE", 37.994038, 23.732553, "University", "<Papei"); poiDao.insertPOI(p);
            p = new POI("EKPA", 37.967739, 23.782904, "University", "<Papei"); poiDao.insertPOI(p);

            p = new POI("Test poi", 37.933398, 23.722775, "Test", "random map point"); poiDao.insertPOI(p);
            p = new POI("Test poi 2", 38.026490, 23.662007, "Test", "random map point"); poiDao.insertPOI(p);
            p = new POI("Test poi 3", 38.117362, 23.900809, "Test", "random map point"); poiDao.insertPOI(p);
            p = new POI("Test poi 4", 37.846763, 23.556971, "Test", "random map point"); poiDao.insertPOI(p);

            p = new POI("Acropoli", 37.971515, 23.725824, "Museum", "Description here"); poiDao.insertPOI(p);
            p = new POI("Pasalimani", 37.937287, 23.648512, "Port", "Description here"); poiDao.insertPOI(p);

            EventOverSpeed eos = new EventOverSpeed(30.2f, 23.3f, 38.006490, 23.632007); overSpeedDao.insertOverSpeed(eos);
            eos = new EventOverSpeed(70.2f, 60.3f, 38.006490, 23.632007); overSpeedDao.insertOverSpeed(eos);
            return null;
        }
    }
}
