package nanoapps.equensworldlie.com.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import nanoapps.equensworldlie.com.R;
import nanoapps.equensworldlie.com.controller.PostHttp;
import nanoapps.equensworldlie.com.model.DbManager;

public class MainActivity extends AppCompatActivity {

    EditText editUsername;
    EditText editPassword;
    Button loginButton;
    TextView registerTextView;
    DbManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DbManager(this);
        editUsername = (EditText) findViewById(R.id.edit_username);
        editPassword = (EditText) findViewById(R.id.edit_password);
        loginButton = (Button) findViewById(R.id.login_button);
        registerTextView = (TextView) findViewById(R.id.textview_register);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editUsername.getText().toString().trim();
                String password = editPassword.getText().toString().trim();


                boolean res = db.checkUser(username, password);
                JSONObject postData =new JSONObject();

                if(res==true){
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent userIntent = new Intent((MainActivity.this), (UserActivity.class));

//                    try{
//                        postData.put("action", "version");
//                        new PostHttp().execute("http://192.168.56.1:7076", postData.toString());
//                    } catch (JSONException e){
//                        e.printStackTrace();
//                    }

                    startActivity(userIntent);
                }
                else {
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent loginIntent = new Intent((MainActivity.this), (RegisterActivity.class));
                startActivity(loginIntent);
            }
        });
    }
}