package uts.tugas.tutor.siswa;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.crud.library.GlobalUtils;
import com.crud.library.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uts.tugas.tutor.R;
import uts.tugas.tutor.paket.Obj_Paket;
import uts.tugas.tutor.paket.Paket_Add;
import uts.tugas.tutor.sekolah.Obj_Sekolah;
import uts.tugas.tutor.sekolah.Sekolah_Add;
import uts.tugas.tutor.siswa.Config;

public class Siswa_Add extends AppCompatActivity implements View.OnClickListener
{
    private EditText editText_No, editText_Name, editText_BirthPlace, editText_BirthDay, editText_Address, editText_Representative, editText_Phone;
    private Spinner spinner_Package, spinner_Sex, spinner_School;
    private Button button_Add_Paket, button_Add_Sekolah, button_Add, button_View;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.siswa_add);

        GlobalUtils.GDevice.scale = getResources().getDisplayMetrics().density;

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
        button_Add = findViewById(R.id.button_Add);
        button_View = findViewById(R.id.button_View);

        button_Add_Paket.setOnClickListener(this);
        button_Add_Sekolah.setOnClickListener(this);
        button_Add.setOnClickListener(this);
        button_View.setOnClickListener(this);
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
                GlobalUtils.GProgressDialog.show(Siswa_Add.this, "Memuat data", "Mohon tunggu...");
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

        ArrayAdapter<Obj_Paket> adapter_package = new ArrayAdapter<Obj_Paket>(Siswa_Add.this, android.R.layout.simple_list_item_1, list_package)
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
        ArrayAdapter<String> adapter_sex = new ArrayAdapter<String>(Siswa_Add.this, android.R.layout.simple_list_item_1, list_sex)
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
        ArrayAdapter<Obj_Sekolah> adapter_school = new ArrayAdapter<Obj_Sekolah>(Siswa_Add.this, android.R.layout.simple_list_item_1, list_school)
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
    }

    @Override
    public void onClick(View view)
    {
        if (view == button_Add_Paket)
            startActivity(new Intent(Siswa_Add.this, Paket_Add.class));
        else if (view == button_Add_Sekolah)
            startActivity(new Intent(Siswa_Add.this, Sekolah_Add.class));
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        init();
    }
}
