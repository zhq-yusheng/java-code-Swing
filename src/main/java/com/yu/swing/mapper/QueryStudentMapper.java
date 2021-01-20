package com.yu.swing.mapper;

import com.yu.swing.view.StudentView;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface QueryStudentMapper {

    @Select("select sId,sName from tblemp")
    List<StudentView> queryStudent();

    @Select("select sId,sName from tblesmp,tblemp where tblemp.sId=tblemp.sId and sStime=#{date}")
    List<StudentView> queryQdStudent(String date);

    @Select("select code from tblesmp where sStime=#{sStime} and sNo=#{sNo}")
    String query(@Param("sStime") String sStime, @Param("sNo") String sNo);

}
