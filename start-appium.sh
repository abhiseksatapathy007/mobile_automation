#!/bin/bash

# Script to start Appium server for Appium Inspector
# This script starts Appium server with CORS enabled for browser-based Inspector

echo "Starting Appium server on http://127.0.0.1:4723..."
echo "Press Ctrl+C to stop the server"
echo ""

# Start Appium server with --allow-cors flag for Inspector browser version
appium --allow-cors

