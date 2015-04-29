package com.swayam.ims.core.dao.hibernate;

import java.util.List;

import com.swayam.ims.core.dao.LookupDao;
import com.swayam.ims.model.orm.Role;

/**
 * Hibernate implementation of LookupDao.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class LookupDaoHibernate extends UniversalDaoHibernate implements LookupDao {

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<Role> getRoles() {
        log.debug("Retrieving all role names...");

        return getHibernateTemplate().find("from Role order by name");
    }
}
