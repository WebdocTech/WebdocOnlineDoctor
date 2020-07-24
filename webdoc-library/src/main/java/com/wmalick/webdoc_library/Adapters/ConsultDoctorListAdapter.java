package com.wmalick.webdoc_library.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wmalick.webdoc_library.Dashboard.Fragments.ConsultDoctorFragments.DoctorConsult.DoctorConsultActivity;
import com.wmalick.webdoc_library.Dashboard.Fragments.ConsultDoctorFragments.DoctorProfile.DoctorProfileFargment;
import com.wmalick.webdoc_library.Essentials.Global;
import com.wmalick.webdoc_library.R;
import com.wmalick.webdoc_library.ResponseModels.DoctorListResult.Doctorprofile;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConsultDoctorListAdapter extends RecyclerView.Adapter<ConsultDoctorListAdapter.ViewHolder> {
    Activity context;
    FragmentManager fragmentManager;
    public ConsultDoctorListAdapter(Activity context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ConsultDoctorListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_doctor_consult_item,parent,false);
        return new ConsultDoctorListAdapter.ViewHolder(view);
    }


    public void onBindViewHolder(@NonNull ConsultDoctorListAdapter.ViewHolder holder, final int position) {
        final Doctorprofile item = Global.doctorListResponse.getDoctorListResult().getDoctorprofiles().get(position);

        final String docName = item.getFirstName() + " " + item.getLastName();
        final String imageUrl = item.getImgLink();
        final String speciality = item.getDoctorSpecialty();
        final String degree = item.getEducation();
        final float stepSize = Float.parseFloat(item.getRate());
        final String city = item.getCityId();

        holder.text_name.setText(docName);
        holder.text_degree.setText(degree);
        holder.text_speciality.setText(speciality);
        Picasso.get().load(imageUrl).placeholder(R.drawable.ic_placeholder_doctor).error(R.drawable.ic_placeholder_doctor).into(holder.profile_image);
        holder.textCity.setText(city);
        holder.ratingBar.setRating(stepSize);


        holder.btn_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.selectedDoctor = item;
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer_WebdocDashboardActivity, new DoctorProfileFargment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        holder.btn_Consult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Global.selectedDoctor = item;
                context.startActivity(new Intent(context,  DoctorConsultActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return Global.doctorListResponse.getDoctorListResult().getDoctorprofiles().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text_name;
        public TextView text_degree;
        public TextView text_speciality;
        public Button btn_Consult;
        public CircleImageView profile_image;
        public TextView textCity;
        public Button btn_Profile;
        public RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_name=itemView.findViewById(R.id.tv_name);
            text_degree=itemView.findViewById(R.id.textDegree);
            text_speciality=itemView.findViewById(R.id.tv_speciality);
            btn_Consult=itemView.findViewById(R.id.btn_Consult);
            profile_image=itemView.findViewById(R.id.user_image);
            textCity=itemView.findViewById(R.id.text_City);
            btn_Profile=itemView.findViewById(R.id.btn_Profile);
            ratingBar = itemView.findViewById(R.id.ratingBar_WebdocRating);
            btn_Consult.setTextColor(Color.parseColor(Global.THEME_COLOR_CODE));
            btn_Profile.setTextColor(Color.parseColor(Global.THEME_COLOR_CODE));
            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(Color.parseColor(Global.THEME_COLOR_CODE), PorterDuff.Mode.SRC_ATOP);
            //ratingBar.setBackgroundColor(Color.parseColor(Global.THEME_COLOR_CODE));
        }
    }
}