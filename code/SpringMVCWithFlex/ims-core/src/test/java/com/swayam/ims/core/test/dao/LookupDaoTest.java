package com.swayam.ims.core.test.dao;

import java.util.List;

import com.swayam.ims.core.dao.BaseDaoTestCase;
import com.swayam.ims.core.dao.LookupDao;

/**
 * This class tests the current LookupDao implementation class
 * @author mraible
 */
public class LookupDaoTest extends BaseDaoTestCase {
    private LookupDao dao;
    
    public void setLookupDao(LookupDao dao) {
        this.dao = dao;
    }

    public void testGetRoles() {
        List roles = dao.getRoles();
        log.debug(roles);
        assertTrue(roles.size() > 0);
    }
}
