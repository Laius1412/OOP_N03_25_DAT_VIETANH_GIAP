package com.example.servingwebcontent;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member add(Member member) {
        return memberRepository.save(member);
    }

    public Optional<Member> findByName(String name) {
        return memberRepository.findByName(name);
    }

    public void update(String oldName, Member updated) {
        memberRepository.findByName(oldName).ifPresent(existing -> {
            existing.setName(updated.getName());
            existing.setGender(updated.getGender());
            existing.setDob(updated.getDob());
            existing.setAddress(updated.getAddress());
            existing.setPhone(updated.getPhone());
            memberRepository.save(existing);
        });
    }

    public void deleteByName(String name) {
        memberRepository.deleteByName(name);
    }
}


