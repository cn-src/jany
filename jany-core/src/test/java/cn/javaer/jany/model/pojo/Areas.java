package cn.javaer.jany.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Areas {
    String area1;
    String area2;
    String area3;
    Long sort;

    public Areas(String area1, String area2, String area3) {
        this.area1 = area1;
        this.area2 = area2;
        this.area3 = area3;
    }
}