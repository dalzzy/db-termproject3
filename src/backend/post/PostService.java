package backend.post;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PostService {
    private PostDAO postDAO;

    public PostService(Connection connection) {
        this.postDAO = new PostDAO(connection);
    }

    // Add a new post
    public void addPost(PostDTO post) {
        try {
            postDAO.addPost(post);
        } catch (SQLException e) {
            System.err.println("Error adding post: " + e.getMessage());
        }
    }

    // Get a post by its ID
    public PostDTO getPostById(int postId) {
        try {
            return postDAO.getPostById(postId);
        } catch (SQLException e) {
            System.err.println("Error retrieving post: " + e.getMessage());
            return null;
        }
    }

    // Get all posts by a user
    public List<PostDTO> getPostsByUserId(int userId) {
        try {
            return postDAO.getPostsByUserId(userId);
        } catch (SQLException e) {
            System.err.println("Error retrieving posts: " + e.getMessage());
            return null;
        }
    }

    // Update a post
    public void updatePost(PostDTO post) {
        try {
            postDAO.updatePost(post);
        } catch (SQLException e) {
            System.err.println("Error updating post: " + e.getMessage());
        }
    }

    // Delete a post
    public void deletePost(int postId) {
        try {
            postDAO.deletePost(postId);
        } catch (SQLException e) {
            System.err.println("Error deleting post: " + e.getMessage());
        }
    }
}
