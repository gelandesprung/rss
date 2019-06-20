package top.froms.www.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil.ItemCallback;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import top.froms.www.R;
import top.froms.www.ViewModels.ImgFragmentViewModel;
import top.froms.www.adapters.ImgFragmentAdapter;
import top.froms.www.repo.ImgData;


public class ImgFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private ImgFragmentAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.blog_fragment, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.blog_container);
        Context context;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new ImgFragmentAdapter(new ItemCallback<ImgData>() {
            @Override
            public boolean areItemsTheSame(@NonNull ImgData oldItem, @NonNull ImgData newItem) {
                return oldItem.getImgUrl().equals(newItem.getImgUrl());
            }

            @Override
            public boolean areContentsTheSame(@NonNull ImgData oldItem, @NonNull ImgData newItem) {
                return oldItem.getTitle().equals(newItem.getTitle());
            }
        });
        mRecyclerView.setAdapter(adapter);
        ViewModelProviders.of(this).get(ImgFragmentViewModel.class).loadImages()
                .observe(this, imgData -> adapter.submitList(imgData));
    }
}
