package com.pihrit.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pihrit.bakingapp.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ThumbnailFragment extends Fragment {
    public static final String TAG = ThumbnailFragment.class.getSimpleName();
    private static final String ARGUMENT_THUMBNAIL_URL = "param1";
    private static final String BUNDLE_THUMBNAIL_URL = "thumbnail-url";

    @BindView(R.id.iv_thumbnail)
    ImageView mThumbnailImageView;

    private String mThumbnailUrl;

    public ThumbnailFragment() {
        // Required empty public constructor
    }

    public static ThumbnailFragment newInstance(String thumbnailUrl) {
        ThumbnailFragment fragment = new ThumbnailFragment();
        Bundle args = new Bundle();
        args.putString(ARGUMENT_THUMBNAIL_URL, thumbnailUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mThumbnailUrl = getArguments().getString(ARGUMENT_THUMBNAIL_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_thumbnail, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            mThumbnailUrl = savedInstanceState.getString(BUNDLE_THUMBNAIL_URL);
        }

        initializePicasso();
    }

    private void initializePicasso() {
        if (!TextUtils.isEmpty(mThumbnailUrl)) {
            Picasso.get()
                    .load(mThumbnailUrl)
                    .placeholder(R.drawable.baking_placeholder)
                    .error(R.drawable.baking_placeholder_error)
                    .into(mThumbnailImageView);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BUNDLE_THUMBNAIL_URL, mThumbnailUrl);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
