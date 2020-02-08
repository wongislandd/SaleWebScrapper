package com.e.uniqlosalewebscrapper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


// 7/16/19
// Currently only reads UNIQLO, maybe add a side menu and just have each brand be a tab,
// basically putting all the sales you want into one spot.
// then maybe add a cuSTOM STORE ONE? not sure how that'd work, it must be more universal if I were to do that

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ArrayList<Item> items = new ArrayList<>();
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dl = (DrawerLayout) findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this,dl,R.string.open,R.string.close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        final NavigationView nav_view = findViewById(R.id.nav_view);
        nav_view.bringToFront(); // another nav_view is present so the clicks are going to that? not sure but this line is needed for it to work.
        nav_view.setItemIconTintList(null);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() { // for selecting which store you wish to see
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Log.d(TAG, "onNavigationItemSelected: " + id);
                switch (id) {
                    case R.id.Uniqlo:
                        Log.d(TAG, "onNavigationItemSelected: Uniqlo clicked");
                        initUniqloItems();
                        dl.closeDrawers(); // this all seems maybe unoptimal but functional. hmmm.
                        break;
                    case R.id.HM:
                        Log.d(TAG, "onNavigationItemSelected: H&M clicked");
                        initHMItems();
                        dl.closeDrawers();
                        break;
                    case R.id.Settings:
                        break;
                }
                return false;
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       int id = item.getItemId();
        switch (id) {
            case R.id.refreshoption:
                if (getTitle().equals("Uniqlo Sale Scrapper")){
                    initUniqloItems();
                }
                else if (getTitle().equals("H&M Sale Scrapper")) {
                    initHMItems();
                }
                Toast.makeText(MainActivity.this, "Results refreshed", Toast.LENGTH_SHORT).show();
                break;
        }
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
    private void initUniqloItems(){
        UniqloHelper helper = new UniqloHelper();
        helper.getWebsiteItems(this);
        setTitle(R.string.uniqlotitle);
    }
    private void initHMItems(){
        HMHelper helper = new HMHelper();
        helper.getWebsiteItems(this);
        setTitle(R.string.hmtitle);
    }
    public void setItemsArr(ArrayList<Item> items){ // same for all, this method is to be used by other threads to set the activities item array, tryna be real modular here
        this.items = items;
        Log.d(TAG, "setItemsArr: items updated");
        initRecyclerView(); // must be here, if used under the specific array, i think it the threads run too fast and by the time it's called the other threads not ready
    }
    private void initRecyclerView() { // same for all
        Log.d(TAG, "initRecyclerView: ");
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        ItemRecyclerViewAdapter adapter = new ItemRecyclerViewAdapter(this, items);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
