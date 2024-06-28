
package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
// import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

public class Main {
    public static void main(String[] args) {

//        UserDaoJDBCImpl userDao = new UserDaoJDBCImpl();
        UserDaoHibernateImpl userHib = new UserDaoHibernateImpl();
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
