package com.pihrit.bakingapp;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MediaPlayerFragment extends Fragment implements Player.EventListener {

    private SimpleExoPlayer mExoPlayer;
    @BindView(R.id.playerView)
    public PlayerView mPlayerView;
    private String mVideoURL;
    private String mThumbnailURL;
    private long mPlayPosition = C.TIME_UNSET;

    private static String TAG = MediaPlayerFragment.class.getSimpleName();
    private static final String EXTRA_VIDEO_URL = "video-url";
    private static final String EXTRA_THUMBNAIL_URL = "thumbnail-url";
    private static final String EXTRA_PLAY_POSITION = "play-position";

    public MediaPlayerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_media_player, container, false);
        ButterKnife.bind(this, v);

        if (savedInstanceState != null) {
            mVideoURL = savedInstanceState.getString(EXTRA_VIDEO_URL);
            mThumbnailURL = savedInstanceState.getString(EXTRA_THUMBNAIL_URL);
            mPlayPosition = savedInstanceState.getLong(EXTRA_PLAY_POSITION, C.TIME_UNSET);
        }

        initializePlayer();

        return v;
    }

    private void initializePlayer() {
        Uri mediaUri = null;
        if (mVideoURL != null && mVideoURL.length() > 0) {
            mediaUri = Uri.parse(mVideoURL);
        } else if (mThumbnailURL != null && mThumbnailURL.length() > 0) {
            mediaUri = Uri.parse(mThumbnailURL);
        }

        if (mExoPlayer == null && mediaUri != null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();


            RenderersFactory renderersFactory = new DefaultRenderersFactory(getActivity());
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            mExoPlayer.addListener(this);

            String userAgent = Util.getUserAgent(getActivity(), getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource.Factory(new DefaultDataSourceFactory(getActivity(), userAgent)).createMediaSource(mediaUri);

            mExoPlayer.prepare(mediaSource);
            mExoPlayer.seekTo(mPlayPosition);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            mPlayPosition = mExoPlayer.getCurrentPosition();
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

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
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }

    public void setVideoURL(String videoURL) {
        this.mVideoURL = videoURL;
    }

    public void setmThumbnailURL(String thumbnailURL) {
        this.mThumbnailURL = thumbnailURL;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_VIDEO_URL, mVideoURL);
        outState.putString(EXTRA_THUMBNAIL_URL, mThumbnailURL);
        outState.putLong(EXTRA_PLAY_POSITION, mPlayPosition);
    }
}
