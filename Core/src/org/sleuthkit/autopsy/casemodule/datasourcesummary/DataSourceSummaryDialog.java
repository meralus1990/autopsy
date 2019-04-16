/*
 * Autopsy Forensic Browser
 *
 * Copyright 2019 Basis Technology Corp.
 * Contact: carrier <at> sleuthkit <dot> org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sleuthkit.autopsy.casemodule.datasourcesummary;

import java.awt.Frame;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import org.openide.util.NbBundle.Messages;
import org.sleuthkit.autopsy.casemodule.IngestJobInfoPanel;
import org.sleuthkit.datamodel.DataSource;

/**
 * Dialog for displaying the Data Sources Summary information
 */
final class DataSourceSummaryDialog extends javax.swing.JDialog implements Observer {

    private static final long serialVersionUID = 1L;
    private final DataSourceSummaryCountsPanel countsPanel;
    private final DataSourceSummaryDetailsPanel detailsPanel;
    private final DataSourceBrowser dataSourcesPanel;
    private final IngestJobInfoPanel ingestHistoryPanel;
    private static final Logger logger = Logger.getLogger(DataSourceSummaryDialog.class.getName());

    /**
     * Creates new form DataSourceSummaryDialog for displaying a summary of the
     * data sources for the fcurrent case and the contents found for each
     * datasource.
     */
    @Messages({
        "DataSourceSummaryDialog.window.title=Data Sources Summary",
        "DataSourceSummaryDialog.countsTab.title=Counts",
        "DataSourceSummaryDialog.detailsTab.title=Details",
        "DataSourceSummaryDialog.ingestHistoryTab.title=Ingest History"
    })
    DataSourceSummaryDialog(Frame owner) {
        super(owner, Bundle.DataSourceSummaryDialog_window_title(), true);
        Map<Long, String> usageMap = DataSourceInfoUtilities.getDataSourceTypes();
        Map<Long, Long> fileCountsMap = DataSourceInfoUtilities.getCountsOfFiles();
        countsPanel = new DataSourceSummaryCountsPanel(fileCountsMap);
        detailsPanel = new DataSourceSummaryDetailsPanel(usageMap);
        dataSourcesPanel = new DataSourceBrowser(usageMap, fileCountsMap);
        ingestHistoryPanel = new IngestJobInfoPanel();
        initComponents();
        dataSourceSummarySplitPane.setLeftComponent(dataSourcesPanel);
        dataSourceTabbedPane.addTab(Bundle.DataSourceSummaryDialog_detailsTab_title(), detailsPanel);
        dataSourceTabbedPane.addTab(Bundle.DataSourceSummaryDialog_countsTab_title(), countsPanel);
        dataSourceTabbedPane.addTab(Bundle.DataSourceSummaryDialog_ingestHistoryTab_title(), ingestHistoryPanel);
        dataSourcesPanel.addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                DataSource selectedDataSource = dataSourcesPanel.getSelectedDataSource();
                countsPanel.updateCountsTableData(selectedDataSource);
                detailsPanel.updateDetailsPanelData(selectedDataSource);
                ingestHistoryPanel.setDataSource(selectedDataSource);
                this.repaint();
            }
        });
        this.pack();
    }

    /**
     * Make this dialog an observer of the DataSourcesPanel.
     */
    void enableObserver() {
        dataSourcesPanel.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        this.dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        closeButton = new javax.swing.JButton();
        dataSourceSummarySplitPane = new javax.swing.JSplitPane();
        dataSourceTabbedPane = new javax.swing.JTabbedPane();

        org.openide.awt.Mnemonics.setLocalizedText(closeButton, org.openide.util.NbBundle.getMessage(DataSourceSummaryDialog.class, "DataSourceSummaryDialog.closeButton.text")); // NOI18N
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        dataSourceSummarySplitPane.setDividerLocation(130);
        dataSourceSummarySplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        dataSourceSummarySplitPane.setRightComponent(dataSourceTabbedPane);

        dataSourceSummarySplitPane.setLeftComponent(dataSourcesPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dataSourceSummarySplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(closeButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dataSourceSummarySplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closeButton)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    /**
     * Select the data source with the specicied data source id. If no data
     * source matches the dataSourceID it will select the first datasource.
     *
     * @param dataSourceID the ID of the datasource to select, null will cause
     *                     the first datasource to be selected
     */
    void selectDataSource(Long dataSourceId) {
        dataSourcesPanel.selectDataSource(dataSourceId);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JSplitPane dataSourceSummarySplitPane;
    private javax.swing.JTabbedPane dataSourceTabbedPane;
    // End of variables declaration//GEN-END:variables
}
