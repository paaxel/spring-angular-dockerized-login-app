package it.palex.provaLogin.library.utils;

import it.palex.provaLogin.library.utils.ClientIPAddressRetriever;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.context.request.ServletWebRequest;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientIpAddressRetriverTests {

    @Mock
    private ServletWebRequest mockRequest;

    @BeforeEach
    public void setupMocks(){
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        this.mockRequest = new ServletWebRequest(request);
    }

    @Test
    public void checkRetriveIpNoProxy() {
        String ip = "127.0.0.1";
        when(this.mockRequest.getRequest().getRemoteAddr()).thenReturn(ip);

        final String proxyType = ClientIPAddressRetriever.PROXY_TYPE_IGNORE_PROXY;

        String retrivedIP = ClientIPAddressRetriever.getIpAddress(this.mockRequest.getRequest(), proxyType);

        Assertions.assertEquals(ip, retrivedIP);
    }

    @Test
    public void checkRetriveIpAddressBehindCloudflareProxy(){
        String ip = "127.0.0.1";
        when(this.mockRequest.getRequest()
                .getHeader(ClientIPAddressRetriever.HTTP_HEADER_X_FORWARDED_FOR)
            ).thenReturn(ip);

        final String proxyType = ClientIPAddressRetriever.PROXY_TYPE_CLOUDFLARE;

        String retrivedIP = ClientIPAddressRetriever.getIpAddress(this.mockRequest.getRequest(), proxyType);

        Assertions.assertEquals(ip, retrivedIP);
    }

    @Test
    public void checkRetriveIpAddressBehindNGINXProxy(){
        String ip = "127.0.0.1";
        when(this.mockRequest.getRequest()
                .getHeader(ClientIPAddressRetriever.HTTP_HEADER_X_REAL_IP)
        ).thenReturn(ip);

        final String proxyType = ClientIPAddressRetriever.PROXY_TYPE_NGINX;

        String retrivedIP = ClientIPAddressRetriever.getIpAddress(this.mockRequest.getRequest(), proxyType);

        Assertions.assertEquals(ip, retrivedIP);
    }

}
