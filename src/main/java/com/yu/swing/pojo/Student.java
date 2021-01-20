package com.yu.swing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String sNo; // 学号
    private String sName; // 姓名
    private String sSex; // 性别
    private int sQsh; // 寝室号
    private String sZw; // 指纹

}
