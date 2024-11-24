package backend.follow;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FollowDAO {
    private Connection connection;

    public FollowDAO(Connection connection) {
        this.connection = connection;
    }

    // 팔로우 추가
    public boolean addFollow(FollowDTO follow) throws SQLException {
        String query = "INSERT INTO Follow (userId, followedUserId) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, follow.getUserId());
            stmt.setInt(2, follow.getFollowedUserId());
            return stmt.executeUpdate() > 0; 
        }
    }

    // 팔로우 삭제 (언팔로우)
    public boolean removeFollow(int userId, int followedUserId) throws SQLException {
        String query = "DELETE FROM Follow WHERE userId = ? AND followedUserId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, followedUserId);
            return stmt.executeUpdate() > 0; 
        }
    }

    // 특정 사용자가 팔로우하는 사용자 리스트 조회
    public List<FollowDTO> getFollowedUsers(int userId) throws SQLException {
        List<FollowDTO> followedUsers = new ArrayList<>();
        String query = "SELECT * FROM Follow WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                FollowDTO follow = new FollowDTO();
                follow.setFollowId(rs.getInt("followId"));
                follow.setUserId(rs.getInt("userId"));
                follow.setFollowedUserId(rs.getInt("followedUserId"));
                followedUsers.add(follow);
            }
        }
        return followedUsers;
    }

    // 특정 사용자를 팔로우하는 사용자 리스트 조회
    public List<FollowDTO> getFollowers(int followedUserId) throws SQLException {
        List<FollowDTO> followers = new ArrayList<>();
        String query = "SELECT * FROM Follow WHERE followedUserId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, followedUserId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                FollowDTO follow = new FollowDTO();
                follow.setFollowId(rs.getInt("followId"));
                follow.setUserId(rs.getInt("userId"));
                follow.setFollowedUserId(rs.getInt("followedUserId"));
                followers.add(follow);
            }
        }
        return followers;
    }
	
    //팔로워 수 조회
    public int getFollowerCount(int followedUserId) throws SQLException{
    	String query = "SELECT COUNT(*) AS followerCount FROM Follow WHERE followedUserId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, followedUserId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("followerCount");
            }
        }
        return 0;
    }
    
    //팔로잉 수 조회
    public int getFollowingCount(int userId) throws SQLException {
        String query = "SELECT COUNT(*) AS followingCount FROM Follow WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("followingCount");
            }
        }
        return 0; 
    }
}
