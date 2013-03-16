package nl24.integration;

import nl24.domain.Claim;

public abstract class SendClaimHandler {
	public abstract void send(Claim claim) throws IntegrationException;
}
