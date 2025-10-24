package org.jyr.jpademo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class PageRequestDTO {
    @Builder.Default
    private int page =1; //페이지 번호는 1 넣고
    @Builder.Default
    private int size =3; // 보통 10을 넣는데 레코드 많이 넣기 힘드니까아..
    private String link; //검색어 따라다니까 많아져서?
    private String type;
    private String keyword;

    //title은 t, content는 c, 작성자는 w 로 해서 한자한자 하면 문자열 스트림이 되게 / t,c,w, tc,tw,twc
    public String[] getTypes(){
        if(type==null || type.isEmpty()){
            return null;
        }
        return type.split(""); //twc=>types[0]="t" 1은 w, 2는 c 배열 형태로 리턴
    }
    //pageable은 data domain 가지고 와야해
    // bno는 오름차순 title은 내림차순 다양하다면 props 여러 형태로 들어간다면
    public Pageable getPageable(String...props){
         return PageRequest.of(this.page-1, size, Sort.by(props).descending());
         //page 0으니까 1-1은 0 , Sort.by 현재 내가 전달한 props로 정렬하겠다. id로 descending 내림차순(정렬차순)하겠다
    }
    public String getLink(){
        if(link==null){
            StringBuilder builder = new StringBuilder();
            builder.append("page="+this.page);
            builder.append("&size="+this.size);
            if(type!=null && type.length()>0){
                builder.append("&type="+type);
            }
           if(keyword!=null && keyword.length()>0){
               builder.append("&keyword="+keyword);
           }
           link=builder.toString(); //스트링형태로 링크를 만들고
        }
        return link;
    }
}
