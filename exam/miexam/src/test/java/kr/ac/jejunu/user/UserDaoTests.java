package kr.ac.jejunu.user;

import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;

public class UserDaoTests {

    private static UserDao userDao;

    @BeforeAll
    public static void setUp() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(DaoFactory.class);
        userDao = ac.getBean("userDao", UserDao.class);
    }

    @Test
    public void get() throws SQLException, ClassNotFoundException {
        Long id = 1l;
        String name = "hulk";
        String password = "1234";
        User user = userDao.findById(id);
        assertThat(user.getId(), is(id));
        assertThat(user.getName(), is(name));
        assertThat(user.getPassword(), is(password));
    }

    @Test
    public void insert() throws SQLException, ClassNotFoundException {
        String name = "hulk";
        String password = "1234";
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        userDao.insert(user);

        assertThat(user.getId(), greaterThan(1L));

        User insertedUesr = userDao.findById(user.getId());

        assertThat(insertedUesr.getName(), is(name));
        assertThat(insertedUesr.getPassword(), is(password));
    }

    @Test
    public void update() throws SQLException, ClassNotFoundException {
        User user = insertedUser();
        String name = "헐크";
        String password = "1111";

        user.setName(name);
        user.setPassword(password);
        userDao.update(user);

        User updatedUser = userDao.findById(user.getId());

        assertThat(updatedUser.getName(), is(name));
        assertThat(updatedUser.getPassword(), is(password));
    }

    @Test
    public void delete() throws SQLException, ClassNotFoundException {
        User user = insertedUser();
        userDao.delete(user.getId());

        User deletedUser = userDao.findById(user.getId());

        assertThat(deletedUser, IsNull.nullValue());
    }

    private static User insertedUser() throws ClassNotFoundException, SQLException {
        String name = "hulk";
        String password = "1234";
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        userDao.insert(user);
        return user;
    }
}
