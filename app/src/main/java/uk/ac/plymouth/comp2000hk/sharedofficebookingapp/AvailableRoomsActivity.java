package uk.ac.plymouth.comp2000hk.sharedofficebookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

// Display room status based on different roles; manager and employee have different layouts.
// Use (isReservedCard) and (BookingDataManager.isRoomReserved) to prevent double-booking.
// Provide default date/timeslot values if Intent extras are null to prevent app crash.
public class AvailableRoomsActivity extends AppCompatActivity {

    private String selectedDate;
    private String selectedTimeSlot;
    private boolean isManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_rooms);

        isManager = "MANAGER".equals(BookingDataManager.currentUserRole);
        selectedDate = getIntent().getStringExtra("DATE");
        selectedTimeSlot = getIntent().getStringExtra("TIMESLOT");
        if (selectedDate == null) selectedDate = "12/11/2026";
        if (selectedTimeSlot == null) selectedTimeSlot = "10:00 - 10:30";

        ImageView ivBg = findViewById(R.id.ivBgRooms);
        ivBg.setImageResource(isManager ? R.drawable.availableroomchris : R.drawable.availableroomrex);

        // Update header subtitle dynamically with the selected date and timeslot
        TextView tvHeader = findViewById(R.id.tvRoomsHeaderDateTime);
        if (tvHeader != null) {
            tvHeader.setText(selectedDate + " • " + selectedTimeSlot);
        }

        findViewById(R.id.btnBackRooms).setOnClickListener(v -> finish());

        Button btnRoom1 = findViewById(R.id.btnSelectRoom1);
        Button btnRoom2 = findViewById(R.id.btnSelectRoom2);
        Button btnRoom3 = findViewById(R.id.btnSelectRoom3);

        if (isManager) {
            // manager available room list
            btnRoom1.setOnClickListener(v -> handleRoomClick("Seminar Room A", false));
            btnRoom2.setOnClickListener(v -> handleRoomClick("Seminar Room B", true));
            btnRoom3.setEnabled(false);
        } else {
            // employee available room list
            btnRoom1.setOnClickListener(v -> handleRoomClick("Meeting Room 1", false));
            btnRoom2.setOnClickListener(v -> handleRoomClick("Meeting Room 3", false));
            btnRoom3.setOnClickListener(v -> handleRoomClick("Seminar Room A", false));
        }
    }

    private void handleRoomClick(String roomName, boolean isReservedCard) {
        if (isReservedCard || BookingDataManager.isRoomReserved(roomName, selectedDate, selectedTimeSlot)) {
            Toast.makeText(this, roomName + " is already RESERVED for this slot!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(AvailableRoomsActivity.this, BookingFormActivity.class);
        intent.putExtra("ROOM_NAME", roomName);
        intent.putExtra("DATE", selectedDate);
        intent.putExtra("TIMESLOT", selectedTimeSlot);
        startActivity(intent);
    }
}