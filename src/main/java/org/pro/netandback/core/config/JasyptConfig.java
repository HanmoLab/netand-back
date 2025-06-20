package org.pro.netandback.core.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@Configuration
@EnableEncryptableProperties
public class JasyptConfig {

  @Value("${jasypt.encryptor.password}")
  private String password;

  @Bean("jasyptStringEncryptor")
  public StringEncryptor stringEncryptor() {
    PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
    SimpleStringPBEConfig config = new SimpleStringPBEConfig();
    config.setPassword(password);
    config.setPoolSize("1");
    config.setKeyObtentionIterations("1000");
    config.setAlgorithm("PBEWithMD5AndDES");
    config.setStringOutputType("base64");
    config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
    encryptor.setConfig(config);
    return encryptor;
  }
}
