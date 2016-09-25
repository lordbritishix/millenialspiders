package com.unitutoring.unitutoring.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unitutoring.unitutoring.R;
import com.unitutoring.unitutoring.models.Availability;
import com.unitutoring.unitutoring.models.Course;
import com.unitutoring.unitutoring.models.Tutor;

import java.util.List;

/**
 * Created by rickychang on 2016-09-24.
 */

public class TutorListAdapter extends RecyclerView.Adapter<TutorListAdapter.TutorViewHolder> {
    public interface TutorListItemOnClickListener {
        void onClicked(int position);
    }

    public static class TutorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tutorDescTextView;
        public LinearLayout availabilityList;
        public LinearLayout courseList;

        public TutorListItemOnClickListener listener;
        public int position = -1;

        public TutorViewHolder(View itemView) {
            super(itemView);

            tutorDescTextView = (TextView) itemView.findViewById(R.id.tutorDescTextView);
            availabilityList = (LinearLayout) itemView.findViewById(R.id.availabilityList);
            courseList = (LinearLayout) itemView.findViewById(R.id.courseList);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onClicked(position);
            }
        }
    }

    private List<Tutor> mTutorList;
    private TutorListItemOnClickListener mListener;

    public TutorListAdapter(List<Tutor> list, TutorListItemOnClickListener listener) {
        mTutorList = list;
        mListener = listener;
    }

    @Override
    public TutorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tutor_tile_view, parent, false);

        TutorViewHolder viewHolder = new TutorViewHolder(itemView);
        itemView.setOnClickListener(viewHolder);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TutorViewHolder holder, int position) {
        Tutor tutor = mTutorList.get(position);

        holder.listener = mListener;
        holder.position = position;
        holder.tutorDescTextView.setText(tutor.firstname + " " + tutor.lastname.substring(0, 1));

        for (Course course :
                tutor.courses) {
            if (course.isMatched) {
                TextView courseTextView = new TextView(holder.itemView.getContext());
                courseTextView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                courseTextView.setText(course.courseId);
                courseTextView.setTextColor(Color.WHITE);
                courseTextView.setTextSize(courseTextView.getContext().getResources().getDimension(R.dimen.text_size_medium));
                holder.courseList.addView(courseTextView);
            }
        }

        for (Availability availability :
                tutor.availability) {

            if (availability.isMatched) {

                TextView availabilityTextView = new TextView(holder.itemView.getContext());
                availabilityTextView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                availabilityTextView.setText(availability.dayOfWeek.substring(0, 1));
                availabilityTextView.setTextColor(Color.WHITE);
                availabilityTextView.setTextSize(availabilityTextView.getContext().getResources().getDimension(R.dimen.text_size_large));
                holder.availabilityList.addView(availabilityTextView);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mTutorList.size();
    }


}
