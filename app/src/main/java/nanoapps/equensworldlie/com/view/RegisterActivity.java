package nanoapps.equensworldlie.com.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.*;
import org.json.JSONException;
//import org.json.JSONObject;

import nanoapps.equensworldlie.com.R;
import nanoapps.equensworldlie.com.controller.AsyncResponse;
import nanoapps.equensworldlie.com.controller.PostHttp;
import nanoapps.equensworldlie.com.model.DbManager;

public class RegisterActivity extends AppCompatActivity{

    DbManager db;
    EditText editUsername;
    EditText editPassword;
    EditText confirmPassword;
    Button registerButton;
    TextView loginTextView;

    JSONObject jsonCreateWallet =new JSONObject();


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


                try{
                    jsonCreateWallet.put("action", "wallet_create");

//                    PostHttp asyncTask = new PostHttp(new AsyncResponse(){
//
//                        @Override
//                        void processFinish(JSONObject output){
//                            //Here you will receive the result fired from async class
//                            //of onPostExecute(result) method.
//                            Toast.makeText(RegisterActivity.this, "Registration", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }).execute("http://192.168.56.1:7076", jsonCreateWallet.toString());

                    new PostHttp().execute("http://192.168.56.1:7076", jsonCreateWallet.toString());
                } catch (JSONException e){
                    e.printStackTrace();
                }

                String tmp1 = "walletId";
                String tmp2 = "accountId";

                if(password.equals(cnfpassword)){


                    long val = db.addUser(username, password, tmp1, tmp2);

                    if(val > 0){
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

//    public void processFinish(JSONObject output){
//        //Here you will receive the result fired from async class
//        //of onPostExecute(result) method.
//    }
}