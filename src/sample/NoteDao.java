package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class NoteDao {
    private static final Logger logger = Logger.getLogger(NoteDao.class.getName());
    public int saveNote(Note note) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getDBConnection();
            connection.setAutoCommit(false);
            String query = "INSERT INTO note(`Ntitle`, `Ntag`, `Ncontent`, `NdateCreated`) VALUES(?, ?, ?, ?)";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int counter = 1;
            statement.setString(counter++, note.getNtitle());
            statement.setString(counter++, note.getNtag());
            statement.setString(counter++, note.getNcontent());
            statement.setString(counter++, note.getNdateCreated());
            statement.executeUpdate();
            connection.commit();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException exception) {
            logger.log(Level.SEVERE, exception.getMessage());
            if (null != connection) {
                connection.rollback();
            }
        } finally {
            if (null != resultSet) {
                resultSet.close();
            }

            if (null != statement) {
                statement.close();
            }

            if (null != connection) {
                connection.close();
            }
        }
        return 0;
    }

    public void saveTextContent(String textContent, int idNote) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/Note_Database_2", "root", "rootpassword");
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql = "UPDATE note " +
                    "SET Ncontent = '" + textContent + "' WHERE idNote = " + idNote +";";
            stmt.executeUpdate(sql);
            System.out.println("note save successfully");

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
            }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
    }

    public void getAllNoteToData() {
    }

//    public int findIDNote(String Nname) throws SQLException {
//        String value = null;
//        Connection connection = null;
//        PreparedStatement statement = null;
//        ResultSet resultSet = null;
//        try {
//            connection = Database.getDBConnection();
//            connection.setAutoCommit(false);
//
//            //lấy title của note ra từ item được chọn trong listview thay vào Note03
//            String query = "select idNote from note where Ntitle = '" + "Note03" +"'";
//            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
//            statement.executeUpdate();
//            connection.commit();
//            resultSet = statement.getGeneratedKeys();
//            if (resultSet.next()) {
//                return resultSet.getInt(1);
//            }
//        } catch (SQLException exception) {
//            logger.log(Level.SEVERE, exception.getMessage());
//            if (null != connection) {
//                connection.rollback();
//            }
//        } finally {
//            if (null != resultSet) {
//                resultSet.close();
//            }
//
//            if (null != statement) {
//                statement.close();
//            }
//
//            if (null != connection) {
//                connection.close();
//            }
//        }
//        return 0;
//    }
}