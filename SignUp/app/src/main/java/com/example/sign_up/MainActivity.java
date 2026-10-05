package com.example.signup;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    EditText username, password;
    Button signup;
    TextView message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signup);
        message = findViewById(R.id.message);
        signup.setOnClickListener(v -> {
            String user = username.getText().toString().trim();
            String pass = password.getText().toString();
            if (user.isEmpty()) {
                username.setError("Username is required");
                username.requestFocus();
                return;
            }
            if (user.length() < 4) {
                username.setError("Username must contain at least 4 characters");
                username.requestFocus();
                return;
            }
            if (pass.isEmpty()) {
                password.setError("Password is required");
                password.requestFocus();
                return;
            }
            if (pass.length() < 6) {
                password.setError("Password must contain at least 6 characters");
                password.requestFocus();
                return;
            }
            if (!pass.matches(".*[A-Z].*")) {
                password.setError("Password must contain at least 1 uppercase letter");
                password.requestFocus();
                return;
            }
            if (!pass.matches(".*[0-9].*")) {
                password.setError("Password must contain at least 1 number");
                password.requestFocus();
                return;
            }
            if (!pass.matches(".*[^a-zA-Z0-9].*")) {
                password.setError("Password must contain at least 1 special character");
                password.requestFocus();
                return;
            }
            message.setText("Sign-Up Successful!");
        });
    }
}

