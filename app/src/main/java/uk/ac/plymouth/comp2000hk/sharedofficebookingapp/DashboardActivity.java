package uk.ac.plymouth.comp2000hk.sharedofficebookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

//control by role for different background layouts and time options to show different UI.
public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //switch dashboard based on active user role
        boolean isManager = BookingDataManager.currentUserRole.equals("MANAGER");

        ImageView ivBg = findViewById(R.id.ivBgDashboard);
        ivBg.setImageResource(isManager ? R.drawable.dashboardchris : R.drawable.dashboardrex);

        Spinner spDate = findViewById(R.id.spDate);
        Spinner spStart = findViewById(R.id.spStartTime);
        Spinner spEnd = findViewById(R.id.spEndTime);

        String[] dates = {"12/11/2026", "13/11/2026", "14/11/2026", "15/11/2026"};
        String[] times = BookingDataManager.getTimeOptions(isManager);

        spDate.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dates));
        spStart.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, times));
        spEnd.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, times));

        // default selection  09:00 - 18:00 for both roles
        if (isManager) {
            spStart.setSelection(18); // 09:00 in 24-hour list
            spEnd.setSelection(36);   // 18:00 in 24-hour list
        } else {
            spStart.setSelection(0);  // 09:00 in office-hours list
            spEnd.setSelection(times.length - 1); // 18:00 in office-hours list
        }

        Button btnFindRooms = findViewById(R.id.btnFindRooms);
        Button btnMyBookings = findViewById(R.id.btnMyBookings);
        Button btnLogout = findViewById(R.id.btnLogout);

        btnFindRooms.setOnClickListener(v -> {
            if (spEnd.getSelectedItemPosition() <= spStart.getSelectedItemPosition()) {
                Toast.makeText(this, "End Time must be later than Start Time!", Toast.LENGTH_SHORT).show();
                return;
            }
            String selectedDate = spDate.getSelectedItem().toString();
            String selectedSlot = spStart.getSelectedItem().toString() + " - " + spEnd.getSelectedItem().toString();

            Intent intent = new Intent(DashboardActivity.this, AvailableRoomsActivity.class);
            intent.putExtra("DATE", selectedDate);
            intent.putExtra("TIMESLOT", selectedSlot);
            startActivity(intent);
        });
//system-wide management view for Manager and personal bookings list for Employee.
        btnMyBookings.setOnClickListener(v -> {
            if (isManager) {
                startActivity(new Intent(DashboardActivity.this, ManageRequestsActivity.class));
            } else {
                startActivity(new Intent(DashboardActivity.this, MyBookingsActivity.class));
            }
        });

        btnLogout.setOnClickListener(v -> finish());
    }
}