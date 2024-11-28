package backend.user;

import backend.db.DatabaseManager;

import java.sql.*;
import java.util.Optional;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    private Connection ensureConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DatabaseManager.getInstance().getConnection();
        }
        return connection;
    }

    // 회원가입 (DB에 새 사용자 등록)
    public boolean registerUser(String name, String email, String password, String birthDate, String gender) throws SQLException {
        ensureConnection();
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

    // 로그인 (email, password로 로그인 시도)
    public Optional<Integer> loginUser(String email, String password) throws SQLException {
        ensureConnection();
        String query = "SELECT userId FROM User WHERE email = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(rs.getInt("userId"));
            }
        }
        return Optional.empty();
    }

    // 사용자 정보 조회 (userID 기준)
    public Optional<UserDTO> getUserById(int userId) throws SQLException {
        ensureConnection();
        String query = "SELECT * FROM User WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(createUserFromResultSet(rs));
            }
        }
        return Optional.empty();
    }

    // 사용자 이름으로 사용자 정보 조회
    public Optional<UserDTO> getUserByName(String name) throws SQLException {
        ensureConnection();
        String query = "SELECT * FROM User WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(createUserFromResultSet(rs));
            }
        }
        return Optional.empty();
    }

    // 사용자 이름으로 사용자 ID 조회
    public Optional<Integer> getUserIdByName(String name) throws SQLException {
        ensureConnection();
        String query = "SELECT userId FROM User WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(rs.getInt("userId"));
            }
        }
        return Optional.empty();
    }

    // 사용자 프로필 수정 (이름 및 성별 변경)
    public boolean updateUserProfile(int userId, String name, String gender) throws SQLException {
        ensureConnection();
        String query = "UPDATE User SET name = ?, gender = ? WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, gender);
            stmt.setInt(3, userId);
            return stmt.executeUpdate() > 0; // 업데이트 성공 여부 반환
        }
    }

    // 비밀번호 변경
    public boolean updateUserPassword(int userId, String newPassword) throws SQLException {
        ensureConnection();
        String query = "UPDATE User SET password = ? WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newPassword);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0; // 업데이트 성공 여부 반환
        }
    }

    // UserDTO 생성 메서드
    private UserDTO createUserFromResultSet(ResultSet rs) throws SQLException {
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
        return user;
    }
}
