str = GetDateTimeStamp()

CreateZip "Z:\BKUP\testbkup\Web_TestAutomation_FCR_"& str &".zip", "\\BDSPZAD00009250\Users\G01196618\Desktop\Automation\FCR_Automation\Web_TestAutomation_FCR"

Function GetDateTimeStamp
  Dim strNow
  strNow = Now()
  GetDateTimeStamp = Year(strNow) & Pad2(Month(strNow)) _
        & Pad2(Day(StrNow)) & Pad2(Hour(strNow)) _
        & Pad2(Minute(strNow)) & Pad2(Second(strNow))
End Function

Function Pad2(strIn)
  Do While Len(strIn) < 2
    strIn = "0" & strIn
  Loop
  Pad2 = strIn
End Function

Sub NewZip(pathToZipFile)
   'WScript.Echo "Newing up a zip file (" & pathToZipFile & ") "
   Dim fso
   Set fso = CreateObject("Scripting.FileSystemObject")
   Dim file

   Set file = fso.CreateTextFile(pathToZipFile)

   file.Write Chr(80) & Chr(75) & Chr(5) & Chr(6) & String(18, 0)

   file.Close
   Set fso = Nothing
   Set file = Nothing

   WScript.Sleep 500
End Sub

Sub CreateZip(pathToZipFile, dirToZip)
'WScript.Echo "Creating zip  (" & pathToZipFile & ") from (" & dirToZip & ")"

   Dim fso
   Set fso= Wscript.CreateObject("Scripting.FileSystemObject")

   pathToZipFile = fso.GetAbsolutePathName(pathToZipFile)
   dirToZip = fso.GetAbsolutePathName(dirToZip)

   If fso.FileExists(pathToZipFile) Then
       'WScript.Echo "That zip file already exists - deleting it."
       fso.DeleteFile pathToZipFile
   End If

   If Not fso.FolderExists(dirToZip) Then
       'WScript.Echo "The directory to zip does not exist."
       Exit Sub
   End If

   NewZip pathToZipFile

   dim sa
   set sa = CreateObject("Shell.Application")

   Dim zip
   Set zip = sa.NameSpace(pathToZipFile)

   'WScript.Echo "opening dir  (" & dirToZip & ")"

   Dim d
   Set d = sa.NameSpace(dirToZip)

   ' Look at http://msdn.microsoft.com/en-us/library/bb787866(VS.85).aspx
   ' for more information about the CopyHere function.
   zip.CopyHere d.items, 4

   Do Until d.Items.Count <= zip.Items.Count
       Wscript.Sleep(200)
   Loop
End Sub
