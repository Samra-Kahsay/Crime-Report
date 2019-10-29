package android.example.com.addiscrimereport;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CrimeListAdapter extends BaseAdapter  {
    private Context context;
    private int layout;
    private ArrayList<Crime> crimList;

    public CrimeListAdapter(Context context, int layout, ArrayList<Crime> crimList) {
        this.context = context;
        this.layout = layout;
        this.crimList = crimList;
    }

    @Override
    public int getCount() {
        return crimList.size();
    }

    @Override
    public Object getItem(int position) {
        return crimList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView mImageView;
        TextView mtvTitle,mtvDescription,mtvDate,mtvLocation;
        }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout,null);
            holder.mtvTitle = (TextView)row.findViewById(R.id.tv_title);
            holder.mtvDescription = (TextView)row.findViewById(R.id.tv_description);
            holder.mtvDate = (TextView)row.findViewById(R.id.tv_date);
            holder.mtvLocation = (TextView)row.findViewById(R.id.tv_location);
            holder.mImageView = (ImageView) row.findViewById(R.id.imageView_image);
            row.setTag(holder);


        }
        else {
            holder = (ViewHolder)row.getTag();

        }
        Crime crime = crimList.get(position);

        holder.mtvTitle.setText(crime.getTitle());
        holder.mtvDescription.setText(crime.getDescription());
        holder.mtvLocation.setText(crime.getLocation());
        holder.mtvDate.setText(crime.getDate());
        byte[] crimeImage = crime.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(crimeImage,0,crimeImage.length);
        holder.mImageView.setImageBitmap(bitmap);


        return row;
    }
}
