# 🔍 Create MCP IntelliJ IDEA Plugin (`create-mcp-intellij`)

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Model Context Protocol](https://img.shields.io/badge/Protocol-MCP-blue.svg)](https://modelcontextprotocol.io/)

A premium JetBrains IDE plugin (compatible with IntelliJ IDEA, PyCharm, WebStorm, etc.) that integrates the **`create-mcp`** static analysis engine directly into the project explorer context menu. 

Right-click any folder or package, click **`Create MCP Server`**, and it will automatically analyze your codebase routers and generate a fully compliant custom Python Model Context Protocol (MCP) server for you!

---

## ✨ Features

*   **Project Explorer Right-Click Menu**: Integrates directly as a primary action at the top of the Project explorer right-click context menu (`ProjectViewPopupMenu`).
*   **Asynchronous Background Tasks**: Spawns python parser processes in background threads using standard JetBrains APIs (`Task.Backgroundable`), guaranteeing the IDE UI remains perfectly fluid and responsive.
*   **Native balloon Notifications**: Alerts you of parsing success or errors using IntelliJ's native Notification manager.
*   **Virtual File System Refresh**: Programmatically forces the IntelliJ VFS to refresh so that the newly generated `generated_mcp_<folder>.py` file instantly appears in your editor view.

---

## 🛠️ Requirements & Setup

This plugin wraps your local **`create-mcp`** parsing engine.
*   Ensure **Python 3** is installed and accessible via `python3`.
*   Ensure the local script `/Users/thapan/create-mcp/check_swagger.py` exists and is executable.

---

## 💻 Development & Testing

If you want to run or modify this plugin locally:

1.  Open the `/Users/thapan/create-mcp-intellij` folder in IntelliJ IDEA.
2.  Let the IDE import the Gradle settings.
3.  Open the Gradle tool window on the right side.
4.  Navigate to Tasks -> `intellij` -> **`runIde`** and double-click to run!
5.  This will boot a sandboxed test instance of IntelliJ with the plugin pre-installed so you can test it live!

---

## 📄 License
This project is licensed under the [MIT License](LICENSE).
