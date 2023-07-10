package com.example.agamelist;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.agamelist.dialog_misma_franquicia.AdapterMismaFranquicia;
import com.example.agamelist.dialog_misma_franquicia.ItemMismaFranquicia;
import com.example.agamelist.recycler_ranking.AdapterRanking;
import com.example.agamelist.recycler_ranking.ItemRanking;

import java.util.ArrayList;
import java.util.List;


public class fragment_mismafranquicia extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_mismafranquicia, container, false);
        return view;
    }

}