package backend.follow;

import java.sql.SQLException;
import java.util.List;


public class FollowService {
    private FollowDAO followDAO;

    public FollowService(FollowDAO followDAO) {
        this.followDAO = followDAO;
    }

    //팔로우
    public boolean followUser(int userId, int followedUserId) {
        try {
            FollowDTO follow = new FollowDTO();
            follow.setUserId(userId);
            follow.setFollowedUserId(followedUserId);
            return followDAO.addFollow(follow);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //언팔로우
    public boolean unfollowUser(int userId, int followedUserId) {
        try {
            return followDAO.removeFollow(userId, followedUserId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //특정 사용자가 팔로우한 사용자 목록 조회
    public List<FollowDTO> getFollowedUsers(int userId) {
        try {
            return followDAO.getFollowedUsers(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    //특정 사용자를 팔로우하는 사용자 목록 조회
    public List<FollowDTO> getFollowers(int followedUserId) {
        try {
            return followDAO.getFollowers(followedUserId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
	    
    // 팔로워 수 조회
    public int getFollowerCount(int followedUserId) {
        try {
            return followDAO.getFollowerCount(followedUserId);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0; 
        }
    }

    // 팔로잉 수 조회
    public int getFollowingCount(int userId) {
        try {
            return followDAO.getFollowingCount(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0; 
        }
    }
}
