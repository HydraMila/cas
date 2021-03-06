package org.apereo.cas.web.flow.resolver.impl.mfa.request;

import org.apereo.cas.CentralAuthenticationService;
import org.apereo.cas.authentication.AuthenticationServiceSelectionPlan;
import org.apereo.cas.authentication.AuthenticationSystemSupport;
import org.apereo.cas.authentication.MultifactorAuthenticationProviderSelector;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.ticket.registry.TicketRegistrySupport;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.util.CookieGenerator;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is {@link RequestParameterMultifactorAuthenticationPolicyEventResolver}
 * that attempts to resolve the next event based on the authentication providers of this service.
 *
 * @author Misagh Moayyed
 * @since 5.0.0
 */
@Slf4j
public class RequestParameterMultifactorAuthenticationPolicyEventResolver extends BaseRequestMultifactorAuthenticationPolicyEventResolver {


    private final String mfaRequestParameter;

    public RequestParameterMultifactorAuthenticationPolicyEventResolver(final AuthenticationSystemSupport authenticationSystemSupport,
                                                                        final CentralAuthenticationService centralAuthenticationService,
                                                                        final ServicesManager servicesManager,
                                                                        final TicketRegistrySupport ticketRegistrySupport,
                                                                        final CookieGenerator warnCookieGenerator,
                                                                        final AuthenticationServiceSelectionPlan authenticationStrategies,
                                                                        final MultifactorAuthenticationProviderSelector selector,
                                                                        final CasConfigurationProperties casProperties) {
        super(authenticationSystemSupport, centralAuthenticationService, servicesManager,
            ticketRegistrySupport, warnCookieGenerator, authenticationStrategies, selector);
        mfaRequestParameter = casProperties.getAuthn().getMfa().getRequestParameter();
    }

    @Override
    protected List<String> resolveEventFromHttpRequest(final HttpServletRequest request) {
        val values = request.getParameterValues(mfaRequestParameter);
        if (values != null && values.length > 0) {
            LOGGER.debug("Received request parameter [{}] as [{}]", mfaRequestParameter, values);
            return Arrays.stream(values).collect(Collectors.toList());
        }

        LOGGER.debug("No value could be found for request parameter [{}]", mfaRequestParameter);
        return null;
    }
}
