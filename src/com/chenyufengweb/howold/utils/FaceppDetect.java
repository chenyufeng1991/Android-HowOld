package com.chenyufengweb.howold.utils;

import java.io.ByteArrayOutputStream;

import org.json.JSONObject;

import android.graphics.Bitmap;
import android.util.Log;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;

public class FaceppDetect {

	public static final String KEY = "82f0b4f1514a5973d2c083fe45b60d26";
	public static final String SECRET = "lbS-dE4hVejE7AmtOpCRcZ7bbvYSozaO";

	public interface CallBack {
		void success(JSONObject result);

		void error(FaceppParseException exception);
	}

	public static void detect(final Bitmap bm, final CallBack callBack) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// request

				try {
					HttpRequests requests = new HttpRequests(KEY, SECRET, true,
							true);
					Bitmap bmSmall = Bitmap.createBitmap(bm, 0, 0,
							bm.getWidth(), bm.getHeight());
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					bmSmall.compress(Bitmap.CompressFormat.JPEG, 100, stream);

					byte[] arrays = stream.toByteArray();

					PostParameters params = new PostParameters();
					params.setImg(arrays);
					JSONObject jsonObject = requests.detectionDetect(params);

					// Log
					Log.i("TAG", jsonObject.toString());

					if (callBack != null) {
						callBack.success(jsonObject);
					}
				} catch (FaceppParseException e) {
					e.printStackTrace();
					if (callBack != null) {
						callBack.error(e);
					}
				}

			}
		}).start();

	}

}
