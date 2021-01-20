package com.yu.swing.service;

import com.yu.swing.mapper.QueryStudentMapper;
import com.yu.swing.utils.MybatisUtils;
import com.yu.swing.view.StudentView;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

public class QueryStudentService {

   public static  List<String> getStudentNames(){
        List<String> names=new ArrayList<>();
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        QueryStudentMapper mapper = sqlSession.getMapper(QueryStudentMapper.class);
        List<StudentView> studentViews = mapper.queryStudent();

        for (StudentView studentView : studentViews) {
            String sName = studentView.getSName();
            names.add(sName);
        }
        return names;
    }
    public static List<String> queryQdStudent(String date){
        List<String> names=new ArrayList<>();
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        QueryStudentMapper mapper = sqlSession.getMapper(QueryStudentMapper.class);
        List<StudentView> studentViews = mapper.queryQdStudent(date);

        for (StudentView studentView : studentViews) {
            String sName = studentView.getSName();
            names.add(sName);
        }
        return names;
    }

    public static  String queryCode(String sStime,String sNo){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        QueryStudentMapper mapper = sqlSession.getMapper(QueryStudentMapper.class);
        String code = mapper.query(sStime,sNo);
        return code;
    }

}
