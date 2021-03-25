package it.eng.hsm.utils;

import java.security.Provider;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class BouncyCastleProviderUtils {
  
  public static final String PROVIDER_NAME = "BC";

  public static void checkBouncyCastleProvider() {
    Provider provider = Security.getProvider(PROVIDER_NAME);
    if (provider == null) {
      Security.insertProviderAt(new BouncyCastleProvider(), 3);
    }
    
  }
  
  private BouncyCastleProviderUtils() {
  }

}
