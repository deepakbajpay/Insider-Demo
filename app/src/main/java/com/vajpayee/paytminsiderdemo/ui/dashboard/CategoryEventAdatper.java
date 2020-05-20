package com.vajpayee.paytminsiderdemo.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vajpayee.paytminsiderdemo.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class CategoryEventAdatper extends RecyclerView.Adapter<CategoryEventAdatper.CategoryViewHolder> {
    Context context;
    JSONObject data;
    JSONArray groupArray;

    public void setData(JSONObject data) {
        this.data = data;
        groupArray = data.optJSONArray("groups");
        notifyDataSetChanged();
    }

    public CategoryEventAdatper(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.category_event_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        JSONArray eventsArray=null;
        if (position==0){
            //show featured list
            holder.categoryName.setText("Featured Events");
            eventsArray = data.optJSONArray("featured");
        }else{
            String categoryName = groupArray.optString(position-1);
            holder.categoryName.setText(categoryName);
            eventsArray = new JSONArray();

            JSONArray eventsInCategory = data.optJSONObject("list").optJSONObject("groupwiseList").optJSONArray(categoryName);

            for (int i=0;i<eventsInCategory.length();i++){
                eventsArray.put(data.optJSONObject("list").optJSONObject("masterList").optJSONObject(eventsInCategory.optString(i)));
            }
        }
        EventAdapter adapter = new EventAdapter(context,eventsArray);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false);
        holder.categoryRv.setLayoutManager(layoutManager);
        holder.categoryRv.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        if (data == null || groupArray== null)
            return 0;
        else return groupArray.length() + 1;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        RecyclerView categoryRv;
        TextView categoryName;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryRv = itemView.findViewById(R.id.categories_rv);
            categoryName = itemView.findViewById(R.id.event_type_tv);
        }
    }
}
