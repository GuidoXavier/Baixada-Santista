package TADS_SEM4_BACK_END.Baixada_Santista.Configuracoes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ConfigSecurity {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // 🔥 Desativa CSRF para evitar bloqueios
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/usuario/**").permitAll()  // 🔓 Libera todas as requisições
                        .anyRequest().permitAll()
                );
        return http.build();
    }
}

