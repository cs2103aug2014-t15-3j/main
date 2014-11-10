set WshShell = WScript.CreateObject("WScript.Shell")
strDesktop = WshShell.SpecialFolders("Desktop")
set oShellLink = WshShell.CreateShortcut(strDesktop & "\Remembra.lnk")
oShellLink.TargetPath = "[t15-3j][V0.5]\[t15-3j][V0.5].jar"
oShellLink.Hotkey = "CTRL+SHIFT+R"
oShellLink.Save