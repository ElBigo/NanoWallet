package nanoapps.equensworldlie.com.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import nanoapps.equensworldlie.com.R;
import nanoapps.equensworldlie.com.model.DbManager;
import nanoapps.equensworldlie.com.model.User;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    DbManager db;
    Button logOutButton;
    TextView getCurrencyTextView;
    TextView transferFundsTextView;
    TextView claimTransactionsTextView;
    TextView balanceTextview;
    EditText getCurrencyEdittext;
    Button getCurrencyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        db = new DbManager(this);
        logOutButton = (Button)findViewById(R.id.log_out_button);
        getCurrencyTextView = (TextView) findViewById(R.id.get_currency_textview);
        getCurrencyEdittext = (EditText) findViewById(R.id.get_currency_edittext);
        getCurrencyButton = (Button) findViewById(R.id.get_currency_button);
        transferFundsTextView = (TextView) findViewById(R.id.transfer_funds);
        claimTransactionsTextView = (TextView) findViewById(R.id.claim_transactions);
        balanceTextview = (TextView) findViewById(R.id.balance_text_view);

        logOutButton.setOnClickListener(this);
        getCurrencyTextView.setOnClickListener(this);
        transferFundsTextView.setOnClickListener(this);
        claimTransactionsTextView.setOnClickListener(this);
        getCurrencyEdittext.setOnClickListener(this);
        getCurrencyButton.setOnClickListener(this);

        Intent login = getIntent();
        User user = (User)login.getSerializableExtra("user");

        balanceTextview.setText(String.valueOf(user.getBalance()));


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.log_out_button:
                Intent moveToLogin = new Intent((UserActivity.this), (LoginActivity.class));
                startActivity(moveToLogin);
                break;

            case R.id.get_currency_textview:
                getCurrencyEdittext.setVisibility(View.VISIBLE);
                getCurrencyButton.setVisibility(View.VISIBLE);

                break;

            case R.id.get_currency_edittext:
                break;

            case R.id.get_currency_button:
                Log.e("Good","On OK!");
                break;

            case R.id.transfer_funds:
                Intent moveToTransferFundsActivity = new Intent((UserActivity.this), (transferFundsActivity.class));
                startActivity(moveToTransferFundsActivity);
                break;
            case R.id.claim_transactions:
                Intent moveToClaimTransactionsActivity = new Intent((UserActivity.this), (claimTransactionsActivity.class));
                startActivity(moveToClaimTransactionsActivity);
                break;
        }
    }
}