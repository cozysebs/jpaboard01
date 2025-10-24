package org.jyr.jpademo.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Setter
@Getter
@ToString
//@Data 코끼리 clean -> other 하는데 @Data 경고 떠서 필요한 내용만 분리함
@Builder
@AllArgsConstructor
@NoArgsConstructor //Builder의 세트 all + no
//@Table(name="tbl_board") 테이블 이름은 board 그대로 하려고
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;
    @Column(nullable = false)
    private String title;
    //255 글자는 작아서 크기를 늘리기로 함
    @Column(nullable = false, length = 3000)
    private String content;
    @Column(nullable = false)
    private String author;
    @ColumnDefault(value = "0")
    private int readcount;

    public void updateReadcount() {
        this.readcount = readcount+1;
    }
    public void change(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

