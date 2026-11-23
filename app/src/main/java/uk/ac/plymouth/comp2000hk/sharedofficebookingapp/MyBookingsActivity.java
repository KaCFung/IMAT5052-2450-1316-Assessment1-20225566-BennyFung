package uk.ac.plymouth.comp2000hk.sharedofficebookingapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
//control that employee only view his personal booking
public class MyBookingsActivity extends AppCompatActivity {

    private ListView lvBookings;
    private List<Booking> userBookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);

        View btnBack = findViewById(R.id.btnBackMyBookings);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                Intent intent = new Intent(MyBookingsActivity.this, DashboardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            });
        }

        lvBookings = findViewById(R.id.lvMyBookings);
        loadUserBookings();

        lvBookings.setOnItemClickListener((parent, view, position, id) -> {
            if (userBookings.isEmpty()) return;
            Booking selected = userBookings.get(position);
            if ("Cancelled".equalsIgnoreCase(selected.status)) {
                Toast.makeText(this, "This booking is already cancelled.", Toast.LENGTH_SHORT).show();
                return;
            }
            new AlertDialog.Builder(this)
                    .setTitle("Cancel Booking")
                    .setMessage("Are you sure you want to cancel " + selected.roomName + " (" + selected.date + " " + selected.timeSlot + ")?")
                    .setPositiveButton("Yes, Cancel", (dialog, which) -> {
                        selected.status = "Cancelled";
                        loadUserBookings();
                        Toast.makeText(this, "Booking Cancelled Successfully!", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Keep", null)
                    .show();
        });
    }

    private void loadUserBookings() {
        userBookings = new ArrayList<>();
        for (Booking b : BookingDataManager.bookingList) {
            if (b.bookedFor.contains("Rex")) {
                userBookings.add(b);
            }
        }

        ArrayAdapter<Booking> adapter = new ArrayAdapter<Booking>(this, R.layout.item_booking_card, userBookings) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.item_booking_card, parent, false);
                }
                Booking b = getItem(position);
                TextView tvRoom = convertView.findViewById(R.id.tvCardRoomName);
                TextView tvStatus = convertView.findViewById(R.id.tvCardStatus);
                TextView tvDateTime = convertView.findViewById(R.id.tvCardDateTime);
                TextView tvDetails = convertView.findViewById(R.id.tvCardDetails);
                TextView tvHint = convertView.findViewById(R.id.tvCardActionHint);

                if (b != null) {
                    tvRoom.setText(b.roomName);
                    tvStatus.setText(b.status);
                    tvDateTime.setText(b.date + "  •  " + b.timeSlot);
                    tvDetails.setText("Purpose: " + b.purpose);

                    if ("Cancelled".equalsIgnoreCase(b.status)) {
                        tvStatus.setBackgroundColor(Color.parseColor("#FEE2E2"));
                        tvStatus.setTextColor(Color.parseColor("#B91C1C"));
                        tvHint.setVisibility(View.GONE);
                    } else {
                        tvStatus.setBackgroundColor(Color.parseColor("#DCFCE7"));
                        tvStatus.setTextColor(Color.parseColor("#15803D"));
                        tvHint.setVisibility(View.VISIBLE);
                    }
                }
                return convertView;
            }
        };
        lvBookings.setAdapter(adapter);
    }
}