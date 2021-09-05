package com.foodtogo.user.ui.home.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foodtogo.user.R;
import com.foodtogo.user.model.home.HomeSearchChild;
import com.foodtogo.user.model.home.HomeSearchHead;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter {

    public interface Listener {
        void onClickStore(HomeSearchHead homeSearchHead);
        void onClickItem(HomeSearchHead homeSearchHead,HomeSearchChild homeSearchChild);
    }

    static final int VT_HEAD = 0;
    static final int VT_CHILD = 1;

    List<Object> objectList;
    Listener listener;

    public SearchAdapter(List<Object> objectList,Listener listener) {
        this.objectList = objectList;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        Object object = objectList.get(position);
        if (object instanceof HomeSearchHead) {
            return VT_HEAD;
        } else {
            return VT_CHILD;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        switch (viewType){
            case VT_HEAD:
                return new HeadViewHolder(layoutInflater.inflate(R.layout.list_item_home_search_restaurant,viewGroup,false));
            case VT_CHILD:
                return new ChildViewHolder(layoutInflater.inflate(R.layout.list_item_home_search_product,viewGroup,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Object object = objectList.get(i);
        if(object instanceof HomeSearchHead){
            HomeSearchHead homeSearchHead = (HomeSearchHead) object;
            HeadViewHolder headViewHolder = (HeadViewHolder) viewHolder;
            headViewHolder.tv.setText(homeSearchHead.getStore_name());
        }else{
            HomeSearchChild homeSearchChild = (HomeSearchChild) object;
            ChildViewHolder childViewHolder = (ChildViewHolder) viewHolder;
            childViewHolder.tv.setText(homeSearchChild.getItem_name());
        }
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    class HeadViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tv;

        public HeadViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tvRestaurantName);
            tv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            HomeSearchHead homeSearchHead = (HomeSearchHead) objectList.get(getAdapterPosition());
            listener.onClickStore(homeSearchHead);
        }
    }

    class ChildViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tv;

        public ChildViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tvItemName);
            tv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            HomeSearchHead clickedHomeSearchHead = null;
            HomeSearchChild clickedHomeSearchChild = (HomeSearchChild) objectList.get(getAdapterPosition());

            for(Object object :objectList){
                if(object instanceof HomeSearchHead){
                    HomeSearchHead homeSearchHead = (HomeSearchHead) object;
                    for(HomeSearchChild homeSearchChild : homeSearchHead.getHomeSearchChildList()){
                        if(homeSearchChild==clickedHomeSearchChild){
                            clickedHomeSearchHead = homeSearchHead;
                        }
                    }
                }
            }

            listener.onClickItem(clickedHomeSearchHead,clickedHomeSearchChild);
        }
    }
}
