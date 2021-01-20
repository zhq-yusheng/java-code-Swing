package com.yu.swing.service;

import com.yu.swing.mapper.AddStudentMapper;
import com.yu.swing.pojo.Student;
import com.yu.swing.pojo.tblesmp;
import com.yu.swing.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

public class AddStudentService {
    public  static void addStudent(Student student){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        AddStudentMapper mapper = sqlSession.getMapper(AddStudentMapper.class);
        mapper.addStudent(student);
        sqlSession.commit();

    }
    public  static void addQStudent(tblesmp tbleps){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        AddStudentMapper mapper = sqlSession.getMapper(AddStudentMapper.class);
        mapper.addQdStudent(tbleps);
        sqlSession.commit();

    }
}
