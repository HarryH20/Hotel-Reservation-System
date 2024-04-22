#!/bin/bash

# Check if running on a Linux system
if [ "$(uname)" == "Linux" ]; then
    # Check if X11 server (e.g., Xorg) is installed
    if ! command -v Xorg &> /dev/null; then
        echo "X11 server is not installed. Attempting to install..."

        # Install X11 server (You may need to modify the package manager and package name)
        sudo apt-get update
        sudo apt-get install -y xorg

        # Check if installation was successful
        if ! command -v Xorg &> /dev/null; then
            echo "Failed to install X11 server."
            exit 1
        fi
        echo "X11 server installed successfully."
    fi

    # Start X11 server if it's not already running
    if ! pgrep -x "Xorg" > /dev/null; then
        echo "Starting X11 server..."
        sudo systemctl start display-manager
    fi

    echo "X11 server is installed and running."
fi
