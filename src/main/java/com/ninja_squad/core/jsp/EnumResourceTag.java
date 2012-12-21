/*
 * The MIT License
 *
 * Copyright (c) 2012, Ninja Squad
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
 * @author JB Nizet
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

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void doTag() throws JspException, IOException {
        if (enumConstant != null && !(enumConstant instanceof Enum<?>)) {
            throw new JspException("The given enum (" + enumConstant + ") is not an instance of enum");
        }

        EnumResources enumResources = EnumResources.getInstance()
                                                   .withLocale(resolveLocale())
                                                   .withSuffix(Strings.nullToEmpty(suffix))
                                                   .withMissingResourceStrategy(MissingResourceStrategies.JSTL);
        String label = enumResources.getString((Enum) enumConstant);
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
