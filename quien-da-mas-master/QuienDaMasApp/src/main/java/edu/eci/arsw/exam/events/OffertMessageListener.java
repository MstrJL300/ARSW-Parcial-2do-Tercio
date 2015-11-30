package edu.eci.arsw.exam.events;

import edu.eci.arsw.exam.IdentityGenerator;
import edu.eci.arsw.exam.Product;
import java.util.Random;
import java.io.*;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class OffertMessageListener implements MessageListener {

    Random rand = new Random(System.currentTimeMillis());
    
    //AGR
    private OffertMessageProducer mproducer=null;
    private boolean env = true;
    
    //AGR
    public void setMessageProducer(OffertMessageProducer mproducer) {
        this.mproducer = mproducer;
    }
    
    public OffertMessageListener() {
        super();
        System.out.println("\n Comprador #"+IdentityGenerator.actualIdentity+"\n esperando eventos...\n ");
    }

    @Override
    public void onMessage(Message message) {
        try {
            if(env){
                
                Product receivedProduct = new Product(message.getBody());
                System.out.println("\n Comprador #"+IdentityGenerator.actualIdentity+"\n recibió: "+receivedProduct.getCode());

                int montoOferta = Math.abs(rand.nextInt(99999999));

                while(montoOferta < receivedProduct.getStartPrice())
                    montoOferta = Math.abs(rand.nextInt(99999999));

                System.out.println("\n Oferta: "+montoOferta);

                //realizar oferta con el monto aleatorio generado

                //AGR
                receivedProduct.setDescription(IdentityGenerator.actualIdentity); //Description, tiene el Identity.
                receivedProduct.setStartPrice(montoOferta); //startPrice, tiene el monto.
                
                synchronized(mproducer){                    
                    mproducer.sendMessages(receivedProduct.toBytes());
                }
            }
            else{
                
                Product receivedProduct = new Product(message.getBody());
                if(IdentityGenerator.actualIdentity == null ? receivedProduct.getDescription() == null : IdentityGenerator.actualIdentity.equals(receivedProduct.getDescription()))
                    System.out.println("\n El comprador #"+receivedProduct.getDescription()+" compro el producto "+receivedProduct.getCode()+".");
            }
            env = !env;
            
        } catch (Exception e) {
            throw new RuntimeException("\n An exception occured while trying to get a AMQP object:" + e.getMessage(), e);
        }

    }

}
