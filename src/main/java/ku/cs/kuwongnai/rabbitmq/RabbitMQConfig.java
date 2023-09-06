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

  private final String USER_CREATED_QUEUE = "user.created.queue";
  private final String USER_UPDATED_QUEUE = "user.updated.queue";
  private final String TOPIC_EXCHANGE_NAME = "events.user";
  private final String USER_CREATED_ROUTING_KEY = "user.created";
  private final String USER_UPDATED_ROUTING_KEY = "user.updated";

  @Bean
  public TopicExchange topic() {
    return new TopicExchange(TOPIC_EXCHANGE_NAME);
  }

  @Bean
  public Queue userCreatedQueue() {
    return new Queue(USER_CREATED_QUEUE);
  }

  @Bean
  public Queue userUpdatedQueue() {
    return new Queue(USER_UPDATED_QUEUE);
  }

  @Bean
  public Binding userCreatedBinding(Queue userCreatedQueue, TopicExchange topic) {
    return BindingBuilder.bind(userCreatedQueue).to(topic).with(USER_CREATED_ROUTING_KEY);
  }

  @Bean
  public Binding userUpdatedBinding(Queue userUpdatedQueue, TopicExchange topic) {
    return BindingBuilder.bind(userUpdatedQueue).to(topic).with(USER_UPDATED_ROUTING_KEY);
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

}
