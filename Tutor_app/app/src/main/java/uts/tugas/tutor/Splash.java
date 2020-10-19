package uts.tugas.tutor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import uts.tugas.tutor.siswa.Siswa_Add;

public class Splash extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);

        exec.schedule(new Runnable()
        {
            @Override
            public void run()
            {
                startActivity(new Intent(getApplicationContext(), Siswa_Add.class));
            }
        }, 3, TimeUnit.SECONDS);
    }
}