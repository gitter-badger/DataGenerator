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
    int index;
    private List<Text> txtColumnNames = new ArrayList<Text>();
    private List<Text> formats = new ArrayList<Text>();
    private List<Combo> formatComboBoxes = new ArrayList<Combo>();
    private List<Boolean> active = new ArrayList<Boolean>();
    private Table dataTable;
    private Display d = new Display();
    private Text txtRows;

    public InputProcessor(List<Text> txtColumnNames, List<Text> formats, List<Combo> formatComboBoxes, List<Boolean> active,
            Table dataTable, Text txtRows) {
        super();
        this.txtColumnNames = txtColumnNames;
        this.formats = formats;
        this.formatComboBoxes = formatComboBoxes;
        this.active = active;
        this.dataTable = dataTable;
        this.txtRows = txtRows;
    }

    public void numberProcessor() {
        for (int j = 0; j < Integer.parseInt(txtRows.getText()); j++) {
            TableItem item = new TableItem(dataTable, SWT.NULL);
            if (j % 2 == 1) {
                item.setBackground(new Color(d, 209, 220, 225));
            }
            item.setText(Integer.toString(RandomGenerators.generateRandomNumber(1, 10)));
        }
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setColumn(int i) {
        final org.eclipse.swt.widgets.TableColumn tblColumn = new org.eclipse.swt.widgets.TableColumn(dataTable, SWT.NONE);
        tblColumn.setWidth(100);
        tblColumn.setText(txtColumnNames.get(index).getText());
    }
}
