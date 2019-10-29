package android.example.com.addiscrimereport;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void reportCrimeClicked(View view) {
        Intent intentReport = new Intent(this,ReportCrime.class);
        startActivity(intentReport);
    }

    public void viewCrimeClicked(View view) {
        Intent intent = new Intent(Menu.this,CrimeList.class);
        startActivity(intent);
    }
}