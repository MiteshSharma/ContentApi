package services.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import exceptions.UserLoginFailedException;
import services.IJwtService;

import java.text.ParseException;

/**
 * Created by mitesh on 18/11/16.
 */
public class JwtService implements IJwtService {

    String sharedKey = "a0a2abd8-6162-41c3-83d6-1cf559b46afc";

    public String getJwtWithMessage(String message) throws UserLoginFailedException {
        Payload payload = new Payload(message);
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        JWSSigner signer = null;
        try {
            signer = new MACSigner(sharedKey.getBytes());
        } catch (KeyLengthException e) {
            e.printStackTrace();
            throw new UserLoginFailedException();
        }

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(signer);
        } catch (JOSEException e) {
            e.printStackTrace();
            throw new UserLoginFailedException();
        }
        return jwsObject.serialize();
    }

    public String verifyJwt(String jwt) {
        JWSObject jwsObject;
        try {
            jwsObject = JWSObject.parse(jwt);
        } catch (ParseException e) {
            return null;
        }

        JWSVerifier verifier = null;
        try {
            verifier = new MACVerifier(sharedKey.getBytes());
        } catch (JOSEException e) {
            return null;
        }

        boolean verifiedSignature = false;

        try {
            verifiedSignature = jwsObject.verify(verifier);
        }
        catch (JOSEException e) {
            return null;
        }

        if (verifiedSignature) {
            return jwsObject.getPayload().toString();
        }
        return null;
    }
}
