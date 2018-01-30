package ad.play.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import ad.play.Adapter.AlbumsAdapter;
import ad.play.R;
import ad.play.REST.DataClient;
import ad.play.REST.Models.songs;
import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity {


    private ArrayList<songs> mSongs;
    @InjectView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    private AlbumsAdapter mAdapter;
    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSongs = new ArrayList<>();
        setupRecyclerView();
        getSongs();
    }


    private void getSongs() {
        loadprogressbar();

        DataClient dataClient = new DataClient();
        dataClient.getDataAPIs().getPlantList(new Callback<ArrayList<songs>>() {
            @Override
            public void success(ArrayList<songs> plantListDataData, Response response) {
                mSongs = plantListDataData;
                mProgress.dismiss();
                setupRecyclerView();
            }

            @Override
            public void failure(RetrofitError error) {
                mProgress.dismiss();

            }
        });

    }


    public void loadprogressbar() {
        mProgress = new ProgressDialog(MainActivity.this);
        mProgress.setIndeterminate(true);
        mProgress.setCancelable(true);
        mProgress.setMessage("Fetching Songs...");
        mProgress.show();
    }


    private void setupRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        RecyclerView.LayoutManager mLayoutManager= new GridLayoutManager(this,2);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2,10,true));
        mAdapter = new AlbumsAdapter(mSongs, this);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        SearchView.SearchAutoComplete searchAutoComplete =
                (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(0xff444444);
        searchAutoComplete.setTextColor(0xff444444);
        search(searchView);
        return true;
    }


    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {

            this.spanCount = spanCount;
            this.includeEdge = includeEdge;
            this.spacing = spacing;

        }

    }
}

