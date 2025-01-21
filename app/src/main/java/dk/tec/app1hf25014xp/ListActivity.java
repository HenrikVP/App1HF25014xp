package dk.tec.app1hf25014xp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    List<Assignment> assignments;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sharedPreferences = getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        //TODO Get list from persistent data
        //TODO Add item from other activity
        //TODO Show list in layout

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), AssignmentActivity.class));
        });

        List<Assignment> assignments = getAssignments();
        Assignment assignment = (Assignment) getIntent().getSerializableExtra("assignment");
        if (assignment != null)
            assignments.add(assignment);
        saveAssignments(assignments);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AssignmentAdapter adapter = new AssignmentAdapter(assignments);
        recyclerView.setAdapter(adapter);
    }

    private void saveAssignments(List<Assignment> assignments) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Convert the list to JSON
        Gson gson = new Gson();
        String json = gson.toJson(assignments);
        editor.putString("ASSIGNMENTS_KEY", json);
        editor.apply();
    }

    private List<Assignment> getAssignments() {
        // Get the JSON string
        String json = sharedPreferences.getString("ASSIGNMENTS_KEY", null);
        // Convert JSON back to a list of assignments
        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Assignment>>() {
            }.getType();
            return gson.fromJson(json, type);
        }
        return new ArrayList<>(); // Return an empty list if no data is found
    }
}