package dk.tec.app1hf25014xp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
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
        initGui();
    }

    private void initGui() {
        findViewById(R.id.fab).setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), AssignmentActivity.class)));

        // Loads assignments. Gets assignment from other activity. If not null, adds it and saves it
        List<Assignment> assignments = getAssignments();
        Assignment assignment = (Assignment) getIntent().getSerializableExtra("assignment");
        if (assignment != null) assignments.add(assignment);
        saveAssignments(assignments);

        // Adds list to a recyclerview through a custom adapter
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AssignmentAdapter(assignments));
    }

    private void saveAssignments(List<Assignment> assignments) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = new Gson().toJson(assignments);
        editor.putString("ASSIGNMENTS_KEY", json);
        editor.apply();
    }

    private List<Assignment> getAssignments() {
        // Get the JSON string
        String json = sharedPreferences.getString("ASSIGNMENTS_KEY", null);
        // Convert JSON back to a list of assignments
        if (json != null) {
            Type type = new TypeToken<ArrayList<Assignment>>() {}.getType();
            return new Gson().fromJson(json, type);
        }
        return new ArrayList<>(); // Return an empty list if no data is found
    }
}