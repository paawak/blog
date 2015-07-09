/*
 * TimestampInterceptor.java
 *
 * Created on Sep 22, 2010 12:46:15 AM
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

package com.swayam.demo.oracle.hibernate;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

/**
 * 
 * @author paawak
 */
public class TimestampInterceptor extends EmptyInterceptor {

    private static final long serialVersionUID = 398221336871362110L;

    private static final Logger LOG = Logger
            .getLogger(TimestampInterceptor.class);

    @Override
    public boolean onSave(Object entity,

    Serializable id,

    Object[] state,

    String[] propertyNames,

    Type[] types) {

        for (int i = 0; i < state.length; i++) {
            LOG.info(i + "> state=" + state[i] + ", type=" + types[i]);
        }

        // state[1] = "2010-09-25 18:59:30:424 Asia/Tokyo";
        // types[1] = new StringType();

        return false;

    }

    @Override
    public String onPrepareStatement(String sql) {
        LOG.info("original sql=" + sql);

        if (sql.startsWith("insert into TIMESTAMP_DEMO_2")) {
            sql = "insert into TIMESTAMP_DEMO_2 (name, TIME_WITH_ZONE, id) values (?, TO_TIMESTAMP_TZ(?, 'YYYY-MM-DD HH24:MI:SS:FF TZR'), ?)";
            LOG.info("modified sql=" + sql);
        }

        return sql;
    }

}
