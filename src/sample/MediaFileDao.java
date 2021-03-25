package sample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MediaFileDao {
    private static final Logger logger = Logger.getLogger(NoteDao.class.getName());
    public int saveMediaFile(MediaFile mediaFile) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getDBConnection();
            connection.setAutoCommit(false);
            String query = "INSERT INTO mediaFile(idMedia, Mname, Msize, Mlink, Mtype, idNote) VALUES(?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int counter = 1;
            statement.setInt(counter++, mediaFile.getIdMedia());
            statement.setString(counter++, mediaFile.getMname());
            statement.setInt(counter++, mediaFile.getMsize());
            statement.setString(counter++, mediaFile.getMlink());
            statement.setString(counter++, mediaFile.getMtype());
            statement.setInt(counter++, mediaFile.getIdNote());
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
}
