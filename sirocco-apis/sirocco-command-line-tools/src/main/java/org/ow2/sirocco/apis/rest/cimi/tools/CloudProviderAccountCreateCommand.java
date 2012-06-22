package org.ow2.sirocco.apis.rest.cimi.tools;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.nocrala.tools.texttablefmt.Table;
import org.ow2.sirocco.apis.rest.cimi.sdk.CimiClient;
import org.ow2.sirocco.cloudmanager.core.api.ICloudProviderManager;
import org.ow2.sirocco.cloudmanager.core.api.IRemoteCloudProviderManager;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.CloudProvider;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.CloudProviderAccount;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "create cloud provider account")
public class CloudProviderAccountCreateCommand implements Command {
    public static String COMMAND_NAME = "cloud-provider-account-create";

    @Parameter(names = "-login", description = "login", required = true)
    private String login;

    @Parameter(names = "-password", description = "password", required = true)
    private String password;

    @Parameter(names = "-provider", description = "provider id", required = true)
    private String providerId;

    @Override
    public String getName() {
        return CloudProviderAccountCreateCommand.COMMAND_NAME;
    }

    @Override
    public void execute(final CimiClient cimiClient) throws Exception {
        Context context = new InitialContext();
        IRemoteCloudProviderManager cloudProviderManager = (IRemoteCloudProviderManager) context
            .lookup(ICloudProviderManager.EJB_JNDI_NAME);

        CloudProviderAccount account = new CloudProviderAccount();
        account.setLogin(this.login);
        account.setPassword(this.password);

        CloudProvider provider = cloudProviderManager.getCloudProviderById(this.providerId);

        account.setCloudProvider(provider);

        account = cloudProviderManager.createCloudProviderAccount(account);

        Table table = new Table(4);
        table.addCell("Cloud Provider Account ID");
        table.addCell("Provider");
        table.addCell("Login");
        table.addCell("Password");

        table.addCell(account.getId().toString());
        table.addCell(account.getCloudProvider().getId().toString());
        table.addCell(account.getLogin());
        table.addCell(account.getPassword());

        System.out.println(table.render());
    }
}