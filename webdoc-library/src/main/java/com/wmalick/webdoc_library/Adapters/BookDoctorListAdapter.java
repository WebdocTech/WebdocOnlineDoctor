package com.wmalick.webdoc_library.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wmalick.webdoc_library.Dashboard.Fragments.ConsultDoctorFragments.DoctorProfile.DoctorProfileFargment;
import com.wmalick.webdoc_library.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class BookDoctorListAdapter extends RecyclerView.Adapter<BookDoctorListAdapter.ViewHolder> {
    Activity context;
    FragmentManager fragmentManager;

    public BookDoctorListAdapter(Activity context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public BookDoctorListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_doctor_appointment_item,parent,false);
        return new BookDoctorListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookDoctorListAdapter.ViewHolder holder, final int position) {

        holder.btn_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer_WebdocDashboardActivity, new DoctorProfileFargment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text_name;
        public TextView text_degree;
        public TextView textSpeciality;
        public CircleImageView profile_image;
        Button btn_booking,btn_Profile;
        public TextView textCity;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_name=itemView.findViewById(R.id.tv_name);
            text_degree=itemView.findViewById(R.id.textDegree);
            textSpeciality=itemView.findViewById(R.id.tv_speciality);
            btn_booking=itemView.findViewById(R.id.btn_Booking);
            profile_image=itemView.findViewById(R.id.user_image);
            textCity=itemView.findViewById(R.id.text_City);
            btn_Profile=itemView.findViewById(R.id.btn_Profile);
            ratingBar = itemView.findViewById(R.id.ratingBar_WebdocRating);
        }
    }
}
