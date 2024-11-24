package backend.comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {
    private Connection connection;

    public CommentDAO(Connection connection) {
        this.connection = connection;
    }

    public List<CommentDTO> getCommentsByPostId(int postId) throws SQLException {
        List<CommentDTO> comments = new ArrayList<>();
        String query = "SELECT * FROM Comment WHERE postId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, postId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CommentDTO comment = new CommentDTO();
                comment.setCommentId(rs.getInt("commentId"));
                comment.setUserId(rs.getInt("userId"));
                comment.setPostId(rs.getInt("postId"));
                comment.setContent(rs.getString("content"));
                comment.setCreatedAt(rs.getString("createdAt"));
                comment.setUpdatedAt(rs.getString("updatedAt"));
                comments.add(comment);
            }
        }
        return comments;
    }

    public boolean addComment(CommentDTO comment) throws SQLException {
        String query = "INSERT INTO Comment (userId, postId, content) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, comment.getUserId());
            stmt.setInt(2, comment.getPostId());
            stmt.setString(3, comment.getContent());
            return stmt.executeUpdate() > 0;
        }
    }
}
