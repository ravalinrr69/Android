package androidservices.ravalinrr.com.myapplication11;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/*
 * Created by ravalin on 4/10/18.
*/

public class ItemDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "location.db";
    public static final String ID = "Id";
    public static final String TABLE_NAME = "restaurants";
    public static final String COL_RESTAURANT_LOCATION = "RestaurantLocation";
    public static final String COL_OVERALL_RATING = "OverallRating";
    public static final String COL_REVIEW = "Review";
    public static final String COL_DATE_OF_VISIT = "DateOfVisit";
    public static final String COL_PHOTOS = "Photos";
    long result;
    SQLiteDatabase sqLiteDatabase;

    public ItemDatabase(Context context) {

        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table restaurants (Id INTEGER PRIMARY KEY AUTOINCREMENT, RestaurantLocation TEXT, OverallRating TEXT, Review TEXT, DateOfVisit TEXT, Photos BLOB)");
    }

    //Check whether the table exists or not and create it
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertLocation(String location) {

        sqLiteDatabase = this.getWritableDatabase();
        Log.d("sqlitedatabase", "*****value: *****" + sqLiteDatabase);
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_RESTAURANT_LOCATION, location);

        result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertReview(String address, String review, float rating) {

        String ratingString = String.valueOf(rating);

        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_RESTAURANT_LOCATION, address);
        contentValues.put(COL_REVIEW, review);
        contentValues.put(COL_OVERALL_RATING, ratingString);
        result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        Log.d("Content Values: ", contentValues.toString());

        if (result == -1)
            return false;
        else
            return true;

    }
}