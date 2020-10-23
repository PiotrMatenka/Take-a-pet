package pl.piotron.animals.security;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import pl.piotron.animals.model.User;

public class CustomMethodSecurityExpressionRoot  extends SecurityExpressionRoot
implements MethodSecurityExpressionOperations
{
    public CustomMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public boolean isUser (Long id)
    {
        User user = ((UserPrincipal) this.getPrincipal()).getUser();
        return user.getId() == id;
    }

    @Override
    public void setFilterObject(Object o) {

    }

    @Override
    public Object getFilterObject() {
        return null;
    }

    @Override
    public void setReturnObject(Object o) {

    }

    @Override
    public Object getReturnObject() {
        return null;
    }

    @Override
    public Object getThis() {
        return null;
    }
}
