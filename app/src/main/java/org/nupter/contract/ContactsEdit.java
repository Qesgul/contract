package org.nupter.contract;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Hashtable;

/*
 * 编辑界面，详情界面，新建联系人界面，通用一个布局，根据标志，更改组件设置
 */
public class ContactsEdit extends BaseActivity implements OnClickListener {

    private EditText et_only_phone;
    private EditText et_only_name;

    EditText edit_name;
    EditText edit_number;
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
        setContentView(R.layout.phoneceshi);
        edit_name = (EditText) super.findViewById(R.id.edit_name);
        edit_number = (EditText) super.findViewById(R.id.edit_number);
        left = (TextView) super.findViewById(R.id.left);
        middle = (TextView) super.findViewById(R.id.middle);
        right = (TextView) super.findViewById(R.id.right);
        call = (Button) super.findViewById(R.id.call);
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        call.setOnClickListener(this);

        et_only_name = (EditText) findViewById(R.id.edit_name);
        et_only_phone = (EditText) findViewById(R.id.edit_number);

        // 得到入口标志，是新建联系人入口，还是详情入口
        Intent it = getIntent();
        id = it.getLongExtra("id", -1);
        isNewContact = (id == -1 ? true : false);
        name = it.getStringExtra("name");
        number = it.getStringExtra("number");

        // 详情入口则设置各组件
        if (!isNewContact) {
            edit_name.setText(name);
            edit_number.setText(number);
            edit_name.setEnabled(false);
            edit_number.setEnabled(false);

            // 新建联系人入口设置各组件
        } else {
            left.setText("取消");
            right.setText("保存");
            middle.setText("新建联系人");
            call.setVisibility(8);
        }
        findViewById(R.id.but).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String name = et_only_name.getText().toString().trim();
                String phone = et_only_phone.getText().toString().trim();
                String contents =name+"\n"+ phone;

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
                    Toast.makeText(ContactsEdit.this, "保存成功！", Toast.LENGTH_SHORT).show();
                    insert(edit_name.getText().toString(), edit_number.getText().toString());
                    startActivity(new Intent(ContactsEdit.this, MainActivity.class));
                }
            } else {
                // 插入数据
                Toast.makeText(ContactsEdit.this, "新建成功！", Toast.LENGTH_SHORT).show();
                insert(edit_name.getText().toString(), edit_number.getText().toString());
                startActivity(new Intent(ContactsEdit.this, MainActivity.class));

            }
            break;

        // 是详情入口拨打电话还是编辑入口删除联系人
        case R.id.call:
            if (isEdit) {
                getContentResolver().delete(Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(id)), null, null);
                Toast.makeText(ContactsEdit.this, "该联系人已删除！", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ContactsEdit.this, MainActivity.class));
            } else {

                Toast.makeText(ContactsEdit.this, "拨打电话！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number.trim()));

                startActivity(intent);
            }

            break;
        }

    }

    // 手机联系人插入
    public void insert(String name, String number) {
        ContentValues value = new ContentValues();
        Uri rawContactUri = getContentResolver().insert(RawContacts.CONTENT_URI, value);
        long rawContactId = ContentUris.parseId(rawContactUri);
        value.clear();
        value.put(Data.RAW_CONTACT_ID, rawContactId);
        value.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
        value.put(StructuredName.GIVEN_NAME, name);
        getContentResolver().insert(Data.CONTENT_URI, value);

        value.clear();
        value.put(Data.RAW_CONTACT_ID, rawContactId);
        value.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
        value.put(Phone.NUMBER, number);
        value.put(Phone.TYPE, Phone.TYPE_MOBILE);
        getContentResolver().insert(Data.CONTENT_URI, value);

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
