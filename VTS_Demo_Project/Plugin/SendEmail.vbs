Call SendEmail()
Public Function SendEmail()
	    Set Arg = WScript.Arguments
	    strSubject = Arg(0)
	    strTOfield = Arg(1)
            strCCfield = Arg(2)
	    strAttachment = Arg(3)
		strEmailHeader = "Hi,<br><br>Please find the attachment for FCR test automation execution result.<br><br><br>=========================<br><tt>Note : Don't reply to all.</tt><br>=========================<br>"
		If instr(strSubject,"BBM") > 0 Then			
			strEmailHeader = "Hi,<br><br>Please find the attachment for FCR test automation execution result.<br><br>=========================<h5>Note : Don't reply to all.</h5>=========================<h5>Build Status : <font color=RED>RED</font> <br> Reason : Unable to login into FCR BBM Application as application gets stuck post entering login credentials.<br> Current progress :<ul><li> Following up with FCR support team.  </li></ul></h5>========================================================================================================================================================================================<br>"		
		End If
	    
	    strEmailFooter = "</table><br>Regards,<br>FCR Testing Team"
		'msgbox strAttachment 
	    Const ForReading = 1
	    Const ForWriting = 2
	    Const ForAppending = 8
	    Const cdoSendUsingPort = 2  'Send the message using the network (SMTP over the network). 
	    Const cdoAnonymous = 0      'Do not authenticate
	    Const cdoBasic = 1          'basic (clear-text) authentication
	    Const cdoNTLM = 2           'NTLM
	    Dim objFSO
	    Dim objFile
	    Dim strHTMLText
	    Dim objMessage
'		strAttachment = Environment("TestDir") & "\" &"Result"&"\"& Settings("Folder") & ".html"
		gstrHexBuildStatus = ""
	    Set objFSO = CreateObject("Scripting.FileSystemObject")
		'strReportName = Environment("TestDir") & "\" &"Result"&"\"& Settings("Folder") & ".html"
		strAttachment = Replace(strAttachment,"%"," ")
		Set objFileNew = objFSO.OpenTextFile(strAttachment, ForReading,1)
	    strHTMLText = objFileNew.ReadAll
	    objFileNew.Close
	    Set objFileNew = Nothing
	    Set objFSO = Nothing
		Set oOutlook = CreateObject("Outlook.Application")
		'oOutlook.Visible = True
		Set oMail = oOutlook.CreateItem(0)
		oMail.To = strTOfield 
		oMail.Subject = Replace(strSubject,"%"," ")
		oMail.CC = strCCfield 
		oMail.htmlbody = strEmailHeader & strHTMLText & strEmailFooter
		oMail.Attachments.Add strAttachment
		oMail.Send
		WScript.Sleep 5000
		Set oMail = Nothing
		Set oOutlook = Nothing
		Set Arg = Nothing
		Set objFSO = Nothing
End Function