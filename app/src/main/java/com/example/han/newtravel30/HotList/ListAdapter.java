package com.example.han.newtravel30.HotList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.han.newtravel30.AR.Touris;
import com.example.han.newtravel30.AR.useAPI;
import com.example.han.newtravel30.R;

import java.util.List;

/**
 * Created by Vincent on 2017/10/1.
 */

public class ListAdapter extends BaseAdapter{
    Context context;
    List<Touris> rowItem;

    public ListAdapter(Context context, List<Touris> rowItem) {
        this.context = context;
        this.rowItem = rowItem;
    }

    private class ViewHolder {
        ImageView thumbnailURL;
        TextView name;
        TextView address;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public int getCount() {
        return rowItem.size();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = mInflater.inflate(R.layout.hotlist, null);
            final ViewHolder holder = new ViewHolder();

            holder.thumbnailURL = (ImageView) view.findViewById(R.id.imageViewThum);
            holder.name = (TextView) view.findViewById(R.id.textViewName);
            holder.address = (TextView) view.findViewById(R.id.textViewAddress);

            final Touris rowPos = rowItem.get(i);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    final Bitmap bitmpa = useAPI.getImageFromURL(rowPos.getImageURL(), context.getResources(), R.drawable.car);

                    // Display image
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            holder.thumbnailURL.setImageBitmap(bitmpa);
                        }
                    });
                }
            }).start();

            holder.name.setText(rowPos.getName());
            holder.address.setText(rowPos.getAddress());
        }

        return view;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

}
