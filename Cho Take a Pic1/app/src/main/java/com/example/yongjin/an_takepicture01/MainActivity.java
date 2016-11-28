package com.example.yongjin.an_takepicture01;

import  android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;


/*
안드로이드에서 사진을 찍을때
미리보기를 위해서는 서페이스 뷰를 생성해야한다

dlfrrlTMr

-서페이스뷰 미리보기를위해 랜더링처리를해주는 녀석 이녀석에게는 가속도의 역할이 있다.
-이녀석을 컨트롤하기위해서는 Holder 를 생성해서 컨트롤 해주어야한다.
-지금 이 예제에 사용될 서페이스 폴더의 타입은 SUFACE_TYPE_PUSH_BUFFERS 를 설정해주어야 한다.
-카메라 객체를 만든후 카메라 객체에 서페이스 홀더 객체를 지정해야한다 .setPreviewDisplay() ;

01, 안드로이드 캡쳐
CameraApp -Intent [Camera] //기존에 사용되고 있던녀석을 사용 - CameraApp 이녀석을 불러와서 엡에다가 보여준다.

해당 버튼에서 사진찍기를 실행할것이다. 이때 사용하는 카메라엡은 기존에 지정되어있는 카메라엡을 사용하겠다.
기존 단말에 있는 카메라 엡을 실행 new Intent(MediaStore.ACTION_IMAGE_CAPTRUE);
그다음 intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(file));
풋 잇스트라를 이용해서 해당 인텐트의 데이터를 넣어주어야 한다.


카메라 해상도를 줄때
BitmapFactory.Options options = new BitmapFactory.Options(); 를 이용한다.
카메라를 가로로 눞히는 작업  방향을 잡기위한.
try{
    ExifiInterface exif = new ExifInterface(file.~~~~~);
}catch{
}

*/
//카메라 사진찍기(인텐트를 이용해 단말의 카메라 앱을 실행하여 사진찍는 방법)
public class MainActivity extends ActionBarActivity {
    public static final int REQUEST_IMAGE_CAPTURE = 1001;

    File file = null;
    ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView1 = (ImageView) findViewById(R.id.imageView1);

        try {

            file = createFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /* 사진찍기 버튼을 클릭하면 인텐트를 생성하여 카메라 앱을 띄워줌
       이때 단말의 카메라 앱이 동작하여 사진을 찍어 지정한 위치에 저장되고,
       액티비티에 사진을 보여주는 것은 메인액티비티의 onActivityResult()로 돌아가 처리
     */
    public void onButton1Clicked(View v) {
        //인텐트를 생성하여 단말의 카메라 앱을 띄워줌
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //사진 저장위치 지정
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

        //인텐트를 처리할 수 있는 앱이 존재하는지 여부를 체크하여 단말의 카메라 앱 실행
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    //사진 파일 저장 패스 및 파일 생성
    private File createFile() throws IOException {
        String imageFileName = "test.jpg";
        //저장 패스를 파일로 생성
        File storageDir = Environment.getExternalStorageDirectory();
        File curFile = new File(storageDir, imageFileName);

        return curFile;
    }

    /* 카메라 앱 화면에서 메인 액티비티로 돌아오는 경우 onActivityResult() 호출
       이때 카메라 앱에서 찍은 사진은 설정한 파일에 저장되어 있으므로,
       이 파일을 이용해 비트맵 객체를 만들어, 이미지뷰에 넣어주면 사진을 화면에 표시할 수 있음
       - 비트맵 객체를 만들 때는 BitmapFactory.decodeFile() 메소드를 사용
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //카메라 해상도 옵션을 지정
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;//사진을 1/8로 사이즈를 줄여줌
            if (file != null) {
                //사진 파일에서 이미지를 읽어들여 비트맵 이미지를 생성
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

                /* ----------------------------------------------------------------
                   - 90도 회전되어 저장된 사진을 정상으로 보이도록 사진을 회전시키는 로직
                   ----------------------------------------------------------------*/
                try {
                    /* ExifInterface 클래스를 이용하여 EXIF 객체 정보를 가져올 수 있음
                       - EXIF : 디지털 사진의 이미지 정보(크기, 화소, 방향, 카메라정보, 노출정도 등)
                     */
                    // EXIF 객체 생성
                    ExifInterface exif = new ExifInterface(file.getAbsolutePath());

                    // 사진의 ORIENTATION 정보를 가져옴
                    int exifOrientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

                    // 사진의 ORIENTATION 정보를 기준으로 사진을 회전 각도로 변환하는 메소드 호출
                    int exifDegree = exifOrientationToDegrees(exifOrientation);

                    // 사진을 정상으로 회전하는 메소드 호출
                    bitmap = rotate(bitmap, exifDegree);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                /* --------------------------------------------------------------------*/

                //비트맵 이미지를 이미지뷰에 넣어준다.
                imageView1.setImageBitmap(bitmap);
            } else {
                Toast.makeText(getApplicationContext(), "File is null.", Toast.LENGTH_LONG).show();
            }
        }
    }

    /* EXIF 정보를 회전각도로 변환하는 메서드
       - @param exifOrientation EXIF 회전각
       - @return 실제 각도
     */
    public int exifOrientationToDegrees(int exifOrientation)    {
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        }
        else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        }
        else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }

        return 0;
    }

    /*  비트맵 사진을 정상으로 회전시키는 메소드
        - @param bitmap 비트맵 이미지
        - @param degrees 회전 각도
        - @return 회전된 이미지
     */
    public Bitmap rotate(Bitmap bitmap, int degrees)  {
        if(degrees != 0 && bitmap != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) bitmap.getWidth() / 2,
                    (float) bitmap.getHeight() / 2);

            try  {
                Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0,
                        bitmap.getWidth(), bitmap.getHeight(), m, true);
                if(bitmap != converted) {
                    bitmap.recycle();
                    bitmap = converted;
                }
            }
            catch(OutOfMemoryError ex)  {
                // 메모리가 부족하여 회전을 시키지 못할 경우 그냥 원본을 반환
            }
        }
        return bitmap;
    }
}
