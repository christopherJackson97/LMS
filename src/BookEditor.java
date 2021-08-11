import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BookEditor {


    private JPanel panel1;
    private JTextField titleTextField;
    private JRadioButton fictionRadioButton;
    private JRadioButton nonFictionRadioButton;
    private JTextField ISBNTextField;
    private JTextField tagsTextField;
    private JTextField genreTextField;
    private JTextField decimalTextField;
    private JTextField descriptionTextField;
    private JCheckBox hasAnIllustratorCheckBox;
    private JTextField illustratorTextField;
    private JTextField authorTextField;
    private JButton submitButton;
    private JButton cancelButton;
    private JButton clearButton;
    private JLabel titleLabel;
    private JLabel authorLabel;
    private JLabel illusLabel;
    private JLabel descriptionLabel;
    private JLabel fictionOrNonFictionLabel;
    private JLabel decimalLabel;
    private JLabel ISBNLabel;
    private JLabel genreLabel;
    private JLabel tagsSeperateWithALabel;

    public BookEditor(Application app) {//This is the method that should be used if we are creating a new book in the system.
        JFrame frame = new JFrame("Adding Book");
        frame.setResizable(false);
        Toolkit tk = frame.getToolkit();
        frame.setMinimumSize(new Dimension(tk.getScreenSize().width/2, tk.getScreenSize().height/2));
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                frame.dispose();
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                for(Component comp : frame.getContentPane().getComponents()){

                    if(comp instanceof JTextField){
                        JTextField txt = (JTextField) comp;
                        txt.setText("");
                    }
                    if(comp instanceof JRadioButton){
                        JRadioButton radio = (JRadioButton) comp;
                        fictionRadioButton.setSelected(true);
                    }
                    if(comp instanceof JCheckBox){
                        JCheckBox box = (JCheckBox) comp;
                        box.setSelected(false);
                    }

                }
                frame.repaint();

            }
        });

        hasAnIllustratorCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                illustratorTextField.setEditable(hasAnIllustratorCheckBox.isSelected());
            }
        });
        nonFictionRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                decimalTextField.setEditable(nonFictionRadioButton.isSelected());
            }
        });
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(submit()){
                    System.out.println("Book Added");
                    app.fillBooksList();

                    frame.dispose();
                }
                else{
                    System.out.println("Failure, Abort Abort");
                }
            }
        });
    }
    public BookEditor(boolean adding, Book book, Application app){//This is the method that should be used if we are editing a book.
        JFrame frame = new JFrame("Editing: " + book.getTitle());
        frame.setResizable(false);
        Toolkit tk = frame.getToolkit();
        frame.setMinimumSize(new Dimension(tk.getScreenSize().width/2, tk.getScreenSize().height/2));
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setFormFields(book);
        frame.setVisible(true);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                frame.dispose();
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                for(Component comp : frame.getContentPane().getComponents()){

                    if(comp instanceof JTextField){
                        JTextField txt = (JTextField) comp;
                        txt.setText("");
                    }
                    if(comp instanceof JRadioButton){
                        JRadioButton radio = (JRadioButton) comp;
                        fictionRadioButton.setSelected(true);
                    }
                    if(comp instanceof JCheckBox){
                        JCheckBox box = (JCheckBox) comp;
                        box.setSelected(false);
                    }

                }
                frame.repaint();

            }
        });

        hasAnIllustratorCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                illustratorTextField.setEditable(hasAnIllustratorCheckBox.isSelected());
            }
        });
        nonFictionRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                decimalTextField.setEditable(nonFictionRadioButton.isSelected());
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(edit()){
                    System.out.println("Book edited");
                    app.fillBooksList();
                    frame.dispose();
                }
                else{
                    System.out.println("Bood not edited");
                }
            }
        });
    }
    public boolean edit(){
        String title, author, illustrator, description, decimal, isbn, genre;
        String tags = tagsTextField.getText();
        Boolean nonFiction;
        Boolean success = false;
        title = titleTextField.getText();
        author = authorTextField.getText();
        if(hasAnIllustratorCheckBox.isSelected() && illustratorTextField.getText().length() > 0) {
            illustrator = illustratorTextField.getText();
        }else{
            illustrator = "N/A";
        }
        description = descriptionTextField.getText();
        nonFiction = nonFictionRadioButton.isSelected();
        if(nonFiction && decimalTextField.getText().length() > 0){
            decimal = decimalTextField.getText();
        }
        else{
            decimal = "N/A";
        }
        isbn = ISBNTextField.getText();
        genre = genreTextField.getText();
        Book book = new Book(title, author, illustrator, isbn, decimal, description, genre, nonFiction);
        book.deserializeTags(tags);
        Database db = new Database();
        if(db.editBook(book)){
            success = true;
        }
        else{
            success = false;
        }
        return success;
    }

    public boolean submit(){
        String title, author, illustrator, description, decimal, isbn, genre;
        String tags = tagsTextField.getText();
        Boolean nonFiction;
        Boolean success = false;
        title = titleTextField.getText();
        author = authorTextField.getText();
        if(hasAnIllustratorCheckBox.isSelected() && illustratorTextField.getText().length() > 0) {
            illustrator = illustratorTextField.getText();
        }else{
            illustrator = "N/A";
        }
        description = descriptionTextField.getText();
        nonFiction = nonFictionRadioButton.isSelected();
        if(nonFiction && decimalTextField.getText().length() > 0){
            decimal = decimalTextField.getText();
        }
        else{
            decimal = "N/A";
        }
        isbn = ISBNTextField.getText();
        genre = genreTextField.getText();
        Book book = new Book(title, author, illustrator, isbn, decimal, description, genre, nonFiction);
        book.deserializeTags(tags);
        Database db = new Database();
        if(db.addBook(book)){
            success = true;
        }
        else{
            success = false;
        }
        return success;

    }

    public void setFormFields(Book book){
        this.titleTextField.setText(book.getTitle());
        this.authorTextField.setText(book.getAuthor());
        if(book.getIllustrator().equalsIgnoreCase("N/A")){
            this.hasAnIllustratorCheckBox.setSelected(false);
        }
        else{
            this.hasAnIllustratorCheckBox.setSelected(true);
            this.illustratorTextField.setEditable(true);
            this.illustratorTextField.setText(book.getIllustrator());
        }
        if(!book.getDecimal().equalsIgnoreCase("N/A")){
            this.nonFictionRadioButton.setSelected(true);
            this.decimalTextField.setEditable(true);
            this.decimalTextField.setText(book.getDecimal());
        }
        else{
            this.fictionRadioButton.setSelected(true);
        }
        this.descriptionTextField.setText(book.getDescription());
        this.ISBNTextField.setText(book.getIsbn());
        this.genreTextField.setText(book.getGenre());
        this.tagsTextField.setText(book.serializeTags());

    }
}
