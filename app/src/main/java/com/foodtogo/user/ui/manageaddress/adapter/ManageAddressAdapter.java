package com.foodtogo.user.ui.manageaddress.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foodtogo.user.BaseApplication;
import com.foodtogo.user.R;
import com.foodtogo.user.model.shippingaddress.MultiLocation;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.foodtogo.user.base.AppConstants.HOME_ADDRESS;
import static com.foodtogo.user.base.AppConstants.WORK_ADDRESS;

public class ManageAddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private AddressClickListener clickListener;
    private List<MultiLocation> multiLocationList;
    private ItemVH itemVH;


    public ManageAddressAdapter(AddressClickListener clickListener, List<MultiLocation> multiLocationList) {
        this.clickListener = clickListener;
        this.multiLocationList = multiLocationList;
    }

    public void setList(List<MultiLocation> multiLocations){
        this.multiLocationList=multiLocations;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v1 =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manage_address, parent, false);
        return new ItemVH(v1);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MultiLocation multiLocation=multiLocationList.get(position);
        itemVH = (ItemVH) holder;
        itemVH.editImg.setOnClickListener(v-> clickListener.editAddressField(multiLocation));
        itemVH.deleteImg.setOnClickListener(v-> clickListener.deleteAddressField(String.valueOf(multiLocation.getLocIc())));
        String address=multiLocation.getLocLocation()+"\n"+multiLocation.getLocAddress();
        itemVH.tvAddress.setText(address);
        itemVH.itemView.setTag(multiLocation);


        /*address image*/
        if(multiLocation.getLocType()==HOME_ADDRESS){
             itemVH.addressType.setText(BaseApplication.getContext().getString(R.string.nav_home));
             itemVH.addressImg.setImageResource(R.drawable.ic_home_address);
        }else if(multiLocation.getLocType()==WORK_ADDRESS){
            itemVH.addressType.setText(BaseApplication.getContext().getString(R.string.key_work));
            itemVH.addressImg.setImageResource(R.drawable.ic_building_front);
        }else{
            itemVH.addressType.setText(BaseApplication.getContext().getString(R.string.others));
            itemVH.addressImg.setImageResource(R.drawable.ic_world_address);
        }


    }



    @Override
    public int getItemCount() {
        return multiLocationList.size();
    }

    class ItemVH extends RecyclerView.ViewHolder{

        @BindView(R.id.address_img)
        ImageView addressImg;

        @BindView(R.id.img_edit_address)
        ImageView editImg;

        @BindView(R.id.img_delete)
        ImageView deleteImg;

        @BindView(R.id.tv_address_type)
        TextView addressType;

        @BindView(R.id.tv_address)
        TextView tvAddress;

        public ItemVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v->{
                clickListener.onItemViewClicked((MultiLocation) itemView.getTag());
            });
        }

    }

    public interface AddressClickListener{
        void editAddressField(MultiLocation multiLocation);
        void deleteAddressField(String id);
        void onItemViewClicked(MultiLocation multiLocation);
    }
}
