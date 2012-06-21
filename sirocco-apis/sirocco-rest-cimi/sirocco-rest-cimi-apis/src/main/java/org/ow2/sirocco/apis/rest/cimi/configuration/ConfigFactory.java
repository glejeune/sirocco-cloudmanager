/**
 *
 * SIROCCO
 * Copyright (C) 2011 France Telecom
 * Contact: sirocco@ow2.org
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 *
 * $Id$
 *
 */
package org.ow2.sirocco.apis.rest.cimi.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ow2.sirocco.apis.rest.cimi.converter.CapacityConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.CloudEntryPointConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.CpuConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.CredentialsCollectionConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.CredentialsConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.CredentialsCreateConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.CredentialsTemplateCollectionConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.CredentialsTemplateConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.DiskConfigurationConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.FrequencyUnitConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.JobCollectionConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.JobConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.MachineCollectionConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.MachineConfigurationCollectionConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.MachineConfigurationConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.MachineConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.MachineCreateConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.MachineDiskCollectionConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.MachineDiskConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.MachineImageCollectionConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.MachineImageConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.MachineTemplateCollectionConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.MachineTemplateConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.MachineVolumeCollectionConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.MachineVolumeConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.MemoryConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.MemoryUnitConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.StorageUnitConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.VolumeCollectionConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.VolumeConfigurationCollectionConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.VolumeConfigurationConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.VolumeConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.VolumeImageCollectionConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.VolumeImageConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.VolumeTemplateCollectionConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.VolumeTemplateConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.VolumeVolumeImageCollectionConverter;
import org.ow2.sirocco.apis.rest.cimi.converter.VolumeVolumeImageConverter;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiAction;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiCapacity;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiCloudEntryPoint;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiCpu;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiCredentials;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiCredentialsCollection;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiCredentialsCreate;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiCredentialsTemplate;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiCredentialsTemplateCollection;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiDiskConfiguration;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiExchange;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiJob;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiJobCollection;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiMachine;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiMachineCollection;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiMachineConfiguration;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiMachineConfigurationCollection;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiMachineCreate;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiMachineDisk;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiMachineDiskCollection;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiMachineImage;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiMachineImageCollection;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiMachineTemplate;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiMachineTemplateCollection;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiMachineVolume;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiMachineVolumeCollection;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiMemory;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiVolume;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiVolumeCollection;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiVolumeConfiguration;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiVolumeConfigurationCollection;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiVolumeImage;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiVolumeImageCollection;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiVolumeTemplate;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiVolumeTemplateCollection;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiVolumeVolumeImage;
import org.ow2.sirocco.apis.rest.cimi.domain.CimiVolumeVolumeImageCollection;
import org.ow2.sirocco.apis.rest.cimi.domain.ExchangeType;
import org.ow2.sirocco.apis.rest.cimi.domain.FrequencyUnit;
import org.ow2.sirocco.apis.rest.cimi.domain.MemoryUnit;
import org.ow2.sirocco.apis.rest.cimi.domain.StorageUnit;
import org.ow2.sirocco.cloudmanager.model.cimi.CloudEntryPoint;
import org.ow2.sirocco.cloudmanager.model.cimi.Credentials;
import org.ow2.sirocco.cloudmanager.model.cimi.CredentialsTemplate;
import org.ow2.sirocco.cloudmanager.model.cimi.Job;
import org.ow2.sirocco.cloudmanager.model.cimi.Machine;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineConfiguration;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineImage;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default configuration factory.
 */
public class ConfigFactory {
    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigFactory.class);

    /** Associated names */
    public static final String NAMES = "names";

    /** Converter class */
    public static final String CONVERTER = "converter";

    /** Class associate to other class (Service class associate to CIMI Class) */
    public static final String ASSOCIATE_TO = "associate-to";

    /**
     * Get the config.
     * 
     * @return The config
     */
    public Config getConfig() {
        Config config = new Config();
        config.setItems(this.buildItems());
        return config;
    }

    /**
     * Build the configuration for CimiEntities.
     * 
     * @return A list of entity configs
     */
    protected List<ItemConfig> buildItems() {
        List<ItemConfig> items = this.buildExchangeItems();
        items.addAll(this.buildServiceItems());
        items.addAll(this.buildOtherItems());
        return items;
    }

    /**
     * Build the configuration for {@link CimiExchange}.
     * 
     * @return A list of items config
     */
    protected List<ItemConfig> buildExchangeItems() {
        List<ItemConfig> items = new ArrayList<ItemConfig>();
        for (ExchangeType type : ExchangeType.values()) {
            items.add(this.buildExchangeItem(type));
        }
        return items;
    }

    /**
     * Build the configuration for the given {@link ExchangeType}.
     * 
     * @return A item config
     */
    protected ItemConfig buildExchangeItem(final ExchangeType type) {
        ItemConfig item = null;
        Map<String, ExchangeType> associatedNames;

        switch (type) {

        case CloudEntryPoint:
            item = new ItemConfig(CimiCloudEntryPoint.class, ExchangeType.CloudEntryPoint);
            item.putData(ConfigFactory.CONVERTER, new CloudEntryPointConverter());
            associatedNames = new HashMap<String, ExchangeType>();
            item.putData(ConfigFactory.NAMES, associatedNames);
            associatedNames.put("credentials", ExchangeType.CredentialsCollection);
            associatedNames.put("credentialsTemplates", ExchangeType.CredentialsTemplateCollection);
            associatedNames.put("machines", ExchangeType.MachineCollection);
            associatedNames.put("machineTemplates", ExchangeType.MachineTemplateCollection);
            associatedNames.put("machineConfigs", ExchangeType.MachineConfigurationCollection);
            associatedNames.put("machineImages", ExchangeType.MachineImageCollection);
            break;

        case Credentials:
            item = new ItemConfig(CimiCredentials.class, ExchangeType.Credentials);
            item.putData(ConfigFactory.CONVERTER, new CredentialsConverter());
            break;

        case CredentialsCollection:
            item = new ItemConfig(CimiCredentialsCollection.class, ExchangeType.CredentialsCollection);
            item.putData(ConfigFactory.CONVERTER, new CredentialsCollectionConverter());
            associatedNames = new HashMap<String, ExchangeType>();
            item.putData(ConfigFactory.NAMES, associatedNames);
            associatedNames.put("credentials", ExchangeType.Credentials);
            break;

        case CredentialsCreate:
            item = new ItemConfig(CimiCredentialsCreate.class, ExchangeType.CredentialsCreate);
            item.putData(ConfigFactory.CONVERTER, new CredentialsCreateConverter());
            break;

        case CredentialsTemplate:
            item = new ItemConfig(CimiCredentialsTemplate.class, ExchangeType.CredentialsTemplate);
            item.putData(ConfigFactory.CONVERTER, new CredentialsTemplateConverter());
            break;

        case CredentialsTemplateCollection:
            item = new ItemConfig(CimiCredentialsTemplateCollection.class, ExchangeType.CredentialsTemplateCollection);
            item.putData(ConfigFactory.CONVERTER, new CredentialsTemplateCollectionConverter());
            associatedNames = new HashMap<String, ExchangeType>();
            item.putData(ConfigFactory.NAMES, associatedNames);
            associatedNames.put("credentialsTemplates", ExchangeType.CredentialsTemplate);
            break;

        case Disk:
            item = new ItemConfig(CimiMachineDisk.class, ExchangeType.Disk);
            item.putData(ConfigFactory.CONVERTER, new MachineDiskConverter());
            break;

        case DiskCollection:
            item = new ItemConfig(CimiMachineDiskCollection.class, ExchangeType.DiskCollection);
            item.putData(ConfigFactory.CONVERTER, new MachineDiskCollectionConverter());
            break;

        case Job:
            item = new ItemConfig(CimiJob.class, ExchangeType.Job);
            item.putData(ConfigFactory.CONVERTER, new JobConverter());
            break;

        case JobCollection:
            item = new ItemConfig(CimiJobCollection.class, ExchangeType.JobCollection);
            item.putData(ConfigFactory.CONVERTER, new JobCollectionConverter());
            associatedNames = new HashMap<String, ExchangeType>();
            item.putData(ConfigFactory.NAMES, associatedNames);
            associatedNames.put("jobs", ExchangeType.Job);
            break;

        case Machine:
            item = new ItemConfig(CimiMachine.class, ExchangeType.Machine);
            item.putData(ConfigFactory.CONVERTER, new MachineConverter());
            break;

        case MachineAction:
            item = new ItemConfig(CimiAction.class, ExchangeType.MachineAction);
            break;

        case MachineCollection:
            item = new ItemConfig(CimiMachineCollection.class, ExchangeType.MachineCollection);
            item.putData(ConfigFactory.CONVERTER, new MachineCollectionConverter());
            associatedNames = new HashMap<String, ExchangeType>();
            item.putData(ConfigFactory.NAMES, associatedNames);
            associatedNames.put("machines", ExchangeType.Machine);
            break;

        case MachineConfiguration:
            item = new ItemConfig(CimiMachineConfiguration.class, ExchangeType.MachineConfiguration);
            item.putData(ConfigFactory.CONVERTER, new MachineConfigurationConverter());
            break;

        case MachineConfigurationCollection:
            item = new ItemConfig(CimiMachineConfigurationCollection.class, ExchangeType.MachineConfigurationCollection);
            item.putData(ConfigFactory.CONVERTER, new MachineConfigurationCollectionConverter());
            associatedNames = new HashMap<String, ExchangeType>();
            item.putData(ConfigFactory.NAMES, associatedNames);
            associatedNames.put("machineConfigurations", ExchangeType.MachineConfiguration);
            break;

        case MachineCreate:
            item = new ItemConfig(CimiMachineCreate.class, ExchangeType.MachineCreate);
            item.putData(ConfigFactory.CONVERTER, new MachineCreateConverter());
            break;

        case MachineImage:
            item = new ItemConfig(CimiMachineImage.class, ExchangeType.MachineImage);
            item.putData(ConfigFactory.CONVERTER, new MachineImageConverter());
            break;

        case MachineImageCollection:
            item = new ItemConfig(CimiMachineImageCollection.class, ExchangeType.MachineImageCollection);
            item.putData(ConfigFactory.CONVERTER, new MachineImageCollectionConverter());
            associatedNames = new HashMap<String, ExchangeType>();
            item.putData(ConfigFactory.NAMES, associatedNames);
            associatedNames.put("machineImages", ExchangeType.MachineImage);
            break;

        case MachineTemplate:
            item = new ItemConfig(CimiMachineTemplate.class, ExchangeType.MachineTemplate);
            item.putData(ConfigFactory.CONVERTER, new MachineTemplateConverter());
            break;

        case MachineTemplateCollection:
            item = new ItemConfig(CimiMachineTemplateCollection.class, ExchangeType.MachineTemplateCollection);
            item.putData(ConfigFactory.CONVERTER, new MachineTemplateCollectionConverter());
            associatedNames = new HashMap<String, ExchangeType>();
            item.putData(ConfigFactory.NAMES, associatedNames);
            associatedNames.put("machineTemplates", ExchangeType.MachineTemplate);
            break;

        case MachineVolume:
            item = new ItemConfig(CimiMachineVolume.class, ExchangeType.MachineVolume);
            item.putData(ConfigFactory.CONVERTER, new MachineVolumeConverter());
            break;

        case MachineVolumeCollection:
            item = new ItemConfig(CimiMachineVolumeCollection.class, ExchangeType.MachineVolumeCollection);
            item.putData(ConfigFactory.CONVERTER, new MachineVolumeCollectionConverter());
            break;

        case Volume:
            item = new ItemConfig(CimiVolume.class, ExchangeType.Volume);
            item.putData(ConfigFactory.CONVERTER, new VolumeConverter());
            break;

        case VolumeCollection:
            item = new ItemConfig(CimiVolumeCollection.class, ExchangeType.VolumeCollection);
            item.putData(ConfigFactory.CONVERTER, new VolumeCollectionConverter());
            break;

        case VolumeConfiguration:
            item = new ItemConfig(CimiVolumeConfiguration.class, ExchangeType.VolumeConfiguration);
            item.putData(ConfigFactory.CONVERTER, new VolumeConfigurationConverter());
            break;

        case VolumeConfigurationCollection:
            item = new ItemConfig(CimiVolumeConfigurationCollection.class, ExchangeType.VolumeConfigurationCollection);
            item.putData(ConfigFactory.CONVERTER, new VolumeConfigurationCollectionConverter());
            break;

        case VolumeImage:
            item = new ItemConfig(CimiVolumeImage.class, ExchangeType.VolumeImage);
            item.putData(ConfigFactory.CONVERTER, new VolumeImageConverter());
            break;

        case VolumeImageCollection:
            item = new ItemConfig(CimiVolumeImageCollection.class, ExchangeType.VolumeImageCollection);
            item.putData(ConfigFactory.CONVERTER, new VolumeImageCollectionConverter());
            break;

        case VolumeTemplate:
            item = new ItemConfig(CimiVolumeTemplate.class, ExchangeType.VolumeTemplate);
            item.putData(ConfigFactory.CONVERTER, new VolumeTemplateConverter());
            break;

        case VolumeTemplateCollection:
            item = new ItemConfig(CimiVolumeTemplateCollection.class, ExchangeType.VolumeTemplateCollection);
            item.putData(ConfigFactory.CONVERTER, new VolumeTemplateCollectionConverter());
            break;
        case VolumeVolumeImage:
            item = new ItemConfig(CimiVolumeVolumeImage.class, ExchangeType.VolumeVolumeImage);
            item.putData(ConfigFactory.CONVERTER, new VolumeVolumeImageConverter());
            break;

        case VolumeVolumeImageCollection:
            item = new ItemConfig(CimiVolumeVolumeImageCollection.class, ExchangeType.VolumeVolumeImageCollection);
            item.putData(ConfigFactory.CONVERTER, new VolumeVolumeImageCollectionConverter());
            break;

        default:
            ConfigFactory.LOGGER.error("Configuration not found : {}", type);
            throw new ConfigurationException("Configuration not found : " + type);
        }
        return item;
    }

    /**
     * Build the configuration for service classes.
     * 
     * @return A list of config items
     */
    // TODO Complete with all service resources
    protected List<ItemConfig> buildServiceItems() {
        ItemConfig item;
        List<ItemConfig> items = new ArrayList<ItemConfig>();

        item = new ItemConfig(CloudEntryPoint.class);
        item.putData(ConfigFactory.ASSOCIATE_TO, CimiCloudEntryPoint.class);
        items.add(item);

        item = new ItemConfig(Credentials.class);
        item.putData(ConfigFactory.ASSOCIATE_TO, CimiCredentials.class);
        items.add(item);

        item = new ItemConfig(CredentialsTemplate.class);
        item.putData(ConfigFactory.ASSOCIATE_TO, CimiCredentialsTemplate.class);
        items.add(item);

        item = new ItemConfig(Job.class);
        item.putData(ConfigFactory.ASSOCIATE_TO, CimiJob.class);
        items.add(item);

        item = new ItemConfig(Machine.class);
        item.putData(ConfigFactory.ASSOCIATE_TO, CimiMachine.class);
        items.add(item);

        item = new ItemConfig(MachineTemplate.class);
        item.putData(ConfigFactory.ASSOCIATE_TO, CimiMachineTemplate.class);
        items.add(item);

        item = new ItemConfig(MachineConfiguration.class);
        item.putData(ConfigFactory.ASSOCIATE_TO, CimiMachineConfiguration.class);
        items.add(item);

        item = new ItemConfig(MachineImage.class);
        item.putData(ConfigFactory.ASSOCIATE_TO, CimiMachineImage.class);
        items.add(item);

        return items;
    }

    /**
     * Build the configuration for other classes.
     * 
     * @return A list of config items
     */
    protected List<ItemConfig> buildOtherItems() {
        ItemConfig item;
        List<ItemConfig> items = new ArrayList<ItemConfig>();

        item = new ItemConfig(CimiCapacity.class);
        item.putData(ConfigFactory.CONVERTER, new CapacityConverter());
        items.add(item);

        item = new ItemConfig(CimiCpu.class);
        item.putData(ConfigFactory.CONVERTER, new CpuConverter());
        items.add(item);

        item = new ItemConfig(CimiDiskConfiguration.class);
        item.putData(ConfigFactory.CONVERTER, new DiskConfigurationConverter());
        items.add(item);

        item = new ItemConfig(CimiMemory.class);
        item.putData(ConfigFactory.CONVERTER, new MemoryConverter());
        items.add(item);

        item = new ItemConfig(FrequencyUnit.class);
        item.putData(ConfigFactory.CONVERTER, new FrequencyUnitConverter());
        items.add(item);

        item = new ItemConfig(MemoryUnit.class);
        item.putData(ConfigFactory.CONVERTER, new MemoryUnitConverter());
        items.add(item);

        item = new ItemConfig(StorageUnit.class);
        item.putData(ConfigFactory.CONVERTER, new StorageUnitConverter());
        items.add(item);

        return items;
    }

}