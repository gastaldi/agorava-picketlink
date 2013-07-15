/*
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.gastaldi.app;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.picketlink.Identity;

@Named
@SessionScoped
public class UserBean implements Serializable
{
   private static final long serialVersionUID = 1L;

   @Inject
   Identity identity;

   public void login()
   {
      System.out.println("Authentication Result: " + identity.login());
   }
}
