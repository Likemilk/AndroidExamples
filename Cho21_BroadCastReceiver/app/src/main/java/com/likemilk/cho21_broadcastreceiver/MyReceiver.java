package com.likemilk.cho21_broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
        // 이 코드는 구글에서 적용하고 있는 코드 .

        //Action의 이름을 추출한다. 여러사건들을 이름값을 뽑아서 분기하면된다.
        //나는 분기하기가 싫다! 그렇다면 사건마다 브로드캐스트를 따로따로 만들어도 된다.
        String name = intent.getAction();
        //onReceive의 두번째파라메터인 intent 를 얻어서 액션을 구한다음에 if 문으로 분기를 한다!
        Toast.makeText(context,"aaaaaaaaaaaaaaaa",Toast.LENGTH_SHORT).show();
        if(name.equals("android.provider.Telephony.SMS_RECEIVED")){
            String str = "";
            Bundle bundle = intent.getExtras();
            if(bundle != null){
                Object [] obj = (Object[]) bundle.get("pdus");
                //pdus 라는 이름으로 객체를 뽑아낸다.
                SmsMessage []msg = new SmsMessage[obj.length];
                for(int i = 0; i<obj.length;i++){
                    msg[i] = SmsMessage.createFromPdu((byte[])obj[i]);
                }
                for(int i = 0; i<msg.length;i++){
                    str += msg[i].getOriginatingAddress()+"  :   "+ msg[i].getMessageBody() +"\n";
                                // 전화번호가 나온다.               // 문자 메세지가 나온다. 많이 입력하게 되면 MMS 로 반환하게 된다.
                                                                    //  그렇기때문에 우리는 SMS 로 하였기때문에 MMS 는 못읽어 오게된다.
                }
                Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
            }
        }
    }
    //테스트 툴 안드로이드->디바이스 모니터로 전화나 문자를 보낼수있다.
}


