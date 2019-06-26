package com.Beans;

import com.Beans.apiModel.Feedback;
import com.Beans.apiModel.LatLog;
import com.Beans.apiModel.MyItems;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static java.lang.System.out;

/**
 * Created by Hp on 11/20/2018.
 */
public class Database {

    public Database() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fyp", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void dbConnection()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fyp", "root", "");
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection connection = null;
    public Statement statement = null;
    public ResultSet resultSet = null;
    public PreparedStatement preparedStatement = null;

    public List reportLatLogApi() {
        // List<ArrayList<String>> latlogs = new ArrayList<ArrayList<String>>();
        List<LatLog> latLogs = new ArrayList<LatLog>();
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fyp", "root", "");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM reports");
            while (resultSet.next()) {
                // ArrayList<String> latlog = new ArrayList<String>();
                LatLog latlog = new LatLog();

                String lat = resultSet.getString("r_lat");
                System.out.println("lat: " + lat);
                String logg = resultSet.getString("r_log");
                System.out.println("log: " + logg);
                System.out.println("\n\n\n");
                latlog.setLat(Double.parseDouble(resultSet.getString("r_lat")));
                latlog.setLog(Double.parseDouble(resultSet.getString("r_log")));
                latlog.setR_title(resultSet.getString("organization_name"));
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
    public int insertReportNewInDB(Report reportBean) {
        try {
            dbConnection();
            String sql = "INSERT INTO reports(organization_name,r_description,r_image,r_video,r_address,r_lat,r_log,r_severity,r_status,user_id,p_id) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            // RoleBean roleBean = new RoleBean();
            // roleBean = userBean.getRole();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, reportBean.getTitle());
            preparedStatement.setString(2, reportBean.getDescription());
            preparedStatement.setBytes(3, reportBean.getImagee());
            preparedStatement.setBytes(4, reportBean.getVideoo());
            preparedStatement.setString(5, reportBean.getAddress());
            preparedStatement.setDouble(6, reportBean.getLat());
            preparedStatement.setDouble(7, reportBean.getLog());
            preparedStatement.setString(8, reportBean.getSeverity());
            preparedStatement.setString(9, reportBean.getStatus());
            preparedStatement.setInt(10, reportBean.getUser_id());
            preparedStatement.setInt(11, reportBean.getP_id());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new ReportNew was Register succesfully");
                return 1;
            }
            connection.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    } //



    public boolean editReportStatus(String r_status, int report_id) {
        boolean status = false;
        try {
            dbConnection();
            String sql = "UPDATE reports SET  r_status=? WHERE r_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, r_status);
            preparedStatement.setInt(2, report_id);
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("An existing Report Status was updated succesfully");
                status = true;
            }

            connection.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    // All Reports updated last time

    public List<Report> getReportsNewByUserId(int user_id) {
        List<Report> reports = new ArrayList<Report>();
        try {
            dbConnection();
            resultSet = statement.executeQuery("SELECT * FROM reports where user_id = '" + user_id + "'");

            while (resultSet.next()) {
                Report report = new Report();
                String title = resultSet.getString("organization_name");
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


                reports.add(report);
            }
            connection.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reports;
    }
    public List<Report> getReports() {
        List<Report> reports = new ArrayList<Report>();

        try {
            dbConnection();
            resultSet = statement.executeQuery("SELECT * FROM reports");
            while (resultSet.next()) {
                Report report = new Report();
                int r_id = resultSet.getInt("r_id");
                String title = resultSet.getString("organization_name");
                String description = resultSet.getString("r_description");
                byte[] image = resultSet.getBytes("r_image");
                InputStream isImage = new ByteArrayInputStream(image);
                byte[] video = resultSet.getBytes("r_video");
                InputStream isVideo = new ByteArrayInputStream(video);
                String severity = resultSet.getString("r_severity");
                String lat = resultSet.getString("r_lat");
                String logg = resultSet.getString("r_log");
                report.setR_id(r_id);
                report.setStatus(resultSet.getString("r_status"));
                report.setTitle(title);
                report.setDescription(description);
                report.setImagee(image);
                report.setVideoo(video);
                report.setLat(Double.parseDouble(lat));
                report.setLog(Double.parseDouble(logg));
                report.setSeverity(severity);


                reports.add(report);
            }
            connection.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reports;
    }
    public List<Report> getReportsByUserId(int user_id) {
        List<Report> reports = new ArrayList<Report>();

        try {
            dbConnection();
            resultSet = statement.executeQuery("SELECT * FROM reports where user_id = '" + user_id + "'");
            while (resultSet.next()) {
                Report report = new Report();
                int r_id = resultSet.getInt("r_id");
                String title = resultSet.getString("organization_name");
                String description = resultSet.getString("r_description");
                byte[] image = resultSet.getBytes("r_image");
                InputStream isImage = new ByteArrayInputStream(image);
                byte[] video = resultSet.getBytes("r_video");
                InputStream isVideo = new ByteArrayInputStream(video);
                String severity = resultSet.getString("r_severity");
                String lat = resultSet.getString("r_lat");
                String logg = resultSet.getString("r_log");
                report.setR_id(r_id);
                report.setStatus(resultSet.getString("r_status"));
                report.setTitle(title);
                report.setDescription(description);
                report.setImagee(image);
                report.setVideoo(video);
                report.setLat(Double.parseDouble(lat));
                report.setLog(Double.parseDouble(logg));
                report.setSeverity(severity);
                reports.add(report);
            }
            connection.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reports;
    }
    public List<Report> getReportsByUserIdByStatus(int user_id,String status) {
        List<Report> reports = new ArrayList<Report>();

        try {
            dbConnection();
            resultSet = statement.executeQuery("SELECT * FROM reports where user_id = '" + user_id + "' AND r_status = '" + status + "'");
            while (resultSet.next()) {
                Report report = new Report();
                int r_id = resultSet.getInt("r_id");
                String title = resultSet.getString("organization_name");
                String description = resultSet.getString("r_description");
                byte[] image = resultSet.getBytes("r_image");
                InputStream isImage = new ByteArrayInputStream(image);
                byte[] video = resultSet.getBytes("r_video");
                InputStream isVideo = new ByteArrayInputStream(video);
                String severity = resultSet.getString("r_severity");
                String lat = resultSet.getString("r_lat");
                String logg = resultSet.getString("r_log");
                report.setR_id(r_id);
                report.setStatus(resultSet.getString("r_status"));
                report.setTitle(title);
                report.setDescription(description);
                report.setImagee(image);
                report.setVideoo(video);
                report.setLat(Double.parseDouble(lat));
                report.setLog(Double.parseDouble(logg));
                report.setSeverity(severity);
                reports.add(report);
            }
            connection.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reports;
    }
    public List<Report> getinboxReport(String org_name) {
        List<Report> reports = new ArrayList<Report>();

        System.out.println("Inbox Report  organization name: "+org_name);

        try {
            dbConnection();
            resultSet = statement.executeQuery("SELECT * FROM reports WHERE organization_name='"+ org_name+"'");
            while (resultSet.next()) {
                Report report = new Report();
                int r_id = resultSet.getInt("r_id");
                String title = resultSet.getString("organization_name");
                String description = resultSet.getString("r_description");
                byte[] image = resultSet.getBytes("r_image");
                InputStream isImage = new ByteArrayInputStream(image);
                byte[] video = resultSet.getBytes("r_video");
                InputStream isVideo = new ByteArrayInputStream(video);
                String severity = resultSet.getString("r_severity");
                String lat = resultSet.getString("r_lat");
                String logg = resultSet.getString("r_log");
                report.setR_id(r_id);
                report.setStatus(resultSet.getString("r_status"));
                report.setTitle(title);
                report.setDescription(description);
                report.setImagee(image);
                report.setVideoo(video);
                report.setLat(Double.parseDouble(lat));
                report.setLog(Double.parseDouble(logg));
                report.setSeverity(severity);


                reports.add(report);
            }
            connection.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reports;
    }
    public List<Report> getinboxReportOrgById(int org_name) {
        List<Report> reports = new ArrayList<Report>();

        System.out.println("Inbox Report  organization name: "+org_name);

        try {
            dbConnection();
            resultSet = statement.executeQuery("SELECT reports.r_id,reports.organization_name,reports.r_description,reports.r_image,reports.r_video,reports.r_address,reports.r_lat,reports.r_log,reports.r_severity,reports.r_status,reports.user_id FROM reports INNER JOIN users ON reports.r_title = '"+ org_name +"' INNER JOIN subuser ON subuser.org_id = users.org_id ");
            while (resultSet.next()) {
                Report report = new Report();
                int r_id = resultSet.getInt("r_id");
                String title = resultSet.getString("organization_name");
                String description = resultSet.getString("r_description");
                byte[] image = resultSet.getBytes("r_image");
                InputStream isImage = new ByteArrayInputStream(image);
                byte[] video = resultSet.getBytes("r_video");
                InputStream isVideo = new ByteArrayInputStream(video);
                String severity = resultSet.getString("r_severity");
                String lat = resultSet.getString("r_lat");
                String logg = resultSet.getString("r_log");
                report.setR_id(r_id);
                report.setStatus(resultSet.getString("r_status"));
                report.setTitle(title);
                report.setDescription(description);
                report.setImagee(image);
                report.setVideoo(video);
                report.setLat(Double.parseDouble(lat));
                report.setLog(Double.parseDouble(logg));
                report.setSeverity(severity);


                reports.add(report);
            }
            connection.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reports;
    }
    public List<Report> getPendingReport(String org_name,String status) {
        List<Report> reports = new ArrayList<Report>();

        System.out.println("Panding Report Db organization name: "+org_name);

        try {
            dbConnection();
            resultSet = statement.executeQuery("SELECT * FROM reports WHERE organization_name='"+ org_name+"' AND r_status='"+status+"'");
            while (resultSet.next()) {
                Report report = new Report();
                int r_id = resultSet.getInt("r_id");
                String title = resultSet.getString("organization_name");
                String description = resultSet.getString("r_description");
                byte[] image = resultSet.getBytes("r_image");
                InputStream isImage = new ByteArrayInputStream(image);
                byte[] video = resultSet.getBytes("r_video");
                InputStream isVideo = new ByteArrayInputStream(video);
                String severity = resultSet.getString("r_severity");
                String lat = resultSet.getString("r_lat");
                String logg = resultSet.getString("r_log");
                report.setR_id(r_id);
                report.setStatus(resultSet.getString("r_status"));
                report.setTitle(title);
                report.setDescription(description);
                report.setImagee(image);
                report.setVideoo(video);
                report.setLat(Double.parseDouble(lat));
                report.setLog(Double.parseDouble(logg));
                report.setSeverity(severity);


                reports.add(report);
            }
            connection.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reports;
    }
    public List<Report> getSolvedReport(String org_name,String status) {
        List<Report> reports = new ArrayList<Report>();

        System.out.println("Panding Report Db organization name: "+org_name);

        try {
            dbConnection();
            resultSet = statement.executeQuery("SELECT * FROM reports WHERE organization_name='"+ org_name+"' AND r_status='"+status+"'");
            while (resultSet.next()) {
                Report report = new Report();
                int r_id = resultSet.getInt("r_id");
                String title = resultSet.getString("organization_name");
                String description = resultSet.getString("r_description");
                byte[] image = resultSet.getBytes("r_image");
                InputStream isImage = new ByteArrayInputStream(image);
                byte[] video = resultSet.getBytes("r_video");
                InputStream isVideo = new ByteArrayInputStream(video);
                String severity = resultSet.getString("r_severity");
                String lat = resultSet.getString("r_lat");
                String logg = resultSet.getString("r_log");
                report.setR_id(r_id);
                report.setStatus(resultSet.getString("r_status"));
                report.setTitle(title);
                report.setDescription(description);
                report.setImagee(image);
                report.setVideoo(video);
                report.setLat(Double.parseDouble(lat));
                report.setLog(Double.parseDouble(logg));
                report.setSeverity(severity);


                reports.add(report);
            }
            connection.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reports;
    }

    public List<User> getEmploye(int org_id) {
        List<User> userList = new ArrayList<User>();

        try {
            dbConnection();
            resultSet = statement.executeQuery("SELECT * FROM subuser where org_id = '"+ org_id +"'");

            while (resultSet.next()) {
                User user = new User();

                user.setFull_name(resultSet.getString("full_name"));
                user.setAddress(resultSet.getString("user_address"));
                user.setPhone_no(resultSet.getString("user_phoneno"));
                user.setCnic(resultSet.getString("cnic"));
                user.setUser_name(resultSet.getString("user_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setEmp_id(resultSet.getString("emp_id"));
                user.setOrg_id(resultSet.getInt("org_id"));

                userList.add(user);
            }
            connection.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    public User getEmployeOfOrganization(int emp_id) {
        User user = new User();
        try {
            dbConnection();
            resultSet = statement.executeQuery("SELECT * FROM subuser where org_id = '"+ emp_id +"'");
            while (resultSet.next()) {
                user.setFull_name(resultSet.getString("full_name"));
                user.setAddress(resultSet.getString("user_address"));
                user.setPhone_no(resultSet.getString("user_phoneno"));
                user.setCnic(resultSet.getString("cnic"));
                user.setUser_name(resultSet.getString("user_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setEmp_id(resultSet.getString("emp_id"));
                user.setOrg_id(resultSet.getInt("org_id"));
            }
            connection.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public Report getReportNew(int r_title) {


        Report repor = new Report();
        try {
            dbConnection();
            resultSet = statement.executeQuery("SELECT * FROM reports where r_id = '" + r_title + "'");

            while (resultSet.next()) {
                String title = resultSet.getString("organization_name");
                String description = resultSet.getString("r_description");
                byte[] image = resultSet.getBytes("r_image");
                InputStream isImage = new ByteArrayInputStream(image);
                byte[] video = resultSet.getBytes("r_video");
                InputStream isVideo = new ByteArrayInputStream(video);
                String severity = resultSet.getString("r_severity");
                String lat = resultSet.getString("r_lat");
                String logg = resultSet.getString("r_log");
                String address = resultSet.getString("r_address");
                repor.setTitle(title);
                repor.setDescription(description);
                repor.setImagee(image);
                repor.setVideoo(video);
                repor.setAddress(address);
                repor.setLat(Double.parseDouble(lat));
                repor.setLog(Double.parseDouble(logg));
                repor.setSeverity(severity);
                repor.setR_id(resultSet.getInt("r_id"));
            }
            connection.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return repor;
    }

    public void deleteReport(int id) {
        try {
            dbConnection();
            String sql = "DELETE FROM reports WHERE organization_name=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A Report was deleted succesfully");
            }
            connection.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // User Sign up and SignIn
    public User RegisterCitizen(User user) { // return type int thay
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
        int role_id = user.getRole().getRole_id();
        try {
            dbConnection();
            preparedStatement = connection.prepareStatement("insert into users(user_fullname,user_address,user_phoneno,user_cnic,user_gender,user_citizenship,user_email,username,user_password,role_id,user_block_status,user_profilepicture) values (?,?,?,?,?,?,?,?,?,?,?,?)");
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
            preparedStatement.setBytes(12, user.getImagee());
            int a = preparedStatement.executeUpdate();
            if (a == 1) {
                status = a;            }
        } catch (Exception ex) {
            out.write(Integer.parseInt(ex + ""));
        }
        return user;
    }
    public int getCnicc(){
        dbConnection();
        int cnic=0;
        try {
            dbConnection(); //SELECT column_name FROM table_name ORDER BY column_name DESC LIMIT 1;
            resultSet = statement.executeQuery("SELECT * FROM users where role_id = '" + 2 + "' ORDER BY user_cnic DESC LIMIT 1 ");
            while (resultSet.next()) {
                User user = new User();
                cnic = resultSet.getInt("user_cnic");
                cnic++;
                System.out.println("Cnic: "+cnic);
            }
            connection.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cnic;
    }
    // User Sign up and SignIn
    public User RegisterOrganization(User user) { // return type int thay
        int status = 0;
        String user_username = user.getUser_name();
        String address = user.getAddress();
        String phone_no = user.getPhone_no();
        String full_name = user.getFull_name();
        //String gender = user.getGender();
        //String cnic = user.getCnic();
        int org_id = user.getOrg_id();
        String password = user.getPassword();
        String email = user.getEmail();
        //String citizenship = user.getCitizenship();
        int role_id = user.getRole().getRole_id();
        int cnicc =getCnicc();
        String cnic = String.valueOf(cnicc);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fyp", "root", "");
            preparedStatement = connection.prepareStatement("insert into users(user_fullname,user_address,user_phoneno,user_cnic,user_email,username,user_password,role_id,user_block_status,user_profilepicture) values (?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, full_name);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3, phone_no);
            preparedStatement.setString(4, cnic);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, user_username);
            preparedStatement.setString(7, password);
            preparedStatement.setInt(8, role_id);
            preparedStatement.setBoolean(9, false);
            preparedStatement.setBytes(10, user.getImagee());
            int a = preparedStatement.executeUpdate();
            if (a == 1) {
                status = a;            }
        } catch (Exception ex) {
            out.write(Integer.parseInt(ex + ""));
        }
        return user;
    }
    public User addEmploye(User user) { // return type int thay
        int status = 0;
        String user_username = user.getUser_name();
        String address = user.getAddress();
        String phone_no = user.getPhone_no();
        String full_name = user.getFull_name();
        //String gender = user.getGender();
        String cnic = user.getCnic();
        int org_id = user.getOrg_id();
        String password = user.getPassword();
        String email = user.getEmail();
        String emp_id = user.getEmp_id();
        //String citizenship = user.getCitizenship();
        int role_id = user.getRole().getRole_id();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fyp", "root", "");
            preparedStatement = connection.prepareStatement("insert into subuser(full_name,user_address,user_phoneno,cnic,email,user_name,password,emp_id,org_id) values (?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, full_name);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3, phone_no);
            preparedStatement.setString(4, cnic);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, user_username);
            preparedStatement.setString(7, password);
            preparedStatement.setString(8, emp_id);
            preparedStatement.setInt(9, org_id);
            //preparedStatement.setInt(8, role_id);
            int a = preparedStatement.executeUpdate();
            if (a == 1) {
                status = a;            }
        } catch (Exception ex) {
            out.write(Integer.parseInt(ex + ""));
        }
        return user;
    }
    public List<User> getRegisteredOrganization(int role_id){
        List<User> userList = new ArrayList<User>();

        try {
            dbConnection();
            resultSet = statement.executeQuery("SELECT * FROM users where role_id = '" + role_id + "' ");
            while (resultSet.next()) {
                User user = new User();
                user.setUser_id(resultSet.getInt("user_id"));
                user.setFull_name(resultSet.getString("user_fullname"));
                user.setAddress(resultSet.getString("user_address"));
                user.setPhone_no(resultSet.getString("user_phoneno"));
                user.setCnic(resultSet.getString("user_cnic"));
                user.setGender(resultSet.getString("user_gender"));
                //user.setCitizenship(resultSet.getString("user_citizenship"));
                user.setEmail(resultSet.getString("user_email"));
                user.setUser_name(resultSet.getString("username"));
                user.setPassword(resultSet.getString("user_password"));
                //user.resultSet.getString("role_id"));
                user.setBlock_status(resultSet.getBoolean("user_block_status"));
                user.setApproved_status(resultSet.getBoolean("user_approved"));
                //user.setOrg_id(resultSet.getString("org_id"));
                userList.add(user);
            }
            connection.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }
    public User checkLogin(String user_username, String password) {
        User user = new User() ;
        dbConnection();
        user = getUser(user_username);
        dbConnection();
        String role = "";
        int role_id = 0;
        if(user_username.equals("awamiawazadmin") && password.equals("awamiawazadmin"))
        {

        }
        System.out.println("Custome User Name: " + user_username + "  Password: " + password);

        try {
            preparedStatement = connection.prepareStatement("select * from users where username=? and user_password=?");// and user_block_status=? ");
            preparedStatement.setString(1, user_username);
            preparedStatement.setString(2, password);
            //preparedStatement.setBoolean(3, false);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                user = getUser(user_username);
                dbConnection();
                role_id = rs.getInt("role_id");
            } else {
                return user;
            }
            dbConnection();
            preparedStatement = connection.prepareStatement("select * from roles where role_id=? ");
            preparedStatement.setInt(1, role_id);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                role = rs.getString("role_name");
            }
        } catch (Exception ex) {
            out.write(Integer.parseInt(ex + ""));
        }
        return user;
    }
    public User checkLoginSubuser(String user_username, String password) {
        User user = null;
        dbConnection();
        dbConnection();
        String role = "";
        int role_id = 0;
        System.out.println("Custome User Name: "+user_username + "  Password: "+password);
        try {
            preparedStatement = connection.prepareStatement("select * from subuser where user_name=? and password=?");
            preparedStatement.setString(1, user_username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next())
            {
                user.setOrg_id(rs.getInt("org_id"));
                System.out.println("org_name: " + rs.getString("org_name"));
                user.setEmp_id(rs.getString("emp_id"));
                user.setFull_name(rs.getString("full_name"));
                user.setAddress(rs.getString("user_address"));
                user.setPhone_no(rs.getString("user_phoneno"));
                user.setCnic(rs.getString("cnic"));
                user.setEmail(rs.getString("email"));
                user.setOrg_name(rs.getString("org_name"));
                user.setPassword(rs.getString("password"));
                user.setRole(getRoleById(2));

            }
        } catch (Exception ex) {
            out.write(Integer.parseInt(ex + ""));
        }
        return user;
    }


    // Role and Access Right Management
    public List<AccessRights> getAccessRight() {
        List<AccessRights> accessRights = new ArrayList<AccessRights>();

        AccessRights accessRight = new AccessRights();
        try {
            dbConnection();
            resultSet = statement.executeQuery("SELECT * FROM access_rights");

            while (resultSet.next()) {
                accessRight = new AccessRights();
                accessRight.setA_id(resultSet.getInt(1));//("access_right_id"));
                System.out.println("UserController: get AccessRight:" + accessRight.getA_id());
                accessRight.setCan_report(resultSet.getBoolean(2)); //("can_report"));
                accessRight.setView_report(resultSet.getBoolean(3));//("view_report"));
                accessRight.setDelete_report(resultSet.getBoolean(4));//("delete_report"));
                accessRight.setUpdate_report(resultSet.getBoolean(5));//("update_report"));
                accessRight.setBlock_users(resultSet.getBoolean(6));//("block_user"));
                accessRight.setDelete_users(resultSet.getBoolean(7));//("delete_user"));
                accessRight.setView_users(resultSet.getBoolean(8));//("view_user"));
                accessRight.setGenerate_visual(resultSet.getBoolean(9));//("generate_visual"));
                accessRight.setGenerate_data_reports(resultSet.getBoolean(10));//("generate_data_reports"));
                accessRights.add(accessRight);

            }
            connection.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return accessRights;
    }

    public void insertAccessRight(AccessRights accessRights) {
        try {
            dbConnection();
            String sql = "INSERT INTO access_rights(can_report,view_report,delete_report,update_report,block_user,delete_user,view_user,generate_visual,generate_data_reports) VALUES (?,?,?,?,?,?,?,?,?)";
            // RoleBean roleBean = new RoleBean();
            // roleBean = userBean.getRole();
            preparedStatement = connection.prepareStatement(sql);
            boolean array[] = new boolean[9];
            array[0] = accessRights.isCan_report();
            array[1] = accessRights.isView_report();//_report();
            array[2] = accessRights.isDelete_report();
            array[3] = accessRights.isUpdate_report();
            array[4] = accessRights.isBlock_users();
            array[5] = accessRights.isDelete_users();
            array[6] = accessRights.isView_users();
            array[7] = accessRights.isGenerate_visual();
            array[8] = accessRights.isGenerate_data_reports();
/*
            for(int i=0;i<9;i++)
            {
                if(accessRights.isCan_report())
                { preparedStatement.setBoolean(1, true);System.out.println("DAtaBAse: insert Accessight can_report: " + accessRights.isCan_report());
                }
               else if(accessRights.isView_report()) { System.out.println("DAtaBAse: insert Accessight View_Report: " + accessRights.isView_report());
                    }
               else if(accessRights.isDelete_report()) { preparedStatement.setBoolean(3, true);}
               else if(accessRights.isUpdate_report()) { preparedStatement.setBoolean(4, true);}
               else if (accessRights.isBlock_users()) { preparedStatement.setBoolean(5, true);}
               else if(accessRights.isDelete_users()) { preparedStatement.setBoolean(6, true);}
               else if (accessRights.isView_users()) { preparedStatement.setBoolean(7, true);}
               else if(accessRights.isGenerate_visual()) { preparedStatement.setBoolean(8, true);}
               else if(accessRights.isGenerate_data_reports()) { preparedStatement.setBoolean(9, true);} */

            preparedStatement.setBoolean(1, array[0]);
            preparedStatement.setBoolean(2, array[1]);
            preparedStatement.setBoolean(3, array[2]);
            preparedStatement.setBoolean(4, array[3]);
            preparedStatement.setBoolean(5, array[4]);
            preparedStatement.setBoolean(6, array[5]);
            preparedStatement.setBoolean(7, array[6]);
            preparedStatement.setBoolean(8, array[7]);
            preparedStatement.setBoolean(9, array[8]);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new AccessRight was inserted succesfully");
            }

            connection.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getAccessRight_id() //get the id of last inserted AccessRight
    {
        String sql = "SELECT * FROM access_rights ORDER BY access_right_id DESC LIMIT 1";
        int access_right_id = 0;
        try {
            dbConnection();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                access_right_id = resultSet.getInt(1);
                System.out.println("getAccessRightId: userController: " + access_right_id);
            }
            connection.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return access_right_id;
    }

    public AccessRights getAccessRight(int access_right_id) {
        AccessRights accessRight = new AccessRights();

        try {
            dbConnection();
            resultSet = statement.executeQuery("SELECT * FROM access_rights where access_right_id = '" + access_right_id + "'");

            while (resultSet.next()) {
                accessRight = new AccessRights();
                accessRight.setA_id(resultSet.getInt(0));
                accessRight.setCan_report(resultSet.getBoolean(1));
                accessRight.setView_report(resultSet.getBoolean(2));
                accessRight.setDelete_report(resultSet.getBoolean(3));
                accessRight.setUpdate_report(resultSet.getBoolean(4));
                accessRight.setBlock_users(resultSet.getBoolean(5));
                accessRight.setDelete_users(resultSet.getBoolean(6));
                accessRight.setView_users(resultSet.getBoolean(7));
                accessRight.setGenerate_visual(resultSet.getBoolean(8));
                accessRight.setGenerate_data_reports(resultSet.getBoolean(9));
            }
            connection.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessRight;
    }


    // Role
    public List<Role> getRoles() {
        List<Role> roles = new ArrayList<Role>();
        AccessRights accessRight = new AccessRights();
        Role role;// = new Role();
        //List<AccessRights> accessRights = new ArrayList<AccessRights>();

        try {
            dbConnection();
            preparedStatement = connection.prepareStatement("select * from roles,access_rights where roles.access_right_id=access_rights.access_right_id AND roles.role_id ");
            resultSet = statement.executeQuery("select * from roles,access_rights where roles.access_right_id=access_rights.access_right_id AND roles.role_id != '" + 45 + "' ");

            while (resultSet.next()) {

                role = new Role();
                accessRight = new AccessRights();
                role.setRole_id(resultSet.getInt(1));
                role.setRole_name(resultSet.getString(2));
                accessRight.setA_id(resultSet.getInt(3));
                accessRight.setCan_report(resultSet.getBoolean(5));
                accessRight.setView_report(resultSet.getBoolean(6));
                accessRight.setDelete_report(resultSet.getBoolean(7));
                accessRight.setUpdate_report(resultSet.getBoolean(8));
                accessRight.setBlock_users(resultSet.getBoolean(9));
                accessRight.setDelete_users(resultSet.getBoolean(10));
                accessRight.setView_users(resultSet.getBoolean(11));
                accessRight.setGenerate_visual(resultSet.getBoolean(12));
                accessRight.setGenerate_data_reports(resultSet.getBoolean(13));
                accessRight.setAdd_role(resultSet.getBoolean(15));
                role.setAccessRights(accessRight);
                roles.add(role);
            }


        } catch (Exception ex) {
            out.write(Integer.parseInt(ex + ""));
        }

        return roles;
        /*
        List<Role> roles = new ArrayList<Role>();

        try{
            Class.forName("com.mysql.jdbc.Driver");

            Role role= new Role();
            List<AccessRights> accessRights = new ArrayList<AccessRights>();
            accessRights = this.getAccessRight();
            connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/fyp","root","");
            statement=connection.createStatement();
            resultSet=statement.executeQuery("SELECT * FROM roles");

            while(resultSet.next())
            {
                role.setAccessRights(accessRights.get(0));
                role.setRole_id(resultSet.getInt("role_id"));
                role.setRole_name(resultSet.getString("role_name"));
                roles.add(role);
            }
            connection.close();
            statement.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return roles;
        */
    }
    public Role editRole(Role role, int role_id) {

        int rowsInserted = 0;
        try {
            dbConnection();
            String sql = "UPDATE roles SET role_name=? , access_right_id=? WHERE role_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, role.getRole_name());
            preparedStatement.setInt(2, role.getAccessRights().getA_id());
            rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("An existing Role was updated succesfully");
            }
            sql = "UPDATE access_rights SET can_report=?,view_report=?,delete_report=?,update_report=?,block_user=?,delete_user=?,view_user=?,generate_visual=?,generate_data_reports=?,add_comment=? WHERE access_right_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBoolean(1, role.getAccessRights().isCan_report());
            preparedStatement.setBoolean(2, role.getAccessRights().isView_report());
            preparedStatement.setBoolean(3, role.getAccessRights().isDelete_report());
            preparedStatement.setBoolean(4, role.getAccessRights().isUpdate_report());
            preparedStatement.setBoolean(5, role.getAccessRights().isBlock_users());
            preparedStatement.setBoolean(6, role.getAccessRights().isDelete_users());
            preparedStatement.setBoolean(7, role.getAccessRights().isView_report());
            preparedStatement.setBoolean(8, role.getAccessRights().isGenerate_visual());
            preparedStatement.setBoolean(9, role.getAccessRights().isGenerate_data_reports());
            preparedStatement.setBoolean(10, role.getAccessRights().isAdd_comment());
            preparedStatement.setInt(11, role.getAccessRights().getA_id());
            rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("An existing AccessRight of A Role was updated succesfully");
            }
            connection.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return role;
    }
    public void addRole(Role role) {
//        this.insertAccessRight(role.getAccessRights());
        int access_right_id = getAccessRight_id();
        try {
            dbConnection();

            System.out.println("in  addrole");
            String sql = "INSERT INTO access_rights(can_report,view_report,delete_report,update_report,block_user,delete_user,view_user,generate_visual,generate_data_report,add_comment) VALUES (?,?,?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBoolean(1, role.getAccessRights().isCan_report());
            preparedStatement.setBoolean(2, role.getAccessRights().isView_report());
            preparedStatement.setBoolean(3, role.getAccessRights().isDelete_report());
            preparedStatement.setBoolean(4, role.getAccessRights().isUpdate_report());
            preparedStatement.setBoolean(5, role.getAccessRights().isBlock_users());
            preparedStatement.setBoolean(6, role.getAccessRights().isDelete_users());
            preparedStatement.setBoolean(7, role.getAccessRights().isView_users());
            preparedStatement.setBoolean(8, role.getAccessRights().isGenerate_visual());
            preparedStatement.setBoolean(9, role.getAccessRights().isGenerate_data_reports());
            preparedStatement.setBoolean(10, role.getAccessRights().isAdd_comment());
            int rowsInserted = preparedStatement.executeUpdate();
            System.out.println("can report: "+role.getAccessRights().isCan_report());
            System.out.println("can :isView_report "+role.getAccessRights().isView_report());
            System.out.println("can :isDelete_report "+role.getAccessRights().isDelete_report());
            System.out.println("can :isUpdate_report "+role.getAccessRights().isUpdate_report());
            System.out.println("can :isBlock_users "+role.getAccessRights().isBlock_users());
            System.out.println("can :isDelete_users "+role.getAccessRights().isDelete_users());
            System.out.println("can :isView_users "+role.getAccessRights().isView_users());
            System.out.println("can :isGenerate_visual "+role.getAccessRights().isGenerate_visual());
            System.out.println("can :isGenerate_data_reports "+role.getAccessRights().isGenerate_data_reports());
            sql=null;
            //preparedStatement=null;
             access_right_id = getAccessRight_id();
            dbConnection();
             sql = "INSERT INTO roles(role_name,access_right_id) VALUES (?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, role.getRole_name());
            preparedStatement.setInt(2, access_right_id);

             rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new Role was inserted succesfully");
            }
            connection.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean deleteRole(int role_id) {
        dbConnection();
        boolean status = false;
        System.out.println("DAtaBAse: DeleteRole: role_id: " + role_id);
        int access_right_id = this.getRoleById(role_id).getAccessRights().getA_id();
        System.out.println("DAtaBAse: DeleteRole: access_right_id : " + access_right_id);

        try {
            String sql = "DELETE FROM roles WHERE role_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, role_id);

            //String sql = "DELETE  roles, access_rights from roles INNER JOIN access_rights ON roles.access_right_id = access_rights.access_right_id WHERE roles.access_right_id=? ";
//            preparedStatement.setInt(1, access_right_id);
            //preparedStatement = connection.prepareStatement(sql);
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A role was deleted succesfully");
                status  = true;
            }//DELETE * from access_rights where access_right_id
            sql = "DELETE FROM access_rights where access_right_id=?";
            preparedStatement = null;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, access_right_id);
            rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Accrss Rigts of Role was deleted succesfully");
                status  = true;
            }
            connection.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return status;
    }
    //single role
    public Role getDatabaseRole(String name) {
        Role role = new Role();
        AccessRights accessRights = new AccessRights();

        try {
            dbConnection();
            preparedStatement = connection.prepareStatement("select * from roles,access_rights where roles.access_right_id=access_rights.access_right_id and roles.role_name='" + name + "'");

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {

                role.setRole_id(rs.getInt(1));
                role.setRole_name(rs.getString(2));
                accessRights.setA_id(rs.getInt(4));
                accessRights.setCan_report(rs.getBoolean(5));
                accessRights.setView_report(rs.getBoolean(6));
                accessRights.setDelete_report(rs.getBoolean(7));
                //accessRights.setUpdate_report(rs.getBoolean(8));
                accessRights.setBlock_users(rs.getBoolean(8));
                accessRights.setDelete_users(rs.getBoolean(9));
                accessRights.setView_users(rs.getBoolean(10));
                accessRights.setGenerate_visual(rs.getBoolean(11));
                accessRights.setGenerate_data_reports(rs.getBoolean(12));
                accessRights.setAdd_comment(rs.getBoolean(14));
                role.setAccessRights(accessRights);
            }

        } catch (Exception ex) {
            out.write(Integer.parseInt(ex + ""));
        }

        return role;

    }
    public Role getRoleById(int role_id) {
        Role role = new Role();
        AccessRights accessRights = new AccessRights();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fyp", "root", "");
            preparedStatement = connection.prepareStatement("select * from roles,access_rights where roles.access_right_id=access_rights.access_right_id and roles.role_id='" + role_id + "'");

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {

                System.out.println("rs.Delete Staus: "+rs.getBoolean(10));

                role.setRole_id(rs.getInt(1));
                role.setRole_name(rs.getString(2));
                accessRights.setA_id(rs.getInt(3));
                accessRights.setCan_report(rs.getBoolean(5));
                accessRights.setView_report(rs.getBoolean(6));
                accessRights.setDelete_report(rs.getBoolean(7));
                accessRights.setUpdate_report(rs.getBoolean(8));
                accessRights.setBlock_users(rs.getBoolean(9));
                accessRights.setDelete_users(rs.getBoolean(10));
                accessRights.setView_users(rs.getBoolean(11));
                accessRights.setGenerate_visual(rs.getBoolean(12));
                accessRights.setGenerate_data_reports(rs.getBoolean(13));
                accessRights.setAdd_comment(rs.getBoolean(14));
                accessRights.setAdd_role(rs.getBoolean(15));
                accessRights.setDelete_role(rs.getBoolean(16));
                role.setAccessRights(accessRights);
            }

        } catch (Exception ex) {
            out.write(Integer.parseInt(ex + ""));
        }

        return role;

    }

    public User getUser(String userName) {
        dbConnection();
        User user = new User();
        Role role = new Role();
        try {
            dbConnection();
            String sql = "select * from users,roles where users.user_username='asadawan'AND users.role_id = roles.role_id;";
            //resultSet = statement.executeQuery("SELECT * FROM users where user_username = '" + userName + "'");
            resultSet = statement.executeQuery("select * from users,roles where users.username='" + userName + "'AND users.role_id = roles.role_id;");

            System.out.println("GETUser");
            while (resultSet.next()) {
                user.setUser_id(resultSet.getInt("user_id"));
                user.setFull_name(resultSet.getString("user_fullname"));
                user.setAddress(resultSet.getString("user_address"));
                user.setPhone_no(resultSet.getString("user_phoneno"));
                user.setCnic(resultSet.getString("user_cnic"));
                user.setGender(resultSet.getString("user_gender"));
                user.setCitizenship(resultSet.getString("user_citizenship"));
                user.setEmail(resultSet.getString("user_email"));
                user.setUser_name(resultSet.getString("username"));
                user.setPassword(resultSet.getString("user_password"));
                user.setBlock_status(resultSet.getBoolean("user_block_status"));
                user.setApproved_status(resultSet.getBoolean("user_approved"));
                byte[] probyte=resultSet.getBytes("user_profilepicture");
                String picture=Base64.getEncoder().encodeToString(probyte);
                user.setProfilePicture(picture);
                role.setRole_id(resultSet.getInt("role_id"));
                role.setRole_name(resultSet.getString("role_name"));
                user.setRole(role);
            }
            connection.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    public User getsubuserorganizaion(String userName) {
        User user = new User();
        Role role = new Role();
        try {
            dbConnection();
            String sql = "select * from users,roles where users.user_username='asadawan'AND users.role_id = roles.role_id;";
            //resultSet = statement.executeQuery("SELECT * FROM users where user_username = '" + userName + "'");
            resultSet = statement.executeQuery("select * from users,roles where users.sername='" + userName + "'AND users.role_id = roles.role_id;");

            while (resultSet.next()) {
                user.setUser_id(resultSet.getInt("user_id"));
                user.setFull_name(resultSet.getString("user_fullname"));
                user.setAddress(resultSet.getString("user_address"));
                user.setPhone_no(resultSet.getString("user_phoneno"));
                user.setCnic(resultSet.getString("user_cnic"));
                user.setGender(resultSet.getString("user_gender"));
                user.setCitizenship(resultSet.getString("user_citizenship"));
                user.setEmail(resultSet.getString("user_email"));
                user.setUser_name(resultSet.getString("username"));
                user.setPassword(resultSet.getString("user_password"));
                user.setBlock_status(resultSet.getBoolean("user_block_status"));
                user.setApproved_status(resultSet.getBoolean("user_approved"));
                user.setOrg_id(resultSet.getInt("org_id"));
                role.setRole_id(resultSet.getInt("role_id"));
                role.setRole_name(resultSet.getString("role_name"));
                user.setRole(role);
            }
            connection.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    public List<User> getUserList() {
        List<User> users = new ArrayList<User>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fyp", "root", "");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM users WHERE user_id != '"+54+"'");
            while (resultSet.next()) {
                User user = new User();
                boolean user_block_status = resultSet.getBoolean("user_block_status");

                user.setFull_name(resultSet.getString("user_fullname"));
                user.setUser_name(resultSet.getString("username"));
                user.setBlock_status(resultSet.getBoolean("user_block_status"));
                user.setApproved_status(resultSet.getBoolean("user_approved"));
                user.setUser_id(resultSet.getInt("user_id"));

                users.add(user);
            }
            connection.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;

    }
    public List<User> getUserListOrganization(int org_id) {
        List<User> users = new ArrayList<User>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fyp", "root", "");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM users WHERE org_id != '"+org_id+"'");
            while (resultSet.next()) {
                User user = new User();
                boolean user_block_status = resultSet.getBoolean("user_block_status");

                user.setFull_name(resultSet.getString("user_fullname"));
                user.setUser_name(resultSet.getString("username"));
                user.setBlock_status(resultSet.getBoolean("user_block_status"));
                user.setApproved_status(resultSet.getBoolean("user_approved"));
                user.setUser_id(resultSet.getInt("user_id"));

                users.add(user);
            }
            connection.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;

    }
    public boolean block_user(String user_name) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fyp", "root", "");
            String sql = "UPDATE users SET user_block_status=? WHERE username=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setString(2, user_name);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A User Was Blocked succesfully");
                return true;
            }

            connection.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean unblock_user(String user_name) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fyp", "root", "");

            String sql = "UPDATE users SET user_block_status=? WHERE username=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBoolean(1, false);
            preparedStatement.setString(2, user_name);
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            }

            connection.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean approved_user(String user_name) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fyp", "root", "");

            String sql = "UPDATE users SET user_approved=? WHERE username=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setString(2, user_name);
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            }
            connection.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean delete_user(String user_name) {
        try {
            dbConnection();
            String sql = "DELETE FROM users WHERE user_username=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user_name);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A USer was deleted succesfully");

                return true;
            }
            connection.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean changeReportStatus(String status,String r_id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fyp", "root", "");

            System.out.println("Database: " + status);
            System.out.println("Database: " + r_id);

            String sql = "UPDATE reports SET  r_status=? WHERE r_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, Integer.parseInt(r_id));
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {

                System.out.println("Database: "+ status);
                System.out.println("Database: "+ r_id);
                return true;

            }

            connection.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean changeReportStatuss(String status[]) {
        Boolean statuss = false;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fyp", "root", "");
            System.out.println("Database: "+ status[0]);
            for(int i =0; i<status.length/2 ; i++) {
            System.out.println("status["+i+"] :"+status[i]);
                i++;
            System.out.println("status["+i+"] :"+status[i]);
            }

            System.out.println("Database Length: " + status.length);
            for(int i =0; i<status.length ; i++)
            {
                String sql = "UPDATE reports SET  r_status=? WHERE r_id=?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, status[i]);
                i++;
                preparedStatement.setInt(2, Integer.parseInt(status[i]));
                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Database: "+ status);
                    statuss =  true;
                }
            }
            connection.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(statuss == true)
        {
            return  statuss;
        }

        return statuss;
    }



    public User checkLoginApi(String user_username, String password) {

        String role = "";
        User user = new User();
        int role_id = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fyp", "root", "");
            preparedStatement = connection.prepareStatement("select * from users where username=? and user_password=? and user_block_status=? ");
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

    //insertReportNewInDB  Not Working
    public Report addReportapi(Report reportBean) {

        String image64base = reportBean.getImageString();
//        String base64Image = image64base.split(",")[1];
        byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(image64base);
        String video64base = reportBean.getImageString();
        // String base64video = video64base.split(",")[1];
        byte[] videoBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(video64base);

        try {
            dbConnection();
            String sql = "INSERT INTO reports(organization_name,r_description,r_image,r_video,r_address,r_lat,r_log,r_severity,r_status,user_id,p_id) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
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
            preparedStatement.setInt(11, reportBean.getProblem().getP_id());

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
    //Show all reports in database
    public List<Report> allReportsapi() {
        List<Report> reports = new ArrayList<Report>();
        try {
            dbConnection();
            // resultSet = statement.executeQuery("SELECT * FROM reports where user_id = '" + user_id + "'");
            resultSet = statement.executeQuery("SELECT * FROM reports INNER JOIN problems on reports.p_id = problems.p_id");

            Problemm problem;
            while (resultSet.next()) {
                problem = new Problemm();
                Report report = new Report();
                String title = resultSet.getString("organization_name");
                String status = resultSet.getString("r_status");
                String address = resultSet.getString("r_address");
                int user_idd = resultSet.getInt("user_id");
                int r_id = resultSet.getInt("r_id");
                System.out.println("title: "+title);
                String description = resultSet.getString("r_description");
                byte[] image = resultSet.getBytes("r_image");
                InputStream isImage = new ByteArrayInputStream(image);
                String imageString = Base64.getEncoder().encodeToString(image);
                byte[] video = resultSet.getBytes("r_video");
                String videoString = Base64.getEncoder().encodeToString(video);
                InputStream isVideo = new ByteArrayInputStream(video);
                String severity = resultSet.getString("r_severity");
                Double lat = resultSet.getDouble("r_lat");
                Double logg = resultSet.getDouble("r_log");
                problem.setP_id(resultSet.getInt("p_id"));
                problem.setU_id(resultSet.getInt("P_userid"));
                problem.setProblem_name(resultSet.getString("problem"));
                report.setTitle(title);
                report.setProblem(problem);
                report.setDescription(description);
                report.setImageString(imageString);
                report.setVideoString(videoString);
                //report.setImagee(image);
                //report.setVideoo(video);
                report.setLat(lat);
                report.setLog(logg);
                report.setSeverity(severity);
                report.setImageString(imageString);
                report.setVideoString(videoString);
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


    public User getUserById(int userid) //hum ney yeh wala function dala ha
    {
        User user = new User();
        Role role = new Role();
        try {
            dbConnection();
            String sql = "select * from users,roles where users.user_username='asadawan' AND users.role_id = roles.role_id;";
            //resultSet = statement.executeQuery("SELECT * FROM users where user_username = '" + userName + "'");
            resultSet = statement.executeQuery("select * from users,roles where users.user_id='" + userid + "'AND users.role_id = roles.role_id;");

            while (resultSet.next()) {
                user.setUser_id(resultSet.getInt("user_id"));
                byte[] image = resultSet.getBytes("user_profilepicture");
                String picture= Base64.getEncoder().encodeToString(image);
                user.setProfilePicture(picture);
                user.setFull_name(resultSet.getString("user_fullname"));
                user.setAddress(resultSet.getString("user_address"));
                user.setPhone_no(resultSet.getString("user_phoneno"));
                user.setCnic(resultSet.getString("user_cnic"));
                user.setGender(resultSet.getString("user_gender"));
                user.setCitizenship(resultSet.getString("user_citizenship"));
                user.setEmail(resultSet.getString("user_email"));
                user.setUser_name(resultSet.getString("username"));
                user.setPassword(resultSet.getString("user_password"));
                user.setBlock_status(resultSet.getBoolean("user_block_status"));
                user.setApproved_status(resultSet.getBoolean("user_approved"));
                role.setRole_id(resultSet.getInt("role_id"));
                role.setRole_name(resultSet.getString("role_name"));
                user.setRole(role);
            }
            connection.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    // Show report of user/citizen by userid
    public List<Report> allReportsUserapi(int user_id) {
        List<Report> reports = new ArrayList<Report>();

        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fyp", "root", "");
            statement = connection.createStatement();
            // resultSet = statement.executeQuery("SELECT * FROM reports where user_id = '" + user_id + "'");
            resultSet = statement.executeQuery("SELECT * FROM reports INNER JOIN problems on reports.p_id = problems.p_id where user_id = '" + user_id + "'");

            Problemm problem;
            while (resultSet.next()) {
                problem = new Problemm();
                Report report = new Report();
                String title = resultSet.getString("organization_name");
                String status = resultSet.getString("r_status");
                String address = resultSet.getString("r_address");
                int user_idd = resultSet.getInt("user_id");
                int r_id = resultSet.getInt("r_id");
                System.out.println("title: "+title);
                String description = resultSet.getString("r_description");
                byte[] image = resultSet.getBytes("r_image");
               // InputStream isImage = new ByteArrayInputStream(image);
                String imageString = Base64.getEncoder().encodeToString(image);
                byte[] video = resultSet.getBytes("r_video");
                String videoString = Base64.getEncoder().encodeToString(video);
                //InputStream isVideo = new ByteArrayInputStream(video);
                String severity = resultSet.getString("r_severity");
                Double lat = resultSet.getDouble("r_lat");
                Double logg = resultSet.getDouble("r_log");
                problem.setP_id(resultSet.getInt("p_id"));
                problem.setU_id(resultSet.getInt("P_userid"));
                problem.setProblem_name(resultSet.getString("problem"));
                report.setTitle(title);
                report.setProblem(problem);
                report.setDescription(description);
                //report.setImagee(image);
                //report.setVideoo(video);
                report.setImageString(imageString);
                report.setVideoString(videoString);
                report.setLat(lat);
                report.setLog(logg);
                report.setSeverity(severity);
                report.setAddress(address);
                report.setUser_id(user_idd);
                report.setStatus(status);
                report.setImageString(imageString);
                report.setVideoString(videoString);
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
    public List reportLatLogapi() {
        // List<ArrayList<String>> latlogs = new ArrayList<ArrayList<String>>();
        List<MyItems> latLogs = new ArrayList<MyItems>();


        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fyp", "root", "");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM reports");
            while (resultSet.next()) {
                // ArrayList<String> latlog = new ArrayList<String>();
                MyItems latlog = new MyItems();

                String lat = resultSet.getString("r_lat");
                System.out.println("lat: "+lat);
                String logg = resultSet.getString("r_log");
                System.out.println("log: "+logg);
                System.out.println("\n\n\n");
                latlog.setLat(resultSet.getDouble("r_lat"));
                latlog.setLog(resultSet.getDouble("r_log"));
                latlog.setR_title(resultSet.getString("organization_name"));
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
    public List<User> getOrganizationListapi() {
        List<User> orgList = new ArrayList<User>();

        try {
            dbConnection();
            resultSet = statement.executeQuery("SELECT * FROM users WHERE role_id = '"+2+"'");
            while (resultSet.next()) {
                User user = new User();

                user.setFull_name(resultSet.getString("user_fullname"));
                user.setUser_id(resultSet.getInt("user_id"));
                orgList.add(user);
            }
            connection.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orgList;

    }
    public List<Problemm> getOrganizationProblemsapi(int user_id) {
        List<Problemm> problemList = new ArrayList<Problemm>();

        try {
            dbConnection();
            resultSet = statement.executeQuery("SELECT * FROM problems WHERE P_userid = '"+user_id+"'");
            while (resultSet.next()) {
                Problemm problem = new Problemm();

                problem.setProblem_name(resultSet.getString("problem"));
                problem.setU_id(resultSet.getInt("P_userid"));
                problem.setP_id(resultSet.getInt("p_id"));
                problemList.add(problem);
            }
            connection.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return problemList;

    }
    public Feedback addCommentapi(Feedback feedback){
        try {
            dbConnection();
            String sql = "INSERT INTO feedback(f_description,f_rating,user_id,r_id) VALUES (?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, feedback.getComment());
            preparedStatement.setDouble(2, feedback.getRanking());
            preparedStatement.setInt(3, feedback.getUser_id());
            preparedStatement.setInt(4, feedback.getR_id());
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new Comment was added succesfully");
                return feedback;
            }
            else
            {
                feedback = null;
            }
            connection.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return feedback;
    }
    public int addComment(Feedback feedback){
        try {
            dbConnection();
            String sql = "INSERT INTO feedback(f_description,f_rating,user_id,r_id) VALUES (?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, feedback.getComment());
            preparedStatement.setDouble(2, feedback.getRanking());
            preparedStatement.setInt(3, feedback.getUser_id());
            preparedStatement.setInt(4, feedback.getR_id());
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {

                System.out.println("A new Comment was added succesfully");
                return 10;
            }
            else
            {
                feedback = null;
            }
            connection.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public List<Feedback> getCommentsApi(int r_id){  List<Feedback> feedbacks = new ArrayList<Feedback>();
        try {
            dbConnection();
            resultSet = statement.executeQuery("SELECT * FROM feedback where r_id = '" + r_id + "'");
            while (resultSet.next()) {
                Feedback feedback = new Feedback();
                String description = resultSet.getString("f_description");
                double rating = resultSet.getDouble("f_rating");
                int user_id = resultSet.getInt("user_id");
                int rid = resultSet.getInt("r_id");
                feedback.setR_id(rid);
                feedback.setUser_id(user_id);
                feedback.setComment(description);
                feedback.setRanking(rating);
                feedback.setFeedback_id(resultSet.getInt("f_id"));
                feedbacks.add(feedback);
            }
            connection.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return feedbacks;
    }




}
