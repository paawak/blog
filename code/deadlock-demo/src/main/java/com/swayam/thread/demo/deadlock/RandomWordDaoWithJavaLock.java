package com.swayam.thread.demo.deadlock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomWordDaoWithJavaLock implements RandomWordDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomWordDaoWithJavaLock.class);

    private final Object lock = new Object();

    @Override
    public void insert(String word) {
        LongRunningProcessExecutor.postImageToLocalTesseractOCR();

        synchronized (lock) {
            insertIntoDB(word);
        }

    }

    private void insertIntoDB(String word) {
        Connection con = DatabaseConnectionUtils.INSTANCE.getConnection();
        try {
            PreparedStatement query = con.prepareStatement("SELECT COUNT(*) FROM random_word");
            ResultSet res = query.executeQuery();
            res.next();
            int id = res.getInt(1) + 1;
            res.close();
            query.close();

            PreparedStatement insert = con.prepareStatement("INSERT INTO random_word (id, word) VALUES (?, ?)");
            insert.setInt(1, id);
            insert.setString(2, word);
            int rowsInserted = insert.executeUpdate();
            LOGGER.info("inserted {} rows", rowsInserted);
            insert.close();

        } catch (SQLException e) {
            LOGGER.error("could not insert row", e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                LOGGER.warn("could not close connection for cleanup", e);
            }
        }
    }

}
