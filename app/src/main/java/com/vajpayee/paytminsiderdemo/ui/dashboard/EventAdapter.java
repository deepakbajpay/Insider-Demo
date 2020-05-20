package com.vajpayee.paytminsiderdemo.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.vajpayee.paytminsiderdemo.R;
import com.vajpayee.paytminsiderdemo.ui.util.Utilities;

import org.json.JSONArray;
import org.json.JSONObject;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    Context context;
    private JSONArray eventsArray;

    public EventAdapter(Context context, JSONArray eventsArray) {
        this.context = context;
        this.eventsArray = eventsArray;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EventViewHolder(LayoutInflater.from(context).inflate(R.layout.event_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        JSONObject eventJsonObject = eventsArray.optJSONObject(position);

        holder.title.setText(eventJsonObject.optString("name"));
        holder.time.setText(eventJsonObject.optString("venue_date_string"));
        holder.venue.setText(eventJsonObject.optString("venue_name"));
        holder.fee.setText("\u20B9"+eventJsonObject.optString("price_display_string"));

        holder.itemView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int width = holder.eventImage.getMeasuredWidth();
        int height = holder.eventImage.getMeasuredHeight();
        System.out.println("EventAdapter.onBindViewHolder "+width +" "+height );

        Picasso.get().load(eventJsonObject.optString("horizontal_cover_image")).resize(width,height).into(holder.eventImage);

//        Utilities.setImage(holder.eventImage, eventJsonObject.optString("horizontal_cover_image"), R.drawable.ic_launcher_background);
    }

    @Override
    public int getItemCount() {
        if (eventsArray == null)
            return 0;
        return eventsArray.length();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        ImageView eventImage;

        TextView title,time,venue,fee;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventImage = itemView.findViewById(R.id.event_iv);
            title = itemView.findViewById(R.id.event_title_tv);
            time = itemView.findViewById(R.id.event_time_tv);
            venue = itemView.findViewById(R.id.event_venue_tv);
            fee = itemView.findViewById(R.id.event_fee_tv);
        }
    }
}
