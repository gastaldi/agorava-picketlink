package org.gastaldi.app.agorava.settings;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;

import org.agorava.LinkedIn;
import org.agorava.core.api.oauth.OAuthAppSettings;
import org.agorava.core.api.oauth.OAuthAppSettingsBuilder;
import org.agorava.core.api.oauth.OAuthSession;
import org.agorava.core.cdi.Current;
import org.agorava.core.oauth.SimpleOAuthAppSettingsBuilder;

/**
 * Configures Agorava with the needed producers
 * 
 * @author <a href="mailto:gegastaldi@gmail.com">George Gastaldi</a>
 */
public class OauthSettingsProducer
{

   @LinkedIn
   @ApplicationScoped
   @Produces
   public OAuthAppSettings produceLinkedInSettings()
   {
      OAuthAppSettingsBuilder builder = new SimpleOAuthAppSettingsBuilder();
      return builder.apiKey("u0r1md2ia2y8").apiSecret("hBZsiNBAKuYrfFaU").callback("faces/oauth_callback.xhtml").build();
   }

   @SessionScoped
   @Produces
   @LinkedIn
   @Current
   public OAuthSession produceOauthSession(@LinkedIn @Default OAuthSession session)
   {
      return session;
   }

}
