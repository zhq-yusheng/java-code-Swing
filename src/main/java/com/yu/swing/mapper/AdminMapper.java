package com.yu.swing.mapper;


import com.yu.swing.pojo.Tbluser;
import org.apache.ibatis.annotations.Select;



public interface AdminMapper {
    @Select("select uName,PWD from tbluser where uName=#{uName} and PWD=#{PWD}")
    Tbluser query(Tbluser admin);
}
