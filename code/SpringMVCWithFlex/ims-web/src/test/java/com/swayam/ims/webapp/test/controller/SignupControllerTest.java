package com.swayam.ims.webapp.test.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.subethamail.wiser.Wiser;

import com.swayam.ims.core.Constants;
import com.swayam.ims.webapp.controller.BaseControllerTestCase;
import com.swayam.ims.webapp.controller.SignupController;

public class SignupControllerTest extends BaseControllerTestCase {
    private SignupController c = null;

    public void setSignupController(SignupController signup) {
        this.c = signup;
    }

    public void testDisplayForm() throws Exception {
        MockHttpServletRequest request = newGet("/signup.html");
        HttpServletResponse response = new MockHttpServletResponse();
        ModelAndView mv = c.handleRequest(request, response);
        assertTrue("returned correct view name", mv.getViewName().equals(
                "signup"));
    }

    public void testSignupUser() throws Exception {
        MockHttpServletRequest request = newPost("/signup.html");
        request.addParameter("username", "self-registered");
        request.addParameter("password", "Password1");
        request.addParameter("confirmPassword", "Password1");
        request.addParameter("firstName", "First");
        request.addParameter("lastName", "Last");
        request.addParameter("address.city", "Denver");
        request.addParameter("address.province", "Colorado");
        request.addParameter("address.country", "USA");
        request.addParameter("address.postalCode", "80210");
        request.addParameter("address.email", "self@raible.com");
        request.addParameter("address.website", "http://raibledesigns.com");
        request.addParameter("passwordHint", "Password is one with you.");

        HttpServletResponse response = new MockHttpServletResponse();

        // start SMTP Server
        Wiser wiser = new Wiser();
        wiser.setPort(getSmtpPort());
        wiser.start();

        ModelAndView mv = c.handleRequest(request, response);
        Errors errors = (Errors) mv.getModel().get(
                BindException.MODEL_KEY_PREFIX + "user");

        assertTrue("no errors returned in model", errors == null);

        // verify an account information e-mail was sent
        wiser.stop();
        assertTrue(wiser.getMessages().size() == 1);

        // verify that success messages are in the request
        assertNotNull(request.getSession().getAttribute("successMessages"));
        assertNotNull(request.getSession().getAttribute(Constants.REGISTERED));

        SecurityContextHolder.getContext().setAuthentication(null);
    }

}
