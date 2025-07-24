package com.mcpclient.mcpclient.config;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.StdioClientTransport;
import io.modelcontextprotocol.client.transport.ServerParameters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.time.Duration;

@Configuration
@EnableConfigurationProperties(McpServerProperties.class)
public class McpConfiguration {

    @Bean
    public McpSyncClient atlassianMcpClient(McpServerProperties properties) {
        McpServerProperties.ServerConfig atlassianConfig = properties.getServers().get("atlassian");
        
        if (atlassianConfig == null) {
            throw new IllegalStateException("Atlassian MCP server configuration not found");
        }

        ServerParameters.Builder serverParamsBuilder = ServerParameters.builder(atlassianConfig.getCommand());
        
        if (atlassianConfig.getArguments() != null && !atlassianConfig.getArguments().isEmpty()) {
            serverParamsBuilder.args(atlassianConfig.getArguments());
        }
        
        // Note: ServerParameters doesn't support working directory directly
        // The working directory would need to be handled by the command itself
        
        ServerParameters serverParams = serverParamsBuilder.build();
        StdioClientTransport transport = new StdioClientTransport(serverParams);

        return McpClient.sync(transport)
                .requestTimeout(Duration.ofSeconds(30))
                .initializationTimeout(Duration.ofSeconds(30))
                .build();
    }
}
