package com.member_management.repository;

import com.member_management.modules._Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<_Member, Integer> {

    @Query("FROM _Member m")
    List<_Member> getAllMembersInformation();

    @Modifying
    @Query("DELETE FROM _Member m WHERE m.maTV = :memberId")
    void deleteById(@Param("memberId") int memberId);

    @Query("SELECT m FROM _Member m WHERE m.maTV = :maTV")
    _Member findByMaTV(@Param("maTV") int maTV);

    @Query("SELECT m FROM _Member m WHERE m.maTV = :maTV")
    List<_Member> findByMaTV(@Param("maTV") String maTV);

    @Query("FROM _Member m WHERE m.hoTen LIKE %:keyword%")
    List<_Member> searchMembersByName(@Param("keyword") String keyword);

    @Query("SELECT m FROM _Member m")
    List<_Member> findAllMembers();

}
