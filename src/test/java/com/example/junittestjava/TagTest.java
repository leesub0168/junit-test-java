package com.example.junittestjava;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagTest {

    @Test
    @DisplayName("태그 테스트 fast")
    @Tag("fast")
    void tag_fast() {
        System.out.println("fast");
    }

    @Test
    @DisplayName("태그 테스트 slow")
    @Tag("slow")
    void tag_slow() {
        System.out.println("slow");
    }

    @FastTest
    @DisplayName("커스텀 태그 테스트 fast")
    void custom_tag_fast() {
        System.out.println("custom fast");
    }

    @SlowTest // 기존에 @Test, @Tag를 붙여 표시했던걸 커스텀 태그로 만들어서 붙일수 있음.
    @DisplayName("커스텀 태그 테스트 slow")
    void custom_tag_slow() {
        System.out.println("custom slow");
    }
}