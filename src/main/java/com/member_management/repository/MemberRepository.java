package com.member_management.repository;

import com.member_management.modules._Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<_Member, Integer> {
    
}
