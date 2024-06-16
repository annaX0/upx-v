package com.example.upx_contabil;

public class Comment {
    private String userEmail;
    private String commentText;

    public Comment() {
    }

    public Comment(String userEmail, String commentText) {
        this.userEmail = userEmail;
        this.commentText = commentText;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
