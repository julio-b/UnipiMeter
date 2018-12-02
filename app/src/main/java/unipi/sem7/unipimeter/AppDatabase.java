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
    public abstract EventApproachingPOIDoa approachingPOIDoa();

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
        private final EventApproachingPOIDoa approachingPOIDoa;

        PopulateDbAsync(AppDatabase db) {
            poiDao = db.poiDao();
            overSpeedDao = db.overspeedDao();
            approachingPOIDoa = db.approachingPOIDoa();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            POI p = new POI("Unipi", 37.941649, 23.652894, "University",
                    "The University of Piraeus (UniPi; Greek: Πανεπιστήμιο Πειραιώς, ΠαΠει) is a Greek public university located in Piraeus,"
                            +" Greece with a total of nine academic departments focused mainly on Statistics, Economics, Finance,"
                            +" Business Management and Information Technology. "); poiDao.insertPOI(p);
            p = new POI("ASOEE", 37.994038, 23.732553, "University",
                    "Athens University of Economics and Business (AUEB; Greek: Οικονομικό Πανεπιστήμιο Αθηνών, Oikonomiko Panepistimio Athinon, abbrev. ΟΠΑ, OPA)"
                            +" was founded in 1920 in Athens, Greece. Its buildings are housed on Patision Street. "); poiDao.insertPOI(p);
            p = new POI("EKPA", 37.967739, 23.782904, "University",
                    "The National and Kapodistrian University of Athens (NKUA), usually referred to simply as the University of Athens (UoA), "
                    +"is a public university in Zografou, Athens, Greece"); poiDao.insertPOI(p);

            p = new POI("Faliro", 37.9444948, 23.6645341, "Metro", "Metro station"); poiDao.insertPOI(p);
            p = new POI("Omonoia", 37.983751, 23.7280703, "Metro", "Metro station"); poiDao.insertPOI(p);
            p = new POI("Athens International Airport", 37.935338, 23.948187, "Airport",
                    "Athens International Airport Eleftherios Venizelos (IATA: ATH, ICAO: LGAV), commonly initialized as \"AIA\", "
                    +"began operation on 28 March 2001 and is the primary international airport that serves the city of Athens and the region of Attica."); poiDao.insertPOI(p);
            p = new POI("Mount Olympus", 40.085556, 22.358611, "mountain",
                    "Mount Olympus (Όλυμπος) is the highest mountain in Greece. It is located in the Olympus Range on the border between Thessaly "
                    +"and Macedonia, between the regional units of Pieria and Larissa, about 80 km (50 mi) southwest from Thessaloniki. Mount Olympus has 52 peaks,"
                    +" deep gorges, and exceptional biodiversity. Olympus was notable in Greek mythology as the home of the Greek gods, on Mytikas peak. Mount Olympus"
                    +" is also noted for its rich flora."); poiDao.insertPOI(p);

            p = new POI("Acropoli", 37.971515, 23.725824, "Museum",
                    "The Acropolis of Athens is an ancient citadel located on a rocky outcrop above the city of Athens and contains the remains of "
                            +"several ancient buildings of great architectural and historic significance, the most famous being the Parthenon."); poiDao.insertPOI(p);
            p = new POI("Pasalimani", 37.937287, 23.648512, "Port",
                    "The Bay of Zea, since Ottoman times and until recently known as Paşalimanı (Πασαλιμάνι), is a broad bay located at "
                            +"the eastern coast of the Piraeus peninsula in Greece. It hosted the swimming events at the 1896 Summer Olympics held in Athens."
                            +" A seaport and marina are in the bay. "); poiDao.insertPOI(p);


            p = new POI("Juventus Stadium", 45.109614, 7.641701, "Football stadium",
                    "Juventus Stadium, known for sponsorship reasons as the Allianz Stadium since July 2017, is an all-seater football stadium in the Vallette"
                            +" borough of Turin, Italy, and the home of Serie A club Juventus Football Club"); poiDao.insertPOI(p);
            p = new POI("Anfield", 53.430806,-2.9613702, "Football stadium",
                    "Iconic football ground and home of one of England's most successful sides, Liverpool FC, since 1892"); poiDao.insertPOI(p);
            p = new POI("OAKA", 38.035912, 23.787430, "Football stadium",
                    "The Olympic Stadium of Athens is a sports stadium in Athens, Greece."); poiDao.insertPOI(p);
            p = new POI("Apostolos Nikolaidis", 37.987285, 23.753845, "Football stadium",
                    "Apostolos Nikolaidis Stadium (commonly known as Leoforos Stadium) is a football stadium and multi-sport center in Athens, Greece."
                    +" It was inaugurated in 1922 and is the oldest football stadium in Greece currently active. It is the traditional athletic center of Panathinaikos A.C."
                    +" and has been the home ground of Panathinaikos FC for the most part of the club's existence."); poiDao.insertPOI(p);

            EventOverSpeed eos = new EventOverSpeed(30.2f, 23.3f, 38.006490, 23.632007); overSpeedDao.insertOverSpeed(eos);
            eos = new EventOverSpeed(93.4f, 89.3f, 37.983518, 23.748003); overSpeedDao.insertOverSpeed(eos);
            eos = new EventOverSpeed(170.2f, 160.64f, 37.994358, 23.720226); overSpeedDao.insertOverSpeed(eos);
            eos = new EventOverSpeed(140.2f, 134.3f, 37.986266, 23.709165); overSpeedDao.insertOverSpeed(eos);
            eos = new EventOverSpeed(80.2f, 78.2f, 37.983306, 23.705313); overSpeedDao.insertOverSpeed(eos);
            eos = new EventOverSpeed(70.2f, 60.3f, 37.959743, 23.720709); overSpeedDao.insertOverSpeed(eos);


            EventApproachingPOI eap = new EventApproachingPOI(1, 37.921649, 23.622894); approachingPOIDoa.insertApproachingPOI(eap);
            eap = new EventApproachingPOI(6, 37.881200, 23.873163); approachingPOIDoa.insertApproachingPOI(eap);
            eap = new EventApproachingPOI(3, 37.985580, 23.821418); approachingPOIDoa.insertApproachingPOI(eap);
            eap = new EventApproachingPOI(1, 37.961714, 23.690558); approachingPOIDoa.insertApproachingPOI(eap);
            eap = new EventApproachingPOI(8, 37.958525, 23.718839); approachingPOIDoa.insertApproachingPOI(eap);
            eap = new EventApproachingPOI(13, 37.992410, 23.761060); approachingPOIDoa.insertApproachingPOI(eap);
            eap = new EventApproachingPOI(13, 37.983971, 23.758989); approachingPOIDoa.insertApproachingPOI(eap);
            return null;
        }
    }
}
