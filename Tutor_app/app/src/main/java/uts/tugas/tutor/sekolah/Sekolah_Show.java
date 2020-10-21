package uts.tugas.tutor.sekolah;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.crud.library.GlobalUtils;
import com.crud.library.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import uts.tugas.tutor.R;

public class Sekolah_Show extends AppCompatActivity
{
    private EditText editText_Id;
    private EditText editText_Name;
    private EditText editText_Location;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sekolah_show);

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        editText_Id = findViewById(R.id.editText_Id);
        editText_Name = findViewById(R.id.editText_Name);
        editText_Location = findViewById(R.id.editText_Location);

        editText_Id.setText(getIntent().getStringExtra(Config.TAG_ID));

        Button button_Update = findViewById(R.id.button_Update), button_Delete = findViewById(R.id.button_Delete);
        button_Update.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                updateSekolah();
            }
        });
        button_Delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                confirmDeleteSekolah();
            }
        });

        getSekolah();
    }

    @SuppressWarnings("deprecation")
    private void getSekolah()
    {
        class Task extends AsyncTask<Void, Void, String>
        {
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                GlobalUtils.GProgressDialog.show(Sekolah_Show.this, "Memuat data", "Mohon tunggu...");
            }

            @Override
            protected String doInBackground(Void... voids)
            {
                return RequestHandler.sendGetRequest(Config.URL_GET + editText_Id.getText());
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                GlobalUtils.GProgressDialog.dismiss();
                showSekolah(s);
            }
        }

        new Task().execute();
    }

    private void showSekolah(String json_data)
    {
        try
        {
            JSONArray result = new JSONObject(json_data).getJSONArray(Config.TAG_JSON);
            JSONObject data = result.getJSONObject(0);

            editText_Name.setText(data.getString(Config.NAME));
            editText_Location.setText(data.getString(Config.LOCATION));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    private void updateSekolah()
    {
        final String id = editText_Id.getText().toString();
        final String name = editText_Name.getText().toString();
        final String location = editText_Location.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(location))
        {
            GlobalUtils.GToast.show(Sekolah_Show.this, "Field tidak boleh kosong!");
            return;
        }

        class Task extends AsyncTask<Void, Void, String>
        {
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                GlobalUtils.GProgressDialog.show(Sekolah_Show.this, "Memperbarui data", "Mohon tunggu...");
            }

            @Override
            protected String doInBackground(Void... voids)
            {
                HashMap<String, String> map = new HashMap<>();
                map.put(Config.ID, id);
                map.put(Config.NAME, name);
                map.put(Config.LOCATION, location);

                return RequestHandler.sendPostRequest(Config.URL_UPDATE, map);
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                GlobalUtils.GProgressDialog.dismiss();
                GlobalUtils.GToast.show(Sekolah_Show.this, s);
                finish();
            }
        }

        new Task().execute();
    }

    @SuppressWarnings("deprecation")
    private void deleteSekolah()
    {
        class Task extends AsyncTask<Void, Void, String>
        {
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                GlobalUtils.GProgressDialog.show(Sekolah_Show.this, "Menghapus data", "Mohon tunggu...");
            }

            @Override
            protected String doInBackground(Void... voids)
            {
                return RequestHandler.sendGetRequest(Config.URL_DELETE + editText_Id.getText());
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                GlobalUtils.GProgressDialog.dismiss();
                GlobalUtils.GToast.show(Sekolah_Show.this, s);
                finish();
            }
        }

        new Task().execute();
    }

    private void confirmDeleteSekolah()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Apakah Anda yakin ingin menghapus sekolah ini?");

        builder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        deleteSekolah();
                    }
                });
        builder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return true;
    }
}
