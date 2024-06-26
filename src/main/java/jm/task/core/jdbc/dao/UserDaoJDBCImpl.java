package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection conn = Util.getConnection();
    String tableCreate = "CREATE TABLE users (Id BIGINT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(20), LastName VARCHAR (20), Age TINYINT)";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate(tableCreate);
        } catch (SQLException e) {
        }
    }

    public void dropUsersTable() {
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("DROP TABLE users");
        } catch (SQLException e) {
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO users (Name, LastName, Age) VALUES ('" + name + "', '" + lastName + "', " + age + ")");
            System.out.printf("User с именем — %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
        }
    }

    public void removeUserById(long id) {
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("DELETE FROM users WHERE Id = " + id);
        } catch (SQLException e) {
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                users.add(new User(resultSet.getString("Name"), resultSet.getString("LastName"), resultSet.getByte("Age")));
            }
            for (User user : users) {
                System.out.println(user.toString());
            }
        } catch (SQLException e) {
        }
        return users;
    }

    public void cleanUsersTable() {
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("DELETE FROM users");
        } catch (SQLException e) {
        }

    }
}
