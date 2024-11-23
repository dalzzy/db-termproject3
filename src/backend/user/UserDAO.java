package backend.user;

import java.sql.*;
import java.util.Optional;

public class UserDAO {
	private Connection connection;
	
	public UserDAO(Connection connection) {
		this.connection = connection;
	}
	
	//회원가입 (DB에 새 사용자 등록)
	public boolean registerUser(String name, String email, String password, String birthDate, String gender) throws SQLException{
		String query = "INSERT INTO User (name, email, password, birthDate, gender) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setDate(4, Date.valueOf(birthDate));
            stmt.setString(5, gender);
            return stmt.executeUpdate() > 0;
        }
    }
	
	//로그인 (email, password로 로그인 시도)
	//성공시 userID 반환, 실패시 Empty Optional 반환
	public Optional<Integer> loginUser(String email, String password) throws SQLException{
		String query = "SELECT userId FROM User WHERE email = ? AND password = ?";
		try(PreparedStatement stmt = connection.prepareStatement(query)){
			stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(rs.getInt("userId"));
            }
		}
		return Optional.empty();
	}
	
	//사용자 정보 조회 (userID 기준)
	//성공시 사용자 정보 반환, 실패시 Empty Optional 반환
	public Optional<UserDTO> getUserById(int userId) throws SQLException {
        String query = "SELECT * FROM User WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId); 
            ResultSet rs = stmt.executeQuery(); 
            if (rs.next()) { 
                UserDTO user = new UserDTO();
                user.setUserId(rs.getInt("userId"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setBirthDate(rs.getDate("birthDate").toString()); 
                user.setGender(rs.getString("gender"));
                user.setNotificationSet(rs.getInt("notificationSet"));
                user.setCreatedAt(rs.getTimestamp("createdAt").toString()); 
                user.setUpdatedAt(rs.getTimestamp("updatedAt").toString()); 
                return Optional.of(user);
            }
        }
        return Optional.empty(); 
    }
}

