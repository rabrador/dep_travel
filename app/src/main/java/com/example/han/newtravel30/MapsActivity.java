package com.example.han.newtravel30;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import static android.location.LocationManager.GPS_PROVIDER;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static int GET_GPS = 1;
    private LocationManager locationManager;
    private Location location = null;
    private LatLng latLng;

    private Marker mArt;
    private Marker mSugar;
    private Marker mTemple;
    private Marker ma1;
    private Marker ma2;
    private Marker ma3;
    private Marker ma4;
    private Marker ma5;
    private Marker ma6;
    private Marker ma7;
    private Marker ma8;
    private Marker ma9;
    private Marker ma10;
    private Marker ma11;
    private Marker ma12;
    private Marker ma13;
    private Marker ma14;
    private Marker ma15;
    private Marker ma16;
    private Marker ma17;
    private Marker ma18;
    private Marker ma19;
    private Marker ma20;
    private Marker ma21;
    private Marker ma22;
    private Marker ma23;
    private Marker ma24;
    private Marker ma25;
    private Marker ma26;
    private Marker ma27;
    private Marker ma28;
    private Marker ma29;
    private Marker ma30;
    private Marker ma31;
    private Marker ma32;
    private Marker ma33;
    private Marker ma34;
    private Marker ma35;
    private Marker ma36;
    private Marker ma37;
    private Marker ma38;
    private Marker ma39;
    private Marker ma40;
    private Marker ma41;
    private Marker ma42;
    private Marker ma43;
    private Marker ma44;
    private Marker ma45;
    private Marker ma46;
    private Marker ma47;
    private Marker ma48;
    private Marker ma49;
    private Marker ma50;
    private Marker ma51;
    private Marker ma52;
    private Marker ma53;
    private Marker ma54;
    private Marker ma55;
    private Marker ma56;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        setGPS();
        marker();
    }

    private Location getLastLocation() {
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        if (bestLocation == null) {
            return null;
        }
        return bestLocation;
    }

    private void userlocation() {
        Location locat = getLastLocation();
        if (locat != null) {
            location = locat;
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14), 1500, null);
        }
    }

    private void locationEnable() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, GET_GPS);
            return;
        } else {
            if (!mMap.isMyLocationEnabled())
                mMap.setMyLocationEnabled(true);
        }
    }

    private void setGPS() {
        if (locationManager.isProviderEnabled(GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationEnable();
            userlocation();
        }
        if (!locationManager.isProviderEnabled(GPS_PROVIDER) || !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Toast.makeText(this, "請開啟定位服務", Toast.LENGTH_LONG).show();
            locationEnable();
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));    //開啟設定頁面
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setGPS();
                } else {
                    Toast.makeText(this, "請允許位置服務以取得最佳功能", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private void marker() {
        // Add some markers to the map, and add a data object to each marker.
        mArt = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.673728, 120.507547))
                .title("鄉土藝術館"));
        mArt.setTag(0);

        mSugar = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.661817, 120.492999))
                .title("屏東糖廠"));
        mSugar.setTag(0);

        mTemple = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.667108, 120.492239))
                .title("玉皇宮"));
        mTemple.setTag(0);

        ma1 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.67936, 120.49921))
                .title("排灣族雕刻館"));
        ma1.setTag(0);

        ma2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.674, 120.492))
                .title("屏東公園"));
        ma2.setTag(0);

        ma3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.673728, 120.507547))
                .title("慈鳳宮"));
        ma3.setTag(0);

        ma4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.6737, 120.507))
                .title("屏東鄉土藝術館"));
        ma4.setTag(0);

        ma5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.65629, 120.44492))
                .title("高屏舊鐵橋與河濱公園"));
        ma5.setTag(0);

        ma6 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.6786, 120.493))
                .title("屏東書院"));
        ma6.setTag(0);

        ma7 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.68746, 120.48098))
                .title("崇蘭蕭家古厝"));
        ma7.setTag(0);

        ma8 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.67465, 120.48984))
                .title("屏東美術館"));
        ma8.setTag(0);

        ma9 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.6616, 120.493))
                .title("屏東糖廠"));
        ma9.setTag(0);

        ma10 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.6784, 120.485))
                .title("將軍之屋"));
        ma10.setTag(0);

        ma11 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.6792, 120.498))
                .title("千禧公園"));
        ma11.setTag(0);

        ma12 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.76514, 120.45122))
                .title("斜張橋"));
        ma12.setTag(0);

        ma13 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.7101, 120.50616))
                .title("海豐濕地"));
        ma13.setTag(0);

        ma14 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.677, 120.4778))
                .title("藝術館"));
        ma14.setTag(0);

        ma15 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.66522, 120.48355))
                .title("萬年溪"));
        ma15.setTag(0);

        ma16 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.676, 120.49132))
                .title("朝陽門"));
        ma16.setTag(0);

        ma17 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.67941, 120.4878))
                .title("屏東縣青創聚落"));
        ma17.setTag(0);

        ma18 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.67479, 120.48479))
                .title("族群音樂館"));
        ma18.setTag(0);

        ma19 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.67449, 120.49032))
                .title("屏東旅遊服務中心"));
        ma19.setTag(0);

        ma20 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.67847, 120.48483))
                .title("眷舍咖啡街"));
        ma20.setTag(0);

        ma21 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.77059, 120.70211))
                .title("德文部落"));
        ma21.setTag(0);

        ma22 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.82476, 120.66025))
                .title("青山村"));
        ma22.setTag(0);

        ma23 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.8265, 120.6568))
                .title("馬兒青山觀光自行車道"));
        ma23.setTag(0);

        ma24 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.8554, 120.6446))
                .title("青葉部落"));
        ma24.setTag(0);

        ma25 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.7093, 120.647))
                .title("屏北部落自行車道"));
        ma25.setTag(0);

        ma26 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.7149, 120.653))
                .title("三地門藝術村"));
        ma26.setTag(0);

        ma27 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.7159, 120.655))
                .title("三地門文化館"));
        ma27.setTag(0);

        ma28 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.8606, 120.642))
                .title("大津瀑布"));
        ma28.setTag(0);

        ma29 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.7692, 120.646))
                .title("莎卡蘭口社村"));
        ma29.setTag(0);

        ma30 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.7949, 120.634))
                .title("安坡部落"));
        ma30.setTag(0);

        ma31 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.7527, 120.647))
                .title("賽嘉航空園區"));
        ma31.setTag(0);

        ma32 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.7523, 120.635))
                .title("高樹-三地門自行車道"));
        ma32.setTag(0);

        ma33 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.7127, 120.653))
                .title("蜻蜓雅築"));
        ma33.setTag(0);

        ma34 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.7445, 120.731))
                .title("霧臺基督長老教會"));
        ma34.setTag(0);

        ma35 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.7464, 120.706))
                .title("伊拉部落"));
        ma35.setTag(0);

        ma36 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.7507, 120.728))
                .title("霧台石板聚落"));
        ma36.setTag(0);

        ma37 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.74357, 120.73078))
                .title("魯凱文物館"));
        ma37.setTag(0);

        ma38 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.74793, 120.70679))
                .title("霧台谷川大橋"));
        ma38.setTag(0);

        ma39 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.749, 120.7282))
                .title("霧台阿禮部落的頭目家屋"));
        ma39.setTag(0);

        ma40 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.74895, 120.72842))
                .title("霧台櫻花"));
        ma40.setTag(0);

        ma41 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.6694, 120.6882))
                .title("禮納里部落"));
        ma41.setTag(0);


        ma42 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.69629, 120.66025))
                .title("山川琉璃吊橋"));
        ma42.setTag(0);


        ma43 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.6816, 120.6856))
                .title("瑪家桃花源"));
        ma43.setTag(0);


        ma44 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.6671, 120.713))
                .title("舊筏灣部落"));
        ma44.setTag(0);


        ma45 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.6863, 120.638))
                .title("涼山瀑布"));
        ma45.setTag(0);


        ma46 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.6517, 120.63))
                .title("笠頂山登山步道"));
        ma46.setTag(0);


        ma47 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.707, 120.652))
                .title("原住民族文化發展中心"));
        ma47.setTag(0);


        ma48 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.7307, 120.485))
                .title("九如三山國王廟"));
        ma48.setTag(0);


        ma49 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.7223, 120.491))
                .title("金桔休閒園區"));
        ma49.setTag(0);


        ma50 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.7596, 120.496))
                .title("龔家古厝"));
        ma50.setTag(0);


        ma51 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.7479, 120.498))
                .title("蘭花蕨鐵馬道"));
        ma51.setTag(0);


        ma52 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.70934, 120.48826))
                .title("大花農場"));
        ma52.setTag(0);


        ma53 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.72792, 120.48899))
                .title("巴轆公園"));
        ma53.setTag(0);


        ma54 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.7733, 120.53116))
                .title("大茉莉農莊"));
        ma54.setTag(0);


        ma55 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.82923, 120.5326))
                .title("金三角信國定遠社區"));
        ma55.setTag(0);


        ma56 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.7771, 120.494))
                .title("里港老街"));
        ma56.setTag(0);

    }
}
