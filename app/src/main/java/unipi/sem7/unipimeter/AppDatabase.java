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
    //todo

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

        PopulateDbAsync(AppDatabase db) {
            poiDao = db.poiDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            POI p = new POI();
            p.title = "Test POI";
            p.category = "Cat 1";
            p.description = "description qeqeweqwq";
            p.location = new Location("db");
            p.location.setLatitude(0);
            p.location.setLongitude(0);
            poiDao.insertPOIs(p, p);
            p.category = "Caet 2";
            p.location.setLongitude(12.22);
            p.location.setLatitude(33.666);
            poiDao.insertPOIs(p, p);
            return null;
        }
    }
}
