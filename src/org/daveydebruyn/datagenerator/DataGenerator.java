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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

public class DataGenerator {

    protected Shell shlDataGenerator;
    private Table dataTable;
    private Label lblWelcomeTo;
    private Button btnGenerateData;
    private Label lblProgress;
    private Label lblRows;
    private ProgressBar progressBar;
    private Text txtRows;
    private Display display = new Display();
    private List<Text> txtColumnNames = new ArrayList<Text>();
    private List<Text> formats = new ArrayList<Text>();
    private List<Combo> formatComboBoxes = new ArrayList<Combo>();
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
        Display display = Display.getDefault();
        createContents();
        shlDataGenerator.open();
        shlDataGenerator.layout();
        while (!shlDataGenerator.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    /**
     * Create contents of the window.
     */
    protected void createContents() {
        shlDataGenerator = new Shell();
        shlDataGenerator.setText("Data Generator");

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
                InputProcessor inputProc = new InputProcessor(txtColumnNames, formats, formatComboBoxes, dataTable, txtRows, display);
                inputProc.processFields();
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
        txtRows.setBounds(457, 175, 76, 21);

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
        txtColumnName.setBounds(539, 140 + (txtColumnNames.size() * 30), 180, 23);
        txtColumnNames.add(txtColumnName);
    }

    private void moveFields() {
        dataTable.setLocation(dataTable.getLocation().x, 200 + (formatComboBoxes.size() * 30));
        btnGenerateData.setLocation(btnGenerateData.getLocation().x, 170 + (formatComboBoxes.size() * 30));
        txtRows.setLocation(txtRows.getLocation().x, 175 + (formatComboBoxes.size() * 30));
        lblRows.setLocation(lblRows.getLocation().x, 175 + (formatComboBoxes.size() * 30));
        lblProgress.setLocation(lblProgress.getLocation().x, 533 + (formatComboBoxes.size() * 30));
        progressBar.setLocation(progressBar.getLocation().x, 533 + (formatComboBoxes.size() * 30));

        shlDataGenerator.setSize(745, 603 + (formatComboBoxes.size() * 30));
    }

}
