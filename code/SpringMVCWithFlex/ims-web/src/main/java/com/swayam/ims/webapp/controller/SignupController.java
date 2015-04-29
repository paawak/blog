package com.swayam.ims.webapp.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.mail.MailException;
import org.springframework.security.AccessDeniedException;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.swayam.ims.core.Constants;
import com.swayam.ims.core.service.RoleManager;
import com.swayam.ims.core.service.UserExistsException;
import com.swayam.ims.model.orm.User;
import com.swayam.ims.webapp.util.RequestUtil;

/**
 * Controller to signup new users.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class SignupController extends BaseFormController {
    private RoleManager roleManager;

    public void setRoleManager(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    public SignupController() {
        setCommandName("user");
        setCommandClass(User.class);
    }

    public ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("entering 'onSubmit' method...");
        }

        User user = (User) command;
        Locale locale = request.getLocale();

        user.setEnabled(true);

        // Set the default user role on this new user
        user.addRole(roleManager.getRole(Constants.USER_ROLE));

        try {
            this.getUserManager().saveUser(user);
        } catch (AccessDeniedException ade) {
            // thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity
            log.warn(ade.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        } catch (UserExistsException e) {
            errors.rejectValue("username", "errors.existing.user",
                    new Object[] { user.getUsername(),
                            user.getAddress().getEmail() }, "duplicate user");

            // redisplay the unencrypted passwords
            user.setPassword(user.getConfirmPassword());
            return showForm(request, response, errors);
        }

        saveMessage(request, getText("user.registered", user.getUsername(),
                locale));
        request.getSession().setAttribute(Constants.REGISTERED, Boolean.TRUE);

        // log user in automatically
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getConfirmPassword(), user
                        .getAuthorities());
        auth.setDetails(user);
        SecurityContextHolder.getContext().setAuthentication(auth);

        // Send user an e-mail
        if (log.isDebugEnabled()) {
            log.debug("Sending user '" + user.getUsername()
                    + "' an account information e-mail");
        }

        // Send an account information e-mail
        message.setSubject(getText("signup.email.subject", locale));

        try {
            sendUserMessage(user, getText("signup.email.message", locale),
                    RequestUtil.getAppURL(request));
        } catch (MailException me) {
            saveError(request, me.getMostSpecificCause().getMessage());
        }

        return new ModelAndView(getSuccessView());
    }
}
