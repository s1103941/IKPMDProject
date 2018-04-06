package com.example.abdoul.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.abdoul.ikpmdproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapters.KeuzeAdapter;
import Database.DatabaseHelper;
import Models.KeuzeModel;
import Models.UserModel;
import Models.getIP;


public class KeuzeActivity extends AppCompatActivity {

    private List<KeuzeModel> vakken;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayout;
    private KeuzeAdapter adapter;
    private getIP ip = new getIP();
    RequestQueue requestQueue;
    String showUrl = "http://" + ip.getIP() + "/uservak.php";
    public static UserModel currentGebruiker;

    // Setting up all the objects needed for the navigation drawer
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private DatabaseHelper mDatabaseHelper = new DatabaseHelper(this);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.SimpleList);
        requestQueue = Volley.newRequestQueue(getApplicationContext());


        mDrawerList = (ListView)findViewById(R.id.navList);
        vakken = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        getVakken();
        getVakkenFromDB();

        gridLayout = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayout);

        Button button = findViewById(R.id.button1);

        Intent i = getIntent();
        currentGebruiker = (UserModel) i.getSerializableExtra("gebruiker");


        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        addDrawerItems();
        setupDrawer();


    }


    public void getVakken(){


    }

    private void addDrawerItems() {
        String[] osArray = { "Mijn keuzevakken"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(KeuzeActivity.this, StudentVakListActivity.class);
                startActivity(intent);
            }
        });
    }


    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }












    private void getVakkenFromDB() {

        if(LoginActivity.networkConnected){
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                    showUrl,null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    System.out.println(response.toString());
                    try {
                        JSONArray vakken = response.getJSONArray("vakken");
                        for (int i = 0; i < vakken.length(); i++) {
                            JSONObject student = vakken.getJSONObject(i);

                            int vakID = student.getInt("KeuzevakID");
                            String modulecode  = student.getString("ModuleCode");
                            String ects = student.getString("Ects");
                            String periode = student.getString("Periode");
                            int inschrijvingen = student.getInt("Inschrijvingen");


                            KeuzeActivity.this.vakken.add(new KeuzeModel(vakID, modulecode, ects, periode, inschrijvingen));

                        }

                        adapter = new KeuzeAdapter(KeuzeActivity.this, KeuzeActivity.this.vakken, KeuzeActivity.this.currentGebruiker);
                        recyclerView.setAdapter(adapter);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.append(error.getMessage());

                }
            });
            requestQueue.add(jsonObjectRequest);
        }

        else {
            Cursor data = mDatabaseHelper.getVakken();
            Log.d("Hewa", "Jackson");
            while(data.moveToNext()){
                KeuzeActivity.this.vakken.add(new KeuzeModel(data.getInt(0), data.getString(1), data.getString(2), data.getString(3), data.getInt(4)));
            }
            adapter = new KeuzeAdapter(KeuzeActivity.this, KeuzeActivity.this.vakken, KeuzeActivity.this.currentGebruiker);
            recyclerView.setAdapter(adapter);
        }

    }

}


