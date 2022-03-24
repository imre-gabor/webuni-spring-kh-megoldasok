package hu.webuni.gateway.filter;

import java.net.URI;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import hu.webuni.tokenlib.JwtAuthFilter;
import hu.webuni.tokenlib.JwtService;
import reactor.core.publisher.Mono;

@Component
public class JwtCheckingFilter implements GlobalFilter{

	@Autowired
	JwtService jwtService;
	
	private PathPattern loginPathPAttern = PathPatternParser.defaultInstance.parse("/users/login");
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		if(isLoginRequest(exchange))
			return chain.filter(exchange);
		
		List<String> authHEaders = exchange.getRequest().getHeaders().get("Authorization");
		if(ObjectUtils.isEmpty(authHEaders)) {
			send401Response(exchange);
		}else {
			String authHeader = authHEaders.get(0);
			UsernamePasswordAuthenticationToken userDetails = null;
			try {
				userDetails = JwtAuthFilter.createUserDetailsFromAuthHeader(authHeader, jwtService);
		
			}catch (Exception e) {
				e.printStackTrace();
			}
			if(userDetails == null)
				send401Response(exchange);
		}
		
		return chain.filter(exchange);
	}

	private void send401Response(ServerWebExchange exchange) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(HttpStatus.UNAUTHORIZED);
		response.setComplete();
	}

	private boolean isLoginRequest(ServerWebExchange exchange) {
		Set<URI> origUrls = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
		
		URI originalUri = origUrls.iterator().next();
		return loginPathPAttern.matches(PathContainer.parsePath(originalUri.toString()).subPath(4));
		
	}

}
