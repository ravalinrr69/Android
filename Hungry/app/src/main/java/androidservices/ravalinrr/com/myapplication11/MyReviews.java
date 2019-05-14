package androidservices.ravalinrr.com.myapplication11;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MyReviews extends AppCompatActivity {

    RecyclerView recyclerView;
    ReviewAdapter reviewAdapter;
    private DrawerLayout mDrawerLayout;
    List<Review> reviewList;
    StringBuffer buffer = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_reviews);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        //LinearLayoutManager by default provides a vertical view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewList = new ArrayList<Review>();
        ActionBar actionbar = getSupportActionBar();
        mDrawerLayout = findViewById(R.id.drawer_layout);

        ItemDatabase mDbHelper = new ItemDatabase(getApplicationContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + ItemDatabase.TABLE_NAME + ";";
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {

                reviewList.add(new Review(
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4)
                ));
            } while (c.moveToNext());
        }
        //to add items from SQLite db - follow this link
        //adding some items to the review list
        reviewAdapter = new ReviewAdapter(this, reviewList);
        recyclerView.setAdapter(reviewAdapter);
    }

    public void checkIn(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}