package com.example.agamelist;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.agamelist.fragments_pprincipal.fragment_noticias;
import com.example.agamelist.fragments_pprincipal.fragment_ranking;
import com.example.agamelist.fragments_pprincipal.fragment_resenas;

public class adaptador_main_pagina_principal extends FragmentStateAdapter {

    public adaptador_main_pagina_principal(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0:
                return new fragment_noticias();
            case 1:
                return new fragment_resenas();
            case 2:
                return new fragment_ranking();
            default:
                return new fragment_noticias();
                // Establecemos página de noticias como principal x defecto
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
