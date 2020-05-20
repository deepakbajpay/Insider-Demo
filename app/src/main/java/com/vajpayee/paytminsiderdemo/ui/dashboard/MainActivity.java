package com.vajpayee.paytminsiderdemo.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vajpayee.paytminsiderdemo.R;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements DashboardContract.View {

    DashboardPresenterImpl dashboardPresenter;
    RecyclerView eventsRv;
    ProgressBar progressBar;
    CategoryEventAdatper adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        dashboardPresenter = new DashboardPresenterImpl(this,new DashboardInterceptImpl(),this);
        eventsRv = findViewById(R.id.events_rv);
        adapter = new CategoryEventAdatper(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        eventsRv.setLayoutManager(layoutManager);
        eventsRv.setAdapter(adapter);

        dashboardPresenter.getEvents("delhi");

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void populateEvents(JSONObject jsonObject) {
        System.out.println("MainActivity.populateEvents "+jsonObject);
        adapter.setData(jsonObject);

    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }
}
