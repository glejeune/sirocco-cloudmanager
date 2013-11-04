package org.ow2.sirocco.cloudmanager.itests;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.After;
import org.junit.Before;
import org.ow2.sirocco.cloudmanager.core.api.remote.IRemoteCloudProviderManager;
import org.ow2.sirocco.cloudmanager.core.api.remote.IRemoteCredentialsManager;
import org.ow2.sirocco.cloudmanager.core.api.remote.IRemoteDatabaseManager;
import org.ow2.sirocco.cloudmanager.core.api.remote.IRemoteJobManager;
import org.ow2.sirocco.cloudmanager.core.api.remote.IRemoteMachineImageManager;
import org.ow2.sirocco.cloudmanager.core.api.remote.IRemoteMachineManager;
import org.ow2.sirocco.cloudmanager.core.api.remote.IRemoteNetworkManager;
import org.ow2.sirocco.cloudmanager.core.api.remote.IRemoteSystemManager;
import org.ow2.sirocco.cloudmanager.core.api.remote.IRemoteTenantManager;
import org.ow2.sirocco.cloudmanager.core.api.remote.IRemoteUserManager;
import org.ow2.sirocco.cloudmanager.core.api.remote.IRemoteVolumeManager;
import org.ow2.sirocco.cloudmanager.model.cimi.Job;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.CloudProvider;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.CloudProviderAccount;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.Tenant;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.User;

public class AbstractTestBase {
    private static final String USER_NAME = "ANONYMOUS";

    private static final String CLOUD_PROVIDER_TYPE = "mock";

    private static final String ACCOUNT_LOGIN = "ignored";

    private static final String ACCOUNT_CREDENTIALS = "ignored";

    /**
     * Timeout (in seconds) for Sirocco to initialize.
     */
    protected static final int INITIALIZE_TIMEOUT = 180;

    protected static final int ASYNC_OPERATION_WAIT_TIME_IN_SECONDS = 1300;

    protected IRemoteMachineManager machineManager;

    protected IRemoteCredentialsManager credManager;

    protected IRemoteMachineImageManager machineImageManager;

    protected IRemoteCloudProviderManager cloudProviderManager;

    protected IRemoteSystemManager systemManager;

    protected IRemoteVolumeManager volumeManager;

    protected IRemoteNetworkManager networkManager;

    protected IRemoteUserManager userManager;

    protected IRemoteTenantManager tenantManager;

    protected IRemoteJobManager jobManager;

    protected IRemoteDatabaseManager databaseManager;

    protected Tenant tenant;

    protected User user;

    protected String getJndiName(final String service) {
        String siroccoVersion = System.getProperty("sirocco.version").replace('-', '.');
        return "java:global/org.ow2.sirocco.cloudmanager.sirocco-cloudmanager-core-manager_" + siroccoVersion + "/" + service
            + "!org.ow2.sirocco.cloudmanager.core.api.IRemote" + service;
    }

    protected void connectToCloudManager() throws Exception {
        final long timeout = System.currentTimeMillis() + AbstractTestBase.INITIALIZE_TIMEOUT * 1000;
        Properties props = new Properties();
        props.setProperty("org.omg.CORBA.ORBInitialPort", System.getProperty("iiop.port", "3700"));
        while (true) {
            try {
                Context context = new InitialContext(props);
                this.machineManager = (IRemoteMachineManager) context.lookup(this.getJndiName("MachineManager"));
                this.cloudProviderManager = (IRemoteCloudProviderManager) context.lookup(this
                    .getJndiName("CloudProviderManager"));
                this.userManager = (IRemoteUserManager) context.lookup(this.getJndiName("UserManager"));
                this.tenantManager = (IRemoteTenantManager) context.lookup(this.getJndiName("TenantManager"));
                this.credManager = (IRemoteCredentialsManager) context.lookup(this.getJndiName("CredentialsManager"));
                this.machineImageManager = (IRemoteMachineImageManager) context.lookup(this.getJndiName("MachineImageManager"));
                this.systemManager = (IRemoteSystemManager) context.lookup(this.getJndiName("SystemManager"));
                this.volumeManager = (IRemoteVolumeManager) context.lookup(this.getJndiName("VolumeManager"));
                this.networkManager = (IRemoteNetworkManager) context.lookup(this.getJndiName("NetworkManager"));
                this.jobManager = (IRemoteJobManager) context.lookup(this.getJndiName("JobManager"));
                this.databaseManager = (IRemoteDatabaseManager) context.lookup(this.getJndiName("DatabaseManager"));
                this.userManager.getUsers();
                break;
            } catch (Exception e) {
                if (System.currentTimeMillis() > timeout) {
                    throw e;
                } else {
                    Thread.sleep(1000);
                }
            }
        }
    }

    @Before
    public void setUp() throws Exception {
        this.connectToCloudManager();
        this.tenant = new Tenant();
        this.tenant.setName("MOCK");
        this.tenant = this.tenantManager.createTenant(this.tenant);
        this.user = this.userManager.createUser("Lov", "Maps", "lov@maps.com", AbstractTestBase.USER_NAME, "232908Ivry");
        this.tenantManager.addUserToTenant(this.tenant.getId().toString(), this.user.getId().toString());
        CloudProvider provider = this.cloudProviderManager.createCloudProvider(AbstractTestBase.CLOUD_PROVIDER_TYPE, "mock");
        CloudProviderAccount account = new CloudProviderAccount();
        account.setLogin(AbstractTestBase.ACCOUNT_LOGIN);
        account.setPassword(AbstractTestBase.ACCOUNT_CREDENTIALS);
        account = this.cloudProviderManager.createCloudProviderAccount(provider.getId().toString(), account);
        this.cloudProviderManager.addCloudProviderAccountToTenant(this.tenant.getId().toString(), account.getId().toString());
    }

    @After
    public void tearDown() throws Exception {
        this.databaseManager.cleanup();
    }

    protected Job.Status waitForJobCompletion(Job job) throws Exception {
        int counter = AbstractTestBase.ASYNC_OPERATION_WAIT_TIME_IN_SECONDS;
        String jobId = job.getId().toString();
        while (true) {
            job = this.jobManager.getJobById(jobId);
            if (job.getState() != Job.Status.RUNNING) {
                break;
            }
            Thread.sleep(1000);
            if (counter-- == 0) {
                throw new Exception("Machine operation time out");
            }
        }
        return job.getState();
    }
}
