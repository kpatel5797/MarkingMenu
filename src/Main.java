/*
    CS 349 Winter 2019 Assignment 3
    (c) 2019 Jeff Avery
 */

 // Parts of code may be taken from the class examples and/or starter code

import javax.swing.*;

public class Main extends JFrame {

    // main entry point
    public Main() {
        // setup window and test area
        setTitle("A3: Marking Menu");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Model model = new Model();
        View view = new View(model);
        model.setView(view);

        JScrollPane scroller = new JScrollPane(view.text);
        setContentPane(scroller);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            var ex = new Main();
            ex.setVisible(true);
        });
    }
}