package com.example.randomwallpaper;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
public class MainActivity extends AppCompatActivity {
    Button changeButton, stopButton;
    TextView status;
    Timer timer;
    Random random = new Random();
    int lastWallpaper = -1;
    int[] wallpapers = {
            R.drawable.wallpaper1,
            R.drawable.wallpaper2,
            R.drawable.wallpaper3,
            R.drawable.wallpaper4
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeButton = findViewById(R.id.changeButton);
        stopButton = findViewById(R.id.stopButton);
        status = findViewById(R.id.status);
        status.setText("Wallpaper changing stopped");
        changeButton.setOnClickListener(v -> {
            stopTimer();
            changeWallpaper();
            status.setText("Wallpaper will change every 30 seconds");
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    changeWallpaper();
                }
            }, 30000, 30000);
            Toast.makeText(
                    MainActivity.this,
                    "Wallpaper changing started",
                    Toast.LENGTH_SHORT
            ).show();
        });
        stopButton.setOnClickListener(v -> {
            stopTimer();
            status.setText("Wallpaper changing stopped");
            Toast.makeText(
                    MainActivity.this,
                    "Wallpaper changing stopped",
                    Toast.LENGTH_SHORT
            ).show();
        });
    }
    private void changeWallpaper() {
        int randomIndex;
        do {
            randomIndex = random.nextInt(wallpapers.length);
        } while (randomIndex == lastWallpaper && wallpapers.length > 1);
        lastWallpaper = randomIndex;
        Bitmap bitmap = BitmapFactory.decodeResource(
                getResources(),
                wallpapers[randomIndex]
        );
        WallpaperManager wallpaperManager =
                WallpaperManager.getInstance(
                        getApplicationContext()
                );
        try {
            wallpaperManager.setBitmap(bitmap);
            new Handler(Looper.getMainLooper()).post(() -> {
                Toast.makeText(
                        MainActivity.this,
                        "Wallpaper " + (randomIndex + 1) + " applied",
                        Toast.LENGTH_SHORT
                ).show();
            });
        } catch (IOException e) {
            e.printStackTrace();
            new Handler(Looper.getMainLooper()).post(() -> {
                Toast.makeText(
                        MainActivity.this,
                        "Failed to change wallpaper",
                        Toast.LENGTH_SHORT
                ).show();
            });
        }
    }
    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }
}
