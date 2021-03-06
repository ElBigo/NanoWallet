package nanoapps.equensworldlie.com.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
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
import nanoapps.equensworldlie.com.model.User;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    DbManager db;
    TextView logOutButton;
    TextView getCurrencyTextView;
    TextView transferFundsTextView;
    TextView claimTransactionsTextView;
    TextView balanceTextview;
    TextView myAccountTextview;
    EditText getCurrencyEdittext;
    TextView refreshBalance;
    Button getCurrencyButton;

    private static final String HOST_ADDRESS  = "http://192.168.1.19:7076";
    final Handler handler = new Handler();
    User user = new User();
    Dialog myDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        db = new DbManager(this);
        logOutButton = (TextView)findViewById(R.id.log_out_button);
        getCurrencyTextView = (TextView) findViewById(R.id.get_currency_textview);
        getCurrencyEdittext = (EditText) findViewById(R.id.get_currency_edittext);
        getCurrencyButton = (Button) findViewById(R.id.get_currency_button);
        transferFundsTextView = (TextView) findViewById(R.id.pay);
        //claimTransactionsTextView = (TextView) findViewById(R.id.claim_transactions);
        balanceTextview = (TextView) findViewById(R.id.balance_text_view);
        myAccountTextview = (TextView) findViewById(R.id.my_account);
        refreshBalance = (TextView) findViewById(R.id.refresh_balance);


        myDialog = new Dialog(this);

        myAccountTextview.setOnClickListener(this);
        logOutButton.setOnClickListener(this);
        getCurrencyTextView.setOnClickListener(this);
        transferFundsTextView.setOnClickListener(this);
        //claimTransactionsTextView.setOnClickListener(this);
        getCurrencyEdittext.setOnClickListener(this);
        getCurrencyButton.setOnClickListener(this);
        refreshBalance.setOnClickListener(this);

        Intent login = getIntent();
        user = (User)login.getSerializableExtra("user");

        balanceTextview.setText(user.getBalance());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.refresh_balance:


                Map<String, String> accountBalance = new HashMap<String, String>();

                accountBalance.put("action","account_balance");
                accountBalance.put("account",user.getAccountId());

                new Request(accountBalance, new RequestCallback(){

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
                }).execute(HOST_ADDRESS);
                break;

            case R.id.my_account:
                Intent accountDetails = new Intent((UserActivity.this), (AccountActivity.class)).putExtra("user", user);
                startActivity(accountDetails);
                break;

            case R.id.log_out_button:
                Intent moveToLogin = new Intent((UserActivity.this), (LoginActivity.class));
                startActivity(moveToLogin);
                break;
            case R.id.get_currency_textview:
                getCurrencyEdittext.setVisibility(View.VISIBLE);
                getCurrencyButton.setVisibility(View.VISIBLE);
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

                    Log.e("Wallet Admin",admin.getWalletId());

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
                                        }).execute(HOST_ADDRESS);
                                    }
                                }).execute(HOST_ADDRESS);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }).execute(HOST_ADDRESS);
                }
                break;
            case R.id.get_currency_edittext:
                break;

            case R.id.pay:
                Intent moveToPayActivity = new Intent((UserActivity.this), (PayActivity.class)).putExtra("user",user);
                startActivity(moveToPayActivity);
                break;
//            case R.id.claim_transactions:
//                Intent moveToClaimTransactionsActivity = new Intent((UserActivity.this), (PendingActivity.class));
//                startActivity(moveToClaimTransactionsActivity);
//                break;
        }
    }


    public void PopupPendingBlock(View view){

        Button proceedButton;
        TextView pendingBlockId;
        myDialog.setContentView(R.layout.popup_pending_block);

        pendingBlockId = (TextView) myDialog.findViewById(R.id.pending_block_id);
        proceedButton = (Button) myDialog.findViewById(R.id.proceed_button);

        Map<String,String > pendingBlockTransaction = new HashMap<String, String>();
        pendingBlockTransaction.put("action","pending");
        pendingBlockTransaction.put("account",user.getAccountId());
        pendingBlockTransaction.put("count","1");

        new Request(pendingBlockTransaction, new RequestCallback(){

            @Override
            public void run() {
                super.run();

                try {
                    JSONObject pendingBlockJsonResponse = new JSONObject(this.Response);
                    String blockId = pendingBlockJsonResponse.getString("blocks");

                    switch (blockId){

                        case "":
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    pendingBlockId.setText("No Pending Block!");
                                }
                            });
                            SystemClock.sleep(2000);
                            myDialog.dismiss();
                            break;
                        default:
                            blockId = blockId.replace("[\"","");
                            blockId = blockId.replace("\"]","");

                            pendingBlockId.setText(blockId);

                            // Receive block implementation
                            String finalBlockId = blockId;
                            proceedButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Map<String,String > receiveBlock = new HashMap<String, String>();
                                    receiveBlock.put("action","receive");
                                    receiveBlock.put("wallet",user.getWalletId());
                                    receiveBlock.put("account",user.getAccountId());
                                    receiveBlock.put("block", finalBlockId);

                                    new Request(receiveBlock, new RequestCallback(){

                                        @Override
                                        public void run() {
                                            super.run();

                                            try {
                                                JSONObject accountBalanceJson = new JSONObject(this.Response);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).execute(HOST_ADDRESS);

                                    myDialog.dismiss();
                                }
                            });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).execute(HOST_ADDRESS);
        myDialog.show();
    }

}