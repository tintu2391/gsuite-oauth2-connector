/*
 * @author TintuJacobShaji
   @version 1.0.0
 */
package org.mule.modules.gsuiteoauth2;

import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.display.FriendlyName;
import org.mule.api.annotations.display.Placement;
import org.mule.api.annotations.display.Summary;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.Base64;
import java.util.Date;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;

@Connector(name = "gsuite-oauth2", friendlyName = "Gsuite Authentication Connector")
public class GsuiteOauth2Connector {
	@Processor(friendlyName = "Authenticate GSuite")
	public Object Authenticate(
			@Placement(group = "Connection", order = 1) @Summary("Private key in string format") @FriendlyName("Private Key") String PrivateKey,
			@Placement(group = "Connection", order = 1) @Summary("Token Uri") @FriendlyName("Token Uri") String tokenUri,
			@Placement(group = "Connection", order = 1) @Summary("Service Account email address") @FriendlyName("Service Account Email Address") String accountId,
			@Placement(group = "Connection", order = 2) @Summary("User Email address") @FriendlyName("User Email Address") String email_id,
			@Placement(group = "Connection", order = 3) @Summary("Scopes required for the authentication") @FriendlyName("Scopes") String scopes)
			throws Exception {
		long now = System.currentTimeMillis();
		try {
			Algorithm algorithm = Algorithm.RSA256(null, (RSAPrivateKey) StringToPrivateKey(PrivateKey, "RSA"));

			String signedJWT = JWT.create().withIssuer(accountId).withSubject(email_id).withAudience(tokenUri)
					.withIssuedAt(new Date(now)).withClaim("scope", scopes).withExpiresAt(new Date(now + 3600 * 1000L))
					.sign(algorithm);
			return signedJWT;
		} catch (Exception ex) {
			return ex.getMessage();
		}
	}

	public PrivateKey StringToPrivateKey(String Key, String Format) throws Exception {
		try {
			String privateKeyContent = Key.replaceAll("\\n", "").replace("-----BEGIN PRIVATE KEY-----", "")
					.replace("-----END PRIVATE KEY-----", "").replaceAll("\\s+", "");
			KeyFactory kf = KeyFactory.getInstance(Format);
			PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
			PrivateKey privateKey = kf.generatePrivate(keySpecPKCS8);
			System.out.println("Private Key After Conversion: " + privateKey);
			return privateKey;
		} catch (Exception ex) {
			throw ex;
		}
	}

}