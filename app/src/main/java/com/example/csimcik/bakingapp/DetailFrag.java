package com.example.csimcik.bakingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.audio.AudioCapabilities;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.RandomTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.util.Util;

import java.security.Policy;

import static android.content.Intent.getIntent;
import static android.view.Gravity.FILL;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.google.android.exoplayer2.SimpleExoPlayer.*;
import static com.google.android.exoplayer2.extractor.mp3.Mp3Extractor.FLAG_ENABLE_CONSTANT_BITRATE_SEEKING;


/**
 * Created by csimcik on 6/17/2017.
 */
public class DetailFrag extends Fragment {

    static public SimpleExoPlayerView playerView;
    static SimpleExoPlayer player;
    static MediaSource mediaSource;
    public static final String ABR_ALGORITHM_EXTRA = "abr_algorithm";
    private static final String ABR_ALGORITHM_DEFAULT = "default";
    private static final String ABR_ALGORITHM_RANDOM = "random";
    String  KEY_TRACK_SELECTOR_PARAMETERS = "track params",
            KEY_AUTO_PLAY = "auto play",
            KEY_WINDOW = "window",
            KEY_POSITION ="position";
    private DefaultTrackSelector trackSelector;
    private DefaultTrackSelector.Parameters trackSelectorParameters;
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    public static boolean startAutoPlay;
    int startWindow;
    long startPosition;
    static public String vidURL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9cb_4-press-crumbs-in-pie-plate-creampie/4-press-crumbs-in-pie-plate-creampie.mp4";
    @Override
    public void onSaveInstanceState(Bundle playerPos) {
        updateTrackSelectorParameters();
        updateStartPosition();
        playerPos.putBoolean(KEY_AUTO_PLAY, startAutoPlay);
        playerPos.putInt(KEY_WINDOW, startWindow);
        playerPos.putLong(KEY_POSITION, startPosition);
        Long exoPosition = player.getCurrentPosition();
        super.onSaveInstanceState(playerPos);
    }
    private void updateTrackSelectorParameters() {
        if (trackSelector != null) {
            trackSelectorParameters = trackSelector.getParameters();
        }
    }

    private void updateStartPosition() {
        if (player != null) {
            startAutoPlay = player.getPlayWhenReady();
            startWindow = player.getCurrentWindowIndex();
            startPosition = Math.max(0, player.getCurrentPosition());
        }
    }

    @Override
public  void onActivityCreated(Bundle savedInstanceState){
super.onActivityCreated(savedInstanceState);
if(savedInstanceState != null){
    if (savedInstanceState != null) {
        trackSelectorParameters = savedInstanceState.getParcelable(KEY_TRACK_SELECTOR_PARAMETERS);
        startAutoPlay = savedInstanceState.getBoolean(KEY_AUTO_PLAY);
        startWindow = savedInstanceState.getInt(KEY_WINDOW);
        startPosition = savedInstanceState.getLong(KEY_POSITION);
        }
}
        initializePlayer();
        Log.i("this Pos",String.valueOf(savedInstanceState));
        Log.i("is seekable ? ", String.valueOf(player.isCurrentWindowSeekable()));
        Log.i("format ", String.valueOf(player.getVideoFormat()));

}
    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
         //  initializePlayer();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
     /*   if ((Util.SDK_INT <= 23 || player == null)) {
           initializePlayer();
        }*/
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
           releasePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.exo_play, null);
        playerView = (SimpleExoPlayerView) v.findViewById(R.id.video_view);
        ViewTreeObserver viewTreeObserver = playerView.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    playerView.getViewTreeObserver();
                 playerView.getLayoutParams().height = (int)(playerView.getWidth()*.57);

                }
            });
        }

        return v;
    }
    private void initializePlayer() {
        Intent intent = getActivity().getIntent();
        TrackSelection.Factory trackSelectionFactory;
        String abrAlgorithm = intent.getStringExtra(ABR_ALGORITHM_EXTRA);
        if (abrAlgorithm == null || ABR_ALGORITHM_DEFAULT.equals(abrAlgorithm)) {
            trackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
        } else if (ABR_ALGORITHM_RANDOM.equals(abrAlgorithm)) {
            trackSelectionFactory = new RandomTrackSelection.Factory();
        } else {
            Log.i("TrckSlctError", intent.getStringExtra(ABR_ALGORITHM_EXTRA));
            return;
        }
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getActivity()),
                new DefaultTrackSelector(), new DefaultLoadControl());
        Uri uri = Uri.parse(vidURL);
        mediaSource = buildMediaSource(uri);
        playerView.setPlayer(player);
        playerView.getLayoutParams().height = 0;
        player.seekTo(startWindow,startPosition);
        player.setPlayWhenReady(startAutoPlay);
        player.prepare(mediaSource, true, false);
        player.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {
                Log.i("window", String.valueOf(timeline.getWindowCount()));
            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {




            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            }
            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity() {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }
        });


    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory("ua"),
                new DefaultExtractorsFactory().setMp3ExtractorFlags(FLAG_ENABLE_CONSTANT_BITRATE_SEEKING), null, null);
    }



}