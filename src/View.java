// Parts of code are from the class examples and/or starter code

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

class View extends JPanel implements ClipboardOwner {

    JTextArea text;
    private Model model;

    class PopUpDemo extends MarkingMenu {

        public PopUpDemo(){
            // file
            var fileMenu = new JMenu("File");
            var newMenuItem = new JMenuItem("New");
            newMenuItem.setToolTipText("Create a new document");
            newMenuItem.addActionListener(e -> {
                System.out.println("File-New");
                doNew();
            });
            fileMenu.add(newMenuItem);

            var exitMenuItem = new JMenuItem("Exit");
            exitMenuItem.setToolTipText("Exit the application");
            exitMenuItem.addActionListener(e -> {
                System.out.println("File-Exit");
                System.exit(1);
            });
            fileMenu.add(exitMenuItem);

            // edit
            var editMenu = new JMenu("Edit");
            var cutMenuItem = new JMenuItem("Cut");
            cutMenuItem.setToolTipText("Cut selection to the clipboard");
            cutMenuItem.addActionListener(e -> {
                System.out.println("Edit-Cut");
                doCopy();
                text.replaceSelection("");
                model.setText(text.getText());
            });
            editMenu.add(cutMenuItem);

            var copyMenuItem = new JMenuItem("Copy");
            copyMenuItem.setToolTipText("Copy selection to the clipboard");
            copyMenuItem.addActionListener(e -> {
                System.out.println("Edit-Copy");
                doCopy();
            });
            editMenu.add(copyMenuItem);

            var pasteMenuItem = new JMenuItem("Paste");
            pasteMenuItem.setToolTipText("Paste from the clipboard");
            pasteMenuItem.addActionListener(e -> {
                System.out.println("Edit-Paste");
                doPaste();
                model.setText(text.getText());
            });
            editMenu.add(pasteMenuItem);

            // format
            var formatMenu = new JMenu("Format");
            var foregroundMenuItem = new JMenuItem("Foreground");
            foregroundMenuItem.setToolTipText("Set colour");
            foregroundMenuItem.addActionListener(e -> {
                System.out.println("Colour-Foreground");
                Color color = JColorChooser.showDialog(this, "Select a foreground colour", text.getForeground());
                model.setText(text.getText());
                model.setFore(color);
            });
            formatMenu.add(foregroundMenuItem);

            var backgroundMenuItem = new JMenuItem("Background");
            backgroundMenuItem.setToolTipText("Set colour");
            backgroundMenuItem.addActionListener(e -> {
                System.out.println("Colour-Background");
                Color color = JColorChooser.showDialog(this, "Select a background colour", text.getBackground());
                model.setText(text.getText());
                model.setBack(color);
            });
            formatMenu.add(backgroundMenuItem);

            // add to popup menu
            add(fileMenu);
            add(editMenu);
            add(formatMenu);
        }

    }

    private void doNew() {
        model.setText(model.getStart());
        model.setFore(Color.BLACK);
        model.setBack(Color.WHITE);
        // text.setForeground(Color.BLACK);
        // text.setBackground(Color.WHITE);
        // text.setText(model.STARTING_STRING);
    }

    private void doCopy() {

        // Get the system clipboard
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();

        // Create a transferable object encapsulating all the info for the copy
        Transferable transferObject = new Transferable() {

            private String textSelected = text.getSelectedText();

            // Returns the copy data
            public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
                System.out.println("  Transferable.getTransferData as " + flavor);
                if (flavor.equals(DataFlavor.stringFlavor)) {
                    return textSelected;
                }
                throw new UnsupportedFlavorException(flavor);
            }

            // Returns the set of data formats we can provide
            public DataFlavor[] getTransferDataFlavors() {
                System.out.println("  Transferable.getTransferDataFlavors");
                return new DataFlavor[] { DataFlavor.stringFlavor };
            }

            // Indicates whether we can provide data in the specified format
            public boolean isDataFlavorSupported(DataFlavor flavor) {
                System.out.println("  Transferable.isDataFlavorSupported: " + flavor);
                return flavor.equals(DataFlavor.stringFlavor);
            }
        };

        // Now set the contents of the clipboard to our transferable object
        // NOTE: The second argument "this" tells the system that this
        //       object would like to be the owner of the clipboard.
        //       As such, this object must implement the ClipboardOwner interface
        System.out.println("COPY: set system clipboard to Transferable");
        cb.setContents(transferObject, this);
    }

    private void doPaste() {

        // Grab system clipboard
        Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        System.out.println(String.format("PASTE: %d available flavours ... ",
                systemClipboard.getAvailableDataFlavors().length));
        for (DataFlavor f: systemClipboard.getAvailableDataFlavors()) {
            System.out.println("  " + f.getHumanPresentableName() + "  " + f.toString());
        }

        // Check if we can get the data as a string
        if (systemClipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
            System.out.println("DataFlavor.stringFlavor available");
            try {
                // Grab the data, set our text area to the data
                String theText = (String)systemClipboard.getData(DataFlavor.stringFlavor);
                text.replaceSelection(theText);
            } catch (UnsupportedFlavorException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("DataFlavor.stringFlavor NOT available");
        }
    }

    class PopClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent e){
            if (e.isPopupTrigger())
                doPop(e);
        }

        public void mouseReleased(MouseEvent e){
            if (e.isPopupTrigger())
                doPop(e);
        }

        private void doPop(MouseEvent e){
            PopUpDemo menu = new PopUpDemo();
            menu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    // required for clipboard management
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        System.out.println("Lost clipboard ownership");
    }

    public View(Model model) {
        this.model = model;
        model.setText(model.getStart());

        text = new JTextArea(model.getStart());
        text.setMargin(new Insets(5, 5, 5, 5));
        text.setFont(new Font("Serif", Font.PLAIN, 16));
        text.setLineWrap(true);
        text.setEditable(true);
        text.setRequestFocusEnabled(true);
        text.addMouseListener(new PopClickListener());

        this.setLayout(new GridBagLayout());
        this.add(text, new GridBagConstraints());
    }

    public void updateView() {
        System.out.println("View: updateView");
        text.setText(model.getText());
        text.setForeground(model.getFore());
        text.setBackground(model.getBack());
    }
}