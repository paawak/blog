package com.swayam.ims.webapp.test.controller;

import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.swayam.ims.core.Constants;
import com.swayam.ims.webapp.controller.BaseControllerTestCase;
import com.swayam.ims.webapp.controller.UserController;

public class UserControllerTest extends BaseControllerTestCase {

    @SuppressWarnings("unchecked")
    public void testHandleRequest() throws Exception {
        UserController c = (UserController) applicationContext
                .getBean("userController");
        ModelAndView mav = c.handleRequest(null, null);
        Map m = mav.getModel();
        assertNotNull(m.get(Constants.USER_LIST));
        assertEquals("admin/userList", mav.getViewName());
    }
}
