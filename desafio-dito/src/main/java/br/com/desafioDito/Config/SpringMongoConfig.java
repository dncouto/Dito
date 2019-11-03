package br.com.desafioDito.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Configuration
@EnableMongoRepositories(basePackages = { "br.com.desafioDito.Repository.Intf" })
public class SpringMongoConfig extends AbstractMongoConfiguration {

    @Autowired
    private Environment env;

    @Autowired MongoDbFactory mongoDbFactory;
    @Autowired MongoMappingContext mongoMappingContext;

    @Bean
    public MappingMongoConverter mappingMongoConverter() {

        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        
        return converter;
    }
    
    @Override
    public MongoClient mongoClient() {
        ServerAddress host = new ServerAddress(getPropertyValueString("mongo.host"), Integer.parseInt(getPropertyValueString("mongo.port")));
        MongoClientOptions options = MongoClientOptions.builder().build();
        if (getPropertyValueString("mongo.userName") != null && getPropertyValueString("mongo.passWord") != null) {
            MongoCredential credentialAcess = MongoCredential.createCredential(getPropertyValueString("mongo.userName"), 
                    getDatabaseName(), getPropertyValueString("mongo.passWord").toCharArray());
            
            return new MongoClient(host, credentialAcess, options);
        }
        return new MongoClient(host, options);
    }

    @Override
    protected String getDatabaseName() {
        return getPropertyValueString("mongo.database");
    }
    
    protected String getPropertyValueString(String key) {
        String value = env.getProperty(key);
        return value == null || value.equals("null") ? null : value;
    }
    
    protected boolean ignorePorperty(String key) {
        String value = getPropertyValueString(key);
        return value != null && value.equals("[IGNORE]");
    }
    
    protected Integer getPropertyValueInteger(String key) {
        String value = getPropertyValueString(key);
        return value == null ? null : Integer.valueOf(value);
    }
}