import java.sql.*;
import java.util.Scanner;

public class OrderUtils {

    public static void printOrder(Connection connection) {
        int orderId = getOrderId(connection);
        try (PreparedStatement statement = connection.prepareStatement(Statements.PRINT)) {
            statement.setInt(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    System.out.print(metaData.getColumnName(i) + "\t\t");
                }
                System.out.println();

                while (resultSet.next()) {
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        System.out.print(resultSet.getString(i) + "\t\t");
                    }
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int getOrderId(Connection connection) {
        int orderId = 0;
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM orders ORDER BY order_id DESC LIMIT 1;")) {
            try (ResultSet result = ps.executeQuery()) {
                while (result.next()) {
                    orderId = result.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderId;
    }

    public static void addOrder(Connection connection, Scanner scanner) {
        System.out.println("Enter your login: ");
        try (PreparedStatement statement = connection.prepareStatement(Statements.ADD_ORDER)) {
            try (PreparedStatement ps = connection.prepareStatement("SELECT user_id FROM users WHERE login = ?")) {
                String login = scanner.nextLine();
                ps.setString(1, login);
                try (ResultSet result = ps.executeQuery()) {
                    while (result.next()) {
                        statement.setInt(1, result.getInt(1));
                    }
                }
            }
            statement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        addOrderItem(connection, scanner);
    }

    private static void addOrderItem(Connection connection, Scanner scanner) {
        int orderId = getOrderId(connection);
        while (true) {
            System.out.println("4: add product");
            System.out.println("5: end");
            System.out.print("-> ");

            String s = scanner.nextLine();
            if (s.equals("4")) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(Statements.ADD_ORDER_ITEM)) {
                    preparedStatement.setInt(1, orderId);
                    System.out.println("Enter name of product: ");
                    String name = scanner.nextLine();
                    try (PreparedStatement ps = connection.prepareStatement("SELECT product_id " +
                            "FROM products WHERE product_name = ?")) {
                        ps.setString(1, name);
                        try (ResultSet result = ps.executeQuery()) {
                            while (result.next()) {
                                preparedStatement.setInt(2, result.getInt(1));
                            }
                        }
                    }
                    System.out.println("Enter count: ");
                    int count = scanner.nextInt();
                    preparedStatement.setInt(3, count);
                    preparedStatement.executeUpdate();
                    System.out.println(orderId);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (s.equals("5")) {
                break;
            }
        }
    }
}
