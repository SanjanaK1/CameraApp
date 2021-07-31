package com.example.sanja.cameraproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.hardware.Camera;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

public class MyCamera extends SurfaceView implements SurfaceHolder, SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;

    public MyCamera(Context context) {
        super(context);
    }

    public static Camera getCameraInstance(int instance){
        Camera camera = null;
        try {
            camera = Camera.open(instance); // attempt to get a Camera instance

        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return camera; // returns null if camera is unavailable
    }



    public MyCamera(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        System.out.println("-----> IN My camera constructor");
        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        //mHolder.set
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        System.out.print("Surface created");
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
            mCamera.setDisplayOrientation(90    );
        } catch (IOException e) {
            //Log.d(TAG, "Error setting camera preview: " + e.getMessage());
            System.out.println(" ----> " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        System.out.print("Surface Changed");

        if (mHolder.getSurface() == null){
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        //Camera.Parameters info = mCamera.getC

        Camera.Parameters param = mCamera.getParameters();
        List<Camera.Size> l = param.getSupportedPreviewSizes();

        //mCamera.get


        param.setPreviewSize(((Camera.Size)l.get(0)).width, ((Camera.Size)l.get(0)).height);

        //mCamera.setPreviewS
        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e){
            //Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }



    @Override
    public void addCallback(Callback callback) {

    }

    @Override
    public void removeCallback(Callback callback) {

    }

    @Override
    public boolean isCreating() {
        return false;
    }

    @Override
    public void setType(int type) {

    }

    @Override
    public void setFixedSize(int width, int height) {

    }

    @Override
    public void setSizeFromLayout() {

    }

    @Override
    public void setFormat(int format) {

    }

    public SurfaceHolder getSurfaceHolder(){
        return mHolder;
    }

    @Override
    public Canvas lockCanvas() {
        return null;
    }

    @Override
    public Canvas lockCanvas(Rect dirty) {
        return null;
    }

    @Override
    public Canvas lockHardwareCanvas() {
        return null;
    }

    @Override
    public void unlockCanvasAndPost(Canvas canvas) {

    }

    @Override
    public Rect getSurfaceFrame() {
        return null;
    }

    @Override
    public Surface getSurface() {
        return null;
    }
}
