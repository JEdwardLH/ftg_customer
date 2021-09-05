package com.foodtogo.user.ui.track.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.track.DeliveryPersonDetails;
import com.foodtogo.user.model.track.OrderStatusDetail;
import com.foodtogo.user.ui.track.interfaces.DriverInfo;
import com.foodtogo.user.util.ConversionUtils;
import com.foodtogo.user.util.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.foodtogo.user.base.AppConstants.YES;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.RatingRowHolder> {

    private List<OrderStatusDetail> orderStatusDetails;
    private DeliveryPersonDetails deliveryPersonDetails;
    private Context mContext;
    private DriverInfo driverInfo;
    private final static String NEW_ORDER = "New Order";
    private final static String ACCEPTED = "Accepted";
    private final static String PREPARE = "Preparing for deliver";
    private final static String DISPATCHED = "Dispatched";
    private final static String STARTED = "Started";
    private final static String ARRIVED = "Arrived";
    private final static String DELIVERED = "Delivered";
    private final static String FAILED = "Failed";


    public TrackAdapter(Context context, List<OrderStatusDetail> orderStatusDetails) {
        this.orderStatusDetails = orderStatusDetails;

        this.mContext = context;
    }

    public void setDeliveryPersonDetails(DeliveryPersonDetails deliveryPersonDetails) {
        this.deliveryPersonDetails = deliveryPersonDetails;
    }

    public void setDriverInfo(DriverInfo driverInfo) {
        this.driverInfo = driverInfo;
    }


    @NonNull
    @Override
    public RatingRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_track, null);
        return new RatingRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingRowHolder holder, int i) {
        OrderStatusDetail statusDetail = orderStatusDetails.get(i);
        holder.tvOrderStatus.setText(statusDetail.getOrdTitle());
        holder.tvDateTime.setText(ConversionUtils.getFormatDateTime(statusDetail.getOrdTiming()));
        holder.ivDriverStatus.setVisibility(View.GONE);
        holder.ivInfoStatus.setVisibility(View.GONE);
        holder.ivStatus.setVisibility(View.VISIBLE);
        setStatus(holder.ivStatusImage, statusDetail.getOrdTitle());
        if (statusDetail.getStageCompleted().equals(YES)) {
            holder.rlView.setAlpha(1f);
        } else {
            holder.ivStatus.setVisibility(View.GONE);
            holder.rlView.setAlpha(0.6f);
        }
        if (deliveryPersonDetails != null) {
            if (statusDetail.getOrdTitle().equals(DISPATCHED) && deliveryPersonDetails.getDeliverAssigned().equals(YES)) {
                GlideUtils.showRoundImage(mContext, holder.ivDriverStatus, R.drawable.profile_default_pic, deliveryPersonDetails.getDeliverImage());
                holder.ivDriverStatus.setVisibility(View.VISIBLE);
                holder.ivInfoStatus.setVisibility(View.VISIBLE);
                holder.ivStatus.setVisibility(View.GONE);
                holder.ivDriverStatus.setOnClickListener(v -> driverInfo.driverInfoClick());
            }
        }
    }


    private void setStatus(ImageView ivStatusImage, String ordTitle) {
        switch (ordTitle) {
            case NEW_ORDER:
                ivStatusImage.setImageResource(R.drawable.ic_new_order);
                break;
            case ACCEPTED:
                ivStatusImage.setImageResource(R.drawable.ic_order_recieved);
                break;
            case PREPARE:
                ivStatusImage.setImageResource(R.drawable.ic_order_confirmed);
                break;
            case DISPATCHED:
                ivStatusImage.setImageResource(R.drawable.ic_track_dispatched);
                break;
            case STARTED:
                ivStatusImage.setImageResource(R.drawable.ic_track_started);
                break;
            case ARRIVED:
                ivStatusImage.setImageResource(R.drawable.ic_track_arrived);
                break;
            case DELIVERED:
                ivStatusImage.setImageResource(R.drawable.ic_track_delivered);
                break;
            case FAILED:
                ivStatusImage.setImageResource(R.drawable.ic_failed_order);
                break;

        }
    }

    @Override
    public int getItemCount() {
        return orderStatusDetails.size();
    }

    class RatingRowHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_status_image)
        ImageView ivStatusImage;

        @BindView(R.id.tv_order_status)
        TextView tvOrderStatus;

        @BindView(R.id.tv_date_time)
        TextView tvDateTime;

        @BindView(R.id.iv_status)
        ImageView ivStatus;

        @BindView(R.id.iv_driver_status)
        ImageView ivDriverStatus;

        @BindView(R.id.iv_info_status)
        ImageView ivInfoStatus;

        @BindView(R.id.rl_view)
        RelativeLayout rlView;


        RatingRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}