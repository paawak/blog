/*
 * ZonedTimestamp.java
 *
 * Created on Sep 12, 2010 9:44:46 PM
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

package com.swayam.demo.oracle.hibernate.custom;

import static com.swayam.demo.oracle.hibernate.util.DateUtil.getOracleFormattedTimeWithZone;
import static com.swayam.demo.oracle.hibernate.util.DateUtil.parseOracleTimestampWithZone;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

/**
 * 
 * @author paawak
 */
public class TimestampType2 extends TimestampType {

    private static final Logger LOG = Logger.getLogger(TimestampType2.class);

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
            throws HibernateException, SQLException {

        Calendar cal = null;
        String timestamp = rs.getString(names[0]);

        LOG.info("raw timestamp=" + timestamp);

        if (timestamp != null) {
            cal = parseOracleTimestampWithZone(timestamp);
        }

        return cal;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index)
            throws HibernateException, SQLException {

        if (value == null) {
            st.setNull(index, Types.DATE);
        } else {

            doInstanceCheck(value);
            Calendar cal = (Calendar) value;

            String dateTime = getOracleFormattedTimeWithZone(cal);

            LOG.info("dateTime=" + dateTime);
            LOG.info("index=" + index);

            st.setString(index, dateTime);

        }

    }

}
