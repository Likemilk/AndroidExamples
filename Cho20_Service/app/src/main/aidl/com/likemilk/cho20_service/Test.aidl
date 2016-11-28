// Test.aidl
package com.likemilk.cho20_service;

// Declare any non-default types here with import statements

interface Test {
//app->new->AIDL 을 하고나면 aidl 이라는 폴더가 생긴다.
//인터페이스를 정의하는것.
//여기서는 호출할 메서드만 선언해주면된다..
// project로 변경하고 app->build-> generated -> source -> aidl ->test 를 보면 아무것도표시가 되지 않는다.
//여기서 빌드를 하면 debug에  패키지 가 생기면서 test 가 만들어진다.
   int getData();
   //접근 제한을 해제한다 자동으로 만들어지기때문이다.


    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
  */

}
