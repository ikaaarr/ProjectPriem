package com.example.priemkomis;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ZayavkaAdapter extends RecyclerView.Adapter<ZayavkaAdapter.ViewHolder> {
    private List<StudDannie> zayavkaList;

    // Конструктор, методы адаптера и класса ViewHolder


    public ZayavkaAdapter(List<StudDannie> zayavkaList) {
        this.zayavkaList = zayavkaList;
    }

    @NonNull

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showdata, parent, false);
        return new ViewHolder(view);
    }




    public void onBindViewHolder(ViewHolder holder, int position) {
        StudDannie zayavka = zayavkaList.get(position);
        holder.nazvanieTextView.setText(zayavka.getФИО());
        holder.opisanieTextView.setText(zayavka.getНомер_телефона());
        holder.dataTextView.setText(zayavka.getБалл());
        holder.timeTextView.setText(zayavka.getАттестат());
        holder.GroupTextView.setText(zayavka.getГруппа());
        holder.DopGroupTextView.setText(zayavka.getДопГруппа());
    }


    public int getItemCount() {
        return zayavkaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nazvanieTextView;
        TextView opisanieTextView;
        TextView dataTextView;
        TextView timeTextView;
        TextView GroupTextView;
        TextView DopGroupTextView;



        public ViewHolder(View itemView) {
            super(itemView);

            nazvanieTextView = itemView.findViewById(R.id.NazvanieMeropTextView);
            opisanieTextView = itemView.findViewById(R.id.OpisanieTextView);
            dataTextView = itemView.findViewById(R.id.DataTextView);
            timeTextView = itemView.findViewById(R.id.TimeTextView);
            GroupTextView = itemView.findViewById(R.id.textView6);
            DopGroupTextView = itemView.findViewById(R.id.textViewDopGrp);

        }
    }
}
