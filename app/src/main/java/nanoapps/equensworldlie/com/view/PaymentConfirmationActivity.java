package nanoapps.equensworldlie.com.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
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
import nanoapps.equensworldlie.com.model.User;

public class PaymentConfirmationActivity extends AppCompatActivity {

    DbManager db;
    TextView paymentTextview;
    EditText paymentEdittext;
    Button paymentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmation);

        db = new DbManager(this);

        paymentTextview = (TextView) findViewById(R.id.payment_textview);
        paymentEdittext = (EditText) findViewById(R.id.payment_edittext);
        paymentButton = (Button) findViewById(R.id.payment_button);

        Intent cnfPayment = getIntent();

        /* infoTx[0] = sender username
           infoTx[1] = "receiver account ID" */
        String[] infoTx = (String[]) cnfPayment.getSerializableExtra("infoTx");
        paymentTextview.setText(infoTx[1]);


        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent moveToLogin = new Intent((PaymentConfirmationActivity.this), (LoginActivity.class));
                startActivity(moveToLogin);

                if(!paymentEdittext.getText().toString().matches("")){

//                    User sender = new User();
//
//                    Cursor cursorAdmin = db.getReadableDatabase().query("users_table", new String[] {"WALLET_ID", "ACCOUNT_ID"},"USERNAME =?", new String[] {infoTx[0]}, null,null,null );
//                    cursorAdmin.moveToFirst();
//                    sender.setWalletId(cursorAdmin.getString(0));
//                    sender.setAccountId(cursorAdmin.getString(1));
//
//                    long tsLong = System.currentTimeMillis()/1000;
//                    String ts =  String.valueOf(tsLong);
//
//                    Map<String,String > dataSendTransactionRequest = new HashMap<String, String>();
//                    dataSendTransactionRequest.put("action","send");
//                    dataSendTransactionRequest.put("wallet",sender.getWalletId());
//                    dataSendTransactionRequest.put("source",sender.getAccountId());
//                    dataSendTransactionRequest.put("destination",infoTx[1]);
//                    dataSendTransactionRequest.put("amount",paymentEdittext.getText().toString().trim());
//                    dataSendTransactionRequest.put("id",ts);  // Unique ID needed for each transactions

//                    new Request(dataSendTransactionRequest, new RequestCallback(){
//                        @Override
//                        public void run() {
//                            super.run();
//
//                            final Handler handler = new Handler();
//
//                            try {
//                                JSONObject sendTransactionJsonResponse = new JSONObject(this.Response);
//                                String blockId = sendTransactionJsonResponse.getString("block");
//
//                                // Faire un TOAST pour montrer le blockId généré
////                                Toast.makeText(PaymentConfirmationActivity.this, blockId, Toast.LENGTH_SHORT).show();
//
//                                handler.post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Toast.makeText(PaymentConfirmationActivity.this, blockId, Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                                // save that BlockID in a database with the accountId for a pending transaction
//                                // this.getWritableDatabase();
//
//
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//
//                    }).execute("http://192.168.56.1:7076");
//                }
//                else {
//                    Toast.makeText(PaymentConfirmationActivity.this, "Enter a Value", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}