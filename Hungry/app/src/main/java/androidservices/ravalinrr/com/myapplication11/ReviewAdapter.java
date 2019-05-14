package androidservices.ravalinrr.com.myapplication11;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ravalin on 4/12/18.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    private Context mCtx;
    private List<Review> reviewList;

    public ReviewAdapter(Context mCtx, List<Review> reviewList) {
        this.mCtx = mCtx;
        this.reviewList = reviewList;
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Creating a LayoutInflater object
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        //Create a View object
        View view = layoutInflater.inflate(R.layout.list_layout, parent, false);

        //Now create a ReviewHolder instance passing the list_layout view
        ReviewHolder reviewHolder = new ReviewHolder(view);
        return reviewHolder;
    }

    @Override
    public void onBindViewHolder(final ReviewHolder holder, int position) {
        Review review = reviewList.get(position);

        holder.restaurantName.setText(review.getRestaurantName());
        holder.restaurantAddress.setText(review.getLocation());
        holder.review.setText(review.getReview());

        //share the card with others
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_SEND);

                intent.putExtra(Intent.EXTRA_TEXT, "Restaurant " + holder.restaurantName.getText().toString() + " located: "
                        + holder.restaurantAddress.getText().toString() +
                        ". My Personal Review was: " + holder.review.getText().toString());
                intent.setType("text/plain");
                mCtx.startActivity(Intent.createChooser(intent, "Send To"));

            }
        });

    }
    /*  Send through Whatsapp
    public void shareCard(View view){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        mCtx.startActivity(sendIntent);
    }
*/
    @Override
    public int getItemCount() {

        return reviewList.size();
    }

    class ReviewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView restaurantName, restaurantAddress, review;
        Button share = itemView.findViewById(R.id.btnShare);

        public ReviewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            review = itemView.findViewById(R.id.review);
            restaurantName = itemView.findViewById(R.id.restaurantName);
            restaurantAddress = itemView.findViewById(R.id.restaurant_address);
        }
    }
}
