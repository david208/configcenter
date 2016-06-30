package com.yizhenmoney.damocles.configcenter.config;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import org.apache.curator.framework.api.transaction.CuratorTransactionFinal;
import org.apache.zookeeper.data.ACL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.yizhenmoney.damocles.configcenter.service.server.PropertiesServerInter;
import com.yizhenmoney.damocles.configcenter.service.server.PropertiesServerService;
import com.yizhenmoney.damocles.configcenter.vo.EnvInfo;
import com.yizhenmoney.damocles.configcenter.vo.PropertyInfo;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class WebSecureConfig extends WebSecurityConfigurerAdapter {
	@Value("${ldap.group}")
	private String ldapGroup;

	@Value("${ldap.url}")
	private String ldapUrl;

	@Bean
	public PropertiesServerInter propertiesServerService() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return new PropertiesServerService("admin:123456", "192.168.220.194:2181");

	}

	@Bean
	public Md5PasswordEncoder passwordEncoder() {
		return new Md5PasswordEncoder();
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandlerImpl() {
		AccessDeniedHandlerImpl accessDeniedHandler = new AccessDeniedHandlerImpl();
		accessDeniedHandler.setErrorPage("/login");
		return accessDeniedHandler;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(WebSecureConfig.class);

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.headers().frameOptions().disable();
		http.authorizeRequests().antMatchers("/static/**").permitAll().anyRequest().hasAuthority(ldapGroup).and()
				.formLogin().loginPage("/login").defaultSuccessUrl("/", true).failureUrl("/login?error").permitAll();
		http.exceptionHandling().accessDeniedHandler(accessDeniedHandlerImpl());
		http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.ldapAuthentication().passwordEncoder(new PasswordEncoder() {

			@Override
			public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
				return true;
			}

			@Override
			public String encodePassword(String rawPass, Object salt) {
				MessageDigest digest;
				try {
					digest = MessageDigest.getInstance("MD5");
					digest.update(rawPass.getBytes("UTF8"));
					String md5Password = new String(Base64.encode(digest.digest()));
					return "{MD5}" + md5Password;
				} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
					LOGGER.error("LDAP错误", e);
				}
				return "";
			}
		}).userSearchFilter("(cn={0})").userSearchBase("dc=yizhen,dc=com").groupSearchBase("dc=yizhen,dc=com")
				.groupSearchFilter("(memberUid={0})").groupRoleAttribute("cn").contextSource().url(ldapUrl)
				.managerDn("cn=admin,dc=yizhen,dc=com").managerPassword("123456");
		;
	}

}
