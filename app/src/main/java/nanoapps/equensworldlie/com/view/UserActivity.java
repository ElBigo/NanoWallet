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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import nanoapps.equensworldlie.com.R;
import nanoapps.equensworldlie.com.controller.Request;
import nanoapps.equensworldlie.com.controller.RequestCallback;
import nanoapps.equensworldlie.com.model.DbManager;
import nanoapps.equensworldlie.com.model.User;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    DbManager db;
    Button logOutButton;
    TextView getCurrencyTextView;
    TextView transferFundsTextView;
    TextView claimTransactionsTextView;
    TextView balanceTextview;
    TextView myAccountTextview;
    EditText getCurrencyEdittext;
    Button getCurrencyButton;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        db = new DbManager(this);
        logOutButton = (Button)findViewById(R.id.log_out_button);
        getCurrencyTextView = (TextView) findViewById(R.id.get_currency_textview);
        getCurrencyEdittext = (EditText) findViewById(R.id.get_currency_edittext);
        getCurrencyButton = (Button) findViewById(R.id.get_currency_button);
        transferFundsTextView = (TextView) findViewById(R.id.pay);
        claimTransactionsTextView = (TextView) findViewById(R.id.claim_transactions);
        balanceTextview = (TextView) findViewById(R.id.balance_text_view);
        myAccountTextview = (TextView) findViewById(R.id.my_account);

        myAccountTextview.setOnClickListener(this);
        logOutButton.setOnClickListener(this);
        getCurrencyTextView.setOnClickListener(this);
        transferFundsTextView.setOnClickListener(this);
        claimTransactionsTextView.setOnClickListener(this);
        getCurrencyEdittext.setOnClickListener(this);
        getCurrencyButton.setOnClickListener(this);

        Intent login = getIntent();
        user = (User)login.getSerializableExtra("user");

        balanceTextview.setText(String.valueOf(user.getBalance()));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.my_account:
                Intent accountDetails = new Intent((UserActivity.this), (AccountActivity.class));
                startActivity(accountDetails);
                break;

            case R.id.log_out_button:
                Intent moveToLogin = new Intent((UserActivity.this), (LoginActivity.class));
                startActivity(moveToLogin);
                break;
            case R.id.get_currency_textview:
                getCurrencyEdittext.setVisibility(View.VISIBLE);
                //getCurrencyButton.setVisibility(View.VISIBLE);
                break;

            case R.id.get_currency_button:

                if(!getCurrencyEdittext.getText().toString().matches("")){
                    getCurrencyButton.setVisibility(View.VISIBLE);

                    User admin = new User();

                    Cursor cursorAdmin = db.getReadableDatabase().query("users_table", new String[] {"WALLET_ID", "ACCOUNT_ID"},"USERNAME =?", new String[] {"admin"}, null,null,null );
                    cursorAdmin.moveToFirst();
                    admin.setWalletId(cursorAdmin.getString(0));
                    admin.setAccountId(cursorAdmin.getString(1));

                    long tsLong = System.currentTimeMillis()/1000;
                    String ts =  String.valueOf(tsLong);

                    Map<String,String > dataSendTransactionRequest = new HashMap<String, String>();
                    dataSendTransactionRequest.put("action","send");
                    dataSendTransactionRequest.put("wallet",admin.getWalletId());
                    dataSendTransactionRequest.put("source",admin.getAccountId());
                    dataSendTransactionRequest.put("destination",user.getAccountId());
                    dataSendTransactionRequest.put("amount",getCurrencyEdittext.getText().toString().trim());
                    dataSendTransactionRequest.put("id",ts);  // Unique ID needed for each transactions

                    new Request(dataSendTransactionRequest, new RequestCallback(){

                        @Override
                        public void run() {
                            super.run();

                            try {
                                JSONObject sendTransactionJsonResponse = new JSONObject(this.Response);
                                String blockId = sendTransactionJsonResponse.getString("block");
                                Map<String,String> dataReceiveTransaction = new HashMap<String, String>();

                                dataReceiveTransaction.put("action","receive");
                                dataReceiveTransaction.put("wallet",user.getWalletId());
                                dataReceiveTransaction.put("account",user.getAccountId());
                                dataReceiveTransaction.put("block",blockId);

                                new Request(dataReceiveTransaction, new RequestCallback(){

                                    @Override
                                    public void run() {
                                        super.run();

                                        Map<String, String> accountBalance = new HashMap<String, String>();

                                        accountBalance.put("action","account_balance");
                                        accountBalance.put("account",user.getAccountId());

                                        new Request(accountBalance, new RequestCallback(){
                                            final Handler handler = new Handler();

                                            @Override
                                            public void run() {
                                                super.run();

                                                try {
                                                    JSONObject accountBalanceJson = new JSONObject(this.Response);
                                                    handler.post(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            try {
                                                                balanceTextview.setText(accountBalanceJson.getString("balance"));
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    });
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).execute("http://192.168.56.1:7076");
                                    }
                                }).execute("http://192.168.56.1:7076");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }).execute("http://192.168.56.1:7076");
                }
                break;
            case R.id.get_currency_edittext:
                break;

            case R.id.pay:
                Intent moveToPayActivity = new Intent((UserActivity.this), (PayActivity.class)).putExtra("username",user.getUsername());
                startActivity(moveToPayActivity);
                break;
            case R.id.claim_transactions:
                Intent moveToClaimTransactionsActivity = new Intent((UserActivity.this), (claimTransactionsActivity.class));
                startActivity(moveToClaimTransactionsActivity);
                break;
        }
    }
}