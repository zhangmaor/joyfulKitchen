package com.app.joyfulkitchen.activity.mychild;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.app.joyfulkitchen.activity.R;
import com.app.joyfulkitchen.util.RoundImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;


public class MyInformationActivity extends Activity implements View.OnClickListener {



    private LinearLayout dateLinearLayout,city_linearLayout;
    private TextView dateText,city_name;
    private DatePickerDialog dialog;
    private int year,monthOfYear,dayOfMonth;
    /**
     * 获取头像的位置
     */
    private LinearLayout ivHead;
    private RoundImageView roundImage_network;
    private Button btnTakePhoto;//拍照
    private Button btnPhotos;//相册
    private Bitmap head;//头像Bitmap
    private static String path="/sdcard/myHead/";//sd路径


    private LayoutInflater inflater;
    private View layout;
    private AlertDialog MyDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.my_activity_information);


        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        monthOfYear = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String text = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                dateText.setText(text);
            }
        },year,monthOfYear,dayOfMonth);
        initView();

    }

    private void initView() {
        //初始化控件
        dateLinearLayout = (LinearLayout) findViewById(R.id.dateLinearLayout);
        city_linearLayout = (LinearLayout) findViewById(R.id.city_linearLayout);
        roundImage_network = (RoundImageView) findViewById(R.id.roundImage_network);
        ivHead = (LinearLayout) findViewById(R.id.ivHead);
        dateText = (TextView) findViewById(R.id.dateText);
        city_name = (TextView) findViewById(R.id.city_name);
        dateLinearLayout.setOnClickListener(this);
        city_linearLayout.setOnClickListener(this);
        ivHead.setOnClickListener(this);

        inflater = LayoutInflater.from(this);
        layout = inflater.inflate(R.layout.my_btn_photos, null);
        btnPhotos = (Button) layout.findViewById(R.id.btnPhotos);
        btnTakePhoto = (Button) layout.findViewById(R.id.btnTakePhoto);
        btnPhotos.setOnClickListener(this);
        btnTakePhoto.setOnClickListener(this);

        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");//从Sd中找头像，转换成Bitmap
        if(bt!=null){
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);//转换成drawable
            roundImage_network.setImageDrawable(drawable);
        }else{
            /**
             *	如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
             *
             */
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dateLinearLayout:
                dialog.show();
                break;
            case R.id.city_linearLayout:
                Intent intent = new Intent(MyInformationActivity.this,CityActivity.class);
                startActivityForResult(intent, 5);
                break;
            case R.id.btnPhotos://从相册里面取照片
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                break;
            case R.id.btnTakePhoto://调用相机拍照
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                        "head.jpg")));
                startActivityForResult(intent2, 2);//采用ForResult打开
                break;
            case R.id.ivHead:
                AlertDialog.Builder MyBuilder = new AlertDialog.Builder(this);
                MyBuilder.setView(layout);
                MyDialog = MyBuilder.create();
                MyDialog.show();
                /*MyDialog.getWindow().setLayout(600, 380);*/
                break;
            default:
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case 5:
                if (resultCode == RESULT_OK) {
                    Bundle b = data.getExtras(); //data为B中回传的Intent
                    String str = b.getString("FromB");//str即为回传的值
                    city_name.setText(str);
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());//裁剪图片
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory()
                            + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));//裁剪图片
                }

                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if(head!=null){
                        /**
                         * 上传服务器代码
                         */
                        setPicToView(head);//保存在SD卡中
                        MyDialog.dismiss();
                        roundImage_network.setImageBitmap(head);//用ImageView显示出来
                    }
                }
                break;
            default:
                break;
        }
    }




    /**
     * 调用系统的裁剪
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }
    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName =path + "head.jpg";//图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}
