package es.voiping.hass.controller.helpers;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateNetworkCmd;
import com.github.dockerjava.api.command.CreateNetworkResponse;
import com.github.dockerjava.api.command.RemoveNetworkCmd;
import com.github.dockerjava.api.model.PruneType;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import org.apache.log4j.Logger;

public class NetworkHelper {
    private static final Logger logger = Logger.getLogger("NetworkHelper");

    private DockerClient dockerClient;
    public NetworkHelper(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    public NetworkHelper() {
        DockerClientConfig clientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("tcp://10.0.122.174:2375")
                .withDockerTlsVerify(false)
                .build();
        this.dockerClient = DockerClientBuilder.getInstance(clientConfig).build();
    }

    public String create(String driver, String name, boolean attachable) {
        try {
            CreateNetworkResponse response = this.dockerClient.createNetworkCmd()
                    .withDriver(driver)
                    .withName(name)
                    .withAttachable(attachable)
                    .exec();
            return response.getId();

        } catch (Exception e) {
            return null;
        }
    }

    public boolean delete(String networkId) {
        try {
            this.dockerClient.removeNetworkCmd(networkId).exec();
            return true;

        } catch(Exception e) {
            return false;
        }
    }

    public boolean connect(String networkId, String containerId) {
        try {
            this.dockerClient.connectToNetworkCmd()
                    .withNetworkId(networkId)
                    .withContainerId(containerId)
                    .exec();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean disconnect(String networkId, String containerId) {
        try {
            this.dockerClient.disconnectFromNetworkCmd()
                    .withNetworkId(networkId)
                    .withContainerId(containerId)
                    .exec();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean prune() {
        try {
            this.dockerClient.pruneCmd(PruneType.NETWORKS).exec();
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
