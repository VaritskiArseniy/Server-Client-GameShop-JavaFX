package mainClasses;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {
    private Connection connection;

    private static DataBase BASE;
    public static DataBase getDataBase()
    {
        if(BASE==null)
        {
            BASE = new DataBase();
        }
        return BASE;
    }

    private DataBase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/game_shop?useUnicode=true&serverTimezone=UTC", "root", "9132");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteStaff(String login){
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM staff WHERE login = ?");
            ps.setString(1, login);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteGames(String name){
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM games WHERE name = ?");
            ps.setString(1, name);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteProgram(String name){
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM program WHERE name = ?");
            ps.setString(1, name);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFromChosenBasket(int idUser, int idGood, int isprogram){
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM basket WHERE idUser = ? AND idGood =? AND isProgram =?");
            ps.setInt(1, idUser);
            ps.setInt(2, idGood);
            ps.setInt(3, isprogram);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFromBasket(int idUser){
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM basket WHERE idUser = ?");
            ps.setInt(1, idUser);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Program> getActiveProgram(int isActivee){
        ArrayList<Program> list = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * from program WHERE isActive =?");
            ps.setInt(1, isActivee);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Long id = rs.getLong("id");
                String genre = rs.getString("genre");
                double price = rs.getDouble("price");
                int sold = rs.getInt("sold");
                int quantity = rs.getInt("quantity");
                int isActive = rs.getInt("isActive");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String OS = rs.getString("OS");

                list.add(new Program(id, genre, price, sold, quantity, isActive, name, description, OS));
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public ArrayList<Program> getAllProgram(){
        ArrayList<Program> list = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * from program");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Long id = rs.getLong("id");
                String genre = rs.getString("genre");
                double price = rs.getDouble("price");
                int sold = rs.getInt("sold");
                int quantity = rs.getInt("quantity");
                int isActive = rs.getInt("isActive");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String OS = rs.getString("OS");

                list.add(new Program(id, genre, price, sold, quantity, isActive, name, description, OS));
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void buyFromBasket(int newSold, int newQuantity, int idGood){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE games SET sold =?, quantity =? WHERE id =?");
            ps.setInt(1, newSold);
            ps.setInt(2, newQuantity);
            ps.setInt(3, idGood);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void buyFromBasketProgram(int newSold, int newQuantity, int idGood){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE program SET sold =?, quantity =? WHERE id =?");
            ps.setInt(1, newSold);
            ps.setInt(2, newQuantity);
            ps.setInt(3, idGood);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCourier(int idOrder, int idCourier){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE orders SET idCourier =? WHERE id =?");
            ps.setInt(1, idCourier);
            ps.setInt(2, idOrder);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStatus(int idOrder, String status){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE orders SET status =? WHERE id =?");
            ps.setString(1, status);
            ps.setInt(2, idOrder);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(String adress, int id){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE users SET adress =? WHERE id =?");
            ps.setString(1, adress);
            ps.setInt(2, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBasket(int idGood, int quantity, int idUser, int isProgram){
        int x;
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE basket SET quantity =? WHERE idUser = ? AND idGood =? AND isProgram =?");
            ps.setInt(1, quantity);
            ps.setLong(2, idUser);
            ps.setLong(3, idGood);
            ps.setInt(4, isProgram);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateQuantityGames(int iD, int quantity){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE games SET quantity =? WHERE id =?");
            ps.setInt(1, quantity);
            ps.setLong(2, iD);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateQuantityProgram(int iD, int quantity){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE program SET quantity =? WHERE id =?");
            ps.setInt(1, quantity);
            ps.setLong(2, iD);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<Basket> getAllBaskets(){
        ArrayList<Basket> baskets = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * from basket");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("id");
                Long idUser = rs.getLong("idUser");
                Long idGood = rs.getLong("idGood");
                int isProgram = rs.getInt("isProgram");
                String genre = rs.getString("genre");
                String model = rs.getString("model");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                baskets.add(new Basket(id, idUser, idGood, isProgram, genre, model, quantity, price));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return baskets;
    }

    public ArrayList<Basket> getBasketByIDUser(int idd){
        ArrayList<Basket> baskets = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * from basket WHERE idUser =?");
            ps.setLong(1, idd);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("id");
                Long idUser = rs.getLong("idUser");
                Long idGood = rs.getLong("idGood");
                int isProgram = rs.getInt("isProgram");
                String genre = rs.getString("genre");
                String model = rs.getString("model");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                baskets.add(new Basket(id, idUser, idGood, isProgram, genre, model, quantity, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return baskets;
    }

    public ArrayList<HistoryPurchase> getAllHistory(){
        ArrayList<HistoryPurchase> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * from history");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("id");
                Long idUser = rs.getLong("idUser");
                Long idGood = rs.getLong("idGood");
                int isProgram = rs.getInt("isProgram");
                String genre = rs.getString("genre");
                String model = rs.getString("model");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                double sum = rs.getDouble("sum");
                String date = rs.getString("date");
                list.add(new HistoryPurchase(id, idUser, idGood, isProgram, genre, model, quantity, price, sum, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<HistoryPurchase> getHistoryByID(int idd){
        ArrayList<HistoryPurchase> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * from history WHERE idUser =?");
            ps.setInt(1, idd);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("id");
                Long idUser = rs.getLong("idUser");
                Long idGood = rs.getLong("idGood");
                int isProgram = rs.getInt("isProgram");
                String genre = rs.getString("genre");
                String model = rs.getString("model");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                double sum = rs.getDouble("sum");
                String date = rs.getString("date");
                list.add(new HistoryPurchase(id, idUser, idGood, isProgram, genre, model, quantity, price, sum, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<User> getUsersByLogin(String username){
        ArrayList<User> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * from users WHERE login =?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("id");
                String login = rs.getString("login");
                String password = rs.getString("password");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String phone = rs.getString("phoneNumber");
                double balance = rs.getDouble("balance");
                String adress = rs.getString("adress");

                list.add(new User(id, login, password, name, surname, phone, balance, adress));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<User> getAllUsers(){
        ArrayList<User> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * from users");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("id");
                String login = rs.getString("login");
                String password = rs.getString("password");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String phone = rs.getString("phoneNumber");
                double balance = rs.getDouble("balance");
                String adress = rs.getString("adress");

                list.add(new User(id, login, password, name, surname, phone, balance, adress));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Order> getAllOrders(){
        ArrayList<Order> orders = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM orders");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("id");
                Long idUser = rs.getLong("idUser");
                int idCourier = rs.getInt("idCourier");
                int quantity = rs.getInt("quantityItems");
                double sum = rs.getDouble("sum");
                String address = rs.getString("address");
                String status = rs.getString("status");
                orders.add(new Order(id, idUser, idCourier, quantity, sum, address, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public ArrayList<Order> getOrdersByIDCourier(int idcourier){
        ArrayList<Order> orders = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM orders WHERE idCourier =?");
            ps.setInt(1, idcourier);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("id");
                Long idUser = rs.getLong("idUser");
                int idCourier = rs.getInt("idCourier");
                int quantity = rs.getInt("quantityItems");
                double sum = rs.getDouble("sum");
                String address = rs.getString("address");
                String status = rs.getString("status");
                orders.add(new Order(id, idUser, idCourier, quantity, sum, address, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public ArrayList<Order> getOrdersByID(int iduser){
        ArrayList<Order> orders = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM orders WHERE idUser =?");
            ps.setInt(1, iduser);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("id");
                Long idUser = rs.getLong("idUser");
                int idCourier = rs.getInt("idCourier");
                int quantity = rs.getInt("quantityItems");
                double sum = rs.getDouble("sum");
                String address = rs.getString("address");
                String status = rs.getString("status");
                orders.add(new Order(id, idUser, idCourier, quantity, sum, address, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public ArrayList<Staff> getStaffByLogin(String logName){
        ArrayList<Staff> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * from staff WHERE login =?");
            ps.setString(1, logName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("id");
                String login = rs.getString("login");
                String password = rs.getString("password");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String phone = rs.getString("phoneNumber");
                String position = rs.getString("position");
                double salary = rs.getDouble("salary");
                list.add(new Staff(id, login, password, name, surname, phone, position, salary));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Staff> getAllStaffs(){
        ArrayList<Staff> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * from staff");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("id");
                String login = rs.getString("login");
                String password = rs.getString("password");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String phone = rs.getString("phoneNumber");
                String position = rs.getString("position");
                double salary = rs.getDouble("salary");
                list.add(new Staff(id, login, password, name, surname, phone, position, salary));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }





    public ArrayList<Program> getProgramByID(int iD){
        ArrayList<Program> list = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * from program WHERE id =?");
            ps.setInt(1, iD);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Long id = rs.getLong("id");
                String genre = rs.getString("genre");
                double price = rs.getDouble("price");
                int sold = rs.getInt("sold");
                int quantity = rs.getInt("quantity");
                int isActive = rs.getInt("isActive");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String OS = rs.getString("OS");

                list.add(new Program(id, genre, price, sold, quantity, isActive, name, description, OS));
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<Games> getGamesByID(int iD){
        ArrayList<Games> list = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * from games WHERE id =?");
            ps.setInt(1, iD);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Long id = rs.getLong("id");
                String genre = rs.getString("genre");
                double price = rs.getDouble("price");
                int sold = rs.getInt("sold");
                int quantity = rs.getInt("quantity");
                int isActive = rs.getInt("isActive");
                String name = rs.getString("name");
                String description = rs.getString("description");

                list.add(new Games(id, genre, price, sold, quantity, isActive, name, description));
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<Games> getActiveGames(int isAct){
        ArrayList<Games> list = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * from games WHERE isActive =?");
            ps.setInt(1, isAct);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Long id = rs.getLong("id");
                String genre = rs.getString("genre");
                double price = rs.getDouble("price");
                int sold = rs.getInt("sold");
                int quantity = rs.getInt("quantity");
                int isActive = rs.getInt("isActive");
                String name = rs.getString("name");
                String description = rs.getString("description");

                list.add(new Games(id, genre, price, sold, quantity, isActive, name, description));
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<Games> getAllGames(){
        ArrayList<Games> list = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * from games");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Long id = rs.getLong("id");
                String genre = rs.getString("genre");
                double price = rs.getDouble("price");
                int sold = rs.getInt("sold");
                int quantity = rs.getInt("quantity");
                int isActive = rs.getInt("isActive");
                String name = rs.getString("name");
                String description = rs.getString("description");

                list.add(new Games(id, genre, price, sold, quantity, isActive, name, description));
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void addOrder(Order order){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO orders (id, idUser, idCourier, quantityItems, sum, address, status) VALUES(NULL, ?, ?, ?, ?, ?, ?)");
            ps.setLong(1, order.getIdUser());
            ps.setLong(2, order.getIdCourier());
            ps.setInt(3, order.getQuantityItems());
            ps.setDouble(4, order.getSum());
            ps.setString(5, order.getAddress());
            ps.setString(6, order.getStatus());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addBasket(Basket basket){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO basket (id, idUser, idGood, isProgram, genre, model, quantity, price) VALUES(NULL, ?, ?, ?, ?, ?, ?, ?)");
            ps.setLong(1, basket.getIdUser());
            ps.setLong(2, basket.getIdGood());
            ps.setInt(3, basket.getIsProgram());
            ps.setString(4, basket.getGenre());
            ps.setString(5, basket.getModel());
            ps.setInt(6, basket.getQuantity());
            ps.setDouble(7, basket.getPrice());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addGames(Games games){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO games (id, genre, price, sold, quantity, isActive, name, description) VALUES(NULL, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, games.getGenre());
            ps.setDouble(2, games.getPrice());
            ps.setInt(3, games.getSold());
            ps.setInt(4, games.getQuantity());
            ps.setInt(5, games.getIsActive());
            ps.setString(6, games.getName());
            ps.setString(7, games.getDescription());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addProgram(Program program){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO program (id, genre, price, sold, quantity, isActive, name, description, OS) VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, program.getGenre());
            ps.setDouble(2, program.getPrice());
            ps.setInt(3, program.getSold());
            ps.setInt(4, program.getQuantity());
            ps.setInt(5, program.getIsActive());
            ps.setString(6, program.getName());
            ps.setString(7, program.getDescription());
            ps.setString(8, program.getOS());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addHistory(HistoryPurchase historyPurchase){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO history (id, idUser, idGood, isProgram, genre, model, quantity, price, sum, date) VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setLong(1, historyPurchase.getIdUser());
            ps.setLong(2, historyPurchase.getIdGood());
            ps.setInt(3, historyPurchase.getIsProgram());
            ps.setString(4, historyPurchase.getGenre());
            ps.setString(5, historyPurchase.getModel());
            ps.setInt(6, historyPurchase.getQuantity());
            ps.setDouble(7, historyPurchase.getPrice());
            ps.setDouble(8, historyPurchase.getSum());
            ps.setString(9, historyPurchase.getDate());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addStaff(Staff staff) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO staff (id, login, password, name, surname, phoneNumber, position, salary) VALUES(NULL, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, staff.getLogin());
            ps.setString(2, staff.getPassword());
            ps.setString(3, staff.getFirstName());
            ps.setString(4, staff.getLastName());
            ps.setString(5, staff.getPhoneNum());
            ps.setString(6, staff.getPosition());
            ps.setDouble(7, staff.getSalary());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO users (id, login, password, name, surname, phoneNumber, balance, adress) VALUES(NULL, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setString(5, user.getPhoneNum());
            ps.setDouble(6, user.getBalance());
            ps.setString(7, user.getAdress());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkStaffName(String login) throws SQLException {
        boolean checkUser = false;
        PreparedStatement ps1 = connection.prepareStatement("SELECT * FROM staff WHERE login =?");
        ps1.setString(1, login);
        ResultSet rs1 = ps1.executeQuery();
        if(rs1.next()){
            checkUser = true;
        }
        ps1.close();
        rs1.close();
        return checkUser;
    }

    public boolean checkUsername(String username) throws SQLException {
        boolean checkUser = false;
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE login =?");
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            checkUser = true;
        }
        ps.close();
        rs.close();
        return checkUser;
    }

    public String checkLoginPassword(String username, String passWord) throws SQLException {
        String answer = "notExists";
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE login =? AND password =?");
        ps.setString(1, username);
        ps.setString(2, passWord);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            answer = "userExists";
        }

        PreparedStatement ps1 = connection.prepareStatement("SELECT * FROM staff WHERE login =? AND password =?");
        ps1.setString(1, username);
        ps1.setString(2, passWord);
        ResultSet rs1 = ps1.executeQuery();
        if(rs1.next()){
            String position = rs1.getString("position");
            if (position.equals("Курьер")){
                answer = "staffExistsCourier";
            }
            else if (position.equals("Директор")){
                answer = "staffExistsCEO";
            }
            else if (position.equals("Главный бухгалтер")){
                answer = "staffExistsMainAccountant";
            }
        }

        ps.close();
        rs.close();
        ps1.close();
        rs1.close();

        return answer;
    }
}
