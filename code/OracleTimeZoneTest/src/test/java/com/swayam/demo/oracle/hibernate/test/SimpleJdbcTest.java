/*
 * SimpleJdbcTest.java
 *
 * Created on Sep 25, 2010 10:22:00 PM
 *
 * Copyright (c) 2002 - 2008 : Swayam Inc.
 *
 * P R O P R I E T A R Y & C O N F I D E N T I A L
 *
 * The copyright of this document is vested in Swayam Inc. without
 * whose prior written permission its contents must not be published,
 * adapted or reproduced in any form or disclosed or
 * issued to any third party.
 */

package com.swayam.demo.oracle.hibernate.test;

import static com.swayam.demo.oracle.hibernate.util.DateUtil.getOracleFormattedTimeWithZone;
import static com.swayam.demo.oracle.hibernate.util.DateUtil.getTimeWithZone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author paawak
 */
public class SimpleJdbcTest {

    private Connection con;

    @Before
    public void init() throws SQLException {

        // Class.forName("oracle.jdbc.OracleDriver");
        con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521/XE", "SWAYAM_TEST",
                "SWAYAM_TEST");

    }

    @After
    public void cleanUp() throws SQLException {
        con.close();
    }

    @Test
    public void insert_1() throws SQLException {

        PreparedStatement pStat = con
                .prepareStatement("INSERT INTO TIMESTAMP_DEMO "
                        + " (ID, NAME, TIME_WITH_ZONE, TIME_WITH_ZONE_LOCAL) "
                        + " VALUES " + " (?, ?, ?, ?)");

        int nextVal = getNextVal();

        pStat.setInt(1, nextVal);
        pStat.setString(2, "insert_1");

        Calendar timeWithZone = getTimeWithZone();

        Timestamp ts = new Timestamp(timeWithZone.getTimeInMillis());

        pStat.setTimestamp(3, ts);

        pStat.setTimestamp(4, ts);

        pStat.execute();

        pStat.close();

    }

    @Test
    public void insert_2() throws SQLException {

        PreparedStatement pStat = con
                .prepareStatement("INSERT INTO TIMESTAMP_DEMO "
                        + " (ID, NAME, TIME_WITH_ZONE, TIME_WITH_ZONE_LOCAL) "
                        + " VALUES " + " (?, ?, ?, ?)");

        int nextVal = getNextVal();

        pStat.setInt(1, nextVal);
        pStat.setString(2, "insert_2");

        Calendar timeWithZone = getTimeWithZone();

        Timestamp ts = new Timestamp(timeWithZone.getTimeInMillis());

        pStat.setTimestamp(3, ts, timeWithZone);

        pStat.setTimestamp(4, ts, timeWithZone);

        pStat.execute();

        pStat.close();

    }

    @Test
    public void insert_3() throws SQLException {

        Calendar timeWithZone = getTimeWithZone();

        String dateTime = getOracleFormattedTimeWithZone(timeWithZone);

        System.out.println("dateTime before insert_3=" + dateTime);

        PreparedStatement pStat = con
                .prepareStatement("INSERT INTO TIMESTAMP_DEMO "
                        + " (ID, NAME, TIME_WITH_ZONE, TIME_WITH_ZONE_LOCAL) "
                        + " VALUES "
                        + " (?, ?, TO_TIMESTAMP_TZ(?, 'YYYY-MM-DD HH24:MI:SS:FF TZR'), ?)");

        int nextVal = getNextVal();

        pStat.setInt(1, nextVal);
        pStat.setString(2, "insert_3");

        Timestamp ts = new Timestamp(timeWithZone.getTimeInMillis());

        pStat.setString(3, dateTime);

        pStat.setTimestamp(4, ts, timeWithZone);

        pStat.execute();

        pStat.close();

    }

    @Test
    public void select_exception() throws SQLException {

        System.out
                .println("**********************    EXCEPTION    *******************************");

        Statement stat = con.createStatement();

        ResultSet res = stat
                .executeQuery("SELECT * FROM TIMESTAMP_DEMO  ORDER BY ID");

        while (res.next()) {

            Timestamp timestamp = res.getTimestamp("TIME_WITH_ZONE");

            Timestamp timestampLocal = null;

            try {
                timestampLocal = res.getTimestamp("TIME_WITH_ZONE_LOCAL");
            } catch (SQLException e) {
                e.printStackTrace();
                break;
            }

            System.out.println("TIME=" + timestamp + ", TIME_LOCAL="
                    + timestampLocal);

        }

        stat.close();
        res.close();

    }

    @Test
    public void select_1() throws SQLException {

        System.out
                .println("**********************    JDBC    *******************************");

        Statement stat = con.createStatement();

        ResultSet res = stat
                .executeQuery("SELECT * FROM TIMESTAMP_DEMO  ORDER BY ID");

        while (res.next()) {

            Timestamp timestamp = res.getTimestamp("TIME_WITH_ZONE");

            Timestamp timestampLocal = res.getTimestamp("TIME_WITH_ZONE_LOCAL",
                    new GregorianCalendar(TimeZone.getDefault()));

            System.out.println(res.getString("NAME") + ": "
                    + res.getString("ID") + ", TIME="
                    + getTimeWithZone(timestamp) + ", TIME_LOCAL="
                    + getTimeWithZone(timestampLocal));

        }

        stat.close();
        res.close();

    }

    @Test
    public void select_2() throws SQLException {

        System.out
                .println("**********************    ORACLE_FUNCTION    *******************************");

        Statement stat = con.createStatement();

        ResultSet res = stat.executeQuery("SELECT ID, NAME, "
                + " TO_CHAR(TIME_WITH_ZONE, 'HH24:MI:SS:FF TZR'), "
                + " TO_CHAR(TIME_WITH_ZONE_LOCAL, 'HH24:MI:SS:FF TZR') "
                + " FROM TIMESTAMP_DEMO  ORDER BY ID");

        while (res.next()) {

            String timestamp = res.getString(3);
            String timestampLocal = res.getString(4);

            System.out.println(res.getString("NAME") + ": "
                    + res.getString("ID") + ", TIME=" + timestamp
                    + ", TIME_LOCAL=" + timestampLocal);

        }

        stat.close();
        res.close();

    }

    private int getNextVal() throws SQLException {

        Statement stat = con.createStatement();

        ResultSet res = stat.executeQuery("SELECT MAX(ID) FROM TIMESTAMP_DEMO");

        int nextVal = 1;

        if (res.next()) {
            nextVal = res.getInt(1);
            nextVal++;
        }

        res.close();
        stat.close();

        return nextVal;

    }

}
