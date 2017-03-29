package com.lyf.slidet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lyf.slidetimeaxis.XLHStepView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        XLHStepView view=(XLHStepView) findViewById(R.id.zidingyi);
        view.setStepChangedListener(new XLHStepView.OnStepChangedListener() {

            @Override
            public void onStepChanged(int currentStep) {
                Log.i("i","change:"+currentStep);
            }
        });
    }
}
