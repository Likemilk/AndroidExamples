package com.likemilk.cho9_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {
//create Layout XML 에다가 체크하고
//콜백함수를 쓰거나 자동완성을하는것에는 체크를 푸른다 그이유는
//콜백함수를 자동완성해주긴하나 어짜피 수정해야할것이고 굳이 안서도되는 군더더기 코드들이 많아지기떄문이다.
// :Fragment 가 관리하는 View 객체
    private View view;
    private TextView txt1;
    private Button btn1;
    public FirstFragment() {
        // Required empty public constructor


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    //LayoutInflater 는메뉴를 만들대 사용하는것을 메뉴인퓰레이터 xml 을 통해서 객체를 만들기위한 객체
    //xml의 내용을 읽어들여서 데이터값을 셋팅해서 만들어주는것이다.
    //이 메서드가 리턴하는 뷰 객체가 화면에 나타나게 된다.



        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_first, container, false); 이렇게 코드를 작성하지 않는것이 좋다.
        //view 를 반환하여 view 를 제어하는 편이 좋다.
        view = inflater.inflate(R.layout.fragment_first, container, false);
        //view 아래에다가 widget 들을 만들어주어야한다.
        txt1 = (TextView) view.findViewById(R.id.txt1);
        btn1 = (Button) view.findViewById(R.id.btn1);
        //프레그먼트에 관련된 것들은 겨이 프레그먼트 메서드안에다가 만들어놓는것이 좋다.
        //의존도가 높은 프로그램일 수록 안좋은프로그램이다. 의존도가 높은 프로그램이 안좋은프로그램.
        //의존성을 떨어트리는 기본. 메소드로 값을 저장하고 메소드로 값을 호출하는 방식으로하는게 가장의존도를 떨어트리는
        //좋은 방법 . 가장 좋다. 죠오오앙.
        btn1.setOnClickListener(new BtnListener());



        return view;
        //여기서 반환하는 객체를 그린다.

    }
    class BtnListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Button btn = ((Button)view.findViewById(R.id.btn1));
            //view 를 쓰는이유는 프레그먼트에서는 findViewById 를 지원하지않는다 그렇기때문에
            //view 를 통해 액티비티를 얻어서 findViewById 를 사용해야하는것이다.
            if(btn1==btn) {
                txt1.setText("출력값: 안녕하세영 ");
            }
        }
    }


}
