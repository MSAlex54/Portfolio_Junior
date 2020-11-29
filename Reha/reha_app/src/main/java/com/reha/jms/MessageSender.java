package com.reha.jms;

import org.springframework.stereotype.Component;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

@Component
public class MessageSender {

    private static final String JMS_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    private static final String DESTINATION = "jms/queue/reha";
    private static final String USERNAME = "mquser";
    private static final String PASSWORD = "mqpassword";
    private static final String INITIAL_CONTEXT_FACTORY = "org.wildfly.naming.client.WildFlyInitialContextFactory";
    private static final String PROVIDER_URL = "http-remoting://127.0.0.1:8081";

    public static void sendMessage() {

        // Set up the namingContext for the JNDI lookup
        final Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
        env.put(Context.PROVIDER_URL, PROVIDER_URL);
        env.put(Context.SECURITY_PRINCIPAL, USERNAME);
        env.put(Context.SECURITY_CREDENTIALS, PASSWORD);

        try {
            Context context = new InitialContext(env);
            QueueConnectionFactory connectionFactory = (QueueConnectionFactory) context.lookup(JMS_CONNECTION_FACTORY);
            Queue queue = (Queue) context.lookup(DESTINATION);
            QueueConnection connection = connectionFactory.createQueueConnection(USERNAME, PASSWORD);
            connection.start();
            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            QueueSender sender = session.createSender(queue);

            TextMessage message = session.createTextMessage();
            sender.send(message);
            sender.close();
            session.close();
            connection.close();
        } catch (NamingException | JMSException e) {
            e.printStackTrace();
        }
    }
}
