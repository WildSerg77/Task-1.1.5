
package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;

public class Main {
    public static void main(String[] args) {

        UserDao userHib = new UserDaoHibernateImpl();
        userHib.createUsersTable();
        userHib.saveUser("Name1", "LastName1", (byte) 20);
        userHib.saveUser("Name2", "LastName2", (byte) 25);
        userHib.saveUser("Name3", "LastName3", (byte) 31);
        userHib.saveUser("Name4", "LastName4", (byte) 38);
        userHib.removeUserById(2);
        userHib.getAllUsers();
        userHib.cleanUsersTable();
        userHib.dropUsersTable();
    }
}
