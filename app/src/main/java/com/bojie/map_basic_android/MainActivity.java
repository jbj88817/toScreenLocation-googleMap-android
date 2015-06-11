package com.bojie.map_basic_android;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity {

    GoogleMap mGoogleMap;
    private static LatLng MountainView = new LatLng(37.42, -122.08);
    private static LatLng MountainView2 = new LatLng(37.421, -122.081);
    View mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initial Map
        try {

            if (mGoogleMap == null) {
                mGoogleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView = getSupportFragmentManager().findFragmentById(R.id.map).getView();

        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);

        //mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MountainView, 15));

        Marker marker = mGoogleMap.addMarker(new MarkerOptions()
                .position(MountainView)
                .title("Have a nice day!"));

        convertCoordinates((float) 37.42, (float) -122.08);

    }

    private void convertCoordinates(float latitude, float longitude) {
        final LatLng location = new LatLng(latitude, longitude);
        if (location.latitude != (float) 0.0 || location.longitude != (float) 0.0) {
            if (mapView.getViewTreeObserver().isAlive()) {
                mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        // remove the listener
                        // ! before Jelly Bean:
                        mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        // ! for Jelly Bean and later:
                        //mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        // set map viewport
                        // CENTER is LatLng object with the center of the map
                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
                        // ! you can query Projection object here
                        Point markerScreenPosition = mGoogleMap.getProjection().toScreenLocation(location);
                        // ! example output in my test code: (356, 483)
                        Log.d("x!!!!", markerScreenPosition.x + "");
                        Log.d("y!!!!", markerScreenPosition.y + "");
                    }
                });

            } else {
                //Log.e(TAG, "BAD COORDINATES!!!");
            }
        }
    }
}
