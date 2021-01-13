/**
 * 
 */
/**
 * @author tshaji
 *
 */
package org.mule.modules.gsuiteoauth2.config;

import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.display.FriendlyName;
import org.mule.api.annotations.display.Placement;

public class ConnectorConfig {
	@Configurable
	@Placement(group = "Connection", order = 1)
	@FriendlyName("Token Uri")
	private String tokenUri;

	@Configurable
	@Placement(group = "Connection", order = 2)
	@FriendlyName("Email")
	private String email_id;

	@Configurable
	@Placement(group = "Connection", order = 3)
	@FriendlyName("Scopes")
	private String scope;

	public void setTokenUri(String tokenUri) {
		this.tokenUri = tokenUri;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getTokenUri() {
		return this.tokenUri;
	}

	public String getEmail_id() {
		return this.email_id;
	}

	public String getScope() {
		return this.scope;
	}
}