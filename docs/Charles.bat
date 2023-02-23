@echo off
title Charles Firewall Bypasser

openfiles > NUL 2>&1 
if not %ERRORLEVEL% == 0 (
  powershell Start-Process -FilePath %~f0 -Verb RunAs -ErrorAction Stop
  exit
)

echo 受信規制を許可します...
netsh advfirewall firewall add rule name="Charles" dir=in action=allow program="%PROGRAMFILES%\Charles\Charles.exe"
echo 送信規制を許可します...
netsh advfirewall firewall add rule name="Charles" dir=out action=allow program="%PROGRAMFILES%\Charles\Charles.exe"

echo 完了しました
timeout /t 10
exit
