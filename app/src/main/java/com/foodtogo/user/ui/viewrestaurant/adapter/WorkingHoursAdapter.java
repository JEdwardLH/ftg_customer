package com.foodtogo.user.ui.viewrestaurant.adapter;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.restaurant.WorkingHour;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.foodtogo.user.base.AppConstants.AVAILABLE;

public class WorkingHoursAdapter extends RecyclerView.Adapter<WorkingHoursAdapter.ItemRowHolder> {

    private List<WorkingHour> itemListList;

    public WorkingHoursAdapter(List<WorkingHour> itemLists) {
        this.itemListList = itemLists;
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_working_hours, null);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder holder, int i) {
        WorkingHour workingHour = itemListList.get(i);
        holder.tvDay.setText(workingHour.getWorkingDate().substring(0, 3));
        holder.tvFromTime.setText(workingHour.getWorkingFromTime());
        holder.tvToTime.setText(workingHour.getWorkingEndTime());
        holder.tvOpenStatus.setText(workingHour.getAvailableStatus());
        holder.itemView.setAlpha(workingHour.getAvailableStatus().equals(AVAILABLE) ? 1.0f : 0.6f);
    }


    @Override
    public int getItemCount() {
        return (null != itemListList ? itemListList.size() : 0);
    }

    class ItemRowHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_day)
        TextView tvDay;

        @BindView(R.id.tv_from_time)
        TextView tvFromTime;

        @BindView(R.id.tv_to_time)
        TextView tvToTime;

        @BindView(R.id.tv_open_status)
        TextView tvOpenStatus;

        ItemRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}