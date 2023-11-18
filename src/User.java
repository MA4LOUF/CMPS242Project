import lombok.Data;

@Data
public class User {
    private String username;
    private String password; // Store hashed passwords
    private String email;
    private int id;
}