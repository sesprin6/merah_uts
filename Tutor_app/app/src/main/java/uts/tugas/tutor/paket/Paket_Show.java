package uts.tugas.tutor.paket;

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

public class Paket_Show extends AppCompatActivity
{
    private EditText editText_Id;
    private EditText editText_Package;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paket_show);

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        editText_Id = findViewById(R.id.editText_Id);
        editText_Package = findViewById(R.id.editText_Package);

        editText_Id.setText(getIntent().getStringExtra(Config.TAG_ID));

        Button button_Update = findViewById(R.id.button_Update), button_Delete = findViewById(R.id.button_Delete);
        button_Update.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                updatePaket();
            }
        });
        button_Delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                confirmDeletePaket();
            }
        });

        getPaket();
    }

    @SuppressWarnings("deprecation")
    private void getPaket()
    {
        class Task extends AsyncTask<Void, Void, String>
        {
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                GlobalUtils.GProgressDialog.show(Paket_Show.this, "Memuat data", "Mohon tunggu...");
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
                showPaket(s);
            }
        }

        new Task().execute();
    }

    private void showPaket(String json_data)
    {
        try
        {
            JSONArray result = new JSONObject(json_data).getJSONArray(Config.TAG_JSON);
            JSONObject data = result.getJSONObject(0);

            editText_Package.setText(data.getString(Config.PACKAGE));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    private void updatePaket()
    {
        final String id = editText_Id.getText().toString();
        final String _package = editText_Package.getText().toString();

        if (TextUtils.isEmpty(_package))
        {
            GlobalUtils.GToast.show(Paket_Show.this, "Field tidak boleh kosong!");
            return;
        }

        class Task extends AsyncTask<Void, Void, String>
        {
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                GlobalUtils.GProgressDialog.show(Paket_Show.this, "Memperbarui data", "Mohon tunggu...");
            }

            @Override
            protected String doInBackground(Void... voids)
            {
                HashMap<String, String> map = new HashMap<>();
                map.put(Config.ID, id);
                map.put(Config.PACKAGE, _package);

                return RequestHandler.sendPostRequest(Config.URL_UPDATE, map);
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                GlobalUtils.GProgressDialog.dismiss();
                GlobalUtils.GToast.show(Paket_Show.this, s);
                finish();
            }
        }

        new Task().execute();
    }

    @SuppressWarnings("deprecation")
    private void deletePaket()
    {
        class Task extends AsyncTask<Void, Void, String>
        {
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                GlobalUtils.GProgressDialog.show(Paket_Show.this, "Menghapus data", "Mohon tunggu...");
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
                GlobalUtils.GToast.show(Paket_Show.this, s);
                finish();
            }
        }

        new Task().execute();
    }

    private void confirmDeletePaket()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Apakah Anda yakin ingin menghapus posisi ini?");

        builder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        deletePaket();
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
