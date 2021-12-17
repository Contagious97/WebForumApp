package com.example.charts_service;

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

public class ChartRepository {
  private static final Logger LOGGER = Logger.getLogger(ChartRepository.class.getName());

  private static Function<Row, Chart> MAPPER = (row) ->
    Chart.of(
      row.getInteger("id"),
      row.getString("data")
    );


  private final MySQLPool client;

  private ChartRepository(MySQLPool _client) {
    this.client = _client;
  }

  //factory method
  public static ChartRepository create(MySQLPool client) {
    return new ChartRepository(client);
  }

  public Future<List<Chart>> findAll() {
    return client.query("SELECT * FROM charts ORDER BY id ASC")
      .execute()
      .map(rs -> StreamSupport.stream(rs.spliterator(), false)
        .map(MAPPER)
        .collect(Collectors.toList())
      );
  }


  public Future<Chart> findById(int id) {
    return client.preparedQuery("SELECT * FROM charts WHERE id=?").execute(Tuple.of(id))
      .map(RowSet::iterator)
      .map(iterator -> {
          if (iterator.hasNext()) {
            return MAPPER.apply(iterator.next());
          }
          throw new RuntimeException("Chart not found with id: " + id);
        }
      );
  }

  public Future<Integer> save(Chart chart) {
    LOGGER.log(Level.INFO, chart.getData());
    client.preparedQuery("INSERT INTO charts(data,date) VALUES (?,?)").execute(Tuple.of(chart.getData(), chart.getDate()),
        res -> {
      if (res.succeeded())
        LOGGER.log(Level.INFO,"Inserted new chart");
        });

    return client.query("select last_insert_id()").execute()
      .map(rs -> rs.iterator().next().getInteger("last_insert_id()"));
  }

}
