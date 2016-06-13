package skypetest;

import com.skype.Call;
import com.skype.CallAdapter;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
 
import com.skype.ChatMessage;
import com.skype.ChatMessageAdapter;
import com.skype.NotAttachedException;
import com.skype.Profile;
import com.skype.Skype;
import com.skype.SkypeException;
import com.skype.User;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class NewJFrame extends javax.swing.JFrame {

    public NewJFrame() throws SkypeException {
        initComponents();
        CallHandler cl=new CallHandler();
        cl.attachCallHandler();
        //setSize(400,200);
        //callHandler();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(400, 200));

        buttonGroup1.add(jToggleButton1);
        jToggleButton1.setText("Aktywny");
        jToggleButton1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton1ItemStateChanged(evt);
            }
        });
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jToggleButton2);
        jToggleButton2.setText("Nieaktywny");
        jToggleButton2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton2ItemStateChanged(evt);
            }
        });

        jTextField1.setText("jTextField1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jToggleButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(156, 156, 156)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(120, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(68, Short.MAX_VALUE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButton1)
                    .addComponent(jToggleButton2))
                .addGap(71, 71, 71))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton1ItemStateChanged

       if(evt.getStateChange()==java.awt.event.ItemEvent.SELECTED){
        System.out.println("button 1 is selected");
        
           try {
               ServiceFactory.getInstance().getConsultantService().setStatus(jTextField1.getText(), Status.ONLINE);
           } catch (MalformedURLException ex) {
               Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
           }
      
       } else if(evt.getStateChange()==java.awt.event.ItemEvent.DESELECTED){
        System.out.println("button 1 is not selected");
      }
    }//GEN-LAST:event_jToggleButton1ItemStateChanged
//public void callHandler() throws SkypeException 
//{
 //   attachCallHandler();
// }
    
    private void jToggleButton2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton2ItemStateChanged
        
        if(evt.getStateChange()==java.awt.event.ItemEvent.SELECTED){
        System.out.println("button 2 is selected");
            try {
                ServiceFactory.getInstance().getConsultantService().setStatus(jTextField1.getText(), Status.OFFLINE);
            } catch (MalformedURLException ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
      } else if(evt.getStateChange()==java.awt.event.ItemEvent.DESELECTED){
        System.out.println("button 2 is not selected");
        //new inCall().setVisible(true);
      }// TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton2ItemStateChanged

  
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
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
       
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new NewJFrame().setVisible(true);
                } catch (SkypeException ex) {
                    Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
              
               
            }
        });
        System.out.println("Hello world");
        System.setSecurityManager(new RMISecurityManager());
        try 
        {
            HelloInterface hello = (HelloInterface) Naming.lookup("//localhost/Hello");
            System.out.println(hello.say());
        }catch (Exception e)
        {
            System.out.println("HelloClient exception: " + e);
        }
         
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    // End of variables declaration//GEN-END:variables

    /*private void attachCallHandler() throws SkypeException 
    {
       
       Skype.addCallListener(new CallAdapter() {
                    @Override
	            public void callReceived(Call receivedCall) throws SkypeException {
	                
	                System.out.println(receivedCall.getPartnerDisplayName() + " CALLIN");
	                	             
	                try {
	                    Thread.sleep(10000); // to prevent finishing this call
	                } catch (InterruptedException e) {
	                }
	            }
	        });
    }*/
}
