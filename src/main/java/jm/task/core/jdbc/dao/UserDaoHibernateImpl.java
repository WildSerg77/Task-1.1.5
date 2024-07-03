package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (Id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                    " Name VARCHAR(20), LastName VARCHAR (20), Age TINYINT)").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction!= null) {
                transaction.rollback();
            }
            System.err.println("Ошибка при создании таблицы: " + e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Ошибка при удалении таблицы: " + e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
            System.out.printf("User с именем — %s добавлен в базу данных\n", name);
        } catch (Exception e) {
            if (transaction!= null) {
                transaction.rollback();
            }
            System.err.println("Ошибка при сохранении пользователя: " + e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.load(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction!= null) {
                transaction.rollback();
            }
            System.err.println("Ошибка при удалении пользователя с id = " + id + ": " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            List<User> users = session.createCriteria(User.class).list();
            transaction.commit();
            for (User user : users) {
                System.out.println(user.toString());
            }
            return users;
        } catch (Exception e) {
            if (transaction!= null) {
                transaction.rollback();
            }
            System.err.println("Ошибка при получении списка всех пользователей: " + e.getMessage());
            return null;
         }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE users").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction!= null) {
                transaction.rollback();
            }
            System.err.println("Ошибка при очистке таблицы: " + e.getMessage());
        }

    }
}
