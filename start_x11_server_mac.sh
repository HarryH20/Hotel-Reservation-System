#!/bin/bash

# Download xQuartz for macOS users
if [ "$(uname)" == "Darwin" ]; then
    # Check if Homebrew is installed
    if ! command -v brew &> /dev/null; then
        echo "Homebrew is not installed. Attempting to install..."
        # Install Homebrew
        /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
    fi

    # Check if XQuartz is installed
    if ! brew list --cask | grep -q xquartz; then
        echo "XQuartz is not installed. Attempting to install..."
        # Install XQuartz using Homebrew
        brew install --cask xquartz
    fi

    # Start XQuartz if it's not already running
    if ! pgrep -x "XQuartz" > /dev/null; then
        open -a XQuartz
    fi
    echo "XQuartz is installed and running."
fi

