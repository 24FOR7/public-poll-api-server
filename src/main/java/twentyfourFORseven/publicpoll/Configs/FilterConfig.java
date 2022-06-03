package twentyfourFORseven.publicpoll.Configs;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import twentyfourFORseven.publicpoll.Filters.TokenFilter;

import javax.servlet.Filter;

@Configuration
public class FilterConfig {
    /**
     * https://steady-coding.tistory.com/601 참고해서 볼것
     * TokenVerifyFilter Bean에 등록
     */
    @Bean
    public FilterRegistrationBean filterRegist(){
        FilterRegistrationBean<Filter> registBean = new FilterRegistrationBean<>();
        registBean.setFilter(new TokenFilter());
        registBean.setOrder(1);
        registBean.addUrlPatterns("/*");
        return registBean;
    }
}
