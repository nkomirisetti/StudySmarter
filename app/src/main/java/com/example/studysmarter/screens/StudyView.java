package com.example.studysmarter.screens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.studysmarter.R;
import com.wajahatkarim3.easyflipview.EasyFlipView;

public class StudyView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_view);

        EasyFlipView easyFlipView = findViewById(R.id.easyFlipView);
        easyFlipView.setFlipDuration(1000);
        easyFlipView.setFlipEnabled(true);

    }
}
