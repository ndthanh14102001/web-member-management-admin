package com.member_management.service;

import com.member_management.modules._Member;
import com.member_management.repository.MemberRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    
    public List<_Member> findAllUsageInformation() {
        List<_Member> members = memberRepository.getAllMembersInformation();
        return members;
    }
    
    public List<_Member> searchMembersByName(String keyword) {
        return memberRepository.searchMembersByName(keyword);
    }
    
    public void addMember(_Member member){
        memberRepository.save(member);
    }
    
    public void deleteMember(int memberId) {
        memberRepository.deleteById(memberId);
    }
    
    public _Member getMemberById(int memberId) {
        return memberRepository.findByMaTV(memberId);
    }

    public void updateMember(int memberId, _Member updatedMember) {
        _Member existingMember = memberRepository.findByMaTV(memberId);
        if (existingMember != null) {
            existingMember.setHoTen(updatedMember.getHoTen());
            existingMember.setPassword(updatedMember.getPassword());
            existingMember.setEmail(updatedMember.getEmail());
            existingMember.setKhoa(updatedMember.getKhoa());
            existingMember.setNganh(updatedMember.getNganh());
            existingMember.setSdt(updatedMember.getSdt());
            memberRepository.save(existingMember);
        }
    }
    
}
