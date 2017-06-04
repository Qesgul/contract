package org.nupter.contract;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Hashtable;

/**
 * Created by Axes on 2016/4/13.
 */
public class ewm_result extends BaseActivity implements View.OnClickListener {
	private TextView et_only_phone;
	private TextView et_only_name;

	TextView edit_name;
	TextView edit_number;
	TextView left;
	TextView right;
	TextView middle;
	Button call;
	long id;
	String name;
	String number;
	boolean isEdit = false;
	boolean isNewContact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_edit);
		Intent intent=getIntent();
		String data=intent.getStringExtra("str");
		String[] s = data.split("\n");
		String nm=s[0];
		String ph=s[1];
		Log.d("ewm_result",data);
		edit_name = (TextView) super.findViewById(R.id.edit_name);
		edit_number = (TextView) super.findViewById(R.id.edit_number);
		left = (TextView) super.findViewById(R.id.left);
		middle = (TextView) super.findViewById(R.id.middle);
		right = (TextView) super.findViewById(R.id.right);
		call = (Button) super.findViewById(R.id.call);
		left.setOnClickListener(this);
		right.setOnClickListener(this);
		call.setOnClickListener(this);

		et_only_name = (TextView) findViewById(R.id.edit_name);
		et_only_phone = (TextView) findViewById(R.id.edit_number);

			left.setText("取消");
			right.setText("保存");
			middle.setText("新建联系人");
			edit_name.setText(nm);
		edit_number.setText(ph);
			call.setVisibility(8);

		findViewById(R.id.but).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = et_only_name.getText().toString().trim();
				String phone = et_only_phone.getText().toString().trim();
				String contents = name+"\n" + phone ;

				try {
					Bitmap bm = qr_code(contents, BarcodeFormat.QR_CODE);

					ImageView img = (ImageView) findViewById(R.id.img_only);

					img.setImageBitmap(bm);
				} catch (WriterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}

	// 各个监听处理
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.left:
				onBackPressed();
				break;

			// 不是新建入口，则分是详情入口还是编辑接口
			case R.id.right:
				if (!isNewContact) {
					if (!isEdit) {
						right.setText("保存");
						left.setText("取消");
						middle.setText("编辑联系人");
						call.setText("删除联系人");
						edit_name.setEnabled(true);
						edit_number.setEnabled(true);
						isEdit = true;

					} else {
						Toast.makeText(ewm_result.this, "保存成功！", Toast.LENGTH_SHORT).show();
						insert(edit_name.getText().toString(), edit_number.getText().toString());
						startActivity(new Intent(ewm_result.this, MainActivity.class));
					}
				} else {
					// 插入数据
					Toast.makeText(ewm_result.this, "新建成功！", Toast.LENGTH_SHORT).show();
					insert(edit_name.getText().toString(), edit_number.getText().toString());
					startActivity(new Intent(ewm_result.this, MainActivity.class));

				}
				break;

			// 是详情入口拨打电话还是编辑入口删除联系人
			case R.id.call:
				if (isEdit) {
					getContentResolver().delete(Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(id)), null, null);
					Toast.makeText(ewm_result.this, "该联系人已删除！", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(ewm_result.this, MainActivity.class));
				} else {

					Toast.makeText(ewm_result.this, "拨打电话！", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number.trim()));

					startActivity(intent);
				}

				break;
		}

	}

	// 手机联系人插入
	public void insert(String name, String number) {
		ContentValues value = new ContentValues();
		Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, value);
		long rawContactId = ContentUris.parseId(rawContactUri);
		value.clear();
		value.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
		value.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
		value.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name);
		getContentResolver().insert(ContactsContract.Data.CONTENT_URI, value);

		value.clear();
		value.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
		value.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
		value.put(ContactsContract.CommonDataKinds.Phone.NUMBER, number);
		value.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
		getContentResolver().insert(ContactsContract.Data.CONTENT_URI, value);

	}

	public Bitmap qr_code(String string, BarcodeFormat format)
			throws WriterException {
		MultiFormatWriter writer = new MultiFormatWriter();
		Hashtable<EncodeHintType, String> hst = new Hashtable<EncodeHintType, String>();
		hst.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix matrix = writer.encode(string, format, 400, 400, hst);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (matrix.get(x, y)) {
					pixels[y * width + x] = 0xff000000;
				}

			}
		}
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		// 通过像素数组生成bitmap,具体参考api
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}
}
