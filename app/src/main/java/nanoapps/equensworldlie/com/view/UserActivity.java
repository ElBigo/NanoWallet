package nanoapps.equensworldlie.com.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import nanoapps.equensworldlie.com.R;
import nanoapps.equensworldlie.com.model.DbManager;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    DbManager db;
    Button logOutButton;
    TextView getCurrencyTextView;
    TextView transferFundsTextView;
    TextView claimTransactionsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        db = new DbManager(this);
        logOutButton = (Button)findViewById(R.id.log_out_button);
        getCurrencyTextView = (TextView) findViewById(R.id.get_currency);
        transferFundsTextView = (TextView) findViewById(R.id.transfer_funds);
        claimTransactionsTextView = (TextView) findViewById(R.id.claim_transactions);

        logOutButton.setOnClickListener(this);
        getCurrencyTextView.setOnClickListener(this);
        transferFundsTextView.setOnClickListener(this);
        claimTransactionsTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.log_out_button:
                Intent moveToLogin = new Intent((UserActivity.this), (MainActivity.class));
                startActivity(moveToLogin);
                break;
            case R.id.get_currency:
                Intent moveToGetCurrencyActivity = new Intent((UserActivity.this), (getCurrencyActivity.class));
                startActivity(moveToGetCurrencyActivity);
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