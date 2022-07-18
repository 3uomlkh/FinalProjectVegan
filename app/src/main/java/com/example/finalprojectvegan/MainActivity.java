package com.example.finalprojectvegan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

//    final String TAG = getClass

    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;

    Fragment fragment_homefeed;
    Fragment fragment_map;
    Fragment fragment_ocr;
    Fragment fragment_mypage;
    Fragment fragment_bookmark;
//    Fragment fragment_ocr_popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragment_homefeed = new FragHomeFeed();
        fragment_map = new FragMap();
        fragment_ocr = new FragOcr();
        fragment_mypage = new FragMypage();
        fragment_bookmark = new FragBookmark();

//        fragment_ocr_popup = new FragOcrPopup();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // 초기 플래그먼트 설정
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment_homefeed).commitAllowingStateLoss();


        // 바텀 네비게이션
        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        // 리스너 등록
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Log.i(TAG, "바텀 네비게이션 클릭");

                switch (item.getItemId()){
                    case R.id.homefeed:
//                        Log.i(TAG, "home 들어옴");
                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment_homefeed).commitAllowingStateLoss();
                        return true;
                    case R.id.map:
//                        Log.i(TAG, "star 들어옴");
                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment_map).commitAllowingStateLoss();
                        return true;
                    case R.id.ocr:
//                        Log.i(TAG, "group 들어옴");
                        Intent ocrintent = new Intent(MainActivity.this, ocrActivity.class);
                        startActivity(ocrintent);
//                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment_ocr).commitAllowingStateLoss();
                        return true;
                    case R.id.mypage:
//                        Log.i(TAG, "hotel 들어옴");
                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment_mypage).commitAllowingStateLoss();
                        return true;
                    case R.id.bookmark:
//                        Log.i(TAG, "hotel 들어옴");
                        //getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment_bookmark).commitAllowingStateLoss();
                        Intent intent = new Intent(MainActivity.this, BookmarkActivity.class);
                        startActivity(intent);
                        return true;
                }
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(getApplicationContext(), "검색창 클릭됨", Toast.LENGTH_SHORT).show();
                return true;
        }
        return true;
    }


}