/*
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.gastaldi.app.picketlink;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.agorava.LinkedIn;
import org.agorava.core.api.UserProfile;
import org.agorava.core.api.oauth.OAuthService;
import org.agorava.core.api.oauth.OAuthSession;
import org.picketlink.annotations.PicketLink;
import org.picketlink.authentication.BaseAuthenticator;
import org.picketlink.credential.DefaultLoginCredentials;
import org.picketlink.idm.credential.Credentials.Status;

@ApplicationScoped
@PicketLink
public class AgoravaAuthenticator extends BaseAuthenticator
{

   @Inject
   DefaultLoginCredentials credentials;

   @Inject
   @PicketLink
   Instance<HttpServletRequest> request;

   @Inject
   @PicketLink
   Instance<HttpServletResponse> response;

   @Inject
   @LinkedIn
   OAuthService service;

   @Override
   public void authenticate()
   {
      OAuthSession session = service.getSession();
      if (session == null)
      {
         String authorizationUrl = service.getAuthorizationUrl();
         try
         {
            response.get().sendRedirect(authorizationUrl);
         }
         catch (IOException e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         credentials.setStatus(Status.IN_PROGRESS);
         setStatus(AuthenticationStatus.DEFERRED);
      }
      else
      {
         UserProfile userProfile = session.getUserProfile();
         credentials.setUserId(userProfile.getId());
         credentials.setCredential(session.getAccessToken());
         credentials.setStatus(Status.VALID);
         setStatus(AuthenticationStatus.SUCCESS);
      }
   }
}
