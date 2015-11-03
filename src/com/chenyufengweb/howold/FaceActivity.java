package com.chenyufengweb.howold;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.chenyufengweb.howold.R;
import com.chenyufengweb.howold.adapter.MyImageAdapter;
import com.chenyufengweb.howold.utils.FaceppDetect;
import com.facepp.error.FaceppParseException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.widget.AdapterView.OnItemSelectedListener;

public class FaceActivity extends Activity implements OnClickListener,
		OnItemSelectedListener {

	private Context context;
	private Gallery gallery;
	private ImageSwitcher imageSwitcher;
	private ScrollView scrollView;

	private MyImageAdapter myImageAdapter;

	private int[] image = { R.drawable.item1, R.drawable.item2,
			R.drawable.item3, R.drawable.item4, R.drawable.item5,
			R.drawable.item6, R.drawable.item7, R.drawable.item8,
			R.drawable.item9, R.drawable.item10, R.drawable.item11,
			R.drawable.item12, R.drawable.item13 };

	private static final int PICK_CODE = 0X110;
	private static final int MSG_SUCCESS = 0X111;
	private static final int MSG_ERROR = 0X112;

	private ImageView mPhoto;
	private ImageButton mGetImage;
	private TextView mDetect;
	// private TextView mTip;
	private View mWaitting;

	private String mCurrentPhotoStr;
	private Bitmap mPhotoImg;

	private Paint mPaint;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case MSG_SUCCESS:
				mWaitting.setVisibility(View.INVISIBLE);
				mDetect.setVisibility(View.INVISIBLE);
				JSONObject rs = (JSONObject) msg.obj;
				prepareRsBitmap(rs);
				mPhoto.setImageBitmap(mPhotoImg);

				break;

			case MSG_ERROR:
				mWaitting.setVisibility(View.INVISIBLE);
				String errorMsg = (String) msg.obj;

				if (TextUtils.isEmpty(errorMsg)) {
					// mTip.setText("ERROR");
				} else {
					// mTip.setText(errorMsg);
				}
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_face);
		this.context = this;

		initViews();

		initEvents();

		mPaint = new Paint();

		myImageAdapter = new MyImageAdapter(context, image);
		gallery.setAdapter(myImageAdapter);

	}

	protected void prepareRsBitmap(JSONObject rs) {

		Bitmap bitmap = Bitmap.createBitmap(mPhotoImg.getWidth(),
				mPhotoImg.getHeight(), mPhotoImg.getConfig());
		Canvas canvas = new Canvas(bitmap);
		canvas.drawBitmap(mPhotoImg, 0, 0, null);
		try {
			JSONArray faces = rs.getJSONArray("face");
			int faceCount = faces.length();
			// mTip.setText("find:" + faceCount);
			for (int i = 0; i < faceCount; i++) {
				// 拿到单独face对象
				JSONObject face = faces.getJSONObject(i);
				JSONObject posObj = face.getJSONObject("position");
				float x = (float) posObj.getJSONObject("center").getDouble("x");
				float y = (float) posObj.getJSONObject("center").getDouble("y");
				float w = (float) posObj.getDouble("width");
				float h = (float) posObj.getDouble("height");

				x = x / 100 * bitmap.getWidth();
				y = y / 100 * bitmap.getHeight();
				w = w / 100 * bitmap.getWidth();
				h = h / 100 * bitmap.getHeight();

				mPaint.setColor(Color.YELLOW);
				mPaint.setStrokeWidth(3);

				// 画box
				canvas.drawLine(x - w / 2, y - h / 2, x - w / 2, y + h / 2,
						mPaint);
				canvas.drawLine(x + w / 2, y - h / 2, x + w / 2, y + h / 2,
						mPaint);
				canvas.drawLine(x - w / 2, y - h / 2, x + w / 2, y - h / 2,
						mPaint);
				canvas.drawLine(x - w / 2, y + h / 2, x + w / 2, y + h / 2,
						mPaint);
				// get age and gender
				int age = face.getJSONObject("attribute").getJSONObject("age")
						.getInt("value");
				Log.i("AGE", age + "");
				String gender = face.getJSONObject("attribute")
						.getJSONObject("gender").getString("value");

				Bitmap ageBitmap = buildAgeBitmap(age, "Male".equals(gender));

				int ageWidth = ageBitmap.getWidth();
				int ageHeight = ageBitmap.getHeight();

				if (bitmap.getWidth() < mPhoto.getWidth()
						&& bitmap.getHeight() < mPhoto.getHeight()) {

					float ratio = Math.max(
							bitmap.getWidth() * 1.0f / mPhoto.getWidth(),
							bitmap.getHeight() * 1.0f / mPhoto.getHeight());

					ageBitmap = Bitmap.createScaledBitmap(ageBitmap,
							(int) (ageWidth * ratio),
							(int) (ageHeight * ratio), false);
				}

				canvas.drawBitmap(ageBitmap, x - ageBitmap.getWidth() / 2, y
						- h / 2 - ageBitmap.getHeight(), null);

				mPhotoImg = bitmap;

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private Bitmap buildAgeBitmap(int age, boolean isMale) {

		TextView tv = (TextView) mWaitting.findViewById(R.id.id_age_gender);
		tv.setText(age + "");
		if (isMale) {
			tv.setCompoundDrawablesWithIntrinsicBounds(getResources()
					.getDrawable(R.drawable.male), null, null, null);

		} else {
			tv.setCompoundDrawablesWithIntrinsicBounds(getResources()
					.getDrawable(R.drawable.female), null, null, null);
		}
		tv.setDrawingCacheEnabled(true);
		Bitmap bitmap = Bitmap.createBitmap(tv.getDrawingCache());
		tv.destroyDrawingCache();

		return bitmap;
	}

	private void initEvents() {

		mGetImage.setOnClickListener(this);
		mDetect.setOnClickListener(this);
		gallery.setOnItemSelectedListener(this);
	}

	private void initViews() {
		mPhoto = (ImageView) findViewById(R.id.id_photo);
		mGetImage = (ImageButton) findViewById(R.id.id_getImage);
		mDetect = (TextView) findViewById(R.id.id_detect);
		// mTip = (TextView) findViewById(R.id.id_tip);
		mWaitting = findViewById(R.id.id_waiting);

		gallery = (Gallery) findViewById(R.id.id_gallery);
		imageSwitcher = (ImageSwitcher) findViewById(R.id.id_imageSwitcher);
		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		scrollView.setVerticalScrollBarEnabled(false);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_getImage:

			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setType("image/*");
			startActivityForResult(intent, PICK_CODE);

			break;

		case R.id.id_detect:
			mWaitting.setVisibility(View.VISIBLE);

			FaceppDetect.detect(mPhotoImg, new FaceppDetect.CallBack() {

				@Override
				public void success(JSONObject result) {
					Message msg = Message.obtain();
					msg.what = MSG_SUCCESS;
					msg.obj = result;
					mHandler.sendMessage(msg);

				}

				@Override
				public void error(FaceppParseException exception) {
					Message msg = Message.obtain();
					msg.what = MSG_ERROR;
					msg.obj = exception.getErrorMessage();
					mHandler.sendMessage(msg);

				}

			});

			break;

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		if (requestCode == PICK_CODE) {
			if (intent != null) {

				Uri uri = intent.getData();
				Cursor cursor = getContentResolver().query(uri, null, null,
						null, null);
				cursor.moveToFirst();

				int idx = cursor
						.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
				mCurrentPhotoStr = cursor.getString(idx);
				cursor.close();

				resizePhoto();
				mPhoto.setImageBitmap(mPhotoImg);
				// mTip.setText("Click Detect-->");
				mDetect.setVisibility(View.VISIBLE);
			}
		}

		super.onActivityResult(requestCode, resultCode, intent);
	}

	private void resizePhoto() {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;

		BitmapFactory.decodeFile(mCurrentPhotoStr, options);

		double ratio = Math.max(options.outWidth * 1.0d / 1024f,
				options.outHeight * 1.0d / 1024f);
		options.inSampleSize = (int) Math.ceil(ratio);

		options.inJustDecodeBounds = false;

		mPhotoImg = BitmapFactory.decodeFile(mCurrentPhotoStr, options);

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		imageSwitcher.setBackgroundResource(image[position % 13]);

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}
}
