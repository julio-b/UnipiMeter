package unipi.sem7.unipimeter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.github.anastr.speedviewlib.ProgressiveGauge;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import unipi.sem7.unipimeter.dummy.DummyContent.DummyItem;

public class MainActivity extends AppCompatActivity implements LocationListener , POIFragment.OnListFragmentInteractionListener {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    public SpeedometerFragment speedometerFragment;
    private MapView mMapView;
    private LocationManager locationManager;
    private Marker locationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.vpcontainer);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        mMapView = (MapView) findViewById(R.id.osmdroid);
        mMapView.setMultiTouchControls(true);
        mMapView.setMinZoomLevel(1.4);
        mMapView.setMaxZoomLevel(18.9);
        mMapView.setTilesScaledToDpi(true);
        mMapView.getController().setZoom(18.0);
        mMapView.getController().setCenter(new GeoPoint(37.941649, 23.652894));

        locationMarker = new Marker(mMapView);
        locationMarker.setPosition(new GeoPoint(37.941649, 23.652894));
        locationMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
        locationMarker.setIcon(getResources().getDrawable(R.drawable.ic_menu_mylocation));
        locationMarker.setTitle(locationMarker.getPosition().toString().replace(",0.0", ""));

        mMapView.getOverlays().add(locationMarker);
        //mMapView.invalidate();


        final ITileSource tileSource = new XYTileSource( "Dark", 1, 18, 256, ".png",
                new String[] {
                        "https://a.basemaps.cartocdn.com/dark_all/",
                        "https://b.basemaps.cartocdn.com/dark_all/",
                        "https://c.basemaps.cartocdn.com/dark_all/",
                        "https://d.basemaps.cartocdn.com/dark_all/" });
        mMapView.setTileSource(tileSource);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        reqGPS();

        speedometerFragment = new SpeedometerFragment(); // temp

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(DummyItem item){
        //TODO POI LIST ITEM CLICK
    }

    public void reqGPS() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    speedometerFragment.setVisibility(View.VISIBLE);
                    reqGPS();
                } else {
                    speedometerFragment.setVisibility(View.INVISIBLE);
                }
                return;
            }
            //TODO req other permissions
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        GeoPoint lgp = new GeoPoint(location.getLatitude(), location.getLongitude());
        mMapView.getController().setCenter(lgp);
        locationMarker.setPosition(lgp);
        locationMarker.setTitle(lgp.toString().replace(",0.0", ""));
        if (location.hasSpeed())
            speedometerFragment.setSpeed(location.getSpeed());

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return SpeedometerFragment.newInstance();
                case 1:
                    return POIFragment.newInstance(1);
                default:
                    return SpeedometerFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
