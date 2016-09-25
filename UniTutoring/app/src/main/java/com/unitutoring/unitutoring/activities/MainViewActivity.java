package com.unitutoring.unitutoring.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.squareup.otto.Subscribe;
import com.unitutoring.unitutoring.BackendSingleton;
import com.unitutoring.unitutoring.R;
import com.unitutoring.unitutoring.UserSingleton;
import com.unitutoring.unitutoring.adapters.TutorListAdapter;
import com.unitutoring.unitutoring.events.MatchEvent;
import com.unitutoring.unitutoring.models.Tutor;

import java.util.ArrayList;
import java.util.List;

public class MainViewActivity extends BaseActivity implements TutorListAdapter.TutorListItemOnClickListener {
    private static final String TAG = "MainViewActivity";
    private List<Tutor> mList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private TutorListAdapter mTutorListViewAdapter;
    private StaggeredGridLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        mTutorListViewAdapter = new TutorListAdapter(mList, this);
        mRecyclerView = (RecyclerView) findViewById(R.id.tutorList);
        mRecyclerView.setAdapter(mTutorListViewAdapter);

        mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        BackendSingleton.getInstance().getTutorMatches(UserSingleton.getInstance().getUser().email);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClicked(int position) {
        Log.d(TAG, "Clicked " + position);

        Tutor tutor = mList.get(position);
        Intent intent = new Intent(this, TutorDetailActivity.class);
        String jsonString = tutor.toJSONString();
        intent.putExtra("tutor", jsonString);
        startActivity(intent);
    }

    @Subscribe
    public void onMatch(MatchEvent event) {
        if (event.isSuccessful) {
            Log.d(TAG, "onMatch: " + event);
            mList.clear();
            mList.addAll(event.tutorList);
            mTutorListViewAdapter.notifyDataSetChanged();
        } else {
            Log.e(TAG, "onMatch: " + event);
        }
    }
}
