package za.co.neslotech.banking.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import za.co.neslotech.banking.components.processors.CreditProcessor;
import za.co.neslotech.banking.components.processors.OverdraftProcessor;
import za.co.neslotech.banking.components.processors.SavingsProcessor;

@Configuration
public class CardLimitConfiguration {

    @Bean
    public OverdraftProcessor getOverdraftProcessor() {
        return new OverdraftProcessor();
    }

    @Bean
    public CreditProcessor getCreditProcessor() {
        return new CreditProcessor();
    }

    @Bean
    public SavingsProcessor getSavingsProcessor() {
        return new SavingsProcessor();
    }

}