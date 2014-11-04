set WshShell = WScript.CreateObject("WScript.Shell")
strDesktop = WshShell.SpecialFolders("Desktop")
set oShellLink = WshShell.CreateShortcut(strDesktop & "\Remembra.lnk")
oShellLink.TargetPath = "C:\Remembra\Remembra.jar"
oShellLink.Hotkey = "CTRL+SHIFT+R"
oShellLink.Save