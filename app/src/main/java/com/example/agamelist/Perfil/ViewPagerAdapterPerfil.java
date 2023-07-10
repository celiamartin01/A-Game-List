package com.example.agamelist.Perfil;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.agamelist.fragments_perfil.fragment_listas;
import com.example.agamelist.fragments_perfil.fragment_rating;
import com.example.agamelist.fragments_perfil.fragment_resenas_perfil;

public class ViewPagerAdapterPerfil extends FragmentStateAdapter {
    public ViewPagerAdapterPerfil(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new fragment_listas();
            case 1:
                return new fragment_resenas_perfil();
            case 2:
                return new fragment_rating();
            default:
                return new fragment_listas();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
