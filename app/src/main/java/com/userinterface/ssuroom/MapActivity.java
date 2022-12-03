package com.userinterface.ssuroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, SearchView.OnQueryTextListener {
    private GoogleMap map;
    private Marker currentMarker;

    /*
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int UPDATE_INTERVAL_MS = 1000; //1초
    private static final int FASTEST_UPDATE_INTERVAL_MS = 500; //0.5초
    private static final int PERMISSIONS_REQUSET_CODE = 100;
    */

    Location mCurrentLocation;
    LatLng currentPosition;

    /*private FusedLocationProviderClient client;
    LocationRequest locationRequest;
    private Location location; */

    public SearchView search;
    public Button submit;
    public Geocoder geocoder;

    //private View mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        search = findViewById(R.id.map_search);

        search.setOnQueryTextListener(this);

        // mLayout = findViewById(R.id.layout_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        geocoder = new Geocoder(this);


        //맵이 켜질때 서울의 좌표가 마커로 뜸
        LatLng seoul = new LatLng(37.56, 126.97);

        MarkerOptions options = new MarkerOptions();
        options.position(seoul)
                .title("서울")
                .snippet("한국의 수도");
        map.addMarker(options);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 12));

        // 맵 클릭시 경도 위도 받아와서 마커 추가
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions mOptions = new MarkerOptions();
                Double latitude = latLng.latitude;
                Double longitude = latLng.longitude;

                mOptions.snippet(latitude.toString() + "," + longitude.toString());
                mOptions.position(new LatLng(latitude, longitude));
                map.addMarker(mOptions);
            }
        });

        /* 실패 뭐가 잘못된건지 잘 모르겠음
        LatLng currentLatlng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions options = new MarkerOptions();
        options.position(currentLatlng);
        options.title("현재 위치");
        options.snippet("내 위치");
        options.draggable(true);

        currentMarker = map.addMarker(options);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentLatlng);
        map.moveCamera(cameraUpdate);
        */

        /* 맵이 켜질때 서울의 좌표가 마커로 뜸
        LatLng seoul = new LatLng(37.56, 126.97);

        MarkerOptions options = new MarkerOptions();
        options.position(seoul)
                .title("서울")
                .snippet("한국의 수도");
        map.addMarker(options);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 12));
        */

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        String text = search.getQuery().toString();
        List<Address> addressList = null;

        try {
            addressList = geocoder.getFromLocationName(text, 10);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(addressList.size()<1){
            Toast.makeText(this,"검색 장소를 찾을 수 없습니다.",Toast.LENGTH_SHORT).show();
            return false;
        }

        String[] splitStr = addressList.get(0).toString().split(",");
        String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length() - 2); // 주소
        String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // 위도
        String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // 경도
        LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

        /*
        MarkerOptions mOptions2 = new MarkerOptions();
        mOptions2.title("검색 결과");
        mOptions2.snippet(address);
        mOptions2.position(point);
        map.addMarker(mOptions2);
         */

        Log.d("map_search",address+": "+latitude+", "+longitude);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}