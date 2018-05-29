package fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.media.Image;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.aplikacija.Gadgets;
import com.example.user.aplikacija.PostsActivity;
import com.example.user.aplikacija.R;
import com.example.user.aplikacija.ReadPostActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import Utils.PostService;
import Utils.RetrofitObject;
import adapters.PostsAdapter;
import model.Post;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tools.Mockup;


public class MyFragment extends Fragment implements OnMapReadyCallback {

    private Post post;

    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvAuthor;
    private TextView tvDate;
    private TextView tvLocation;
    private TextView postPopularity;

    private ImageButton btnLike;
    private boolean btnLikePressed;
    private ImageButton btnDislike;
    private boolean btnDislikePressed;


    private SupportMapFragment supportMapFragment;
    MapView mMapView;
    GoogleMap mGoogleMap;
    private UiSettings mUiSettings;

    private int userId;

    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View myInflatedView = inflater.inflate(R.layout.content_detail, container, false);
        Post post = (Post) getArguments().getSerializable("post");


        TextView tvTitle = (TextView) myInflatedView.findViewById(R.id.tvTitle);
        TextView tvDescription = (TextView) myInflatedView.findViewById(R.id.tvDescription);
        ImageView image = (ImageView) myInflatedView.findViewById(R.id.ivPhoto);
        TextView tvAuthor = (TextView) myInflatedView.findViewById(R.id.tvAuthor);
        TextView tvDate = (TextView) myInflatedView.findViewById(R.id.tvDate);
        String dateString = post.getDate().toString().substring(3, 16);
        tvLocation = (TextView) myInflatedView.findViewById(R.id.tvLocation);

        btnLike = myInflatedView.findViewById(R.id.buttonLike);
        btnDislike = myInflatedView.findViewById(R.id.buttonDislike);
        postPopularity = (TextView) myInflatedView.findViewById(R.id.tvPopularity);
        tvTitle.setText(post.getTitle());
        tvDescription.setText(post.getDescription());
        tvAuthor.setText(post.getAuthor().getName());

        postPopularity.setTextColor((post.getPopularity() >= 0) ? Color.GREEN : Color.RED);
        postPopularity.setText(Integer.toString(post.getPopularity()));

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLikePressed();
            }
        });
        btnDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDislikePressed();
            }
        });


        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = df.format(post.getDate());
        tvDate.setText(strDate);
        //tvDate.setText(dateString);
        Picasso.with(getContext()).load(post.getImage()).into(image);

        googlePlayServicesAvailable();

        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapPostLocation);
        supportMapFragment.getMapAsync(this);




        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLikePressed();
            }
        });

        btnDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDislikePressed();
            }
        });


        return myInflatedView;
    }


    @Override
    public void onResume() {
        super.onResume();
        post = (Post) getArguments().getSerializable("post");



        Geocoder geocoder = new Geocoder(getContext());
        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(post.getLatitude(), post.getLongitude(), 1);
            String locality = addresses.get(0).getLocality();
            String countryName = addresses.get(0).getCountryName();
            tvLocation.setText("Near " + locality + " , " + countryName);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


    private void btnLikePressed () {
        // Provera da li je ulogovani korisnik autora posta, ako jeste ne moze da lajkuje-dislajkuje
        if (userId == 0)
            userId = getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE).getInt("userId", 1);
        if (userId == post.getAuthor().getId()) {
            Gadgets.showSimpleOkDialog(getContext(), "Ne mozete lajkovati svoju objavu!!!");
            return;
        }


        if(btnLike.getBackground().getConstantState() == getResources().getDrawable(R.drawable.like_pressed).getConstantState()){
            removeLike();
            return;
        }

        // Ako nije vec kliknuto izvrsava se, u suprotnom kulira
        if (!btnLikePressed) {
            PostService postService = RetrofitObject.retrofit.create(PostService.class);
            Call<ResponseBody> call = postService.updateLikes(post.getId());
            // ProgressDialog
            //final ProgressDialog progressDialog = Gadgets.getProgressDialog(getContext());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    response.body().close();

                    post.like();
                    postPopularity.setText(Integer.toString(post.getPopularity()));
                    // Ako je popularnost veca ili jednaka nuli bojimo u zeleno, ako ne crvenimo ga
                    postPopularity.setTextColor((post.getPopularity() >= 0) ? Color.GREEN : Color.RED);

                    btnLikePressed = true;
                    btnLike.setBackgroundDrawable(getResources().getDrawable(R.drawable.like_pressed));
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

    private void removeLike() {


        if (!btnDislikePressed) {
            PostService postService = RetrofitObject.retrofit.create(PostService.class);
            Call<ResponseBody> call = postService.updateDislikes(post.getId());
            // ProgressDialog
            //final ProgressDialog progressDialog = Gadgets.getProgressDialog(getContext());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    response.body().close();

                    post.dislike();
                    postPopularity.setText(Integer.toString(post.getPopularity()));
                    // Ako je popularnost veca ili jednaka nuli bojimo u zeleno, ako ne crvenimo ga
                    postPopularity.setTextColor((post.getPopularity() >= 0) ? Color.GREEN : Color.RED);

                    btnLikePressed = false;
                    btnLike.setBackgroundDrawable(getResources().getDrawable(R.drawable.like));
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    private void btnDislikePressed () {
        // Provera da li je ulogovani korisnik autora posta, ako jeste ne moze da lajkuje-dislajkuje
        if (userId == 0)
            userId = getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE).getInt("userId", 1);
        if (userId == post.getAuthor().getId()) {
            Gadgets.showSimpleOkDialog(getContext(), "Ne mozete dislajkovati svoju objavu!!!");
            return;
        }


        if(btnDislike.getBackground().getConstantState() == getResources().getDrawable(R.drawable.dislike_pressed).getConstantState()){
            removeDislike();
            return;
        }

        if (!btnDislikePressed) {
            PostService postService = RetrofitObject.retrofit.create(PostService.class);
            Call<ResponseBody> call = postService.updateDislikes(post.getId());
            // ProgressDialog
            //final ProgressDialog progressDialog = Gadgets.getProgressDialog(getContext());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    response.body().close();

                    post.dislike();
                        postPopularity.setText(Integer.toString(post.getPopularity()));
                    // Ako je popularnost veca ili jednaka nuli bojimo u zeleno, ako ne crvenimo ga
                    postPopularity.setTextColor((post.getPopularity() >= 0) ? Color.GREEN : Color.RED);

                    btnDislikePressed = true;
                    btnDislike.setBackgroundDrawable(getResources().getDrawable(R.drawable.dislike_pressed));
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void removeDislike() {
        if (!btnLikePressed) {
            PostService postService = RetrofitObject.retrofit.create(PostService.class);
            Call<ResponseBody> call = postService.updateLikes(post.getId());
            // ProgressDialog
            //final ProgressDialog progressDialog = Gadgets.getProgressDialog(getContext());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    response.body().close();

                    post.like();
                    postPopularity.setText(Integer.toString(post.getPopularity()));
                    // Ako je popularnost veca ili jednaka nuli bojimo u zeleno, ako ne crvenimo ga
                    postPopularity.setTextColor((post.getPopularity() >= 0) ? Color.GREEN : Color.RED);

                    btnDislikePressed = false;
                    btnDislike.setBackgroundDrawable(getResources().getDrawable(R.drawable.dislike));
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                }
            });


        }


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        LatLng pozicijaPosta = new LatLng(post.getLatitude(), post.getLongitude());
        mGoogleMap.addMarker(new MarkerOptions().position(pozicijaPosta).title("Post created from this location"));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(pozicijaPosta));

        mUiSettings = mGoogleMap.getUiSettings();

        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true);
    }

    /**
     * Method to check if Google Play Services are available on device
     *
     * @return boolean
     */
    public boolean googlePlayServicesAvailable() {
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getActivity());

        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and user can make map requests
            Toast.makeText(getActivity(), "Google Play Services is working", Toast.LENGTH_SHORT).show();
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(getActivity(), "Can't make map requests, sorry...", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
