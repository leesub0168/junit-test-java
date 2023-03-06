package com.example.junittestjava;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.time.Duration;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {
    @Test
    @Disabled
    @DisplayName("스터디 테스트")
    void create_new() {
        Study study = new Study(-10);
// 요렇게 assert를 따로 해서 테스트를 진행하면 3개의 테스트중 먼저 실패한 테스트만 감지됨. (현재 assertEquals, assertTrue 모두 실패해야하는 상황)
//        assertNotNull(study);
//        // 람다로 메세지를 주는 이유 : 메세지의 형태가 복잡한 경우(상황에 따라 연산이 필요한 경우),
//        //                         람다식으로 해놓으면 문자열 연산을 필요한 경우에만 하기 때문에 성능상 더 유리함.
//        assertEquals(StudyStatus.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면 DRAFT 상태여야 한다.");
//        assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야한다.");

        // 이렇게 assertAll을 사용해서, executable로 넘기면 실패한 테스트를 모두 확인할 수 있다.
        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(),
                    () -> "스터디를 처음 만들면 " + StudyStatus.DRAFT + " 상태다."),
                () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야한다.")
        );
    }

    @Test
    @Disabled
    @DisplayName("예외발생 테스트")
    void create_new_again() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        String message = exception.getMessage();
        assertEquals("limit은 0보다 커야한다.", message);
    }

    @Test
    @Disabled
    @DisplayName("시간 초과 테스트")
    void create_new_time() {
        // 이 경우는 해당 기능이 10초 걸린다면 테스트시 10초를 다 기다리게됨.
        assertTimeout(Duration.ofMillis(100), () -> {
            new Study(10);
            Thread.sleep(300);
        });
    }

    @Test
    @Disabled
    @DisplayName("시간 초과 테스트2")
    void create_new_time_limit() {
        // assertTimeoutPreemptively는 기능이 걸리는 시간을 다 기다리지 않고, 지정한 시간이 되면 종료함.
        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            new Study(10);
            Thread.sleep(300);
        });
        // TODO ThreadLocal : 트랜잭션과 관련된 경우에는 assertTimeoutPreemptively는 사용하지 않는 걸 권장.
        // 임의로 스레드를 sleep하기 때문에, 실제 스레드는 실행되어 반영되버리는 경우가 생길 수 있기 때문.
    }

    @Test
    @DisplayName("assertThat")
    void create_new_assertThat() {
        Study actual = new Study(10);
        assertThat(actual.getLimit()).isGreaterThan(0);
    }

    @Test
    void create_new_assumeTrue() {
        String test_env = System.getenv("TEST_ENV");
        System.out.println(test_env);
        assumeTrue("LOCAL".equalsIgnoreCase(System.getenv("TEST_ENV")));
    }

    @Test
    @DisplayName("조건에 따라 테스트 실행")
    void create_new_assumingThat() {
        String test_env = System.getenv("TEST_ENV");
        System.out.println(test_env);

        assumingThat("LOCAL".equalsIgnoreCase(test_env), () -> {
            System.out.println("local");
            Study actual = new Study(100);
            assertThat(actual.getLimit()).isGreaterThan(0);
        });

        assumingThat("leesoo".equalsIgnoreCase(test_env), () -> {
            System.out.println("leesoo");
            Study actual = new Study(10);
            assertThat(actual.getLimit()).isGreaterThan(0);
        });
    }

    @Test
    @DisplayName("특정 OS에서 테스트 동작")
    @EnabledOnOs({OS.WINDOWS, OS.MAC}) // 특정 os에서만 테스트가 동작
    void create_new_os() {
        System.out.println("It's Windows");
    }

    @Test
    @DisplayName("특정 OS에서 제외처리")
    @DisabledOnOs(OS.MAC)
    void create_disable_os() {
        System.out.println("It's MAC");
    }

    @Test
    @DisplayName("특정 자바버전에서 동작")
    @EnabledOnJre({JRE.JAVA_8, JRE.JAVA_11})
    void create_enable_java() {
        System.out.println("It's Java 8, Java 11");
    }

    @Test
    @DisplayName("환경변수값이 일치할때 테스트 동작")
    @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "LOCAL")
    void use_annotation_env() {
        Study actual = new Study(10);
        assertThat(actual.getLimit()).isGreaterThan(0);
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("before All");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("after All");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("before each");
    }

    @AfterEach
    void afterEach() {
        System.out.println("after each");
    }
}