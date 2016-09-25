package com.unitutoring.unitutoring.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.unitutoring.unitutoring.R;
import com.unitutoring.unitutoring.Utils;
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
        public ImageView portrait;

        public TutorListItemOnClickListener listener;
        public int position = -1;

        public TutorViewHolder(View itemView) {
            super(itemView);

            tutorDescTextView = (TextView) itemView.findViewById(R.id.tutorDescTextView);
            availabilityList = (LinearLayout) itemView.findViewById(R.id.availabilityList);
            courseList = (LinearLayout) itemView.findViewById(R.id.courseList);
            portrait = (ImageView) itemView.findViewById(R.id.portrait);
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

//        if (position % 2 == 0) {
//            holder.itemView.setBackgroundColor(Color.WHITE);
//        } else {
//            holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.background));
//        }

        holder.listener = mListener;
        holder.position = position;
        holder.tutorDescTextView.setText(tutor.firstname + " " + tutor.lastname.substring(0, 1));

        Picasso.with(holder.itemView.getContext())
                .load(tutor.photo)
                .resize(200,200)
                .transform(new Transformation() {
                    @Override
                    public Bitmap transform(Bitmap source) {
                        int size = Math.min(source.getWidth(), source.getHeight());

                        int x = (source.getWidth() - size) / 2;
                        int y = (source.getHeight() - size) / 2;

                        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
                        if (squaredBitmap != source) {
                            source.recycle();
                        }

                        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

                        Canvas canvas = new Canvas(bitmap);
                        Paint paint = new Paint();
                        BitmapShader shader = new BitmapShader(squaredBitmap,
                                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
                        paint.setShader(shader);
                        paint.setAntiAlias(true);

                        float r = size / 2f;
                        canvas.drawCircle(r, r, r, paint);

                        squaredBitmap.recycle();
                        return bitmap;
                    }

                    @Override
                    public String key() {
                        return "circular";
                    }
                }).centerInside()
                .into(holder.portrait);

        for (Course course :
                tutor.courses) {
            if (course.isMatched) {
                TextView courseTextView = new TextView(holder.itemView.getContext());
                courseTextView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                courseTextView.setText(course.courseId);
                courseTextView.setTextColor(Utils.colorForCourse(course.courseId));
                courseTextView.setTextSize(16);
                holder.courseList.addView(courseTextView);
            }
        }

        for (Availability availability :
                tutor.availability) {

            if (availability.isMatched) {

                TextView availabilityTextView = new TextView(holder.itemView.getContext());
                availabilityTextView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                availabilityTextView.setText(availability.dayOfWeek.substring(0, 1));
                availabilityTextView.setTextColor(Utils.colorForWeekday(availability.dayOfWeek));
                availabilityTextView.setTextSize(16);
                holder.availabilityList.addView(availabilityTextView);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mTutorList.size();
    }


}
