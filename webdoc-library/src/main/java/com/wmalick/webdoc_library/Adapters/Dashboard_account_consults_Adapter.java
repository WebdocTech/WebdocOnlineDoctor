package com.wmalick.webdoc_library.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.wmalick.webdoc_library.Essentials.Global;
import com.wmalick.webdoc_library.R;
import com.wmalick.webdoc_library.ResponseModels.CustomerConsultationResult.Consultationdetail;


public class Dashboard_account_consults_Adapter extends RecyclerView.Adapter<Dashboard_account_consults_Adapter.ViewHolder> {
    Context context;

    public Dashboard_account_consults_Adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_prescription_history_item,parent,false);
        return new ViewHolder(view);
    }


    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Consultationdetail myListName = Global.customerConsultationDetailsList.get(position);

        String medicineName = myListName.getMedicineName();
        String medicineDays = myListName.getNoOfDays();
        String medicineMorning = myListName.getMorning();
        String medicineNoon = myListName.getNight();
        String additionalNotes = myListName.getAdditionalNotes();

        holder.MedicineName.setText(medicineName);

        holder.MedicineDays.setText(medicineDays);

        holder.MedicineQntyMorning.setText(medicineMorning);

        holder.MedicineQntyEvening.setText(medicineNoon);

        holder.expand_notes.setText(additionalNotes);

    }

    @Override
    public int getItemCount() {
        return Global.customerConsultationDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView MedicineName,MedicineDays,MedicineQntyMorning,MedicineQntyNoon,MedicineQntyEvening;
        public ExpandableTextView expand_notes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            MedicineName=itemView.findViewById(R.id.tv_MedicineName);
            MedicineDays=itemView.findViewById(R.id.tv_MedicineDays);
            MedicineQntyMorning=itemView.findViewById(R.id.tv_qntyMorning);
            MedicineQntyNoon=itemView.findViewById(R.id.tv_qntyNoon);
            MedicineQntyEvening=itemView.findViewById(R.id.tv_qntyEvening);
            expand_notes=itemView.findViewById(R.id.expand_notes);
        }
    }
}
