package com.pihrit.bakingapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.pihrit.bakingapp.R;
import com.pihrit.bakingapp.activities.DetailsViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MediaPlayerFragment extends Fragment implements Player.EventListener {
    public static final String TAG = MediaPlayerFragment.class.getSimpleName();
    private static final String BUNDLE_VIDEO_URL = "video-url";
    private static final String BUNDLE_PLAY_POSITION = "play-position";
    private static final String BUNDLE_AUTO_PLAY = "auto-play";

    private SimpleExoPlayer mExoPlayer;
    @BindView(R.id.playerView)
    PlayerView mPlayerView;

    private String mVideoUrl;
    private long mPlayPosition;
    private boolean mStartAutoPlay;
    private MediaSource mMediaSource;

    public MediaPlayerFragment() {
        // Required empty public constructor
    }

    /**
     * Creates new instance of the fragment.
     *
     * @param videoUrl - Caller of this creation will decide if url is from videoUrl OR thumbnailUrl of the Recipe
     * @return
     */
    public static MediaPlayerFragment newInstance(String videoUrl) {
        MediaPlayerFragment fragment = new MediaPlayerFragment();
        Bundle args = new Bundle();
        args.putString(DetailsViewActivity.ARGUMENT_VIDEO_URL, videoUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mVideoUrl = getArguments().getString(DetailsViewActivity.ARGUMENT_VIDEO_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_media_player, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            mVideoUrl = savedInstanceState.getString(BUNDLE_VIDEO_URL);
            mPlayPosition = savedInstanceState.getLong(BUNDLE_PLAY_POSITION);
            mStartAutoPlay = savedInstanceState.getBoolean(BUNDLE_AUTO_PLAY);
        } else {
            clearPlayPosition();
        }

        initializePlayer();
    }

    private void initializePlayer() {
        Uri mediaUri = Uri.parse(mVideoUrl);
        if (mExoPlayer == null) {
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getActivity()),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl()
            );
            mPlayerView.setPlayer(mExoPlayer);
            mExoPlayer.addListener(this);

            String userAgent = Util.getUserAgent(getActivity(), getString(R.string.app_name));
            mMediaSource = new ExtractorMediaSource.Factory(
                    new DefaultDataSourceFactory(getActivity(), userAgent))
                    .createMediaSource(mediaUri);

            mExoPlayer.prepare(mMediaSource);
            mExoPlayer.seekTo(mPlayPosition);
            mExoPlayer.setPlayWhenReady(mStartAutoPlay);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            updatePlayPosition();
            mExoPlayer.release();
            mExoPlayer = null;
            mMediaSource = null;
        }
    }

    private void updatePlayPosition() {
        if (mExoPlayer != null) {
            mStartAutoPlay = mExoPlayer.getPlayWhenReady();
            mPlayPosition = Math.max(0, mExoPlayer.getContentPosition());
        }
    }

    private void clearPlayPosition() {
        mStartAutoPlay = true;
        mPlayPosition = C.TIME_UNSET;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        updatePlayPosition();

        outState.putString(BUNDLE_VIDEO_URL, mVideoUrl);
        outState.putLong(BUNDLE_PLAY_POSITION, mPlayPosition);
        outState.putBoolean(BUNDLE_AUTO_PLAY, mStartAutoPlay);
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
        if (mExoPlayer != null && mExoPlayer.getPlaybackError() != null) {
            updatePlayPosition();
        }
    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }
}
