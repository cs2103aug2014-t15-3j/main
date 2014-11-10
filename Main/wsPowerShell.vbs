set WshShell = WScript.CreateObject("WScript.Shell")
strDesktop = WshShell.SpecialFolders("Desktop")
set oShellLink = WshShell.CreateShortcut(strDesktop & "\Remembra.lnk")
oShellLink.TargetPath = "RememebraV0.5.jar"
oShellLink.Hotkey = "CTRL+SHIFT+R"
oShellLink.Save