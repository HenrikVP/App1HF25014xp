package dk.tec.app1hf25014xp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner statusSpinner;
    private SeekBar rewardSeekBar;
    private ToggleButton paidOutToggle;
    private Button datetimeButton;
    private TextView datetimeText;
    private TextView rewardValue;

    private Assignment assignment = new Assignment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initGui();
    }

    private void initGui() {
        // Initialize UI components
        statusSpinner = findViewById(R.id.statusSpinner);
        rewardSeekBar = findViewById(R.id.rewardSeekBar);
        paidOutToggle = findViewById(R.id.paidOutToggle);
        datetimeButton = findViewById(R.id.datetimeButton);
        datetimeText = findViewById(R.id.datetimeText);
        rewardValue = findViewById(R.id.rewardValue);
        Button createButton = findViewById(R.id.btn_create);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAssingment();
                //TODO Add assignment to list in another actitivy
            }
        });

        List<Status> statuses = Arrays.asList(Status.values());
        ArrayAdapter<Status> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statuses);
        statusSpinner.setAdapter(adapter);

        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Status selectedStatus = (Status) parent.getItemAtPosition(position);
                assignment.setStatus(selectedStatus);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Set up the SeekBar
        rewardSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rewardValue.setText("Reward: " + progress * 5);
                //TODO Fix progress. It is NOT giving the x5 value
                assignment.setReward(progress * 5);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        rewardSeekBar.setMax(20);

        // Set up the ToggleButton
        paidOutToggle.setOnCheckedChangeListener((buttonView, isChecked) -> assignment.setPaidOut(isChecked));

        // Set up the DateTime Picker
        datetimeButton.setOnClickListener(v -> showDateTimePicker());
    }


    private Assignment createAssingment() {
        //Assignment assignment = new Assignment();
        EditText title = findViewById(R.id.edit_title);
        String titlestring = title.getText().toString();
        assignment.setTitle(titlestring);
        Log.d("Create Assignment", "createAssingment: " + assignment.getTitle());
        return assignment;
    }

    private void showDateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            new TimePickerDialog(this, (timeView, hourOfDay, minute) -> {
                LocalDateTime selectedDateTime = LocalDateTime.of(year, month + 1, dayOfMonth, hourOfDay, minute);
                assignment.setDatetime(selectedDateTime);
                datetimeText.setText("Selected: " + selectedDateTime.toString());
            }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true).show();
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH)).show();
    }
}