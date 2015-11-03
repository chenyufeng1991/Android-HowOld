package com.chenyufengweb.howold.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class MyImageAdapter extends BaseAdapter {
	private Context context;
	private int[] image;

	public MyImageAdapter(Context context, int[] image) {
		super();
		this.context = context;
		this.image = image;
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public Object getItem(int position) {
		return image[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ImageView imageView = new ImageView(context);
		imageView.setBackgroundResource(image[position % 13]);
		imageView.setLayoutParams(new Gallery.LayoutParams(400, 300));
		imageView.setScaleType(ScaleType.FIT_XY);

		return imageView;
	}

}
