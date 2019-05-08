# MarkingMenu

This program implements a MarkingMenu class which is used as a drop-in replacement for the JPopupMenu in View's PopUpDemo class.

When a two-finger click is used on a Mac (probably right click on Windows), a new Marking Menu shows up on the window. This  circular marking menu consists of different menus separated into sectors and these sectors can contain submenus. When a sector is clicked, and a submenu exists, a new circular marking menu shows up on top of the original one. This one can consists multiple sectors as well, and once one of these sectors is clicked, a specified action is performed.

This MarkingMenu class implementation only supports 2 levels and works with clicking the mouse (not dragging).

The program uses MVC architechture.

To run the app from command line, please run 'gradle build', 'gradle run', and 'gradle clean' command
