package com.nightbeer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class purchaseData implements Serializable {
    private List<Object[]> rows;

    public purchaseData() {
        this.rows = new ArrayList<>();
    }

    public void addRow(Object[] row) {
        rows.add(row);
    }

    public List<Object[]> getRows() {
        return rows;
    }
}
