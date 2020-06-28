package com.wmalick.webdoc_library.Dashboard.Fragments.ConsultDoctorFragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.wmalick.webdoc_library.Dashboard.Fragments.ConsultDoctorFragments.DoctorConsultation.DoctorConsultationFragment;
import com.wmalick.webdoc_library.Dashboard.WebdocDashboardActivity;
import com.wmalick.webdoc_library.R;

import java.util.ArrayList;
import java.util.List;

public class ConsultDoctorFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_consult_doctor, container, false);
        WebdocDashboardActivity.toolbar.setTitle(getString(R.string.consult_doctor));

        InitControl(view);
        ActionControl(view);
        return view;
    }

    private void ActionControl(View view) {

    }

    private void InitControl(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.viewPager_ConsultDortorFrag);
        viewPager.setOffscreenPageLimit(1);
        addTabs(viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout_ConsulDoctorFrag);
        tabLayout.setupWithViewPager(viewPager);
        setupTabView();
    }


    private void addTabs(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFrag(new DoctorConsultationFragment());
        //adapter.addFrag(new DoctorAppointmentFragment());
        viewPager.setAdapter(adapter);
    }

    public void setupTabView(){
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setCustomView(R.layout.custom_tab_layout_item);
            TextView tab_name = tabLayout.getTabAt(i).getCustomView().findViewById(R.id.textView);
            ImageView imgView = tabLayout.getTabAt(i).getCustomView().findViewById(R.id.imageView);
            if (i == 0) {
                tab_name.setText(getString(R.string.consult));
                imgView.setImageResource(R.drawable.appoitment);
            }
            if (i == 1) {
                tab_name.setText(getString(R.string.appointment));
                imgView.setImageResource(R.drawable.consultation);
            }
        }
    }


    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment) {
            mFragmentList.add(fragment);
        }
    }

}
