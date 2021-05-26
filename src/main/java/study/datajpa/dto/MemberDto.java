package study.datajpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import study.datajpa.entity.Member;

@Data
@AllArgsConstructor
public class MemberDto {

    private Long id;
    private String userName;
    private String teamName;

    // Dto 안에서 Member 엔티티를 받는것은 상관없다. 필드로만 사용하지 않으면 된다!
    public MemberDto(Member member) {
        this.id = member.getId();
        this.userName = member.getUsername();
    }
}
