package uk.ac.plymouth.comp2000hk.sharedofficebookingapp;

import android.content.Intent;
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

        btnLogin.setOnClickListener(v -> {
            String inputUser = etUsername.getText().toString().trim().toLowerCase();

            if (inputUser.isEmpty()) {
                etUsername.setError("Please enter username (rex or chris)");
                etUsername.requestFocus();
                return;
            }

            if (inputUser.equals("rex")) {
                BookingDataManager.currentUserRole = "EMPLOYEE";
                BookingDataManager.currentUserName = "Rex";
                startActivity(new Intent(MainActivity.this, DashboardActivity.class));
            } else if (inputUser.equals("chris")) {
                BookingDataManager.currentUserRole = "MANAGER";
                BookingDataManager.currentUserName = "Chris";
                startActivity(new Intent(MainActivity.this, DashboardActivity.class));
            } else {
                etUsername.setError("Unknown user");
                Toast.makeText(MainActivity.this, "Invalid username! Use 'rex' or 'chris'.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}