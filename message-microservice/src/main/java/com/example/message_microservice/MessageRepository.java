package com.example.message_microservice;

import io.vertx.core.Future;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.Tuple;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MessageRepository {
  private static final Logger LOGGER = Logger.getLogger(MessageRepository.class.getName());

  private static Function<Row, Message> MAPPER = (row) ->
    Message.of(
      row.getInteger("id"),
      row.getString("content"),
      Timestamp.valueOf(row.getLocalDateTime("date")),
      row.getInteger("receiverId"),
      row.getInteger("senderId")
    );


  private final MySQLPool client;

  private MessageRepository(MySQLPool _client) {
    this.client = _client;
  }

  //factory method
  public static MessageRepository create(MySQLPool client) {
    return new MessageRepository(client);
  }

  public Future<List<Message>> findAll() {
    return client.query("SELECT * FROM messages ORDER BY id ASC")
      .execute()
      .map(rs -> StreamSupport.stream(rs.spliterator(), false)
        .map(MAPPER)
        .collect(Collectors.toList())
      );
  }


  public Future<Message> findById(int id) {
    return client.preparedQuery("SELECT * FROM messages WHERE receiverId=?").execute(Tuple.of(id))
      .map(RowSet::iterator)
      .map(iterator -> {
          if (iterator.hasNext()) {
            return MAPPER.apply(iterator.next());
          }
          throw new RuntimeException("Messages not found for user with id" + id);
        }
      );
  }

  public Future<Integer> save(Message message) {
    LOGGER.log(Level.INFO, message.getContent()+ message.getReceiverId());
    client.preparedQuery("INSERT INTO messages(message,date_sent,receiver_id,sender_id) VALUES (?,?,?)").execute(Tuple.of(message.getContent(), message.getDate(), message.getReceiverId(),message.getSenderId()),
        res -> {
      if (res.succeeded())
        LOGGER.log(Level.INFO,"Inserted new message");
        });

    return client.query("select last_insert_id()").execute()
      .map(rs -> rs.iterator().next().getInteger("last_insert_id()"));
  }

}
