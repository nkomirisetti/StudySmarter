package com.example.studysmarter.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.studysmarter.MainActivity;
import com.example.studysmarter.R;

public class StudyReview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_review);

        TextView tv = findViewById(R.id.summary_info);
        getIntent().getIntExtra("DECK_ID", -1);

        String output = "You took " +
                getIntent().getStringExtra("TIME_ELAPSED") +
                " seconds to complete studying this desk, and you took " +
                getIntent().getStringExtra("TIME_PER_CARD") +
                " seconds per card.  You were confident about " +
                getIntent().getStringExtra("YES") +
                " cards, so your total proficiency score is " +
                getIntent().getStringExtra("PROF") + ".";

        tv.setText(output);
    }

    @Override
    public void onBackPressed() {
        Intent studyReview = new Intent(this, MainActivity.class);
        startActivity(studyReview);
    }

    public void returnToHome(View view) {
        Intent studyReview = new Intent(this, MainActivity.class);
        startActivity(studyReview);
    }
}
