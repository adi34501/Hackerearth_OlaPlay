package ad.play.Activity;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Picasso;

import ad.play.R;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class PlayerActivity extends AppCompatActivity {

    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;
    String URL, Cover, song, artist;
    @InjectView(R.id.thumbnail_song)
    ImageView thumb;
    @InjectView(R.id.play)
    ImageView play_button;
    @InjectView(R.id.pause)
    ImageView pause_button;
    @InjectView(R.id.song_name)
    TextView song_name;
    @InjectView(R.id.artist_name)
    TextView Artist_name;
    @InjectView(R.id.download)
    ImageView mDownload;
    DownloadManager downloadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ButterKnife.inject(this);
        intit();
        initMediaPlayer();

    }

    private void intit() {

        URL = getIntent().getStringExtra("URL");
        Cover = getIntent().getStringExtra("Cover");
        song = getIntent().getStringExtra("SONG");
        artist = getIntent().getStringExtra("ARTIST");
        Picasso.with(this)
                .load(Cover)
                .into(thumb);
        song_name.setText(song);
        Artist_name.setText(artist);

        mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadData(Uri.parse(URL));
            }
        });

    }

    private long DownloadData(Uri uri) {

        long downloadReference;
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        Toast.makeText(getApplicationContext(), "Downloading..", Toast.LENGTH_SHORT).show();

        request.setTitle(song);
        request.setDescription("Android Data download using DownloadManager.");

        request.setDestinationInExternalFilesDir(PlayerActivity.this, Environment.DIRECTORY_DOWNLOADS, song);

        downloadReference = downloadManager.enqueue(request);

        return downloadReference;
    }

    private void initMediaPlayer() {
        Handler mHandler = new Handler();
        SimpleExoPlayerView playerView = (SimpleExoPlayerView) findViewById(R.id.video_view);
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:40.0) Gecko/20100101 Firefox/40.0";
        Uri uri = Uri.parse(URL);
        DataSource.Factory dataSourceFactory = new DefaultHttpDataSourceFactory(
                userAgent, null,
                DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
                DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
                true);
        MediaSource mediaSource = new ExtractorMediaSource(uri, dataSourceFactory, Mp3Extractor.FACTORY,
                mHandler, null);

        TrackSelector trackSelector = new DefaultTrackSelector();
        DefaultLoadControl loadControl = new DefaultLoadControl();
        exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
        playerView.setPlayer(exoPlayer);
        exoPlayer.prepare(mediaSource);

        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                exoPlayer.setPlayWhenReady(true);
            }
        });

        pause_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exoPlayer.setPlayWhenReady(false);
            }


        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        exoPlayer.release();
    }


};



