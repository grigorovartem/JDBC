
public class Statements {

    public static final String USERS = "CREATE TABLE IF NOT EXISTS Users(user_id INT NOT NULL AUTO_INCREMENT, " +
            "login VARCHAR(50) NOT NULL, password VARCHAR(50), age INT, " +
            "PRIMARY KEY(user_id))";
    public static final String PRODUCTS = "CREATE TABLE IF NOT EXISTS Products(product_id INT NOT NULL AUTO_INCREMENT, " +
            "product_name VARCHAR(50), product_count INT, price DECIMAL, " +
            "PRIMARY KEY(product_id))";
    public static final String ORDERS = "CREATE TABLE IF NOT EXISTS Orders(order_id INT NOT NULL AUTO_INCREMENT, " +
            "user_id INT NOT NULL, " +
            "PRIMARY KEY(order_id), FOREIGN KEY(user_id) REFERENCES Users (user_id))";
    public static final String ORDER_ITEMS = "CREATE TABLE IF NOT EXISTS Order_Items(item_id INT NOT NULL AUTO_INCREMENT, " +
            "order_id INT NOT NULL, product_id INT, count INT, " +
            "PRIMARY KEY(item_id), FOREIGN KEY(order_id) REFERENCES Orders (order_id), " +
            "FOREIGN KEY(product_id) REFERENCES Products (product_id))";
    public static final String ADD_USER = "INSERT INTO Users(login, password, age) VALUES (?, ?, ?)";
    public static final String ADD_PRODUCT = "INSERT INTO Products(product_name, product_count, price) VALUES (?, ?, ?)";
    public static final String ADD_ORDER = "INSERT INTO Orders(user_id) VALUES (?)";
    public static final String ADD_ORDER_ITEM = "INSERT INTO order_items(order_id, product_id, count) VALUES (?, ?, ?)";
    public static final String PRINT = "SELECT users.login, products.product_name, products.price, order_items.count \n" +
            "FROM order_items, orders, products, users\n" +
            "WHERE order_items.order_id=? \n" +
            "AND order_items.product_id = products.product_id \n" +
            "AND order_items.order_id=orders.order_id \n" +
            "AND orders.user_id=users.user_id";
}
