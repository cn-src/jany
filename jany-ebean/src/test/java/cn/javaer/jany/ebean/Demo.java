package cn.javaer.jany.ebean;

import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Demo extends BaseModel<Demo> {
    public static final DemoFinder find = new DemoFinder();

    @Id
    private Long id;

    private String name;

    @WhenCreated
    LocalDateTime createdDate;

    @WhenModified
    LocalDateTime modifiedDate;
}