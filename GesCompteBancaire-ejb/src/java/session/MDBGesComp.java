/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author luchi
 */
@JMSDestinationDefinition(name = "java:app/jms/loggingMessages", interfaceName = "javax.jms.Queue", resourceAdapter = "jmsra", destinationName = "loggingMessages")
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:app/jms/loggingMessages"),
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"), 
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class MDBGesComp implements MessageListener {
    
    public MDBGesComp() {
    }
    
    @Override
       public void onMessage(Message message) {  
        if (message instanceof TextMessage) {  
            TextMessage tm = (TextMessage) message;  
            try {  
                String text = tm.getText();  
                System.out.println("Received new message :" + text);  
            } catch (JMSException e) {  
            }  
        }  
    }
    
}
