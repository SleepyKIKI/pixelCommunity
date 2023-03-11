import org.springframework.beans.factory.annotation.Value;

public class ValueTest {
    @Value("${github.client.id}")
    public String clientId;

    @Value("${github.client.scret}")
    public String clientScret;

    @Value("${github.redirect.uri}")
    public String redirectUri;

    @Value("${server.port}")
    public String port;
}
