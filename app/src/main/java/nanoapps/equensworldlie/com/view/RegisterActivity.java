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
import nanoapps.equensworldlie.com.model.Account;
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

                Map<String, String> dataWalletCreation = new HashMap<String, String>();
                dataWalletCreation.put("action","wallet_create");

                switch (username){
                    case "admin":
                        new Request(dataWalletCreation, new RequestCallback(){

                            @Override
                            public void run() {

                                super.run();
                                String walletCreationResponse = this.Response;

                                try {

                                    JSONObject walletCreateJson = new JSONObject(walletCreationResponse);

                                    String walletId = walletCreateJson.getString("wallet");
                                    String accountId = "nano_3e3j5tkog48pnny9dmfzj1r16pg8t1e76dz5tmac6iq689wyjfpiij4txtdo";
                                    String publicKey = "B0311EA55708D6A53C75CDBF88300259C6D018522FE3D4D0A242E431F9E8B6D0";
                                    String privateKey = "34F0A37AAD20F4A260F0A5B3CB3D7FB50673212263E58A380BC10474BB039CE4";

                                    if(password.equals(cnfpassword)){

                                        long val = db.addUser(username, password, walletId, accountId, publicKey, privateKey);

                                        if(val > 0){

                                            Map<String, String> adminWalletAssociation = new HashMap<String, String>();
                                            adminWalletAssociation.put("action","wallet_add");
                                            adminWalletAssociation.put("wallet",walletId);
                                            adminWalletAssociation.put("key",privateKey);

                                            new Request(adminWalletAssociation, new RequestCallback(){
                                                @Override
                                                public void run(){
                                                    super.run();

                                                    Log.e("Output",this.Response);
                                                }
                                            }).execute("http://192.168.56.1:7076");

                                            Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                                            Intent moveToLogin = new Intent((RegisterActivity.this), (LoginActivity.class));
                                            startActivity(moveToLogin);
                                        }
                                        else{Toast.makeText(RegisterActivity.this, "Registration", Toast.LENGTH_SHORT).show(); }
                                    }
                                    else{Toast.makeText(RegisterActivity.this, "Password do not match", Toast.LENGTH_SHORT).show();}

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).execute("http://192.168.56.1:7076");
                        break;

                    default:
                        new Request(dataWalletCreation, new RequestCallback(){

                            @Override
                            public void run() {

                                super.run();
                                String walletCreationResponse = this.Response;

                                try {

                                    JSONObject walletCreateJson = new JSONObject(walletCreationResponse);

                                    String walletId = walletCreateJson.getString("wallet");
                                    Map<String, String> dataKeyCreation = new HashMap<String, String>();
                                    dataKeyCreation.put("action","key_create");

                                    new Request(dataKeyCreation, new RequestCallback(){

                                        @Override
                                        public void run(){

                                            super.run();
                                            String keyCreationResponse = this.Response;

                                            try {

                                                JSONObject keyCreateJson = new JSONObject(keyCreationResponse);
                                                String accountId = keyCreateJson.getString("account");
                                                String publicKey = keyCreateJson.getString("public");
                                                String privateKey = keyCreateJson.getString("private");

                                                if(password.equals(cnfpassword)){

                                                    long val = db.addUser(username, password, walletId, accountId, publicKey, privateKey);

                                                    if(val > 0){

                                                        Map<String, String> dataWalletAccountAssociation = new HashMap<String, String>();
                                                        dataWalletAccountAssociation.put("action","wallet_add");
                                                        dataWalletAccountAssociation.put("wallet",walletId);
                                                        dataWalletAccountAssociation.put("key",privateKey);

                                                        new Request(dataWalletAccountAssociation, new RequestCallback(){

                                                            @Override
                                                            public void run(){
                                                                super.run();
                                                            }
                                                        }).execute("http://192.168.56.1:7076");

                                                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                                                        Intent moveToLogin = new Intent((RegisterActivity.this), (LoginActivity.class));
                                                        startActivity(moveToLogin);
                                                    }
                                                    else{Toast.makeText(RegisterActivity.this, "Registration", Toast.LENGTH_SHORT).show(); }
                                                }
                                                else{Toast.makeText(RegisterActivity.this, "Password do not match", Toast.LENGTH_SHORT).show();}
                                            } catch (JSONException e){
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
            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent((RegisterActivity.this), (LoginActivity.class));
                startActivity(registerIntent);
            }
        });
    }
}