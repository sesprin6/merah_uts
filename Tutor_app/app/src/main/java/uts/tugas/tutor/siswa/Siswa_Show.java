package uts.tugas.tutor.siswa;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.crud.library.GlobalUtils;
import com.crud.library.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uts.tugas.tutor.R;
import uts.tugas.tutor.paket.Obj_Paket;
import uts.tugas.tutor.paket.Paket_Add;
import uts.tugas.tutor.sekolah.Obj_Sekolah;
import uts.tugas.tutor.sekolah.Sekolah_Add;

public class Siswa_Show extends AppCompatActivity implements View.OnClickListener
{
    private EditText editText_Id, editText_No, editText_Name, editText_BirthPlace, editText_BirthDay, editText_Address, editText_Representative, editText_Phone;
    private Spinner spinner_Package, spinner_Sex, spinner_School;
    private Button button_Add_Paket, button_Add_Sekolah, button_Update, button_Delete;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.siswa_show);

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        editText_Id = findViewById(R.id.editText_Id);
        editText_No = findViewById(R.id.editText_No);
        editText_Name = findViewById(R.id.editText_Name);
        editText_BirthPlace = findViewById(R.id.editText_BirthPlace);
        editText_BirthDay = findViewById(R.id.editText_BirthDay);
        editText_Address = findViewById(R.id.editText_Address);
        editText_Representative = findViewById(R.id.editText_Representative);
        editText_Phone = findViewById(R.id.editText_Phone);

        spinner_Package = findViewById(R.id.spinner_Package);
        spinner_Sex = findViewById(R.id.spinner_Sex);
        spinner_School = findViewById(R.id.spinner_School);

        button_Add_Paket = findViewById(R.id.button_Add_Paket);
        button_Add_Sekolah = findViewById(R.id.button_Add_Sekolah);
        button_Update = findViewById(R.id.button_Update);
        button_Delete = findViewById(R.id.button_Delete);

        button_Add_Paket.setOnClickListener(this);
        button_Add_Sekolah.setOnClickListener(this);
        button_Update.setOnClickListener(this);
        button_Delete.setOnClickListener(this);

        editText_Id.setText(getIntent().getStringExtra(Config.TAG_ID));
    }

    @SuppressWarnings("deprecation")
    private void getSiswa()
    {
        class Task extends AsyncTask<Void, Void, String>
        {
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                GlobalUtils.GProgressDialog.show(Siswa_Show.this, "Memuat data siswa", "Mohon tunggu...");
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
                showSiswa(s);
            }
        }

        new Task().execute();
    }

    private void showSiswa(String json_data)
    {
        try
        {
            JSONArray result = new JSONObject(json_data).getJSONArray(Config.TAG_JSON);
            JSONObject data = result.getJSONObject(0);

            editText_No.setText(data.getString(Config.NO));
            editText_Name.setText(data.getString(Config.NAME));
            editText_BirthPlace.setText(data.getString(Config.BIRTHPLACE));
            editText_BirthDay.setText(data.getString(Config.BIRTHDAY));
            editText_Address.setText(data.getString(Config.ADDRESS));
            editText_Representative.setText(data.getString(Config.REPRESENTATIVE));
            editText_Phone.setText(data.getString(Config.PHONE));

            for (int i = 1; i < spinner_Package.getAdapter().getCount(); i++)
            {
                if (spinner_Package.getItemAtPosition(i).toString().equals(data.getString(Config.PACKAGE)))
                {
                    spinner_Package.setSelection(i);
                    break;
                }
            }
            for (int i = 1; i < spinner_Sex.getAdapter().getCount(); i++)
            {
                if (spinner_Sex.getItemAtPosition(i).toString().equals(data.getString(Config.SEX)))
                {
                    spinner_Sex.setSelection(i);
                    break;
                }
            }
            for (int i = 1; i < spinner_School.getAdapter().getCount(); i++)
            {
                if (spinner_School.getItemAtPosition(i).toString().equals(data.getString(Config.SCHOOL)))
                {
                    spinner_School.setSelection(i);
                    break;
                }
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    private void init()
    {
        class Task extends AsyncTask<Void, Void, String[]>
        {
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                GlobalUtils.GProgressDialog.show(Siswa_Show.this, "Memuat data", "Mohon tunggu...");
            }

            @Override
            protected String[] doInBackground(Void... voids)
            {
                String response_package = RequestHandler.sendGetRequest(uts.tugas.tutor.paket.Config.URL_GET_ALL);
                String response_school = RequestHandler.sendGetRequest(uts.tugas.tutor.sekolah.Config.URL_GET_ALL);

                return new String[] { response_package, response_school };
            }

            @Override
            protected void onPostExecute(String[] strings)
            {
                super.onPostExecute(strings);
                GlobalUtils.GProgressDialog.dismiss();
                parseObjects(strings);
            }
        }

        new Task().execute();
    }

    private void parseObjects(String[] response)
    {
        List<Obj_Paket> list_package = new ArrayList<>();
        List<String> list_sex = new ArrayList<>();
        List<Obj_Sekolah> list_school = new ArrayList<>();

        list_package.add(new Obj_Paket("NULL", "Pilih paket"));

        list_sex.add("Pilih");
        list_sex.add("L");
        list_sex.add("P");

        list_school.add(new Obj_Sekolah("NULL", "Pilih sekolah"));

        try
        {
            JSONArray result_package = new JSONObject(response[0]).getJSONArray(Config.TAG_JSON);
            JSONArray result_school = new JSONObject(response[1]).getJSONArray(Config.TAG_JSON);

            for (int i = 0; i < result_package.length(); i++)
                list_package.add(new Obj_Paket(result_package.getJSONObject(i).getString(uts.tugas.tutor.paket.Config.ID), result_package.getJSONObject(i).getString(uts.tugas.tutor.paket.Config.PACKAGE)));
            for (int i = 0; i < result_school.length(); i++)
                list_school.add(new Obj_Sekolah(result_school.getJSONObject(i).getString(uts.tugas.tutor.sekolah.Config.ID), result_school.getJSONObject(i).getString(uts.tugas.tutor.sekolah.Config.NAME)));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        ArrayAdapter<Obj_Paket> adapter_package = new ArrayAdapter<Obj_Paket>(Siswa_Show.this, android.R.layout.simple_list_item_1, list_package)
        {
            @Override
            public boolean isEnabled(int position)
            {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
            {
                View view = super.getDropDownView(position, convertView, parent);
                TextView text = (TextView) view;

                if (position == 0)
                {
                    text.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                    text.setTextColor(Color.GRAY);
                    text.setTypeface(null, Typeface.BOLD);
                    text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
                else
                {
                    text.setTextColor(Color.BLACK);
                    text.setPadding(GlobalUtils.GDevice.getPixel(16), 0, GlobalUtils.GDevice.getPixel(16), 0);
                }

                return view;
            }
        };
        ArrayAdapter<String> adapter_sex = new ArrayAdapter<String>(Siswa_Show.this, android.R.layout.simple_list_item_1, list_sex)
        {
            @Override
            public boolean isEnabled(int position)
            {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
            {
                View view = super.getDropDownView(position, convertView, parent);
                TextView text = (TextView) view;

                if (position == 0)
                {
                    text.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                    text.setTextColor(Color.GRAY);
                    text.setTypeface(null, Typeface.BOLD);
                    text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
                else
                {
                    text.setTextColor(Color.BLACK);
                    text.setPadding(GlobalUtils.GDevice.getPixel(16), 0, GlobalUtils.GDevice.getPixel(16), 0);
                }

                return view;
            }
        };
        ArrayAdapter<Obj_Sekolah> adapter_school = new ArrayAdapter<Obj_Sekolah>(Siswa_Show.this, android.R.layout.simple_list_item_1, list_school)
        {
            @Override
            public boolean isEnabled(int position)
            {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
            {
                View view = super.getDropDownView(position, convertView, parent);
                TextView text = (TextView) view;

                if (position == 0)
                {
                    text.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                    text.setTextColor(Color.GRAY);
                    text.setTypeface(null, Typeface.BOLD);
                    text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
                else
                {
                    text.setTextColor(Color.BLACK);
                    text.setPadding(GlobalUtils.GDevice.getPixel(16), 0, GlobalUtils.GDevice.getPixel(16), 0);
                }

                return view;
            }
        };

        adapter_package.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_sex.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_school.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_Package.setAdapter(adapter_package);
        spinner_Sex.setAdapter(adapter_sex);
        spinner_School.setAdapter(adapter_school);

        getSiswa();
    }

    @SuppressWarnings("deprecation")
    private void updateSiswa()
    {
        final String id = editText_Id.getText().toString();
        final String no = editText_No.getText().toString();
        final String name = editText_Name.getText().toString();
        final String birthplace = editText_BirthPlace.getText().toString();
        final String birthday = editText_BirthDay.getText().toString();
        final String address = editText_Address.getText().toString();
        final String representative = editText_Representative.getText().toString();
        final String phone = editText_Phone.getText().toString();

        if(TextUtils.isEmpty(id) || TextUtils.isEmpty(no) || TextUtils.isEmpty(name) || TextUtils.isEmpty(birthplace) || TextUtils.isEmpty(birthday) || TextUtils.isEmpty(address) || TextUtils.isEmpty(representative) || TextUtils.isEmpty(phone))
        {
            GlobalUtils.GToast.show(Siswa_Show.this, "Form tidak boleh kosong");
            return;
        }

        if (spinner_Package.getSelectedItemPosition() == 0 || spinner_Sex.getSelectedItemPosition() == 0 || spinner_School.getSelectedItemPosition() == 0)
        {
            GlobalUtils.GToast.show(Siswa_Show.this, "Spinner kosong terdeteksi");
            return;
        }

        final String pack = ((Obj_Paket) spinner_Package.getSelectedItem()).getId();
        final String sex = spinner_Sex.getSelectedItem().toString();
        final String school = ((Obj_Sekolah) spinner_School.getSelectedItem()).getId();

        class Task extends AsyncTask<Void, Void, String>
        {
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                GlobalUtils.GProgressDialog.show(Siswa_Show.this, "Memperbarui data", "Mohon tunggu...");
            }

            @Override
            protected String doInBackground(Void... voids)
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.ID, id);
                params.put(Config.NO, no);
                params.put(Config.NAME, name);
                params.put(Config.BIRTHPLACE, birthplace);
                params.put(Config.BIRTHDAY, birthday);
                params.put(Config.ADDRESS, address);
                params.put(Config.REPRESENTATIVE, representative);
                params.put(Config.PHONE, phone);
                params.put(Config.SEX, sex);
                params.put(Config.PACKAGE, pack);
                params.put(Config.SCHOOL, school);

                return RequestHandler.sendPostRequest(Config.URL_UPDATE, params);
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                GlobalUtils.GProgressDialog.dismiss();
                GlobalUtils.GToast.show(Siswa_Show.this, s);
                finish();
            }
        }

        new Task().execute();
    }

    @SuppressWarnings("deprecation")
    private void deleteSiswa()
    {
        class Task extends AsyncTask<Void, Void, String>
        {
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                GlobalUtils.GProgressDialog.show(Siswa_Show.this, "Menghapus data", "Mohon tunggu...");
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
                GlobalUtils.GToast.show(Siswa_Show.this, s);
                finish();
            }
        }

        new Task().execute();
    }

    private void confirmDeleteSiswa()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Apakah Anda yakin ingin menghapus siswa ini?");

        builder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        deleteSiswa();
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
    public void onClick(View view)
    {
        if (view == button_Add_Paket)
            startActivity(new Intent(Siswa_Show.this, Paket_Add.class));
        else if (view == button_Add_Sekolah)
            startActivity(new Intent(Siswa_Show.this, Sekolah_Add.class));
        else if (view == button_Update)
            updateSiswa();
        else if (view == button_Delete)
            confirmDeleteSiswa();
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

    @Override
    protected void onResume()
    {
        super.onResume();
        init();
    }
}
