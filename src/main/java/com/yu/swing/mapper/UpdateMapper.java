package com.yu.swing.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface UpdateMapper {
    @Update("update tblesmp set sEtime=#{sEtime},code=#{code} where sStime=#{sStime} and sNo=#{sNo} ")
    void updateStudent(@Param("sStime")String sStime,@Param("sNo") String sNo,@Param("sEtime") String sEtime, @Param("code") String code);
}
