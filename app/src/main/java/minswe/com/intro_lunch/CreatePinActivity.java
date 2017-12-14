package minswe.com.intro_lunch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;

public class CreatePinActivity extends AppCompatActivity {

    private  static final  int PIN_SIZE=6;
    EditText [] pin_text;
    private  static  final String TAG="PIN";
    PinLockView mPinLockView;
    PinIndicator pinIndicator;
    Button btnCreate;
    String result_pin="";
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_pin_layout);
        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
        mPinLockView.setPinLockListener(mPinLockListener);
        btnCreate= findViewById(R.id.btn_create);
        mHandler = new Handler();
        pinIndicator= findViewById(R.id.pin_indicator);
        btnCreate.setEnabled(false);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Runnable mPendingRunnable = new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(CreatePinActivity.this, HomeActivity.class));
                        finish();
                    }

                };

                if (mPendingRunnable != null) {
                    mHandler.post(mPendingRunnable);
                }

            }
        });
        //mPinLockView.attachIndicatorDots(pinIndicator)/;
    }

    private PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            Log.d(TAG, "Pin complete: " + pin);
            result_pin=pin;
            pinIndicator.updateDot(PIN_SIZE);
            mPinLockView.setEnabled(false);
            btnCreate.setEnabled(true);
        }

        @Override
        public void onEmpty() {
            Log.d(TAG, "Pin empty");
            pinIndicator.updateDot(0);
            result_pin="";
        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {
            result_pin= intermediatePin;
            Log.d(TAG, "Pin changed, new length " + pinLength + " with intermediate pin " + intermediatePin);
            pinIndicator.updateDot(pinLength);

        }
    };

}
