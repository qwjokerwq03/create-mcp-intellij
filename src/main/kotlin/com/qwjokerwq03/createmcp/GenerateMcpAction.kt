package com.qwjokerwq03.createmcp

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.process.ProcessAdapter
import com.intellij.execution.process.ProcessEvent
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.util.Key
import java.io.File

class GenerateMcpAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return
        
        if (!virtualFile.isDirectory) return
        
        val folderPath = virtualFile.path
        val folderName = virtualFile.name

        // Run as an asynchronous background task to keep IDE UI responsive
        ProgressManager.getInstance().run(object : Task.Backgroundable(project, "Creating MCP Server...", false) {
            override fun run(indicator: ProgressIndicator) {
                indicator.isIndeterminate = true
                
                try {
                    val scriptPath = "/Users/thapan/create-mcp/check_swagger.py"
                    val commandLine = GeneralCommandLine("python3", scriptPath, "--url", folderPath)
                    commandLine.workDirectory = File(folderPath)
                    
                    val handler = OSProcessHandler(commandLine)
                    handler.addProcessListener(object : ProcessAdapter() {
                        override fun processTerminated(event: ProcessEvent) {
                            ApplicationManager.getApplication().invokeLater {
                                // Refresh Virtual File System so the generated file shows up instantly!
                                virtualFile.refresh(false, true)
                                
                                val generatedFile = File(folderPath, "generated_mcp_$folderName.py")
                                if (generatedFile.exists()) {
                                    showNotification(
                                        "Success!",
                                        "Custom Python MCP Server generated successfully at:\n${generatedFile.absolutePath}",
                                        NotificationType.INFORMATION
                                    )
                                } else {
                                    showNotification(
                                        "Scan Complete",
                                        "API analysis finished. No custom MCP server was generated.",
                                        NotificationType.WARNING
                                    )
                                }
                            }
                        }

                        override fun onTextAvailable(event: ProcessEvent, outputType: Key<*>) {
                            // Standard output/error capture if needed
                        }
                    })
                    
                    handler.startNotify()
                    handler.waitFor()
                    
                } catch (ex: Exception) {
                    ApplicationManager.getApplication().invokeLater {
                        showNotification("Error", "Failed to generate MCP Server: ${ex.message}", NotificationType.ERROR)
                    }
                }
            }
        })
    }

    override fun update(e: AnActionEvent) {
        val virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE)
        // Only visible and active when a directory/folder is selected
        e.presentation.isEnabledAndVisible = virtualFile != null && virtualFile.isDirectory
    }

    private fun showNotification(title: String, content: String, type: NotificationType) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("Compiler") // Fallback standard group
            .createNotification(title, content, type)
            .notify(null)
    }
}
