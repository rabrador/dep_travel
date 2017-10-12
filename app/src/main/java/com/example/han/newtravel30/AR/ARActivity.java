package com.example.han.newtravel30.AR;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.han.newtravel30.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class ARActivity extends AppCompatActivity implements LocationListener {

    /*********************
     * DEFINE VARIABLE
     ********************/
    private final boolean DEBUG_MESSAGE = false;
    private final boolean TEST_TRACE_CODE = true;
    private final int REQUEST_CAMERA = 1;
    public static final int REQUEST_LOCATION = 2;
    private final int REQUEST_SCREEN_SHOT = 3;
    private final int MAXIMUM_NUM_DISPLAY_AR = 2;
    private final int MAXIMUM_DISTANCE = 20000; //meter
    private final int MINIMUM_DISTANCE_TO_SHOW = 5000;
    /***********************************************************/

    /******************** LOCAL VARIABLE ********************/
    private GoogleApiClient mGoogleApiClient;
    private boolean isLocatOK = false;
    private Size previewSize = null;
    private TextureView cameraText = null;
    private CameraDevice camDevice = null;
    private CaptureRequest.Builder capBuilder = null;
    private CameraCaptureSession cameraSeeion = null;
    private FrameLayout frameAR;
    private String accelData = "Accelerometer Data";
    private String compassData = "Compass Data";
    private String gyroData = "";
    private Rect areaRect;
    ArrayList<Touris> dbTouris = new ArrayList<>();
    private String data;
    float[] accelerometerValues = new float[3];
    float[] magneticFieldValues = new float[3];
    private int myOri;
    private Bitmap arNotFound;
    private Bitmap arLocatMark;
    private ImageButton btnScreen;
    private int screenHeight;
    private int screenWidth;
    private ArChar dbAR = new ArChar();
    private int dispCount = 0;
    public static Location myLocat;
    /***********************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Full screen */
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_ar);

        initView();

        /* Get Screen height and width */
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
        dbAR.setWindowHeight(screenHeight);
        dbAR.setWindowWidth(screenWidth);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.CAMERA}, REQUEST_LOCATION);
        } else {
            myLocat = useAPI.getMyLocation(ARActivity.this);
            isLocatOK = true;
        }

        arNotFound = BitmapFactory.decodeResource(getResources(), R.drawable.locat_not_found);
        arLocatMark = BitmapFactory.decodeResource(getResources(), R.drawable.ar_locat_mark);
        cameraText.setSurfaceTextureListener(mSurfaceTextureListener);

        OverlayView arContent = new OverlayView(getApplicationContext());
        frameAR.addView(arContent);

        // Load Raw file and covert to String
        data = useAPI.covRawToString(getResources().openRawResource(R.raw.attractions));

        // Parser json data
        dbTouris = useAPI.parserJsonFromTouris(data);

        // insert and init to ListView
        if (DEBUG_MESSAGE == true) {
            /*************************  For Debug Beg ************************* */
            dbAR.setData("東海運動公園", ((float)22.743967), ((float)121.142554), 0, 0, 0, false, 0);
            dbAR.setData("臺東舊鐵道路廊", ((float)22.752842), ((float)121.145902), 0, 0, 0, false, 0);
            dbAR.setData("卑南豬血湯台東店", ((float)22.758896), ((float)121.143733), 0, 0, 0, false, 0);
            dbAR.setData("台東糖廠冰店", ((float)22.768181), ((float)121.128052), 0, 0, 0, false, 0);
            dbAR.setData("卑南遺址", ((float)22.791554), ((float)121.119651), 0, 0, 0, false, 0);
            dbAR.setData("台東森林公園", ((float)22.763301), ((float)121.158261), 0, 0, 0, false, 0);
            dbAR.setData("星星部落景觀咖啡", ((float)22.811714), ((float)121.150892), 0, 0, 0, false, 0);
            dbAR.setData("知本站", ((float)22.712427), ((float)121.061074), 0, 0, 0, false, 0);
            dbAR.setData("三和海濱公園", ((float)22.669924), ((float)121.035710), 0, 0, 0, false, 0);
            dbAR.setData("後山傳奇美食館", ((float)22.668706), ((float)121.034508), 0, 0, 0, false, 0);
//        dbAR.setData("新竹關東橋郵局", ((float) 24.782646), ((float) 121.018707), 0, 0, 0, false, 0);
//        dbAR.setData("竹北火車站", ((float) 24.839656), ((float) 121.009613), 0, 0, 0, false, 0);
//        dbAR.setData("十八尖山停車場", ((float) 24.795013), ((float) 120.986764), 0, 0, 0, false, 9);
            /*************************  For Debug End ************************* */
        }
        else {
            for (int i = 0; i < dbTouris.size(); i++) {
                dbAR.setData(dbTouris.get(i).getName(), Float.parseFloat(dbTouris.get(i).getLatitude()), Float.parseFloat(dbTouris.get(i).getLongitude()), 0, 0, 0, false, 0);
                Log.d("dbAR.getLatitude", String.valueOf(dbAR.getLatitude(i)));
            }
        }

        btnScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //screenShot(getWindow().getDecorView().getRootView());
                captureScreen(REQUEST_SCREEN_SHOT);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraText.setSurfaceTextureListener(mSurfaceTextureListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case REQUEST_LOCATION:
                    myLocat = useAPI.getMyLocation(ARActivity.this);
                    isLocatOK = true;
                    break;
                case REQUEST_SCREEN_SHOT:
                    captureScreen(REQUEST_SCREEN_SHOT);
                    break;
            }

        } else {
            //user do reject
        }
    }

    private void initView() {
        cameraText = (TextureView) findViewById(R.id.textureView);
        frameAR = (FrameLayout) findViewById(R.id.framelayout);
        btnScreen = (ImageButton) findViewById(R.id.imgBtn);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    /* invoke onSurfaceTextureAvailable when TextureView is activity */
    private TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }

    };

    private void openCamera() {
        CameraManager camMgr = (CameraManager) getSystemService(CAMERA_SERVICE);

        try {
            /* get Camera ID */
            String camId = camMgr.getCameraIdList()[0];
            CameraCharacteristics camChar = camMgr.getCameraCharacteristics(camId);

            StreamConfigurationMap map = camChar.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            previewSize = map.getOutputSizes(SurfaceTexture.class)[0];

            /* Activity Camera */
            if (ContextCompat.checkSelfPermission(ARActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                camMgr.openCamera(camId, mCameraStateCallback, null);
            }

        } catch (Exception e) {
            Toast.makeText(this, "Open camera Error", Toast.LENGTH_LONG).show();
            //Error
        }

    }

    private CameraDevice.StateCallback mCameraStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            camDevice = camera;
            startPreview();

            OverlayView arContent = new OverlayView(getApplicationContext());
            frameAR.addView(arContent);
        }

        @Override
        public void onDisconnected(CameraDevice camera) {

        }

        @Override
        public void onError(CameraDevice camera, int error) {

        }
    };

    private CameraCaptureSession.StateCallback mCameraCaptureSessionCallback = new CameraCaptureSession.StateCallback() {
        @Override
        public void onConfigured(CameraCaptureSession session) {
            closeAllCameraSession();

            cameraSeeion = session;

            capBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);

            HandlerThread backThread = new HandlerThread("CameraPreview");
            backThread.start();
            Handler backHandler = new Handler(backThread.getLooper());

            try {
                cameraSeeion.setRepeatingRequest(capBuilder.build(), null, backHandler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onConfigureFailed(CameraCaptureSession session) {

        }
    };

    private void startPreview() {
        SurfaceTexture surfaceText = cameraText.getSurfaceTexture();
        surfaceText.setDefaultBufferSize(previewSize.getWidth(), previewSize.getHeight());

        Surface surface = new Surface(surfaceText);

        try {
            capBuilder = camDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
        } catch (CameraAccessException e) {
            e.printStackTrace();
            //Error
        }

        capBuilder.addTarget(surface);

        try {
            camDevice.createCaptureSession(Arrays.asList(surface), mCameraCaptureSessionCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
            //Error
        }
    }

    private void closeAllCameraSession() {
        if (cameraSeeion != null) {
            cameraSeeion.close();
            cameraSeeion = null;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                for (int i = 0; i < dbAR.getSize(); i++) {
                    if (dbAR.getIsShown(i) == true) {
                        if (useAPI.isTouchInContain(arLocatMark.getWidth() - 300, x, y, dbAR.getXCoord(i), dbAR.getYCoord(i))) {
//                            Intent intent = new Intent();
//                            intent.setClass(ARActivity.this, ListContentActivity.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putString("name", dbAR.getName(i));
////                            bundle.putString("introduction", dbAR.getIntroduction());
//                            intent.putExtras(bundle);
//                            startActivity(intent);
                        }
                    }
                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    public class OverlayView extends View implements SensorEventListener {
        public OverlayView(Context context) {
            super(context);

            SensorManager sensors = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            Sensor accelSensor = sensors.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            Sensor compassSensor = sensors.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            Sensor gyroSensor = sensors.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

            boolean isAccelAvailable = sensors.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);
            boolean isCompassAvailable = sensors.registerListener(this, compassSensor, SensorManager.SENSOR_DELAY_NORMAL);
            boolean isGyroAvailable = sensors.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            dispCount = 0;

            if (DEBUG_MESSAGE == true) {
                dbgCreateArObj(canvas, ((float) (screenWidth * 0.35)), ((float) (screenHeight * 0.85)), "debug mode");
            }

            for (int i = 0; i < dbAR.getSize(); i++) {
                if (myOri != dbAR.getQuadrant(i)) {
                    continue;
                }

                if (dbAR.getDistance(i) <= MAXIMUM_DISTANCE) {
                    Log.d("getLatitude", ARActivity.myLocat.getLatitude() - dbAR.getLatitude(i) + "");
                    createNewObj(canvas, dbAR.getXCoord(i), dbAR.getYCoord(i), i);
                    dbAR.setIsShown(i, true);
                    dispCount++;
                }
                else {
                    dbAR.setIsShown(i, false);
                }

//                if (dispCount > MAXIMUM_NUM_DISPLAY_AR) {
//                    break;
//                }
            }

            if (dispCount == 0) {
                showArNotFound(canvas, ((float) (screenWidth * 0.35)), (((float) (screenHeight * 0.6))), arNotFound);
            }
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            double azimuth;
            float dist[] = new float[1];

            if (isLocatOK == true) {
                if (myLocat != null) {
                    dbAR.updateDB(myLocat);
                }
            }

//            StringBuilder msg = new StringBuilder(event.sensor.getName()).append(" ");
//            for (float value : event.values) {
//                msg.append("[").append(value).append("]");
//            }

            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
//                    accelData = msg.toString();
                    accelerometerValues = event.values;
                    break;
//                case Sensor.TYPE_GYROSCOPE:
//                    gyroData = msg.toString();
//                    Log.d("TYPE_GYROSCOPE", gyroData);
//                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
//                    compassData = msg.toString();
                    magneticFieldValues = event.values;
                    break;
            }

            /* get myself orientation */
            calculateOrientation();

            /* well be call OnDraw() always */
            this.invalidate();
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

    }

    public void createNewObj(Canvas canvas, float x, float y, int index) {
        Bitmap b = Bitmap.createScaledBitmap(arLocatMark, arLocatMark.getWidth()-300, arLocatMark.getHeight()-300, false);
        Paint contentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        contentPaint.setTextSize(40);
        canvas.drawBitmap(b, x, y, contentPaint);
//        canvas.drawText(namesArr[index], x.floatValue() + 110, y.floatValue() + 150, contentPaint);
        canvas.drawText(dbAR.getName(index), x + 110, y + 150, contentPaint);
        canvas.drawText("距離為 : " + dbAR.getDistance(index) + "公尺", x + 110, y + 200, contentPaint);
    }

    public void dbgCreateArObj(Canvas canvas, float x, float y, String str) {
        Paint contentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        contentPaint.setColor(Color.RED);
        contentPaint.setTextSize(50);

        areaRect = new Rect(((int) x), ((int) y-70), (int)x+300, (int)y+50);
        canvas.drawRect(areaRect, contentPaint);

        contentPaint.setColor(Color.WHITE);
        canvas.drawText(str, x, y, contentPaint);
        canvas.drawText("My position : " + ArChar.positionStr[myOri], 0, y + 100, contentPaint);
        canvas.drawText("My location : " + myLocat.getLatitude() + ", " + myLocat.getLongitude(), 0, y + 150, contentPaint);
        canvas.drawText("window size : " + dbAR.getWindowHeight() + ", " + dbAR.getWindowWidth(), 0, y + 200, contentPaint);
    }

    public void showArNotFound(Canvas canvas, float x, float y, Bitmap bitmap) {
        Bitmap b = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 4, bitmap.getHeight() / 4, false);
        Paint contentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        contentPaint.setTextAlign(Paint.Align.CENTER);
        contentPaint.setTextSize(40);
        canvas.drawBitmap(b, x-10, y-85, contentPaint);
        canvas.drawText("附近無景點", x + 95, y + 135, contentPaint);
    }

//    private double getAzimuthFromGPS(double lat_a, double lng_a, double lat_b, double lng_b) {
//        double d, b;
//        lat_a = lat_a * Math.PI / 180;
//        lng_a = lng_a * Math.PI / 180;
//        lat_b = lat_b * Math.PI / 180;
//        lng_b = lng_b * Math.PI / 180;
//
//        d = Math.sin(lat_a) * Math.sin(lat_b) + Math.cos(lat_a) * Math.cos(lat_b) * Math.cos(lng_b - lng_a);
//        d = Math.sqrt(1 - d * d);
//        d = Math.cos(lat_b) * Math.sin(lng_b - lng_a) / d;
//        b = Math.cos(lat_a) * Math.sin(lng_b - lng_a) / d;
//        b = Math.asin(b) * 180 / Math.PI + 180;
//        d = Math.asin(d) * 180 / Math.PI;
//
////        if (d < 0) {
////            d += 360;
////        }
//        Log.d("Azimuth", "Azimuth = " + String.valueOf(b));
//
//// d = Math.round(d*10000);
//        return b;
//    }

    private void calculateOrientation() {
        float[] values = new float[3];
        float[] R = new float[9];
        SensorManager.getRotationMatrix(R, null, accelerometerValues, magneticFieldValues);
        SensorManager.getOrientation(R, values);

        // 要经过一次数据格式的转换，转换为度
        values[0] = (float) Math.toDegrees(values[0]);
//        Log.i("Orientation", values[0] + "");
        //values[1] = (float) Math.toDegrees(values[1]);
        //values[2] = (float) Math.toDegrees(values[2]);

        if (values[0] >= -5 && values[0] < 5) {
            myOri = ArChar.positionEnum.NORTH.ordinal();
//            Log.i("Orientation", "正北");
        } else if (values[0] >= 5 && values[0] < 85) {
            myOri = ArChar.positionEnum.EAST_NORTH.ordinal();
//            Log.i("Orientation", "東北");
        } else if (values[0] >= 85 && values[0] <= 95) {
            myOri = ArChar.positionEnum.EAST.ordinal();
//            Log.i("Orientation", "正東");
        } else if (values[0] >= 95 && values[0] < 175) {
            myOri = ArChar.positionEnum.EAST_SOUTH.ordinal();
//            Log.i("Orientation", "東南");
        } else if ((values[0] >= 175 && values[0] <= 180) || (values[0]) >= -180 && values[0] < -175) {
            myOri = ArChar.positionEnum.SOUTH.ordinal();
//            Log.i("Orientation", "正南");
        } else if (values[0] >= -175 && values[0] < -95) {
            myOri = ArChar.positionEnum.WEST_SOUTH.ordinal();
//            Log.i("Orientation", "西南");
        } else if (values[0] >= -95 && values[0] < -85) {
            myOri = ArChar.positionEnum.WEST.ordinal();
//            Log.i("Orientation", "正西");
        } else if (values[0] >= -85 && values[0] < -5) {
            myOri = ArChar.positionEnum.WEST_NORTH.ordinal();
//            Log.i("Orientation", "西北");
        }
    }

//    private Bitmap screenShot(View view) {
//        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        view.draw(canvas);
//        return bitmap;
//    }
//
//    private static Bitmap getScreenShot(View view) {
//        View screenView = view.getRootView();
//        screenView.setDrawingCacheEnabled(true);
//        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
//        screenView.setDrawingCacheEnabled(false);
//        return bitmap;
//    }

    private void captureScreen(int requestCode) {
        boolean hasObj = false;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
        } else {
            Bitmap bitmap = cameraText.getBitmap();
            Canvas canvas = new Canvas(bitmap);

            for (int i = 0; i < dbAR.getSize(); i++) {
                if (dbAR.getIsShown(i) == true) {
                    createNewObj(canvas, dbAR.getXCoord(i), dbAR.getYCoord(i), i);
                    hasObj = true;
                }
            }

            if (hasObj == false) {
                showArNotFound(canvas, ((float) (screenWidth * 0.35)), (((float) (screenHeight * 0.6))), arNotFound);
            }

            Date now = new Date();
            android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
            CaptureScreen.savePic(bitmap, "sdcard/" + now + ".jpg");
        }
    }
}
