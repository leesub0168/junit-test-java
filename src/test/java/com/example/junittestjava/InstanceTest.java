package com.example.junittestjava;

import com.example.junittestjava.domain.Study;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 클래스마다 인스턴스 생성
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// 테스트 순서지정은 PER_CLASS와 관계없이도 보장되나,
// 같은 인스턴스를 사용하는게 상태를 공유하므로 좀더 신뢰성이 높다고 볼수 있음
public class InstanceTest {
    // 기본적으로 junit은 테스트간에 의존성을 없애기 위해
    // 테스트 메소드마다 클래스 인스턴스를 새로 만들어서 실행
    // 테스트의 순서는 junit 내부로직에 따라 정해져있기는 하나, 그것에 의존해서는 안됨

    int value = 1;

    @Order(1)
    @FastTest
    void create_study() {
        System.out.println(this);
        Study study = new Study(value);
        System.out.println(study.getLimit());
    }

    @Order(2)
    @SlowTest
    void create_study2() {
        value++;
        System.out.println(this);
        System.out.println("create2 " + value);
    }

    @Order(3)
    @SlowTest
    @Disabled
    void create_study3() {
        value++;
        System.out.println(this);
        System.out.println("create3 " + value);
    }

    @BeforeAll // 인스턴스 생성이 PER_CLASS인 경우는 static일 필요가 없음.
    static void beforeAll() {
        System.out.println("before all");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("after all");
    }

}
