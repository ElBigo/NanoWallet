package nanoapps.equensworldlie.com.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import nanoapps.equensworldlie.com.R;
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
        setContentView(R.layout.activity_main);

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

                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    user.setUsername(username);

                    

                    Intent userIntent = new Intent((LoginActivity.this), (UserActivity.class));
                    startActivity(userIntent);
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