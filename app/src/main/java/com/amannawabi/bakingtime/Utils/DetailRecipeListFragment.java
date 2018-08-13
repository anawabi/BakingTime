package com.amannawabi.bakingtime.Utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amannawabi.bakingtime.R;

import java.util.ArrayList;

public class DetailRecipeListFragment extends Fragment {

    public static String LIST_NAMES_EXTRA = "names_extra";

    private ArrayList<String> mNameList;

    public DetailRecipeListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mNameList = savedInstanceState.getStringArrayList(LIST_NAMES_EXTRA);
        } else {
            mNameList = getArguments().getStringArrayList(LIST_NAMES_EXTRA);
        }

        final View rootView = inflater.inflate(R.layout.fragment_detail_recipe_list,container,false);

        RecyclerView recyclerView = rootView.findViewById(R.id.lv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        ListAdapter listAdapter = new ListAdapter(getContext(),(ListAdapter.ItemListener) getContext(), mNameList);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(listAdapter);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(LIST_NAMES_EXTRA,mNameList);
    }
}
