package com.mcpclient.mcpclient.controller;

import com.mcpclient.mcpclient.service.McpService;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/mcp")
@CrossOrigin(origins = "*")
public class McpController {

    private final McpService mcpService;

    public McpController(McpService mcpService) {
        this.mcpService = mcpService;
    }

    /**
     * Initialize the MCP client connection
     */
    @PostMapping("/initialize")
    public CompletableFuture<ResponseEntity<McpSchema.InitializeResult>> initialize() {
        return mcpService.initialize()
                .thenApply(ResponseEntity::ok)
                .exceptionally(throwable -> ResponseEntity.internalServerError().build());
    }

    /**
     * Get server capabilities
     */
    @GetMapping("/capabilities")
    public CompletableFuture<ResponseEntity<McpSchema.ServerCapabilities>> getServerCapabilities() {
        return mcpService.getServerCapabilities()
                .thenApply(ResponseEntity::ok)
                .exceptionally(throwable -> ResponseEntity.internalServerError().build());
    }

    /**
     * Get server information
     */
    @GetMapping("/server-info")
    public CompletableFuture<ResponseEntity<McpSchema.Implementation>> getServerInfo() {
        return mcpService.getServerInfo()
                .thenApply(ResponseEntity::ok)
                .exceptionally(throwable -> ResponseEntity.internalServerError().build());
    }

    /**
     * Get server instructions
     */
    @GetMapping("/server-instructions")
    public CompletableFuture<ResponseEntity<String>> getServerInstructions() {
        return mcpService.getServerInstructions()
                .thenApply(ResponseEntity::ok)
                .exceptionally(throwable -> ResponseEntity.internalServerError().build());
    }

    /**
     * Check if the client is ready
     */
    @GetMapping("/ready")
    public CompletableFuture<ResponseEntity<Boolean>> isReady() {
        return mcpService.isReady()
                .thenApply(ResponseEntity::ok)
                .exceptionally(throwable -> ResponseEntity.internalServerError().build());
    }

    /**
     * Ping the server
     */
    @PostMapping("/ping")
    public CompletableFuture<ResponseEntity<Object>> ping() {
        return mcpService.ping()
                .thenApply(ResponseEntity::ok)
                .exceptionally(throwable -> ResponseEntity.internalServerError().build());
    }

    // Tools endpoints
    
    /**
     * List available tools
     */
    @GetMapping("/tools")
    public CompletableFuture<ResponseEntity<List<McpSchema.Tool>>> listTools() {
        return mcpService.listTools()
                .thenApply(ResponseEntity::ok)
                .exceptionally(throwable -> ResponseEntity.internalServerError().build());
    }

    /**
     * Execute a tool
     */
    @PostMapping("/tools/{toolName}/execute")
    public CompletableFuture<ResponseEntity<McpSchema.CallToolResult>> executeTool(
            @PathVariable String toolName,
            @RequestBody Map<String, Object> arguments) {
        return mcpService.executeTool(toolName, arguments)
                .thenApply(ResponseEntity::ok)
                .exceptionally(throwable -> ResponseEntity.internalServerError().build());
    }

    // Resources endpoints
    
    /**
     * List available resources
     */
    @GetMapping("/resources")
    public CompletableFuture<ResponseEntity<List<McpSchema.Resource>>> listResources() {
        return mcpService.listResources()
                .thenApply(ResponseEntity::ok)
                .exceptionally(throwable -> ResponseEntity.internalServerError().build());
    }

    /**
     * Read a specific resource
     */
    @GetMapping("/resources/read")
    public CompletableFuture<ResponseEntity<McpSchema.ReadResourceResult>> readResource(
            @RequestParam String uri) {
        return mcpService.readResource(uri)
                .thenApply(ResponseEntity::ok)
                .exceptionally(throwable -> ResponseEntity.internalServerError().build());
    }

    // Prompts endpoints
    
    /**
     * List available prompts
     */
    @GetMapping("/prompts")
    public CompletableFuture<ResponseEntity<List<McpSchema.Prompt>>> listPrompts() {
        return mcpService.listPrompts()
                .thenApply(ResponseEntity::ok)
                .exceptionally(throwable -> ResponseEntity.internalServerError().build());
    }

    /**
     * Get a specific prompt
     */
    @PostMapping("/prompts/{promptName}")
    public CompletableFuture<ResponseEntity<McpSchema.GetPromptResult>> getPrompt(
            @PathVariable String promptName,
            @RequestBody(required = false) Map<String, Object> arguments) {
        Map<String, Object> args = arguments != null ? arguments : Map.of();
        return mcpService.getPrompt(promptName, args)
                .thenApply(ResponseEntity::ok)
                .exceptionally(throwable -> ResponseEntity.internalServerError().build());
    }

    // Management endpoints
    
    /**
     * Close the client connection gracefully
     */
    @PostMapping("/close")
    public CompletableFuture<ResponseEntity<Boolean>> closeGracefully() {
        return mcpService.closeGracefully()
                .thenApply(ResponseEntity::ok)
                .exceptionally(throwable -> ResponseEntity.internalServerError().build());
    }
}
