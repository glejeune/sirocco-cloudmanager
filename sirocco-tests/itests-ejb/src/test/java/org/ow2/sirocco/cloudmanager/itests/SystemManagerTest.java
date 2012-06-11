package org.ow2.sirocco.cloudmanager.itests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ow2.sirocco.cloudmanager.core.api.exception.CloudProviderException;
import org.ow2.sirocco.cloudmanager.itests.util.SiroccoTester;
import org.ow2.sirocco.cloudmanager.model.cimi.ComponentDescriptor;
import org.ow2.sirocco.cloudmanager.model.cimi.ComponentDescriptor.ComponentType;
import org.ow2.sirocco.cloudmanager.model.cimi.Credentials;
import org.ow2.sirocco.cloudmanager.model.cimi.Job;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineConfiguration;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineImage;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineTemplate;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineVolumeCollection;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineVolumeTemplateCollection;
import org.ow2.sirocco.cloudmanager.model.cimi.NetworkInterface;
import org.ow2.sirocco.cloudmanager.model.cimi.SystemCreate;
import org.ow2.sirocco.cloudmanager.model.cimi.SystemTemplate;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.CloudProvider;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.CloudProviderAccount;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.CloudProviderLocation;
import org.ow2.sirocco.cloudmanager.model.cimi.extension.User;

@SuppressWarnings("unused")
public class SystemManagerTest extends SiroccoTester{


    @Before
    public void setUp() throws Exception {

        this.setUpDatabase();
        this.connectToCloudManager();
        
        // change password that is not validated by user manager
        User user = userManager.createUser("Jeanne", "Calmant", "jeanne.calmant@vieux.com", "ANONYMOUS", "titigrosminet");
        CloudProvider provider = this.cloudProviderManager.createCloudProvider("mock", "mock");
        CloudProviderAccount account = this.cloudProviderManager.createCloudProviderAccount(provider.getId().toString(),
                "ignored", "machinetest");
        this.cloudProviderManager.addCloudProviderAccountToUser(user.getId().toString(), account.getId().toString());

        
    }



    @Test
    public void testSystemManager() throws Exception {

        //User user = userManager.createUser("Jeanne", "Calmant", "jeanne.calmant@vieux.com", "jeanne.calmant", "titigrosminet");

        
        
        
       

        
        //creating machine template
        MachineTemplate machineTemplate = new MachineTemplate();        
        MachineManagerTest machineTest=new MachineManagerTest();
        
        MachineConfiguration in_c = machineTest.initMachineConfiguration();
        MachineConfiguration out_c = machineManager.createMachineConfiguration(in_c);
        
        MachineConfiguration machineConfig = out_c;        
        machineTemplate.setMachineConfiguration(machineConfig);
        
        MachineImage in_i = machineTest.initMachineImage();
        Job out_j = this.machineImageManager.createMachineImage(in_i);
        
        machineTemplate.setMachineImage((MachineImage)out_j.getTargetEntity()); 
        
        Credentials out_cr = this.credManager.createCredentials(machineTest.initCredentials());
        
        machineTemplate.setCredentials(out_cr);
        machineTemplate.setVolumes(new MachineVolumeCollection());
        machineTemplate.setVolumeTemplates(new MachineVolumeTemplateCollection());
        machineTemplate.setNetworkInterfaces(new ArrayList<NetworkInterface>());
        
        machineTemplate=machineManager.createMachineTemplate(machineTemplate);
        
        ComponentDescriptor component1=new ComponentDescriptor();
        component1.setComponentName("MaMachine");
        component1.setComponentQuantity(1);
        component1.setComponentType(ComponentType.MACHINE);
        component1.setComponentDescription("desc-comp");
        HashMap<String,Object> map1=new HashMap<String,Object>();
        map1.put("testProp", "testPropValue");
        component1.setComponentProperties(map1);
        component1.setComponentTemplate(machineTemplate.getId().toString());
        
        ComponentDescriptor component2=new ComponentDescriptor();
        component2.setComponentName("MaMachineBisque");
        component2.setComponentQuantity(1);
        component2.setComponentType(ComponentType.MACHINE);
        component2.setComponentDescription("desc-comp2");
        HashMap<String,Object> map2=new HashMap<String,Object>();
        map2.put("testProp", "testPropValue2");
        component2.setComponentProperties(map2);
        component2.setComponentTemplate(machineTemplate.getId().toString());        
        
        
        HashSet<ComponentDescriptor> componentDescriptors1=new HashSet<ComponentDescriptor>();
        
        componentDescriptors1.add(component1); 
        
        
        HashSet<ComponentDescriptor> componentDescriptors2=new HashSet<ComponentDescriptor>();
        componentDescriptors2.add(component2); 
        
        
        SystemTemplate systemTemplate1=new SystemTemplate();
        
        systemTemplate1.setDescription("descr-st1");
        systemTemplate1.setName("systemTemplateTest1");
        systemTemplate1.setComponentDescriptors(componentDescriptors1);
        
        SystemTemplate systemTemplate2=new SystemTemplate();
        
        systemTemplate2.setDescription("descr-st2");
        systemTemplate2.setName("systemTemplateTest2");
        systemTemplate2.setComponentDescriptors(componentDescriptors2);
        
        
        SystemCreate systemCreate1=new SystemCreate();
        
        systemCreate1.setDescription("descr-sc1");
        systemCreate1.setName("systemTest1");
        systemCreate1.setSystemTemplate(systemTemplate1);
        
        systemTemplate2=systemManager.createSystemTemplate(systemTemplate2);
        
        ComponentDescriptor component3=new ComponentDescriptor();
        component3.setComponentName("MonSystemeBisque");
        component3.setComponentQuantity(1);
        component3.setComponentType(ComponentType.SYSTEM);
        component3.setComponentDescription("desc-comp3");
        HashMap<String,Object> map3=new HashMap<String,Object>();
        map3.put("testProp", "testPropValue3");
        component3.setComponentProperties(map3);
        component3.setComponentTemplate(systemTemplate2.getId().toString());
        //componentDescriptors1.add(component3);  
        
        Job j=systemManager.createSystem(systemCreate1);
        String jobId=j.getId().toString();
        String systemId=j.getTargetEntity().getId().toString();
        int counter =130;
        while (true) {
            j = this.jobManager.getJobById(jobId);
            if (j.getStatus() != Job.Status.RUNNING) {
                break;
            }
            Thread.sleep(1000);
            if (counter-- == 0) {
                throw new Exception("system operation time out");
            }
        }
        
        j=systemManager.startSystem(systemId);
        jobId=j.getId().toString();
        counter =130;
        while (true) {
            j = this.jobManager.getJobById(jobId);
            if (j.getStatus() != Job.Status.RUNNING) {
                break;
            }
            Thread.sleep(1000);
            if (counter-- == 0) {
                throw new Exception("system operation time out");
            }
        }  
        
        j=systemManager.stopSystem(systemId);
        jobId=j.getId().toString();
        counter =130;
        while (true) {
            j = this.jobManager.getJobById(jobId);
            if (j.getStatus() != Job.Status.RUNNING) {
                break;
            }
            Thread.sleep(1000);
            if (counter-- == 0) {
                throw new Exception("system operation time out");
            }
        } 
        
        j=systemManager.deleteSystem(systemId);
        jobId=j.getId().toString();
        counter =130;
        while (true) {
            j = this.jobManager.getJobById(jobId);
            if (j.getStatus() != Job.Status.RUNNING) {
                break;
            }
            Thread.sleep(1000);
            if (counter-- == 0) {
                throw new Exception("system operation time out");
            }
        }       
        
        
    }

    class bob extends Thread{
        Job jo;
        
        bob(Job j)
        {
            jo=j;
        }
        
        public void run()
        {
            
            String lockedID="";
            try {System.out.println("start thread");
                lockedID=jobManager.lock(jo.getId().toString());
                System.out.println("1-job "+jo.getId().toString()+" locked with ID "+lockedID+"!");
                jo=jobManager.getJobById(jo.getId().toString());
                System.out.println("1-job date "+jo.getLockedTime());
            } catch (Exception e) {
                
                System.out.println("1-job "+jo.getId().toString()+" not locked!");
                System.out.println("1-Exception "+e.getClass().getName());
            }
            
           /* try {
                jobManager.unLock(jo.getId().toString(),"fdfd");
                System.out.println("1-job "+jo.getId().toString()+" unlocked!");
                } catch (Exception e) {
                    
                    System.out.println("1-job "+jo.getId().toString()+" not unlocked!");
                    System.out.println("1-Exception "+e.getClass().getName());
                }*/
            
        }
        
    }
    //@Tvcvest
    public void testJob() throws Exception {

        System.out.println("start");
        
        Job j=jobManager.createJob(null, "bob", null);
        
        new bob(j).start();
        Thread.sleep(12000);
        try {
        jobManager.unlock(j.getId().toString(),"fsdfsdfds");
        } catch (Exception e) {
            
            System.out.println("2-job "+j.getId().toString()+" not unlocked!");
            System.out.println("2-Exception "+e.getClass().getName());
        }
        String lockedID="";
        
        try {
            lockedID=jobManager.lock(j.getId().toString());
            j=jobManager.getJobById(j.getId().toString());
            System.out.println("2-job "+j.getId().toString()+" locked with ID "+lockedID+"!and date "+j.getLockedTime());
        } catch (Exception e) {
            
            System.out.println("2-job "+j.getId().toString()+" not locked!");
            System.out.println("2-Exception "+e.getClass().getName());
        }
        Thread.sleep(16000);
        

        //jobManager.sendJobNotification(j.getId().toString(),5000L);

        // Job parent=jm.createJob("pere", "http://",null);
        // parent.setTargetEntity(parent.getTargetEntity()+"-"+parent.getId().toString());
        // parent=jm.updateJob(parent);
        // System.out.println("created job "+parent.getTargetEntity());
        // String parentId=parent.getId().toString();
        // Job fiston=jm.createJob("fils", "http://", parentId);
        // fiston.setTargetEntity(fiston.getTargetEntity()+"-"+fiston.getId().toString());
        // fiston=jm.updateJob(fiston);
        // System.out.println("created job "+fiston.getTargetEntity());

        // parent=jm.getJobById(parentId);

        // java.util.List l=parent.getNestedJobs();
        // System.out.println("list nested: "+((Job)l.get(0)).getTargetEntity());
    }

    @After
    public void tearDown() throws Exception {
    }

}
