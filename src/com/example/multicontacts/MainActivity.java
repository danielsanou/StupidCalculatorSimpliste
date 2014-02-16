package com.example.multicontacts;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MainActivity extends Activity implements OnTouchListener {
	    
    VueDessin mVue;

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        mVue = new VueDessin(this);
        mVue.setOnTouchListener(this);
        setContentView(mVue);
    }
    
    
   
    public boolean onTouch(View v, MotionEvent event) {
    	mVue.setEvent (event);
    	v.invalidate();
        return true;
    }    
}