package dasturlash.uz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

@Configuration //Bean contaninerga bu yerdagi methodlarniyam qo'sh
@EnableWebSecurity //web security bor
public class SpringConfig {

    @Bean
    public AuthenticationProvider authenticationProvider() {
        // authentication - Foydalanuvchining identifikatsiya qilish.
        // Ya'ni berilgan login va parolli user bor yoki yo'qligini aniqlash.
        String pass = UUID.randomUUID().toString(), pass2=""; //p1
        pass="admin"; //p2
        pass2="admin"; //p2
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        pass2 = bc.encode("123456"); //p3
        System.out.println("user pass mazgi: "+pass);
        System.out.println("admin pass mazgi: "+pass2);

        UserDetails user = User.builder()
                .username("user")
                .password("{noop}"+pass)//pass1
                .roles("USER")
                .build(); //ecurity ga defoult user ni ruchnoy yarattik

        final DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(); //<- shu obyekt orqali
        daoAuthenticationProvider.setUserDetailsService(new InMemoryUserDetailsManager(user)); //va InM.. xotiraga joylab qoydik /baza/file bolMumkun
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // authorization - Foydalanuvchining tizimdagi huquqlarini tekshirish.
        // Ya'ni foydalanuvchi murojat qilayotgan API-larni ishlatishga ruxsati bor yoki yo'qligini tekshirishdir.
        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
            authorizationManagerRequestMatcherRegistry
//                    .requestMatchers("/task").permitAll() //murojat qlyotkan url /task mos bolsa, permitAll-ruxsat | authenticated qilmaydi m:/task
                    .requestMatchers(HttpMethod.GET,"/task", "/task/*").permitAll() //murojat qlyotkan url /task mos bolsa va qandaydir qiymati bolsa va GET type bolsa, permitAll-ruxsat | authenticated qilmaydi m:/task/, /task/qqq-aaaa-zzzz-xxx, /task/activ
                    .requestMatchers("/task/finished/all").hasRole("ADMIN") //murojat qlyotkan url faqatgina ADMIN ga ruxsat
                    .anyRequest() //krb kelyotgan barcha requestlarni
                    .authenticated();    //avtorizatsiyadan otkaz
        });

        http.httpBasic(Customizer.withDefaults()); //login age ni o'rniga avtorizatsiya uchun ishlaydi

        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);

        return http.build(); //shu methodni obyektni build qlb yubor
    }
}
