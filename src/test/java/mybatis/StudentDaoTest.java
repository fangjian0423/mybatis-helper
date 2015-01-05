package mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.format.mybatis.helper.dao.StudentDao;
import org.format.mybatis.helper.entity.Student;
import org.format.mybatis.helper.entity.StudentDto;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class StudentDaoTest {

    private SqlSessionFactory sqlSessionFactory;

    private static Logger log = Logger.getLogger(StudentDaoTest.class);

    @Before
    public void setUp() {
        try {
            String resource = "mybatis.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test() {
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();
            StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
            StudentDto dto = new StudentDto();
            dto.setPageSize(1);
            List<Student> studentList = studentDao.query(dto);
            System.out.println(studentList);
            int count = studentDao.count(dto);
            System.out.println(count);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSearch() {
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();
            StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
            StudentDto search = new StudentDto();
            search.setName("format33");
            List<Student> studentList = studentDao.query(search);
            System.out.println(studentList);
            int count = studentDao.count(search);
            System.out.println(count);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testAdd() {
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();
            StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
            Student stu = new Student();
            stu.setName("format22");
            stu.setClassroomId(1);
            studentDao.insert(stu);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testDel() {
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();
            StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
            studentDao.delete(5l);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdate() {
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();
            StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
            Student stu = new Student();
            stu.setId(4l);
            stu.setName("format33");
            studentDao.update(stu);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testGetById() {
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();
            StudentDao studentDao = sqlSession.getMapper(StudentDao.class);
            System.out.println(studentDao.getById(4l).getName());
        } finally {
            sqlSession.close();
        }
    }

}
