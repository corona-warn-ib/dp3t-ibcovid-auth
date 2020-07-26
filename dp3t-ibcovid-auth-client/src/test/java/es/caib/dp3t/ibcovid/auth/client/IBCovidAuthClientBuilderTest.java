package es.caib.dp3t.ibcovid.auth.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class IBCovidAuthClientBuilderTest {

    @Test
    public void givenClientBuilder_whenBuilding_thenReturnAClient() {
        // Given
        final IBCovidAuthClientBuilder builder = new IBCovidAuthClientBuilder();

        // When
        final IBCovidAuthClient client = builder.build("http://localhost:8080");

        // Then
        assertNotNull(client);
    }


}
