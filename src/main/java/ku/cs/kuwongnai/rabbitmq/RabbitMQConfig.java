package ku.cs.kuwongnai.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

@Configuration
public class RabbitMQConfig {
  @Bean
  public TopicExchange topic() {
    return new TopicExchange("events.user");
  }

  @Bean
  public Queue userCreatedQueue() {
    return new Queue("user.created.queue");
  }

  @Bean
  public Queue userUpdatedQueue() {
    return new Queue("user.updated.queue");
  }

  @Bean
  public Binding userCreatedBinding(Queue userCreatedQueue, TopicExchange topic) {
    return BindingBuilder.bind(userCreatedQueue).to(topic).with("user.created");
  }

  @Bean
  public Binding userUpdatedBinding(Queue userUpdatedQueue, TopicExchange topic) {
    return BindingBuilder.bind(userUpdatedQueue).to(topic).with("user.updated");
  }

  @Bean
  public MessageConverter converter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(converter());
    return rabbitTemplate;
  }

  /* Don't know if this code is needed or not because it worked without it */

  // @Bean
  // SimpleMessageListenerContainer userCreatedContainer(ConnectionFactory
  // connectionFactory,
  // MessageListenerAdapter userCreatedListenerAdapter) {
  // SimpleMessageListenerContainer container = new
  // SimpleMessageListenerContainer();
  // container.setConnectionFactory(connectionFactory);
  // container.setQueueNames(userCreatedQueue().getName());
  // container.setMessageListener(userCreatedListenerAdapter);
  // return container;
  // }

  // @Bean
  // SimpleMessageListenerContainer userUpdatedContainer(ConnectionFactory
  // connectionFactory,
  // MessageListenerAdapter userUpdatedListenerAdapter) {
  // SimpleMessageListenerContainer container = new
  // SimpleMessageListenerContainer();
  // container.setConnectionFactory(connectionFactory);
  // container.setQueueNames(userUpdatedQueue().getName());
  // container.setMessageListener(userUpdatedListenerAdapter);
  // return container;
  // }

  // @Bean
  // MessageListenerAdapter userCreatedListenerAdapter(UserReceiver receiver) {
  // return new MessageListenerAdapter(receiver, "handleUserCreatedMessage");
  // }

  // @Bean
  // MessageListenerAdapter userUpdatedListenerAdapter(UserReceiver receiver) {
  // return new MessageListenerAdapter(receiver, "handleUserUpdatedMessage");
  // }
}
