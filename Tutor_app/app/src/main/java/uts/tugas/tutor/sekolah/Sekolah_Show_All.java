package uts.tugas.tutor.sekolah;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.crud.library.GlobalUtils;
import com.crud.library.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import uts.tugas.tutor.R;

public class Sekolah_Show_All extends AppCompatActivity
{
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sekolah_show_all);

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent intent = new Intent(Sekolah_Show_All.this, Sekolah_Show.class);
                HashMap<String, String> map = (HashMap<String, String>) adapterView.getItemAtPosition(i);
                String id = map.get(Config.ID);
                intent.putExtra(Config.TAG_ID, id);
                startActivity(intent);
            }
        });

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                getJSON();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @SuppressWarnings("deprecation")
    private void getJSON()
    {
        class Task extends AsyncTask<Void, Void, String>
        {
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                GlobalUtils.GProgressDialog.show(Sekolah_Show_All.this, "Mengambil data", "Mohon tunggu...");
            }

            @Override
            protected String doInBackground(Void... voids)
            {
                return RequestHandler.sendGetRequest(Config.URL_GET_ALL);
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
        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        try
        {
            JSONArray result = new JSONObject(json_data).getJSONArray(Config.TAG_JSON);

            for (int i = 0; i < result.length(); i++)
            {
                JSONObject obj = result.getJSONObject(i);

                HashMap<String, String> sekolah = new HashMap<>();
                sekolah.put(Config.ID, obj.getString(Config.ID));
                sekolah.put(Config.NAME, obj.getString(Config.NAME));

                list.add(sekolah);
            }
        }
        catch (JSONException ignored) {}

        ListAdapter adapter = new SimpleAdapter(Sekolah_Show_All.this, list, R.layout.list_item, new String[] {Config.ID, Config.NAME}, new int[] {R.id.id, R.id.name});
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        getJSON();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
            finish();

        return true;
    }
}
