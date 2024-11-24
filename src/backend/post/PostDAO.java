package backend.post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {
    private Connection connection;

    public PostDAO(Connection connection) {
        this.connection = connection;
    }

    // Create a new post
    public void addPost(PostDTO post) throws SQLException {
        String query = "INSERT INTO Post (userId, content) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, post.getUserId());
            ps.setString(2, post.getContent());
            ps.executeUpdate();
        }
    }

    // Retrieve a post by its ID
    public PostDTO getPostById(int postId) throws SQLException {
        String query = "SELECT * FROM Post WHERE postId = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, postId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PostDTO post = new PostDTO();
                    post.setPostId(rs.getInt("postId"));
                    post.setUserId(rs.getInt("userId"));
                    post.setContent(rs.getString("content"));
                    post.setCreatedAt(rs.getTimestamp("createdAt"));
                    post.setUpdatedAt(rs.getTimestamp("updatedAt"));
                    return post;
                }
            }
        }
        return null;
    }

    // Retrieve all posts by a user
    public List<PostDTO> getPostsByUserId(int userId) throws SQLException {
        String query = "SELECT * FROM Post WHERE userId = ?";
        List<PostDTO> postList = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PostDTO post = new PostDTO();
                    post.setPostId(rs.getInt("postId"));
                    post.setUserId(rs.getInt("userId"));
                    post.setContent(rs.getString("content"));
                    post.setCreatedAt(rs.getTimestamp("createdAt"));
                    post.setUpdatedAt(rs.getTimestamp("updatedAt"));
                    postList.add(post);
                }
            }
        }
        return postList;
    }

    // Update a post's content
    public void updatePost(PostDTO post) throws SQLException {
        String query = "UPDATE Post SET content = ?, updatedAt = CURRENT_TIMESTAMP WHERE postId = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, post.getContent());
            ps.setInt(2, post.getPostId());
            ps.executeUpdate();
        }
    }

    // Delete a post by its ID
    public void deletePost(int postId) throws SQLException {
        String query = "DELETE FROM Post WHERE postId = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, postId);
            ps.executeUpdate();
        }
    }
}
