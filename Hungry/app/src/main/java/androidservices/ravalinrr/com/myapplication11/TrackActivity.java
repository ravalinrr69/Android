package androidservices.ravalinrr.com.myapplication11;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;


public class TrackActivity extends AppCompatActivity {

    RatingBar ratingBar;
    EditText reviewTextView;
    String restaurantAddress;
    ItemDatabase itemDatabase = new ItemDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        Bundle bundle = getIntent().getExtras();
        restaurantAddress = bundle.getString("addresss");
    }

    public void save(View view) {

        reviewTextView = (EditText) findViewById(R.id.review_tv);

        boolean isInserted = itemDatabase.insertReview(restaurantAddress, reviewTextView.getText().toString(), ratingBar.getRating());
        Log.d("Values inserted", ": " + isInserted);
        Toast.makeText(getApplicationContext(), "Review inserted", Toast.LENGTH_LONG).show();
    }

    public void myReviews(View view) {
        Intent intent = new Intent(this, MyReviews.class);
        startActivity(intent);
    }
}
