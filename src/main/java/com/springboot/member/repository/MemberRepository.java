package com.springboot.member.repository;

import com.springboot.member.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MemberRepository extends R2dbcRepository<Member, Long> {
    Mono<Member> findByEmail(String email);
    Flux<Member> findAllBy(Pageable pageable);
}
