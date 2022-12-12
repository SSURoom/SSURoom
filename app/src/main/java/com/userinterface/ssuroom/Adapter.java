package com.userinterface.ssuroom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class Adapter extends PagerAdapter {

    private byte[][] images = new byte[3][];
    private LayoutInflater inflater;
    private Context context;
    private boolean loading;

    public Adapter(Context context)
    {
        loading=false;
        this.context = context;
    }

    public Adapter(Context context,byte[][] images){
        this.context=context;
        finishLoading(images);
    }


    private void finishLoading(byte[][] images){
        this.images=images;
        loading=true;
    }


    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout)object);

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.d("final_log",""+position);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.slider, container, false);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
        if(loading){
            Bitmap bitmap = BitmapFactory.decodeByteArray( images[position], 0, images[position].length ) ;
            imageView.setImageBitmap(bitmap);
        }
        else{
            imageView.setImageResource(R.drawable.loading_image);
        }

        container.addView(v);
        return v;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.invalidate();
    }
}
