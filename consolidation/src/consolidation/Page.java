package consolidation;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import ru.diman.database.QueryRestriction;
import ru.diman.database.QueryRestrictions;
import ru.diman.description.Template;
import ru.diman.description.Templates;
import ru.diman.description.memento.DataReader;
import ru.diman.description.memento.XMLTemplatesReader;
import ru.diman.description.templates.TemplatesImpl;
import ru.diman.system.PathManager;

public class Page extends JApplet {

    /**
     * ����� ��������� �������
     */
    private List<SinglePage> pages;


    /**
     * ��� ���������
     */
    private int recordIndex;

    /**
     * �����������
     * @param recordIndex
     * @param fileName
     */
    public Page(int recordIndex, String fileName) {

        this.pages = new ArrayList<SinglePage>();
        this.recordIndex = recordIndex;
        String template = PathManager.getTemplatePath() + fileName;
        LoadTemplates(recordIndex, template);
    }

    void LoadTemplates(int recordIndex, String fileName) {
        Templates t = new TemplatesImpl();

        DataReader<Templates> reader = new XMLTemplatesReader(fileName);
        reader.loadData(t);

        Container cp = getContentPane();

        final JTabbedPane tabbedPane = new JTabbedPane();


        JButton saveButton = new JButton(
                new AbstractAction("���������") {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        // ������� ��� �������� � ��������� ������
                        for (int i = 0; i < pages.size(); i++){
                            SinglePage page = pages.get(i);
                            page.saveData();
                        }
                    }
                });

        JButton clearReportButton = new JButton(
                new AbstractAction("������� ������ ������") {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        deleteReportData();
                    }
                });


        final JButton addRowButton = new JButton(
                new AbstractAction("��������") {

                    public void actionPerformed(ActionEvent e) {

                        final SinglePage page = (SinglePage) tabbedPane.getSelectedComponent();

                        int tableRowIndex = page.getTable().getSelectedRow();
                        int modelRowIndex = page.getTable().convertRowIndexToModel(tableRowIndex);

                        page.getModel().addRow(modelRowIndex);

                        //JTableEx table = page.getTable();
                        //table.getModel().addRow();
                    }
                });
        addRowButton.setFocusable(false);


        for (int pageIndex = 0; pageIndex < t.getCount(); pageIndex++) {

            // �������� ��������
            Template template = t.getItem(pageIndex);

            // ������� �������� ��� �����������
            final SinglePage page = new SinglePage();
            pages.add(page);

            // ��������� ������ �������� �������
            //int recordIndex = 1478344;

            // ������� ����� �����������
            QueryRestrictions restrictions = new QueryRestrictions();
            restrictions.add(new QueryRestriction("�����������������������.���������������", recordIndex));
            restrictions.add(new QueryRestriction("�����������������������.����������������", pageIndex));

            page.loadTemplate(template, restrictions);

            // ��������� ��������
            tabbedPane.addTab(template.getPageName(), page);

            page.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {

                @Override
                public void valueChanged(ListSelectionEvent e) {

                    
                    //int row = Math.min(e.getLastIndex(), e.getFirstIndex());
                    //e.
                    int row = page.getTable().getSelectedRow();
                    if (row > -1) {
                        int realRow = page.getTable().convertRowIndexToModel(row);
                        addRowButton.setEnabled(page.getModel().canInsertRow(realRow));
                    }
                }
            });
        }

        tabbedPane.setSelectedIndex(0);
        JToolBar toolBar = new JToolBar();

        //JButton addRowButton = new JButton("��������");


        JButton delRowButton = new JButton("�������");

        toolBar.add(saveButton);
        //toolBar.add(clearReportButton);
        toolBar.add(addRowButton);
        toolBar.add(delRowButton);

        cp.add(BorderLayout.CENTER, tabbedPane);
        cp.add(BorderLayout.NORTH, toolBar);

        //cp.add(BorderLayout.SOUTH, buttonAdd);
        validate();

        //setSize(900, 480);
    }

    final void deleteReportData(){

        //QueryManager qm = new QueryManagerImpl();
        //qm.deleteReportData(recordIndex);
    }
}
