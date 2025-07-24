package com.mcpclient.mcpclient.service;

import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class McpService {

    private final McpSyncClient atlassianMcpClient;

    public McpService(McpSyncClient atlassianMcpClient) {
        this.atlassianMcpClient = atlassianMcpClient;
    }

    /**
     * Initialize the MCP client connection
     */
    public CompletableFuture<McpSchema.InitializeResult> initialize() {
        return CompletableFuture.supplyAsync(() -> atlassianMcpClient.initialize());
    }

    /**
     * Get server capabilities
     */
    public CompletableFuture<McpSchema.ServerCapabilities> getServerCapabilities() {
        return CompletableFuture.supplyAsync(() -> atlassianMcpClient.getServerCapabilities());
    }

    /**
     * List available tools from the Atlassian MCP server
     */
    public CompletableFuture<List<McpSchema.Tool>> listTools() {
        return CompletableFuture.supplyAsync(() -> {
            McpSchema.ListToolsResult result = atlassianMcpClient.listTools();
            return result.tools();
        });
    }

    /**
     * Execute a tool with the given name and arguments
     */
    public CompletableFuture<McpSchema.CallToolResult> executeTool(String toolName, Map<String, Object> arguments) {
        return CompletableFuture.supplyAsync(() -> {
            McpSchema.CallToolRequest request = new McpSchema.CallToolRequest(toolName, arguments);
            return atlassianMcpClient.callTool(request);
        });
    }

    /**
     * List available resources from the Atlassian MCP server
     */
    public CompletableFuture<List<McpSchema.Resource>> listResources() {
        return CompletableFuture.supplyAsync(() -> {
            McpSchema.ListResourcesResult result = atlassianMcpClient.listResources();
            return result.resources();
        });
    }

    /**
     * Read a specific resource by URI
     */
    public CompletableFuture<McpSchema.ReadResourceResult> readResource(String uri) {
        return CompletableFuture.supplyAsync(() -> {
            McpSchema.ReadResourceRequest request = new McpSchema.ReadResourceRequest(uri);
            return atlassianMcpClient.readResource(request);
        });
    }

    /**
     * List available prompts from the Atlassian MCP server
     */
    public CompletableFuture<List<McpSchema.Prompt>> listPrompts() {
        return CompletableFuture.supplyAsync(() -> {
            McpSchema.ListPromptsResult result = atlassianMcpClient.listPrompts();
            return result.prompts();
        });
    }

    /**
     * Get a specific prompt with arguments
     */
    public CompletableFuture<McpSchema.GetPromptResult> getPrompt(String name, Map<String, Object> arguments) {
        return CompletableFuture.supplyAsync(() -> {
            McpSchema.GetPromptRequest request = new McpSchema.GetPromptRequest(name, arguments);
            return atlassianMcpClient.getPrompt(request);
        });
    }

    /**
     * Check if the client is initialized and ready
     */
    public CompletableFuture<Boolean> isReady() {
        return CompletableFuture.supplyAsync(() -> atlassianMcpClient.isInitialized());
    }

    /**
     * Get server information
     */
    public CompletableFuture<McpSchema.Implementation> getServerInfo() {
        return CompletableFuture.supplyAsync(() -> atlassianMcpClient.getServerInfo());
    }

    /**
     * Get server instructions
     */
    public CompletableFuture<String> getServerInstructions() {
        return CompletableFuture.supplyAsync(() -> atlassianMcpClient.getServerInstructions());
    }

    /**
     * Ping the server to check connectivity
     */
    public CompletableFuture<Object> ping() {
        return CompletableFuture.supplyAsync(() -> atlassianMcpClient.ping());
    }

    /**
     * Close the client connection gracefully
     */
    public CompletableFuture<Boolean> closeGracefully() {
        return CompletableFuture.supplyAsync(() -> atlassianMcpClient.closeGracefully());
    }
}
