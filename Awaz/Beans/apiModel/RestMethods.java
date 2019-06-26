package com.Beans.apiModel;

/**
 * Created by Asad on 3/24/2019.
 */

import com.Beans.Customer;
import com.Beans.Report;
import com.Beans.User;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

@Component
public class RestMethods {

    // Dummy database. Initialize with some dummy values.

    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;

    // DataBase
    public User RegisterCitizen(User user) {

        int status = 0;
        String user_username = user.getUser_name();
        String address = user.getAddress();
        String phone_no = user.getPhone_no();
        String user_name = user.getFull_name();
        String gender = user.getGender();
        String cnic = user.getCnic();
        String password = user.getPassword();
        String email = user.getEmail();
        String citizenship = user.getCitizenship();
        int role_id = 1;// user.getRole().getRole_id();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fyp", "root", "");
            preparedStatement = connection.prepareStatement("insert into users(user_name,user_address,user_phoneno,user_cnic,user_gender,user_citizenship,user_email,user_username,user_password,role_id,user_block_status) values (?,?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, user_name);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3, phone_no);
            preparedStatement.setString(4, cnic);
            preparedStatement.setString(5, gender);
            preparedStatement.setString(6, citizenship);
            preparedStatement.setString(7, email);
            preparedStatement.setString(8, user_username);
            preparedStatement.setString(9, password);
            preparedStatement.setInt(10, role_id);
            preparedStatement.setBoolean(11, false);
            int a = preparedStatement.executeUpdate();
            if (a == 1) {
                status = a;
            }
        } catch (Exception ex) {
            out.write(Integer.parseInt(ex + ""));
        }
        System.out.println("\n\nUser Registered Success\n\n");
        return user;
    }
    //checkLogin
    public User checkLogin(String user_username, String password) {

        String role = "";
        User user = new User();
        int role_id = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fyp", "root", "");
            preparedStatement = connection.prepareStatement("select * from users where user_username=? and user_password=? and user_block_status=? ");
            preparedStatement.setString(1, user_username);
            System.out.println("CutomerDAO 1c User Name: " + user_username + "  Password: " + password);
            preparedStatement.setString(2, password);
            preparedStatement.setBoolean(3, false);
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println("CutomerDAO 3 User Name: " + user_username + "  Password: " + password);
            if (rs.next()) {
                role_id = rs.getInt("role_id");
            } else {
                return null;
            }
            preparedStatement = connection.prepareStatement("select * from roles where role_id=? ");
            preparedStatement.setInt(1, role_id);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                role = rs.getString("role_name");
                System.out.println("role Name: "+role);
                user = getUser(user_username);
            }
        } catch (Exception ex) {
            out.write(Integer.parseInt("exception: " + ex + ""));
        }
        System.out.println("login success");

        return user;
    }
    public User getUser(String userName) {
        User user = new User();

        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fyp", "root", "");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM users where user_username = '" + userName + "'");

            while (resultSet.next()) {
                user.setUser_id(resultSet.getInt("user_id"));
                user.setFull_name(resultSet.getString("user_name"));
                user.setAddress(resultSet.getString("user_address"));
                user.setPhone_no(resultSet.getString("user_phoneno"));
                user.setCnic(resultSet.getString("user_cnic"));
                user.setGender(resultSet.getString("user_gender"));
                user.setCitizenship(resultSet.getString("user_citizenship"));
                user.setEmail(resultSet.getString("user_email"));
                user.setUser_name(resultSet.getString("user_username"));
                user.setPassword(resultSet.getString("user_password"));
                user.setBlock_status(resultSet.getBoolean("user_block_user"));

            }
            connection.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    //insertReportNewInDB
    public Report addReport(Report reportBean) {
        String image64base = reportBean.getImageString();
        String base64Image = image64base.split(",")[1];
        byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
        String video64base = reportBean.getImageString();
        String base64video = video64base.split(",")[1];
        byte[] videoBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64video);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fyp", "root", "");
            String sql = "INSERT INTO reports(r_title,r_description,r_image,r_video,r_address,r_lat,r_log,r_severity,r_status,user_id) VALUES (?,?,?,?,?,?,?,?,?,?)";
            // RoleBean roleBean = new RoleBean();
            // roleBean = userBean.getRole();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, reportBean.getTitle());
            preparedStatement.setString(2, reportBean.getDescription());
            // preparedStatement.setBytes(3, reportBean.getImagee());
            // preparedStatement.setBytes(4, reportBean.getVideoo());
            preparedStatement.setBytes(3, imageBytes);
            preparedStatement.setBytes(4, videoBytes);
            preparedStatement.setString(5, reportBean.getAddress());
            preparedStatement.setDouble(6, reportBean.getLat());
            preparedStatement.setDouble(7, reportBean.getLog());
            preparedStatement.setString(8, reportBean.getSeverity());
            preparedStatement.setString(9, reportBean.getStatus());
            preparedStatement.setInt(10, reportBean.getUser_id());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new ReportNew was Register succesfully");
                return reportBean;
            }
            connection.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Report> allReports() {
        List<Report> reports = new ArrayList<Report>();

        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fyp", "root", "");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM reportsazeem");

            while (resultSet.next()) {
                Report report = new Report();
                String title = resultSet.getString("r_title");
                String status = resultSet.getString("r_status");
                String address = resultSet.getString("r_address");
                int user_idd = resultSet.getInt("user_id");
                int r_id = resultSet.getInt("r_id");
                System.out.println("title: "+title);
                String description = resultSet.getString("r_description");
                byte[] image = resultSet.getBytes("r_image");
                InputStream isImage = new ByteArrayInputStream(image);
                byte[] video = resultSet.getBytes("r_video");
                InputStream isVideo = new ByteArrayInputStream(video);
                String severity = resultSet.getString("r_severity");
                String lat = resultSet.getString("r_lat");
                String logg = resultSet.getString("r_log");
                report.setTitle(title);
                report.setDescription(description);
                report.setImagee(image);
                report.setVideoo(video);
                report.setLat(Double.parseDouble(lat));
                report.setLog(Double.parseDouble(logg));
                report.setSeverity(severity);
                report.setAddress(address);
                report.setUser_id(user_idd);
                report.setStatus(status);
                report.setR_id(r_id);
                reports.add(report);
            }
            connection.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reports;
    }
    public List<Report> allReportsUser(int user_id) {
        List<Report> reports = new ArrayList<Report>();

        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fyp", "root", "");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM reportsazeem where user_id = '" + user_id + "'");

            while (resultSet.next()) {
                Report report = new Report();
                String title = resultSet.getString("r_title");
                String status = resultSet.getString("r_status");
                String address = resultSet.getString("r_address");
                int user_idd = resultSet.getInt("user_id");
                int r_id = resultSet.getInt("r_id");
                System.out.println("title: "+title);
                String description = resultSet.getString("r_description");
                byte[] image = resultSet.getBytes("r_image");
                InputStream isImage = new ByteArrayInputStream(image);
                byte[] video = resultSet.getBytes("r_video");
                InputStream isVideo = new ByteArrayInputStream(video);
                String severity = resultSet.getString("r_severity");
                String lat = resultSet.getString("r_lat");
                String logg = resultSet.getString("r_log");
                report.setTitle(title);
                report.setDescription(description);
                report.setImagee(image);
                report.setVideoo(video);
                report.setLat(Double.parseDouble(lat));
                report.setLog(Double.parseDouble(logg));
                report.setSeverity(severity);
                report.setAddress(address);
                report.setUser_id(user_idd);
                report.setStatus(status);
                report.setR_id(r_id);
                reports.add(report);
            }
            connection.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reports;
    }
    public List<Report> reportsUser(int r_id,int user_id) {
        List<Report> reports = new ArrayList<Report>();

        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fyp", "root", "");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM reportsazeem where r_id = '" + r_id + "' and user_id = '"+ user_id +"'");

            while (resultSet.next()) {
                Report report = new Report();
                String title = resultSet.getString("r_title");
                String status = resultSet.getString("r_status");
                String address = resultSet.getString("r_address");
                int user_idd = resultSet.getInt("user_id");
                int r_idd = resultSet.getInt("r_id");
                System.out.println("title: "+title);
                String description = resultSet.getString("r_description");
                byte[] image = resultSet.getBytes("r_image");
                InputStream isImage = new ByteArrayInputStream(image);
                byte[] video = resultSet.getBytes("r_video");
                InputStream isVideo = new ByteArrayInputStream(video);
                String severity = resultSet.getString("r_severity");
                String lat = resultSet.getString("r_lat");
                String logg = resultSet.getString("r_log");
                report.setTitle(title);
                report.setDescription(description);
                report.setImagee(image);
                report.setVideoo(video);
                report.setLat(Double.parseDouble(lat));
                report.setLog(Double.parseDouble(logg));
                report.setSeverity(severity);
                report.setAddress(address);
                report.setUser_id(user_idd);
                report.setStatus(status);
                report.setR_id(r_idd);
                reports.add(report);
            }
            connection.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reports;
    }
    public List reportLatLog() {
        // List<ArrayList<String>> latlogs = new ArrayList<ArrayList<String>>();
        List<LatLog> latLogs = new ArrayList<LatLog>();


        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fyp", "root", "");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM reportsazeem");
            while (resultSet.next()) {
                // ArrayList<String> latlog = new ArrayList<String>();
                LatLog latlog = new LatLog();

                String lat = resultSet.getString("r_lat");
                System.out.println("lat: "+lat);
                String logg = resultSet.getString("r_log");
                System.out.println("log: "+logg);
                System.out.println("\n\n\n");
                latlog.setLat(Double.parseDouble(resultSet.getString("r_lat")));
                latlog.setLog(Double.parseDouble(resultSet.getString("r_log")));
                latlog.setR_title(resultSet.getString("r_title"));
                //latlog.add(lat);
                //latlog.add(logg);
                latLogs.add(latlog);
            }
            connection.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return latLogs;
    }
    public RestMethods() {
        customers = new ArrayList();

        customers.add(new Customer(102, "John", "Doe", "djohn@gmail.com", "121-232-3435"));
        customers.add(new Customer(202, "Russ", "Smith", "sruss@gmail.com", "343-545-2345"));
        customers.add(new Customer(302, "Kate", "Williams", "kwilliams@gmail.com", "876-237-2987"));
    }
    private static List customers;
    {
        customers = new ArrayList();
        customers.add(new Customer(101, "John", "Doe", "djohn@gmail.com", "121-232-3435"));
        customers.add(new Customer(201, "Russ", "Smith", "sruss@gmail.com", "343-545-2345"));
        customers.add(new Customer(301, "Kate", "Williams", "kwilliams@gmail.com", "876-237-2987"));
    }
    /**
     * Returns list of customers from dummy database.
     *
     * @return list of customers
     */
    public List list() {
        return customers;
    }
    /**
     * Return customer object for given id from dummy database. If customer is
     * not found for id, returns null.
     *
     * @param id customer id
     * @return customer object for given id
     */
    public Customer get(Long id) {

        /*
        for ( Customer c : customers) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        */
        for(int i=0; i<customers.size(); i++)
        {
            Customer c = (Customer) customers.get(i);
            if (c.getId().equals(id)) {
                return c;
            }

        }
        return null;
    }
    /**
     * Create new customer in dummy database. Updates the id and insert new
     * customer in list.
     *
     * @param customer Customer object
     * @return customer object with updated id
     */
    public Customer create(Customer customer) {
        customer.setId(System.currentTimeMillis());
        customers.add(customer);
        return customer;
    }
    /**
     * Delete the customer object from dummy database. If customer not found for
     * given id, returns null.
     *
     * @param id the customer id
     * @return id of deleted customer object
     */
    public Long delete(Long id) {

        /*
        for ( Customer c : customers) {
            if (c.getId().equals(id)) {
                customers.remove(c);
                return id;
            }
        }*/
        for(int i=0; i<customers.size(); i++)
        {
            Customer c = (Customer) customers.get(i);
            if (c.getId().equals(id)) {
                customers.remove(c);
                return id;
            }
        }

        return null;
    }
    /**
     * Update the customer object for given id in dummy database. If customer
     * not exists, returns null
     *
     * @param id
     * @param customer
     * @return customer object with id
     */
    public Customer update(Long id, Customer customer) {
        /*
        for ( Customer c : customers ) {

            if (c.getId().equals(id)) {
                customer.setId(c.getId());
                customers.remove(c);
                customers.add(customer);
                return customer;
            }
        }*/
        for (int i = 0; i < customers.size(); i++) {
            Customer c = (Customer) customers.get(i);
            if (c.getId().equals(id)) {
                customer.setId(c.getId());
                customers.remove(c);
                customers.add(customer);
                return customer;
            }
        }
        return null;
    }
}
