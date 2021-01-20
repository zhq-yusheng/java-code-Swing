import com.yu.swing.mapper.QueryStudentMapper;
import com.yu.swing.utils.DateUtil;
import com.yu.swing.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.Date;

public class test {
    @Test
    public void test(){
        Long sdf = DateUtil.getSdf("2021-01-06 15:20");
        Date date = new Date();
        if(sdf>date.getTime()) System.out.println("上课时间大");
        else System.out.println("现在大");
    }
    @Test
    public void test1(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        QueryStudentMapper mapper = sqlSession.getMapper(QueryStudentMapper.class);
        String code = mapper.query("2021-01-07 15:20", "2019710235");
        System.out.println(code);
    }


}
