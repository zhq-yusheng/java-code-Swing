package com.yu.swing.service;

import com.yu.swing.mapper.UpdateMapper;
import com.yu.swing.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

public class UpdateSevice {

    public static void update(String sStime,String sNo,String sEtime,String code){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UpdateMapper mapper = sqlSession.getMapper(UpdateMapper.class);
        mapper.updateStudent(sStime,sNo,sEtime,code);

    }
}
