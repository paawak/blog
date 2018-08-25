package com.swayam.thread.demo.deadlock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomWordDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomWordDao.class);

    public int getNextId() {
        String sql = "SELECT COUNT(*) FROM random_word";
        Connection con = DatabaseConnectionUtils.INSTANCE.getConnection();
        try {

            PreparedStatement lock = con.prepareStatement("LOCK TABLES random_word WRITE");
            // lock.execute();

            PreparedStatement pStat = con.prepareStatement(sql);
            ResultSet res = pStat.executeQuery();
            if (res.next()) {
                return res.getInt(1) + 1;
            }
            res.close();
            pStat.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                PreparedStatement unlock = con.prepareStatement("UNLOCK TABLES");
                // unlock.execute();
                con.close();
            } catch (SQLException e) {
                LOGGER.warn("could not close connection for cleanup", e);
            }
        }

        return -1;
    }

    public void insert(int id, String word) {
        String sql = "INSERT INTO random_word (id, word) VALUES (?, ?)";
        Connection con = DatabaseConnectionUtils.INSTANCE.getConnection();
        try {
            PreparedStatement lock = con.prepareStatement("LOCK TABLES random_word WRITE");
            lock.execute();
            PreparedStatement pStat = con.prepareStatement(sql);
            pStat.setInt(1, id);
            pStat.setString(2, word);
            int rowsInserted = pStat.executeUpdate();
            LOGGER.info("inserted {} rows", rowsInserted);
            pStat.close();

        } catch (SQLException e) {
            LOGGER.warn("could not insert: {}", e.getMessage());
        } finally {
            try {
                PreparedStatement unlock = con.prepareStatement("UNLOCK TABLES");
                // unlock.execute();
                con.close();
            } catch (SQLException e) {
                LOGGER.warn("could not close connection for cleanup", e);
            }
        }

    }

}
