package com.likemilk.cho12_customadaptor;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    String data[] = {
            "항목1","항목2","항목3","항목4","항목5",
            "항목6","항목7","항목8","항목9","항목10",
            "항목11","항목12","항목13","항목14","항목15",
    };
    TextView text1;
    ListView list1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1 = (TextView) findViewById(R.id.textView);
        list1 = (ListView) findViewById(R.id.listView);


        list1.setAdapter(new ListAdapter());
    }

    class ListAdapter extends BaseAdapter {
        BtnListener l;
        public ListAdapter(){

            l = new BtnListener();
        }
        @Override//중요!! return 을 직접 구현해주어야한다.       항목의 갯수를 반환하는 메서드
        public int getCount() {
            return data.length;  //String []data의 갯수를 넘겨야한다. 왜냐하면 저항목의 갯수에따라 리스트가 결정될것이기 때문이다.
        }

        @Override//중요!!
        public Object getItem(int position) {
            return null;
        }

        @Override//중요!!
        public long getItemId(int position) {
            return 0;
        }

        @Override///중요!! return 을 직접 구현해주어야한다.      총 3개의 과정을 거쳐야한다 Position 번째의 항목을 구성하는 메서드이다.
        public View getView(int position, View convertView, ViewGroup parent) {
            //보여지게 될때마다 생성되는 객체. 보여지게되면서 position 을 만들며 tag 값에다가 position값을 넣어준다.
            //이것이 실행될때마다 만들어 주는용의 메서드가 맞다.!!!
            //이것으로 리스트뷰를 만드는이유는 디바이스환경에 따라 10개이상이되어야 스크롤이생기는 디바이스도 있는반면에 5개가 넘으면 스크롤이생기는 디바이스도 있고
            // 매우 가변적인 상황이 닥치게될거라는 소리다. 그렇기때문에 하나부터 여기에서 리스트 뷰를 생성한다.!

            //1, 항목 뷰를 만들어줘야한다.
            //재사용 가능한 뷰가 없다면 만들어준다.
            //있으면 그냥 쓰면된다. 뷰는 xml 가지고 만들면된다.
            if(convertView == null){
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.row,null);
                //이 만들어진 뷰에 넣을게 있다면 뭔가 넣겠지만 넣을게 없기 때문에 null 로 한다.

            }
            //2, 항목 뷰에 데이터를 설정해준다.

            // 여기서 저 메서드가 실행될때 (그 때는 안보이다가 드래그해서 다시 보고싶을때 [이때는 아마 재 사용일것이다.]
            // 이메서드가 실행될때마다 한번씩 실행한다.
            // 그렇게되면 전혀 새로운 객체가 만들어지게되는것이기도 하다.)
            TextView text2 = (TextView)convertView.findViewById(R.id.textView2);
            Button btn1 = (Button)convertView.findViewById(R.id.button);
            Button btn2 = (Button)convertView.findViewById(R.id.button2);

            btn1.setOnClickListener(l);
            btn2.setOnClickListener(l);
            btn1.setTag(position);
            btn2.setTag(position);


            text2.setText(data[position]);
            //3, 만들어진 항목 뷰를 반환해준다.

            return convertView;
        }
    }
    class BtnListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            int position = (Integer) v.getTag();
            // 몇번째줄의 버튼인지 확인은 이걸로
            int id = v.getId();
            // 어떤버튼인지 확인및 대조는 이걸로! 근대 이건 잘 안쓴다구
            if(id==R.id.button){
                Toast.makeText(getBaseContext(),"첫번째버튼 :"+position+1+"번째줄"+"|"+id,Toast.LENGTH_SHORT).show();
                text1.setText("첫번째버튼 :"+id+"|"+position);
            }else if(id==R.id.button2){
                Toast.makeText(getBaseContext(),"두번째버튼 :"+position+1+"번째줄"+"|"+id,Toast.LENGTH_SHORT).show();
                text1.setText("두번째버튼 :"+id+"|"+position);
            }
        }
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
