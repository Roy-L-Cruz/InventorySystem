import INVENTORY.Connections;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFrame;

public class Login extends javax.swing.JFrame {

    public Login() {
        
        initComponents();
        setLocationRelativeTo(null);
        
        //Connection to sql
        Connections connect = new Connections();
        try {
            connect.connectToDatabase();
        } catch (SQLException ex) {
            Logger.getLogger(Connections.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        emailLogin = new javax.swing.JTextField();
        passwordLogin = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Lucida Sans", 0, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("OR");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 440, 360, 40));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/bg.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, -10, 630, 660));

        jLabel3.setFont(new java.awt.Font("Lucida Sans", 0, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Email");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 170, 380, 40));

        emailLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailLoginActionPerformed(evt);
            }
        });
        getContentPane().add(emailLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 220, 380, -1));
        getContentPane().add(passwordLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 290, 380, -1));

        jButton1.setBackground(new java.awt.Color(123, 164, 255));
        jButton1.setFont(new java.awt.Font("Lucida Sans", 0, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Log In");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 350, 380, 30));

        jSeparator1.setForeground(new java.awt.Color(153, 153, 153));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 460, 170, 50));

        jSeparator2.setForeground(new java.awt.Color(153, 153, 153));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 460, 170, 50));

        jLabel7.setFont(new java.awt.Font("Lucida Sans", 0, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Don't have an account? Register");
        jLabel7.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 510, 190, 20));

        jLabel8.setFont(new java.awt.Font("Lucida Sans", 0, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Password");
        jLabel8.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 240, 380, 40));

        jLabel4.setFont(new java.awt.Font("Lucida Sans", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("ORGANIZED");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 20, 110, 60));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/bg (1).png"))); // NOI18N
        jLabel1.setToolTipText("");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 10, 60, 80));

        jButton2.setBackground(new java.awt.Color(102, 102, 102));
        jButton2.setFont(new java.awt.Font("Lucida Sans", 0, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Exit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 400, 380, 30));

        jLabel9.setFont(new java.awt.Font("Lucida Sans", 1, 24)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("INVENTORY MANAGEMENT SYSTEM");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 540, 110));

        jLabel5.setFont(new java.awt.Font("Lucida Sans", 0, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("LOGIN");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 540, 40));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void emailLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailLoginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailLoginActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     
        Connections connect = new Connections();
        
        String email, pass, query;
        email = emailLogin.getText();
        pass = passwordLogin.getText();
        
        if ("".equals(emailLogin.getText())) {
            JOptionPane.showMessageDialog(new JFrame(), "EMAIL cannot be empty");
            return;
        }
        if ("".equals(passwordLogin.getText() )) {
            JOptionPane.showMessageDialog(new JFrame(), "PASSWORD cannot be empty");
            return;
        }
        else{
            query = "SELECT * FROM account_info WHERE acc_email = ? AND acc_password = ?";
            
            try (PreparedStatement pstmt = connect.getConnection().prepareStatement(query)) {
            pstmt.setString(1, email);
            pstmt.setString(2, pass);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    JOptionPane.showMessageDialog(new JFrame(), "Login Success");
                    if (emailLogin.getText().equals("admin")&&passwordLogin.getText().equals("admin")) {
                        Admin ad = new Admin();
                        ad.setVisible(true);
                        setVisible(false);
                    }else{
                        User use = new User();
                        use.setLabelText(email);
                        use.setVisible(true);
                        setVisible(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Invalid Credentials");
                }
            }
            } catch (SQLException e) {
                System.out.println("Query failed");
                e.printStackTrace();
            }
            
        }  
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        Register reg = new Register();
        reg.setVisible(true);
        setVisible(false);
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int exit = JOptionPane.showConfirmDialog(null, "Do you want to close Inventory System", "Select",JOptionPane.YES_NO_OPTION);
        if (exit == 0) {
            System.exit(0);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Register().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField emailLogin;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPasswordField passwordLogin;
    // End of variables declaration//GEN-END:variables
}
