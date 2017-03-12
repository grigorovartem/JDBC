import java.math.BigDecimal;
import java.sql.*;
import java.util.Scanner;

public class DBUtils {

    public static void create(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(Statements.PRODUCTS);
            statement.execute(Statements.USERS);
            statement.execute(Statements.ORDERS);
            statement.execute(Statements.ORDER_ITEMS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addUser(Connection connection, Scanner scanner) {
        System.out.println("Enter your login: ");
        String login = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();
        System.out.println("Enter your age: ");
        int age = scanner.nextInt();

        try (PreparedStatement statement = connection.prepareStatement(Statements.ADD_USER)) {
            statement.setString(1, login);
            statement.setString(2, password);
            statement.setInt(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addProduct(Connection connection, Scanner scanner) {
        System.out.println("Enter name of product: ");
        String productName = scanner.nextLine();
        System.out.println("Enter count: ");
        int productCount = scanner.nextInt();
        System.out.println("Enter price: ");
        BigDecimal price = new BigDecimal(scanner.nextDouble());

        try (PreparedStatement statement = connection.prepareStatement(Statements.ADD_PRODUCT)) {
            statement.setString(1, productName);
            statement.setInt(2, productCount);
            statement.setBigDecimal(3, price);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
