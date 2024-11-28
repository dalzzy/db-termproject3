package backend.user;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class UserService {
    private UserDAO userDAO;

    public UserService(Connection connection) {
        this.userDAO = new UserDAO(connection);
    }

    // 회원가입
    public boolean registerUser(String name, String email, String password, String birthDate, String gender) {
        try {
            return userDAO.registerUser(name, email, password, birthDate, gender);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 로그인
    public Optional<Integer> loginUser(String email, String password) {
        try {
            return userDAO.loginUser(email, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    // 사용자 정보 조회 (userId 기준)
    public Optional<UserDTO> getUserById(int userId) {
        try {
            return userDAO.getUserById(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    // 사용자 정보 조회 (name 기준)
    public Optional<UserDTO> getUserByName(String name) {
        try {
            return userDAO.getUserByName(name);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    // 사용자 ID 조회 (name 기준)
    public int getUserIdByName(String name) {
        try {
            return userDAO.getUserIdByName(name)
                    .orElseThrow(() -> new UserNotFoundException("User not found: " + name));
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // 예외 상황에서 -1 반환
        }
    }

    public class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }
}
