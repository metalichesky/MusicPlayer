package com.ldt.musicr.ui.bottomnavigationtab.library.playlist;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ldt.musicr.R;
import com.ldt.musicr.service.MusicServiceEventListener;
import com.ldt.musicr.ui.BaseActivity;
import com.ldt.musicr.ui.bottomnavigationtab.BaseMusicServiceFragment;
import com.ldt.musicr.ui.bottomnavigationtab.pager.PlaylistPagerFragment;
import com.ldt.musicr.loader.PlaylistLoader;
import com.ldt.musicr.model.Playlist;
import com.ldt.musicr.ui.bottomnavigationtab.feature.FeaturePlaylistAdapter;
import com.ldt.musicr.ui.widget.fragmentnavigationcontroller.SupportFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaylistChildTab extends BaseMusicServiceFragment implements FeaturePlaylistAdapter.PlaylistClickListener {
    private static final String TAG ="PlaylistChildTab";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    PlaylistChildAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.playlist_child_tab,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        mAdapter = new PlaylistChildAdapter(getActivity(),true);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        if(getActivity() instanceof BaseActivity) {
            ((BaseActivity)getActivity()).addMusicServiceEventListener(this);
        }
        refreshData();
;    }
    private void refreshData() {
        if(getActivity() !=null)
      mAdapter.setData(PlaylistLoader.getAllPlaylistsWithAuto(getActivity()));
    }

    @Override
    public void onClickPlaylist(Playlist playlist, @org.jetbrains.annotations.Nullable Bitmap bitmap) {
        SupportFragment sf = PlaylistPagerFragment.newInstance(getContext(),playlist,bitmap);
        Fragment parentFragment = getParentFragment();
        if(parentFragment instanceof SupportFragment)
            ((SupportFragment)parentFragment).getNavigationController().presentFragment(sf);
    }

    @Override
    public void onMediaStoreChanged() {
        refreshData();
    }

}
