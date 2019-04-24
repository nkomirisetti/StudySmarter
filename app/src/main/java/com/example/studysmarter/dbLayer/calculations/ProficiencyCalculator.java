package com.example.studysmarter.dbLayer.calculations;


import android.util.Log;

public class ProficiencyCalculator {
    public static double CalculateProficiency(double rawProficiency, double averageTime) {
        double finalProf = 0;
        Log.i("Time", averageTime + "");
        Log.i("Prof R", rawProficiency + "");

        if (averageTime < 15) {
            finalProf = finalProf + 50;
            Log.i("Prof", finalProf + "");
        } else if (averageTime < 50) {
            finalProf = finalProf + (50 - averageTime);
        }

        if (rawProficiency > .95) {
            finalProf = finalProf + 50;
            Log.i("HUH", finalProf + "");
        } else {
            finalProf = finalProf + (rawProficiency * 50);
        }
        return finalProf;
    }
}
