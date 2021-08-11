import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Application {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JList booksList;
    private JRadioButton authorRadioButton;
    private JList searchedBooksList;
    private JTextField titleTextField;
    private JTextField authorTextField;
    private JTextField illustratorTextField;
    private JTextField ISBNTextField;
    private JTextField decimalTextField;
    private JTextField fictionTextField;
    private JTextField genreTextField;
    private JTextArea descriptionTextArea;
    private JRadioButton titleRadioButton;
    private JTextField searchTextField;
    private JButton searchButton;
    private JTextField tagsTextField;
    private JTextField currentUserTextField;
    private JList usersList;
    private JButton addUserButton;
    private JButton editUserButton;
    private JButton removeUserButton;
    private JList adminBooksList;
    private JButton addBookButton;
    private JButton editBookButton;
    private JButton removeBookButton;
    private ArrayList<Book> booksArray = new ArrayList<>();
    private Database db;
    private User currentUser;


    public Application(User user){
        currentUser = user;
        currentUserTextField.setText(user.getName());
        db = new Database();
        JFrame frame = new JFrame("Library Management System");
        frame.setContentPane(panel1);
        Toolkit tk = frame.getToolkit();
        frame.setMinimumSize(new Dimension(tk.getScreenSize().width, tk.getScreenSize().height - 40));
        frame.setResizable(false);
        frame.setLocation(new Point(0,0));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fillBooksList();
        frame.setVisible(true);
        Application app = this;
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                BookEditor bk = new BookEditor(app);
            }
        });
        editBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        adminBooksList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if(adminBooksList.getSelectedValue() != null || !adminBooksList.isSelectionEmpty()){
                    editBookButton.setEnabled(true);
                }
            }
        });
        editBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String title = adminBooksList.getSelectedValue().toString();
                Book book = null;
                for(Book books : booksArray){
                    if(books.getTitle().equalsIgnoreCase(title)){
                        book = books;
                    }
                }
                BookEditor bk = new BookEditor(false, book, app);
            }
        });
    }

    public void fillBooksList(){
        booksArray = db.getAllBooksArray();
        DefaultListModel<String> model = new DefaultListModel<>();
        for(Book book : booksArray){
            model.addElement(book.getTitle());
        }
        adminBooksList.setModel(model);
        booksList.setModel(model);
    }

}
