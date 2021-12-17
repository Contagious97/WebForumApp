package com.example.user_microservice;

import io.vertx.core.Future;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class LogRepository {
  private static final Logger LOGGER = Logger.getLogger(LogRepository.class.getName());

  private static Function<Row, Log> MAPPER = (row) ->
    Log.of(
      row.getInteger("id"),
      row.getString("content"),
      Timestamp.valueOf(row.getLocalDateTime("date")),
      row.getInteger("userId")
    );


  private final MySQLPool client;

  private LogRepository(MySQLPool _client) {
    this.client = _client;
  }

  //factory method
  public static LogRepository create(MySQLPool client) {
    return new LogRepository(client);
  }

  public Future<List<Log>> findAll() {
    return client.query("SELECT * FROM logs ORDER BY id ASC")
      .execute()
      .map(rs -> StreamSupport.stream(rs.spliterator(), false)
        .map(MAPPER)
        .collect(Collectors.toList())
      );
  }


  public Future<List<Log>> findById(int id) {
    return client.preparedQuery("SELECT * FROM logs WHERE userId=?").execute(Tuple.of(id))
      .map(rs -> StreamSupport.stream(rs.spliterator(), false)
        .map(MAPPER)
        .collect(Collectors.toList())
      );
  }

  public Future<Integer> save(Log log) {
    LOGGER.log(Level.INFO,log.getContent()+log.getUserId());
    client.preparedQuery("INSERT INTO logs(content,date,userId) VALUES (?,?,?)").execute(Tuple.of(log.getContent(), log.getDate(),log.getUserId()),
        res -> {
      if (res.succeeded())
        LOGGER.log(Level.INFO,"Inserted new log");
        });


    return client.query("select last_insert_id()").execute()
      .map(rs -> rs.iterator().next().getInteger("last_insert_id()"));
  }



}
