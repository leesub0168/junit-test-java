package com.example.junittestjava.Member;

import com.example.junittestjava.domain.Member;
import com.example.junittestjava.domain.Study;

import java.util.Optional;

public interface MemberService {
    Optional<Member> findById(Long memberId);
    void validate(Long memberId);
    void notify(Study newStudy);
    void notify(Member member);
}
