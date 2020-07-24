package com.wmalick.webdoc_library.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.wangjie.shadowviewhelper.ShadowProperty;
import com.wangjie.shadowviewhelper.ShadowViewDrawable;
import com.wmalick.webdoc_library.Dashboard.Fragments.PrescriptionHistoryFragment.PrescriptionHistoryDetailsFragment;
import com.wmalick.webdoc_library.Essentials.Global;
import com.wmalick.webdoc_library.R;
import com.wmalick.webdoc_library.ResponseModels.CustomerConsultationResult.ConusltationList;

import static android.graphics.Color.rgb;


public class PrescriptionHistoryAdapter extends RecyclerView.Adapter<PrescriptionHistoryAdapter.ViewHolder>{
    Context context;

    public PrescriptionHistoryAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public  ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.prescription_history_item,parent,false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final ConusltationList myListComplain = Global.customerConsultationList.get(position);

        //for shadow layout
        ShadowProperty sp = new ShadowProperty()
                .setShadowColor(0x77000000)
                .setShadowDy(dip2px(context, 1.5f))
                .setShadowRadius(dip2px(context, 3));
        //  .setShadowSide(ShadowProperty.ALL);
        ShadowViewDrawable sd = new ShadowViewDrawable(sp, rgb(250,250,250), 30, 30);
        ViewCompat.setBackground(holder.constraintLayout, sd);
        ViewCompat.setLayerType(holder.constraintLayout, View.LAYER_TYPE_SOFTWARE, null);

        final String doc1 = myListComplain.getCompliant();
        String consultationDate = myListComplain.getConsultationDate();
        String doctorName = myListComplain.getDoctorName();

        holder.complain.setText(doc1);

        /*final  Dashboard_Consult_Model myListdate = Global.ConsultDate.get(position);
        final String doc2 = myListdate.getDate();*/
        holder.date.setText(consultationDate);

        /*final  Dashboard_Consult_Model myListname = Global.ConsultDoctor.get(position);
        final String doc3 = myListname.getDrName();*/
        holder.name.setText(doctorName);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Global.selectedCustomerConsultationPosition = position;

                final AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new PrescriptionHistoryDetailsFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.Consult_frag, myFragment).addToBackStack(null).commit();
            }
        });

      }


    public static int dip2px(Context context, float dpValue) {
        try {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 1.5f);
        } catch (Throwable throwable) {
            // ignore
        }
        return 0;
    }


    @Override
    public int getItemCount() {
        return Global.customerConsultationList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
         public TextView name, date, complain;
         public ImageView next;
         ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            complain=itemView.findViewById(R.id.tv_complain);
            date=itemView.findViewById(R.id.tv_date);
            name=itemView.findViewById(R.id.tv_drName);
            next=itemView.findViewById(R.id.iv_next);
            constraintLayout=itemView.findViewById(R.id.Prescription_shadowLayout);

            name.setTextColor(Color.parseColor(Global.THEME_COLOR_CODE));
        }
    }


}
