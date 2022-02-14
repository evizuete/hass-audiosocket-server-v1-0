package es.voiping.hass.controller;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.LocalNodeState;
import com.github.dockerjava.api.model.SwarmNode;
import org.apache.log4j.Logger;

import java.util.List;

public class SwarmHelper {
    private static final Logger logger = Logger.getLogger("SwarmHelper");
    private DockerClient dockerClient;

    public SwarmHelper(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    public String getStatus() {
        Info info = this.dockerClient.infoCmd().exec();
        List<SwarmNode> nodes = this.dockerClient.listSwarmNodesCmd().exec();
        return info.toString();
    }
}
