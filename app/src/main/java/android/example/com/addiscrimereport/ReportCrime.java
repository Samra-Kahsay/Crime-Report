package android.example.com.addiscrimereport;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Calendar;

public class ReportCrime extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener{
    private static final int RESULT_LOAD_IMG = 10;
    private TextView mTVDatePicker, mTVTimePicker;
    private Button mButtonUploadImage;
    private ImageView mImageViewUpload;
    private EditText mETTitle,mETDescription,mETLocation;
    private Spinner mSpinnerType;
    public static SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_crime);
        init();
        sqLiteHelper = new SQLiteHelper(this, "CrimeDB.sqlite", null,1);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS CRIMES (Id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR, type VARCHAR, description VARCHAR, date VARCHAR, time VARCHAR, location VARCHAR, image BLOB)");

    }

    private void init(){
        mTVDatePicker = (TextView)findViewById(R.id.tv_Date);
        mTVTimePicker = (TextView)findViewById(R.id.tv_Time);
        mButtonUploadImage = (Button)findViewById(R.id.button_upload_image);
        mImageViewUpload = (ImageView)findViewById(R.id.image_Upload);
        mETTitle = (EditText)findViewById(R.id.et_Title);
        mETDescription = (EditText)findViewById(R.id.et_Description);
        mETLocation = (EditText)findViewById(R.id.et_Location);
        mSpinnerType = (Spinner)findViewById(R.id.spinner_Type);

    }



    public void datePicker(View view) {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(),"date picker");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int date) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DATE,date);
        String currenDate = DateFormat.getDateInstance().format(c.getTime());
        mTVDatePicker.setText(currenDate);
    }

    public void timePicker(View view) {
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(),"timePicker");

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int min) {
        mTVTimePicker.setText("Hour: " + hour + " Minute " + min);


    }

    public void UploadImage(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,RESULT_LOAD_IMG);
    }

    public void ImageViewClicked(View view) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMG){
            if(resultCode  == RESULT_OK){
                if(data != null){
                    Uri selectedImage = data.getData();
                    mImageViewUpload.setImageURI(selectedImage);
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        mImageViewUpload.setImageBitmap(bitmap);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }

            }
        }
    }

    public void ReportCrime(View view) {
        try{
            sqLiteHelper.insertData(
                    mETTitle.getText().toString(),
                    mSpinnerType.getSelectedItem().toString(),
                    mETDescription.getText().toString(),
                    mTVDatePicker.getText().toString(),
                    mTVTimePicker.getText().toString(),
                    mETLocation.getText().toString(),
                    ImageViewToByte(mImageViewUpload)
            );
            Toast toast = Toast.makeText(getApplicationContext(),"Added successfully, submit another report.",Toast.LENGTH_SHORT);
            toast.show();
            mETTitle.setText("");
            mETDescription.setText("");
            mTVTimePicker.setText("");
            mTVDatePicker.setText("");
            mETLocation.setText("");
            mImageViewUpload.setImageResource(R.mipmap.ic_launcher);

        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    private byte[] ImageViewToByte(ImageView image) {

        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream  =  new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray  = stream.toByteArray();
        return byteArray;

    }
}
