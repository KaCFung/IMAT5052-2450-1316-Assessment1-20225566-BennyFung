package uk.ac.plymouth.comp2000hk.sharedofficebookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

//handle basic authentication for Rex and Chris
//didn't enable password function
public class MainActivity extends AppCompatActivity {

    //Password check function - (REQUIRE_PASSWORD = false) for easier testing
    private static final boolean REQUIRE_PASSWORD = false;

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

            // Password check block (skipped when REQUIRE_PASSWORD is false)
            if (REQUIRE_PASSWORD) {
                int passwordId = getResources().getIdentifier("etPassword", "id", getPackageName());
                if (passwordId != 0) {
                    EditText etPassword = findViewById(passwordId);
                    String inputPass = etPassword.getText().toString().trim();
                    if (!checkPassword(inputUser, inputPass)) {
                        etPassword.setError("Invalid password");
                        etPassword.requestFocus();
                        Toast.makeText(MainActivity.this, "Wrong password!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
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

    //password verification for future production upgrade
    private boolean checkPassword(String username, String password) {
        if ("rex".equals(username)) return "rex123".equals(password);
        if ("chris".equals(username)) return "chris123".equals(password);
        return false;
    }
}