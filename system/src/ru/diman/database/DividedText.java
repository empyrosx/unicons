/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.diman.database;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class DividedText {

    List<String> values;

    public DividedText() {
        this.values = new ArrayList<String>();
    }

    public void add(String value) {
        values.add(value);
    }

    public String getValues(String divider) {
        String result = "";

        for (String value : values) {
            if (result == "") {
                result = value;
            } else {
                result = result + divider + value;
            }
        }

        return result;
    }
}
