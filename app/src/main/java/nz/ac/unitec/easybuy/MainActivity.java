package nz.ac.unitec.easybuy;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_BARCODE_CAPTURE = 9001;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    System.out.println(barcode.displayValue);
                } else {
                    System.out.println("No barcode captured, intent data is null");
                }
            } else {
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /// Create BirdLocationClient
        ProductItemClient productItemClient = new ProductItemClient(this);

        /// Subscribe on BirdLocationAvailable event
        Fragment frMap = getFragmentManager().findFragmentById(R.id.fragment_map);
        productItemClient.setOnProductItemListener((ProductItemAvailableListener)frMap);

        findViewById(R.id.button_camera).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_camera) {
            // launch barcode activity.
            Intent intent = new Intent(this, BarcodeCaptureActivity.class);
            intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
            intent.putExtra(BarcodeCaptureActivity.UseFlash, false);

            startActivityForResult(intent, RC_BARCODE_CAPTURE);
        }
    }
}
