package projects.blog.member.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projects.blog.member.entity.Member;
import projects.blog.security.dto.UserDto;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.email=:email")
    Optional<Member> findByEmail(String email);

    @Query("SELECT new projects.blog.security.dto.UserDto(m.id, m.email, m.name) " +
            "FROM Member m " +
            "WHERE m.email = :email")
    Optional<UserDto> findUserDtoByEmail(String email);

    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from Member m where m.email=:email and m.fromSocial=:social")
    Optional<Member> findByEmailAndSocial(String email, boolean social);


}
