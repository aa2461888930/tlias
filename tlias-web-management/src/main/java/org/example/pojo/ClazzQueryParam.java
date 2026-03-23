package org.example.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data // 需要至少@Data，设置get/set方法，输出才不是对象地址值
public class ClazzQueryParam {
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate begin;
    @DateTimeFormat(pattern = "yyyy-MM-dd")  // 注意LocalDate 与 LocalDateTime
    private LocalDate end;
    private Integer page = 1; // 页码
    private Integer pageSize = 10;
}
