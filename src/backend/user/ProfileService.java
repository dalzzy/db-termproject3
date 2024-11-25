package backend.user;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class ProfileService {
	private UserDAO userDAO;
	
	public ProfileService(Connection connection) {
		this.userDAO = new UserDAO(connection);
	}
	
	// 사용자 프로필 수정
	public boolean updateProfile(int userId, String name, String gender) {
        try {
            return userDAO.updateUserProfile(userId, name, gender); // UserDAO에 작업 위임
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 비밀번호 변경 (기존 비밀번호 한번 확인하고 틀리면 실패, 맞으면 변경 시도)
	public boolean changePassword(int userId, String oldPassword, String newPassword) {
        try {
            Optional<UserDTO> userOpt = userDAO.getUserById(userId);
            if (userOpt.isPresent()) {
                UserDTO user = userOpt.get();
                if (user.getPassword().equals(oldPassword)) {
                    return userDAO.updateUserPassword(userId, newPassword);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
	
}

