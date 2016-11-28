package com.likemilk.cho13_fragmentlist;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

// 안드로이드 OS가읽을수 있게 아이디값을 정하게 하기위함임.
//즉 아이디를 @android:id/list d이렇게  이것은 안드로이드OS가 기억하고 있는 ID 값이다.
// 이 예제는 리스트 프레그먼트를 사용하기위함이며 메모리면이나 프로세스면에서
// 엑티비티에서 정의하는 리스트뷰 보다 훨씬 효율면에서 괜찮다.

//app -> new -> fragment(blank)-> fragment.java -> 이렇게 ListFragment 상속.


/**
 * A simple {@link Fragment} subclass.
 */

// 상속 Fragment-> ListFragment로 지정한다
// ListFragment는 아이디가 @android:id/list 인 ListView 를 찾아준다 from android OS 에서 말이다

public class TestFragment extends ListFragment {
    //support 로 임포트를 한다. 모든 안드로이드 환경에서 적용될 수 있도록 말이다.
    //프래그먼트로 리스트뷰를 만들면 글씨만 터치해야 이벤트가 발생한다 그렇기때문에
    // wrap_content 로 되어있다는 소리다 그러므로 ㄹ이것을 matchParent로 맞춰주어야한다.
    final static String TAG ="Liekmilk우유좋아:";
    final static boolean D =true;

    String data1[]={"항목1","항목2","항목3","항목4",
            "항목5","항목6","항목7","항목8"};

    public TestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ArrayAdapter<String> adpater = new ArrayAdapter<String>(inflater.getContext(),android.R.layout.simple_list_item_1,data1);
        //메인 컨텍스틀 얻는멉. 인플레이터는 컨텍스트를 가지고 있다.
        // 프레그먼트는 액티비티에 의존되어서 얹어지므로 얹어지는 parent를 구하는것이다. inflater!!로 말이다.

        setListAdapter(adpater);
        // return inflater.inflate(R.layout.fragment_test, container, false); 이렇게 되어있던것을
        //▽ 이렇게 바꾼다.
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        // view 와 inflate 의 상속관계가 궁금해요. View 는 Object 바로 밑단 LayoutInflater 도 바로 Object 바로 밑단
        // 인데 inflate 는 View 를 반환하는 메서드입니다. 그렇다면 LayoutInflater 에서 구해진값이나 정보들을
        // View 로 패키징해서 값을 반환하는것인가요?
        return view;
    }
}


































