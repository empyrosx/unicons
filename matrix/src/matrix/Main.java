/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package matrix;

import javax.swing.JFrame;

/**
 *
 * @author Димитрий
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        MatrixView matrixView = new MatrixView();
        matrixView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        matrixView.setVisible(true);
    }



}
