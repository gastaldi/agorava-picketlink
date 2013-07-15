/*
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.gastaldi.app.agorava.http;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.agorava.LinkedIn;
import org.agorava.core.api.oauth.OAuthService;

@WebFilter(filterName = "AgoravaPicketLink Filter",
         urlPatterns = "/*")
public class AgoravaPicketLinkFilter implements Filter
{

   @Inject
   private HttpProducer producer;

   @Inject
   @LinkedIn
   OAuthService service;

   @Override
   public void init(FilterConfig filterConfig) throws ServletException
   {
      // Do nothing
   }

   @Override
   public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException,
            ServletException
   {
      HttpServletRequest httpRequest = (HttpServletRequest) request;
      HttpServletResponse httpResponse = (HttpServletResponse) response;
      producer.setRequest(httpRequest);
      producer.setResponse(httpResponse);

      if ("/oauth_callback".equals(httpRequest.getRequestURI()))
      {
         String verifier = httpRequest.getParameter(service.getVerifierParamName());
         service.setVerifier(verifier);
         service.initAccessToken();
         // TODO: Redirect someplace else
         httpResponse.sendRedirect("/");
      }
      else
      {
         chain.doFilter(request, response);
      }
   }

   @Override
   public void destroy()
   {
   }
}