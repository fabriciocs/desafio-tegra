package br.com.tegra.config;

import java.time.Duration;

import br.com.tegra.domain.AirplaneTrip;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(br.com.tegra.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(br.com.tegra.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(br.com.tegra.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.tegra.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.tegra.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(br.com.tegra.domain.AirplaneTripImport.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.tegra.domain.Airline.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.tegra.domain.Airport.class.getName(), jcacheConfiguration);
            cm.createCache(AirplaneTrip.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry

        };
    }
}
