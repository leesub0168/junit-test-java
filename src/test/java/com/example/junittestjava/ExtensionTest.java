package com.example.junittestjava;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

//@ExtendWith(FindSlowTestExtension.class) // 선언적인 등록
public class ExtensionTest {

    // 프로그래밍 등록
    @RegisterExtension
    static FindSlowTestExtension findSlowTestExtension = new FindSlowTestExtension(1005L);

    @Test
    void slow_test() throws InterruptedException {
        Thread.sleep(1005L);
    }

    @SlowTest
    void slow_test2() throws InterruptedException {
        Thread.sleep(1005L);
    }

}
