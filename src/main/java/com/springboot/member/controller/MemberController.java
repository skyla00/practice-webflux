package com.springboot.member.controller;

import com.springboot.member.dto.MemberDto;
import com.springboot.member.mapper.MemberMapper;
import com.springboot.member.service.MemberService;
import com.springboot.utils.UriCreator;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/v12/members")
public class MemberController {
    private final static String MEMBER_DEFAULT_URL = "/v12/members";
    private final MemberService memberService;
    private final MemberMapper mapper;

    public MemberController(MemberService memberService, MemberMapper mapper) {
        this.memberService = memberService;
        this.mapper = mapper;
    }
    // MemberService 클래스의 메서드를 호출 해 mono sequence 를 추가적으로 연결할 수 있음.
    // >> service 리턴 타입이 mono dla.
    // >> 기존에는 member 객체를 리턴 했지만, Mono로 래핑한 값을 리턴함.

    @PostMapping
    public Mono<ResponseEntity> postMember(@Valid @RequestBody Mono<MemberDto.Post> requestBody) {
        // request body Mono 로 래핑해서 전달 받음 : 전달받은 객체에 blocking 요소가 포함되지 않도록 함.
        // operator 체인을 바로 연결.
        return requestBody
                // createMember() 메서드를 호출해 회원정보 저장 처리를 바로 이어서 수행
                .flatMap(post -> memberService.createMember(mapper.memberPostToMember(post)))
                .map(createdMember -> {
                    URI location = UriCreator.createUri(MEMBER_DEFAULT_URL, createdMember.getMemberId());
                    return ResponseEntity.created(location).build();
                });
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@PathVariable("member-id") @Positive long memberId,
                                      @Valid @RequestBody Mono<MemberDto.Patch> requestBody) {
        Mono<MemberDto.Response> response =
                requestBody
                        .flatMap(patch -> {
                            patch.setMemberId(memberId);
                            return memberService.updateMember(mapper.memberPatchToMember(patch));
                        })
                        .map(member -> mapper.memberToMemberResponse(member));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") @Positive long memberId) {
        Mono<MemberDto.Response> response =
                memberService.findMember(memberId)
                        .map(member -> mapper.memberToMemberResponse(member));
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMembers(@RequestParam("page") @Positive int page,
                                     @RequestParam("size") @Positive int size) {
        Mono<List<MemberDto.Response>> response =
                // 페이지네이션 처리를 위해 PageRequest 객체를 만들어서 service 로 전달함.
                memberService.findMembers(PageRequest.of(page - 1, size, Sort.by("memberId").descending()))
                        .map(pageMember -> mapper.membersToMemberResponses(pageMember.getContent()));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") long memberId) {
        Mono<Void> result = memberService.deleteMember(memberId);
        // responseEntity 에 넘겨주는 응답 데이터가 단순 객체가 아닌 Mono로 래핑된 객체임.
        return new ResponseEntity(result, HttpStatus.NO_CONTENT);
    }
}
