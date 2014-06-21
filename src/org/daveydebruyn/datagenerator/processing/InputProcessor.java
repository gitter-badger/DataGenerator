package org.daveydebruyn.datagenerator.processing;

import java.util.ArrayList;
import java.util.List;

import org.daveydebruyn.datagenerator.generators.RandomGenerators;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class InputProcessor {
    int index = 0;
    private List<Text> txtColumnNames = new ArrayList<Text>();
    private List<Text> formats = new ArrayList<Text>();
    private List<Combo> formatComboBoxes = new ArrayList<Combo>();
    private Table dataTable;
    private Display display;
    private TableItem items[];
    private Text txtRows;

    public InputProcessor(List<Text> txtColumnNames, List<Text> formats, List<Combo> formatComboBoxes, Table dataTable, Text txtRows,
            Display display) {
        super();
        this.txtColumnNames = txtColumnNames;
        this.formats = formats;
        this.formatComboBoxes = formatComboBoxes;
        this.dataTable = dataTable;
        this.txtRows = txtRows;
        this.display = display;
    }

    public void processFields() {
        while (dataTable.getColumnCount() > 0) {
            dataTable.getColumn(0).dispose();
        }
        dataTable.removeAll();
        processItems();
        for (Text format : formats) {

            switch (formatComboBoxes.get(getIndex()).getText()) {
            case "Name":
                processName();
                break;
            case "Number":
                processNumber();
                break;
            case "Decimal":
                processDecimal();
                break;
            case "Unique Identifier":
                System.out.println(format.getText());
                break;
            case "Fixed Value":
                System.out.println(format.getText());
                break;
            case "Custom Random":
                System.out.println(format.getText());
                break;
            case "Custom Format":
                System.out.println(format.getText());
                break;
            default:

                break;

            }
            setIndex(getIndex() + 1);
        }
    }

    public void processItems() {
        int rowCount = Integer.parseInt(txtRows.getText());
        items = new TableItem[rowCount];
        for (int j = 0; j < rowCount; j++) {
            items[j] = new TableItem(dataTable, SWT.NULL);
            if (j % 2 == 1) {
                items[j].setBackground(new Color(display, 209, 220, 225));
            }
        }
    }

    public void processName() {
        setColumn();
        for (int j = 0; j < Integer.parseInt(txtRows.getText()); j++) {
            items[j].setText(index, RandomGenerators.generateRandomString(true, 10));
        }
    }

    public void processNumber() {
        setColumn();
        for (int j = 0; j < Integer.parseInt(txtRows.getText()); j++) {
            items[j].setText(index, Integer.toString(RandomGenerators.generateRandomNumber(1, 10)));
        }
    }

    public void processDecimal() {
        setColumn();
        for (int j = 0; j < Integer.parseInt(txtRows.getText()); j++) {
            items[j].setText(index, String.format("%s", RandomGenerators.generateRandomDecimal(1.000D, 10.999D)));
        }
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setColumn() {
        final org.eclipse.swt.widgets.TableColumn tblColumn = new org.eclipse.swt.widgets.TableColumn(dataTable, SWT.NONE, getIndex());
        tblColumn.setWidth(100);
        tblColumn.setText(txtColumnNames.get(getIndex()).getText());
    }
}
