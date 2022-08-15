package repository;

import domain.User;
import exception.NoSuchEntityException;
import org.apache.commons.lang3.StringUtils;
import util.DatabasePropertiesReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static repository.UserTableColumns.ID;
import static repository.UserTableColumns.NAME;
import static repository.UserTableColumns.SURNAME;
import static repository.UserTableColumns.BIRTH;
import static repository.UserTableColumns.CREATED;
import static repository.UserTableColumns.CHANGED;
import static repository.UserTableColumns.WEIGHT;

import static util.DatabasePropertiesReader.POSTGRES_DRIVER_NAME;
import static util.DatabasePropertiesReader.DATABASE_URL;
import static util.DatabasePropertiesReader.DATABASE_PORT;
import static util.DatabasePropertiesReader.DATABASE_NAME;
import static util.DatabasePropertiesReader.DATABASE_LOGIN;
import static util.DatabasePropertiesReader.DATABASE_PASSWORD;


public class UserRepository implements UserRepositoryInterface {


    public List<User> findAll() {
        return findAll(DEFAULT_LIMIT, DEFAULT_OFFSET);


    }

    private Connection getConnection() throws SQLException {
        try {

            String driver = DatabasePropertiesReader.getProperty(POSTGRES_DRIVER_NAME);

            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver Cannot be loaded!");
            throw new RuntimeException("JDBC Driver Cannot be loaded!");
        }

        String url = DatabasePropertiesReader.getProperty(DATABASE_URL);
        String port = DatabasePropertiesReader.getProperty(DATABASE_PORT);
        String dbName = DatabasePropertiesReader.getProperty(DATABASE_NAME);
        String login = DatabasePropertiesReader.getProperty(DATABASE_LOGIN);
        String password = DatabasePropertiesReader.getProperty(DATABASE_PASSWORD);

        String jdbcUrl = StringUtils.join(url, port, dbName);
        return DriverManager.getConnection(jdbcUrl, login, password);

    }

    private User userRowMapping(ResultSet rs) throws SQLException {
        User user = new User();

        user.setId(rs.getLong(ID));
        user.setUserName(rs.getString(NAME));
        user.setSurname(rs.getString(SURNAME));
        user.setBirth(rs.getTimestamp(BIRTH));
        user.setCreationDate(rs.getTimestamp(CREATED));
        user.setModificationDate(rs.getTimestamp(CHANGED));
        user.setWeight(rs.getDouble(WEIGHT));

        return user;
    }


    @Override
    public User findById(Long id) {
        final String findById = "select * from carshop.users where id=" + id;
        Connection connection;
        Statement statement;
        ResultSet rs;


        try {
            connection = getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(findById);
            boolean hasRow = rs.next();
            if (hasRow) {
                return userRowMapping(rs);

            } else {
                throw new NoSuchEntityException("Entity user with" + id + "does not exist", 404);
            }


        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }


    }

    @Override
    public User findByNameAndSurname(String name, String surname) {

        final String findByNameAndSurname = "select * from carshop.users where user_name=? and " +
                " user_surname=?";

        Connection connection;
        PreparedStatement statement;
        ResultSet rs;


        try {
            connection = getConnection();
            statement = connection.prepareStatement(findByNameAndSurname);
            statement.setString(1, name);
            statement.setString(2, surname);
            rs = statement.executeQuery();
            rs.next();

            return userRowMapping(rs);


        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }

    }


    @Override
    public Optional<User> findOne(Long id) {
        return Optional.of(findById(id));
    }

    @Override
    public List<User> findAll(int limit, int offset) {
        final String findAll = "select * from carshop.users limit " + limit + " offset " + offset;

        List<User> result = new ArrayList<>();

        Connection connection;
        Statement statement;
        ResultSet rs;


        try {
            connection = getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(findAll);

            while (rs.next()) {


                result.add(userRowMapping(rs));

            }
            return result;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public User create(User object) {
        final String insertQuery =
                "insert into carshop.users (user_name, user_surname, user_birth, is_deleted, creation_date, modification_date, weight) " +
                        " values (?, ?, ?, ?, ?, ?, ?);";

        Connection connection;
        PreparedStatement statement;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(insertQuery);

            statement.setString(1, object.getUserName());
            statement.setString(2, object.getSurname());
            statement.setTimestamp(3, object.getBirth());
            statement.setBoolean(4, object.isDeleted());
            statement.setTimestamp(5, object.getCreationDate());
            statement.setTimestamp(6, object.getModificationDate());
            statement.setDouble(7, object.getWeight());

            //executeUpdate - for INSERT, UPDATE, DELETE
            statement.executeUpdate();
            //For select
            //statement.executeQuery();

            /*Get users last insert id for finding new object in DB*/
            ResultSet resultSet = connection.prepareStatement("SELECT currval('carshop.users_id_seq') as last_id").executeQuery();
            resultSet.next();
            long userLastInsertId = resultSet.getLong("last_id");

            return findById(userLastInsertId);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }


    }

    @Override
    public User update(User object) {

        final String updateQuery =
                "update carshop.users " +
                        "set " +
                        "user_name = ?, user_surname = ?, user_birth = ?, is_deleted = ?, creation_date = ?, modification_date = ?, weight = ? " +
                        " where id = ?";

        Connection connection;
        PreparedStatement statement;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(updateQuery);

            statement.setString(1, object.getUserName());
            statement.setString(2, object.getSurname());
            statement.setTimestamp(3, object.getBirth());
            statement.setBoolean(4, object.isDeleted());
            statement.setTimestamp(5, object.getCreationDate());
            statement.setTimestamp(6, object.getModificationDate());
            statement.setDouble(7, object.getWeight());
            statement.setLong(8, object.getId());

            //executeUpdate - for INSERT, UPDATE, DELETE
            statement.executeUpdate();
            //For select
            //statement.executeQuery();

            /*Get users last insert id for finding new object in DB*/


            return findById(object.getId());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public Long delete(Long id) {
        final String deleteQuery = "delete from carshop.users where id=?";

        Connection connection;
        PreparedStatement preparedStatement;


        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            return id;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }

    }


    @Override
    public Map<String, Double> getUserStats() {
        final String getUserStats = "select carshop.get_user_avg_weight(?)";

        Connection connection;
        PreparedStatement preparedStatement;


        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(getUserStats);
            preparedStatement.setBoolean(1, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            double resultSetDouble = resultSet.getDouble(1);

            return Collections.singletonMap("avg", resultSetDouble);


        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }

    }
}







