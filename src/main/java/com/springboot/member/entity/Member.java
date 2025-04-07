package com.springboot.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Table
public class Member {
    @Id
    private Long memberId;

    private String email;

    private String name;

    private String phone;

    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    // 데이터가 저장 또는 업데이트될 때
    // 별도의 날짜/시간 정보를 추가하지 않아도
    // Auditing 기능을 통해 자동으로 날짜/시간 정보가 테이블에 저장

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column("last_modified_at")
    private LocalDateTime modifiedAt;

    public Member(String email) {
        this.email = email;
    }

    public Member(String email, String name, String phone) {
        this.email = email;
        this.name = name;
        this.phone = phone;
    }
    public enum MemberStatus {
        MEMBER_ACTIVE("활동중"),
        MEMBER_SLEEP("휴면 상태"),
        MEMBER_QUIT("탈퇴 상태");

        @Getter
        private String status;

        MemberStatus(String status) {
           this.status = status;
        }
    }
}
