/**
* ===========================================
* Java Pdf Extraction Decoding Access Library
* ===========================================
*
* Project Info:  http://www.jpedal.org
* (C) Copyright 1997-2008, IDRsolutions and Contributors.
*
* 	This file is part of JPedal
*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA


*
* ---------------
* CertificateHolder.java
* ---------------
*/

package it.eng.hybrid.module.jpedal.acroforms;

import javax.swing.JDialog;
import java.math.BigInteger;

public class CertificateHolder extends javax.swing.JPanel {


    private Details detailsTab;
    private General generalTab;
    private JDialog frame;

    public void setValues(String name, int version, String hashAlgorithm, String subjectFields, String issuerFields,
                          BigInteger serialNumber, String notBefore, String notAfter, String publicKeyDescription, String publicKey,
                          String x509Data, String sha1Digest, String md5Digest) {
        // TODO Auto-generated method stub
        generalTab = new General();
        detailsTab = new Details();

        generalTab.setValues(name, notBefore, notAfter);
        detailsTab.setValues(version, hashAlgorithm, subjectFields, issuerFields,
                serialNumber, notBefore, notAfter, publicKeyDescription, publicKey, x509Data, sha1Digest, md5Digest);

        jTabbedPane1.addTab("General", generalTab);
        jTabbedPane1.addTab("Details", detailsTab);
    }


    /**
     * Creates new form CertificateHolder
     *
     * @param dialog
     */
    public CertificateHolder(JDialog dialog) {
        initComponents();
        this.frame = dialog;
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jButton1 = new javax.swing.JButton();

        setLayout(null);
        add(jTabbedPane1);
        jTabbedPane1.setBounds(10, 10, 420, 360);

        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                close(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(350, 390, 73, 23);
    }// </editor-fold>//GEN-END:initComponents

    private void close(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_close
        frame.setVisible(false);

        // TODO add your handling code here:
    }//GEN-LAST:event_close


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables


}
