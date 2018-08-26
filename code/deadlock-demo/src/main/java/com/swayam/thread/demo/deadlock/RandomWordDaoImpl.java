package com.swayam.thread.demo.deadlock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomWordDaoImpl implements RandomWordDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomWordDaoImpl.class);

    @Override
    public void insert(String word) {
        Connection con = DatabaseConnectionUtils.INSTANCE.getConnection();
        try {
            PreparedStatement lock = con.prepareStatement("LOCK TABLES random_word WRITE");
            lock.execute();
            lock.close();

            PreparedStatement query = con.prepareStatement("SELECT COUNT(*) FROM random_word");
            ResultSet res = query.executeQuery();
            res.next();
            int id = res.getInt(1) + 1;
            res.close();
            query.close();

            exec();

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
                PreparedStatement unlock = con.prepareStatement("UNLOCK TABLES");
                unlock.execute();
                unlock.close();
                con.close();
            } catch (SQLException e) {
                LOGGER.warn("could not close connection for cleanup", e);
            }
        }

    }

    private void exec() {
        Process p;
        try {
            p = Runtime.getRuntime().exec(
                    "curl -v -X POST -H \"content-type:multipart/form-data\" -F image=@/kaaj/source/porua/tesseract-ocr-docker/sample-images/bangla.jpg -F language=ben http://localhost:8080/rest/ocr");
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

        StringBuilder sb = new StringBuilder();

        while (true) {
            String line;
            try {
                line = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (line == null) {
                break;
            }
            sb.append(line).append("\n");
        }
        sb.setLength(sb.length() - 1);
        String output = sb.toString();
        LOGGER.debug("output from curl: {}", output);
    }

}
