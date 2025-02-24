package projects.blog.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import projects.blog.common.entity.BaseEntity;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    private boolean fromSocial;

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    public void addMemberRole(MemberRole memberRole) {
        roleSet.add(memberRole);
    }
}
