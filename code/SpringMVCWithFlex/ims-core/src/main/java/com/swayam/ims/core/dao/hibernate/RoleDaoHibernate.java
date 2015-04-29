package com.swayam.ims.core.dao.hibernate;

import java.util.List;

import com.swayam.ims.core.dao.RoleDao;
import com.swayam.ims.model.orm.Role;

/**
 * This class interacts with Spring's HibernateTemplate to save/delete and retrieve Role objects.
 * 
 * @author <a href="mailto:bwnoll@gmail.com">Bryan Noll</a>
 */
public class RoleDaoHibernate extends GenericDaoHibernate<Role> implements
        RoleDao {

    /**
     * Constructor to create a Generics-based version using Role as the entity
     */
    public RoleDaoHibernate() {
        super(Role.class);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public Role getRoleByName(String rolename) {
        List roles = getHibernateTemplate().find("from Role where name=?",
                rolename);
        if (roles.isEmpty()) {
            return null;
        } else {
            return (Role) roles.get(0);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void removeRole(String rolename) {
        Object role = getRoleByName(rolename);
        getHibernateTemplate().delete(role);
    }
}
