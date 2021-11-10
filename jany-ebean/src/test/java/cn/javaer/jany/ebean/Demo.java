package cn.javaer.jany.ebean;

import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Demo {
    public static final DemoFinder find = new DemoFinder();
    @Id
    private Long id;

    private String name;

    @WhenCreated
    LocalDateTime createdDate;

    @WhenModified
    LocalDateTime modifiedDate;
}