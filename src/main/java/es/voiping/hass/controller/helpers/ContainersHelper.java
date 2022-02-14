package es.voiping.hass.controller.helpers;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.CreateNetworkResponse;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.google.common.collect.ImmutableSet;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContainersHelper {
    private static final Logger logger = Logger.getLogger("ContainersHelper");

    private DockerClient dockerClient;
    public ContainersHelper(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    public ContainersHelper() {
        DockerClientConfig clientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("tcp://10.0.122.174:2375")
                .withDockerTlsVerify(false)
                .build();
        this.dockerClient = DockerClientBuilder.getInstance(clientConfig).build();
    }

    public String create(String imageName, String containerName, String portStr) {
        try {
            List<ExposedPort> exposedPorts = new ArrayList<>();
            Arrays.stream(portStr.split(",")).forEach(
                port -> {
                    exposedPorts.add(ExposedPort.parse(port));
                }
            );

            HostConfig hostConfig = HostConfig.newHostConfig();
            Ports ports = new Ports();
            ports.bind(ExposedPort.parse("8080/tcp"), Ports.Binding.parse("80/tcp"));
            hostConfig.withPortBindings(ports);

            CreateContainerResponse response = this.dockerClient.createContainerCmd(imageName)
                    .withName(containerName)
                    .withHostConfig(hostConfig)
                    .exec();
            return response.getId();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean delete(String containerId) {
        try {
            this.dockerClient.removeContainerCmd(containerId).withForce(true).exec();
            return true;

        } catch(Exception e) {
            return false;
        }
    }

    public boolean start(String containerId) {
        try {
            this.dockerClient.startContainerCmd(containerId).exec();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean stop(String containerId) {
        try {
            this.dockerClient.stopContainerCmd(containerId).withTimeout(5).exec();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean restart(String containerId) {
        try {
            this.dockerClient.restartContainerCmd(containerId).exec();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean prune() {
        try {
            this.dockerClient.pruneCmd(PruneType.CONTAINERS).exec();
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
