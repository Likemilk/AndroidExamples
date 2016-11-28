package com.likemilk.cho15_notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {
//노티피케이션 을 쓰는이유.
// 메세지와 같이 보기를 ..
// 예를들어 다운이 완료되었을때 알림메세지를 토스트로 전달하게되면 사용자가 다른작업을 할시에 못보고 지나칠수도 있어서 계속해서 다운을 기다리는
// 개그가 벌어질수도 있다.
// 그렇다고 다이얼로그를 띄우게되면 사용자가 모바일 디바이스로 작업중일때는 방해가 되기도한다.
// 5.0 부터는 투명색과 투명색이 아닌것만 구분이 된다.  즉 이미지같은건 안된다.
// 노티피케이션 다른거 . 즉 진행되는것이 보이게 하는것은 나중에 네트워크를 할때! 그때 한다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void basic_notification(View view){
        /*NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        /*
        //상태바에 나타나는 문자열(5.0 부터는 나오지 않는다.)
        //노티피케이션을 써도 되는대 하위버전 지원을 위해 NotificationCompact 를 사용한다.

        builder.setTicker(":Ticker");
        builder.setSmallIcon(R.drawable.ic_launcher);
        //스몰아이콘은 필수적으로 만들어주어야함
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),android.R.drawable.ic_menu_call);
        builder.setLargeIcon(bitmap);
        //라지아이콘은 선택으로
        // 이거 두개는 5.0 이후버전에는 한곳에 한번에나오지만...
        // 이전에는 따로따로 나온다.
        builder.setNumber(100);
        builder.setContentTitle(":title");
        builder.setContentText("text");
        builder.setShowWhen(true);
        Notification noti = builder.build();
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.notify(10,noti);
        */
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setTicker(":Ticker");
        builder.setSmallIcon(R.drawable.ic_launcher);
        //스몰아이콘은 필수적으로 만들어주어야함
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),android.R.drawable.ic_menu_call);
        builder.setLargeIcon(bitmap);
        //라지아이콘은 선택으로
        // 이거 두개는 5.0 이후버전에는 한곳에 한번에나오지만...
        // 이전에는 따로따로 나온다.
        builder.setNumber(100);
        builder.setContentTitle(":title");
        builder.setContentText("text");



        //노티피케이션을 클릭할시에 나타낸다.
        Intent intent = new Intent(this,TestActivity.class);
        PendingIntent pending = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pending);
        //자동으로 삭제되도록 설정한다.
        //오에스마다 각 어플알림
        builder.setAutoCancel(true);

        Notification noti = builder.build();
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.notify(10,noti);

        //코드의 순서 중요하다.
    }

    public void big_picture_notification(View view){
        NotificationCompat.Builder  builder = new NotificationCompat.Builder(this);
                                                //필요한 파라메터 :android.content.Context context
        builder.setContentTitle("title");
        builder.setContentText("text");
        //-------접혓을때의 택스트
        builder.setTicker("ticker DEATH-★");
        //-------맨위에 상태바에서 출력할 메세지    [[Ticker는 5.0  이하만나오고 나머지는 안나온다.
        builder.setSmallIcon(R.drawable.ic_launcher);
        //notification 을 나타나게 하려면 무조건 smallIcon 을 넣어주어야한다.
        NotificationCompat.BigPictureStyle big = new NotificationCompat.BigPictureStyle(builder);
        //호환을 위하여 Support 로 import 를 한다.
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
        big.bigPicture(bitmap);
        big.setBigContentTitle("Big Picture Title");
        big.setSummaryText("Summary");
        //-------펼쳣을때 택스트
        //초기에 이미지가 펼쳐서 나올수도있고 접혀서나올수도있다. 단말기마다 다르다.
        Notification noti = builder.build();

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(20,noti);//notify 앞에 숫자를 다르게 넣으면 서로 다른메세지가 notification 하게 된다.
        //즉 안드로이드 시스템이 서로 다른메세지로 인식하게 한다.
        //public void notify(String tag, int id, Notification notification) 이것을 사용하게 되면
        // tag 에 따라서 id 값이 같아도 tag에 따라 다르게 출력할수도 있음.


    }
    public void big_text_notification(View view){

        //메일은 인박스로나옴. 여러개가 왔을때는 대강 제목정도만.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("content Title");
        builder.setContentText("Context Text");
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setTicker("Ticker");
        // 액션을 지정할때는 PendingIntent 를 사용하여야 한다.

        NotificationCompat.BigTextStyle big = new NotificationCompat.BigTextStyle(builder);
        big.setSummaryText("set Summary Text");
        big.bigText("깊은산 높은ㄷ골 적막한 산하 눈내진 전선으로 우리는 간다 젊은넋 숨져간 그때그자리 상처입은 노송은 나를 잊었나. 전우여 들리는가 한 맺힌목소리 전우여 들리는가 ");

        Notification noti=builder.build();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(30,noti);

    }

    public void in_box_notification(View view){
        //장문의 글이나옴.확인하는 메일이 한통밖에없다면? 최대 5줄까지만나오고 그이후는 .... 으로 생략한다.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentText("content text");
        builder.setContentTitle("content title");
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setTicker("setTicker ");
        //이것들은 전부다 젤리빈에서 바뀐것이다.
        //이런것들을 4.4나 5.0 에서만든것을 2.2에서 돌리면 에러가뜬다 . 아니면 기본 메세지만 나오거나
        //이러한 기능들 여기 지금까지한 다양한종류의 ... 이런것들은 4.1부터 지원을 한다.
        //BigPicture 는 4.1 부터 지원함 그 이하는 지원하지않는다.
        //
        NotificationCompat.InboxStyle inbox = new NotificationCompat.InboxStyle(builder);
        inbox.setSummaryText("set SummaryText");

        inbox.addLine("ㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱ");
        inbox.addLine("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ");
        inbox.addLine("ㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷ");
        inbox.addLine("ㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹ");
        inbox.addLine("ㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂ");
        inbox.addLine("ㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅ");
        inbox.addLine("ㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱ");
        inbox.addLine("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ");
        inbox.addLine("ㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷㄷ");
        inbox.addLine("ㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹ");
        inbox.addLine("ㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂ");
        inbox.addLine("ㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅ");
        //action
        Intent intent1 = new Intent(this,TestActivity.class);
        PendingIntent pending1 = PendingIntent.getActivity(this,0,intent1,0);

        builder.addAction(android.R.drawable.ic_menu_help,"액션1",pending1);

        Intent intent2 = new Intent(this,TestActivity.class);
        PendingIntent pending2 = PendingIntent.getActivity(this,0,intent2,0);

        builder.addAction(android.R.drawable.ic_menu_help,"액션2",pending2);

        Intent intent3 = new Intent(this,TestActivity.class);
        PendingIntent pending3 = PendingIntent.getActivity(this,0,intent3,0);

        builder.addAction(android.R.drawable.ic_menu_help,"액션3",pending3);
        //안드로이드 이용할대 기본아이콘을 이용해주는 편이 좋다. 왜냐하면 사용자가 익숙한아이콘을 사용하여야
        //개연성이란것이 있기때문에 사용자가 쉽게 예측할 수 있다.
        //그러나 앞서말했듯이 4.1버전 미만에서는 펼치거나 접는것 또는 다양한 기능이 이용이 불가능하기때문에
        // 이런걸 만들어도 적용이 안될수가 있으니 이점에 유의하자.
        //getActivity로 엑티비티를 실행하느냐  PendingIntent뒤에 get 으로 뜨면 실행할수있는 퍼포먼스가 실행된다.

        //support 러ㅗ 골라준다.
        Notification noti=builder.build();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(30,noti);

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
