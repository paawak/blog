package com.swayam.ims.core.test.service.impl;

import com.swayam.ims.core.Constants;
import com.swayam.ims.core.dao.LookupDao;
import com.swayam.ims.core.service.impl.BaseManagerMockTestCase;
import com.swayam.ims.core.service.impl.LookupManagerImpl;
import com.swayam.ims.model.orm.LabelValue;
import com.swayam.ims.model.orm.Role;
import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;


public class LookupManagerImplTest extends BaseManagerMockTestCase {
    private LookupManagerImpl mgr = new LookupManagerImpl();
    private LookupDao lookupDao = null;

    @Before
    public void setUp() throws Exception {
        lookupDao = context.mock(LookupDao.class);
        mgr.setLookupDao(lookupDao);
    }

    @Test
    public void testGetAllRoles() {
        log.debug("entered 'testGetAllRoles' method");

        // set expected behavior on dao
        Role role = new Role(Constants.ADMIN_ROLE);
        final List<Role> testData = new ArrayList<Role>();
        testData.add(role);
        context.checking(new Expectations() {{
            one(lookupDao).getRoles();
            will(returnValue(testData));
        }});

        List<LabelValue> roles = mgr.getAllRoles();
        assertTrue(roles.size() > 0);
    }
}
