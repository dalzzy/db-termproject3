package backend.comment;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CommentService {
    private CommentDAO commentDAO;

    public CommentService(Connection connection) {
        this.commentDAO = new CommentDAO(connection);
    }

    public List<CommentDTO> fetchCommentsForPost(int postId) {
        try {
            return commentDAO.getCommentsByPostId(postId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean createComment(CommentDTO comment) {
        try {
            return commentDAO.addComment(comment);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
