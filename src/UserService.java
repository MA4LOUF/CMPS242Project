import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
public class UserService {

    public void registerUser(String email, String password, String username) throws SQLException{
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = Y_Database.getConnection();
            if(userExists(conn, username, email)){
                System.out.println("Username or email already in use, please choose another");
                return;
            }

            String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, hashPassword(password)); // Hash the password
            pstmt.setString(3, email);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("User registered successfully.");
            }
            else {
                System.out.println("User registration failed.");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if(pstmt != null)
                pstmt.close();
            if(conn != null)
                conn.close();
        }
    }
    public boolean loginUser(String email, String password) throws SQLException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = Y_Database.getConnection();
            String sql = "SELECT password FROM users WHERE email = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedPasswordHash = rs.getString("password");
                return BCrypt.checkpw(password, storedPasswordHash);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if(rs != null)
                rs.close();
            if(conn != null)
                conn.close();
            if(pstmt != null)
                pstmt.close();
        }
        return false;
    }
    private boolean userExists(Connection conn, String username, String email) throws SQLException{
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String sql = "SELECT COUNT(*) FROM users WHERE username = ? OR email = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
            return false;
        }
        finally {
            if (resultSet != null)
                resultSet.close();
            if (pstmt != null)
                pstmt.close();
        }
    }
    private String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }
}
