package org.daveydebruyn.datagenerator;

import java.util.ArrayList;
import java.util.List;

import org.daveydebruyn.datagenerator.processing.InputProcessor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

public class DataGenerator {

    protected Shell shlDataGenerator;
    protected Table dataTable;
    private Label lblWelcomeTo;
    private Button btnGenerateData;
    private Label lblProgress;
    private Label lblRows;
    private ProgressBar progressBar;
    protected Display display;
    protected Text txtRows;
    protected List<Text> columnNames = new ArrayList<Text>();
    protected List<Text> formats = new ArrayList<Text>();
    protected List<Combo> formatComboBoxes = new ArrayList<Combo>();
    private List<Boolean> active = new ArrayList<Boolean>();
    String[] tooltipsArray = new String[] {
            "Insert the minimum length and the maximum length of the name you want to generate.",
            "Insert the minimum value and the maximum value of the number you want to generate, for example: <!number(1, 400)!> will generate a number from 1 to 400.",
            "Insert the minimum value, the maximum value of the number and the amount of digits after the comma you want to generate, for example: <!decim(1, 400, 2)!> will generate a number from 1.00 to 400.00.",
            "This format will generate a unique identifier. No parameters are required.",
            "This format will generate a fixed value, for example: <!!test!!> will output the string 'test' to all the rows you generate for this column, <!!<!number(1,400)!>!!> will generate a number from 1 to 400 once, then use this number for all the rows.",
            "Insert the values you wish to generate from. This format will select a random value from the values you provide, for example: <!random(1, 2, <!number(1,400)!>!> will select either 1, 2 or a random number from 1 to 400. Note: you can also use <!!<!number(1,400)!>!!> if you only want to generate this number once",
            "Create a custom generation format. You can use all the other generators here, for example: AAaaA_<!number(100,200)!><!!.xml!!> will output data like NHpoU_184.jpg" };
    String[] formatsArray = new String[] { "<!name(minimumLength, maximumLength)!>", "<!number(minimumValue, maximumValue)!>",
            "<!decim(minimumValue, maximumValue, numbersAfterComma)!>", "<!uuid!>", "<!!value!!>",
            "<!random('value1','value2', 'value3', ...)!>", "Custom Format" };

    private InputProcessor inputProc;

    /**
     * Launch the application.
     * 
     * @param args
     */
    public static void main(String[] args) {
        try {
            DataGenerator window = new DataGenerator();
            window.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Open the window.
     */
    public void open() {
        display = Display.getDefault();
        initiateClasses();
        createContents();
        shlDataGenerator.open();
        shlDataGenerator.layout();
        while (!shlDataGenerator.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    public void initiateClasses() {
        inputProc = new InputProcessor();
    }

    /**
     * Create contents of the window.
     */
    protected void createContents() {
        shlDataGenerator = new Shell();
        shlDataGenerator.setText("Data Generator");

        createMenu();

        lblWelcomeTo = new Label(shlDataGenerator, SWT.WRAP);
        lblWelcomeTo.setBounds(10, 10, 709, 125);
        lblWelcomeTo
                .setText("Welcome to the random data generator.\r\n\r\nSelect a format for the column you wish to create, like a number for a PK_ID, or create your own format.\r\nYou can combine the custom format with the predefined formats.\r\n\r\nFor example: a custom format like AAAaa_<!int(1,400)!><!!.jpg!!> would create output like NDIne_2.jpg or PEBjt_398.jpg.\r\n\r\nIn this example you can see that fixed values are defined by using <!!value!!>");

        dataTable = new Table(shlDataGenerator, SWT.BORDER | SWT.FULL_SELECTION);
        dataTable.setBounds(10, 200, 709, 325);
        dataTable.setHeaderVisible(true);
        dataTable.setLinesVisible(true);

        btnGenerateData = new Button(shlDataGenerator, SWT.NONE);
        btnGenerateData.setEnabled(false);
        btnGenerateData.setBounds(539, 170, 180, 25);
        btnGenerateData.setText("Generate Data");
        btnGenerateData.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent e) {
                disableInput();
                boolean generatingDone = inputProc.processFields();
                if (generatingDone) {
                    enableInput();
                }
            }
        });

        progressBar = new ProgressBar(shlDataGenerator, SWT.NONE);
        progressBar.setBounds(549, 533, 170, 17);

        lblProgress = new Label(shlDataGenerator, SWT.NONE);
        lblProgress.setBounds(478, 533, 55, 15);
        lblProgress.setText("Progress:");

        txtRows = new Text(shlDataGenerator, SWT.BORDER | SWT.WRAP | SWT.RIGHT);
        txtRows.setToolTipText("Specify the amount of rows you wish to generate.");
        txtRows.setText("0");
        txtRows.setBounds(457, 172, 76, 21);

        lblRows = new Label(shlDataGenerator, SWT.NONE);
        lblRows.setBounds(420, 175, 31, 15);
        lblRows.setText("Rows:");

        active.add(0, false);

        createNewInputLine();
    }

    private void setupInputFields(SelectionEvent e, Combo box) {
        int i = 0;
        boolean newline = false;
        if (active.size() == 1) {
            btnGenerateData.setEnabled(true);
        }
        for (Combo comboBox : formatComboBoxes) {
            if (!(Boolean) active.get(i)) {
                newline = true;
                if (comboBox == box) {
                    active.add(i, true);

                }
                if (comboBox != box) {
                    newline = false;
                }
            }
            if (comboBox == box) {
                formats.get(i).setEnabled(true);
                formats.get(i).setText(formatsArray[box.getSelectionIndex()]);
                formats.get(i).setToolTipText(tooltipsArray[box.getSelectionIndex()]);
            }
            i++;
        }
        if (newline) {
            createNewInputLine();
        }
    }

    private void createNewInputLine() {
        moveFields();

        final Combo formatComboBox = new Combo(shlDataGenerator, SWT.READ_ONLY);
        formatComboBox.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                setupInputFields(e, formatComboBox);
            }
        });
        formatComboBox.setToolTipText("Select a format from this dropdown or create your own format");
        formatComboBox.setItems(new String[] { "Name", "Number", "Decimal", "Unique Identifier", "Fixed Value", "Custom Random",
                "Custom Format" });
        formatComboBox.setBounds(10, 140 + (formatComboBoxes.size() * 30), 170, 23);
        formatComboBox.setText("Select your format");
        formatComboBox.getData();
        formatComboBoxes.add(formatComboBox);

        final Text format = new Text(shlDataGenerator, SWT.BORDER | SWT.RIGHT);
        format.setEnabled(false);
        format.setBounds(188, 140 + (formats.size() * 30), 345, 23);
        formats.add(format);

        final Text txtColumnName = new Text(shlDataGenerator, SWT.BORDER | SWT.RIGHT);
        txtColumnName.setToolTipText("Enter the name of the column you want to create data for.");
        txtColumnName.setText("Column Name");
        txtColumnName.setBounds(539, 140 + (columnNames.size() * 30), 180, 23);
        columnNames.add(txtColumnName);

    }

    private void moveFields() {
        int factor = (formatComboBoxes.size() * 30);
        dataTable.setLocation(dataTable.getLocation().x, 200 + factor);
        btnGenerateData.setLocation(btnGenerateData.getLocation().x, 170 + factor);
        lblRows.setLocation(lblRows.getLocation().x, 175 + factor);
        lblProgress.setLocation(lblProgress.getLocation().x, 533 + factor);
        progressBar.setLocation(progressBar.getLocation().x, 533 + factor);
        txtRows.setLocation(txtRows.getLocation().x, 172 + factor);

        shlDataGenerator.setSize(745, 603 + (formatComboBoxes.size() * 30));
    }

    private void disableInput() {
        btnGenerateData.setEnabled(false);
        txtRows.setEnabled(false);
        for (int i = 0; i < formatComboBoxes.size(); i++) {
            formatComboBoxes.get(i).setEnabled(false);
            formats.get(i).setEnabled(false);
            columnNames.get(i).setEnabled(false);
        }
    }

    private void enableInput() {
        btnGenerateData.setEnabled(true);
        txtRows.setEnabled(true);
        for (int i = 0; i < formatComboBoxes.size(); i++) {
            formatComboBoxes.get(i).setEnabled(true);
            formats.get(i).setEnabled(true);
            columnNames.get(i).setEnabled(true);
        }
    }

    private void createMenu() {
        Menu menu = new Menu(shlDataGenerator, SWT.BAR);
        shlDataGenerator.setMenuBar(menu);

        // Create File Menu
        MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
        mntmFile.setText("File");

        Menu mnFile = new Menu(mntmFile);
        mntmFile.setMenu(mnFile);

        MenuItem mntmNewGenerator = new MenuItem(mnFile, SWT.NONE);
        mntmNewGenerator.setText("New Generator");

        MenuItem mntmNewItem_1 = new MenuItem(mnFile, SWT.NONE);
        mntmNewItem_1.setText("Open Generator");

        MenuItem mntmSaveGenerator = new MenuItem(mnFile, SWT.NONE);
        mntmSaveGenerator.setText("Save Generator");

        new MenuItem(mnFile, SWT.SEPARATOR);

        MenuItem mntmRecentFiles = new MenuItem(mnFile, SWT.CASCADE);
        mntmRecentFiles.setText("Recent Files");

        Menu mnRecentFiles = new Menu(mntmRecentFiles);
        mntmRecentFiles.setMenu(mnRecentFiles);

        new MenuItem(mnFile, SWT.SEPARATOR);

        MenuItem mntmQuit = new MenuItem(mnFile, SWT.NONE);
        mntmQuit.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                System.exit(0);
            }
        });
        mntmQuit.setText("Quit");

        // Create Generator Menu
        MenuItem mntmNewSubmenu_1 = new MenuItem(menu, SWT.CASCADE);
        mntmNewSubmenu_1.setText("Generator");

        Menu mnGenerator = new Menu(mntmNewSubmenu_1);
        mntmNewSubmenu_1.setMenu(mnGenerator);

        MenuItem mntmDefaultTemplate = new MenuItem(mnGenerator, SWT.NONE);
        mntmDefaultTemplate.setText("Create Default Generator Template");

        new MenuItem(mnGenerator, SWT.SEPARATOR);

        MenuItem mntmRenameCurrent = new MenuItem(mnGenerator, SWT.NONE);
        mntmRenameCurrent.setText("Rename Current Generator");

        MenuItem mntmRenameAll = new MenuItem(mnGenerator, SWT.NONE);
        mntmRenameAll.setText("Rename All Generators");

        new MenuItem(mnGenerator, SWT.SEPARATOR);

        MenuItem mntmGenerateCurrent = new MenuItem(mnGenerator, SWT.NONE);
        mntmGenerateCurrent.setText("Generate Data For Current Screen");

        MenuItem mntmGenerateAll = new MenuItem(mnGenerator, SWT.NONE);
        mntmGenerateAll.setText("Generate Data For All Screens");

        // Create Help Menu
        MenuItem mntmHelp = new MenuItem(menu, SWT.CASCADE);
        mntmHelp.setText("Help");

        Menu mnHelp = new Menu(mntmHelp);
        mntmHelp.setMenu(mnHelp);

        MenuItem mntmCheatSheet = new MenuItem(mnHelp, SWT.NONE);
        mntmCheatSheet.setText("Cheat Sheet");

        MenuItem mntmTipsTricks = new MenuItem(mnHelp, SWT.NONE);
        mntmTipsTricks.setText("Tips and Tricks");

        new MenuItem(mnHelp, SWT.SEPARATOR);

        MenuItem mntmSubmitGenerator = new MenuItem(mnHelp, SWT.NONE);
        mntmSubmitGenerator.setText("Submit Your Own Generator");

        new MenuItem(mnHelp, SWT.SEPARATOR);

        MenuItem mntmReportBug = new MenuItem(mnHelp, SWT.NONE);
        mntmReportBug.setText("Report Bug");

        new MenuItem(mnHelp, SWT.SEPARATOR);

        MenuItem mntmAbout = new MenuItem(mnHelp, SWT.NONE);
        mntmAbout.setText("About Data Generator");
    }
}
