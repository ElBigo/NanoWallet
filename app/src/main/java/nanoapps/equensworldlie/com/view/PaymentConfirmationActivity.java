package nanoapps.equensworldlie.com.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import nanoapps.equensworldlie.com.R;
import nanoapps.equensworldlie.com.controller.Request;
import nanoapps.equensworldlie.com.controller.RequestCallback;
import nanoapps.equensworldlie.com.model.DbManager;
import nanoapps.equensworldlie.com.model.SpecialUser;
import nanoapps.equensworldlie.com.model.User;

public class PaymentConfirmationActivity extends AppCompatActivity {

    DbManager db;
    TextView paymentTextview;
    EditText paymentEdittext;
    Button paymentButton;

    private static final String HOST_ADDRESS  = "http://192.168.1.19:7076";

    SpecialUser specialUser = new SpecialUser();
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmation);

        db = new DbManager(this);

        paymentTextview = (TextView) findViewById(R.id.payment_textview);
        paymentEdittext = (EditText) findViewById(R.id.payment_edittext);
        paymentButton = (Button) findViewById(R.id.payment_button);

        Intent cnfPayment = getIntent();


        specialUser = (SpecialUser) cnfPayment.getSerializableExtra("specialUser");

        paymentTextview.setText(specialUser.getRecipient());

        user.setUsername(specialUser.getUsername());
        user.setAccountId(specialUser.getAccountId());
        user.setWalletId(specialUser.getWalletId());


        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent moveToLogin = new Intent((PaymentConfirmationActivity.this), (LoginActivity.class));
                startActivity(moveToLogin);

                if(!paymentEdittext.getText().toString().matches("")){

                    long tsLong = System.currentTimeMillis()/1000;
                    String ts =  String.valueOf(tsLong);

                    Map<String,String > dataSendTransactionRequest = new HashMap<String, String>();
                    dataSendTransactionRequest.put("action","send");
                    dataSendTransactionRequest.put("wallet",specialUser.getWalletId());
                    dataSendTransactionRequest.put("source",specialUser.getAccountId());
                    dataSendTransactionRequest.put("destination",specialUser.getRecipient());
                    dataSendTransactionRequest.put("amount",paymentEdittext.getText().toString().trim());
                    dataSendTransactionRequest.put("id",ts);  // Unique ID needed for each transactions

                    new Request(dataSendTransactionRequest, new RequestCallback(){
                        @Override
                        public void run() {
                            super.run();

                            try {
                                JSONObject sendTransactionJsonResponse = new JSONObject(this.Response);
                                String blockId = sendTransactionJsonResponse.getString("block");

                                Toast.makeText(PaymentConfirmationActivity.this, "Block ID: "+blockId, Toast.LENGTH_LONG).show();

                                Intent userIntent = new Intent((PaymentConfirmationActivity.this), (UserActivity.class)).putExtra("user", user);
                                startActivity(userIntent);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }).execute(HOST_ADDRESS);
                }
                else {
                    Toast.makeText(PaymentConfirmationActivity.this, "Value Required", Toast.LENGTH_SHORT).show();

                    Intent userIntent = new Intent((PaymentConfirmationActivity.this), (UserActivity.class)).putExtra("user", user);
                    startActivity(userIntent);
                }
            }
        });

    }
}