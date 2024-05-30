package com.example.workoutgen;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkoutActivity extends AppCompatActivity {

    private static final String TAG = "WorkoutActivity";
    private List<String> workoutList;
    private Map<String, String> videoMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        workoutList = getIntent().getStringArrayListExtra("workouts");
        videoMap = (Map<String, String>) getIntent().getSerializableExtra("video_map");

        LinearLayout workoutContainer = findViewById(R.id.workout_container);

        for (String workout : workoutList) {
            View workoutView = getLayoutInflater().inflate(R.layout.item_workout, workoutContainer, false);
            TextView workoutText = workoutView.findViewById(R.id.workout_text);
            TextView timerText = workoutView.findViewById(R.id.timer_text);
            Button playButton = workoutView.findViewById(R.id.play_button);
            TextView youtubeLinkText = workoutView.findViewById(R.id.youtube_link_text);

            workoutText.setText(workout);

            playButton.setOnClickListener(v -> {
                startTimer(timerText);
                String videoUrl = videoMap.get(workout);
                youtubeLinkText.setText(videoUrl);
                youtubeLinkText.setVisibility(View.VISIBLE); // Show YouTube link
            });

            youtubeLinkText.setOnClickListener(v -> {
                String videoUrl = videoMap.get(workout);
                openYouTubeLink(videoUrl);
            });

            workoutContainer.addView(workoutView);
        }
    }

    private void startTimer(TextView timerText) {
        new CountDownTimer(15 * 60 * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                long minutes = (millisUntilFinished / 1000) / 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                timerText.setText("Time remaining: " + minutes + ":" + String.format("%02d", seconds));
            }

            public void onFinish() {
                timerText.setText("Time's up!");
                MediaPlayer mediaPlayer = MediaPlayer.create(WorkoutActivity.this, R.raw.buzzer);
                mediaPlayer.start();
            }
        }.start();
    }
    private void openYouTubeLink(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }


            private Map<String, String> readVideoLinks() {
        Map<String, String> videoMap = new HashMap<>();
        try {
            InputStream inputStream = getAssets().open("video_links.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    videoMap.put(parts[0], parts[1]);
                }
            }
            reader.close();
        } catch (IOException e) {
            Log.e(TAG, "Error reading video links file", e);
        }
        return videoMap;
    }
}
