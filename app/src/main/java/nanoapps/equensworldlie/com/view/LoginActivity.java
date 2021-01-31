package nanoapps.equensworldlie.com.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity {

    EditText editUsername;
    EditText editPassword;
    Button loginButton;
    TextView registerTextView;
    DbManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DbManager(this);
        editUsername = (EditText) findViewById(R.id.edit_username);
        editPassword = (EditText) findViewById(R.id.edit_password);
        loginButton = (Button) findViewById(R.id.login_button);
        registerTextView = (TextView) findViewById(R.id.textview_register);
        User user = new User();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editUsername.getText().toString().trim();
                String password = editPassword.getText().toString().trim();


                boolean res = db.checkUser(username, password);
                JSONObject postData =new JSONObject();

                if(res==true){

                    user.setUsername(username);

                    Cursor cursor = db.getReadableDatabase().query("users_table", new String[] {"WALLET_ID", "ACCOUNT_ID", "PUBLIC_KEY"},"USERNAME = ?", new String[] {username}, null,null,null );

                    cursor.moveToFirst();
                        user.setWalletId(cursor.getString(0));
                        user.setAccountId(cursor.getString(1));
                        user.setpKey(cursor.getString(2));


                    Map<String, String> accountBalance = new HashMap<String, String>();

                    accountBalance.put("action","account_balance");
                    accountBalance.put("account",user.getAccountId());


                    new Request(accountBalance, new RequestCallback(){

                        @Override
                        public void run() {
                            super.run();

                            String balance = this.Response;

                            try {
                                JSONObject accountBalanceJson = new JSONObject(balance);

                                //TODO
                                // This casting modifies the Value of the Balance. to be review!
                                user.setBalance(accountBalanceJson.getString("balance"));

                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent userIntent = new Intent((LoginActivity.this), (UserActivity.class)).putExtra("user", user);
                                startActivity(userIntent);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }).execute("http://192.168.56.1:7076");
//
//                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
//                    Intent userIntent = new Intent((LoginActivity.this), (UserActivity.class)).putExtra("user", user);
//                    startActivity(userIntent);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent loginIntent = new Intent((LoginActivity.this), (RegisterActivity.class));
                startActivity(loginIntent);
            }
        });
    }
}