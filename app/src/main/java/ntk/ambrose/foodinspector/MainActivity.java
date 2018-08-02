package ntk.ambrose.foodinspector;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ntk.ambrose.foodinspector.recognizer.Classifier;
import ntk.ambrose.foodinspector.recognizer.ImageClassifierFactory;
import ntk.ambrose.foodinspector.recognizer.ImageUtilsKt;
import ntk.ambrose.foodinspector.recognizer.Result;

public class MainActivity extends AppCompatActivity {

    TextureView textureView;
    TextureView.SurfaceTextureListener textureListener;
    CameraDevice cameraDevice;
    private HandlerThread mBackgroundThread;
    private Handler mBackgroundHandler;

    private static final boolean MAINTAIN_ASPECT = true;

    private static final Size DESIRED_PREVIEW_SIZE = new Size(640, 480);

    private Integer sensorOrientation;

    private Matrix frameToCropTransform;
    private Matrix cropToFrameTransform;

    private String cameraId;
    protected CameraCaptureSession cameraCaptureSessions;
    protected CaptureRequest.Builder captureRequestBuilder;
    private Size imageDimension;
    private ImageReader imageReader;
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private FloatingActionButton btCapture;

    private Classifier foodClassifier;
    private Classifier eatableClassifier;

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecognizers();
        initCameraPreview();
        btCapture = findViewById(R.id.btCapture);
        btCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               RecognitionActivity.results=new ArrayList<>();
                Log.i("Classifier","Starting");

                //RecognitionActivity.results = createFakeResult();
                //startActivity(new Intent(MainActivity.this,RecognitionActivity.class));

                if(textureView.getBitmap()!=null) {
                    Log.i("Classifier","Accepted");
                    classify(textureView.getBitmap());
                    startActivity(new Intent(MainActivity.this,RecognitionActivity.class));
                }
            }
        });
    }

    public void classify(Bitmap photo){
        Bitmap croppedImage = ImageUtilsKt.getCroppedBitmap(photo);
        ArrayList<Result> results = eatableClassifier.recognizeImage(croppedImage);
        if(results.size()>=1){
            if(results.get(0).getResult().equals("eatable")){
                RecognitionActivity.results = foodClassifier.recognizeImage(croppedImage);
            }
            else{
                TastyToast.makeText(getBaseContext(),"There are no food!",TastyToast.LENGTH_LONG,TastyToast.CONFUSING).show();
            }
        }
    }

    private Classifier createClassifier(String graphFile, String labelsFile, int imageSize, String inputName, String outputName) {
        return ImageClassifierFactory.INSTANCE.create(
                getAssets(),
                graphFile,
                labelsFile,
                imageSize,
                inputName,
                outputName
        );
    }

    private void initCameraPreview(){
        textureView = findViewById(R.id.cameraPreview);
        textureView.setSurfaceTextureListener(textureListener);

        textureListener = new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                // Open camera khi ready
                openCamera();
            }
            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                // Transform you image captured size according to the surface width and height, và thay đổi kích thước ảnh
            }
            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                return false;
            }
            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
            }
        };
    }

    private void initRecognizers(){
        foodClassifier = createClassifier("file:///android_asset/food_graph.pb","file:///android_asset/food_labels.txt",224,"Placeholder","final_result");
        eatableClassifier = createClassifier("file:///android_asset/eatable_graph.pb","file:///android_asset/eatable_labels.txt",224,"Placeholder","final_result");
    }

    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }


    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            // Camera opened

            cameraDevice = camera;
            createCameraPreview();
        }
        @Override
        public void onDisconnected(CameraDevice camera) {
            cameraDevice.close();
        }
        @Override
        public void onError(CameraDevice camera, int error) {
            cameraDevice.close();
            cameraDevice = null;
        }
    };

    final CameraCaptureSession.CaptureCallback captureCallbackListener = new CameraCaptureSession.CaptureCallback() {
        @Override
        public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
            super.onCaptureCompleted(session, request, result);
            createCameraPreview();
        }
    };

    protected void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("Camera Background");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }
    protected void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    protected void takePicture() {
        if(null == cameraDevice) {

            return;
        }
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraDevice.getId());
            Size[] jpegSizes = null;
            if (characteristics != null) {
                jpegSizes = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP).getOutputSizes(ImageFormat.JPEG);
            }

            // CAPTURE IMAGE với tuỳ chỉnh kích thước
            int width = 640;
            int height = 480;
            if (jpegSizes != null && 0 < jpegSizes.length) {
                width = jpegSizes[0].getWidth();
                height = jpegSizes[0].getHeight();
            }
            ImageReader reader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1);
            List<Surface> outputSurfaces = new ArrayList<Surface>(2);
            outputSurfaces.add(reader.getSurface());
            outputSurfaces.add(new Surface(textureView.getSurfaceTexture()));
            final CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            captureBuilder.addTarget(reader.getSurface());
            captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
            // kiểm tra orientation tuỳ thuộc vào mỗi device khác nhau như có nói bên trên
            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation));
            ImageReader.OnImageAvailableListener readerListener = new ImageReader.OnImageAvailableListener() {
                @Override
                public void onImageAvailable(ImageReader reader) {
                    Image image = null;

                }

            };
            reader.setOnImageAvailableListener(readerListener, mBackgroundHandler);
            final CameraCaptureSession.CaptureCallback captureListener = new CameraCaptureSession.CaptureCallback() {
                @Override
                public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
                    super.onCaptureCompleted(session, request, result);
                    createCameraPreview();
                }
            };
            cameraDevice.createCaptureSession(outputSurfaces, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(CameraCaptureSession session) {
                    try {
                        session.capture(captureBuilder.build(), captureListener, mBackgroundHandler);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onConfigureFailed(CameraCaptureSession session) {
                }
            }, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }


    protected void createCameraPreview() {
        try {
            SurfaceTexture texture = textureView.getSurfaceTexture();
            assert texture != null;
            texture.setDefaultBufferSize(imageDimension.getWidth(), imageDimension.getHeight());
            Surface surface = new Surface(texture);
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);
            cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback(){
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    //The camera is already closed
                    if (null == cameraDevice) {
                        return;
                    }
                    // When the session is ready, we start displaying the preview.
                    cameraCaptureSessions = cameraCaptureSession;
                    updatePreview();
                }
                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {

                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    private void openCamera() {
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            cameraId = manager.getCameraIdList()[0];
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            assert map != null;
            imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];
            // Add permission for camera and let user grant the permission
            // Kiểm tra permission với android sdk >= 23
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA_PERMISSION);
                return;
            }
            manager.openCamera(cameraId, stateCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }
    protected void updatePreview() {
        if(null == cameraDevice) {

        }
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        try {
            cameraCaptureSessions.setRepeatingRequest(captureRequestBuilder.build(), null, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    private void closeCamera() {
        if (null != cameraDevice) {
            cameraDevice.close();
            cameraDevice = null;
        }
        if (null != imageReader) {
            imageReader.close();
            imageReader = null;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                // close the app
                Toast.makeText(MainActivity.this, "Sorry!!!, you can't use this app without granting permission", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

        startBackgroundThread();
        if (textureView.isAvailable()) {
            openCamera();
        } else {
            textureView.setSurfaceTextureListener(textureListener);
        }
    }
    @Override
    protected void onPause() {

        //closeCamera();
        stopBackgroundThread();
        super.onPause();
    }

    private ArrayList<Result> createFakeResult(){
        ArrayList<Result> result = new ArrayList<>();
        result.add(new Result("Rice",0.8f));
        result.add(new Result("Beefsteak",0.2f));
        return result;
    }

}
