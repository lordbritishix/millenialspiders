package com.unitutoring.unitutoring.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.unitutoring.unitutoring.R;
import com.unitutoring.unitutoring.Utils;
import com.unitutoring.unitutoring.models.Availability;
import com.unitutoring.unitutoring.models.Course;
import com.unitutoring.unitutoring.models.Tutor;

public class TutorDetailActivity extends BaseActivity {

    private static final String TAG = "TutorDetailActivity";
    private Tutor mTutor;

    private ImageView mPortrait;
    private TextView mEmailTextView;
    private TextView mName;
    private LinearLayout mAvailabilityLayout;
    private LinearLayout mCourseListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_detail);

        Gson gson = new Gson();
        mTutor = gson.fromJson(getIntent().getStringExtra("tutor"), Tutor.class);
        Log.d(TAG, "found Tutor: " + mTutor);

        mPortrait = (ImageView) findViewById(R.id.portrait);
        mEmailTextView = (TextView) findViewById(R.id.emailTextView);
        mName = (TextView) findViewById(R.id.name);
        mAvailabilityLayout = (LinearLayout) findViewById(R.id.availabilityLayout);
        mCourseListLayout = (LinearLayout) findViewById(R.id.courseLayout);

        setupPortrait();

        mEmailTextView.setText(mTutor.email);
        mName.setText(mTutor.firstname + " " + mTutor.lastname);

        setupCourses();

        setupAvailability();
    }

    private void setupPortrait() {
        Picasso.with(this)
                .load(mTutor.photo)
                .resize(400, 400)
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
                })
                .centerInside()
                .into(mPortrait);
    }

    private void setupAvailability() {
        for (Availability availability :
                mTutor.availability) {

            if (availability.isMatched) {

                TextView availabilityTextView = new TextView(this);
                availabilityTextView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                availabilityTextView.setText(availability.dayOfWeek);
                availabilityTextView.setTextColor(Utils.colorForWeekday(availability.dayOfWeek));
//                availabilityTextView.setTextSize(availabilityTextView.getContext().getResources().getDimensionPixelSize(R.dimen.text_size_small));
                mAvailabilityLayout.addView(availabilityTextView);
            }
        }
    }

    private void setupCourses() {
        for (Course course :
                mTutor.courses) {
            if (course.isMatched) {
                TextView courseTextView = new TextView(this);
                courseTextView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                courseTextView.setText(course.courseId);
                courseTextView.setTextColor(Utils.colorForCourse(course.courseId));
//                courseTextView.setTextSize(courseTextView.getContext().getResources().getDimensionPixelSize(R.dimen.text_size_small));
                mCourseListLayout.addView(courseTextView);
            }
        }
    }
}
