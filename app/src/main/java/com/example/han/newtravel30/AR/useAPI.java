package com.example.han.newtravel30.AR;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

public class useAPI {
    public useAPI() {
    }

    public static String covRawToString(InputStream inputStream) {
        String data = null;

        try {
            int size = inputStream.available();

            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            data = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static ArrayList<Touris> parserJsonFromTouris(String sJson) {
        ArrayList<Touris> list = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(sJson);
            JSONArray array = obj.getJSONArray("data");

            for (int i = 0; i < array.length(); i++) {
                JSONObject subObj = array.getJSONObject(i);

                try {
                    JSONArray imgArray = subObj.getJSONArray("Images");
                    JSONObject imgObj = imgArray.getJSONObject(0);

                    Touris touris = new Touris(subObj.getString("Name"), subObj.getString("Title"), subObj.getString("Introduction"),
                            subObj.getString("FullAddress"), subObj.getString("Elong"), subObj.getString("Nlat"), imgObj.getString("Original"), 0);
                    list.add(touris);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static boolean isTouchInContain(int width, float touchX, float touchY, float targetX, float targetY) {
        return touchX > width && touchX < targetX + width &&
                touchY > width && touchY < targetY + width;
    }

    public static Location getMyLocation(Context packageContext) {
        LocationManager locationMgr;

        Location bestLocation = null;
        locationMgr = (LocationManager) packageContext.getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(packageContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)packageContext, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, ARActivity.REQUEST_LOCATION);
        }

        List<String> providers = locationMgr.getProviders(true);
        for (String provider : providers) {
            Location locat = locationMgr.getLastKnownLocation(provider);
            if (locat == null) {
                continue;
            }
            if (bestLocation == null || locat.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = locat;
            }
        }

        return bestLocation;
    }

    public static Bitmap getImageFromURL(String src, Resources res, int defaultImageID) {
        try {
            URL url = new URL(src);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();

            InputStream input = conn.getInputStream();
            return BitmapFactory.decodeStream(input);

        } catch (IOException e) {
            e.printStackTrace();
            /* Default Image */
            return BitmapFactory.decodeResource(res, defaultImageID);
        }
    }
}
