import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login {
    private JPanel panel1;
    private JTextField usernameTextField;
    private JPasswordField passwordTextField;
    private JButton loginButton;
    private JButton exitButton;
    private User currentUser;

    public Login() {
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFrame frame = (JFrame) SwingUtilities.getRoot(exitButton);
                frame.dispose();
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String username = usernameTextField.getText();
                String password = new String(passwordTextField.getPassword());

                if(login(username, password, new Database())){
                    Application application = new Application(currentUser);
                    JFrame frame = (JFrame) SwingUtilities.getRoot(loginButton);
                    frame.dispose();
                }
            }
        });

    }
    public void setUser(User user){
        if(this.currentUser == null ){
            this.currentUser = user;
        }

    }


    public static void main(String[] args){
        Login login = new Login();
        Database database = new Database();
        database.createTables();
        database.createAdminAccount();
        JFrame frame = new JFrame("LMS Login");
        frame.setContentPane(login.panel1);
        frame.getRootPane().setDefaultButton(login.loginButton);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Toolkit tk = frame.getToolkit();
        frame.setMinimumSize(new Dimension(tk.getScreenSize().width / 4, tk.getScreenSize().height / 4));
        frame.setMaximumSize(new Dimension(tk.getScreenSize().width, tk.getScreenSize().height));
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public boolean login(String username, String password, Database db){
        boolean loggedIn = false;
        if(db.login(username, password, this)){

            System.out.println("Logged in");
            System.out.println(this.currentUser.getUsername() + " | " + this.currentUser.getAccessLevel());
            loggedIn = true;
        }
        else{
            System.out.println("Login Failed, Please Try Again!");
            loggedIn = false;
        }
        return loggedIn;
    }
}
