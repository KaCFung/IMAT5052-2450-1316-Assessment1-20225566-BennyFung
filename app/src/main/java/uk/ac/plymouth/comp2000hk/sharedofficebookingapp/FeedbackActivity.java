package uk.ac.plymouth.comp2000hk.sharedofficebookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
//for display the booking details, providing clean info after submission immediately.
public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        boolean isManager = BookingDataManager.currentUserRole.equals("MANAGER");

        String roomName = getIntent().getStringExtra("ROOM_NAME");
        String date = getIntent().getStringExtra("DATE");
        String timeSlot = getIntent().getStringExtra("TIMESLOT");
        String purpose = getIntent().getStringExtra("PURPOSE");
        String bookedFor = getIntent().getStringExtra("BOOKED_FOR");

        TextView tvDetails = findViewById(R.id.tvFeedbackDetails);
        tvDetails.setText("Room: " + roomName +
                "\nDate: " + date +
                "\nTime: " + timeSlot +
                "\nPurpose: " + purpose +
                "\nBooked For: " + bookedFor);

        findViewById(R.id.btnFeedbackViewBookings).setOnClickListener(v -> {
            if (isManager) {
                startActivity(new Intent(FeedbackActivity.this, ManageRequestsActivity.class));
            } else {
                startActivity(new Intent(FeedbackActivity.this, MyBookingsActivity.class));
            }
            finish();
        });
    }
}