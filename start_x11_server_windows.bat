@echo off

REM Check if Xming is installed
echo Checking if Xming is installed...
IF NOT EXIST "C:\Program Files\Xming" (
    REM If not installed, download and install Xming
    echo Xming is not installed. Downloading and installing...
    powershell -Command "& { iwr -Uri 'https://sourceforge.net/projects/xming/files/latest/download' -OutFile 'C:\xming.exe' }"
    echo Xming downloaded. Installing...
    REM Xming does not require installation, so no additional steps are needed.
    echo Xming installed successfully.
) else (
    echo Xming is already installed.
)

REM Start Xming
echo Starting Xming...
start "" "C:\Program Files\Xming\Xming.exe"
echo Xming started successfully.
