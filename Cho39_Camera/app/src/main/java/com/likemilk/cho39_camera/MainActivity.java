package com.likemilk.cho39_camera;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;

//이거 다시해야함 . 이미지가... ㅅㅂ .. 모르겠어 ! 모르겟다고 용량이 전혀 줄지않아.
public class MainActivity extends ActionBarActivity {

    ImageView imgView;
    String picPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgView =(ImageView)findViewById(R.id.imageView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //이 액티비티로 돌아올때 받는 값
        if(requestCode==100){
        //기본사진 찍기 는 썸네일이 나온다. 화질이 안좋다.

            if(resultCode == RESULT_OK){
                //사진찍고 ok 누르면 ok값이 cancel 누르면 cancel 값이 넘어온다.
                if(data!=null){
                    Bitmap bit = (Bitmap)data.getExtras().get("data");
                    imgView.setImageBitmap(bit);
                }
            }
        }else if(requestCode == 200) { //직접 읽어오는 방식.
            if (resultCode == RESULT_OK) {
                rebuild_image(picPath);
            }
        }else if(requestCode==300){
            if(resultCode == RESULT_OK){
                //사용자가 선택한 이미지의 데이터를 가져올 수 있는 URI 를 추출
                Uri picUri = data.getData();
                //이미지 파일 이름 컬럼
                String col[] = {MediaStore.Images.Media.DATA};
                ContentResolver resolver = getContentResolver();
                Cursor c = resolver.query(picUri,col,null,null,null);
                c.moveToNext();
                int idx=c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                //실제 파일명 추출
                String source = c.getString(idx);
                //파일명을 이용해 bitmap 생성
                Bitmap bit = BitmapFactory.decodeFile(source);
                imgView.setImageBitmap(bit);

                //외부에 있는 저장소의 경로를 안드로이드 os 에 저장되어있기때문에
                // Resolver ContentProvider 로 불러와야한다. 파일 이미지의 경로값을 불러와서 보여주는 방식으로 되어야한다.

            }
        }
    }

    public void camera1(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult( intent, 100);
        //썸네일로 사진을 찍는다.
    }
    public void camera2(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //지정할 파일의 이름 경로 설정 imageName
        String imageName="test_"+System.currentTimeMillis()+".jpg";// 이름중복방지를 위해.
        //경로에 파일이름을 붙혀준다.
        picPath= Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+imageName;
        //인텐트를 통해 넘어가지 못하는 데이터이다.
        //intent에 저장될 파일의 경로를 지정
        File file = new File(Environment.getExternalStorageDirectory(),"/");
        Uri uri = Uri.fromFile(new File(file, imageName));
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        //액티비티 실행
        startActivityForResult(intent,200);
    }
    public void album(View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent,300);
        //이거 이미지는 contentProvider로 얻와야한다.
    }
    //사진 조작 메서드
    public void rebuild_image(String source){
        try{
            ExifInterface exif = new ExifInterface(source);
            int ori = 0;
            int degree = 0;
            if(exif != null){
                //회전 각도값 추출
                ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,-1); //ExifInterface. comma 를 찍고 보면 이미지에대한 정보를 추출할 수 있다. ALTITUDE 고도측정이 안된다. 장비가 비쌈.
                                                                                //LATITUDE 위도 LONGTITUDE 경도 gps 를 키고 찍으면 값이 세팅이된다.
                //회전값은 무조건 들어 가있다 만약 이이름이 들어가있는 값을 추출하지못했다면 이미지 오류이다.
                if(ori != -1){
                    switch(ori){
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            degree = 90;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            degree = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            degree = 270;
                            break;
                    }
                }
            }
            // RAM 같은 주기억 메모리.
            //메모리가 부족할경우 LargeHeam =true 로 설정한다.
            //이제 파일로부터 이미지를 생성
            //용량은 25mb 로 할당이된다.
            //GC청소는 액티비티에 보이는것을 청소하는게아니다. 보통 어플개발할때 LargeHeap을 신경쓰지않고하지만 성능사용이 필요할 경우에는
            //이미지를 그린다.
            Bitmap bitmap = BitmapFactory.decodeFile(source);
            //이미지 조작 정보를 담고 있을 객체
            Matrix m = new Matrix();
             //2f 이차원 3f 3차원 4f 4차원  openGL 3d 엔진이므로 graphics 를 사용한다.
            //조작정보 세팅
            float w = bitmap.getWidth() * 0.3f;//0.3 은 30퍼센트로 줄이겠다.
            float h = bitmap.getHeight() * 0.3f;//0.3은 30퍼센트로 줄이겠다.
            if(w>1 && h>1){
                m.postScale(0.3f,0.3f); //크기를 설정하는게 postScale 밖에없다 0.3 크기만큼 줄이거나 늘린다.
            }
            //각도 정보
            m.postRotate(degree);
            //조작된 이미지 정보를 추출
            Bitmap rotateBitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),m,false);
            //bitmap은 원본이미지
            //두번째 세번째 x,y 뜯어내고자하는 정도


            //imgView.setImageBitmap(rotateBitmap); 이미지를 화면에 띄우는부분이다. 그러나 우리는 이미지를 줄인 데이터의 용량을 확인하기위함이니... 주석처리함.
            File file = new File(picPath);
            FileOutputStream fos = new FileOutputStream(file);
            rotateBitmap.compress(Bitmap.CompressFormat.JPEG,30,fos);
            //1파라매터  확장자로 저장하겠다.
            //2파라매터 화질을 의미한다
            //3 w저장할 이미지 소스를 의미한다

            fos.flush();
            fos.close();

            Intent intent1 = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.parse(source));
            Intent intent2 = new Intent(Intent.ACTION_MEDIA_MOUNTED);
            sendBroadcast(intent1);
            sendBroadcast(intent2);

            imgView.setImageBitmap(rotateBitmap);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //이미지 뷰에 이미지를 셋팅하는 메서드
   public void setImageView(String path){

   }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
