package nanoapps.equensworldlie.com.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import nanoapps.equensworldlie.com.R;
import nanoapps.equensworldlie.com.model.DbManager;

public class rechargeActivity extends AppCompatActivity implements View.OnClickListener  {


    DbManager db;
    Button logOutButton;
    Button checkOutButton;
    TextView myAccountTextView;
    TextView transferFundsTextView;
    TextView claimTransactionsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        db = new DbManager(this);
        logOutButton = (Button)findViewById(R.id.log_out_button);
        transferFundsTextView = (TextView) findViewById(R.id.transfer_funds);
        claimTransactionsTextView = (TextView) findViewById(R.id.claim_transactions);
        myAccountTextView = (TextView) findViewById(R.id.my_account);
        checkOutButton = (Button) findViewById(R.id.check_out);

        logOutButton.setOnClickListener(this);
        transferFundsTextView.setOnClickListener(this);
        claimTransactionsTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.log_out_button:
                Intent moveToLogin = new Intent((this), (LoginActivity.class));
                startActivity(moveToLogin);
                break;
            case R.id.transfer_funds:
                Intent moveToTransferFundsActivity = new Intent((this), (transferFundsActivity.class));
                startActivity(moveToTransferFundsActivity);
                break;
            case R.id.claim_transactions:
                Intent moveToClaimTransactionsActivity = new Intent((this), (claimTransactionsActivity.class));
                startActivity(moveToClaimTransactionsActivity);
                break;
            case R.id.check_out:
                // method to get tokens from hardcoded nano test instance
                break;
        }
    }
}