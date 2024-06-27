package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection conn = Util.getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String tableCreate = "CREATE TABLE IF NOT EXISTS users (Id BIGINT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(20), LastName VARCHAR (20), Age TINYINT)";
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(tableCreate);
            conn.commit();
        } catch (SQLException e) {
            System.out.println("SQLException. Executing rollback");
            try {
                conn.rollback();
            } catch (SQLException ex) {
            }
        }
    }

    public void dropUsersTable() {
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            conn.commit();
        } catch (SQLException e) {
            System.out.println("SQLException. Executing rollback");
            try {
                conn.rollback();
            } catch (SQLException ex) {
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO users (Name, LastName, Age) VALUES (?, ?, ?)");
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            conn.commit();
            System.out.printf("User с именем — %s добавлен в базу данных\n", name);
            statement.close();
        } catch (SQLException e) {
            System.out.println("SQLException. Executing rollback");
            try {
                conn.rollback();
            } catch (SQLException ex) {
            }
        }
    }

    public void removeUserById(long id) {
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate("DELETE FROM users WHERE Id = " + id);
            conn.commit();
        } catch (SQLException e) {
            System.out.println("SQLException. Executing rollback");
            try {
                conn.rollback();
            } catch (SQLException ex) {
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                users.add(new User(resultSet.getString("Name"), resultSet.getString("LastName"), resultSet.getByte("Age")));
            }
            for (User user : users) {
                System.out.println(user.toString());
            }
            resultSet.close();
        } catch (SQLException e) {
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate("TRUNCATE users");
            conn.commit();
        } catch (SQLException e) {
            System.out.println("SQLException. Executing rollback");
            try {
                conn.rollback();
            } catch (SQLException ex) {
            }
        }

    }
}
