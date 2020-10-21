package uts.tugas.tutor.paket;

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

public class Paket_Add extends AppCompatActivity
{
    private EditText editText_Package;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paket_add);

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        editText_Package = findViewById(R.id.editText_Package);

        Button button_Add = findViewById(R.id.button_Add);
        Button button_View = findViewById(R.id.button_View);

        button_Add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                addPaket();
            }
        });
        button_View.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(Paket_Add.this, Paket_Show_All.class));
            }
        });
    }

    @SuppressWarnings("deprecation")
    private void addPaket()
    {
        final String name = editText_Package.getText().toString();

        if (TextUtils.isEmpty(name))
        {
            GlobalUtils.GToast.show(Paket_Add.this, "Form tidak boleh kosong");
            return;
        }

        class Task extends AsyncTask<Void, Void, String>
        {
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                GlobalUtils.GProgressDialog.show(Paket_Add.this, "Menambahkan", "Mohon tunggu...");
            }

            @Override
            protected String doInBackground(Void... voids)
            {
                HashMap<String, String> params = new HashMap<>();
                params.put("dummy", "dummy");
                params.put(Config.PACKAGE, name);

                return RequestHandler.sendPostRequest(Config.URL_ADD, params);
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                GlobalUtils.GProgressDialog.dismiss();
                GlobalUtils.GToast.show(Paket_Add.this, s);
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
