package org.daveydebruyn.datagenerator.processing;

import org.daveydebruyn.datagenerator.DataGenerator;
import org.daveydebruyn.datagenerator.generators.RandomGenerators;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class InputProcessor extends DataGenerator {
    int index = 0;
    private TableItem items[];

    public boolean processFields() {
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
                processUUID();
                break;
            case "Fixed Value":
                processFixedValue();
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
        return true;
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

    public void processUUID() {
        setColumn();
        for (int j = 0; j < Integer.parseInt(txtRows.getText()); j++) {
            items[j].setText(index, RandomGenerators.generateRandomUUID().toString());
        }
    }

    public void processFixedValue() {
        setColumn();
        for (int j = 0; j < Integer.parseInt(txtRows.getText()); j++) {
            items[j].setText(index, formats.get(index).getText());
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
        tblColumn.setText(columnNames.get(getIndex()).getText());
    }

}
