package android.example.com.addiscrimereport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button)findViewById(R.id.button_get_started);
    }

    public void getStartedClicked(View view) {
        Intent intent = new Intent(this,Menu.class);
        startActivity(intent);
    }
}
