package com.yu.swing.mapper;

import com.yu.swing.pojo.Student;
import com.yu.swing.pojo.tblesmp;
import org.apache.ibatis.annotations.Insert;

public interface AddStudentMapper {
    @Insert("insert into tblemp(sNo,sName,sSex,sQsh,sZw) values(#{sNo},#{sName},#{sSex},#{sQsh},#{sZw})")
    void addStudent(Student student);

    @Insert("insert into tblesmp(sZw,sNo,sStime,code) values(#{sZw},#{sNo},#{sStime},#{code})")
    void addQdStudent(tblesmp tpleps);
}
