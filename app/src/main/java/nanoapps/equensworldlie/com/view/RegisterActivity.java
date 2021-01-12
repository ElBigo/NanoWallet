package nanoapps.equensworldlie.com.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.*;
//import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import nanoapps.equensworldlie.com.R;
import nanoapps.equensworldlie.com.controller.Request;
import nanoapps.equensworldlie.com.controller.RequestCallback;
import nanoapps.equensworldlie.com.model.DbManager;
import nanoapps.equensworldlie.com.model.User;

public class RegisterActivity extends AppCompatActivity{

    DbManager db;
    EditText editUsername;
    EditText editPassword;
    EditText confirmPassword;
    Button registerButton;
    TextView loginTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        db = new DbManager(this);
        editUsername = (EditText) findViewById(R.id.edit_username);
        editPassword = (EditText) findViewById(R.id.enter_password);
        confirmPassword = (EditText) findViewById(R.id.confirm_password);
        registerButton = (Button) findViewById(R.id.register_button);
        loginTextView= (TextView) findViewById(R.id.textview_login);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = editUsername.getText().toString().trim();
                String password = editPassword.getText().toString().trim();
                String cnfpassword = confirmPassword.getText().toString().trim();

                Map<String, String> data = new HashMap<String, String>();
                data.put("action","wallet_create");

                new Request(data, new RequestCallback(){

                    @Override
                    public void run() {

                        super.run();

                        String res = this.Response;


                        try {
                            JSONObject json = new JSONObject(res);

                            String walletId = json.getString("wallet");
                            Log.d("Wallet_ID", walletId);

                            Map<String, String> data_1 = new HashMap<String, String>();
                            data_1.put("action","key_create");

                            new Request(data_1, new RequestCallback(){

                                @Override
                                public void run(){

                                    super.run();

                                    String res_2 = this.Response;
                                    Log.d("Key_create",res_2);

                                    try {

                                        JSONObject json_2 = new JSONObject(res_2);
                                        String accountId = json_2.getString("account");

                                        Log.d("account_Id", accountId);

                                        if(password.equals(cnfpassword)){

                                            long val = db.addUser(username, password, walletId, accountId);

                                            if(val > 0){

                                                new User(username, password, walletId);

                                                Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                                Intent moveToLogin = new Intent((RegisterActivity.this), (MainActivity.class));
                                                startActivity(moveToLogin);
                                            }
                                            else{
                                                Toast.makeText(RegisterActivity.this, "Registration", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else{
                                            Toast.makeText(RegisterActivity.this, "Password do not match", Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).execute("http://192.168.56.1:7076");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).execute("http://192.168.56.1:7076");
            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent registerIntent = new Intent((RegisterActivity.this), (MainActivity.class));
                startActivity(registerIntent);
            }
        });
    }
}