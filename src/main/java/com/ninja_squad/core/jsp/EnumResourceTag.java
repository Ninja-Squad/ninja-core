package com.ninja_squad.core.jsp;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Strings;
import com.ninja_squad.core.i18n.EnumResources;
import com.ninja_squad.core.i18n.MissingResourceStrategies;

/**
 * A JSP tag to get an internationalized label from an enum constant
 * using {@link EnumResources}
 * @author JB
 */
public class EnumResourceTag extends SimpleTagSupport {

    private String suffix;
    private Object enumConstant;
    private String var;
    private int scope = PageContext.PAGE_SCOPE;

    /**
     * Sets the enum constant for which an internationalized label must be found
     * @param enumConstant the value of the enum constant
     */
    public void setEnum(Object enumConstant) {
        this.enumConstant = enumConstant;
    }

    /**
     * Sets the suffix to use
     * @param suffix the suffix. If <code>null</code>, an empty suffix is used instead.
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * Sets the name of the attribute the label is written to
     * @param var the name of the attribute
     */
    public void setVar(String var) {
        this.var = var;
    }

    /**
     * Sets the scope of the attribute the label is written to
     * @param scope the scope of the attribute (request, session or application)
     */
    public void setScope(String scope) {
        if ("request".equals(scope)) {
            this.scope = PageContext.REQUEST_SCOPE;
        }
        else if ("session".equals(scope)) {
            this.scope = PageContext.SESSION_SCOPE;
        }
        else if ("application".equals(scope)) {
            this.scope = PageContext.APPLICATION_SCOPE;
        }
        else {
            this.scope = PageContext.PAGE_SCOPE;
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void doTag() throws JspException, IOException {
        String label = "";
        if (enumConstant != null && !(enumConstant instanceof Enum<?>)) {
            throw new JspException("The given enum (" + enumConstant + ") is not an instance of enum");
        }

        EnumResources enumResources = EnumResources.getInstance()
                                                   .withLocale(resolveLocale())
                                                   .withSuffix(Strings.nullToEmpty(suffix))
                                                   .withMissingResourceStrategy(MissingResourceStrategies.JSTL);
        label = enumResources.getString((Enum) enumConstant);
        if (var == null) {
            getJspContext().getOut().print(label);
        }
        else {
            getJspContext().setAttribute(var, label, scope);
        }
    }

    @VisibleForTesting
    protected Locale resolveLocale() {
        return Config.getLocaleResolver().resolveLocale((PageContext) getJspContext());
    }
}
