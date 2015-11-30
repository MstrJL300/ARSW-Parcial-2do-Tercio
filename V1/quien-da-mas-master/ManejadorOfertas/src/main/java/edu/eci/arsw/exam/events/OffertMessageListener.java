package edu.eci.arsw.exam.events;

//import edu.eci.arsw.exam.IdentityGenerator;
import edu.eci.arsw.exam.Product;
import java.util.Random;
import java.io.*;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class OffertMessageListener implements MessageListener {

    Random rand = new Random(System.currentTimeMillis());

    public OffertMessageListener() {
        super();
        //System.out.println("\n\n Comprador #"+IdentityGenerator.actualIdentity+" esperando eventos...\n");
    }

    @Override
    public void onMessage(Message message) {
        try {
            Product receivedProduct = new Product(message.getBody());
            System.out.println(" recibió: "+receivedProduct.getCode());
            
            //int montoOferta = Math.abs(rand.nextInt(99999999));
            //while(montoOferta < receivedProduct.setStartPrice())
              //  montoOferta = Math.abs(rand.nextInt(99999999));
            
            //System.out.println("\n Ofere"+montoOferta+"\n");
            
            //receivedProduct.setStartPrice(montoOferta);

            //realizar oferta con el monto aleatorio generado
            
        } catch (Exception e) {
            throw new RuntimeException("An exception occured while trying to get a AMQP object:" + e.getMessage(), e);
        }

    }

}
