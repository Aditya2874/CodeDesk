package com.example.cpscheduler;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class VPAdapter extends FragmentStateAdapter {


    public VPAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

       switch (position){
           case 0:
               return new AllFragment();
           case 1:
               return new codeforcesFragment();
           case 2:
               return new codechefFragment();
           default:
               return new leetcodeFragment();
       }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
