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
//for manager to view all booking and manage for cancellation.
//added dynamic card(item_booking_card.xml) and confirmation alert to prevent accidental cancellation.
public class ManageRequestsActivity extends AppCompatActivity {

    private ListView lvAllBookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_requests);

        View btnBack = findViewById(R.id.btnBackManageRequests);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                Intent intent = new Intent(ManageRequestsActivity.this, DashboardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            });
        }

        lvAllBookings = findViewById(R.id.lvManageRequests);
        if (lvAllBookings != null) {
            loadAllBookings();

            lvAllBookings.setOnItemClickListener((parent, view, position, id) -> {
                if (BookingDataManager.bookingList.isEmpty()) return;
                Booking selected = BookingDataManager.bookingList.get(position);
                if ("Cancelled".equalsIgnoreCase(selected.status)) {
                    Toast.makeText(this, "This booking is already cancelled.", Toast.LENGTH_SHORT).show();
                    return;
                }
                new AlertDialog.Builder(this)
                        .setTitle("Manager Override: Cancel Booking")
                        .setMessage("Cancel booking for " + selected.bookedFor + " at " + selected.roomName + " (" + selected.date + " " + selected.timeSlot + ")?")
                        .setPositiveButton("Cancel Booking", (dialog, which) -> {
                            selected.status = "Cancelled";
                            loadAllBookings();
                            Toast.makeText(this, "Reservation cancelled by Manager.", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Close", null)
                        .show();
            });
        }
    }

    private void loadAllBookings() {
        ArrayAdapter<Booking> adapter = new ArrayAdapter<Booking>(this, R.layout.item_booking_card, BookingDataManager.bookingList) {
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
                    tvDetails.setText("Booked For: " + b.bookedFor + "  |  Purpose: " + b.purpose);

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
        lvAllBookings.setAdapter(adapter);
    }
}