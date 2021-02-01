package nanoapps.equensworldlie.com.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nanoapps.equensworldlie.com.R;
import nanoapps.equensworldlie.com.model.SpecialUser;
import nanoapps.equensworldlie.com.model.User;

public class PayActivity extends AppCompatActivity {

    Button scanQrCodeButton;
    TextView accountIdTextview;
    final Handler handler = new Handler();

    User user = new User();
    SpecialUser specialUser = new SpecialUser();

    private static final int CAMERA_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);


        Intent payment = getIntent();
        user = (User) payment.getSerializableExtra("user");

        specialUser.setUsername(user.getUsername());
        specialUser.setAccountId(user.getAccountId());
        specialUser.setWalletId(user.getWalletId());

        scanQrCodeButton = (Button) findViewById(R.id.scan_code_button);

        scanQrCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent moveToConfirmation = new Intent((PayActivity.this), (PaymentConfirmationActivity.class));
//                startActivity(moveToConfirmation);

                /* On on click button, first check if device version is greater or equal to android 6
                   then check the permission granted, if ok, call the camera scanner*/
                if(Build.VERSION.SDK_INT>=23){
                    if(checkPermission((Manifest.permission.CAMERA))){
                        openScanner();
                    }
                    else{
                        //same permission passed, also the camera code
                        requestPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
                    }

                }
                else {
                    openScanner();
                }
            }
        });
    }


    // Permission is granted -> open the camera to read QR code
    private void openScanner(){
        new IntentIntegrator(this).initiateScan();
    }

    // parsing the data read on QR code
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null){
            if(result.getContents() == null){
                Toast.makeText(this, "Empty", Toast.LENGTH_LONG).show();

//                Intent moveToUserActivity = new Intent((this), (UserActivity.class));
//                startActivity(moveToUserActivity);

            }
            else{
                // content store in "result.getcontents()"

                specialUser.setRecipient(result.getContents());
                Toast.makeText(this, "QR_Value: "+result.getContents(), Toast.LENGTH_LONG).show();

                Intent moveToConfirmation = new Intent((this), (PaymentConfirmationActivity.class)).putExtra("specialUser", specialUser);
                startActivity(moveToConfirmation);
            }
        }
        else{
            Toast.makeText(this, "Empty", Toast.LENGTH_LONG).show();

        }
    }

    /*Method for check and Request permission*/
    private boolean checkPermission(String permission){

        int result = ContextCompat.checkSelfPermission(this, permission);

        // return true if permission granted
        if(result == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else{return false;}
    }

    // requesting permission
    private void requestPermission(String permission, int code){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, permission)){

        }
        else {
            ActivityCompat.requestPermissions(this, new String[] {permission}, code);
        }
    }
    //Method for opening Camera Scanner if permission successful or failed

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case CAMERA_PERMISSION_CODE:
                // Camera Scanner called when permission granted
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

                }

        }
    }
}