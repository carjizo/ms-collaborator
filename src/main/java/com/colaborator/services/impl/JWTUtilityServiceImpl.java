package com.colaborator.services.impl;

import com.colaborator.services.JWTUtilityService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Service
public class JWTUtilityServiceImpl implements JWTUtilityService {

    @Value("classpath:jwtKeys/public_key.pem")
    private Resource publicKeyResource;

    @Override
    public JWTClaimsSet parseJWT(String jwt) throws JOSEException, IOException, ParseException, NoSuchAlgorithmException, InvalidKeySpecException, java.text.ParseException {
        PublicKey publicKey = loadPublicKey(publicKeyResource);

        SignedJWT signedJWT = SignedJWT.parse(jwt);

        JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) publicKey);
        if (!signedJWT.verify(verifier)) {
            throw new JOSEException("Invalid signature");
        }

        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
        if (claimsSet.getExpirationTime().before(new Date())) {
            throw new JOSEException("Expired token");
        }

        return claimsSet;
    }

    private PublicKey loadPublicKey(Resource resource) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Files.readAllBytes(Paths.get(resource.getURI()));
        String publicKeyPEM = new String(keyBytes, StandardCharsets.UTF_8)
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decodedKey = Base64.getDecoder().decode(publicKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(new X509EncodedKeySpec(decodedKey));
    }
}