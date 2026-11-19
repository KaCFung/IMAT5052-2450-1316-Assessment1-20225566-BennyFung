package uk.ac.plymouth.comp2000hk.sharedofficebookingapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etUsername = findViewById(R.id.etUsername);
        Button btnLogin = findViewById(R.id.btnLogin);

        // check user role on single login button click
        btnLogin.setOnClickListener(v -> {
            String input = etUsername.getText().toString().trim().toLowerCase();
            if (input.equals("chris")) {
                BookingDataManager.currentUserRole = "MANAGER";
                BookingDataManager.currentUserName = "Chris";
                Toast.makeText(this, "Welcome Chris! Logged in as Manager", Toast.LENGTH_SHORT).show();
            } else if (input.equals("rex") || input.isEmpty()) {
                BookingDataManager.currentUserRole = "EMPLOYEE";
                BookingDataManager.currentUserName = "Rex";
                Toast.makeText(this, "Welcome Rex! Logged in as Employee", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Invalid user (try 'Rex' or 'Chris')", Toast.LENGTH_SHORT).show();
            }
        });
    }
}