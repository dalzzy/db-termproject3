//package backend.user;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
//public class ProfileService {
//	private UserDAO userDAO;
//
//	public ProfileService(Connection connection) {
//		this.userDAO = new UserDAO(connection);
//	}
//
//	// 사용자 프로필 수정
//    public boolean updateProfile(int userId, String name, String gender) {
//        try {
//            Optional<UserDTO> userOpt = userDAO.getUserById(userId);
//            if (userOpt.isPresent()) {
//                String query = "UPDATE User SET name = ?, gender = ? WHERE userId = ?";
//                try (PreparedStatement stmt = connection.prepareStatement(query)) {
//                    stmt.setString(1, name);
//                    stmt.setString(2, gender);
//                    stmt.setInt(3, userId);
//                    return stmt.executeUpdate() > 0;
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    // 비밀번호 변경 (기존 비밀번호 한번 확인하고 틀리면 실패, 맞으면 변경 시도)
//    public boolean changePassword(int userId, String oldPassword, String newPassword) {
//        try {
//            Optional<UserDTO> userOpt = userDAO.getUserById(userId);
//            if (userOpt.isPresent()) {
//            	if(user.getPassword().equals(oldPassword)) { //기존 비밀번호 확인
//            		String query = "UPDATE User SET password = ? WHERE userId = ?";
//                    try (PreparedStatement stmt = connection.prepareStatement(query)) {
//                        stmt.setString(1, newPassword);
//                        stmt.setInt(2, userId);
//                        return stmt.executeUpdate() > 0;
//                    }
//            	}	else {
//            		return false; //기존 비밀번호 불일치
//            	}
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//}
//
