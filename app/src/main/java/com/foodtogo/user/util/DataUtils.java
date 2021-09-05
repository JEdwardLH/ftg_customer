package com.foodtogo.user.util;

import android.content.Context;

import com.foodtogo.user.R;
import com.foodtogo.user.model.allrestaurant.CategoryList;
import com.foodtogo.user.model.allrestaurant.TabbedMenu;
import com.foodtogo.user.ui.allrestaurant.enums.CategoryType;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataUtils {

    private final static String NEW_ORDER = "New Order";
    private final static String ACCEPTED = "Accepted";
    private final static String PREPARE = "Preparing for deliver";
    private final static String DISPATCHED = "Dispatched";
    private final static String STARTED = "Started";
    private final static String ARRIVED = "Arrived";
    private final static String DELIVERED = "Delivered";
    private final static String FAILED = "Failed";

    //Show item width
    public final static String KEY_TOP_OFFER = "topOffer";
    public final static String KEY_SPECIAL_OFFER = "specialOffer";
    public final static String KEY_COMBO = "Combo";
    public final static String KEY_HALAL = "Halal";

    //Show category
    public final static String KEY_ITEM_TYPE = "itemType";

    //Show available time
    public final static String KEY_BREAK_FAST = "BreakFast";
    public final static String KEY_BRUNCH = "Brunch";
    public final static String KEY_LUNCH = "Lunch";
    public final static String KEY_SUPPER = "Supper";
    public final static String KEY_DINNER = "Dinner";

    public final static String KEY_VEG = "veg";
    public final static String KEY_NON_VEG = "nonveg";


    public static JSONObject getDefaultFilter(String time) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_TOP_OFFER, false);
            jsonObject.put(KEY_COMBO, false);
            jsonObject.put(KEY_HALAL, false);
            jsonObject.put(KEY_SPECIAL_OFFER, false);

            jsonObject.put(KEY_ITEM_TYPE, "");

            jsonObject.put(KEY_BREAK_FAST, time.equals(KEY_BREAK_FAST));
            jsonObject.put(KEY_BRUNCH, time.equals(KEY_BRUNCH));
            jsonObject.put(KEY_LUNCH, time.equals(KEY_LUNCH));
            jsonObject.put(KEY_SUPPER, time.equals(KEY_SUPPER));
            jsonObject.put(KEY_DINNER, time.equals(KEY_DINNER));
        } catch (Exception e) {
            return null;
        }
        return jsonObject;
    }



    public static ArrayList<CategoryList> getFormatCategory(Context ctx, List<CategoryList> categoryLists) {
        ArrayList<CategoryList> categoryLists1 = new ArrayList<>();
        for (int index = 0; index < categoryLists.size(); index++) {
            categoryLists.get(index).setCategoryType(CategoryType.CUISINES);
            categoryLists.get(index).setSelected(true);
            categoryLists1.add(categoryLists.get(index));
        }
        categoryLists1.add(new CategoryList(1, ctx.getResources().getString(R.string.show_restaurant_with), CategoryType.FOOD_TYPE, true));
      //  categoryLists1.add(new CategoryList(3, ctx.getResources().getString(R.string.most_preferable_time), CategoryType.AVAILABLE_TIME, true));
        return categoryLists1;
    }

    public static JSONObject getRestaurantDefaultFilter() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_ITEM_TYPE, "12");
            jsonObject.put(KEY_BREAK_FAST, true);
            jsonObject.put(KEY_LUNCH, true);
            jsonObject.put(KEY_DINNER, true);
        } catch (Exception e) {
            return null;
        }
        return jsonObject;
    }

    public static JSONObject convertString2JSON(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject;
        } catch (Exception e) {
            return null;
        }
    }

    public static ArrayList<TabbedMenu> getDefaultMenu(Context ctx) {
        ArrayList<TabbedMenu> categoryLists1 = new ArrayList<>();
        categoryLists1.add(new TabbedMenu(1, "1", ctx.getResources().getString(R.string.all), true));
        categoryLists1.add(new TabbedMenu(2, "1", ctx.getResources().getString(R.string.top_offer), false));
        categoryLists1.add(new TabbedMenu(3, "1", ctx.getResources().getString(R.string.delivery_time), false));
        categoryLists1.add(new TabbedMenu(4, "1", ctx.getResources().getString(R.string.ratings), false));
        //categoryLists1.add(new TabbedMenu(5, "1", ctx.getResources().getString(R.string.halal), false));
        return categoryLists1;
    }


    public static int getStatus(String ordTitle) {
        int resourceId = 0;
        switch (ordTitle) {
            case NEW_ORDER:
                resourceId = R.drawable.ic_new_order;
                break;
            case ACCEPTED:
                resourceId = R.drawable.ic_order_recieved;
                break;
            case PREPARE:
                resourceId = R.drawable.ic_order_confirmed;
                break;
            case DISPATCHED:
                resourceId = R.drawable.ic_track_dispatched;
                break;
            case STARTED:
                resourceId = R.drawable.ic_track_started;
                break;
            case ARRIVED:
                resourceId = R.drawable.ic_track_arrived;
                break;
            case DELIVERED:
                resourceId = R.drawable.ic_track_delivered;
                break;
            case FAILED:
                resourceId = R.drawable.ic_failed_order;
                break;

        }
        return resourceId;
    }


}
