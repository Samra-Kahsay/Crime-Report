package android.example.com.addiscrimereport;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class CrimeList extends AppCompatActivity {
    ListView listView;
    ArrayList<Crime> list;
    CrimeListAdapter adapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_crimes);

        listView = (ListView)findViewById(R.id.list_view);
        list = new ArrayList<>();
        adapter = new CrimeListAdapter(this,R.layout.crime_list,list);
        listView.setAdapter(adapter);

        Cursor cursor = ReportCrime.sqLiteHelper.getData("SELECT * FROM CRIMES");
        list.clear();

        while (cursor.moveToNext()) {

            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String type = cursor.getString(2);
            String description = cursor.getString(3);
            String date = cursor.getString(4);
            String time = cursor.getString(5);
            String location = cursor.getString(6);
            byte[] image = cursor.getBlob(7);

            list.add(new Crime(id, title, description, date, location, image));
        }
        adapter.notifyDataSetChanged();



    }


}
