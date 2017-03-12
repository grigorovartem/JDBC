import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Runner {
    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/mydb";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {

        try (Connection connection = DriverManager.getConnection(DB_CONNECTION, USER, PASSWORD)) {
            DBUtils.create(connection);
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("1: add client");
                System.out.println("2: add product");
                System.out.println("3: add order");
                System.out.println("4: print order");
                System.out.println("5: exit");
                System.out.print("-> ");

                String s = scanner.nextLine();
                switch (s) {
                    case "1":
                        DBUtils.addUser(connection, scanner);
                        break;
                    case "2":
                        DBUtils.addProduct(connection, scanner);
                        break;
                    case "3":
                        OrderUtils.addOrder(connection, scanner);
                        break;
                    case "4":
                        OrderUtils.printOrder(connection);
                        break;
                    default:
                        return;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
