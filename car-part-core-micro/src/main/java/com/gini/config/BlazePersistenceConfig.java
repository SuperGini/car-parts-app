package com.gini.config;

import com.blazebit.persistence.Criteria;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.integration.view.spring.EnableEntityViews;
import com.blazebit.persistence.spi.CriteriaBuilderConfiguration;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.spi.EntityViewConfiguration;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

@Configuration
@EnableEntityViews(basePackages = "com.gini.model.views")
public class BlazePersistenceConfig {

    //https://persistence.blazebit.com/documentation/1.6/entity-view/manual/en_US/#spring-data-features
    //https://vladmihalcea.com/blaze-persistence-multiset/
    //https://github.com/Blazebit/blaze-persistence/discussions/1410

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;


    @Bean
    public CriteriaBuilderFactory createCriteriaBuilderFactory() {
        CriteriaBuilderConfiguration config = Criteria.getDefault();
        // do some configuration
        return config.createCriteriaBuilderFactory(entityManagerFactory);
    }


//    @Bean
//    public EntityViewConfiguration entityViewConfiguration() {
//        EntityViewConfiguration entityViewConfiguration = EntityViews
//                .createDefaultConfiguration();
//        entityViewConfiguration.addEntityView(CarView.class);
//        entityViewConfiguration.addEntityView(PartView.class);
//        entityViewConfiguration.addEntityView(AftermarketPartNumberView.class);
//
//        return entityViewConfiguration;
//    }


    @Bean
    // inject the criteria builder factory which will be used along with the entity view manager
    public EntityViewManager createEntityViewManager(CriteriaBuilderFactory cbf, EntityViewConfiguration entityViewConfiguration) {
        return entityViewConfiguration.createEntityViewManager(cbf);
    }


}
