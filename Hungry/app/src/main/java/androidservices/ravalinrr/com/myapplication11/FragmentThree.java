package androidservices.ravalinrr.com.myapplication11;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ravalinagabandi on 4/13/18.
 */

public class FragmentThree extends Fragment {

    Activity context;

    public FragmentThree() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_review, container, false);
        Intent intent = new Intent(context, MyReviews.class);
        startActivity(intent);
        return v;
    }
}