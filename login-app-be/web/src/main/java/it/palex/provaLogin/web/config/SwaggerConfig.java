package it.palex.provaLogin.web.config;

import it.palex.provaLogin.library.rest.GenericResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springframework.util.MimeTypeUtils;


/**
 * @author Alessandro Pagliaro
 *
 */
//@Profile({"dev"}) activate always for the demo
@Configuration
public class SwaggerConfig {

	private static final String GENERIC_INTERNAL_ERROR_MESSAGE = "Internal Server Error";
	@Value("${swagger.api.title}")
	private String apiTitle;

	@Value("${swagger.api.description}")
	private String apiDescription;

	@Value("${swagger.api.version}")
	private String apiVersion;

	@Value("${swagger.api.termsOfServiceUrl}")
	private String apiTermsOfService;

	@Value("${swagger.api.contact.name}")
	private String apiContactName;

	@Value("${swagger.api.contact.site-url}")
	private String apiContactSiteUrl;

	@Value("${swagger.api.contact.site-email}")
	private String apiContactSiteEmail;

	@Value("${swagger.api.license}")
	private String apiLicence;

	@Value("${swagger.api.licenseUrl}")
	private String apiLicenceUrl;



	@Bean
    public OpenAPI openAPI() {
		Contact contact = new Contact();
		contact.setName(this.apiContactName);
		contact.setUrl(this.apiContactSiteUrl);

		License licence = new License();
		licence.setName(this.apiLicence);
		licence.setUrl(this.apiLicenceUrl);

        return new OpenAPI()
                .components(new Components())
                .info(new Info().title(this.apiTitle)
                		.description(this.apiDescription)
                		.contact(contact)
                		.license(licence)
                	);
    }
//
//	@Bean
//	public OpenApiCustomiser customOpenApiCustomiser() {
//		// add for each api Internal error 500
//		return openApi -> {
//			openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
//
//				Schema sharedErrorSchema = ModelConverters.getInstance()
//						.read(GenericResponse.class)
//						.getOrDefault("GenericResponse", new Schema());
//
//				MediaType sharedMediaType = new MediaType().schema(sharedErrorSchema);
//				Content sharedContent = new Content()
//						.addMediaType(MimeTypeUtils.APPLICATION_JSON_VALUE, sharedMediaType);
//
//				ApiResponses apiResponses = operation.getResponses();
//
//				ApiResponse response = new ApiResponse()
//						.description(GENERIC_INTERNAL_ERROR_MESSAGE)
//						.content(sharedContent);
//				apiResponses.addApiResponse("500", response);
//			}));
//		};
//	}
}