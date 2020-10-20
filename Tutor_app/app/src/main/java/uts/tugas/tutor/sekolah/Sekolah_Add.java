package uts.tugas.tutor.sekolah;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.crud.library.GlobalUtils;
import com.crud.library.RequestHandler;

import java.util.HashMap;

import uts.tugas.tutor.R;

public class Sekolah_Add extends AppCompatActivity
{
    private EditText editText_Name, editText_Location;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sekolah_add);

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        editText_Name = findViewById(R.id.editText_Name);
        editText_Location = findViewById(R.id.editText_Location);

        Button button_Add = findViewById(R.id.button_Add);
        Button button_View = findViewById(R.id.button_View);

        button_Add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                addSekolah();
            }
        });
        button_View.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(Sekolah_Add.this, Sekolah_Show_All.class));
            }
        });
    }

    @SuppressWarnings("deprecation")
    private void addSekolah()
    {
        final String name = editText_Name.getText().toString().trim();
        final String location = editText_Location.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(location))
        {
            GlobalUtils.GToast.show(Sekolah_Add.this, "Form tidak boleh kosong");
            return;
        }

        class Task extends AsyncTask<Void, Void, String>
        {
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                GlobalUtils.GProgressDialog.show(Sekolah_Add.this, "Menambahkan", "Mohon tunggu...");
            }

            @Override
            protected String doInBackground(Void... voids)
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.NAME, name);
                params.put(Config.LOCATION, location);

                return RequestHandler.sendPostRequest(Config.URL_ADD, params);
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                GlobalUtils.GProgressDialog.dismiss();
                GlobalUtils.GToast.show(Sekolah_Add.this, s);
            }
        }

        new Task().execute();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
            finish();

        return true;
    }
}
