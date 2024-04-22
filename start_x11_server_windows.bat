@echo off

REM Check if vcxsrv is installed
echo Checking if VcXsrv is installed...
IF NOT EXIST "C:\Program Files\VcXsrv" (
    REM If not installed, download and install VcXsrv
    echo VcXsrv is not installed. Downloading and installing...
    powershell -Command "& { iwr -Uri 'https://sourceforge.net/projects/vcxsrv/files/latest/download' -OutFile 'C:\vcxsrv.exe' }"
    echo VcXsrv downloaded. Installing...
    start /wait C:\vcxsrv.exe /S
    echo VcXsrv installed successfully.
) else (
    echo VcXsrv is already installed.
)

REM Start VcXsrv
echo Starting VcXsrv...
start "" "C:\Program Files\VcXsrv\vcxsrv.exe" :0 -ac -terminate -lesspointer -multiwindow -clipboard -wgl -dpi auto -screen 0 @1
echo VcXsrv started successfully.
