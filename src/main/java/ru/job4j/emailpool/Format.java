package ru.job4j.emailpool;

public class Format {
    private final String subjectFormat = "Notification {username} to email {email}";
    private final String bodyFormat = "Add a new event to {username}";
    private final String username;
    private final String email;

    public Format(User user) {
        this.username = user.getName();
        this.email = user.getEmail();
    }

    public String getSubject() {
        return subjectFormat.replaceAll("\\{username}", username).replaceAll("\\{email}", email);
    }

    public String getBody() {
        return bodyFormat.replaceAll("\\{username}", username);
    }
}
