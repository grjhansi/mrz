package com.nurture.nurture_user.mrz;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.microblink.activity.ScanActivity;
import com.microblink.activity.ScanCard;
import com.microblink.detectors.DetectorSettings;
import com.microblink.hardware.camera.CameraType;
import com.microblink.recognizers.BaseRecognitionResult;
import com.microblink.recognizers.RecognitionResults;
import com.microblink.recognizers.settings.RecognitionSettings;
import com.microblink.recognizers.settings.RecognizerSettings;
import com.microblink.recognizers.settings.RecognizerSettingsUtils;
import com.microblink.util.Log;
import com.microblink.util.RecognizerCompatibility;
import com.microblink.util.RecognizerCompatibilityStatus;
import com.nurture.nurture_user.mrz.IdDetector.DetectorActivity;


public class MainActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 100;
    public static final int MY_BLINK_ID_REQUEST_CODE = 0x101;

    public static final String TAG = "BlinkIDDemo";
    TextView txtPrimaryId, txtSecId, txtGender, txtDob, txtExpiry, txtNation, txtDocNum;
    ImageView imgScanImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // check if BlinkID is supported on the device
        Button btnScan = (Button) findViewById(R.id.btnScan);
        txtDob = findViewById(R.id.txtDOB);
        txtDocNum = findViewById(R.id.txtDocNum);
        txtExpiry = findViewById(R.id.txtExpiry);
        txtGender = findViewById(R.id.txtGender);
        txtNation = findViewById(R.id.txtNation);
        txtPrimaryId = findViewById(R.id.txtPrimaryId);
        txtSecId = findViewById(R.id.txtSecId);
        imgScanImage = findViewById(R.id.imgScanImage);

        /*RecognizerCompatibilityStatus supportStatus = RecognizerCompatibility.getRecognizerCompatibilityStatus(this);
        if (supportStatus == RecognizerCompatibilityStatus.RECOGNIZER_SUPPORTED) {
            btnScan.setEnabled(true);
        } else {
            btnScan.setEnabled(false);
            Toast.makeText(this, "BlinkID is not supported! Reason: " + supportStatus.name(), Toast.LENGTH_LONG).show();
        }*/

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void myClickHandler(View view) {
        // Intent for MyScanActivity
        final Intent intent = new Intent(this, MyScanActivity.class);

        startActivityForResult(intent, MY_BLINK_ID_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // onActivityResult is called whenever we are returned from activity started
        // with startActivityForResult. We need to check request code to determine
        // that we have really returned from BlinkID activity.
        if (requestCode == MY_BLINK_ID_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {

                txtPrimaryId.setText(data.getStringExtra("PrimaryId"));
                txtSecId.setText(data.getStringExtra("SecId"));
                txtNation.setText(data.getStringExtra("Nationality"));
                txtGender.setText(data.getStringExtra("Gender"));
                txtExpiry.setText(data.getStringExtra("Expiry"));
                txtDocNum.setText(data.getStringExtra("DocNum"));
                txtDob.setText(data.getStringExtra("DOB"));
                byte[] byteArray = data.getByteArrayExtra("Image");
                Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                imgScanImage.setImageBitmap(bmp);

                //Log.i("Data ", data.getStringExtra("PrimaryId"));
                //Toast.makeText(getBaseContext(), data.getStringExtra("PrimaryId"), Toast.LENGTH_LONG).show();


          /*      // depending on settings, we may have multiple scan results.
                // we first need to obtain recognition results
                RecognitionResults results = data.getParcelableExtra(ScanActivity.EXTRAS_RECOGNITION_RESULTS);
                BaseRecognitionResult[] resArray = null;
                if (results != null) {

                    // get array of recognition results
                    resArray = results.getRecognitionResults();

                }
                if (resArray != null) {
                    Log.i(TAG, "Data count: " + resArray.length);
                    int i = 1;

                    for(BaseRecognitionResult res : resArray) {
                        Log.i(TAG, "Data #" + Integer.valueOf(i++).toString());

                        // Each element in resultArray inherits BaseRecognitionResult class and
                        // represents the scan result of one of activated recognizers that have
                        // been set up.

                        res.log();
                    }

                } else {
                    Log.e(TAG, "Unable to retrieve recognition data!");
                }

                //data.setComponent(new ComponentName(this, ResultActivity.class));
                //startActivity(data);
                Log.d("Result",resArray.toString());*/
            }
        }
    }


}
