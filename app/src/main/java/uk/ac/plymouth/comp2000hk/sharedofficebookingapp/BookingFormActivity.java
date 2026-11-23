package uk.ac.plymouth.comp2000hk.sharedofficebookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
//handle booking confirmation.
//pre-fills data to minize user input.
public class BookingFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_form);

        boolean isManager = "MANAGER".equals(BookingDataManager.currentUserRole);

        String roomName = getIntent().getStringExtra("ROOM_NAME");
        String date = getIntent().getStringExtra("DATE");
        String timeSlot = getIntent().getStringExtra("TIMESLOT");
        if (roomName == null) roomName = "Meeting Room 1";
        if (date == null) date = "12/11/2026";
        if (timeSlot == null) timeSlot = "10:00 - 10:30";

        ImageView ivBg = findViewById(R.id.ivBgForm);
        ivBg.setImageResource(isManager ? R.drawable.bookingformchris : R.drawable.bookingformrex);

        TextView tvSummary = findViewById(R.id.tvFormSummary);
        tvSummary.setText(roomName + "\nDate: " + date + "\nTime: " + timeSlot);

        EditText etPurpose = findViewById(R.id.etPurpose);
        Spinner spBookedFor = findViewById(R.id.spBookedFor);

        if (isManager) {
            spBookedFor.setVisibility(View.VISIBLE);
            String[] staffList = {"Chris (Manager)", "Rex (Staff)", "Alice (Staff)", "Bob (Staff)"};
            spBookedFor.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, staffList));
        } else {
            spBookedFor.setVisibility(View.GONE);
        }

        findViewById(R.id.btnBackForm).setOnClickListener(v -> finish());

        Button btnSubmit = findViewById(R.id.btnSubmitBooking);
        final String finalRoomName = roomName;
        final String finalDate = date;
        final String finalTimeSlot = timeSlot;
//prevent empty purpose submission.
        btnSubmit.setOnClickListener(v -> {
            String purpose = etPurpose.getText().toString().trim();
            if (purpose.isEmpty()) {
                Toast.makeText(this, "Please enter a booking purpose!", Toast.LENGTH_SHORT).show();
                return;
            }

            String bookedFor = isManager
                    ? spBookedFor.getSelectedItem().toString()
                    : "Rex (Staff)";
//insert new booking as 0 to show the newest booking on top.
            Booking newBooking = new Booking(finalRoomName, finalDate, finalTimeSlot, purpose, bookedFor, "Confirmed");
            BookingDataManager.bookingList.add(0, newBooking);

            Intent intent = new Intent(BookingFormActivity.this, FeedbackActivity.class);
            intent.putExtra("ROOM_NAME", finalRoomName);
            intent.putExtra("DATE", finalDate);
            intent.putExtra("TIMESLOT", finalTimeSlot);
            intent.putExtra("PURPOSE", purpose);
            intent.putExtra("BOOKED_FOR", bookedFor);
            startActivity(intent);
            finish();
        });
    }
}