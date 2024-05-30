package com.example.workoutgen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";

    private Spinner workoutTypeSpinner;
    private TextView workoutTextView;
    private ProgressBar loadingProgressBar;
    private Button selectButton;
    private List<String> workoutList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        workoutTypeSpinner = findViewById(R.id.workout_type_spinner);
        workoutTextView = findViewById(R.id.workout_text_view);
        loadingProgressBar = findViewById(R.id.loading_progress_bar);
        selectButton = findViewById(R.id.select_button);

        // Populate the spinner with workout types
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.workout_types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workoutTypeSpinner.setAdapter(adapter);

        // Set button click listener
        selectButton.setOnClickListener(v -> selectWorkouts());
    }

    private void selectWorkouts() {
        String workoutType = workoutTypeSpinner.getSelectedItem().toString().toLowerCase();
        Log.d(TAG, "Selected workout type: " + workoutType);
        try {
            String workoutData = readFromFile(workoutType + "_workouts.txt", this);
            List<String> workoutList = new ArrayList<>();
            Map<String, String> videoMap = new HashMap<>();

            // Parse the organized data and populate workoutList and videoMap
            String[] lines = workoutData.split("\n");
            for (String line : lines) {
                String[] parts = line.split("=");
                workoutList.add(parts[0]);
                videoMap.put(parts[0], parts[1]);
            }

            // Pass workout list and video information to WorkoutActivity
            Intent intent = new Intent(HomeActivity.this, WorkoutActivity.class);
            intent.putStringArrayListExtra("workouts", (ArrayList<String>) workoutList);
            intent.putExtra("video_map", (Serializable) videoMap);
            startActivity(intent);
        } catch (IOException e) {
            Log.e(TAG, "Error reading file", e);
            workoutTextView.setText("Error: " + e.getMessage());
        }
    }

    // Function to read from a file in the assets folder
    private String readFromFile(String fileName, Context context) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = context.getAssets().open(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        reader.close();
        return stringBuilder.toString();
    }

    // Function to get 4 random workouts from the data
    private List<String> getRandomWorkouts(String workoutData) {
        String[] workouts = workoutData.split("\n");
        List<String> workoutList = new ArrayList<>();
        Collections.addAll(workoutList, workouts);
        Collections.shuffle(workoutList);
        List<String> randomWorkouts = workoutList.subList(0, Math.min(4, workoutList.size()));
        // Convert the SubList to ArrayList
        return new ArrayList<>(randomWorkouts);
    }
}
