// Parts of code are from the class examples and/or starter code

import java.awt.*;
import java.util.ArrayList;

public class Model {
    
    private String STARTING_STRING = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
            "empor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation " +
            "ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
            "voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non " +
            "proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n\n" +
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
            "empor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation " +
            "ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
            "voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non " +
            "proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n\n" +
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
            "empor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation " +
            "ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
            "voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non " +
            "proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n\n" +
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
            "empor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation " +
            "ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
            "voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non " +
            "proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n\n";

    private Color foregroundColor;
    private Color backgroundColor;
    private String text;
    private ArrayList<View> views = new ArrayList<View>();

    public String getStart() {
        return STARTING_STRING;
    }

    public void setView(View view) {
        views.add(view);
        view.updateView();
    }

    public String getText() {
        return text;
    }

    public Color getFore() {
        return foregroundColor;
    }

    public Color getBack() {
        return backgroundColor;
    }

    public void setText(String newText) {
        text = newText;
        notifyObserver();
    }

    public void setFore(Color color) {
        foregroundColor = color;
        notifyObserver();
    }

    public void setBack(Color color) {
        backgroundColor = color;
        notifyObserver();
    }

    private void notifyObserver() {
        for (View view : this.views) {
            System.out.println("Model: notify View");
            view.updateView();
        }
    }
}